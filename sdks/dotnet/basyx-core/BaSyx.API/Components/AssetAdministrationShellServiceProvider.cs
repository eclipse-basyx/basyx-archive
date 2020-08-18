/*******************************************************************************
* Copyright (c) 2020 Robert Bosch GmbH
* Author: Constantin Ziesche (constantin.ziesche@bosch.com)
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License 2.0 which is available at
* http://www.eclipse.org/legal/epl-2.0
*
* SPDX-License-Identifier: EPL-2.0
*******************************************************************************/
using BaSyx.Models.Core.AssetAdministrationShell.Generics;
using BaSyx.Utils.ResultHandling;
using System.Collections.Generic;
using System.Linq;
using BaSyx.API.AssetAdministrationShell.Extensions;
using BaSyx.Models.Connectivity.Descriptors;
using BaSyx.Models.Core.Common;

namespace BaSyx.API.Components
{
    public abstract class AssetAdministrationShellServiceProvider : IAssetAdministrationShellServiceProvider, ISubmodelServiceProviderRegistry
    {
        public abstract IAssetAdministrationShell AssetAdministrationShell { get; protected set; }

        private IAssetAdministrationShellDescriptor _serviceDescriptor;
        public IAssetAdministrationShellDescriptor ServiceDescriptor
        {
            get
            {
                if (_serviceDescriptor == null)
                    _serviceDescriptor = new AssetAdministrationShellDescriptor(AssetAdministrationShell, null);

                if (_serviceDescriptor.SubmodelDescriptors?.Count == 0 && SubmodelServiceProviders != null)
                    foreach (var submodelServiceProvider in SubmodelServiceProviders)
                    {
                        if (submodelServiceProvider.Value?.ServiceDescriptor != null)
                            _serviceDescriptor.SubmodelDescriptors.Create(submodelServiceProvider.Value.ServiceDescriptor);
                    }

                return _serviceDescriptor;
            }
            private set
            {
                _serviceDescriptor = value;
            }
        }
        public ISubmodelServiceProviderRegistry SubmodelRegistry => this;
        private Dictionary<string, ISubmodelServiceProvider> SubmodelServiceProviders { get; } = new Dictionary<string, ISubmodelServiceProvider>();

        public AssetAdministrationShellServiceProvider(IAssetAdministrationShellDescriptor assetAdministrationShellDescriptor) : this()
        {
            ServiceDescriptor = assetAdministrationShellDescriptor;
        }
        public AssetAdministrationShellServiceProvider(IAssetAdministrationShell assetAdministrationShell)
        {
            BindTo(assetAdministrationShell);
        }

        public AssetAdministrationShellServiceProvider()
        {
            AssetAdministrationShell = GenerateAssetAdministrationShell();
            BindTo(AssetAdministrationShell);
        }

        public virtual void UseDefaultSubmodelServiceProvider()
        {
            foreach (var submodel in AssetAdministrationShell.Submodels)
            {
                var submodelServiceProvider = submodel.CreateServiceProvider();
                RegisterSubmodelServiceProvider(submodel.IdShort, submodelServiceProvider);
            }
        }

        public virtual IResult<ISubmodelDescriptor> RegisterSubmodelServiceProvider(string submodelId, ISubmodelServiceProvider submodelServiceProvider)
        {
            if (SubmodelServiceProviders.ContainsKey(submodelId))
                SubmodelServiceProviders[submodelId] = submodelServiceProvider;
            else
                SubmodelServiceProviders.Add(submodelId, submodelServiceProvider);

            return new Result<ISubmodelDescriptor>(true, submodelServiceProvider.ServiceDescriptor);
        }
        public virtual IResult<ISubmodelServiceProvider> GetSubmodelServiceProvider(string submodelId)
        {
            if (SubmodelServiceProviders.TryGetValue(submodelId, out ISubmodelServiceProvider submodelServiceProvider))
                return new Result<ISubmodelServiceProvider>(true, submodelServiceProvider);
            else
                return new Result<ISubmodelServiceProvider>(false, new NotFoundMessage());
        }

        public virtual IResult UnregisterSubmodelServiceProvider(string submodelId)
        {
            if (SubmodelServiceProviders.ContainsKey(submodelId))
            {
                SubmodelServiceProviders.Remove(submodelId);
                return new Result<ISubmodelServiceProvider>(true);
            }
            else
                return new Result<ISubmodelServiceProvider>(false, new NotFoundMessage());
        }

        public abstract IAssetAdministrationShell GenerateAssetAdministrationShell();

        public virtual void BindTo(IAssetAdministrationShell element)
        {
            AssetAdministrationShell = element;
            ServiceDescriptor = ServiceDescriptor ?? new AssetAdministrationShellDescriptor(element, null);
        }
        public virtual IAssetAdministrationShell GetBinding()
        {
            return AssetAdministrationShell;
        }

        public virtual IResult<ISubmodel> CreateSubmodel(ISubmodel submodel)
        {
            if (AssetAdministrationShell.Submodels == null)
                AssetAdministrationShell.Submodels = new ElementContainer<ISubmodel>();
            return AssetAdministrationShell.Submodels.Create(submodel);
        }

        public virtual IResult DeleteSubmodel(string submodelId)
        {
            if (AssetAdministrationShell.Submodels == null)
                return new Result(false, new NotFoundMessage(submodelId));
            return AssetAdministrationShell.Submodels.Delete(submodelId);
        }

        public virtual IResult<ISubmodel> RetrieveSubmodel(string submodelId)
        {
            if (AssetAdministrationShell.Submodels == null)
                return new Result<ISubmodel>(false, new NotFoundMessage(submodelId));
            return AssetAdministrationShell.Submodels.Retrieve(submodelId);
        }

        public virtual IResult<IElementContainer<ISubmodel>> RetrieveSubmodels()
        {
            if (AssetAdministrationShell.Submodels == null)
                return new Result<ElementContainer<ISubmodel>>(false, new NotFoundMessage("Submodels"));
            return AssetAdministrationShell.Submodels.RetrieveAll();
        }

        public virtual IResult<IEnumerable<ISubmodelServiceProvider>> GetSubmodelServiceProviders()
        {
            if (SubmodelServiceProviders.Values == null)
                return new Result<IEnumerable<ISubmodelServiceProvider>>(false, new NotFoundMessage("Submodel Service Providers"));

            return new Result<IEnumerable<ISubmodelServiceProvider>>(true, SubmodelServiceProviders.Values?.ToList());
        }

        public IResult<IAssetAdministrationShell> RetrieveAssetAdministrationShell()
        {
            var binding = GetBinding();
            return new Result<IAssetAdministrationShell>(binding == null, binding);
        }
    }
}
