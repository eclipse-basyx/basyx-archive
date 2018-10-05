using BaSys40.API.Agents;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;

namespace BaSys40.API.AssetAdministrationShell.Connectables
{
    public class ConnectedProperty : IConnectableProperty
    {
        public IPropertyDescription Property { get; }
        private IAssetAdministrationShell AssetAdministrationShell { get; }
        private ISubModel SubModel { get; }

        public event SetPropertyValueHandler SetPropertyValueHandler;
        public event GetPropertyValueHandler GetPropertyValueHandler;

        private readonly ISubModelAgent serviceImpl;

        public ConnectedProperty(ISubModelAgent service, IAssetAdministrationShell aas, ISubModel subModel, IPropertyDescription propertyDescription)
        {
            AssetAdministrationShell = aas;
            SubModel = subModel;
            Property = propertyDescription;

            serviceImpl = service;
        }

        public IValue GetLocalValue()
        {
            var result = GetPropertyValueHandler?.Invoke(this);
            return result;
        }

        public void SetLocalValue(IValue value)
        {
            SetPropertyValueHandler?.Invoke(this, value);
        }

        public IValue GetRemoteValue()
        {
            var result = serviceImpl.RetrieveProperty(AssetAdministrationShell.Identification.Id, SubModel.Identification.Id, Property.Identification.Id);
            return result.Entity.Value;
        }

        public void SetRemoteValue(IValue value)
        {
            serviceImpl.UpdateProperty(AssetAdministrationShell.Identification.Id, SubModel.Identification.Id, Property.Identification.Id, value);
        }
    }
}
