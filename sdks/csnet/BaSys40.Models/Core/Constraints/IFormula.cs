using BaSys40.Models.Core.Extensions.References;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.Constraints
{
    public interface IFormula : IConstraint
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "dependsOn")]
        List<IReference> DependsOn { get; }
    }
}
