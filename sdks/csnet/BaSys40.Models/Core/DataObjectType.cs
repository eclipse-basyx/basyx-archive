using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core
{
    [DataContract]
    public class DataObjectType : IEquatable<DataObjectType>
    {
        public static readonly DataObjectType None = new DataObjectType("none");

        public static readonly DataObjectType AnyType = new DataObjectType("anyType");
        public static readonly DataObjectType AnySimpleType = new DataObjectType("anySimpleType");

        public static readonly DataObjectType UInt8 = new DataObjectType("unsignedByte");
        public static readonly DataObjectType UInt16 = new DataObjectType("unsignedShort");
        public static readonly DataObjectType UInt32 = new DataObjectType("unsignedInt");
        public static readonly DataObjectType UInt64 = new DataObjectType("unsignedLong");

        public static readonly DataObjectType Int8 = new DataObjectType("byte");
        public static readonly DataObjectType Int16 = new DataObjectType("short");
        public static readonly DataObjectType Int32 = new DataObjectType("int");
        public static readonly DataObjectType Int64 = new DataObjectType("long");

        public static readonly DataObjectType String = new DataObjectType("string");
        public static readonly DataObjectType LangString = new DataObjectType("langString");

        public static readonly DataObjectType Integer = new DataObjectType("integer");
        public static readonly DataObjectType NonPositiveInteger = new DataObjectType("nonPositiveInteger");
        public static readonly DataObjectType NonNegativeInteger = new DataObjectType("nonNegativeInteger");
        public static readonly DataObjectType NegativeInteger = new DataObjectType("negativeInteger");
        public static readonly DataObjectType PositiveInteger = new DataObjectType("positiveInteger");

        public static readonly DataObjectType Decimal = new DataObjectType("decimal");
        public static readonly DataObjectType Double = new DataObjectType("double");
        public static readonly DataObjectType Float = new DataObjectType("float");
        public static readonly DataObjectType Bool = new DataObjectType("boolean");

        public static readonly DataObjectType Duration = new DataObjectType("duration");
        public static readonly DataObjectType DayTimeDuration = new DataObjectType("dayTimeDuration");
        public static readonly DataObjectType YearMonthDuration = new DataObjectType("yearMonthDuration");

        public static readonly DataObjectType DateTime = new DataObjectType("dateTime");
        public static readonly DataObjectType DateTimeStamp = new DataObjectType("dateTimeStamp");

        public static readonly DataObjectType AnyURI = new DataObjectType("anyURI");
        public static readonly DataObjectType Base64Binary = new DataObjectType("base64binary");
        public static readonly DataObjectType HexBinary = new DataObjectType("hexBinary");

        private static Dictionary<string, DataObjectType> DataObjectTypes;
        static DataObjectType()
        {
            var fields = typeof(DataObjectType).GetFields(BindingFlags.Public | BindingFlags.Static);
            DataObjectTypes = fields.ToDictionary(k => ((DataObjectType)k.GetValue(null)).Name, v => ((DataObjectType)v.GetValue(null)));
        }

        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "name")]
        public string Name { get; }

       
        [JsonConstructor]
        protected DataObjectType(string name)
        {
            Name = name ?? throw new ArgumentNullException("name");
        }

        public static bool TryParse(string s, out DataObjectType dataObjectType)
        {
            if (DataObjectTypes.TryGetValue(s, out dataObjectType))
                return true;
            else
                return false;
        }

        public override string ToString()
        {
            return Name;
        }

        public bool Equals(DataObjectType other)
        {
            if (ReferenceEquals(null, other))
            {
                return false;
            }
            if (ReferenceEquals(this, other))
            {
                return true;
            }

            return this.Name.Equals(other.Name);
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

            return obj.GetType() == GetType() && Equals((DataObjectType)obj);
        }


        public override int GetHashCode()
        {
            unchecked
            {
                var result = 0;
                result = (result * 397) ^ Name.GetHashCode();
                return result;
            }
        }

        public static bool operator ==(DataObjectType x, DataObjectType y)
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

            return x.Name == y.Name;
        }
        public static bool operator !=(DataObjectType x, DataObjectType y)
        {
            return !(x == y);
        }
    }

    
}
