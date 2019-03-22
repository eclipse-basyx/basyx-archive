using System.Runtime.Serialization;

namespace BaSys40.Models.Core.Constraints
{
    [DataContract]
    public enum ExpressionLogic : int
    {
        None = 0,
        Equal = 1,
        NotEqual = 2,
        GreaterThan = 3,
        GreaterEqual = 4,
        Less = 5,
        LessEqual = 6 
    }
}
