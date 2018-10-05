using BaSys40.API.ServiceProvider;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Utils.ResultHandling;

namespace BaSys40.API.AssetAdministrationShell
{
    public interface IConnectableAssetAdministrationShell : IAssetAdministrationShellServiceProvider
    {
        IAssetAdministrationShell AssetAdministrationShell { get; }

    }
}
