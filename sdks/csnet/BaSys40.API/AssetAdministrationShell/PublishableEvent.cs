using BaSys40.Models.Core;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.Extensions.References;
using BaSys40.Models.Core.Identification;
using System.Collections.Generic;

namespace BaSys40.API.AssetAdministrationShell
{
    public class PublishableEvent : IPublishableEvent
    {
        public string TimeStamp { get; set; }

        public string Message { get; set; }

        public string Originator { get; set; }

        public Identifier Identification { get; set; }

        public Reference<IEvent> EventDescriptionReference { get; set; }

        public List<Description> Descriptions { get; set; }

        public string DisplayName { get; set; }
        public IReference Parent { get; set; }

        public Dictionary<string, string> MetaData { get; set; }

        public AdministrativeInformation Administration { get; set; }
        public string IdShort { get; set; }

        public string Category { get; set; }

        public ModelType ModelElementType => ModelType.Event;

    }
}
