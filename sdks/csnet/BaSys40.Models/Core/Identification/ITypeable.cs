using BaSys40.Models.Core.AssetAdministrationShell.Enums;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.Identification
{
    public interface ITypeable
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "kind")]
        Kind? Kind { get; }
    }
}
