/*******************************************************************************
* Copyright (c) 2020 Robert Bosch GmbH
* Author: Constantin Ziesche (constantin.ziesche@bosch.com)
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License 2.0 which is available at
* http://www.eclipse.org/legal/epl-2.0
*
* SPDX-License-Identifier: EPL-2.0
*******************************************************************************/
using BaSyx.Models.Core.AssetAdministrationShell.Generics.SubmodelElementTypes;
using BaSyx.Models.Core.AssetAdministrationShell.References;
using BaSyx.Models.Core.Common;
using Newtonsoft.Json;
using System;
using System.Runtime.Serialization;

namespace BaSyx.Models.Core.AssetAdministrationShell.Implementations.SubmodelElementTypes
{
    [DataContract]
    public class Property : SubmodelElement, IProperty
    {
        public override ModelType ModelType => ModelType.Property;
        
        internal object _value;
        public virtual object Value
        {
            get => _value;
            set
            {
                ValueChanged?.Invoke(this, new ValueChangedArgs(this.IdShort, value, ValueType));
                _value = value;
            }
        }
        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "valueType")]
        public virtual DataType ValueType { get; set; }
        public IReference ValueId { get; set; }

        public event EventHandler<ValueChangedArgs> ValueChanged;

        [IgnoreDataMember]
        public virtual GetPropertyValueHandler Get { get; set; }
        [IgnoreDataMember]
        public virtual SetPropertyValueHandler Set { get; set; }

        public Property() : this(null, null)
        { }

        public Property(DataType valueType) : this(valueType, null)
        { }

        [JsonConstructor]
        public Property(DataType valueType, object value) : base()
        {
            ValueType = valueType;
            Value = value;
        }

        public T ToObject<T>()
        {
            return new ElementValue(Value, ValueType).ToObject<T>();
        }
    }

    [DataContract]
    public class Property<TInnerType> : Property, IProperty<TInnerType>
    {
        public override ModelType ModelType => ModelType.Property;

        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "value")]
        public virtual new TInnerType Value
        {
            get
            {
                if (base.Value != null)
                    return (TInnerType)base.Value;
                else
                    return default;
            }
            set => base.Value = value;
        }
        public override DataType ValueType => DataType.GetDataTypeFromSystemType(typeof(TInnerType));

        [IgnoreDataMember]
        public new GetPropertyValueHandler<TInnerType> Get
        {
            get
            {
                if (base.Get != null)
                    return new GetPropertyValueHandler<TInnerType>(de => { return base.Get.Invoke(de).ToObject<TInnerType>(); });
                else
                    return null;
            }
            set => base.Get = new GetPropertyValueHandler(de => new PropertyValue<TInnerType>(value.Invoke(de)));
        }
        [IgnoreDataMember]
        public new SetPropertyValueHandler<TInnerType> Set
        {
            get
            {
                if (base.Set != null)
                    return new SetPropertyValueHandler<TInnerType>((de, val) => { base.Set.Invoke(de, new PropertyValue<TInnerType>(val)); });
                else
                    return null;
            }
            set => base.Set = new SetPropertyValueHandler((de, val) => value.Invoke(de, val.ToObject<TInnerType>()));
        }

        public Property() : base(DataType.GetDataTypeFromSystemType(typeof(TInnerType))) { }

    }
}
