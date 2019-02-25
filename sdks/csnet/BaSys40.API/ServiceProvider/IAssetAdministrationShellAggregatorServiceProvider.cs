using BaSys40.API.Platform;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using System.Collections.Generic;

namespace BaSys40.API.ServiceProvider
{
    public interface IAssetAdministrationShellAggregatorServiceProvider : IServiceProvider<IEnumerable<IAssetAdministrationShell>>, IAssetAdministrationShellAggregator
    {
        IEnumerable<IAssetAdministrationShell> AssetAdministrationShells { get; }
        void RegisterAssetAdministrationShellServiceProvider(string id, IAssetAdministrationShellServiceProvider assetAdministrationShellServiceProvider);
        IAssetAdministrationShellServiceProvider GetAssetAdministrationShellServiceProvider(string id);
        IEnumerable<IAssetAdministrationShellServiceProvider> GetAssetAdministrationShellServiceProviders();
    }
}
