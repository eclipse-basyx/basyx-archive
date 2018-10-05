using BaSys40.API.ServiceProvider;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;

namespace BaSys40.API.AssetAdministrationShell
{
    public interface IConnectableProperty
    {
        IPropertyDescription Property { get; }

        event SetPropertyValueHandler SetPropertyValueHandler;
        event GetPropertyValueHandler GetPropertyValueHandler;

        IValue GetLocalValue();
        void SetLocalValue(IValue value);
        IValue GetRemoteValue();
        void SetRemoteValue(IValue value);

    }
}
