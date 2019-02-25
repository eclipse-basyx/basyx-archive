using System;

namespace BaSys40.Models.Connectivity
{
    public class MqttEndpoint : IEndpoint
    {
        public string Address { get; }

        public Uri BrokerUri { get; }

        public string Topic { get; }

        public string Type => EndpointType.MQTT;

        public IEndpointSecurity Security { get; set; }

        public MqttEndpoint(string brokerUri, string topic)
        {
            brokerUri = brokerUri ?? throw new ArgumentNullException("url");
            BrokerUri = new Uri(brokerUri);
            Topic = topic ?? "/";
            Address = brokerUri + topic;
        }
    }
}
