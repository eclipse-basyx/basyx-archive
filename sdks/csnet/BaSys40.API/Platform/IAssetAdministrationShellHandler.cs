using BaSys40.API.AssetAdministrationShell;

namespace BaSys40.API.Platform
{
    public interface IAssetAdministrationShellHandler
    {
        void RegisterSetPropertyValueHandler(IConnectableSubModel connectableSubModel, IConnectableProperty connectableProperty, SetPropertyValueHandler setPropertyValueHandler);
        void RegisterGetPropertyValueHandler(IConnectableSubModel connectableSubModel, IConnectableProperty connectableProperty, GetPropertyValueHandler getPropertyValueHandler);
        void RegisterMethodCalledEventHandler(IConnectableSubModel connectableSubModel, IConnectableOperation connectableOperation, MethodCalledEventHandler handler);
        void RegisterEventHandler(IConnectableSubModel connectableSubModel, IConnectableEvent connectableEvent, EventHandler handler);
    }
}
