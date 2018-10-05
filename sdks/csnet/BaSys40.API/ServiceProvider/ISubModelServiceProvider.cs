using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Utils.ResultHandling;
using System.Collections.Generic;

namespace BaSys40.API.ServiceProvider
{
    public interface ISubModelServiceProvider : IServiceProvider<ISubModel>
    {
        
        #region Operation - CRUD-Operations
        IResult<IOperationDescription> CreateOperation(IOperationDescription operation);

        IResult<IElementContainer<IOperationDescription>> RetrieveOperations();

        IResult<IOperationDescription> RetrieveOperation(string operationId);

        IResult DeleteOperation(string operationId);

        IResult InvokeOperation(string operationId, List<IArgument> inputArguments, out List<IArgument> outputArguments, int timeout);
        #endregion

        #region Property - CRUD-Operations
        IResult<IPropertyDescription> CreateProperty(IPropertyDescription property);

        IResult<IElementContainer<IPropertyDescription>> RetrieveProperties();

        IResult<IPropertyDescription> RetrieveProperty(string propertyId);

        IResult UpdateProperty(string propertyId, IValue value);

        IResult DeleteProperty(string propertyId);
        #endregion

        #region Event - CRUD-Operations
        IResult<IEventDescription> CreateEvent(IEventDescription eventable);

        IResult<IElementContainer<IEventDescription>> RetrieveEvents();

        IResult<IEventDescription> RetrieveEvent(string eventId);

        IResult DeleteEvent(string eventId);
        #endregion
    }
}
