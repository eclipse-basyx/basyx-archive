namespace BaSys40.Utils.ResultHandling
{
    public interface IMessage
    {
        MessageType MessageType { get; set; }
        string Code { get; set; }
        string Text { get; set; }
        string ToString();
    }
}