using NLog;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Xml;
using System.Xml.Linq;
using System.Xml.Serialization;
using static BaSys40.Utils.Settings.FileWatcher;

namespace BaSys40.Utils.Settings
{
    [Serializable]
    public abstract class Settings<T> : ISettings where T : ISettings, new()
    {
        private FileWatcher fileWatcher;

        public string FilePath { get; set; }

        public const string SettingsAppendix = ".xml";
        public const string MiscellaneousConfig = "Miscellaneous";

        private static Logger logger = LogManager.GetCurrentClassLogger();
        public static string FileName => typeof(T).Name + SettingsAppendix;

        public Settings()
        {
            ServerConfig = new ServerConfiguration();
            ClientConfig = new ClientConfiguration();
            PathConfig = new PathConfiguration();
            ProxyConfig = new ProxyConfiguration();
            Miscellaneous = new Dictionary<string, string>();
        }

        public virtual void ConfigureSettingsWatcher(string settingsFilePath, FileChanged settingsFileChangedHandler)
        {
            fileWatcher = new FileWatcher(settingsFilePath, settingsFileChangedHandler);
        }
    
        public static T LoadSettings(string filePath)
        {
            if (string.IsNullOrEmpty(filePath) || !File.Exists(filePath))
                return default(T);

            T settings = new T();

            try
            {
                XmlSerializer serializer = new XmlSerializer(typeof(T));
                using (var reader = XmlReader.Create(filePath))
                {
                    if (serializer.CanDeserialize(reader))
                    {
                        settings = (T)serializer.Deserialize(reader);
                    }
                }

                var miscElement = XElement.Load(filePath).Element(MiscellaneousConfig);
                if (miscElement != null)
                {
                    settings.Miscellaneous = miscElement.Elements().Where(e => !e.HasElements).ToDictionary(e => e.Name.LocalName, e => e.Value);
                }

                settings.FilePath = filePath;
                
                return settings;
            }
            catch (Exception e)
            {
                logger.Error(e, "Could not load " + filePath);
                return default(T);
            }
        }
        public enum ServiceType
        {
            None,
            Server,
            Client,
            Both
        }

        [XmlElement]
        public ServiceType OperationMode { get; set; }
        [XmlElement(IsNullable = true)]
        public ServerConfiguration ServerConfig { get; set; }
        [XmlElement(IsNullable = true)]
        public ClientConfiguration ClientConfig { get; set; }
        [XmlElement(IsNullable = true)]
        public PathConfiguration PathConfig { get; set; }
        [XmlElement(IsNullable = true)]
        public ProxyConfiguration ProxyConfig { get; set; }

        [XmlIgnore]
        public Dictionary<string, string> Miscellaneous { get; set; }


        [Serializable]
        public class ServerConfiguration
        {
            [XmlElement]
            public string ServerId { get; set; }
            [XmlElement]
            public string Endpoint { get; set; }
        }

        [Serializable]
        public class ClientConfiguration
        {
            [XmlElement]
            public string ClientId { get; set; }
            [XmlElement]
            public string Endpoint { get; set; }         
        }

        [Serializable]
        public class PathConfiguration
        {
            [XmlElement]
            public string Host { get; set; }
            [XmlElement]
            public string BasePath { get; set; }
            [XmlElement]
            public string ServicePath { get; set; }
            [XmlElement]
            public string AggregatePath { get; set; }
            [XmlElement]
            public string EntityPath { get; set; }
            [XmlElement]
            public string EntityId { get; set; }
        }

        [Serializable]
        public class ProxyConfiguration
        {
            [XmlElement]
            public bool? UseProxy { get; set; }
            [XmlElement]
            public string ProxyAddress { get; set; }
            [XmlElement]
            public string Domain { get; set; }
            [XmlElement]
            public string UserName { get; set; }
            [XmlElement]
            public string Password { get; set; }
        }
    }
}
