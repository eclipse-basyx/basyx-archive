using BaSys40.API.Platform;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Utils.ResultHandling;
using System;

namespace BaSys40.API.Components
{
    public interface IComponentConnector
    {
        IAssetAdministrationShell GenerateAssetAdministrationShell();

        IResult Register(IAssetAdministrationShellRegistry registry, IAssetAdministrationShell aas);

        IResult CreateStructure(IAssetAdministrationShellManager manager, IAssetAdministrationShellHandler handler, IAssetAdministrationShell aas);

        IResult Unregister(IAssetAdministrationShellRegistry registry, string aasId);

    }
}
