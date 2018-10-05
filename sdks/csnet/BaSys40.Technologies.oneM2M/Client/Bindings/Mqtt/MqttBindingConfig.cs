using System;
using Newtonsoft.Json.Linq;
using System.Xml.Serialization;
using oneM2MClient.Utils;

namespace oneM2MClient.Client.Bindings
{
    [Serializable]
    public class MqttBindingConfig : BindingConfig
    {
        [XmlElement]
        public string MqttProviderInstanceName { get; private set; }
        [XmlElement]
        public string BrokerIPAddress { get; private set; }
        [XmlElement]
        public int BrokerPort { get; private set; }
        [XmlElement]
        public string SecurityLevel { get; private set; }
        [XmlElement]
        public bool SecureConnection { get; private set; }
        [XmlElement]
        public string UserName { get; private set; }
        [XmlElement]
        public string Password { get; private set; }
     

        public const string DefaultSecurityLevel = "l0";
        public const string DefaultBrokerInstanceName = "MqttProviderDefault";
        public const string DefaultBrokerIPAddress = "127.0.0.1";
        public const int DefaultBrokerPort = 1883;
        public new const string DefaultScheme = "mqtt";

        public MqttBindingConfig(Uri brokerUri, string brokerInstanceName = DefaultBrokerInstanceName, string securityLevel = DefaultSecurityLevel) 
        {
            if(brokerUri != null)
            {
                BrokerIPAddress = brokerUri.Host;
                BrokerPort = brokerUri.Port;
                if (!string.IsNullOrEmpty(brokerUri.Scheme))
                {
                    SecureConnection = brokerUri.Scheme.Contains("mqtts") ? true : false;
                    Scheme = brokerUri.Scheme;
                }
                if (!string.IsNullOrEmpty(brokerUri.UserInfo) && brokerUri.UserInfo.Contains(":"))
                {
                    string[] account = brokerUri.UserInfo.Split(':');
                    UserName = account[0];
                    Password = account[1];
                }

                MqttProviderInstanceName = brokerInstanceName;
                SecurityLevel = securityLevel;
                ConfigurationPath = "restconf/config/onem2m-protocol-mqtt:onem2m-protocol-mqtt-providers/" + MqttProviderInstanceName + "/mqtt-client-config";
            }
            else
                throw new Exception("Could not load MqttConfig");
        }

        public MqttBindingConfig(string brokerInstanceName = DefaultBrokerInstanceName)
            : this(Helper.ConcatUrl(DefaultScheme, DefaultBrokerIPAddress, DefaultBrokerPort, null), brokerInstanceName)
        { }

        public override string GenerateConfigurationPayload()
        {
            JObject config = new JObject();

            JObject clientConfig = new JObject();
            clientConfig.Add("onem2m-protocol-mqtt:mqtt-broker-port", BrokerPort);
            clientConfig.Add("onem2m-protocol-mqtt:mqtt-broker-ip", BrokerIPAddress);
            clientConfig.Add("onem2m-protocol-mqtt:security-level", SecurityLevel);

            config.Add("onem2m-protocol-mqtt:mqtt-client-config", clientConfig);

            return config.ToString();
        }
    }
}
