using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Generics
{
    public interface IOperation : ISubmodelElement
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "in")]
        List<IOperationVariable> In { get; set; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "out")]
        List<IOperationVariable> Out { get; set; }
    }

}
