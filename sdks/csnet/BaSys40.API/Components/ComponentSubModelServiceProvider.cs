using BaSys40.API.AssetAdministrationShell;
using BaSys40.API.ServiceProvider;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.AssetAdministrationShell.Implementations;
using BaSys40.Utils.ResultHandling;
using BaSys40.Utils.Client;
using System.Collections.Generic;
using System;
using Newtonsoft.Json;
using System.Reflection;
using System.Linq.Expressions;
using System.Linq;
using BaSys40.Models.Connectivity;
using BaSys40.Models.Core.AssetAdministrationShell;

namespace BaSys40.API.Components
{
    public class ComponentSubmodelServiceProvider : ISubmodelServiceProvider
    {

        static ComponentSubmodelServiceProvider()
        {
            JsonSerializerSettings = new JsonSerializerSettings()
            {
                Formatting = Formatting.Indented,
                NullValueHandling = NullValueHandling.Ignore,
            };
            JsonSerializerSettings.Converters.Add(new Newtonsoft.Json.Converters.StringEnumConverter());
        }
        public static JsonSerializerSettings JsonSerializerSettings { get; set; }

        public ISubmodel Submodel { get; protected set; }

        public IServiceDescriptor ServiceDescriptor { get; internal set; }

        private Dictionary<string, Delegate> methodCalledHandler;
        private Dictionary<string, DataElementHandler> dataElementHandler;
        private Dictionary<string, Action<IValue>> updateFunctions;
        private IMessageClient messageClient;
      
        public ComponentSubmodelServiceProvider(SubmodelDescriptor submodelDescriptor) : this()
        {
            ServiceDescriptor = submodelDescriptor;
        }

        public ComponentSubmodelServiceProvider()
        {
            methodCalledHandler = new Dictionary<string, Delegate>();
            dataElementHandler = new Dictionary<string, DataElementHandler>();
            updateFunctions = new Dictionary<string, Action<IValue>>();
        }

        public ComponentSubmodelServiceProvider(ISubmodel submodel) : this()
        {
            BindTo(submodel);
        }

        public void BindTo(ISubmodel element)
        {
            Submodel = element;
        }
        public ISubmodel GetBinding()
        {
            return Submodel;
        }

        public IResult<IEvent> CreateEvent(IEvent eventable)
        {
            return Submodel.Events.Create(eventable);
        }

        public IResult<IOperation> CreateOperation(IOperation operation)
        {
            return Submodel.Operations.Create(operation);
        }

        public IResult<IDataElement> CreateDataElement(IDataElement property)
        {
            return Submodel.DataElements.Create(property);
        }

        public IResult DeleteEvent(string eventId)
        {
            return Submodel.Events.Delete(eventId);
        }

        public IResult DeleteOperation(string operationId)
        {
            return Submodel.Operations.Delete(operationId);
        }

        public IResult DeleteDataElement(string dataElementId)
        {
            return Submodel.DataElements.Delete(dataElementId);
        }

        public MethodCalledHandler RetrieveMethodCalledHandler(string operationId)
        {
            if (methodCalledHandler.TryGetValue(operationId, out Delegate handler))
                return (MethodCalledHandler)handler;
            else
                return null;
        }

        public Delegate RetrieveMethodDelegate(string operationId)
        {
            if (methodCalledHandler.TryGetValue(operationId, out Delegate handler))
                return handler;
            else
                return null;
        }

        public DataElementHandler RetrieveDataElementHandler(string dataElementId)
        {
            if (dataElementHandler.TryGetValue(dataElementId, out DataElementHandler handler))
                return handler;
            else
                return null;
        }

        public void RegisterDataElementHandler(string dataElementId, DataElementHandler handler)
        {
            if (!dataElementHandler.ContainsKey(dataElementId))
                dataElementHandler.Add(dataElementId, handler);
            else
                dataElementHandler[dataElementId] = handler;
        }

        public void RegisterMethodCalledHandler(string operationId, MethodCalledHandler handler)
        {
            if (!methodCalledHandler.ContainsKey(operationId))
                methodCalledHandler.Add(operationId, handler);
            else
                methodCalledHandler[operationId] = handler;
        }

        public void RegisterMethodCalledHandler(string operationId, Delegate handler)
        {
            if (!methodCalledHandler.ContainsKey(operationId))
                methodCalledHandler.Add(operationId, handler);
            else
                methodCalledHandler[operationId] = handler;
        }

        public void RegisterMethodCalledHandler(string operationId, MethodInfo methodInfo, object target)
        {
            var parameters = from parameter in methodInfo.GetParameters() select parameter.ParameterType;
            Delegate del = methodInfo.CreateDelegate(Expression.GetDelegateType(parameters.Concat(new[] { methodInfo.ReturnType }).ToArray()), target);
            RegisterMethodCalledHandler(operationId, del);
        }

        public IResult InvokeOperation(string operationId, List<IArgument> inputArguments, List<IArgument> outputArguments, int timeout)
        {
            var operation_Retrieved = RetrieveOperation(operationId);
            if (methodCalledHandler.TryGetValue(operationId, out Delegate handler) && operation_Retrieved.Success && operation_Retrieved.Entity != null)
            {
                var result = (IResult)handler.DynamicInvoke(operation_Retrieved.Entity, inputArguments, outputArguments);
                return result;
            }
            outputArguments = null;
            return operation_Retrieved;
        }

        public IResult ThrowEvent(IPublishableEvent publishableEvent, string topic = "/", Action<IMessagePublishedEventArgs> MessagePublished = null, byte qosLevel = 2)
        {
            if (messageClient == null || !messageClient.IsConnected)
                return new Result(false, new Message(MessageType.Warning, "MessageClient is not initialized or not connected"));

            if (publishableEvent == null)
                return new Result(new ArgumentNullException("publishableEvent"));

            string message = JsonConvert.SerializeObject(publishableEvent, JsonSerializerSettings);
            return messageClient.Publish(topic, message, MessagePublished, qosLevel);
        }

        public IResult<IEvent> RetrieveEvent(string eventId)
        {
            return Submodel.Events.Retrieve(eventId);
        }

        public IResult<ElementContainer<IEvent>> RetrieveEvents()
        {
            return Submodel.Events.RetrieveAll();
        }

        public IResult<IOperation> RetrieveOperation(string operationId)
        {
            return Submodel.Operations.Retrieve(operationId);
        }

        public IResult<ElementContainer<IOperation>> RetrieveOperations()
        {
            return Submodel.Operations.RetrieveAll();
        }

        public IResult<ElementContainer<IDataElement>> RetrieveDataElements()
        {
            return Submodel.DataElements.RetrieveAll();
        }

        public IResult<IDataElement> RetrieveDataElement(string dataElementId)
        {
            return Submodel.DataElements.Retrieve(dataElementId);
        }

        public IResult<IValue> RetrieveDataElementValue(string dataElementId)
        {
            if (dataElementHandler.TryGetValue(dataElementId, out DataElementHandler handler) && handler.GetHandler != null)
            {
                var dataElement = RetrieveDataElement(dataElementId);
                if(dataElement.Success && dataElement.Entity != null)
                    return new Result<IValue>(true, handler.GetHandler.Invoke(dataElement.Entity));
                else
                    return new Result<IValue>(false, new Message(MessageType.Error, "DataElement not found"));
            }
            else
                return new Result<IValue>(false, new Message(MessageType.Error, "DataElementHandler or GetHandler not found"));
        }


        public IResult UpdateDataElementValue(string dataElementId, IValue value)
        {
            if (dataElementHandler.TryGetValue(dataElementId, out DataElementHandler handler) && handler.SetHandler != null)
            {
                var dataElement = RetrieveDataElement(dataElementId);
                if (dataElement.Success && dataElement.Entity != null)
                {
                    handler.SetHandler.Invoke(dataElement.Entity, value);
                    return new Result(true);
                }
                else
                    return new Result<IValue>(false, new Message(MessageType.Error, "DataElement not found"));
            }
            else
                return new Result<IValue>(false, new Message(MessageType.Error, "DataElementHandler or SetHandler not found"));
        }

        public virtual void ConfigureEventHandler(IMessageClient messageClient)
        {
            this.messageClient = messageClient;
        }

        public virtual void SubscribeUpdates(string dataElementId, Action<IValue> updateFunction)
        {
            if (!updateFunctions.ContainsKey(dataElementId))
                updateFunctions.Add(dataElementId, updateFunction);
            else
                updateFunctions[dataElementId] = updateFunction;
        }

        public virtual void PublishUpdate(string dataElementId, IValue value)
        {
            if (updateFunctions.TryGetValue(dataElementId, out Action<IValue> updateFunction))
                updateFunction.Invoke(value);

        }

    }
}
