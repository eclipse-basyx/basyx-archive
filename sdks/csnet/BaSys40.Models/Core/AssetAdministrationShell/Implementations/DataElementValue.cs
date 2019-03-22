using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Implementations
{
    [DataContract]
    public class DataElementValue : IValue
    {
        public object Value { get; set; }
        public DataType ValueType { get; protected set; }

        [JsonConstructor]
        public DataElementValue(object value, DataType dataType)
        {
            Value = value;
            ValueType = dataType;
        }

        public T ToObject<T>()
        {
            if (Value is T)
                return (T)Value;
            else
            {
                try
                {
                    Value = Convert.ChangeType(Value, DataType.GetSystemTypeFromDataType(ValueType));
                    return (T)Value;
                }
                catch (Exception e)
                {
                    try
                    {
                        var jVal = JValue.Parse(Value.ToString());
                        var convertedVal = jVal.ToObject<T>();
                        return convertedVal;
                    }
                    catch (Exception)
                    {
                        throw new InvalidCastException("Cannot convert " + DataType.GetDataTypeFromSystemTypes(typeof(T)).DataObjectType + " to " + ValueType.DataObjectType + "- Exception: " + e.Message);
                    }
                }
            }
        }
    }
    [DataContract]
    public class DataElementValue<TValue> : DataElementValue, IValue<TValue>
    {
        public new TValue Value { get; set; }

        public DataElementValue() : this(default(TValue), DataType.GetDataTypeFromSystemTypes(typeof(TValue)))
        { }

        public DataElementValue(TValue value) : this(value, DataType.GetDataTypeFromSystemTypes(typeof(TValue)))
        { }

        public DataElementValue(TValue value, DataType dataType) : base(value, dataType)
        {
            Value = value;
            ValueType = dataType;
        }
    }
}
