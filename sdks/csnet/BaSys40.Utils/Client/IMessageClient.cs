using System;
using System.Collections.Generic;
using System.Text;
using BaSys40.Utils.ResultHandling;

namespace BaSys40.Utils.Client
{
    public interface IMessageClient
    {
        bool IsConnected { get; }

        IResult Publish(string topic, string message, Action<IMessagePublishedEventArgs> messagePublishedHandler, byte qosLevel);
        IResult Subscribe(string topic, Action<IMessageReceivedEventArgs> messageReceivedHandler, byte qosLevel);
        IResult Unsubscribe(string topic);

        IResult Start();
        IResult Stop();
    }
    public interface IMessagePublishedEventArgs
    {
        bool IsPublished { get; }
        string MessageId { get; }        
    }
    public interface IMessageReceivedEventArgs
    {
        string Message { get; }
        string Topic { get; }
        byte QosLevel { get; }
    }

}
