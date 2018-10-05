using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Utils.ResultHandling;
using BaSys40.API.Agents;
using System.Collections.Generic;

namespace BaSys40.API.AssetAdministrationShell.Connectables
{
    public class ConnectedSubModel : IConnectableSubModel
    {
        public ISubModel SubModel { get; private set; }
        private IAssetAdministrationShell AssetAdministrationShell { get; }

        private readonly ISubModelAgent serviceImpl;

        public ConnectedSubModel(ISubModelAgent service, IAssetAdministrationShell aas,  ISubModel subModel)
        {
            SubModel = subModel;
            AssetAdministrationShell = aas;
            serviceImpl = service;
        }

        public void BindTo(ISubModel element)
        {
            SubModel = element;
        }

        public ISubModel GetBinding()
        {
            return SubModel;
        }

        public IResult<IEventDescription> CreateEvent(IEventDescription eventable)
        {
            return serviceImpl.CreateEvent(AssetAdministrationShell.Identification.Id, SubModel.Identification.Id, eventable);
        }

        public IResult<IOperationDescription> CreateOperation(IOperationDescription operation)
        {
            return serviceImpl.CreateOperation(AssetAdministrationShell.Identification.Id, SubModel.Identification.Id, operation);
        }

        public IResult<IPropertyDescription> CreateProperty(IPropertyDescription property)
        {
            return serviceImpl.CreateProperty(AssetAdministrationShell.Identification.Id, SubModel.Identification.Id, property);
        }

        public IResult DeleteEvent(string eventId)
        {
            return serviceImpl.DeleteEvent(AssetAdministrationShell.Identification.Id, SubModel.Identification.Id, eventId);
        }

        public IResult DeleteOperation(string operationId)
        {
            return serviceImpl.DeleteOperation(AssetAdministrationShell.Identification.Id, SubModel.Identification.Id, operationId);
        }

        public IResult DeleteProperty(string propertyId)
        {
            return serviceImpl.DeleteProperty(AssetAdministrationShell.Identification.Id, SubModel.Identification.Id, propertyId);
        }

        public IResult<IEventDescription> RetrieveEvent(string eventId)
        {
            return serviceImpl.RetrieveEvent(AssetAdministrationShell.Identification.Id, SubModel.Identification.Id, eventId);
        }

        public IResult<IElementContainer<IEventDescription>> RetrieveEvents()
        {
            return serviceImpl.RetrieveEvents(AssetAdministrationShell.Identification.Id, SubModel.Identification.Id);
        }

        public IResult<IOperationDescription> RetrieveOperation(string operationId)
        {
            return serviceImpl.RetrieveOperation(AssetAdministrationShell.Identification.Id, SubModel.Identification.Id, operationId);
        }

        public IResult<IElementContainer<IOperationDescription>> RetrieveOperations()
        {
            return serviceImpl.RetrieveOperations(AssetAdministrationShell.Identification.Id, SubModel.Identification.Id);   
        }

        public IResult<IElementContainer<IPropertyDescription>> RetrieveProperties()
        {
            return serviceImpl.RetrieveProperties(AssetAdministrationShell.Identification.Id, SubModel.Identification.Id);
        }

        public IResult<IPropertyDescription> RetrieveProperty(string propertyId)
        {
            return serviceImpl.RetrieveProperty(AssetAdministrationShell.Identification.Id, SubModel.Identification.Id, propertyId);
        }

        public IResult UpdateProperty(string propertyId, IValue value)
        {
            return serviceImpl.UpdateProperty(AssetAdministrationShell.Identification.Id, SubModel.Identification.Id, propertyId, value);
        }

        public IResult InvokeOperation(string operationId, List<IArgument> inputArguments, out List<IArgument> outputArguments, int timeout)
        {
            return serviceImpl.InvokeOperation(AssetAdministrationShell.Identification.Id, SubModel.Identification.Id, operationId, inputArguments, out outputArguments, timeout);
        }

       
    }
}
