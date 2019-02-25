using BaSys40.Models.Core.AssetAdministrationShell.Enums;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.Extensions.References;
using BaSys40.Models.Core.Identification;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Implementations
{
    [DataContract]
    public class Submodel : ISubmodel
    {
        public string IdShort { get; set; }
        public Identifier Identification { get; set; }
        public IReference Parent { get; set; }
        public Kind? Kind { get; set; }
        public IReference SemanticId { get; set; }
        public List<Description> Descriptions { get; set; }
        public ElementContainer<IDataElement> DataElements { get; set; }
        public ElementContainer<IOperation> Operations { get; set; }
        public ElementContainer<IEvent> Events { get; set; }
        public ElementContainer<ISubmodelElement> SubmodelElements { get; set; }
        public Dictionary<string, string> MetaData { get; set; }
        public AdministrativeInformation Administration { get; set; }
        public string Category { get; set; }
        public ModelType ModelType => ModelType.Submodel;
        public List<IEmbeddedDataSpecification> EmbeddedDataSpecifications { get; set; }
        public IConceptDescription ConceptDescription { get; set; }

    }
}
