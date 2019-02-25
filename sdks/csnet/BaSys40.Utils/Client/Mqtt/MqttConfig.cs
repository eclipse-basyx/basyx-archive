using BaSys40.Utils.Config;
using BaSys40.Utils.Security;
using System.Security.Cryptography.X509Certificates;

namespace BaSys40.Utils.Client.Mqtt
{
    public class MqttConfig : IEventHandlerConfig
    {
        public string ClientId { get; }

        public string BrokerEndpoint { get; }

        public ICredentials Credentials { get; }

        public bool SecureConnection { get; set; } = false;

        public int PublishTimeout { get; } = 5000;

        public int ReceiveTimeout { get; } = 5000;

        public ISecurity Security { get; }

        public MqttConnectConfig MqttConnectConfig { get; set; }

        public MqttConfig(string clientId, string brokerEndpoint)
        {
            ClientId = clientId;
            BrokerEndpoint = brokerEndpoint;
            MqttConnectConfig = new MqttConnectConfig();
        }
        public MqttConfig(string clientId, string brokerEndpoint, MqttCredentials credentials) : this(clientId, brokerEndpoint)
        {
            Credentials = credentials;
        }

        public MqttConfig(string clientId, string brokerEndpoint, MqttCredentials credentials, MqttSecurity security) : this(clientId, brokerEndpoint, credentials)
        {
            Security = security;
        }
    }

    public class MqttConnectConfig
    {
        public bool WillRetain { get; set; } = false;
        public byte WillQosLevel { get; set; } = 0;
        public bool WillFlag { get; set; } = false;
        public string WillTopic { get; set; } = null;
        public string WillMessage { get; set; } = null;
        public bool CleanSession { get; set; } = true;
        public ushort KeepAlivePeriod { get; set; } = 60;
    }

    public class MqttCredentials : ICredentials
    {
        public string UserName { get; set; }
        public string Password { get; set; }

        internal MqttCredentials()
        { }
        
        public MqttCredentials(string userName, string password)
        {
            UserName = userName;
            Password = password;
        }
    }

    public class MqttSecurity : ISecurity
    {
        public X509Certificate CaCert { get; }
        public X509Certificate ClientCert { get; }

        public MqttSecurity(X509Certificate caCert)
        {
            CaCert = caCert;
        }
        public MqttSecurity(X509Certificate caCert, X509Certificate clientCert)
        {
            CaCert = caCert;
            ClientCert = clientCert;
        }
    }
}
