using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using System.IO;
using System.Xml;
using System.Xml.Schema;
using BaSys40.API.Agents;

namespace BaSys40.API.AssetAdministrationShell.Connectables
{
    public class ConnectedEvent : IConnectableEvent
    {
        public IEventDescription Event { get; }
        private IAssetAdministrationShell AssetAdministrationShell { get; }
        private ISubModel SubModel { get; }

        public event EventHandler EventHandler;

        private readonly ISubModelAgent serviceImpl;

        public ConnectedEvent(ISubModelAgent service, IAssetAdministrationShell aas, ISubModel subModel, IEventDescription eventDescription)
        {
            AssetAdministrationShell = aas;
            SubModel = subModel;
            Event = eventDescription;
            serviceImpl = service;
        }

        public void Invoke(IPublishableEvent publishableEvent)
        {
            EventHandler?.Invoke(this, publishableEvent);
        }

        public void Publish(IPublishableEvent publishableEvent, byte qosLevel)
        {
            EventHandler?.Invoke(this, publishableEvent);
        }

        public void Subscribe(string subscriberId, string subscriberEndpoint, EventHandler eventHandler, byte qosLevel)
        {
            if(EventHandler == null)
                EventHandler += eventHandler;
        }

        public void Unsubscribe(string subscriberId)
        {
            EventHandler = null;
        }

        public bool Validate(IPublishableEvent eventToValidate)
        {
            if (!string.IsNullOrEmpty(Event.Schema))
            {
                if (Event.SchemaType.HasValue && Event.SchemaType.Value == SchemaType.XSD)
                {
                    using (var stream = GenerateStreamFromString(Event.Schema))
                    {
                        XmlSchema schema = XmlSchema.Read(stream, SchemaValidationEventHandler);
                        XmlDocument message = new XmlDocument();
                        message.Schemas.Add(schema);
                        message.LoadXml(eventToValidate.Message);
                        try
                        {
                            message.Validate(SchemaValidationEventHandler);
                            return true;
                        }
                        catch
                        {
                            return false;
                        }
                    }
                }
                    return false;
            }
            else
                return true;
        }

        private void SchemaValidationEventHandler(object sender, ValidationEventArgs e)
        {
            if (e.Severity == XmlSeverityType.Error)
                throw new XmlSchemaValidationException(e.Message);
        }

        public static Stream GenerateStreamFromString(string s)
        {
            var stream = new MemoryStream();
            var writer = new StreamWriter(stream);
            writer.Write(s);
            writer.Flush();
            stream.Position = 0;
            return stream;
        }
    }
}
