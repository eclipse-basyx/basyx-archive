using Newtonsoft.Json;
using System;
using System.Collections.Generic;

namespace oneM2MClient.Utils.ResultHandling
{
    public interface IResult
    {
        Type EntityType { get; }
        object EntityObject { get; }
        bool Success { get; }
        List<IMessage> Messages { get; }
        T GetEntity<T>();
    }

    public interface IResult<TEntity> : IResult
    {
        [JsonConverter(typeof(CustomTypeSerializer))]
        TEntity Entity { get; }
    }
}
