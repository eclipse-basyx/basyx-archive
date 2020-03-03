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
using BaSyx.Models.Core.AssetAdministrationShell.References;
using BaSyx.Models.Core.AssetAdministrationShell.Identification;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.Runtime.Serialization;
using BaSyx.Models.Core.Common;
using System.Linq;
using System.Collections;

namespace BaSyx.Models.Connectivity.Descriptors
{
    [DataContract]
    public class AssetAdministrationShellAggregatorDescriptor : IAssetAdministrationShellAggregatorDescriptor
    {
        [IgnoreDataMember]
        public Identifier Identification { get; set; }
        [IgnoreDataMember]
        public AdministrativeInformation Administration { get; set; }
        [IgnoreDataMember]
        public string IdShort { get; set; }
        [IgnoreDataMember]
        public LangStringSet Description { get; set; }
        public IEnumerable<IEndpoint> Endpoints { get; internal set; }

        [IgnoreDataMember]
        public IReference Parent => null;
        [IgnoreDataMember]
        public string Category => null;

        public ModelType ModelType => ModelType.AssetAdministrationShellAggregatorDescriptor;

        public IElementContainer<IAssetAdministrationShellDescriptor> AssetAdministrationShellDescriptors { get; set; }

        public AssetAdministrationShellAggregatorDescriptor(IEnumerable<IEndpoint> endpoints) 
        {
            Endpoints = endpoints ?? new List<IEndpoint>();
            AssetAdministrationShellDescriptors = new ElementContainer<IAssetAdministrationShellDescriptor>();
        }
     
        [JsonConstructor]
        public AssetAdministrationShellAggregatorDescriptor(IEnumerable<IAssetAdministrationShell> shells, IEnumerable<IEndpoint> endpoints) : this(endpoints)
        {
            if (shells?.Count() > 0)
                foreach (var shell in shells)
                {
                    AddAssetAdministrationShell(shell);
                }
        }

        public void AddAssetAdministrationShell(IAssetAdministrationShell aas)
        {
            AssetAdministrationShellDescriptor assetAdministrationShellDescriptor = new AssetAdministrationShellDescriptor(aas, Endpoints.ToList());
            if (aas.Submodels?.Count > 0)
                foreach (var submodel in aas.Submodels)
                {
                    assetAdministrationShellDescriptor.SubmodelDescriptors.Create(new SubmodelDescriptor(submodel, Endpoints.ToList()));
                }
               
            AssetAdministrationShellDescriptors.Create(assetAdministrationShellDescriptor);
        }

        public void AddEndpoints(IEnumerable<IEndpoint> endpoints)
        {
            foreach (var endpoint in endpoints)
            {
                (Endpoints as IList).Add(endpoint);
            }
        }

        public void SetEndpoints(IEnumerable<IEndpoint> endpoints)
        {
            Endpoints = endpoints;
        }
    }
}
