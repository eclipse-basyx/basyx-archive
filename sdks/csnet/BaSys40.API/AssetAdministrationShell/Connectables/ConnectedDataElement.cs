using BaSys40.API.Platform.Agents;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;

namespace BaSys40.API.AssetAdministrationShell.Connectables
{
    public class ConnectedDataElement : IConnectableDataElement
    {
        public IDataElement DataElement { get; }
        public IAssetAdministrationShell AssetAdministrationShell { get; }
        public ISubmodel Submodel { get; }

        public event SetDataElementValueEventHandler SetDataElementValueHandler;
        public event GetDataElementValueEventHandler GetDataElementValueHandler;

        private readonly ISubmodelAgent serviceImpl;

        public ConnectedDataElement(ISubmodelAgent service, IAssetAdministrationShell aas, ISubmodel submodel, IDataElement dataElement)
        {
            AssetAdministrationShell = aas;
            Submodel = submodel;
            DataElement = dataElement;

            serviceImpl = service;
        }

        public IValue GetLocalValue()
        {
            var result = GetDataElementValueHandler?.Invoke(this);
            return result;
        }

        public void SetLocalValue(IValue value)
        {
            SetDataElementValueHandler?.Invoke(this, value);
        }

        public IValue GetRemoteValue()
        {
            var result = serviceImpl.RetrieveDataElementValue(AssetAdministrationShell.IdShort, Submodel.IdShort, DataElement.IdShort);
            return result.Entity;
        }

        public void SetRemoteValue(IValue value)
        {
            serviceImpl.UpdateDataElementValue(AssetAdministrationShell.IdShort, Submodel.IdShort, DataElement.IdShort, value);
        }
    }
}
