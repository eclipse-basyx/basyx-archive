using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Generics
{
    public interface IValue
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "value")]
        object Value { get; set; }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "valueType")]
        DataType ValueType { get; }
        T ToObject<T>();
    }

    public interface IValue<out T> : IValue
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "value")]
        new T Value { get; }
    }
}
