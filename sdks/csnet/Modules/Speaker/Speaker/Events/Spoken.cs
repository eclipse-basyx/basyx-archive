using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using System;
using System.Collections.Generic;
using BaSys40.Models.Core.Identification;

namespace Speaker.Events
{
    public class Spoken : IEventDescription, IPublishableEvent
    {
        public string TimeStamp => DateTime.Now.ToString();

        public string Message { get; set; }

        public string Originator => "Microsoft_SAM";

        public string EventName => "Spoken";

        public string EventCategory => "Speaker.Events";

        public EntityType? EntityType => BaSys40.Models.Core.AssetAdministrationShell.Generics.EntityType.Primitive;

        public SchemaType? SchemaType => BaSys40.Models.Core.AssetAdministrationShell.Generics.SchemaType.None;

        public string Schema => null;

        public string Description => "The last spoken words";

        public string DisplayName => "Spoken";
        public IIdentifiable Parent { get; set; }
        public Dictionary<string, string> MetaData => null;

        private Identifier Identifier;
        public Identifier EventDescriptionReference => Identifier;
        public Identifier Identification
        {
            get
            {
                if (Identifier == null)
                    Identifier = new Identifier(Guid.NewGuid().ToString(), Identificator.Internal);
                return Identifier;
            }
        }
    }
}
