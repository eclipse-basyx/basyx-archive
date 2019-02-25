using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Generics
{
    public interface IEvent : ISubmodelElement
    {
        [DataMember]
        DataType DataType { get; }
    }
}
