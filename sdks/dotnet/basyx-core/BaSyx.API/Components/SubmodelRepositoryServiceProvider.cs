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
using BaSyx.API.AssetAdministrationShell.Extensions;
using BaSyx.Models.Connectivity.Descriptors;
using BaSyx.Models.Core.AssetAdministrationShell.Generics;
using BaSyx.Models.Core.Common;
using BaSyx.Utils.ResultHandling;
using System;
using System.Collections.Generic;
using System.Linq;

namespace BaSyx.API.Components
{
    public class SubmodelRepositoryServiceProvider : ISubmodelRepositoryServiceProvider
    {
        public IEnumerable<ISubmodel> Submodels => GetBinding();

        private Dictionary<string, ISubmodelServiceProvider> SubmodelServiceProviders { get; }

        private ISubmodelRepositoryDescriptor _serviceDescriptor;
        public ISubmodelRepositoryDescriptor ServiceDescriptor
        {
            get
            {
                if (_serviceDescriptor == null)
                    _serviceDescriptor = new SubmodelRepositoryDescriptor(Submodels, null);

                return _serviceDescriptor;
            }
            private set
            {
                _serviceDescriptor = value;
            }
        }
        public SubmodelRepositoryServiceProvider(ISubmodelRepositoryDescriptor descriptor) : this()
        {
            ServiceDescriptor = descriptor;
        }

        public SubmodelRepositoryServiceProvider()
        {
            SubmodelServiceProviders = new Dictionary<string, ISubmodelServiceProvider>();
        }

        public void BindTo(IEnumerable<ISubmodel> submodels)
        {
            foreach (var submodel in submodels)
            {
                RegisterSubmodelServiceProvider(submodel.IdShort, submodel.CreateServiceProvider());
            }
            ServiceDescriptor = ServiceDescriptor ?? new SubmodelRepositoryDescriptor(submodels, null);
        }
        public IEnumerable<ISubmodel> GetBinding()
        {
            IEnumerable<ISubmodelServiceProvider> serviceProviders = GetSubmodelServiceProviders();
            List<ISubmodel> submodels = new List<ISubmodel>();
            foreach (var serviceProvider in serviceProviders)
            {
                ISubmodel binding = serviceProvider.GetBinding();
                submodels.Add(binding);
            }
            return submodels;
        }

        public IResult<ISubmodel> CreateSubmodel(ISubmodel submodel)
        {
            if (submodel == null)
                return new Result<ISubmodel>(new ArgumentNullException(nameof(submodel)));
            RegisterSubmodelServiceProvider(submodel.IdShort, submodel.CreateServiceProvider());

            ISubmodelServiceProvider serviceProvider = GetSubmodelServiceProvider(submodel.IdShort);
            if (serviceProvider != null && serviceProvider.GetBinding() != null)
                return new Result<ISubmodel>(true, serviceProvider.GetBinding());
            else
                return new Result<ISubmodel>(false, new Message(MessageType.Error, "Could not retrieve Submodel Service Provider"));
        }

        public IResult DeleteSubmodel(string submodelId)
        {
            if (string.IsNullOrEmpty(submodelId))
                return new Result<ISubmodel>(new ArgumentNullException(nameof(submodelId)));
            UnregisterSubmodelServiceProvider(submodelId);
            return new Result(true);
        }

        public ISubmodelServiceProvider GetSubmodelServiceProvider(string id)
        {
            if (SubmodelServiceProviders.TryGetValue(id, out ISubmodelServiceProvider submodelServiceProvider))
                return submodelServiceProvider;
            else
                return null;
        }

        public IEnumerable<ISubmodelServiceProvider> GetSubmodelServiceProviders()
        {
           return SubmodelServiceProviders?.Values.ToList();
        }

        public void RegisterSubmodelServiceProvider(string id, ISubmodelServiceProvider submodelServiceProvider)
        {
            if (!SubmodelServiceProviders.ContainsKey(id))
                SubmodelServiceProviders.Add(id, submodelServiceProvider);
        }

        public void UnregisterSubmodelServiceProvider(string id)
        {
            if (!SubmodelServiceProviders.ContainsKey(id))
                SubmodelServiceProviders.Remove(id);
        }

        public IResult<ISubmodel> RetrieveSubmodel(string submodelId)
        {
            ISubmodelServiceProvider serviceProvider = GetSubmodelServiceProvider(submodelId);
            if(serviceProvider != null && serviceProvider.GetBinding() != null)
            {
                ISubmodel binding = serviceProvider.GetBinding();
                return new Result<ISubmodel>(true, binding);
            }
            return new Result<ISubmodel>(false, new NotFoundMessage("Submodel Service Provider"));
        }

        public IResult<IElementContainer<ISubmodel>> RetrieveSubmodels()
        {
            return new Result<IElementContainer<ISubmodel>>(true, new ElementContainer<ISubmodel>(Submodels));
        }

        public IResult UpdateSubmodel(string submodelId, ISubmodel submodel)
        {
            if (string.IsNullOrEmpty(submodelId))
                return new Result<ISubmodel>(new ArgumentNullException(nameof(submodelId)));
            if (submodel == null)
                return new Result<ISubmodel>(new ArgumentNullException(nameof(submodel)));
            return CreateSubmodel(submodel);
        }
    }
}
