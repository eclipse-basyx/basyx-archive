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
using System;
using Newtonsoft.Json.Linq;
using BaSyx.Models.Extensions;
using BaSyx.Models.Core.AssetAdministrationShell.Implementations;
using BaSyx.Models.Core.AssetAdministrationShell.Implementations.SubmodelElementTypes;
using BaSyx.Models.Core.Common;
using BaSyx.Models.Communication;

namespace BaSyx.API.Http.Controllers
{
    /// <summary>
    /// All Asset Administration Shell Services provided by the component
    /// </summary>
    public class SubmodelServices : Controller
    {
        private readonly ISubmodelServiceProvider serviceProvider;

        /// <summary>
        /// Constructor for the Submodel Services Controller
        /// </summary>
        /// <param name="submodelServiceProvider">The Submodel Service Provider implementation provided by the dependency injection</param>
        public SubmodelServices(ISubmodelServiceProvider submodelServiceProvider)
        {
            serviceProvider = submodelServiceProvider;
        }

        #region REST-Interface Submodel


        /// <summary>
        /// Retrieves a customizable table version of a Submodel
        /// </summary>
        /// <param name="columns">A comma-separated list of field names to structure the payload beeing returned</param>
        /// <returns></returns>
        /// <response code="200">Success</response>
        /// <response code="404">Submodel not found</response>   
        [HttpGet("submodel/table", Name = "GetSubmodelAsTable")]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetSubmodelAsTable([FromQuery] string columns)
        {
            if (string.IsNullOrEmpty(columns))
                return ResultHandling.NullResult(nameof(columns));

            var result = serviceProvider.RetrieveSubmodel();
            if (result != null && result.Entity != null)
            {
                string[] columnNames = columns.Split(new char[] { ',' }, StringSplitOptions.RemoveEmptyEntries);
                JToken customizedSubmodel = result.Entity.CustomizeSubmodel(columnNames);
                return new JsonResult(customizedSubmodel);
            }

            return result.CreateActionResult(CrudOperation.Retrieve);
        }


        /// <summary>
        /// Retrieves the minimized version of a Submodel, i.e. only the values of SubmodelElements are serialized and returned
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Success</response>
        /// <response code="404">Submodel not found</response>       
        [HttpGet("submodel/values", Name = "GetSubmodelValues")]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetMinimizedSubmodel()
        {
            var result = serviceProvider.RetrieveSubmodel();

            if (result != null && result.Entity != null)
            {
                JObject minimizedSubmodel = result.Entity.MinimizeSubmodel();
                return new JsonResult(minimizedSubmodel);
            }

            return result.CreateActionResult(CrudOperation.Retrieve);
        }

        /// <summary>
        /// Retrieves the entire Submodel
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Success</response>
        /// <response code="404">Submodel not found</response>       
        [HttpGet("submodel", Name = "GetSubmodel")]
        [ProducesResponseType(typeof(Submodel), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetSubmodel()
        {
            var result = serviceProvider.RetrieveSubmodel();
            return result.CreateActionResult(CrudOperation.Retrieve);
        }

        /// <summary>
        /// Retrieves all SubmodelElements from the current Submodel
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Returns a list of found SubmodelElements</response>
        /// <response code="404">Submodel not found</response>       
        [HttpGet("submodel/submodelElements", Name = "GetSubmodelElements")]
        [ProducesResponseType(typeof(SubmodelElement[]), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetSubmodelElements()
        {
            var result = serviceProvider.RetrieveSubmodelElements();
            return result.CreateActionResult(CrudOperation.Retrieve);
        }

        /// <summary>
        /// Retrieves all Properties from the current Submodel
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Returns a list of found Properties</response>
        /// <response code="404">Submodel not found</response>       
        [HttpGet("submodel/properties", Name = "GetProperties")]
        [ProducesResponseType(typeof(Property[]), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetProperties()
        {
            var result = serviceProvider.RetrieveProperties();
            return result.CreateActionResult(CrudOperation.Retrieve);
        }

        /// <summary>
        /// Retrieves all Operations from the current Submodel
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Success</response>
        /// <response code="404">Submodel not found</response>      
        [HttpGet("submodel/operations", Name = "GetOperations")]
        [ProducesResponseType(typeof(Operation[]), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetOperations()
        {
            var result = serviceProvider.RetrieveOperations();
            return result.CreateActionResult(CrudOperation.Retrieve);
        }

        /// <summary>
        /// Retrieves all Events from the current Submodel
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Success</response>
        /// <response code="404">Submodel not found</response>      
        [HttpGet("submodel/events", Name = "GetEvents")]
        [ProducesResponseType(typeof(Event[]), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetEvents()
        {
            var result = serviceProvider.RetrieveEvents();
            return result.CreateActionResult(CrudOperation.Retrieve);
        }


        #region SubmodelElement - REST-Calls
        /// <summary>
        /// Adds a new Submodel-Element to the Submodel
        /// </summary>
        /// <param name="submodelElement">The Submodel-Element object</param>
        /// <returns></returns>
        /// <response code="201">Submodel Element created successfully</response>
        /// <response code="400">Bad Request</response>
        /// <response code="404">Submodel not found</response>
        [HttpPut("submodel/submodelElements", Name = "PutSubmodelElement")]
        [ProducesResponseType(typeof(SubmodelElement), 201)]
        [ProducesResponseType(typeof(Result), 400)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult PutSubmodelElement([FromBody] ISubmodelElement submodelElement)
        {
            if(submodelElement == null)
                return ResultHandling.NullResult(nameof(submodelElement));

            var result = serviceProvider.CreateSubmodelElement(submodelElement);
            return result.CreateActionResult(CrudOperation.Create, "submodel/submodelElements/" + submodelElement.IdShort);
        }
                

        /// <summary>
        /// Retrieves a specific Submodel-Element from the Submodel
        /// </summary>
        /// <param name="submodelElementIdShort">The Submodel-Element's short id</param>
        /// <returns></returns>
        /// <response code="200">Returns the requested Submodel-Element</response>
        /// <response code="404">Submodel / Submodel Element not found</response>     
        [HttpGet("submodel/submodelElements/{*submodelElementIdShort}", Name = "GetSubmodelElementByIdShort")]
        [ProducesResponseType(typeof(Property), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetSubmodelElementByIdShort(string submodelElementIdShort)
        {
            if (string.IsNullOrEmpty(submodelElementIdShort))
                return ResultHandling.NullResult(nameof(submodelElementIdShort));

            var result = serviceProvider.RetrieveSubmodelElement(submodelElementIdShort);                     
            return result.CreateActionResult(CrudOperation.Retrieve);
        }

        /// <summary>
        /// Deletes a specific Submodel-Element from the Submodel
        /// </summary>
        /// <param name="submodelElementIdShort">The Submodel-Element's short id</param>
        /// <returns></returns>
        /// <response code="204">Submodel-Element deleted successfully</response>
        /// <response code="404">Submodel / Submodel-Element not found</response>
        [HttpDelete("submodel/submodelElements/{*submodelElementIdShort}", Name = "DeleteSubmodelElementByIdShort")]
        [ProducesResponseType(typeof(Result), 200)]
        public IActionResult DeleteSubmodelElementByIdShort(string submodelElementIdShort)
        {
            if (string.IsNullOrEmpty(submodelElementIdShort))
                return ResultHandling.NullResult(nameof(submodelElementIdShort));

            var result = serviceProvider.DeleteSubmodelElement(submodelElementIdShort);
            return result.CreateActionResult(CrudOperation.Delete);
        }
        #endregion
        /// <summary>
        /// Retrieves a specific Property from the Asset Administrations's Submodel
        /// </summary>
        /// <param name="propertyIdShort">The Property's short id</param>
        /// <returns></returns>
        /// <response code="200">Returns the requested Property</response>
        /// <response code="404">Submodel / Property not found</response>     
        [HttpGet("submodel/properties/{propertyIdShort}", Name = "GetPropertyByIdShort")]
        [ProducesResponseType(typeof(Property), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetPropertyByIdShort(string propertyIdShort)
        {
            if (string.IsNullOrEmpty(propertyIdShort))
                return ResultHandling.NullResult(nameof(propertyIdShort));

            var result = serviceProvider.RetrieveProperty(propertyIdShort);
            return result.CreateActionResult(CrudOperation.Retrieve);
        }

        /// <summary>
        /// Retrieves the value of a specific Property from the Asset Administrations Shell's Submodel
        /// </summary>
        /// <param name="propertyIdShort">The Property's short id</param>
        /// <returns></returns>
        /// <response code="200">Returns the requested Property's value</response>
        /// <response code="404">Submodel / Property not found</response>     
        [HttpGet("submodel/properties/{propertyIdShort}/value", Name = "GetPropertyValueByIdShort")]
        [ProducesResponseType(typeof(ElementValue), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetPropertyValueByIdShort(string propertyIdShort)
        {
            if (string.IsNullOrEmpty(propertyIdShort))
                return ResultHandling.NullResult(nameof(propertyIdShort));

            var result = serviceProvider.RetrievePropertyValue(propertyIdShort);
            return result.CreateActionResult(CrudOperation.Retrieve);
        }

        /// <summary>
        /// Updates the Asset Administration Shell's Submodel's Property
        /// </summary>
        /// <param name="propertyIdShort">The Property's short id</param>
        /// <param name="value">The new value</param>
        /// <returns></returns>
        /// <response code="200">Property's value changed successfully</response>
        /// <response code="404">Submodel / Property not found</response>     
        [HttpPut("submodel/properties/{propertyIdShort}/value", Name = "PutPropertyValueByIdShort")]
        [ProducesResponseType(typeof(ElementValue), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult PutPropertyValueByIdShort(string propertyIdShort, [FromBody] IValue value)
        {
            if (string.IsNullOrEmpty(propertyIdShort))
                return ResultHandling.NullResult(nameof(propertyIdShort));

            var result = serviceProvider.UpdatePropertyValue(propertyIdShort, value);
            return result.CreateActionResult(CrudOperation.Update);
        }

        #endregion
        #region Operation - REST-Calls
        /// <summary>
        /// Retrieves a specific Operation from the Asset Administration Shell's Submodel
        /// </summary>
        /// <param name="operationIdShort">The Operation's short id</param>
        /// <returns></returns>
        /// <response code="200">Success</response>
        /// <response code="404">Submodel / Operation not found</response>     
        [HttpGet("submodel/operations/{operationIdShort}", Name = "GetOperationByIdShort")]
        [ProducesResponseType(typeof(Operation), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetOperationByIdShort(string operationIdShort)
        {
            if (string.IsNullOrEmpty(operationIdShort))
                return ResultHandling.NullResult(nameof(operationIdShort));

            var result = serviceProvider.RetrieveOperation(operationIdShort);
            return result.CreateActionResult(CrudOperation.Retrieve);
        }
        /// <summary>
        /// Synchronously invokes a specific operation from the Submodel
        /// </summary>
        /// <param name="operationIdShort">The Operation's short id</param>
        /// <param name="invocationRequest">The parameterized request object for the invocation</param>
        /// <returns></returns>
        /// <response code="200">Operation invoked successfully</response>
        /// <response code="400">Bad Request</response>
        /// <response code="404">Submodel / Operation not found</response>
        [HttpPost("submodel/operations/{operationIdShort}", Name = "InvokeOperationByIdShort")]
        [ProducesResponseType(typeof(InvocationResponse), 200)]
        [ProducesResponseType(typeof(Result), 400)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult InvokeOperationByIdShort(string operationIdShort, [FromBody] InvocationRequest invocationRequest)
        {
            if (string.IsNullOrEmpty(operationIdShort))
                return ResultHandling.NullResult(nameof(operationIdShort));
            if (invocationRequest == null)
                return ResultHandling.NullResult(nameof(invocationRequest));

            IResult<InvocationResponse> result = serviceProvider.InvokeOperation(operationIdShort, invocationRequest);
            return result.CreateActionResult(CrudOperation.Invoke);
        }

        /// <summary>
        /// Asynchronously invokes a specific operation from the Submodel
        /// </summary>
        /// <param name="operationIdShort">The Operation's short id</param>
        /// <param name="invocationRequest">The parameterized request object for the invocation</param>
        /// <returns></returns>
        /// <response code="200">Operation invoked successfully</response>
        /// <response code="400">Bad Request</response>
        /// <response code="404">Submodel / Method handler not found</response>
        [HttpPost("submodel/operations/{operationIdShort}/async", Name = "InvokeOperationByIdShortAsync")]
        [ProducesResponseType(typeof(CallbackResponse), 200)]
        [ProducesResponseType(typeof(Result), 400)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult InvokeOperationByIdShortAsync(string operationIdShort, [FromBody] InvocationRequest invocationRequest)
        {
            if (string.IsNullOrEmpty(operationIdShort))
                return ResultHandling.NullResult(nameof(operationIdShort));
            if (invocationRequest == null)
                return ResultHandling.NullResult(nameof(invocationRequest));

            IResult<CallbackResponse> result = serviceProvider.InvokeOperationAsync(operationIdShort, invocationRequest);
            return result.CreateActionResult(CrudOperation.Invoke);
        }

        /// <summary>
        /// Retrieves the result of an asynchronously started operation
        /// </summary>
        /// <param name="operationIdShort">The Operation's short id</param>
        /// <param name="requestId">The request id</param>
        /// <returns></returns>
        /// <response code="200">Result found</response>
        /// <response code="400">Bad Request</response>
        /// <response code="404">Submodel / Operation / Request not found</response>
        [HttpGet("submodel/operations/{operationIdShort}/invocationList/{requestId}", Name = "GetInvocationResultByIdShort")]
        [ProducesResponseType(typeof(InvocationResponse), 200)]
        [ProducesResponseType(typeof(Result), 400)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetInvocationResultByIdShort(string operationIdShort, string requestId)
        {
            if (string.IsNullOrEmpty(operationIdShort))
                return ResultHandling.NullResult(nameof(operationIdShort));
            if (string.IsNullOrEmpty(requestId))
                return ResultHandling.NullResult(nameof(requestId));

            IResult<InvocationResponse> result = serviceProvider.GetInvocationResult(operationIdShort, requestId);
            return result.CreateActionResult(CrudOperation.Invoke);
        }

        #endregion
        #region Event - REST-Calls
        /// <summary>
        /// Retrieves a specific event from the Asset Administration Shell's Submodel
        /// </summary>
        /// <param name="eventIdShort">The Event's short id</param>
        /// <returns></returns>
        /// <response code="200">Success</response>
        /// <response code="404">Submodel / Event not found</response>     
        [HttpGet("submodel/events/{eventIdShort}", Name = "GetEventByIdShort")]
        [ProducesResponseType(typeof(Event), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetEventByIdShort(string eventIdShort)
        {
            if (string.IsNullOrEmpty(eventIdShort))
                return ResultHandling.NullResult(nameof(eventIdShort));

            var result = serviceProvider.RetrieveEvent(eventIdShort);
            return result.CreateActionResult(CrudOperation.Retrieve);
        }

        #endregion

        #region Helper Methods


        #endregion
    }
}
