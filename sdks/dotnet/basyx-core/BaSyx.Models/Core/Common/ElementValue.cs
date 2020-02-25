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
using BaSyx.Models.Core.Common;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Runtime.Serialization;

namespace BaSyx.Models.Core.AssetAdministrationShell.Implementations
{
    [DataContract]
    public class ElementValue : IValue
    {
        internal object _value;
        [JsonProperty(DefaultValueHandling = DefaultValueHandling.Include, NullValueHandling = NullValueHandling.Include)]
        public object Value
        {
            get => _value;
            set
            {
                ValueChanged?.Invoke(this, new ValueChangedArgs(null, value, ValueType));
                _value = value;
            }
        }
        public DataType ValueType { get; protected set; }

        public event EventHandler<ValueChangedArgs> ValueChanged;

        [JsonConstructor]
        public ElementValue(object value, DataType valueType)
        {
            Value = value;
            ValueType = valueType;
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
                        throw new InvalidCastException("Cannot convert " + DataType.GetDataTypeFromSystemType(typeof(T)).DataObjectType + " to " + ValueType.DataObjectType + "- Exception: " + e.Message);
                    }
                }
            }
        }
    }
    [DataContract]
    public class PropertyValue<TValue> : ElementValue, IValue<TValue>
    {
        [JsonProperty(DefaultValueHandling = DefaultValueHandling.Include)]
        public new TValue Value { get; set; }

        public PropertyValue() : this(default, DataType.GetDataTypeFromSystemType(typeof(TValue)))
        { }

        public PropertyValue(TValue value) : this(value, DataType.GetDataTypeFromSystemType(typeof(TValue)))
        { }

        public PropertyValue(TValue value, DataType valueType) : base(value, valueType)
        {
            Value = value;
            ValueType = valueType;
        }

        public static implicit operator PropertyValue<TValue>(TValue value)
        {
            return new PropertyValue<TValue>(value);
        }
    }
}
