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
using BaSyx.API.Clients;
using BaSyx.API.Components;
using BaSyx.Models.Core.AssetAdministrationShell.Generics;
using BaSyx.Utils.Client;
using BaSyx.Utils.ResultHandling;
using System;
using BaSyx.Models.Connectivity.Descriptors;
using BaSyx.Models.Core.Common;
using BaSyx.Models.Core.AssetAdministrationShell.Generics.SubmodelElementTypes;

namespace BaSyx.Submodel.ServiceProvider.Distributed
{
    public class DistributedSubmodelServiceProvider : ISubmodelServiceProvider
    {
        public ISubmodel Submodel => GetBinding();

        public ISubmodelDescriptor ServiceDescriptor { get; }

        private ISubmodelClient submodelClient;

        public DistributedSubmodelServiceProvider(ISubmodelClientFactory submodelClientFactory, ISubmodelDescriptor serviceDescriptor)
        {
            ServiceDescriptor = serviceDescriptor;
            submodelClient = submodelClientFactory.CreateSubmodelClient(serviceDescriptor);            
        }

        public void SubscribeUpdates(string propertyId, Action<IValue> updateFunction)
        {
            throw new NotImplementedException();
        }

        public void PublishUpdate(string propertyId, IValue value)
        {
            throw new NotImplementedException();
        }

        public IResult ThrowEvent(IPublishableEvent publishableEvent, string topic, Action<IMessagePublishedEventArgs> MessagePublished, byte qosLevel)
        {
            throw new NotImplementedException();
        }

        public PropertyHandler RetrievePropertyHandler(string propertyId)
        {
            throw new NotImplementedException();
        }

        public void RegisterPropertyHandler(string propertyId, PropertyHandler handler)
        {
            throw new NotImplementedException();
        }

        public Delegate RetrieveMethodDelegate(string operationId)
        {
            throw new NotImplementedException();
        }

        public void RegisterMethodCalledHandler(string operationId, Delegate handler)
        {
            throw new NotImplementedException();
        }

        public void RegisterEventHandler(IMessageClient messageClient)
        {
            throw new NotImplementedException();
        }

        public void BindTo(ISubmodel element)
        {
            throw new NotImplementedException();
        }

        public ISubmodel GetBinding()
        {
            var submodel = RetrieveSubmodel();
            if (submodel.Success && submodel.Entity != null)
                return submodel.Entity;
            return null;
        }
        public IResult<ISubmodel> RetrieveSubmodel()
        {
            return submodelClient.RetrieveSubmodel();
        }

        public IResult<IOperation> CreateOperation(IOperation operation)
        {
            return submodelClient.CreateOperation(operation);
        }

        public IResult<IElementContainer<IOperation>> RetrieveOperations()
        {
            return submodelClient.RetrieveOperations();
        }

        public IResult<IOperation> RetrieveOperation(string operationId)
        {
            return submodelClient.RetrieveOperation(operationId);
        }

        public IResult DeleteOperation(string operationId)
        {
            return submodelClient.DeleteOperation(operationId);
        }

        public IResult InvokeOperation(string operationId, IOperationVariableSet inputArguments, IOperationVariableSet outputArguments, int timeout)
        {
            return submodelClient.InvokeOperation(operationId, inputArguments, outputArguments, timeout);
        }

        public IResult<IProperty> CreateProperty(IProperty Property)
        {
            return submodelClient.CreateProperty(Property);
        }

        public IResult<IElementContainer<IProperty>> RetrieveProperties()
        {
            return submodelClient.RetrieveProperties();
        }

        public IResult<IProperty> RetrieveProperty(string propertyId)
        {
            return submodelClient.RetrieveProperty(propertyId);
        }

        public IResult<IValue> RetrievePropertyValue(string propertyId)
        {
            return submodelClient.RetrievePropertyValue(propertyId);
        }

        public IResult UpdatePropertyValue(string propertyId, IValue value)
        {
            return submodelClient.UpdatePropertyValue(propertyId, value);
        }

        public IResult DeleteProperty(string propertyId)
        {
            return submodelClient.DeleteProperty(propertyId);
        }

        public IResult<IEvent> CreateEvent(IEvent eventable)
        {
            return submodelClient.CreateEvent(eventable);
        }

        public IResult<IElementContainer<IEvent>> RetrieveEvents()
        {
            return submodelClient.RetrieveEvents();
        }

        public IResult<IEvent> RetrieveEvent(string eventId)
        {
            return submodelClient.RetrieveEvent(eventId);
        }

        public IResult DeleteEvent(string eventId)
        {
            return submodelClient.DeleteEvent(eventId);
        }

        public IResult ThrowEvent(IPublishableEvent publishableEvent, string topic, Action<IMessagePublishedEventArgs> MessagePublished, byte qosLevel, bool retain)
        {
            throw new NotImplementedException();
        }

        public void RegisterEventHandler(string eventId, Delegate handler)
        {
            throw new NotImplementedException();
        }

        public void ConfigureEventHandler(IMessageClient messageClient)
        {
            throw new NotImplementedException();
        }

        public void RegisterEventDelegate(string eventId, EventDelegate handler)
        {
            throw new NotImplementedException();
        }

        public IResult<ISubmodelElement> CreateSubmodelElement(ISubmodelElement submodelElement)
        {
            return submodelClient.CreateSubmodelElement(submodelElement);
        }

        public IResult<IElementContainer<ISubmodelElement>> RetrieveSubmodelElements()
        {
            return submodelClient.RetrieveSubmodelElements();
        }

        public IResult<ISubmodelElement> RetrieveSubmodelElement(string submodelElementId)
        {
            return submodelClient.RetrieveSubmodelElement(submodelElementId);
        }

        public IResult<IValue> RetrieveSubmodelElementValue(string submodelElementId)
        {
            return submodelClient.RetrieveSubmodelElementValue(submodelElementId);
        }

        public IResult UpdateSubmodelElement(string submodelElementId, ISubmodelElement submodelElement)
        {
            return submodelClient.UpdateSubmodelElement(submodelElementId, submodelElement);
        }

        public IResult DeleteSubmodelElement(string submodelElementId)
        {
            return submodelClient.DeleteSubmodelElement(submodelElementId);
        }
    }
}
