using BaSys40.Utils.Settings;
using System;
using System.Xml.Serialization;

namespace BaSys40.Registry.Client
{
    public class RegistryClientSettings : Settings<RegistryClientSettings>
    {        
        public RegistryConfiguration RegistryConfig { get; set; } = new RegistryConfiguration();

        public CatalogConfiguration CatalogConfig { get; set; } = new CatalogConfiguration();

        public SecurityConfiguration SecurityConfig { get; set; } = new SecurityConfiguration();


        [Serializable]
        public class RegistryConfiguration 
        {
            [XmlElement]
            public string RegistryUrl { get; set; }
            [XmlElement]
            public string TenantId { get; set; }
            [XmlElement]
            public string BasePath { get; set; }      
        }
        [Serializable]
        public class CatalogConfiguration
        {
            [XmlElement]
            public string CatalogUrl { get; set; }
            [XmlElement]
            public string PrivateToken { get; set; }
            [XmlElement]
            public string NamespaceId { get; set; }
        }
        [Serializable]
        public class SecurityConfiguration
        {
            [XmlElement]
            public string KeyCloakUrl { get; set; }
            [XmlElement]
            public string KeyCloakClientId { get; set; }
            [XmlElement]
            public string KeyCloakClientSecret { get; set; }
        }
    }
}
