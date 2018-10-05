using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Utils.ResultHandling;
using BaSys40.API.Agents;

namespace BaSys40.API.AssetAdministrationShell.Connectables
{
    public class ConnectedAssetAdministrationShell : IConnectableAssetAdministrationShell
    {
        public IAssetAdministrationShell AssetAdministrationShell { get; private set; }

        private readonly IAssetAdministrationShellAgent serviceImpl;

        public ConnectedAssetAdministrationShell(IAssetAdministrationShellAgent service,  IAssetAdministrationShell aas)
        {
            AssetAdministrationShell = aas;
            serviceImpl = service;
        }
        
        public void BindTo(IAssetAdministrationShell element)
        {
            AssetAdministrationShell = element;
        }

        public IAssetAdministrationShell GetBinding()
        {
            return AssetAdministrationShell;
        }
        
        public IResult<ISubModel> CreateSubModel(ISubModel subModel)
        {
            return serviceImpl.CreateSubModel(AssetAdministrationShell.Identification.Id, subModel);
        }

        public IResult DeleteSubModel(string subModelId)
        {
            return serviceImpl.DeleteSubModel(AssetAdministrationShell.Identification.Id, subModelId);
        }

        public IResult<ISubModel> RetrieveSubModel(string subModelId)
        {
            return serviceImpl.RetrieveSubModel(AssetAdministrationShell.Identification.Id, subModelId);
        }

        public IResult<IElementContainer<ISubModel>> RetrieveSubModels()
        {
            return serviceImpl.RetrieveSubModels(AssetAdministrationShell.Identification.Id);
        }

        
    }
}
