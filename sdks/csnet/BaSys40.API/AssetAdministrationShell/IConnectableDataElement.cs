using BaSys40.API.ServiceProvider;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;

namespace BaSys40.API.AssetAdministrationShell
{
    public interface IConnectableDataElement
    {
        IAssetAdministrationShell AssetAdministrationShell { get; }
        ISubmodel Submodel { get; }
        IDataElement DataElement { get; }

        event SetDataElementValueEventHandler SetDataElementValueHandler;
        event GetDataElementValueEventHandler GetDataElementValueHandler;

        IValue GetLocalValue();
        void SetLocalValue(IValue value);
        IValue GetRemoteValue();
        void SetRemoteValue(IValue value);

    }
}
