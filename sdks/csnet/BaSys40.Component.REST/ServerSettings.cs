using BaSys40.Utils.Settings;
using System;
using System.Collections.Generic;
using System.Xml.Serialization;

namespace BaSys40.Component.REST
{
    public class ServerSettings : Settings<ServerSettings>
    {
        [XmlElement]
        public HostingConfiguration Hosting { get; set; }

    }
    [Serializable]
    public class HostingConfiguration
    {
        [XmlArrayItem("Url")]
        public List<string> Urls { get; set; }
    }
}
