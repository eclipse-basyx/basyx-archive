using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Generics
{
    public interface IOperationVariable : ISubmodelElement
    {
        [DataMember]
        int? Index { get; set; }
        [DataMember]
        DataType DataType { get; set; }
    }
}
