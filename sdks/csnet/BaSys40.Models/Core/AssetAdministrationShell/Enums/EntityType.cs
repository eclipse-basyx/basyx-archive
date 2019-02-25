using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Enums
{
    [DataContract]
    public enum EntityType : int
    {
        None = 0,
        Primitive = 1,
        Object = 2
    }
}
