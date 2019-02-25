using BaSys40.Models.Core.AssetAdministrationShell.Enums;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.Constraints;
using BaSys40.Models.Core.Extensions.References;
using BaSys40.Models.Core.Identification;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Implementations
{
    [DataContract]
    public abstract class SubmodelElement : ISubmodelElement
    {
        public IReference SemanticId { get; set; }

        public List<IConstraint> Constraints { get; set; }

        public string IdShort { get; set; }

        public string Category { get; set; }

        public List<Description> Descriptions { get; set; }

        public IReference Parent { get; set; }

        public Dictionary<string, string> MetaData { get; set; }

        public Kind? Kind { get; set; }

        public abstract ModelType ModelType { get; }

        public List<IEmbeddedDataSpecification> EmbeddedDataSpecifications { get; set; }
        public IConceptDescription ConceptDescription { get; set; }

        public SubmodelElement() : this(null)
        { }

        [JsonConstructor]
        public SubmodelElement(IConceptDescription conceptDescription, params IEmbeddedDataSpecification[] embeddedDataSpecifications)
        {
            ConceptDescription = conceptDescription;    
            EmbeddedDataSpecifications = embeddedDataSpecifications?.ToList();

            if (ConceptDescription?.EmbeddedDataSpecifications?.Count > 0)
                EmbeddedDataSpecifications.AddRange(ConceptDescription.EmbeddedDataSpecifications);
        }
    }
}
