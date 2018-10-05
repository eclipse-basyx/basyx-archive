using System.Threading;

namespace oneM2MClient.Utils.ResultHandling
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
            return string.Format(Thread.CurrentThread.CurrentCulture,"{0} | {1} - {2}", MessageType, Code, Text);
        }
    }
}