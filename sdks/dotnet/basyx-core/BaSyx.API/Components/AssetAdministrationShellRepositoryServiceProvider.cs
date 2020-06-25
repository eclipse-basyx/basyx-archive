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
    public class AssetAdministrationShellRepositoryServiceProvider : IAssetAdministrationShellRepositoryServiceProvider
    {
        public IEnumerable<IAssetAdministrationShell> AssetAdministrationShells => GetBinding();

        private Dictionary<string, IAssetAdministrationShellServiceProvider> AssetAdministrationShellServiceProviders { get; }

        private IAssetAdministrationShellRepositoryDescriptor _serviceDescriptor;
        public IAssetAdministrationShellRepositoryDescriptor ServiceDescriptor
        {
            get
            {
                if (_serviceDescriptor == null)
                    _serviceDescriptor = new AssetAdministrationShellRepositoryDescriptor(AssetAdministrationShells, null);

                return _serviceDescriptor;
            }
            private set
            {
                _serviceDescriptor = value;
            }
        }
        public AssetAdministrationShellRepositoryServiceProvider(IAssetAdministrationShellRepositoryDescriptor descriptor) : this()
        {
            ServiceDescriptor = descriptor;
        }

        public AssetAdministrationShellRepositoryServiceProvider()
        {
            AssetAdministrationShellServiceProviders = new Dictionary<string, IAssetAdministrationShellServiceProvider>();
        }

        public void BindTo(IEnumerable<IAssetAdministrationShell> assetAdministrationShells)
        {
            foreach (var assetAdministrationShell in assetAdministrationShells)
            {
                RegisterAssetAdministrationShellServiceProvider(assetAdministrationShell.IdShort, assetAdministrationShell.CreateServiceProvider(true));
            }
            ServiceDescriptor = ServiceDescriptor ?? new AssetAdministrationShellRepositoryDescriptor(assetAdministrationShells, null);
        }
        public IEnumerable<IAssetAdministrationShell> GetBinding()
        {
            IEnumerable<IAssetAdministrationShellServiceProvider> serviceProviders = GetAssetAdministrationShellServiceProviders();
            List<IAssetAdministrationShell> assetAdministrationShells = new List<IAssetAdministrationShell>();
            foreach (var serviceProvider in serviceProviders)
            {
                IAssetAdministrationShell binding = serviceProvider.GetBinding();
                assetAdministrationShells.Add(binding);
            }
            return assetAdministrationShells;
        }

        public IResult<IAssetAdministrationShell> CreateAssetAdministrationShell(IAssetAdministrationShell aas)
        {
            if (aas == null)
                return new Result<IAssetAdministrationShell>(new ArgumentNullException(nameof(aas)));
            RegisterAssetAdministrationShellServiceProvider(aas.IdShort, aas.CreateServiceProvider(true));

            IAssetAdministrationShellServiceProvider serviceProvider = GetAssetAdministrationShellServiceProvider(aas.IdShort);
            if (serviceProvider != null && serviceProvider.GetBinding() != null)
                return new Result<IAssetAdministrationShell>(true, serviceProvider.GetBinding());
            else
                return new Result<IAssetAdministrationShell>(false, new Message(MessageType.Error, "Could not retrieve Asset Administration Shell Service Provider"));
        }

        public IResult DeleteAssetAdministrationShell(string aasId)
        {
            if (string.IsNullOrEmpty(aasId))
                return new Result<IAssetAdministrationShell>(new ArgumentNullException(nameof(aasId)));
            UnregisterAssetAdministrationShellServiceProvider(aasId);
            return new Result(true);
        }

        public IAssetAdministrationShellServiceProvider GetAssetAdministrationShellServiceProvider(string id)
        {
            if (AssetAdministrationShellServiceProviders.TryGetValue(id, out IAssetAdministrationShellServiceProvider assetAdministrationShellServiceProvider))
                return assetAdministrationShellServiceProvider;
            else
                return null;
        }

        public IEnumerable<IAssetAdministrationShellServiceProvider> GetAssetAdministrationShellServiceProviders()
        {
           return AssetAdministrationShellServiceProviders?.Values.ToList();
        }

        public void RegisterAssetAdministrationShellServiceProvider(string id, IAssetAdministrationShellServiceProvider assetAdministrationShellServiceProvider)
        {
            if (!AssetAdministrationShellServiceProviders.ContainsKey(id))
                AssetAdministrationShellServiceProviders.Add(id, assetAdministrationShellServiceProvider);
        }

        public void UnregisterAssetAdministrationShellServiceProvider(string id)
        {
            if (!AssetAdministrationShellServiceProviders.ContainsKey(id))
                AssetAdministrationShellServiceProviders.Remove(id);
        }

        public IResult<IAssetAdministrationShell> RetrieveAssetAdministrationShell(string aasId)
        {
            IAssetAdministrationShellServiceProvider serviceProvider = GetAssetAdministrationShellServiceProvider(aasId);
            if(serviceProvider != null && serviceProvider.GetBinding() != null)
            {
                IAssetAdministrationShell binding = serviceProvider.GetBinding();
                return new Result<IAssetAdministrationShell>(true, binding);
            }
            return new Result<IAssetAdministrationShell>(false, new NotFoundMessage("Asset Administration Shell Service Provider"));
        }

        public IResult<IElementContainer<IAssetAdministrationShell>> RetrieveAssetAdministrationShells()
        {
            return new Result<IElementContainer<IAssetAdministrationShell>>(true, new ElementContainer<IAssetAdministrationShell>(AssetAdministrationShells));
        }

        public IResult UpdateAssetAdministrationShell(string aasId, IAssetAdministrationShell aas)
        {
            if (string.IsNullOrEmpty(aasId))
                return new Result<IAssetAdministrationShell>(new ArgumentNullException(nameof(aasId)));
            if (aas == null)
                return new Result<IAssetAdministrationShell>(new ArgumentNullException(nameof(aas)));
            return CreateAssetAdministrationShell(aas);
        }
    }
}
