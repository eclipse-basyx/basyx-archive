using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;

namespace oneM2MClient.Utils.ResultHandling
{
    public class Result : IResult
    {
        [DataMember(Order = 0)]
        public bool Success { get; internal set; }
        [DataMember(Order = 1)]
        public Type EntityType { get; private set; }
        [DataMember(Order = 2)]
        public object EntityObject { get; private set; }
        
        private List<IMessage> messages;
        [DataMember(Order = 3)]
        public List<IMessage> Messages
        {
            get
            {
                if (this.messages == null)
                    this.messages = new List<IMessage>();
                return this.messages;
            }
        }

        public Result(bool success) : this(success, null, null, null)
        { }

        public Result(bool success, List<IMessage> messages) : this(success, null, null, messages)
        { }

        public Result(bool success, object entity, Type entityType) : this(success, entity, entityType, null)
        { }

        public Result(Exception e) : this(false, e, e.GetType(), new List<IMessage>() { new Message(MessageType.Error, e.Message, e.HResult.ToString()) })
        { }

        public Result(bool success, object entity, Type entityType, List<IMessage> messages)
        {
            Success = success;

            if (messages != null)
                foreach (Message msg in messages)
                    Messages.Add(msg);

            if (entity != null && entityType != null)
            {
                EntityObject = entity;
                EntityType = entityType;
            }

        }

        public T GetEntity<T>()
        {
            if (EntityObject != null && 
                (EntityObject is T ||
                EntityObject.GetType().IsAssignableFrom(typeof(T)) ||
                EntityObject.GetType().GetInterfaces().Contains(typeof(T))))
                return (T)EntityObject;
            return default(T);
        }
    }

    public class Result<TEntity> : Result, IResult<TEntity>
    {
        [IgnoreDataMember]
        public TEntity Entity { get; private set; }

        public Result(bool success, TEntity entity) : this(success, entity, new List<IMessage>())
        { }
        public Result(bool success, IMessage message) : this(success, default(TEntity), new List<IMessage>() { message })
        { }
        public Result(bool success, List<IMessage> messages) : this(success, default(TEntity), messages)
        { }
        public Result(bool success, TEntity entity, IMessage message) : this(success, entity, new List<IMessage>() { message })
        { }
        public Result(Exception e) : base(e)
        { }
        public Result(bool success, TEntity entity, List<IMessage> messages) : base(success, entity, typeof(TEntity), messages)
        {
            Entity = entity;
        }
    }
}
