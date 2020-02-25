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
using BaSyx.Models.Core.AssetAdministrationShell.Enums;
using BaSyx.Models.Core.AssetAdministrationShell.Generics;
using BaSyx.Models.Core.AssetAdministrationShell.Constraints;
using BaSyx.Models.Core.AssetAdministrationShell.References;
using BaSyx.Models.Core.AssetAdministrationShell.Identification;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.Runtime.Serialization;
using BaSyx.Models.Core.AssetAdministrationShell.Semantics;
using BaSyx.Models.Core.Common;
using BaSyx.Models.Core.AssetAdministrationShell.Generics.SubmodelElementTypes;

namespace BaSyx.Models.Core.AssetAdministrationShell.Implementations
{
    [DataContract]
    public class Submodel : ISubmodel
    {
        public string IdShort { get; set; }
        public Identifier Identification { get; set; }
        public IReference Parent { get; set; }
        public ModelingKind Kind { get; set; }
        public IReference SemanticId { get; set; }
        public LangStringSet Description { get; set; }
        public IEnumerable<IProperty> Properties => SubmodelElements.FilterAsReference<IProperty>(ModelType.Property);
        public IEnumerable<IOperation> Operations => SubmodelElements.FilterAsReference<IOperation>(ModelType.Operation);
        public IEnumerable<IEvent> Events => SubmodelElements.FilterAsReference<IEvent>(ModelType.Event);
        public IElementContainer<ISubmodelElement> SubmodelElements { get; set; }
        public Dictionary<string, string> MetaData { get; set; }
        public AdministrativeInformation Administration { get; set; }
        public string Category { get; set; }
        public ModelType ModelType => ModelType.Submodel;
        public IEnumerable<IEmbeddedDataSpecification> EmbeddedDataSpecifications { get; set; }
        public IConceptDescription ConceptDescription { get; set; }
        public List<IConstraint> Constraints { get; set; }

        [JsonConstructor]
        public Submodel()
        {
            SubmodelElements = new ElementContainer<ISubmodelElement>();
            MetaData = new Dictionary<string, string>();
            EmbeddedDataSpecifications = new List<IEmbeddedDataSpecification>();
            Constraints = new List<IConstraint>();
        }

    }
}
