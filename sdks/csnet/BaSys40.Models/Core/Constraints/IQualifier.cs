using BaSys40.Models.Core.Extensions.References;
using BaSys40.Models.Semantics;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.Constraints
{
    public interface IQualifier : IConstraint, IHasSemantics
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "qualifierType")]
        string QualifierType { get; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "qualifierValue")]
        object QualifierValue { get; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "qualifierValueId")]
        IReference QualifierValueId { get; }
    }
}
