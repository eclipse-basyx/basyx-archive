using BaSys40.Models.Core.Extensions.References;
using BaSys40.Models.Core.Identification;
using BaSys40.Models.Semantics;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.Views
{
    public interface IView : IHasSemantics, IReferable, IModelElement
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "containedElements")]
        List<IReference> ContainedElements { get; }
    }
}
