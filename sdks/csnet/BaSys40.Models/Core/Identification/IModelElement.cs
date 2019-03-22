using System.Runtime.Serialization;

namespace BaSys40.Models.Core.Identification
{
    public interface IModelElement
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "modelType")]
        ModelType ModelType { get; }
    }
}
