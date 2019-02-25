using System.Net;
using System.Threading;

namespace BaSys40.Utils.ResultHandling
{
    public class Message : IMessage
    {
        public MessageType MessageType { get; set; }
        public string Text { get; set; }
        public string Code { get; set; }

        public Message(MessageType messageType, string text) : this(messageType, text, null)
        { }
        public Message(MessageType messageType, string text, string code)
        {
            MessageType = messageType;
            Text = text;
            Code = code;
        }


        public override string ToString()
        {
            if(!string.IsNullOrEmpty(Code))
                return string.Format(Thread.CurrentThread.CurrentCulture, "{0} | {1} - {2}", MessageType, Code, Text);
            else
                return string.Format(Thread.CurrentThread.CurrentCulture, "{0} | {1}", MessageType, Text);
        }
    }

    public class HttpMessage : Message
    {
        public HttpStatusCode HttpStatusCode { get; set; }

        public HttpMessage(MessageType messageType, HttpStatusCode httpStatusCode) : base(messageType, httpStatusCode.ToString(), ((int)httpStatusCode).ToString())
        {
            HttpStatusCode = httpStatusCode;
        }
    }

    public class NotFoundMessage : Message
    {
        public NotFoundMessage() : base(MessageType.Information, "NotFound", "404")
        { }
    }

    public class EmptyMessage : Message
    {
        public EmptyMessage() : base(MessageType.Information, "Empty")
        { }
    }
}