using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Utils.ResultHandling;

namespace BaSys40.API.Agents
{
    public interface IAssetAdministrationShellAgent
    {
        #region SubModel - CRUD-Operations

        IResult<ISubModel> CreateSubModel(string aasId, ISubModel submodel);

        IResult<IElementContainer<ISubModel>> RetrieveSubModels(string aasId);

        IResult<ISubModel> RetrieveSubModel(string aasId, string subModelId);

        IResult DeleteSubModel(string aasId, string subModelId);

        #endregion
    }    
}
