using System.Runtime.Serialization;

namespace BaSys40.Models.Core.Extensions.References
{    
    [DataContract]
    public enum KeyElements : int
    {
        GlobalReference,

        ConceptDictionary,
        AccessPermissionRule,
        DataElement,
        View,
        Property,
        SubmodelElement,
        File,
        Blob,
        ReferenceElement,
        SubmodelElementCollection,
        RelationshipElement,
        Event,
        Operation,
        OperationParameter,

        AssetAdministrationShell,
        Submodel,
        ConceptDescription,
        Asset
    }
}