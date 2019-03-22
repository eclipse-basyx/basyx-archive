using BaSys40.API.ServiceProvider;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;

namespace BaSys40.API.AssetAdministrationShell
{
    public interface IConnectableEvent 
    {
        IAssetAdministrationShell AssetAdministrationShell { get; }
        ISubmodel Submodel { get; }
        IEvent Event { get; }

        event EventHandler EventHandler;

        void Invoke(IPublishableEvent publishableEvent);

        void Publish(IPublishableEvent publishableEvent, byte qosLevel);

        void Subscribe(string subscriberId, string subscriberEndpoint, EventHandler eventHandler, byte qosLevel);

        void Unsubscribe(string subscriberId);

        bool Validate(IPublishableEvent eventToValidate);

        string ToString();
    }
}
