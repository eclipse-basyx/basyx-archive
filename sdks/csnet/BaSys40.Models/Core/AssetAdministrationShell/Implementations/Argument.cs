using System.Collections.Generic;
using System.Runtime.Serialization;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.Extensions.References;
using BaSys40.Models.Core.Constraints;

namespace BaSys40.Models.Core.AssetAdministrationShell.Implementations
{
    [DataContract]
    public class Argument : IArgument
    {
        public virtual object Value { get; set; }
        public virtual DataType ValueType { get; set; }
        public int? Index { get; set; }
        public string IdShort { get; set; }
        public List<IConstraint> Constraints { get; set; }
        public IReference SemanticId { get; set; }

        public Argument()
        { }

        public Argument(IOperationVariable param, object value)
        {
            this.ValueType = param.DataType;
            this.IdShort = param.IdShort;
            this.Index = param.Index;
            this.Constraints = param.Constraints;
            this.SemanticId = param.SemanticId;
            this.Value = value;
            this.Constraints = param.Constraints;
        }

        public T ToObject<T>()
        {
            return new DataElementValue(Value, ValueType).ToObject<T>();
        }
    }

    [DataContract]
    public class Argument<TInnerType> : Argument, IValue<TInnerType>
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "valueType")]
        public override DataType ValueType => DataType.GetDataTypeFromSystemTypes(typeof(TInnerType));

        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "value")]
        public virtual new TInnerType Value
        {
            get
            {
                if (base.Value != null)
                    return (TInnerType)base.Value;
                else
                    return default(TInnerType);
            }
            set => base.Value = value;
        }
    }
}
