using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using System.IO;
using System.Xml;
using System.Xml.Schema;
using BaSys40.API.Platform.Agents;
using BaSys40.Models.Core.AssetAdministrationShell.Enums;

namespace BaSys40.API.AssetAdministrationShell.Connectables
{
    public class ConnectedEvent : IConnectableEvent
    {
        public IEvent Event { get; }
        public IAssetAdministrationShell AssetAdministrationShell { get; }
        public ISubmodel Submodel { get; }

        public event EventHandler EventHandler;

        private readonly ISubmodelAgent serviceImpl;

        public ConnectedEvent(ISubmodelAgent service, IAssetAdministrationShell aas, ISubmodel submodel, IEvent eventDescription)
        {
            AssetAdministrationShell = aas;
            Submodel = submodel;
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
            if (!string.IsNullOrEmpty(Event.DataType.Schema))
            {
                if (Event.DataType.SchemaType.HasValue && Event.DataType.SchemaType.Value == SchemaType.XSD)
                {
                    using (var stream = GenerateStreamFromString(Event.DataType.Schema))
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
