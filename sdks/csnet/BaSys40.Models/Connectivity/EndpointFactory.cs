using System;

namespace BaSys40.Models.Connectivity
{
    public static partial class EndpointFactory
    {
        public static IEndpoint CreateEndpoint(string endpointType, string address, IEndpointSecurity security)
        {
            switch (endpointType.ToLower())
            {
                case EndpointType.HTTP: return new HttpEndpoint(address);
                case EndpointType.MQTT:
                    {
                        Uri uri = new Uri(address);
                        var brokerUri = uri.AbsoluteUri;
                        var topic = uri.AbsolutePath;

                        return new MqttEndpoint(brokerUri, topic);
                    }
                case EndpointType.OPC_UA: return new OpcUaEndpoint(address);
                default:
                    return null;
            }
        }
    }
}
