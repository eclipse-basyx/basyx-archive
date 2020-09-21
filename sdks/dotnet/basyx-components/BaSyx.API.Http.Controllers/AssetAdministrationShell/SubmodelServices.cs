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
            if (submodelElement == null)
                return ResultHandling.NullResult(nameof(submodelElement));

            var result = serviceProvider.CreateSubmodelElement("/", submodelElement);
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
        [ProducesResponseType(typeof(SubmodelElement), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetSubmodelElementByIdShort(string submodelElementIdShort)
        {
            if (string.IsNullOrEmpty(submodelElementIdShort))
                return ResultHandling.NullResult(nameof(submodelElementIdShort));

            IResult result;
            if (submodelElementIdShort.EndsWith("/value"))
            {
                var retrieveElementValueResult = serviceProvider.RetrieveSubmodelElementValue(submodelElementIdShort.Replace("/value", string.Empty));
                if (retrieveElementValueResult.Success && retrieveElementValueResult.Entity != null)
                    return new OkObjectResult(retrieveElementValueResult.Entity.Value);
                else
                    result = retrieveElementValueResult;
            }
            else
                result = serviceProvider.RetrieveSubmodelElement(submodelElementIdShort);

            return result.CreateActionResult(CrudOperation.Retrieve);
        }

        /// <summary>
        /// Retrieves the value of a specific Submodel-Element from the Submodel
        /// </summary>
        /// <param name="submodelElementIdShort">The Submodel-Element's short id</param>
        /// <returns></returns>
        /// <response code="200">Returns the value of a specific Submodel-Element</response>
        /// <response code="404">Submodel / Submodel-Element not found</response>  
        /// <response code="405">Method not allowed</response>  
        [HttpGet("submodel/submodelElements/{submodelElementIdShort}/value", Name = "GetSubmodelElementValueByIdShort")]
        [ProducesResponseType(typeof(object), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        [ProducesResponseType(typeof(Result), 405)]
        public IActionResult GetSubmodelElementValueByIdShort(string submodelElementIdShort)
        {
            return GetSubmodelElementByIdShort(submodelElementIdShort + "/value");
        }

        /// <summary>
        /// Updates the Asset Administration Shell's Submodel's Submodel-Element
        /// </summary>
        /// <param name="submodelElementIdShort">The Submodel-Element's short id</param>
        /// <param name="value">The new value</param>
        /// <returns></returns>
        /// <response code="200">Submodel-Element's value changed successfully</response>
        /// <response code="404">Submodel / Submodel-Element not found</response>     
        /// <response code="405">Method not allowed</response>  
        [HttpPut("submodel/submodelElements/{*submodelElementIdShort}", Name = "PutSubmodelElementValueByIdShort")]
        [ProducesResponseType(typeof(ElementValue), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult PutSubmodelElementValueByIdShort(string submodelElementIdShort, [FromBody] IValue value)
        {
            if (submodelElementIdShort.EndsWith("/value"))
            {
                if (string.IsNullOrEmpty(submodelElementIdShort))
                    return ResultHandling.NullResult(nameof(submodelElementIdShort));
                if (value == null)
                    return ResultHandling.NullResult(nameof(value));

                var result = serviceProvider.UpdateSubmodelElementValue(submodelElementIdShort.Replace("/value", string.Empty), value);
                return result.CreateActionResult(CrudOperation.Update);
            }
            return ResultHandling.MethodNotAllowedResult();
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

        /// <summary>
        /// Invokes a specific operation from the Submodel synchronously or asynchronously
        /// </summary>
        /// <param name="operationIdShort">The Operation's short id</param>
        /// <param name="invocationRequest">The parameterized request object for the invocation</param>
        /// <param name="async">Determines whether the execution of the operation is asynchronous (true) or not (false)</param>
        /// <returns></returns>
        /// <response code="200">Operation invoked successfully</response>
        /// <response code="400">Bad Request</response>
        /// <response code="404">Submodel / Method handler not found</response>
        [HttpPost("submodel/submodelElements/{operationIdShort}/invoke/{async:bool?}", Name = "InvokeOperationByIdShortAsync")]
        [ProducesResponseType(typeof(Result), 400)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult InvokeOperationByIdShort(string operationIdShort, [FromBody] InvocationRequest invocationRequest, [FromQuery] bool async)
        {
            if (string.IsNullOrEmpty(operationIdShort))
                return ResultHandling.NullResult(nameof(operationIdShort));
            if (invocationRequest == null)
                return ResultHandling.NullResult(nameof(invocationRequest));

            if (async)
            {
                IResult<CallbackResponse> result = serviceProvider.InvokeOperationAsync(operationIdShort, invocationRequest);
                return result.CreateActionResult(CrudOperation.Invoke);
            }
            else
            {
                IResult<InvocationResponse> result = serviceProvider.InvokeOperation(operationIdShort, invocationRequest);
                return result.CreateActionResult(CrudOperation.Invoke);
            }
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
        [HttpGet("submodel/submodelElements/{operationIdShort}/invocationList/{requestId}", Name = "GetInvocationResultByIdShort")]
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
      }
}

