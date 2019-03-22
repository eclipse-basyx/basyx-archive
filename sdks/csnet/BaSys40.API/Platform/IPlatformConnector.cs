using BaSys40.API.Platform;
using BaSys40.Models.Core.AssetAdministrationShell;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Utils.ResultHandling;

namespace BaSys40.API.Platform
{
    public interface IPlatformConnector
    {
        IAssetAdministrationShell GenerateAssetAdministrationShell();

        IResult Register(IAssetAdministrationShellRegistry registry, AssetAdministrationShellDescriptor aas);

        IResult CreateStructure(IAssetAdministrationShellManager manager, IAssetAdministrationShellHandler handler, IAssetAdministrationShell aas);

        IResult Unregister(IAssetAdministrationShellRegistry registry, string aasId);

    }
}
