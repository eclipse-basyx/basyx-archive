using BaSys40.API.ServiceProvider;
using BaSys40.Models.Connectivity;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Utils.ResultHandling;
using System.Collections.Generic;
using System.Linq;

namespace BaSys40.API.Components
{
    public class ComponentAggregatorServiceProvider : IAssetAdministrationShellAggregatorServiceProvider
    {
        public IEnumerable<IAssetAdministrationShell> AssetAdministrationShells { get; protected set; }

        private Dictionary<string, IAssetAdministrationShellServiceProvider> AssetAdministrationShellServiceProviders { get; } = new Dictionary<string, IAssetAdministrationShellServiceProvider>();

        public IServiceDescriptor ServiceDescriptor { get; private set; }

        public ComponentAggregatorServiceProvider()
        {
            AssetAdministrationShells = new List<IAssetAdministrationShell>();
        }

        public void BindTo(IEnumerable<IAssetAdministrationShell> element)
        {
            AssetAdministrationShells = element;
        }
        public IEnumerable<IAssetAdministrationShell> GetBinding()
        {
            return AssetAdministrationShells;
        }

        public IResult<IAssetAdministrationShell> CreateAssetAdministrationShell(IAssetAdministrationShell aas)
        {
            AssetAdministrationShells.ToList().Add(aas);
            return new Result<IAssetAdministrationShell>(true, aas);
        }

        public IResult DeleteAssetAdministrationShell(string aasId)
        {
            AssetAdministrationShells.ToList().RemoveAll(a => a.IdShort == aasId);
            return new Result(true);
        }

        public IAssetAdministrationShellServiceProvider GetAssetAdministrationShellServiceProvider(string id)
        {
            if (AssetAdministrationShellServiceProviders.TryGetValue(id, out IAssetAdministrationShellServiceProvider assetAdministrationShellServiceProvider))
                return assetAdministrationShellServiceProvider;
            else
                return null;
        }

        public IEnumerable<IAssetAdministrationShellServiceProvider> GetAssetAdministrationShellServiceProviders()
        {
           return AssetAdministrationShellServiceProviders?.Values.ToList();
        }

        public void RegisterAssetAdministrationShellServiceProvider(string id, IAssetAdministrationShellServiceProvider assetAdministrationShellServiceProvider)
        {
            AssetAdministrationShellServiceProviders.Add(id, assetAdministrationShellServiceProvider);
        }

        public IResult<IAssetAdministrationShell> RetrieveAssetAdministrationShell(string aasId)
        {
            var aas = AssetAdministrationShells.ToList().Find(a => a.IdShort == aasId);
            if (aas != null)
                return new Result<IAssetAdministrationShell>(true, aas);
            else
                return new Result<IAssetAdministrationShell>(false, new Message(MessageType.Error, "Not found"));
        }

        public IResult<List<IAssetAdministrationShell>> RetrieveAssetAdministrationShells()
        {
            return new Result<List<IAssetAdministrationShell>>(true, AssetAdministrationShells.ToList());
        }

        public IResult UpdateAssetAdministrationShell(string aasId, IAssetAdministrationShell aas)
        {
            int i = AssetAdministrationShells.ToList().FindIndex(a => a.IdShort == aas.IdShort);
            if (i >= 0)
            {
                AssetAdministrationShells.ToList()[i] = aas;
                return new Result<List<IAssetAdministrationShell>>(true);
            }
            return new Result(false, new Message(MessageType.Error, "Not found"));
        }
    }
}
