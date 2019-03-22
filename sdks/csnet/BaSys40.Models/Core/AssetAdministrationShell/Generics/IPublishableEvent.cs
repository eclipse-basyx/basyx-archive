using BaSys40.Models.Core.Identification;
using System.Runtime.Serialization;
using BaSys40.Models.Core.Extensions.References;

namespace BaSys40.Models.Core.AssetAdministrationShell.Generics
{
    public interface IPublishableEvent : IIdentifiable
    {
        [DataMember]
        string Originator { get; }

        [DataMember]
        Reference<IEvent> EventDescriptionReference { get; }

        [DataMember]
        string TimeStamp { get; }

        [DataMember]
        string Message { get; set; }
    }
}
