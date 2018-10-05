using BaSys40.Models.Core.AssetAdministrationShell.Generics;
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

        public Identifier EventDescriptionReference { get; set; }

        public string Description { get; set; }

        public string DisplayName { get; set; }
        public IIdentifiable Parent { get; set; }

        public Dictionary<string, string> MetaData { get; set; }
    }
}
