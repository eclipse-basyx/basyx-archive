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
using System.Xml.Serialization;
using BaSyx.Models.Core.Common;
using BaSyx.Models.Core.AssetAdministrationShell.Views;
using BaSyx.Models.Core.AssetAdministrationShell.Semantics;

namespace BaSyx.Models.Core.AssetAdministrationShell.Implementations
{
    [DataContract, XmlType]
    public class AssetAdministrationShell : IAssetAdministrationShell
    {
        [XmlElement]
        public string IdShort { get; set; }
        [XmlElement]
        public Identifier Identification { get; set; }
        public IAsset Asset { get; set; }
        public IElementContainer<ISubmodel> Submodels { get; set; }
        public IReference Parent { get; set; }
        public LangStringSet Description { get; set; }
        public Dictionary<string, string> MetaData { get; set; }        
        public IReference<IAssetAdministrationShell> DerivedFrom { get; set; }     
        public IElementContainer<IView> Views { get; set; }
        public AdministrativeInformation Administration { get; set; }
        public string Category { get; set; }
        public ModelType ModelType => ModelType.AssetAdministationShell;
        public IEnumerable<IEmbeddedDataSpecification> EmbeddedDataSpecifications { get; }
        public IElementContainer<IConceptDictionary> ConceptDictionaries { get; set; }
        public IConceptDescription ConceptDescription { get; set; }

        [JsonConstructor]
        public AssetAdministrationShell()
        {
            Submodels = new ElementContainer<ISubmodel>();
            Views = new ElementContainer<IView>();
            MetaData = new Dictionary<string, string>();
            ConceptDictionaries = new ElementContainer<IConceptDictionary>();
            EmbeddedDataSpecifications = new List<IEmbeddedDataSpecification>();
        }
    }
}
