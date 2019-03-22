using BaSys40.Models.Core.AssetAdministrationShell.Enums;
using BaSys40.Models.Core.AssetAdministrationShell.Implementations.DataElementSubtypes;
using BaSys40.Models.Core.Extensions.References;
using BaSys40.Models.Semantics;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core
{

    [DataContract]
    public class DataType : IHasSemantics, IEquatable<DataType>
    {
        [IgnoreDataMember]
        public bool? IsCollection { get; internal set; }

        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "dataObjectType")]
        public DataObjectType DataObjectType { get; internal set; }

        [IgnoreDataMember]
        public Type SystemType { get; internal set; }

        public IReference SemanticId { get; internal set; }

        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "schemaType")]
        public SchemaType? SchemaType { get; internal set; }

        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "schema")]
        public string Schema { get; internal set; }


        //[DataMember(EmitDefaultValue = false, IsRequired = false, Name = "valueRank")]
        //public ValueRank? ValueRank { get; }

        internal DataType() { }

        public DataType(DataObjectType dataObjectType) : this(dataObjectType, false, null)
        { }

        [JsonConstructor]
        public DataType(DataObjectType dataObjectType, bool? isCollection, IReference semanticId = null)
        {
            DataObjectType = dataObjectType;
            SemanticId = semanticId;

            SystemType = GetSystemTypeFromDataType(dataObjectType);

            
            if (isCollection.HasValue)
                IsCollection = isCollection;
            else
                IsCollection = false;
            
        }
        public DataType(IReference semanticId, SchemaType schemaType, string schema)
            :this(DataObjectType.AnyType, false, semanticId)
        {
            SchemaType = schemaType;
            Schema = schema;
        }

        
        /*
        public enum ValueRank : int
        {
            Scalar = -1,
            OneDimensional = 1,
            TwoDimensional = 2,
            ScalarOrOneDimensional = 3
        }
        */
        public static DataType GetDataTypeFromSystemTypes(Type type)
        {
            DataType dataType = new DataType();
            Type innerType = null;

            if (IsGenericList(type))
            {
                dataType.IsCollection = true;
                innerType = type.GetGenericArguments()[0];
            }
            else if (IsArray(type))
            {
                dataType.IsCollection = true;
                innerType = type.GetElementType();
            }
            else
            {
                dataType.IsCollection = false;
                innerType = type;
            }
            dataType.SystemType = innerType;

            switch (innerType.FullName)
            {
                case "System.Decimal": dataType.DataObjectType = DataObjectType.Decimal; break;
                case "System.String": dataType.DataObjectType = DataObjectType.String; break;
                case "System.SByte": dataType.DataObjectType = DataObjectType.Int8; break;
                case "System.Int16": dataType.DataObjectType = DataObjectType.Int16; break;
                case "System.Int32": dataType.DataObjectType = DataObjectType.Int32; break;
                case "System.Int64": dataType.DataObjectType = DataObjectType.Int64; break;
                case "System.Byte": dataType.DataObjectType = DataObjectType.UInt8; break;
                case "System.UInt16": dataType.DataObjectType = DataObjectType.UInt16; break;
                case "System.UInt32": dataType.DataObjectType = DataObjectType.UInt32; break;
                case "System.UInt64": dataType.DataObjectType = DataObjectType.UInt64; break;
                case "System.Boolean": dataType.DataObjectType = DataObjectType.Bool; break;
                case "System.Single": dataType.DataObjectType = DataObjectType.Float; break;
                case "System.Double": dataType.DataObjectType = DataObjectType.Double; break;
                case "System.DateTime": dataType.DataObjectType = DataObjectType.DateTime; break;
                case "System.Uri": dataType.DataObjectType = DataObjectType.AnyURI; break;
                default:
                    if (!IsSimpleType(innerType))
                    {
                        dataType.DataObjectType = DataObjectType.AnyType;
                        return dataType;
                    }
                    else
                        return null;
            }
            return dataType;
        }

        public static bool IsGenericList(Type type)
        {
            return (type.IsGenericType && (
				type.GetGenericTypeDefinition() == typeof(List<>) || 
				type.GetGenericTypeDefinition() == typeof(IEnumerable<>)));
        }

        public static bool IsArray(Type type)
        {
            return type.IsArray;
        }

        public static Type GetSystemTypeFromDataType(DataObjectType dataObjectType)
        {
            if (dataObjectType == DataObjectType.String)
                return typeof(string);
            if (dataObjectType == DataObjectType.LangString)
                return typeof(string);
            else if (dataObjectType == DataObjectType.Bool)
                return typeof(bool);
            else if (dataObjectType == DataObjectType.Float)
                return typeof(float);
            else if (dataObjectType == DataObjectType.Double)
                return typeof(double);
            else if (dataObjectType == DataObjectType.UInt8)
                return typeof(byte);
            else if (dataObjectType == DataObjectType.UInt16)
                return typeof(UInt16);
            else if (dataObjectType == DataObjectType.UInt32)
                return typeof(UInt32);
            else if (dataObjectType == DataObjectType.UInt64)
                return typeof(UInt64);
            else if (dataObjectType == DataObjectType.Int8)
                return typeof(sbyte);
            else if (dataObjectType == DataObjectType.Int16)
                return typeof(Int16);
            else if (dataObjectType == DataObjectType.Int32)
                return typeof(Int32);
            else if (dataObjectType == DataObjectType.Int64)
                return typeof(Int64);
            else if (dataObjectType == DataObjectType.Integer)
                return typeof(decimal);
            else if (dataObjectType == DataObjectType.NegativeInteger)
                return typeof(decimal);
            else if (dataObjectType == DataObjectType.PositiveInteger)
                return typeof(decimal);
            else if (dataObjectType == DataObjectType.NonNegativeInteger)
                return typeof(decimal);
            else if (dataObjectType == DataObjectType.NonPositiveInteger)
                return typeof(decimal);
            else if (dataObjectType == DataObjectType.AnyType)
                return typeof(object);
            else if (dataObjectType == DataObjectType.AnySimpleType)
                return typeof(string);
            else if (dataObjectType == DataObjectType.DateTime)
                return typeof(DateTime);
            else if (dataObjectType == DataObjectType.DateTimeStamp)
                return typeof(DateTime);
            else if (dataObjectType == DataObjectType.AnyURI)
                return typeof(Uri);
            else if (dataObjectType == DataObjectType.Base64Binary)
                return typeof(byte[]);
            else if (dataObjectType == DataObjectType.HexBinary)
                return typeof(byte[]);
            else if (dataObjectType == DataObjectType.Duration)
                return typeof(TimeSpan);
            else if (dataObjectType == DataObjectType.DayTimeDuration)
                return typeof(TimeSpan);
            else if (dataObjectType == DataObjectType.YearMonthDuration)
                return typeof(TimeSpan);
            else if (dataObjectType == ModelType.Property)
                return typeof(Property);
            else if (dataObjectType == ModelType.Blob)
                return typeof(Blob);
            else if (dataObjectType == ModelType.File)
                return typeof(File);
            else if (dataObjectType == ModelType.ReferenceElement)
                return typeof(ReferenceElement);
            else if (dataObjectType == ModelType.DataElementCollection)
                return typeof(DataElementCollection);
            else
                return null;
        }

        public static Type GetSystemTypeFromDataType(DataType dataType)
        {
            return GetSystemTypeFromDataType(dataType.DataObjectType);
        }
        
        public static bool IsSimpleType(Type type)
        {
            TypeInfo typeInfo = type.GetTypeInfo();
            if (typeInfo.IsGenericType && typeInfo.GetGenericTypeDefinition() == typeof(Nullable<>))
                return IsSimpleType(typeInfo.GetGenericArguments()[0]);

            return typeInfo.IsEnum || typeInfo.IsPrimitive || type.Equals(typeof(string)) || type.Equals(typeof(decimal));
        }
        #region IEquatable Interface Implementation
        public bool Equals(DataType other)
        {
            if (ReferenceEquals(null, other))
            {
                return false;
            }
            if (ReferenceEquals(this, other))
            {
                return true;
            }

            return this.DataObjectType.Equals(other.DataObjectType)
                   && this.IsCollection.Equals(other.IsCollection);
        }
        public override bool Equals(object obj)
        {
            if (ReferenceEquals(null, obj))
            {
                return false;
            }
            if (ReferenceEquals(this, obj))
            {
                return true;
            }

            return obj.GetType() == GetType() && Equals((DataType)obj);
        }


        public override int GetHashCode()
        {
            unchecked
            {
                var result = 0;
                result = (result * 397) ^ DataObjectType.GetHashCode();
                result = (result * 397) ^ (IsCollection.Value ? 1 : 0);
                return result;
            }
        }

        public static bool operator ==(DataType x, DataType y)
        {

            if (ReferenceEquals(x, y))
            {
                return true;
            }

            if (ReferenceEquals(x, null))
            {
                return false;
            }
            if (ReferenceEquals(y, null))
            {
                return false;
            }

            return x.DataObjectType == y.DataObjectType && x.IsCollection == y.IsCollection;
        }
        public static bool operator !=(DataType x, DataType y)
        {
            return !(x == y);
        }
        #endregion

    }


}
