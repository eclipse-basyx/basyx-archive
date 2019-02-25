using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.Identification;
using BaSys40.Models.Core.Views;
using Newtonsoft.Json;
using System;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.Extensions.References
{
    [DataContract]
    public class Key : IKey, IEquatable<Key>
    {
        public KeyElements? Type { get; }
        public KeyType? IdType { get; }
        public string Value { get; }
        public bool? Local { get; }
        public int? Index { get; set; }

        protected Key() { }

        [JsonConstructor]
        public Key(KeyElements type, KeyType idType, string value, bool local)
        {
            Type = type;
            IdType = idType;
            Value = value;
            Local = local;
        }

        public static KeyElements GetReferableElement(Type type)
        {
            if (typeof(IAsset).IsAssignableFrom(type))
                return KeyElements.Asset;
            else if (typeof(IAssetAdministrationShell).IsAssignableFrom(type))
                return KeyElements.AssetAdministrationShell;
            else if (typeof(ISubmodel).IsAssignableFrom(type))
                return KeyElements.Submodel;
            else if (typeof(IView).IsAssignableFrom(type))
                return KeyElements.View;
            else if (typeof(IDataElement).IsAssignableFrom(type))
                return KeyElements.Property;
            else if (typeof(IOperation).IsAssignableFrom(type))
                return KeyElements.Operation;
            else if (typeof(IEvent).IsAssignableFrom(type))
                return KeyElements.Event;
            else if (typeof(IConceptDescription).IsAssignableFrom(type))
                return KeyElements.ConceptDescription;
            else
                throw new InvalidOperationException("Cannot convert type " + type.FullName + "to referable element");
        }

        #region IEquatable
        public bool Equals(Key other)
        {
            if (ReferenceEquals(null, other))
            {
                return false;
            }
            if (ReferenceEquals(this, other))
            {
                return true;
            }

            return this.IdType.Equals(other.IdType)
                && this.Index.Equals(other.Index)
                && this.Local.Equals(other.Local)
                && this.Type.Equals(other.Type)
                && this.Value.Equals(other.Type);
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

            return obj.GetType() == GetType() && Equals((Key)obj);
        }


        public override int GetHashCode()
        {
            unchecked
            {
                var result = 0;
                result = (result * 397) ^ IdType.GetHashCode();
                result = (result * 397) ^ Index.GetHashCode();
                result = (result * 397) ^ Type.GetHashCode();
                result = (result * 397) ^ (Local.Value ? 1 : 0);
                return result;
            }
        }

        public static bool operator ==(Key x, Key y)
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

            return x.IdType == y.IdType
                && x.Index == y.Index
                && x.Local == y.Local
                && x.Type == y.Type
                && x.Value == y.Value;
        }
        public static bool operator !=(Key x, Key y)
        {
            return !(x == y);
        }
        #endregion
    }

    [DataContract]
    public class Key<T> : Key
    {
        public Key(KeyType idType, string value, bool local) : base(GetReferableElement(typeof(T)), idType, value, local)
        { }
    }

}