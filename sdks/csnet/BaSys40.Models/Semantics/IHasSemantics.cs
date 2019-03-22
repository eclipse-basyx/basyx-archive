using BaSys40.Models.Core.Extensions.References;
using System.Runtime.Serialization;

namespace BaSys40.Models.Semantics
{
    public interface IHasSemantics
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "semanticId")]
        IReference SemanticId { get; }
    }
}
