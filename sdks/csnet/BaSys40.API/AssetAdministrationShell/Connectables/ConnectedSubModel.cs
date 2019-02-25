using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Utils.ResultHandling;
using BaSys40.API.Platform.Agents;
using System.Collections.Generic;
using BaSys40.Models.Core.AssetAdministrationShell.Implementations;
using BaSys40.Utils.Client;
using System;
using System.Reflection;
using System.Linq.Expressions;
using System.Linq;
using Newtonsoft.Json;
using BaSys40.Models.Connectivity;

namespace BaSys40.API.AssetAdministrationShell.Connectables
{
    public class ConnectedSubmodel : IConnectableSubmodel
    {
        public ISubmodel Submodel { get; private set; }
        private IAssetAdministrationShell AssetAdministrationShell { get; }
        private IMessageClient messageClient;

        private readonly ISubmodelAgent submodelServiceImpl;
        private Dictionary<string, Delegate> methodCalledHandler;
        private Dictionary<string, DataElementHandler> dataElementHandler;
        private Dictionary<string, Action<IValue>> updateFunctions;

        public IServiceDescriptor ServiceDescriptor { get; private set; }

        public ConnectedSubmodel(ISubmodelAgent submodelService, IAssetAdministrationShell aas)
        {
            AssetAdministrationShell = aas;
            submodelServiceImpl = submodelService;
            methodCalledHandler = new Dictionary<string, Delegate>();
            dataElementHandler = new Dictionary<string, DataElementHandler>();
            updateFunctions = new Dictionary<string, Action<IValue>>();
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
            return submodelServiceImpl.CreateEvent(AssetAdministrationShell.IdShort, Submodel.IdShort, eventable);
        }

        public IResult<IOperation> CreateOperation(IOperation operation)
        {
            return submodelServiceImpl.CreateOperation(AssetAdministrationShell.IdShort, Submodel.IdShort, operation);
        }

        public IResult<IDataElement> CreateDataElement(IDataElement property)
        {
            return submodelServiceImpl.CreateDataElement(AssetAdministrationShell.IdShort, Submodel.IdShort, property);
        }

        public IResult DeleteEvent(string eventId)
        {
            return submodelServiceImpl.DeleteEvent(AssetAdministrationShell.IdShort, Submodel.IdShort, eventId);
        }

        public IResult DeleteOperation(string operationId)
        {
            return submodelServiceImpl.DeleteOperation(AssetAdministrationShell.IdShort, Submodel.IdShort, operationId);
        }

        public IResult DeleteDataElement(string dataElementId)
        {
            return submodelServiceImpl.DeleteDataElement(AssetAdministrationShell.IdShort, Submodel.IdShort, dataElementId);
        }

        public IResult<IEvent> RetrieveEvent(string eventId)
        {
            return submodelServiceImpl.RetrieveEvent(AssetAdministrationShell.IdShort, Submodel.IdShort, eventId);
        }

        public IResult<ElementContainer<IEvent>> RetrieveEvents()
        {
            return submodelServiceImpl.RetrieveEvents(AssetAdministrationShell.IdShort, Submodel.IdShort);
        }

        public IResult<IOperation> RetrieveOperation(string operationId)
        {
            return submodelServiceImpl.RetrieveOperation(AssetAdministrationShell.IdShort, Submodel.IdShort, operationId);
        }

        public IResult<ElementContainer<IOperation>> RetrieveOperations()
        {
            return submodelServiceImpl.RetrieveOperations(AssetAdministrationShell.IdShort, Submodel.IdShort);   
        }

        public IResult<ElementContainer<IDataElement>> RetrieveDataElements()
        {
            return submodelServiceImpl.RetrieveDataElements(AssetAdministrationShell.IdShort, Submodel.IdShort);
        }

        public IResult<IDataElement> RetrieveDataElement(string dataElementId)
        {
            return submodelServiceImpl.RetrieveDataElement(AssetAdministrationShell.IdShort, Submodel.IdShort, dataElementId);
        }

        public IResult<IValue> RetrieveDataElementValue(string dataElementId)
        {
            return submodelServiceImpl.RetrieveDataElementValue(AssetAdministrationShell.IdShort, Submodel.IdShort, dataElementId);
        }

        public IResult UpdateDataElementValue(string dataElementId, IValue value)
        {
            return submodelServiceImpl.UpdateDataElementValue(AssetAdministrationShell.IdShort, Submodel.IdShort, dataElementId, value);
        }

        public IResult InvokeOperation(string operationId, List<IArgument> inputArguments, List<IArgument> outputArguments, int timeout)
        {
            return submodelServiceImpl.InvokeOperation(AssetAdministrationShell.IdShort, Submodel.IdShort, operationId, inputArguments, outputArguments, timeout);
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

        public IResult ThrowEvent(IPublishableEvent publishableEvent, string topic, Action<IMessagePublishedEventArgs> MessagePublished, byte qosLevel)
        {
            var settings = new JsonSerializerSettings()
            {
                Formatting = Formatting.Indented,
                NullValueHandling = NullValueHandling.Ignore,
            };
            settings.Converters.Add(new Newtonsoft.Json.Converters.StringEnumConverter());

            string message = JsonConvert.SerializeObject(publishableEvent, settings);
            return messageClient.Publish(topic, message, MessagePublished, qosLevel);
        }

        public void ConfigureEventHandler(IMessageClient messageClient)
        {
            this.messageClient = messageClient;
        }
        public Delegate RetrieveMethodDelegate(string operationId)
        {
            if (methodCalledHandler.TryGetValue(operationId, out Delegate handler))
                return handler;
            else
                return null;
        }
        public MethodCalledHandler RetrieveMethodCalledHandler(string operationId)
        {
            if (methodCalledHandler.TryGetValue(operationId, out Delegate handler))
                return (MethodCalledHandler)handler;
            else
                return null;
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
