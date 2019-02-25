using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.Extensions.References;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Implementations
{
    [DataContract, JsonConverter(typeof(DataElementValueConverter))]
    public class DataElement : SubmodelElement, IDataElement
    {
        public virtual object Value { get; set; }
        public virtual DataType ValueType { get; set; }
        public override ModelType ModelType => ModelType.DataElement;

        public DataElement(IConceptDescription conceptDescription, params IEmbeddedDataSpecification[] embeddedDataSpecifications)
            : base(conceptDescription, embeddedDataSpecifications)
        { }

        public T ToObject<T>()
        {
            if (DataType.GetDataTypeFromSystemTypes(typeof(T)) == ValueType)
                return (T)Value;
            else
                throw new InvalidCastException("Cannot convert " + DataType.GetDataTypeFromSystemTypes(typeof(T)).DataObjectType + " to " + ValueType.DataObjectType);
        }
    }
    [DataContract]
    public class DataElement<TValue> : DataElement, IDataElement<TValue>
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "value")]
        public virtual new TValue Value
        {
            get
            {
                if (base.Value != null)
                    return (TValue)base.Value;
                else
                    return default(TValue);
            }
            set => base.Value = value;
        }

        public DataElement() : this(null)  { }

        [JsonConstructor]
        public DataElement(IConceptDescription conceptDescription, params IEmbeddedDataSpecification[] embeddedDataSpecifications)
            : base(conceptDescription, embeddedDataSpecifications)
        { }
    }
}
