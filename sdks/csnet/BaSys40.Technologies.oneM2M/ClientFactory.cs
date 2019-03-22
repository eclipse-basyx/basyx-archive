using oneM2MClient.Client;
using oneM2MClient.Client.Bindings;
using System;

namespace oneM2MClient
{
    public static class ClientFactory
    {
        public enum Protocol
        {
            Http,
            Mqtt
        }
        public static IClient CreateClient(Protocol protocol, string clientId = null, BindingConfig config = null)
        {
            switch (protocol)
            {
                case Protocol.Http:
                    return new HttpBinding();
                case Protocol.Mqtt:
                    return new MqttBinding(clientId, config);
                default:
                    return null;
            }
        }

        public static IClient CreateClient(string protocol, string clientId = null, BindingConfig config = null)
        {
            if (!string.IsNullOrEmpty(protocol))
            {
                switch (protocol.ToLower())
                {
                    case "http": return CreateClient(Protocol.Http, clientId, config);
                    case "mqtt": return CreateClient(Protocol.Mqtt, clientId, config);
                    default:
                        throw new InvalidOperationException("Protocol is not recognized: '" + protocol + "'");
                }
            }
            else
                throw new ArgumentNullException("protocol");
        }
    }
}
