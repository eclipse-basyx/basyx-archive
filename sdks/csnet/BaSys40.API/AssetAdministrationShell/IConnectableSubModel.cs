using BaSys40.API.ServiceProvider;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;

namespace BaSys40.API.AssetAdministrationShell
{
    public interface IConnectableSubModel : ISubModelServiceProvider
    {
        ISubModel SubModel { get; }

     }
}
