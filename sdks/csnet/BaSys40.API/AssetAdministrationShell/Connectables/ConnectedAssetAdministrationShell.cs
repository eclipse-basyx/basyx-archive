using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Utils.ResultHandling;
using BaSys40.API.Platform.Agents;
using BaSys40.Models.Core.AssetAdministrationShell.Implementations;
using BaSys40.API.ServiceProvider;
using System.Collections.Generic;
using System.Linq;
using BaSys40.Models.Connectivity;

namespace BaSys40.API.AssetAdministrationShell.Connectables
{
    public class ConnectedAssetAdministrationShell : IConnectableAssetAdministrationShell
    {
        public IAssetAdministrationShell AssetAdministrationShell { get; private set; }

        public IServiceDescriptor ServiceDescriptor { get; private set; }

        private readonly IAssetAdministrationShellAgent serviceImpl;
        private Dictionary<string, ISubmodelServiceProvider> submodelServiceProviders;

        public ConnectedAssetAdministrationShell(IAssetAdministrationShellAgent service)
        {
            serviceImpl = service;
            submodelServiceProviders = new Dictionary<string, ISubmodelServiceProvider>();
        }
        
        public void BindTo(IAssetAdministrationShell element)
        {
            AssetAdministrationShell = element;
        }

        public IAssetAdministrationShell GetBinding()
        {
            return AssetAdministrationShell;
        }
        
        public IResult<ISubmodel> CreateSubmodel(ISubmodel submodel)
        {
            return serviceImpl.CreateSubmodel(AssetAdministrationShell.IdShort, submodel);
        }

        public IResult DeleteSubmodel(string submodelId)
        {
            return serviceImpl.DeleteSubmodel(AssetAdministrationShell.IdShort, submodelId);
        }

        public IResult<ISubmodel> RetrieveSubmodel(string submodelId)
        {
            return serviceImpl.RetrieveSubmodel(AssetAdministrationShell.IdShort, submodelId);
        }

        public IResult<ElementContainer<ISubmodel>> RetrieveSubmodels()
        {
            return serviceImpl.RetrieveSubmodels(AssetAdministrationShell.IdShort);
        }

        public void RegisterSubmodelServiceProvider(string id, ISubmodelServiceProvider submodelServiceProvider)
        {
            submodelServiceProviders.Add(id, submodelServiceProvider);
        }

        public ISubmodelServiceProvider GetSubmodelServiceProvider(string id)
        {
            if (submodelServiceProviders.TryGetValue(id, out ISubmodelServiceProvider submodelServiceProvider))
                return submodelServiceProvider;
            else
                return null;
        }

        public IEnumerable<ISubmodelServiceProvider> GetSubmodelServiceProviders()
        {
            return submodelServiceProviders.Values?.ToList();
        }
    }
}
