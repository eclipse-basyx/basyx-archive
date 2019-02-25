using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Enums
{
    [DataContract]
    public enum Kind : int
    {
        Type = 0,
        Instance = 1
    }
}
