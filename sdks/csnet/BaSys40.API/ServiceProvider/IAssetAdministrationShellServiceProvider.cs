using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.AssetAdministrationShell.Implementations;
using BaSys40.Utils.ResultHandling;
using System.Collections.Generic;

namespace BaSys40.API.ServiceProvider
{
    public interface IAssetAdministrationShellServiceProvider : IServiceProvider<IAssetAdministrationShell>
    {
        IAssetAdministrationShell AssetAdministrationShell { get; }


        #region Submodel - CRUD-Operations

        IResult<ISubmodel> CreateSubmodel(ISubmodel submodel);

        IResult<ElementContainer<ISubmodel>> RetrieveSubmodels();

        IResult<ISubmodel> RetrieveSubmodel(string submodelId);

        IResult DeleteSubmodel(string submodelId);

        #endregion

        void RegisterSubmodelServiceProvider(string id, ISubmodelServiceProvider submodelServiceProvider);
        ISubmodelServiceProvider GetSubmodelServiceProvider(string id);
        IEnumerable<ISubmodelServiceProvider> GetSubmodelServiceProviders();
    }
}
