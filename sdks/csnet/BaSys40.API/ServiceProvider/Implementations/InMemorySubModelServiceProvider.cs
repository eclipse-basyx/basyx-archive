using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Utils.ResultHandling;
using System;
using System.Collections.Generic;
using System.Text;

namespace BaSys40.API.ServiceProvider.Implementations
{
    public class InMemorySubModelServiceProvider : ISubModelServiceProvider
    {
        private ISubModel SubModel { get; set; }

        public InMemorySubModelServiceProvider()
        { }

        public void BindTo(ISubModel subModel)
        {
            if (subModel != null)
                SubModel = subModel;
        }

        public ISubModel GetBinding()
        {
            return SubModel;
        }

        public IResult<IOperationDescription> CreateOperation(IOperationDescription operation)
        {
            try
            {
                SubModel.Operations.Add(operation);
                return new Result<IOperationDescription>(true, operation);
            }
            catch (Exception e)
            {
                return new Result<IOperationDescription>(e);
            }
        }

        public IResult<IElementContainer<IOperationDescription>> RetrieveOperations()
        {
            try
            {
                return new Result<IElementContainer<IOperationDescription>>(true, SubModel.Operations);
            }
            catch (Exception e)
            {
                return new Result<IElementContainer<IOperationDescription>>(e);
            }
        }

        public IResult<IOperationDescription> RetrieveOperation(string operationId)
        {
            try
            {
                var operation = SubModel.Operations[operationId];
                if (operation == null)
                    throw new KeyNotFoundException("operationId not found");
                return new Result<IOperationDescription>(true, operation);
            }
            catch (Exception e)
            {
                return new Result<IOperationDescription>(e);
            }
        }

        public IResult DeleteOperation(string operationId)
        {
            try
            {
                var operation = SubModel.Operations[operationId];
                if (operation == null)
                    throw new KeyNotFoundException("operationId not found");
                SubModel.Operations.Remove(operation);
                return new Result(true);
            }
            catch (Exception e)
            {
                return new Result(e);
            }
        }

        public IResult InvokeOperation(string operationId, List<IArgument> inputArguments, out List<IArgument> outputArguments, int timeout)
        {
            throw new NotImplementedException();
        }

        public IResult<IPropertyDescription> CreateProperty(IPropertyDescription property)
        {
            try
            {
                SubModel.Properties.Add(property);
                return new Result<IPropertyDescription>(true, property);
            }
            catch (Exception e)
            {
                return new Result<IPropertyDescription>(e);
            }
        }

        public IResult<IElementContainer<IPropertyDescription>> RetrieveProperties()
        {
            try
            {
                return new Result<IElementContainer<IPropertyDescription>>(true, SubModel.Properties);
            }
            catch (Exception e)
            {
                return new Result<IElementContainer<IPropertyDescription>>(e);
            }
        }

        public IResult<IPropertyDescription> RetrieveProperty(string propertyId)
        {
            try
            {
                var property = SubModel.Properties[propertyId];
                if (property == null)
                    throw new KeyNotFoundException("propertyId not found");
                return new Result<IPropertyDescription>(true, property);
            }
            catch (Exception e)
            {
                return new Result<IPropertyDescription>(e);
            }
        }

        public IResult UpdateProperty(string propertyId, IValue value)
        {
            try
            {
                var property = SubModel.Properties[propertyId];
                if (property == null)
                    throw new KeyNotFoundException("propertyId not found");

                SubModel.Properties[propertyId].Value = value;
                return new Result<IValue>(true, value);
            }
            catch (Exception e)
            {
                return new Result(e);
            }
        }

        public IResult DeleteProperty(string propertyId)
        {
            try
            {
                var property = SubModel.Properties[propertyId];
                if (property == null)
                    throw new KeyNotFoundException("propertyId not found");
                SubModel.Properties.Remove(property);
                return new Result(true);
            }
            catch (Exception e)
            {
                return new Result(e);
            }
        }

        public IResult<IEventDescription> CreateEvent(IEventDescription eventable)
        {
            try
            {
                SubModel.Events.Add(eventable);
                return new Result<IEventDescription>(true, eventable);
            }
            catch (Exception e)
            {
                return new Result<IEventDescription>(e);
            }
        }

        public IResult<IElementContainer<IEventDescription>> RetrieveEvents()
        {
            try
            {
                return new Result<IElementContainer<IEventDescription>>(true, SubModel.Events);
            }
            catch (Exception e)
            {
                return new Result<IElementContainer<IEventDescription>>(e);
            }
        }

        public IResult<IEventDescription> RetrieveEvent(string eventId)
        {
            try
            {
                var eventable = SubModel.Events[eventId];
                if (eventable == null)
                    throw new KeyNotFoundException("eventId not found");
                return new Result<IEventDescription>(true, eventable);
            }
            catch (Exception e)
            {
                return new Result<IEventDescription>(e);
            }
        }

        public IResult DeleteEvent(string eventId)
        {
            try
            {
                var eventable = SubModel.Events[eventId];
                if (eventable == null)
                    throw new KeyNotFoundException("eventId not found");
                SubModel.Events.Remove(eventable);
                return new Result(true);
            }
            catch (Exception e)
            {
                return new Result(e);
            }
        }
    }
}