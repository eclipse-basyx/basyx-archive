using BaSys40.Models.Core.AssetAdministrationShell;
using BaSys40.Utils.ResultHandling;
using System.Collections.Generic;

namespace BaSys40.API.Platform
{
    public interface IAssetAdministrationShellRegistry
    {
        IResult<AssetAdministrationShellDescriptor> CreateAssetAdministrationShell(AssetAdministrationShellDescriptor aas);
        IResult<AssetAdministrationShellDescriptor> RetrieveAssetAdministrationShell(string aasId);
        IResult<List<AssetAdministrationShellDescriptor>> RetrieveAssetAdministrationShells();
        IResult UpdateAssetAdministrationShell(string aasId, Dictionary<string, string> metaData);
        IResult DeleteAssetAdministrationShell(string aasId);

        IResult<SubmodelDescriptor> CreateSubmodel(string aasId, SubmodelDescriptor submodel);
        IResult<List<SubmodelDescriptor>> RetrieveSubmodels(string aasId);
        IResult<SubmodelDescriptor> RetrieveSubmodel(string aasId, string submodelId);
        IResult DeleteSubmodel(string aasId, string submodelId);
    }
}
