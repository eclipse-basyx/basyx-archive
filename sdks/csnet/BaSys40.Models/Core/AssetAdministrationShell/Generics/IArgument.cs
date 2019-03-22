using BaSys40.Models.Core.AssetAdministrationShell.Enums;
using BaSys40.Models.Core.Constraints;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Generics
{
    public interface IArgument : IQualifiable, IValue
    {
        [DataMember]
        int? Index { get; set; }
        [DataMember]
        string IdShort { get; set; }     
    }   
}
