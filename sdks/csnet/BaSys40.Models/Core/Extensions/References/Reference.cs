using BaSys40.Models.Core.Identification;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.Extensions.References
{
    [DataContract]
    public class Reference : IReference
    {

        [IgnoreDataMember]
        public IKey First
        {
            get
            {
                if (Keys?.Count > 0)
                    return Keys.First();
                return null;
            }
        }

        [DataMember(EmitDefaultValue = false, IsRequired = false, Name = "keys")]
        public List<IKey> Keys { get; protected set; }

        [JsonConstructor]
        public Reference(params IKey[] keys)
        {
            if (keys == null)
                throw new ArgumentNullException("keys");


            for (int i = 0; i < keys.Count(); i++)
                keys[i].Index = i;

            if (Keys?.Count > 0)
            {
                foreach (var key in keys)
                    if (!Keys.Contains(key))
                        Keys.Add(key);
            }
            else
                Keys = keys.ToList();
        }
    }

    [DataContract]
    public class Reference<T> : Reference, IReference<T> where T : IReferable
    {
        [JsonConstructor]
        private Reference(params IKey[] keys) : base(keys)
        { }

        public Reference(T element)
        {
            if (element == null)
                throw new ArgumentNullException("element");

            var keys = new List<IKey>();

            if (element is IIdentifiable identifiable)
            {
                keys.Add(new ModelKey(Key.GetReferableElement(identifiable.GetType()), identifiable.Identification.IdType.Value, identifiable.Identification.Id));
            }
            else if (element is IReferable referable)
            {
                if (referable.Parent != null && referable.Parent is IIdentifiable parentIdentifiable)
                    keys.Add(new ModelKey(Key.GetReferableElement(parentIdentifiable.GetType()), parentIdentifiable.Identification.IdType.Value, parentIdentifiable.Identification.Id));

                keys.Add(new ModelKey(Key.GetReferableElement(referable.GetType()), KeyType.IdShort, referable.IdShort));
            }

            for (int i = 0; i < keys.Count(); i++)
                keys[i].Index = i;

            Keys = keys.ToList();
        }
    }
}
