using BaSys40.API.Agents;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Utils.ResultHandling;
using System.Collections.Generic;

namespace BaSys40.API.Platform
{
    public interface IAssetAdministrationShellRegistry : IAssetAdministrationShellAgent
    {
        IResult<IAssetAdministrationShell> CreateAssetAdministrationShell(IAssetAdministrationShell aas);
        IResult<IAssetAdministrationShell> RetrieveAssetAdministrationShell(string aasId);
        IResult<List<IAssetAdministrationShell>> RetrieveAssetAdministrationShells();
        IResult UpdateAssetAdministrationShell(string aasId, Dictionary<string, string> metaData);
        IResult DeleteAssetAdministrationShell(string aasId);       
    }
}
