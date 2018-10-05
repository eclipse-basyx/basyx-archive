using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Utils.ResultHandling;
using System.Collections.Generic;

namespace BaSys40.API.Agents
{
    public interface ISubModelAgent
    {

        #region Operation - CRUD-Operations
        IResult<IOperationDescription> CreateOperation(string aasId, string subModelId, IOperationDescription operation);

        IResult<IElementContainer<IOperationDescription>> RetrieveOperations(string aasId, string subModelId);

        IResult<IOperationDescription> RetrieveOperation(string aasId, string subModelId, string operationId);

        IResult DeleteOperation(string aasId, string subModelId, string operationId);

        IResult InvokeOperation(string aasId, string subModelId, string operationId, List<IArgument> inputArguments, out List<IArgument> outputArguments, int timeout);
        #endregion

        #region Property - CRUD-Operations
        IResult<IPropertyDescription> CreateProperty(string aasId, string subModelId, IPropertyDescription property);

        IResult<IElementContainer<IPropertyDescription>> RetrieveProperties(string aasId, string subModelId);

        IResult<IPropertyDescription> RetrieveProperty(string aasId, string subModelId, string propertyId);

        IResult UpdateProperty(string aasId, string subModelId, string propertyId, IValue value);

        IResult DeleteProperty(string aasId, string subModelId, string propertyId);
        #endregion

        #region Event - CRUD-Operations
        IResult<IEventDescription> CreateEvent(string aasId, string subModelId, IEventDescription eventable);

        IResult<IElementContainer<IEventDescription>> RetrieveEvents(string aasId, string subModelId);

        IResult<IEventDescription> RetrieveEvent(string aasId, string subModelId, string eventId);

        IResult DeleteEvent(string aasId, string subModelId, string eventId);
        #endregion
    }
}
