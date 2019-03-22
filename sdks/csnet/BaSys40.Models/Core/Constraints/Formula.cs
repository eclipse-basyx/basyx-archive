using BaSys40.Models.Core.Extensions.References;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.Constraints
{
    public class Formula : IConstraint, IFormula
    {
        public List<IReference> DependsOn { get; set; }

        [DataMember(EmitDefaultValue = false, IsRequired = true, Name = "modelType")]
        public ModelType ModelType => ModelType.Formula;
    }
}
