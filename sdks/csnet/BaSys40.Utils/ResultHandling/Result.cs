using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;

namespace BaSys40.Utils.ResultHandling
{
    public class Result : IResult
    {
        public bool Success { get; private set; }

        public object Entity { get; private set; }

        public Type EntityType { get; private set; }

        private List<IMessage> messages;

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

        public Result(Exception e) : 
            this(false, e, e.GetType(), new List<IMessage>()
            {
                new Message(MessageType.Error, e.Message),
                new Message(MessageType.Error, e.InnerException != null ? e.InnerException.Message : "InnerException = null")
            })
        { }

        public Result(bool success, object entity, Type entityType, List<IMessage> messages)
        {
            Success = success;

            if (messages != null)
                foreach (Message msg in messages)
                    Messages.Add(msg);

            if (entity != null && entityType != null)
            {
                Entity = entity;
                EntityType = entityType;
            }

        }

        public T GetEntity<T>()
        {
            if (Entity != null && 
                (Entity is T ||
                Entity.GetType().IsAssignableFrom(typeof(T)) ||
                Entity.GetType().GetInterfaces().Contains(typeof(T))))
                return (T)Entity;
            return default(T);
        }
    }

    public class Result<TEntity> : Result, IResult<TEntity>
    {
        [IgnoreDataMember]
        public new TEntity Entity { get; private set; }

        public Result(bool success) : this(success, default(TEntity), new List<IMessage>())
        { }
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
