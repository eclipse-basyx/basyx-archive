using Newtonsoft.Json;

namespace BaSys40.Models.Core
{
    public class ModelType : DataObjectType
    {
        public static readonly ModelType Asset = new ModelType("Asset");
        public static readonly ModelType AssetAdministationShell = new ModelType("AssetAdministationShell");
        public static readonly ModelType Submodel = new ModelType("Submodel");
        public static readonly ModelType SubmodelElement = new ModelType("SubmodelElement");
        public static readonly ModelType SubmodelElementCollection = new ModelType("SubmodelElementCollection");
        public static readonly ModelType Operation = new ModelType("Operation");
        public static readonly ModelType OperationVariable = new ModelType("OperationVariable");
        public static readonly ModelType Event = new ModelType("Event");
        public static readonly ModelType View = new ModelType("View");
        public static readonly ModelType RelationshipElement = new ModelType("RelationshipElement");
        public static readonly ModelType DataElement = new ModelType("DataElement");
        public static readonly ModelType Property = new ModelType("Property");
        public static readonly ModelType File = new ModelType("File");
        public static readonly ModelType Blob = new ModelType("Blob");
        public static readonly ModelType ReferenceElement = new ModelType("ReferenceElement");
        public static readonly ModelType DataElementCollection = new ModelType("DataElementCollection");
        

        public static readonly ModelType Constraint = new ModelType("Constraint");
        public static readonly ModelType Formula = new ModelType("Formula");
        public static readonly ModelType Qualifier = new ModelType("Qualifier");

        public static readonly ModelType ConceptDescription = new ModelType("ConceptDescription");

        [JsonConstructor]
        protected ModelType(string name) : base(name)
        { }
    }
}
