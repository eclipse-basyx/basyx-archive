using BaSys40.Models.Core.Identification;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.Extensions.References
{
    public interface IKey
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "type")]
        KeyElements? Type { get; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "idType")]
        KeyType? IdType { get; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "value")]
        string Value { get; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "local")]
        bool? Local { get; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "index")]
        int? Index { get; set; }
    }
}