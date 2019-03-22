using BaSys40.Utils.Security;

namespace BaSys40.Utils.Config
{
    public interface IEventHandlerConfig
    {
        string ClientId { get; }
        string BrokerEndpoint { get; }
        int PublishTimeout { get; }
        int ReceiveTimeout { get; }
        ICredentials Credentials { get; }
        ISecurity Security { get; }
    }
}