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
using Microsoft.AspNetCore.Mvc;
using BaSyx.Models.Core.AssetAdministrationShell.Generics;
using BaSyx.Utils.ResultHandling;
using BaSyx.API.Components;
using BaSyx.Utils.Client;
using System;
using BaSyx.API.AssetAdministrationShell;
using Newtonsoft.Json.Linq;
using BaSyx.Models.Extensions;
using BaSyx.Models.Core.AssetAdministrationShell.Generics.SubmodelElementTypes;
using BaSyx.Models.Core.AssetAdministrationShell.Implementations.SubmodelElementTypes;
using BaSyx.Models.Connectivity.Descriptors;
using BaSyx.Models.Core.Common;
using BaSyx.Models.Communication;
using BaSyx.Models.Core.AssetAdministrationShell.Implementations;

namespace BaSyx.API.Http.Controllers
{
    /// <summary>
    /// All Asset Administration Shell Services provided by the component
    /// </summary>
    public class SubmodelServices : Controller, ISubmodelServiceProvider
    {
        private readonly ISubmodelServiceProvider submodelServiceProvider;

        public ISubmodel Submodel => submodelServiceProvider?.GetBinding();
        public ISubmodelDescriptor ServiceDescriptor { get; }

        public SubmodelServices(ISubmodelServiceProvider submodelServiceProvider)
        {
            this.submodelServiceProvider = submodelServiceProvider;
            ServiceDescriptor = submodelServiceProvider?.ServiceDescriptor;
        }

        public void BindTo(ISubmodel element)
        {
            submodelServiceProvider.BindTo(element);
        }
        public ISubmodel GetBinding()
        {
            return submodelServiceProvider.GetBinding();
        }


        #region REST-Interface Submodel

        /// <summary>
        /// Retrieves a customizable table version of a Submodel
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Success</response>
        /// <response code="404">Submodel not found</response>       
        [HttpGet("submodel/table", Name = "GetSubmodelAsTable")]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetSubmodelAsTable([FromQuery] string columns)
        {
            var result = RetrieveSubmodel();

            if (result != null && result.Entity != null)
            {
                string[] columnNames = columns.Split(new char[] { ';' }, StringSplitOptions.RemoveEmptyEntries);
                JToken customizedSubmodel = result.Entity.CustomizeSubmodel(columnNames);
                return new JsonResult(customizedSubmodel);
            }

            return result.CreateActionResult(CrudOperation.Retrieve);
        }


        /// <summary>
        /// Retrieves the minimized version of a Submodel
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Success</response>
        /// <response code="404">Submodel not found</response>       
        [HttpGet("submodel/values", Name = "GetSubmodelValues")]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetMinimizedSubmodel()
        {
            var result = RetrieveSubmodel();

            if (result != null && result.Entity != null)
            {
                JObject minimizedSubmodel = result.Entity.MinimizeSubmodel();
                return new JsonResult(minimizedSubmodel);
            }

            return result.CreateActionResult(CrudOperation.Retrieve);
        }

        /// <summary>
        /// Retrieves the Submodel
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Success</response>
        /// <response code="404">Submodel not found</response>       
        [HttpGet("submodel", Name = "GetSubmodel")]
        [ProducesResponseType(typeof(BaSyx.Models.Core.AssetAdministrationShell.Implementations.Submodel), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetSubmodel()
        {
            var result = RetrieveSubmodel();
            return result.CreateActionResult(CrudOperation.Retrieve);
        }

        /// <summary>
        /// Retrieves all SubmodelElements from the current Submodel
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Returns a list of found SubmodelElements</response>
        /// <response code="404">Submodel not found / No SubmodelElements found</response>       
        [HttpGet("submodel/submodelElements", Name = "GetSubmodelElements")]
        [ProducesResponseType(typeof(Property[]), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetSubmodelElements()
        {
            var result = RetrieveSubmodelElements();
            return result.CreateActionResult(CrudOperation.Retrieve);
        }

        /// <summary>
        /// Retrieves all Properties from the current Submodel
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Returns a list of found Properties</response>
        /// <response code="404">Submodel not found / No Properties found</response>       
        [HttpGet("submodel/properties", Name = "GetProperties")]
        [ProducesResponseType(typeof(Property[]), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetProperties()
        {
            var result = RetrieveProperties();
            return result.CreateActionResult(CrudOperation.Retrieve);
        }

        /// <summary>
        /// Retrieves all Operations from the current Submodel
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Success</response>
        /// <response code="404">Submodel not found / No Operations found</response>      
        [HttpGet("submodel/operations", Name = "GetOperations")]
        [ProducesResponseType(typeof(Operation[]), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetOperations()
        {
            var result = RetrieveOperations();
            return result.CreateActionResult(CrudOperation.Retrieve);
        }

        /// <summary>
        /// Retrieves all Events from the current Submodel
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Success</response>
        /// <response code="404">Submodel not found / No Events found</response>      
        [HttpGet("submodel/events", Name = "GetEvents")]
        [ProducesResponseType(typeof(Event[]), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetEvents()
        {
            var result = RetrieveEvents();
            return result.CreateActionResult(CrudOperation.Retrieve);
        }


        #region SubmodelElement - REST-Calls
        /// <summary>
        /// Adds a new Submodel Element to the Submodel
        /// </summary>
        /// <param name="submodelElement">The Submodel Element's description object</param>
        /// <returns></returns>
        /// <response code="201">Submodel Element created successfully</response>
        /// <response code="400">Bad Request</response>
        /// <response code="404">Submodel not found</response>
        [HttpPost("submodel/submodelElements", Name = "PostSubmodelElement")]
        [ProducesResponseType(typeof(Property), 201)]
        [ProducesResponseType(typeof(Result), 400)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult PostSubmodelElement([FromBody] ISubmodelElement submodelElement)
        {
            var result = CreateSubmodelElement(submodelElement);
            return result.CreateActionResult(CrudOperation.Create, "submodel/submodelElements/" + submodelElement.IdShort);
        }
        /// <summary>
        /// Retrieves a specific Submodel Element from the Submodel
        /// </summary>
        /// <param name="submodelElementId">The Submodel Element's short id</param>
        /// <returns></returns>
        /// <response code="200">Returns the requested Submodel Element</response>
        /// <response code="404">Submodel/Submodel Element not found</response>     
        [HttpGet("submodel/submodelElements/{submodelElementId}", Name = "GetSubmodelElementByIdShort")]
        [ProducesResponseType(typeof(Property), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetSubmodelElementByIdShort(string submodelElementId)
        {
            var result = RetrieveSubmodelElement(submodelElementId);
            return result.CreateActionResult(CrudOperation.Retrieve);
        }

        /// <summary>
        /// Deletes a specific Submodel Element from the Submodel
        /// </summary>
        /// <param name="submodelElementId">The Submodel Element's short id</param>
        /// <returns></returns>
        /// <response code="204">Submodel Element deleted successfully</response>
        /// <response code="404">Submodel/Submodel Element not found</response>
        [HttpDelete("submodel/submodelElements/{submodelElementId}", Name = "DeleteSubmodelElementByIdShort")]
        [ProducesResponseType(typeof(IResult), 200)]
        public IActionResult DeleteSubmodelElementByIdShort(string submodelElementId)
        {
            var result = DeleteSubmodelElement(submodelElementId);
            return result.CreateActionResult(CrudOperation.Delete);
        }
        #endregion
        #region Property - REST-Calls
        /// <summary>
        /// Adds a new Property to the Asset Administration Shell's Submodel
        /// </summary>
        /// <param name="property">The Property's description object</param>
        /// <returns></returns>
        /// <response code="201">Property created successfully</response>
        /// <response code="400">Bad Request</response>
        /// <response code="404">Submodel not found</response>
        [HttpPost("submodel/properties", Name = "PostProperty")]
        [ProducesResponseType(typeof(Property), 201)]
        [ProducesResponseType(typeof(Result), 400)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult PostProperty([FromBody] IProperty property)
        {
            var result = CreateProperty(property);
            return result.CreateActionResult(CrudOperation.Create, "submodel/properties/" + property.IdShort);
        }
        /// <summary>
        /// Retrieves a specific Property from the Asset Administrations's Submodel
        /// </summary>
        /// <param name="propertyId">The Property's short id</param>
        /// <returns></returns>
        /// <response code="200">Returns the requested Property</response>
        /// <response code="404">Submodel/Property not found</response>     
        [HttpGet("submodel/properties/{propertyId}", Name = "GetProperty")]
        [ProducesResponseType(typeof(Property), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetProperty(string propertyId)
        {
            var result = RetrieveProperty(propertyId);
            return result.CreateActionResult(CrudOperation.Retrieve);
        }

        /// <summary>
        /// Retrieves the value of a specific Property from the Asset Administrations Shell's Submodel
        /// </summary>
        /// <param name="propertyId">The Property's short id</param>
        /// <returns></returns>
        /// <response code="200">Returns the requested Property's value</response>
        /// <response code="404">Submodel/Property not found</response>     
        [HttpGet("submodel/properties/{propertyId}/value", Name = "GetPropertyValue")]
        [ProducesResponseType(typeof(ElementValue), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetPropertyValue(string propertyId)
        {
            var result = RetrievePropertyValue(propertyId);
            return result.CreateActionResult(CrudOperation.Retrieve);
        }

        /// <summary>
        /// Updates the Asset Administration Shell's Submodel's Property
        /// </summary>
        /// <param name="propertyId">The Property's short id</param>
        /// <param name="value">The new value</param>
        /// <returns></returns>
        /// <response code="200">Property's value changed successfully</response>
        /// <response code="404">Submodel/Property not found</response>     
        [HttpPut("submodel/properties/{propertyId}/value", Name = "PutPropertyValue")]
        [ProducesResponseType(typeof(ElementValue), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult PutPropertyValue(string propertyId, [FromBody] IValue value)
        {
            var result = UpdatePropertyValue(propertyId, value);
            return result.CreateActionResult(CrudOperation.Update);
        }
        /// <summary>
        /// Deletes a specific Property from the Asset Administration Shell's Submodel
        /// </summary>
        /// <param name="propertyId">The Property's short id</param>
        /// <returns></returns>
        /// <response code="204">Property deleted successfully</response>
        /// <response code="404">Submodel not found</response>
        [HttpDelete("submodel/properties/{propertyId}", Name = "DelProperty")]
        [ProducesResponseType(typeof(IResult), 200)]
        public IActionResult DelProperty(string propertyId)
        {
            var result = DeleteProperty(propertyId);
            return result.CreateActionResult(CrudOperation.Delete);
        }
        #endregion
        #region Operation - REST-Calls
        /// <summary>
        /// Adds a new operation to the Asset Administraiton Shell's Submodel
        /// </summary>
        /// <param name="operation">The operation description object</param>
        /// <returns></returns>
        /// <response code="201">Operation created successfully</response>
        /// <response code="400">Bad Request</response>
        /// <response code="404">Submodel not found</response>
        [HttpPost("submodel/operations", Name = "PostOperation")]
        [ProducesResponseType(typeof(Operation), 201)]
        [ProducesResponseType(typeof(Result), 400)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult PostOperation([FromBody] IOperation operation)
        {
            var result = CreateOperation(operation);
            return result.CreateActionResult(CrudOperation.Create, "submodel/operations/" + operation.IdShort);
        }
        /// <summary>
        /// Retrieves a specific Operation from the Asset Administration Shell's Submodel
        /// </summary>
        /// <param name="operationId">The Operation's short id</param>
        /// <returns></returns>
        /// <response code="200">Success</response>
        /// <response code="404">Submodel/Operation not found</response>     
        [HttpGet("submodel/operations/{operationId}", Name = "GetOperation")]
        [ProducesResponseType(typeof(Operation), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetOperation(string operationId)
        {
            var result = RetrieveOperation(operationId);
            return result.CreateActionResult(CrudOperation.Retrieve);
        }
        /// <summary>
        /// Deletes a specific Operation from the Asset Administration Shell's Submodel
        /// </summary>
        /// <param name="operationId">The Operation's short id</param>
        /// <returns></returns>
        /// <response code="204">Operation deleted successfully</response>  
        /// <response code="404">Submodel not found</response>
        [HttpDelete("submodel/operations/{operationId}", Name = "DelOperation")]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult DelOperation(string operationId)
        {
            var result = DeleteOperation(operationId);
            return result.CreateActionResult(CrudOperation.Delete);
        }
        /// <summary>
        /// Synchronously invokes a specific operation from the Submodel
        /// </summary>
        /// <param name="operationId">The operation's short id</param>
        /// <param name="invocationRequest">The parameterized request object for the invocation</param>
        /// <returns></returns>
        /// <response code="200">Operation invoked successfully</response>
        /// <response code="400">Bad Request</response>
        /// <response code="404">Submodel / Method handler not found</response>
        [HttpPost("submodel/operations/{operationId}", Name = "InvokeOperationRest")]
        [ProducesResponseType(typeof(InvocationResponse), 200)]
        [ProducesResponseType(typeof(Result), 400)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult InvokeOperationRest(string operationId, [FromBody] InvocationRequest invocationRequest)
        {
            IResult<InvocationResponse> result = InvokeOperation(operationId, invocationRequest);
            return result.CreateActionResult(CrudOperation.Invoke);
        }

        /// <summary>
        /// Asynchronously invokes a specific operation from the Submodel
        /// </summary>
        /// <param name="operationId">The operation's short id</param>
        /// <param name="invocationRequest">The parameterized request object for the invocation</param>
        /// <returns></returns>
        /// <response code="200">Operation invoked successfully</response>
        /// <response code="400">Bad Request</response>
        /// <response code="404">Submodel / Method handler not found</response>
        [HttpPost("submodel/operations/{operationId}/async", Name = "InvokeOperationRestAsync")]
        [ProducesResponseType(typeof(CallbackResponse), 200)]
        [ProducesResponseType(typeof(Result), 400)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult InvokeOperationRestAsync(string operationId, [FromBody] InvocationRequest invocationRequest)
        {
            IResult<CallbackResponse> result = InvokeOperationAsync(operationId, invocationRequest);
            return result.CreateActionResult(CrudOperation.Invoke);
        }

        /// <summary>
        /// Retrieves the result of an asynchronously started operation
        /// </summary>
        /// <param name="operationId">The operation's short id</param>
        /// <param name="requestId">The request id</param>
        /// <returns></returns>
        /// <response code="200">Result found</response>
        /// <response code="400">Bad Request</response>
        /// <response code="404">Submodel / Operation / Request not found</response>
        [HttpGet("submodel/operations/{operationId}/invocationList/{requestId}", Name = "GetInvocationResultRest")]
        [ProducesResponseType(typeof(InvocationResponse), 200)]
        [ProducesResponseType(typeof(Result), 400)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetInvocationResultRest(string operationId, string requestId)
        {
            IResult<InvocationResponse> result = GetInvocationResult(operationId, requestId);
            return result.CreateActionResult(CrudOperation.Invoke);
        }

        #endregion
        #region Event - REST-Calls
        /// <summary>
        /// Adds a new event to the Asset Administration Shell's Submodel
        /// </summary>
        /// <param name="eventable">The Event description object</param>
        /// <returns></returns>
        /// <response code="201">Event created successfully</response>
        /// <response code="400">Bad Request</response>
        /// <response code="404">Submodel not found</response>
        [HttpPost("submodel/events", Name = "PostEvent")]
        [ProducesResponseType(typeof(Event), 201)]
        [ProducesResponseType(typeof(Result), 400)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult PostEvent([FromBody] IEvent eventable)
        {
            var result = CreateEvent(eventable);
            return result.CreateActionResult(CrudOperation.Create, "submodel/events/" + eventable.IdShort);
        }
        /// <summary>
        /// Retrieves a specific event from the Asset Administration Shell's Submodel
        /// </summary>
        /// <param name="eventId">The Event's short id</param>
        /// <returns></returns>
        /// <response code="200">Success</response>
        /// <response code="404">Submodel/Event not found</response>     
        [HttpGet("submodel/events/{eventId}", Name = "GetEvent")]
        [ProducesResponseType(typeof(Event), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetEvent(string eventId)
        {
            var result = RetrieveEvent(eventId);
            return result.CreateActionResult(CrudOperation.Retrieve);
        }
        /// <summary>
        /// Deletes a specific event from the Asset Administration Shell's Submodel
        /// </summary>
        /// <param name="eventId">The Event's short id</param>
        /// <returns></returns>
        /// <response code="204">Event deleted successfully</response>
        /// <response code="404">Submodel not found</response>
        [HttpDelete("submodel/events/{eventId}", Name = "DelEvent")]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult DelEvent(string eventId)
        {
            var result = DeleteEvent(eventId);
            return result.CreateActionResult(CrudOperation.Delete);
        }


        #endregion

        #endregion

        #region Interface Implementation SubmodelServiceProvider

        public IResult<IOperation> CreateOperation(IOperation operation)
        {
            return submodelServiceProvider.CreateOperation(operation);
        }

        public IResult<IElementContainer<IOperation>> RetrieveOperations()
        {
            return submodelServiceProvider.RetrieveOperations();
        }

        public IResult<IOperation> RetrieveOperation(string operationId)
        {
            return submodelServiceProvider.RetrieveOperation(operationId);
        }

        public IResult DeleteOperation(string operationId)
        {
            return submodelServiceProvider.DeleteOperation(operationId);
        }

        public IResult<InvocationResponse> InvokeOperation(string operationId, InvocationRequest invocationRequest)
        {
            return submodelServiceProvider.InvokeOperation(operationId, invocationRequest);
        }

        public IResult<IProperty> CreateProperty(IProperty dataElement)
        {
            return submodelServiceProvider.CreateProperty(dataElement);
        }

        public IResult<IElementContainer<IProperty>> RetrieveProperties()
        {
            return submodelServiceProvider.RetrieveProperties();
        }

        public IResult<IProperty> RetrieveProperty(string propertyId)
        {
            return submodelServiceProvider.RetrieveProperty(propertyId);
        }

        public IResult UpdatePropertyValue(string propertyId, IValue propertyValue)
        {
            return submodelServiceProvider.UpdatePropertyValue(propertyId, propertyValue);
        }

        public IResult DeleteProperty(string propertyId)
        {
            return submodelServiceProvider.DeleteProperty(propertyId);
        }

        public IResult<IEvent> CreateEvent(IEvent eventable)
        {
            return submodelServiceProvider.CreateEvent(eventable);
        }

        public IResult<IElementContainer<IEvent>> RetrieveEvents()
        {
            return submodelServiceProvider.RetrieveEvents();
        }

        public IResult<IEvent> RetrieveEvent(string eventId)
        {
            return submodelServiceProvider.RetrieveEvent(eventId);
        }

        public IResult ThrowEvent(IPublishableEvent publishableEvent, string topic, Action<IMessagePublishedEventArgs> MessagePublished, byte qosLevel, bool retain)
        {
            return submodelServiceProvider.ThrowEvent(publishableEvent, topic, MessagePublished, qosLevel, retain);
        }

        public IResult DeleteEvent(string eventId)
        {
            return submodelServiceProvider.DeleteEvent(eventId);
        }

        public Delegate RetrieveMethodDelegate(string operationId)
        {
            return submodelServiceProvider.RetrieveMethodDelegate(operationId);
        }

        public void RegisterMethodCalledHandler(string operationId, Delegate handler)
        {
            submodelServiceProvider.RegisterMethodCalledHandler(operationId, handler);
        }

        public void ConfigureEventHandler(IMessageClient messageClient)
        {
            submodelServiceProvider.ConfigureEventHandler(messageClient);
        }

        public IResult<IValue> RetrievePropertyValue(string propertyId)
        {
            return submodelServiceProvider.RetrievePropertyValue(propertyId);
        }

        public PropertyHandler RetrievePropertyHandler(string propertyId)
        {
            return submodelServiceProvider.RetrievePropertyHandler(propertyId);
        }

        public void RegisterPropertyHandler(string propertyId, PropertyHandler handler)
        {
            submodelServiceProvider.RegisterPropertyHandler(propertyId, handler);
        }

        public void SubscribeUpdates(string propertyId, Action<IValue> updateFunction)
        {
            submodelServiceProvider.SubscribeUpdates(propertyId, updateFunction);
        }

        public void PublishUpdate(string propertyId, IValue propertyValue)
        {
            submodelServiceProvider.PublishUpdate(propertyId, propertyValue);
        }

        public IResult<ISubmodel> RetrieveSubmodel()
        {
            return submodelServiceProvider.RetrieveSubmodel();
        }

        public void RegisterEventDelegate(string eventId, EventDelegate eventDelegate)
        {
            submodelServiceProvider.RegisterEventDelegate(eventId, eventDelegate);
        }

        public IResult<ISubmodelElement> CreateSubmodelElement(ISubmodelElement submodelElement)
        {
            return submodelServiceProvider.CreateSubmodelElement(submodelElement);
        }

        public IResult<IElementContainer<ISubmodelElement>> RetrieveSubmodelElements()
        {
            return submodelServiceProvider.RetrieveSubmodelElements();
        }

        public IResult<ISubmodelElement> RetrieveSubmodelElement(string submodelElementId)
        {
            return submodelServiceProvider.RetrieveSubmodelElement(submodelElementId);
        }

        public IResult<IValue> RetrieveSubmodelElementValue(string submodelElementId)
        {
            return submodelServiceProvider.RetrieveSubmodelElementValue(submodelElementId);
        }

        public IResult UpdateSubmodelElement(string submodelElementId, ISubmodelElement submodelElement)
        {
            return submodelServiceProvider.UpdateSubmodelElement(submodelElementId, submodelElement);
        }

        public IResult DeleteSubmodelElement(string submodelElementId)
        {
            return submodelServiceProvider.DeleteSubmodelElement(submodelElementId);
        }

        public IResult<CallbackResponse> InvokeOperationAsync(string operationId, InvocationRequest invocationRequest)
        {
            return submodelServiceProvider.InvokeOperationAsync(operationId, invocationRequest);
        }

        public IResult<InvocationResponse> GetInvocationResult(string operationId, string requestId)
        {
            return submodelServiceProvider.GetInvocationResult(operationId, requestId);
        }

        #endregion

        #region Helper Methods


        #endregion
    }
}
