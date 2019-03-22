using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.Extensions.References;
using BaSys40.Models.Core.Identification;
using BaSys40.Models.Semantics;
using System;
using System.Collections.Generic;
using System.Text;

namespace BaSys40.Models.Core.AssetAdministrationShell.Implementations
{
    public class ConceptDescription : IConceptDescription
    {
        public List<IEmbeddedDataSpecification> EmbeddedDataSpecifications { get; set; }

        public List<IReference> IsCaseOf { get; set; }

        public Identifier Identification { get; set; }

        public AdministrativeInformation Administration { get; set; }

        public string IdShort { get; set; }

        public string Category { get; set; }

        public List<Description> Descriptions { get; set; }

        public IReference Parent { get; set; }

        public Dictionary<string, string> MetaData { get; set; }

        public ModelType ModelType => ModelType.ConceptDescription;

        IConceptDescription IHasDataSpecification.ConceptDescription => this;
    }
}
