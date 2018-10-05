using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Utils.ResultHandling;

namespace BaSys40.API.ServiceProvider
{
    public interface IAssetAdministrationShellServiceProvider : IServiceProvider<IAssetAdministrationShell>
    {
        #region SubModel - CRUD-Operations

        IResult<ISubModel> CreateSubModel(ISubModel submodel);

        IResult<IElementContainer<ISubModel>> RetrieveSubModels();

        IResult<ISubModel> RetrieveSubModel(string subModelId);

        IResult DeleteSubModel(string subModelId);

        #endregion

  
    }
}
