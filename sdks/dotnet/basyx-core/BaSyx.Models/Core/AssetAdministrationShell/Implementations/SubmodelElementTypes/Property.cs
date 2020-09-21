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
using BaSyx.Models.Core.AssetAdministrationShell.Generics;
using BaSyx.Models.Core.AssetAdministrationShell.References;
using BaSyx.Models.Core.Common;
using Newtonsoft.Json;
using System;
using System.Runtime.Serialization;

namespace BaSyx.Models.Core.AssetAdministrationShell.Implementations
{
    ///<inheritdoc cref="IProperty"/>
    [DataContract]
    public class Property : SubmodelElement, IProperty
    {
        public override ModelType ModelType => ModelType.Property;

        private object _value;
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

        public Property(string idShort) : this(idShort, null, null)
        { }

        public Property(string idShort, DataType valueType) : this(idShort, valueType, null)
        { }

        [JsonConstructor]
        public Property(string idShort, DataType valueType, object value) : base(idShort)
        {
            ValueType = valueType;
            Value = value;

            Get = element  => { return new ElementValue(Value, ValueType); };
            Set = (element, val) => { Value = val.Value; };
        }

        public T ToObject<T>()
        {
            return new ElementValue(Value, ValueType).ToObject<T>();
        }

        public object ToObject(Type type)
        {
            return new ElementValue(Value, ValueType).ToObject(type);
        }
    }
    ///<inheritdoc cref="IProperty"/>
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
        public override DataType ValueType => typeof(TInnerType);

        [IgnoreDataMember]
        public new GetValueHandler<TInnerType> Get
        {
            get
            {
                if (base.Get != null)
                    return new GetValueHandler<TInnerType>(element => { return base.Get.Invoke(element).ToObject<TInnerType>(); });
                else
                    return null;
            }
            set => base.Get = new GetValueHandler(de => new ElementValue<TInnerType>(value.Invoke(de)));
        }
        [IgnoreDataMember]
        public new SetValueHandler<TInnerType> Set
        {
            get
            {
                if (base.Set != null)
                    return new SetValueHandler<TInnerType>((element, val) => { base.Set.Invoke(element, new ElementValue<TInnerType>(val)); });
                else
                    return null;
            }
            set => base.Set = new SetValueHandler((element, val) => value.Invoke(element, val.ToObject<TInnerType>()));
        }

        public Property(string idShort) : base(idShort, typeof(TInnerType)) { }

        [JsonConstructor]
        public Property(string idShort, TInnerType value) : base(idShort, typeof(TInnerType), value) { }

    }
}
