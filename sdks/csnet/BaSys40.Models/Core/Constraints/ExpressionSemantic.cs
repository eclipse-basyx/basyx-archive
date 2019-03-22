using System.Runtime.Serialization;

namespace BaSys40.Models.Core.Constraints
{
    [DataContract]
    public enum ExpressionSemantic : int
    {
        None = 0,
        Requirement = 1,
        Assurance = 2,
        Confirmation = 3,
        Measurement = 4,
        Setting = 5
    }
}
