/*******************************************************************************
* Copyright (c) 2020 Robert Bosch GmbH
* Author: Constantin Ziesche (constantin.ziesche@bosch.com)
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License 2.0 which is available at
* http://www.eclipse.org/legal/epl-2.0
*
* SPDX-License-Identifier: EPL-2.0
*******************************************************************************/
using BaSyx.API.AssetAdministrationShell;
using BaSyx.Models.Core.AssetAdministrationShell.Generics;
using BaSyx.Models.Core.AssetAdministrationShell.Implementations;
using BaSyx.Utils.ResultHandling;
using BaSyx.Utils.Client;
using System.Collections.Generic;
using System;
using Newtonsoft.Json;
using System.Reflection;
using System.Linq.Expressions;
using System.Linq;
using BaSyx.Models.Connectivity.Descriptors;
using BaSyx.Models.Core.Common;
using BaSyx.Models.Core.AssetAdministrationShell.Generics.SubmodelElementTypes;
using BaSyx.Models.Communication;
using System.Threading.Tasks;
using BaSyx.Models.Extensions;

namespace BaSyx.API.Components
{
    public class SubmodelServiceProvider : ISubmodelServiceProvider
    {
        public ISubmodel Submodel { get; protected set; }
        public ISubmodelDescriptor ServiceDescriptor { get; internal set; }

        private readonly Dictionary<string, Delegate> methodCalledHandler;
        private readonly Dictionary<string, PropertyHandler> propertyHandler;
        private readonly Dictionary<string, Action<IValue>> updateFunctions;
        private readonly Dictionary<string, EventDelegate> eventDelegates;
        private readonly Dictionary<string, InvocationResponse> invocationResults;

        private IMessageClient messageClient;

        private readonly GetPropertyValueHandler GenericPropertyGetHandler = de => { return new ElementValue(de.Value, de.ValueType); };
        private readonly SetPropertyValueHandler GenericPropertySetHandler = (de, val) => { de.Value = val.Value; };

        public SubmodelServiceProvider()
        {
            methodCalledHandler = new Dictionary<string, Delegate>();
            propertyHandler = new Dictionary<string, PropertyHandler>();
            updateFunctions = new Dictionary<string, Action<IValue>>();
            eventDelegates = new Dictionary<string, EventDelegate>();
            invocationResults = new Dictionary<string, InvocationResponse>();
        }

        public SubmodelServiceProvider(ISubmodel submodel) : this()
        {
            BindTo(submodel);
        }
        public SubmodelServiceProvider(ISubmodel submodel, ISubmodelDescriptor submodelDescriptor) : this()
        {
            Submodel = submodel;
            ServiceDescriptor = submodelDescriptor;
        }

        public void BindTo(ISubmodel element)
        {
            Submodel = element;
            ServiceDescriptor = new SubmodelDescriptor(element, null);
        }
        public ISubmodel GetBinding()
        {
            return Submodel;
        }

        public void UseInMemoryPropertyHandler()
        {
            if(Submodel.Properties?.Count() > 0)
                foreach (var property in Submodel.Properties)
                {
                    RegisterPropertyHandler(property.IdShort,
                        new PropertyHandler(property.Get ?? GenericPropertyGetHandler, property.Set ?? GenericPropertySetHandler));
                }
        }

        public void UsePropertyHandler(PropertyHandler propertyHandler)
        {
            if (Submodel.Properties?.Count() > 0)
                foreach (var property in Submodel.Properties)
                    RegisterPropertyHandler(property.IdShort, propertyHandler);
        }

        public void UseOperationHandler(MethodCalledHandler methodCalledHandler)
        {
            if (Submodel.Operations?.Count() > 0)
                foreach (var operation in Submodel.Operations)
                    RegisterMethodCalledHandler(operation.IdShort, methodCalledHandler);
        }

        public IResult<IEvent> CreateEvent(IEvent eventable)
        {
            if (Submodel == null)
                return new Result<IEvent>(false, new NotFoundMessage("Submodel"));

            return Submodel.SubmodelElements.Create(eventable);
        }

        public IResult<IOperation> CreateOperation(IOperation operation)
        {
            if (Submodel == null)
                return new Result<IOperation>(false, new NotFoundMessage("Submodel"));

            return Submodel.SubmodelElements.Create(operation);
        }

        public IResult<IProperty> CreateProperty(IProperty property)
        {
            if (Submodel == null)
                return new Result<IProperty>(false, new NotFoundMessage("Submodel"));

            RegisterPropertyHandler(property.IdShort, 
                new PropertyHandler(property.Get ?? GenericPropertyGetHandler, property.Set ?? GenericPropertySetHandler));

            return Submodel.SubmodelElements.Create(property);
        }

        public IResult DeleteEvent(string eventId)
        {
            if (Submodel == null)
                return new Result(false, new NotFoundMessage("Submodel"));

            if (Submodel.Events == null)
                return new Result(false, new NotFoundMessage(eventId));
            return Submodel.SubmodelElements.Delete(eventId);
        }

        public IResult DeleteOperation(string operationId)
        {
            if (Submodel == null)
                return new Result(false, new NotFoundMessage("Submodel"));

            if (Submodel.Operations == null)
                return new Result(false, new NotFoundMessage(operationId));
            return Submodel.SubmodelElements.Delete(operationId);
        }

        public IResult DeleteProperty(string propertyId)
        {
            if (Submodel == null)
                return new Result(false, new NotFoundMessage("Submodel"));

            if (Submodel.Properties == null)
                return new Result(false, new NotFoundMessage(propertyId));

            if (propertyHandler.ContainsKey(propertyId))
                propertyHandler.Remove(propertyId);

            return Submodel.SubmodelElements.Delete(propertyId);
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

        public PropertyHandler RetrievePropertyHandler(string propertyId)
        {
            if (propertyHandler.TryGetValue(propertyId, out PropertyHandler handler))
                return handler;
            else
                return null;
        }

        public void RegisterPropertyHandler(string propertyId, PropertyHandler handler)
        {
            if (!propertyHandler.ContainsKey(propertyId))
                propertyHandler.Add(propertyId, handler);
            else
                propertyHandler[propertyId] = handler;
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

        public void RegisterEventDelegate(string eventId, EventDelegate eventDelegate)
        {
            if (!eventDelegates.ContainsKey(eventId))
                eventDelegates.Add(eventId, eventDelegate);
            else
                eventDelegates[eventId] = eventDelegate;
        }

        public void RegisterMethodCalledHandler(string operationId, MethodInfo methodInfo, object target)
        {
            var parameters = from parameter in methodInfo.GetParameters() select parameter.ParameterType;
            Delegate del = methodInfo.CreateDelegate(Expression.GetDelegateType(parameters.Concat(new[] { methodInfo.ReturnType }).ToArray()), target);
            RegisterMethodCalledHandler(operationId, del);
        }

        public IResult<CallbackResponse> InvokeOperationAsync(string operationId, InvocationRequest invocationRequest)
        {
            if (Submodel == null)
                return new Result<CallbackResponse>(false, new NotFoundMessage("Submodel"));
            if (invocationRequest == null)
                return new Result<CallbackResponse>(new ArgumentNullException(nameof(invocationRequest)));

            var operation_Retrieved = RetrieveOperation(operationId);
            if (operation_Retrieved.Success && operation_Retrieved.Entity != null)
            {
                Delegate methodDelegate;
                if (operation_Retrieved.Entity.OnMethodCalled != null)
                    methodDelegate = operation_Retrieved.Entity.OnMethodCalled;
                else if (methodCalledHandler.TryGetValue(operationId, out Delegate handler))
                    methodDelegate = handler;
                else
                    return new Result<CallbackResponse>(false, new NotFoundMessage($"MethodHandler for {operationId}"));

                var invocationTask = Task.Run(() =>
                {
                    InvocationResponse invocationResponse = new InvocationResponse(invocationRequest.RequestId);
                    SetInvocationResult(operationId, invocationRequest.RequestId, ref invocationResponse);

                    try
                    {
                        invocationResponse.ExecutionState = ExecutionState.Running;
                        Task<OperationResult> taskResult = (Task<OperationResult>)methodDelegate.DynamicInvoke(operation_Retrieved.Entity, invocationRequest.InputArguments, invocationResponse.OutputArguments);
                        invocationResponse.OperationResult = taskResult.Result;
                        invocationResponse.ExecutionState = ExecutionState.Completed;
                    }
                    catch (Exception e)
                    {
                        invocationResponse.ExecutionState = ExecutionState.Failed;
                        invocationResponse.OperationResult = new OperationResult(e);
                    }
                });
                string endpoint = ServiceDescriptor?.Endpoints?.FirstOrDefault()?.Address;
                CallbackResponse callbackResponse = new CallbackResponse(invocationRequest.RequestId);
                if (string.IsNullOrEmpty(endpoint))
                    callbackResponse.CallbackUrl = new Uri($"/operations/{operationId}/invocationList/{invocationRequest.RequestId}", UriKind.Relative);
                else
                    callbackResponse.CallbackUrl = new Uri($"{endpoint}/operations/{operationId}/invocationList/{invocationRequest.RequestId}", UriKind.Absolute);
                return new Result<CallbackResponse>(true, callbackResponse);
            }
            return new Result<CallbackResponse>(operation_Retrieved);
        }

        private void SetInvocationResult(string operationId, string requestId, ref InvocationResponse invocationResponse)
        {
            string key = string.Join("_", operationId, requestId);
            if (invocationResults.ContainsKey(key))
            {
                invocationResults[key] = invocationResponse;
            }
            else
            {
                invocationResults.Add(key, invocationResponse);
            }
        }

        public IResult<InvocationResponse> GetInvocationResult(string operationId, string requestId)
        {
            string key = string.Join("_", operationId, requestId);
            if (invocationResults.ContainsKey(key))
            {
                return new Result<InvocationResponse>(true, invocationResults[key]);
            }
            else
            {
               return new Result<InvocationResponse>(false, new NotFoundMessage($"Request with id {requestId}"));
            }
        }


        public IResult<InvocationResponse> InvokeOperation(string operationId, InvocationRequest invocationRequest)
        {
            if (Submodel == null)
                return new Result<InvocationResponse>(false, new NotFoundMessage("Submodel"));

            var operation_Retrieved = RetrieveOperation(operationId);
            if (operation_Retrieved.Success && operation_Retrieved.Entity != null)
            {
                Delegate methodDelegate;
                if (operation_Retrieved.Entity.OnMethodCalled != null)
                    methodDelegate = operation_Retrieved.Entity.OnMethodCalled;
                else if (methodCalledHandler.TryGetValue(operationId, out Delegate handler))
                    methodDelegate = handler;
                else
                    return new Result<InvocationResponse>(false, new NotFoundMessage($"MethodHandler for {operationId}"));
                try
                {
                    InvocationResponse invocationResponse = new InvocationResponse(invocationRequest.RequestId);
                    invocationResponse.ExecutionState = ExecutionState.Running;
                    Task<OperationResult> taskResult = (Task<OperationResult>)methodDelegate.DynamicInvoke(operation_Retrieved.Entity, invocationRequest.InputArguments, invocationResponse.OutputArguments);
                    invocationResponse.OperationResult = taskResult.Result;
                    invocationResponse.ExecutionState = ExecutionState.Completed;

                    return new Result<InvocationResponse>(true, invocationResponse);
                }
                catch (Exception e)
                {
                    return new Result<InvocationResponse>(e);
                }
            }
            return new Result<InvocationResponse>(operation_Retrieved);
        }

        public IResult ThrowEvent(IPublishableEvent publishableEvent, string topic = "/", Action<IMessagePublishedEventArgs> MessagePublished = null, byte qosLevel = 2, bool retain = false)
        {
            if (messageClient == null || !messageClient.IsConnected)
                return new Result(false, new Message(MessageType.Warning, "MessageClient is not initialized or not connected"));

            if (publishableEvent == null)
                return new Result(new ArgumentNullException(nameof(publishableEvent)));

            if (eventDelegates.TryGetValue(publishableEvent.Name, out EventDelegate eventDelegate))
                eventDelegate.Invoke(this, publishableEvent);

            string message = JsonConvert.SerializeObject(publishableEvent, Formatting.Indented);
            return messageClient.Publish(topic, message, MessagePublished, qosLevel, retain);
        }

        public IResult<IEvent> RetrieveEvent(string eventId)
        {
            if (Submodel == null)
                return new Result<IEvent>(false, new NotFoundMessage("Submodel"));

            if (Submodel.Events == null)
                return new Result<IEvent>(false, new NotFoundMessage(eventId));
            return Submodel.SubmodelElements.Retrieve<IEvent>(eventId);
        }

        public IResult<IElementContainer<IEvent>> RetrieveEvents()
        {
            if (Submodel == null)
                return new Result<ElementContainer<IEvent>>(false, new NotFoundMessage("Submodel"));

            if(Submodel.Events == null)
                return new Result<ElementContainer<IEvent>>(false, new NotFoundMessage("Events"));
            return Submodel.SubmodelElements.RetrieveAll<IEvent>();
        }

        public IResult<IOperation> RetrieveOperation(string operationId)
        {
            if (Submodel == null)
                return new Result<IOperation>(false, new NotFoundMessage("Submodel"));

            if (Submodel.Operations == null)
                return new Result<IOperation>(false, new NotFoundMessage(operationId));
            return Submodel.SubmodelElements.Retrieve<IOperation>(operationId);
        }

        public IResult<IElementContainer<IOperation>> RetrieveOperations()
        {
            if (Submodel == null)
                return new Result<ElementContainer<IOperation>>(false, new NotFoundMessage("Submodel"));

            if (Submodel.Operations == null)
                return new Result<ElementContainer<IOperation>>(false, new NotFoundMessage("Operations"));
            return Submodel.SubmodelElements.RetrieveAll<IOperation>();
        }

        public IResult<IElementContainer<IProperty>> RetrieveProperties()
        {
            if (Submodel == null)
                return new Result<ElementContainer<IProperty>>(false, new NotFoundMessage("Submodel"));

            if (Submodel.Properties == null)
                return new Result<ElementContainer<IProperty>>(false, new NotFoundMessage("Properties"));
            return Submodel.SubmodelElements.RetrieveAll<IProperty>();
        }

        public IResult<IProperty> RetrieveProperty(string propertyId)
        {
            if (Submodel == null)
                return new Result<IProperty>(false, new NotFoundMessage("Submodel"));

            if (Submodel.Properties == null)
                return new Result<IProperty>(false, new NotFoundMessage(propertyId));
            return Submodel.SubmodelElements.Retrieve<IProperty>(propertyId);
        }

        public IResult<IValue> RetrievePropertyValue(string propertyId)
        {
            if (Submodel == null)
                return new Result<IValue>(false, new NotFoundMessage("Submodel"));

            if (propertyHandler.TryGetValue(propertyId, out PropertyHandler handler) && handler.GetHandler != null)
            {
                var property = RetrieveProperty(propertyId);
                if(property.Success && property.Entity != null)
                    return new Result<IValue>(true, handler.GetHandler.Invoke(property.Entity));
                else
                    return new Result<IValue>(false, new Message(MessageType.Error, "property not found"));
            }
            else
                return new Result<IValue>(false, new Message(MessageType.Error, "propertyHandler or GetHandler not found"));
        }


        public IResult UpdatePropertyValue(string propertyId, IValue value)
        {
            if (Submodel == null)
                return new Result(false, new NotFoundMessage("Submodel"));

            if (propertyHandler.TryGetValue(propertyId, out PropertyHandler handler) && handler.SetHandler != null)
            {
                var property = RetrieveProperty(propertyId);
                if (property.Success && property.Entity != null)
                {
                    handler.SetHandler.Invoke(property.Entity, value);
                    return new Result(true);
                }
                else
                    return new Result<IValue>(false, new Message(MessageType.Error, "property not found"));
            }
            else
                return new Result<IValue>(false, new Message(MessageType.Error, "propertyHandler or SetHandler not found"));
        }

        public virtual void ConfigureEventHandler(IMessageClient messageClient)
        {
            this.messageClient = messageClient;
        }

        public virtual void SubscribeUpdates(string propertyId, Action<IValue> updateFunction)
        {
            if (!updateFunctions.ContainsKey(propertyId))
                updateFunctions.Add(propertyId, updateFunction);
            else
                updateFunctions[propertyId] = updateFunction;
        }

        public virtual void PublishUpdate(string propertyId, IValue value)
        {
            if (updateFunctions.TryGetValue(propertyId, out Action<IValue> updateFunction))
                updateFunction.Invoke(value);

        }

        public IResult<ISubmodel> RetrieveSubmodel()
        {
            var submodel = GetBinding();
            return new Result<ISubmodel>(submodel != null, submodel);
        }

        public IResult<ISubmodelElement> CreateSubmodelElement(ISubmodelElement submodelElement)
        {
            if (Submodel == null)
                return new Result<ISubmodelElement>(false, new NotFoundMessage("Submodel"));

            return Submodel.SubmodelElements.Create(submodelElement);
        }

        public IResult<IElementContainer<ISubmodelElement>> RetrieveSubmodelElements()
        {
            if (Submodel == null)
                return new Result<ElementContainer<ISubmodelElement>>(false, new NotFoundMessage("Submodel"));

            if (Submodel.SubmodelElements == null)
                return new Result<ElementContainer<ISubmodelElement>>(false, new NotFoundMessage("SubmodelElements"));
            return Submodel.SubmodelElements.RetrieveAll();
        }

        public IResult<ISubmodelElement> RetrieveSubmodelElement(string submodelElementId)
        {
            if (Submodel == null)
                return new Result<ISubmodelElement>(false, new NotFoundMessage("Submodel"));

            if (Submodel.SubmodelElements == null)
                return new Result<ISubmodelElement>(false, new NotFoundMessage(submodelElementId));

            IResult<ISubmodelElement> result;
            if(submodelElementId.Contains("/"))
            {
                string[] smeIds = submodelElementId.Split(new char[] { '/' }, StringSplitOptions.RemoveEmptyEntries);
                IResult<ISubmodelElement> firstElement = Submodel.SubmodelElements.Retrieve(smeIds[0]);
                result = RetrieveSubordinateElement(firstElement, smeIds.Skip(1));
            }
            else
            {
                result = Submodel.SubmodelElements.Retrieve(submodelElementId);
            }
            return result;
        }

        private IResult<ISubmodelElement> RetrieveSubordinateElement(IResult<ISubmodelElement> smElement, IEnumerable<string> idShorts)
        {
            if(smElement.Success && smElement.Entity != null)
            {
                if(smElement.Entity.ModelType == ModelType.SubmodelElementCollection)
                {
                    ISubmodelElementCollection smeCollection = smElement.Entity.Cast<ISubmodelElementCollection>();
                    if(idShorts?.Count() > 0 && smeCollection.Value?.Count > 0)
                    {
                        IResult<ISubmodelElement> nextElement = smeCollection.Value.Retrieve(idShorts.First());
                        if (idShorts.Count() > 1)
                            return RetrieveSubordinateElement(nextElement, idShorts.Skip(1));
                        else
                            return nextElement;
                    }
                }
                else
                {
                    if (idShorts.Count() > 0)
                        return new Result<ISubmodelElement>(false, new NotFoundMessage(string.Join("/", idShorts)));
                }
            }
            return smElement;
        }

        public IResult<IValue> RetrieveSubmodelElementValue(string submodelElementId)
        {
            if (Submodel == null)
                return new Result<IValue>(false, new NotFoundMessage("Submodel"));

            if (Submodel.SubmodelElements == null)
                return new Result<IValue>(false, new NotFoundMessage(submodelElementId));

            return new Result<IValue>(true, 
                new ElementValue(
                    (Submodel.SubmodelElements[submodelElementId] as dynamic).Value, 
                    (Submodel.SubmodelElements[submodelElementId] as dynamic).ValueType as DataType));
        }

        public IResult UpdateSubmodelElement(string submodelElementId, ISubmodelElement submodelElement)
        {
            if (Submodel == null)
                return new Result(false, new NotFoundMessage("Submodel"));

            if (Submodel.SubmodelElements == null)
                return new Result(false, new NotFoundMessage(submodelElementId));

            return Submodel.SubmodelElements.Update(submodelElementId, submodelElement);
        }

        public IResult DeleteSubmodelElement(string submodelElementId)
        {
            if (Submodel == null)
                return new Result(false, new NotFoundMessage("Submodel"));

            if (Submodel.SubmodelElements == null)
                return new Result(false, new NotFoundMessage(submodelElementId));

            return Submodel.SubmodelElements.Delete(submodelElementId);
        }        
    }
}
