using System;
using System.Xml.Serialization;

namespace oneM2MClient.Client.Bindings
{
    public abstract class BindingConfig : oneM2MConfig
    {
        [XmlElement]
        public string ConfigurationPath { get; protected set; }

        public abstract string GenerateConfigurationPayload();

        public BindingConfig()
        { }
        public BindingConfig(Uri cseHostUri, string cseName = DefaultCSEName, oneM2M.CseTypeId cseType = DefaultCSEType)
        {
            CSEName = cseName;
            CSEType = cseType;

            if (cseHostUri != null)
            {
                CSEHost = cseHostUri.Host;
                CSEPort = cseHostUri.Port;
                Scheme = cseHostUri.Scheme;
            }
            else
            {
                CSEHost = DefaultCSEHost;
                CSEPort = DefaultCSEPort;
                Scheme = DefaultScheme;
            }
        }
        
    }
}
