using BaSys40.Models.Core.AssetAdministrationShell.Enums;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.Attributes;
using BaSys40.Models.Core.Extensions.References;
using BaSys40.Models.Core.Identification;
using BaSys40.Models.Core.Views;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Implementations
{
    [DataContract]
    public class AssetAdministrationShell : IAssetAdministrationShell
    {
        public string IdShort { get; set; }
        public Identifier Identification { get; set; }
        [ExportAsReference(KeyElements.Asset)]
        public IAsset Asset { get; set; }
        [ExportAsReference(KeyElements.Submodel)]
        public ElementContainer<ISubmodel> Submodels { get; set; }
        public IReference Parent { get; set; }
        public List<Description> Descriptions { get; set; }
        public Dictionary<string, string> MetaData { get; set; }        
        public IReference<IAssetAdministrationShell> DerivedFrom { get; set; }     
        public ElementContainer<IView> Views { get; set; }
        public AdministrativeInformation Administration { get; set; }
        public string Category { get; set; }
        public ModelType ModelType => ModelType.AssetAdministationShell;
        public List<IEmbeddedDataSpecification> EmbeddedDataSpecifications { get; }
        public List<ConceptDictionary> ConceptDictionaries { get; set; }
        public IConceptDescription ConceptDescription { get; set; }
    }
}
