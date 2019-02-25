using System.Runtime.Serialization;

namespace BaSys40.Models.Core.Constraints
{
    [DataContract]
    public static class QualifierType
    {
        public const string ExpressionLogic = "ExpressionLogic";
        public const string ExpressionSemantic = "ExpressionSemantic";
        public const string Enumeration = "Enumeration";
        public const string Owner = "Owner";
        public const string Min = "Min";
        public const string Max = "Max";
        public const string StrLen = "StrLen";
        public const string MimeType = "MimeType";
        public const string RegEx = "RegEx";
        public const string Existence = "Existence";
    }
}
