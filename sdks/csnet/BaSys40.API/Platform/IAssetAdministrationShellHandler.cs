using BaSys40.API.AssetAdministrationShell;

namespace BaSys40.API.Platform
{
    public interface IAssetAdministrationShellHandler
    {
        void RegisterSetPropertyValueHandler(IConnectableSubmodel connectableSubmodel, IConnectableDataElement connectableProperty, SetDataElementValueEventHandler setPropertyValueHandler);
        void RegisterGetPropertyValueHandler(IConnectableSubmodel connectableSubmodel, IConnectableDataElement connectableProperty, GetDataElementValueEventHandler getPropertyValueHandler);
        void RegisterMethodCalledEventHandler(IConnectableSubmodel connectableSubmodel, IConnectableOperation connectableOperation, MethodCalledEventHandler handler);
        void RegisterEventHandler(IConnectableSubmodel connectableSubmodel, IConnectableEvent connectableEvent, EventHandler handler);
    }
}
