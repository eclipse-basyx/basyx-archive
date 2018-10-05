using BaSys40.Utils.JsonHandling;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BaSys40.Utils.ResultHandling
{
    public interface IResult
    {
        [DataMember(EmitDefaultValue = false, IsRequired = false)]
        Type EntityType { get; }
        [DataMember(EmitDefaultValue = false, IsRequired = false)]
        object Entity { get; }
        [DataMember(IsRequired = true)]
        bool Success { get; }
        [DataMember(EmitDefaultValue = false, IsRequired = false)]
        List<IMessage> Messages { get; }

        T GetEntity<T>();
    }

    public interface IResult<TEntity> : IResult
    {
        [JsonConverter(typeof(CustomTypeSerializer))]
        new TEntity Entity { get; }
    }
}
