using BaSys40.Models.Core.Identification;
using Newtonsoft.Json;

namespace BaSys40.Models.Core.Constraints
{
    [JsonConverter(typeof(ConstraintConverter))]
    public interface IConstraint : IModelElement
    { } 

    public abstract class Constraint : IConstraint
    {
        public abstract ModelType ModelType { get; }
    }
}