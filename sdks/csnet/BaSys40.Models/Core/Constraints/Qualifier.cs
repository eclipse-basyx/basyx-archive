using BaSys40.Models.Core.Extensions.References;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.Constraints
{
    [DataContract]
    public class Qualifier : IConstraint, IQualifier
    {
        public string QualifierType { get; set; }

        public object QualifierValue { get; set; }

        public IReference SemanticId { get; set; }
        public ModelType ModelType => ModelType.Qualifier;
        public IReference QualifierValueId { get; set; }
    }
}
