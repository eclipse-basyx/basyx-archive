using BaSys40.Utils.Settings;
using System;
using System.Xml.Serialization;

namespace BaSys40.RI.AAS.SmartControl
{
    public class SmartControlSettings : Settings<SmartControlSettings>
    {
        public oneM2MConfiguration oneM2MConfig { get; set; } = new oneM2MConfiguration();

        public string CallbackEndpointUrl { get; set; }

        [Serializable]
        public class oneM2MConfiguration
        {
            [XmlElement]
            public string ProtocolBinding { get; set; }
            [XmlElement]
            public string ClientId { get; set; }
            [XmlElement]
            public string CSEName { get; set; }
            [XmlElement]
            public string Endpoint { get; set; }
        }

    }
}
