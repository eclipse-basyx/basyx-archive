using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.AssetAdministrationShell.Generics.DataElementSubtypes;
using BaSys40.Models.Core.Extensions.References;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.AssetAdministrationShell.Implementations.DataElementSubtypes
{
    [DataContract]
    public class Property : DataElement, IProperty
    {
        public override ModelType ModelType => ModelType.Property;
        public override object Value { get => base.Value; set => base.Value = value; }
        public IReference ValueId { get; set; }

        public Property(DataType valueType) : this(valueType, null, null)  { }

        public Property(DataType valueType, IConceptDescription conceptDescription, params IEmbeddedDataSpecification[] embeddedDataSpecifications) : this(valueType, null, conceptDescription, embeddedDataSpecifications)
        { }

        [JsonConstructor]
        public Property(DataType valueType, object value, IConceptDescription conceptDescription, params IEmbeddedDataSpecification[] embeddedDataSpecifications) : base(conceptDescription, embeddedDataSpecifications)
        {
            ValueType = valueType;
            Value = value;
        }
    }

    [DataContract]
    public class Property<TInnerType> : DataElement<TInnerType>, IProperty<TInnerType>
    {
        public override ModelType ModelType => ModelType.Property;
        public override TInnerType Value { get => base.Value; set => base.Value = value; }
        public override DataType ValueType => DataType.GetDataTypeFromSystemTypes(typeof(TInnerType));
        public IReference ValueId { get; set; }

        public Property() : this(null)  { }

        [JsonConstructor]
        public Property(IConceptDescription conceptDescription, params IEmbeddedDataSpecification[] embeddedDataSpecifications)
            : base(conceptDescription, embeddedDataSpecifications) { }
    }
}
