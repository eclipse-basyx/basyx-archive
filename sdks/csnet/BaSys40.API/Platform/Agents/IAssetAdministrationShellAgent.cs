using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.AssetAdministrationShell.Implementations;
using BaSys40.Utils.ResultHandling;

namespace BaSys40.API.Platform.Agents
{
    public interface IAssetAdministrationShellAgent
    {

        #region Submodel - CRUD-Operations

        IResult<ISubmodel> CreateSubmodel(string aasId, ISubmodel submodel);

        IResult<ElementContainer<ISubmodel>> RetrieveSubmodels(string aasId);

        IResult<ISubmodel> RetrieveSubmodel(string aasId, string submodelId);

        IResult DeleteSubmodel(string aasId, string submodelId);

        #endregion
    }    
}
