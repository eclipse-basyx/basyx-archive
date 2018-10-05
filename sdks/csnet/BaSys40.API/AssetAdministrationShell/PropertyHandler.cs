using BaSys40.Models.Core.AssetAdministrationShell.Generics;

namespace BaSys40.API.AssetAdministrationShell
{
    public delegate IValue GetPropertyValueHandler(IConnectableProperty property);
    public delegate void SetPropertyValueHandler(IConnectableProperty property, IValue value);

}
