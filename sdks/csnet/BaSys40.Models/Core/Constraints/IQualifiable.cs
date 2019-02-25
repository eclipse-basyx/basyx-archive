using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.Constraints
{
    public interface IQualifiable
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "constraints")]
        List<IConstraint> Constraints { get; }
    }
}
