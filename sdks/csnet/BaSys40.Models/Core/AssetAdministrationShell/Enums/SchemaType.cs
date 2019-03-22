using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Enums
{
    [DataContract]
    public enum SchemaType : int
    {
        None = 0,
        XSD = 1,
        RDFS = 2,
        JSchema = 3
    }
}
