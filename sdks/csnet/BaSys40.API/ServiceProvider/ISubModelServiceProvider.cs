using BaSys40.Utils.Client;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.AssetAdministrationShell.Implementations;
using BaSys40.Utils.ResultHandling;
using System.Collections.Generic;
using System;
using BaSys40.API.AssetAdministrationShell;

namespace BaSys40.API.ServiceProvider
{
    public interface ISubmodelServiceProvider : IServiceProvider<ISubmodel>
    {
        ISubmodel Submodel { get; }

        #region Operation - CRUD-Operations
        IResult<IOperation> CreateOperation(IOperation operation);

        IResult<ElementContainer<IOperation>> RetrieveOperations();

        IResult<IOperation> RetrieveOperation(string operationId);

        IResult DeleteOperation(string operationId);
    
        IResult InvokeOperation(string operationId, List<IArgument> inputArguments, List<IArgument> outputArguments, int timeout);
        #endregion

        #region DataElement - CRUD-Operations
        IResult<IDataElement> CreateDataElement(IDataElement dataElement);

        IResult<ElementContainer<IDataElement>> RetrieveDataElements();

        IResult<IDataElement> RetrieveDataElement(string dataElementId);

        IResult<IValue> RetrieveDataElementValue(string dataElementId);

        IResult UpdateDataElementValue(string dataElementId, IValue value);

        IResult DeleteDataElement(string dataElementId);

        void SubscribeUpdates(string dataElementId, Action<IValue> updateFunction);
        void PublishUpdate(string dataElementId, IValue value);
        #endregion

        #region Event - CRUD-Operations
        IResult<IEvent> CreateEvent(IEvent eventable);

        IResult<ElementContainer<IEvent>> RetrieveEvents();

        IResult<IEvent> RetrieveEvent(string eventId);

        IResult ThrowEvent(IPublishableEvent publishableEvent, string topic, Action<IMessagePublishedEventArgs> MessagePublished, byte qosLevel);

        IResult DeleteEvent(string eventId);
        #endregion

        DataElementHandler RetrieveDataElementHandler(string dataElementId);
        void RegisterDataElementHandler(string dataElementId, DataElementHandler handler);
        Delegate RetrieveMethodDelegate(string operationId);
        void RegisterMethodCalledHandler(string operationId, Delegate handler);
        void ConfigureEventHandler(IMessageClient messageClient);
    }
}
