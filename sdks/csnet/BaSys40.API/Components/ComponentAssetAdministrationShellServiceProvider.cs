using BaSys40.API.ServiceProvider;
using BaSys40.Models.Connectivity;
using BaSys40.Models.Core.AssetAdministrationShell;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.AssetAdministrationShell.Implementations;
using BaSys40.Utils.ResultHandling;
using System.Collections.Generic;
using System.Linq;

namespace BaSys40.API.Components
{
    public abstract class ComponentAssetAdministrationShellServiceProvider : IAssetAdministrationShellServiceProvider
    {
        public abstract IAssetAdministrationShell AssetAdministrationShell { get; protected set; }
        private Dictionary<string, ISubmodelServiceProvider> SubmodelServiceProviders { get; } = new Dictionary<string, ISubmodelServiceProvider>();
        public IServiceDescriptor ServiceDescriptor { get; private set; }

        public ComponentAssetAdministrationShellServiceProvider(AssetAdministrationShellDescriptor assetAdministrationShellDescriptor) : this()
        {
            ServiceDescriptor = assetAdministrationShellDescriptor;
        }
        public ComponentAssetAdministrationShellServiceProvider(IAssetAdministrationShell assetAdministrationShell)
        {
            BindTo(assetAdministrationShell);
        }

        public ComponentAssetAdministrationShellServiceProvider()
        {
            AssetAdministrationShell = GenerateAssetAdministrationShell();
            BindTo(AssetAdministrationShell);
        }

        public virtual void RegisterSubmodelServiceProvider(string submodelId, ISubmodelServiceProvider submodelServiceProvider)
        {
            SubmodelServiceProviders.Add(submodelId, submodelServiceProvider);
        }
        public virtual ISubmodelServiceProvider GetSubmodelServiceProvider(string submodelId)
        {
            if (SubmodelServiceProviders.TryGetValue(submodelId, out ISubmodelServiceProvider submodelServiceProvider))
                return submodelServiceProvider;
            else
                return null;
        }

        public abstract IAssetAdministrationShell GenerateAssetAdministrationShell();

        public virtual void BindTo(IAssetAdministrationShell element)
        {
            AssetAdministrationShell = element;
        }
        public virtual IAssetAdministrationShell GetBinding()
        {
            return AssetAdministrationShell;
        }

        public virtual IResult<ISubmodel> CreateSubmodel(ISubmodel submodel)
        {
            return AssetAdministrationShell.Submodels.Create(submodel);
        }

        public virtual IResult DeleteSubmodel(string submodelId)
        {
            return AssetAdministrationShell.Submodels.Delete(submodelId);
        }

        public virtual IResult<ISubmodel> RetrieveSubmodel(string submodelId)
        {
            return AssetAdministrationShell.Submodels.Retrieve(submodelId);
        }

        public virtual IResult<ElementContainer<ISubmodel>> RetrieveSubmodels()
        {
            return AssetAdministrationShell.Submodels.RetrieveAll();
        }

        public virtual IEnumerable<ISubmodelServiceProvider> GetSubmodelServiceProviders()
        {
            return SubmodelServiceProviders.Values?.ToList();
        }
    }
}
