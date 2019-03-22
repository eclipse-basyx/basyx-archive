using BaSys40.Models.Core.Extensions.References;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Generics
{
    public interface IEmbeddedDataSpecification
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "hasDataSpecification")]
        IReference HasDataSpecification { get; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "dataSpecificationContent")]
        IDataSpecificationContent DataSpecificationContent { get; }
    }
}