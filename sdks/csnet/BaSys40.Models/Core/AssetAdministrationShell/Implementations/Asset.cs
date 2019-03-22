using BaSys40.Models.Core.AssetAdministrationShell.Enums;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.Extensions.References;
using BaSys40.Models.Core.Identification;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Implementations
{
    [DataContract]
    public class Asset : IAsset
    {
        public string IdShort { get; set; }
        public Identifier Identification { get; set; }
        public Kind? Kind { get; set; }    
        public List<Description> Descriptions { get; set; }
        public IReference Parent { get; set; }
        public Dictionary<string, string> MetaData { get; set; }
        public IReference<ISubmodel> AssetIdentificationModel { get; set; }
        public AdministrativeInformation Administration { get; set; }
        public string Category { get; set; }
        public IReference SemanticId { get; set; }
        public List<IEmbeddedDataSpecification> EmbeddedDataSpecifications { get; }
        public IConceptDescription ConceptDescription { get; set; }
        public ModelType ModelType => ModelType.Asset;
    }
}
