using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.AssetAdministrationShell.Implementations;
using BaSys40.Utils.ResultHandling;
using System.Collections.Generic;

namespace BaSys40.API.Platform.Agents
{
    public interface ISubmodelAgent
    {

        #region Operation - CRUD-Operations
        IResult<IOperation> CreateOperation(string aasId, string submodelId, IOperation operation);

        IResult<ElementContainer<IOperation>> RetrieveOperations(string aasId, string submodelId);

        IResult<IOperation> RetrieveOperation(string aasId, string submodelId, string operationId);

        IResult DeleteOperation(string aasId, string submodelId, string operationId);

        IResult InvokeOperation(string aasId, string submodelId, string operationId, List<IArgument> inputArguments, List<IArgument> outputArguments, int timeout);
        #endregion

        #region DataElement - CRUD-Operations
        IResult<IDataElement> CreateDataElement(string aasId, string submodelId, IDataElement dataElement);

        IResult<ElementContainer<IDataElement>> RetrieveDataElements(string aasId, string submodelId);

        IResult<IDataElement> RetrieveDataElement(string aasId, string submodelId, string dataElementId);

        IResult UpdateDataElementValue(string aasId, string submodelId, string dataElementId, IValue value);

        IResult<IValue> RetrieveDataElementValue(string aasId, string submodelId, string dataElementId);

        IResult DeleteDataElement(string aasId, string submodelId, string dataElementId);
        #endregion

        #region Event - CRUD-Operations
        IResult<IEvent> CreateEvent(string aasId, string submodelId, IEvent eventable);

        IResult<ElementContainer<IEvent>> RetrieveEvents(string aasId, string submodelId);

        IResult<IEvent> RetrieveEvent(string aasId, string submodelId, string eventId);

        IResult DeleteEvent(string aasId, string submodelId, string eventId);
        #endregion
    }
}
