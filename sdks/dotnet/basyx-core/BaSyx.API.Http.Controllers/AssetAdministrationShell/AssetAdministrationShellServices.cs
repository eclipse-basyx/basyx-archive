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
using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;
using BaSyx.Models.Core.AssetAdministrationShell.Generics;
using BaSyx.Utils.ResultHandling;
using BaSyx.API.Components;
using BaSyx.Models.Connectivity;
using System.Linq;
using BaSyx.Models.Core.AssetAdministrationShell.Implementations;
using Microsoft.Extensions.DependencyInjection;
using BaSyx.Models.Connectivity.Descriptors;
using BaSyx.Models.Core.Common;
using BaSyx.Models.Core.AssetAdministrationShell.Implementations.SubmodelElementTypes;
using BaSyx.Models.Core.AssetAdministrationShell.Generics.SubmodelElementTypes;
using BaSyx.Models.Communication;

namespace BaSyx.API.Http.Controllers
{
    /// <summary>
    /// All Asset Administration Shell Services provided by the component
    /// </summary>
    public class AssetAdministrationShellServices : Controller
    {
        private readonly IAssetAdministrationShellServiceProvider serviceProvider;

        /// <summary>
        /// Constructor for the Asset Administration Shell Services Controller
        /// </summary>
        /// <param name="assetAdministrationShellServiceProvider">The Asset Administration Shell Service Provider implementation provided by the dependency injection</param>
        public AssetAdministrationShellServices(IAssetAdministrationShellServiceProvider assetAdministrationShellServiceProvider)
        {
            serviceProvider = assetAdministrationShellServiceProvider;
        }

        #region REST-Interface AssetAdministrationShell

        /// <summary>
        /// Retrieves the Asset Administration Shell Descriptor
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Success</response>   
        [HttpGet("aas", Name = "GetAssetAdministrationShell")]
        [ProducesResponseType(typeof(AssetAdministrationShellDescriptor), 200)]
        public IActionResult GetAssetAdministrationShell()
        {
            var serviceDescriptor = serviceProvider?.ServiceDescriptor;

            if(serviceDescriptor == null)
                return StatusCode(502);
            else
                return new OkObjectResult(serviceProvider.ServiceDescriptor);
        }

        #region Submodel - REST-Calls
        /// <summary>
        /// Creates or updates a Submodel to an existing Asset Administration Shell
        /// </summary>
        /// <param name="submodel">The serialized Submodel object</param>
        /// <returns></returns>
        /// <response code="201">Submodel created successfully</response>
        /// <response code="400">Bad Request</response>               
        [HttpPost("aas/submodels", Name = "PutSubmodelToShell")]
        [ProducesResponseType(typeof(Submodel), 201)]
        [ProducesResponseType(typeof(Result), 400)]
        public IActionResult PutSubmodelToShell([FromBody] ISubmodel submodel)
        {
            if (submodel == null)
                return ResultHandling.NullResult(nameof(submodel));

            var spEndpoints = serviceProvider
                .ServiceDescriptor
                .Endpoints
                .ToList()
                .ConvertAll(c => new HttpEndpoint(DefaultEndpointRegistration.GetSubmodelEndpoint(c, submodel.IdShort)));

            ISubmodelDescriptor descriptor = new SubmodelDescriptor(submodel, spEndpoints);
            SubmodelServiceProvider cssp = new SubmodelServiceProvider(submodel, descriptor);
            var result = serviceProvider.SubmodelRegistry.RegisterSubmodelServiceProvider(submodel.IdShort, cssp);

            return result.CreateActionResult(CrudOperation.Create, "aas/submodels/" + submodel.IdShort);
        }
        /// <summary>
        /// Retrieves the Submodel from the Asset Administration Shell
        /// </summary>
        /// <param name="submodelIdShort">The Submodel's short id</param>
        /// <returns></returns>
        /// <response code="200">Submodel retrieved successfully</response>
        /// <response code="404">No Submodel Service Provider found</response>    
        [HttpGet("aas/submodels/{submodelIdShort}", Name = "GetSubmodelFromShellByIdShort")]
        [ProducesResponseType(typeof(Submodel), 200)]
        [ProducesResponseType(typeof(Result), 400)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetSubmodelFromShellByIdShort(string submodelIdShort)
        {
            if (string.IsNullOrEmpty(submodelIdShort))
                return ResultHandling.NullResult(nameof(submodelIdShort));

            var submodelProvider = serviceProvider.SubmodelRegistry.GetSubmodelServiceProvider(submodelIdShort);
            if (!submodelProvider.Success || submodelProvider?.Entity == null)
                return NotFound(new Result(false, new NotFoundMessage("Submodel")));

            return new OkObjectResult(submodelProvider.Entity.GetBinding());
        }
        /// <summary>
        /// Deletes a specific Submodel from the Asset Administration Shell
        /// </summary>
        /// <param name="submodelIdShort">The Submodel's short id</param>
        /// <returns></returns>
        /// <response code="204">Submodel deleted successfully</response>
        /// <response code="400">Bad Request</response>    
        [HttpDelete("aas/submodels/{submodelIdShort}", Name = "DeleteSubmodelFromShellByIdShort")]
        [ProducesResponseType(typeof(Result), 400)]
        public IActionResult DeleteSubmodelFromShellByIdShort(string submodelIdShort)
        {
            if (string.IsNullOrEmpty(submodelIdShort))
                return ResultHandling.NullResult(nameof(submodelIdShort));

            var result = serviceProvider.SubmodelRegistry.UnregisterSubmodelServiceProvider(submodelIdShort);
            return result.CreateActionResult(CrudOperation.Delete);
        }
        /// <summary>
        /// Retrieves all Submodels from the  Asset Administration Shell
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Returns a list of found Submodels</response>
        /// <response code="404">No Submodel Service Providers found</response>       
        [HttpGet("aas/submodels", Name = "GetSubmodelsFromShell")]
        [ProducesResponseType(typeof(Submodel[]), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetSubmodelsFromShell()
        {
            var submodelProviders = serviceProvider.SubmodelRegistry.GetSubmodelServiceProviders();

            if (!submodelProviders.Success || submodelProviders.Entity?.Count() == 0)
                return NotFound(new Result(false, new NotFoundMessage("Submodels")));

            var submodelBindings = submodelProviders.Entity.Select(s => s.GetBinding()).ToArray();

            return new OkObjectResult(submodelBindings);
        }

        #endregion

        #endregion

        #region REST-Interface Submodel

        /// <summary>
        /// Retrieves all Submodel-Elements from the Submodel
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Returns a list of found Submodel-Elements</response>
        /// <response code="404">Submodel not found / No Submodel-Elements found</response>       
        [HttpGet("aas/submodels/{submodelIdShort}/submodel/submodelElements", Name = "RoutedGetSubmodelElements")]
        [ProducesResponseType(typeof(SubmodelElement[]), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult RoutedGetSubmodelElements()
        {
            var controller = HttpContext.RequestServices.GetService<SubmodelServices>();
            return controller.GetSubmodelElements();
        }

        /// <summary>
        /// Retrieves all Properties from the Submodel
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Returns a list of found Properties</response>
        /// <response code="404">Submodel not found / No Properties found</response>       
        [HttpGet("aas/submodels/{submodelIdShort}/submodel/properties", Name = "RoutedGetProperties")]
        [ProducesResponseType(typeof(Property[]), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult RoutedGetProperties()
        {
            var controller = HttpContext.RequestServices.GetService<SubmodelServices>();
            return controller.GetProperties();            
        }

        /// <summary>
        /// Retrieves all Operations from the Submodel
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Success</response>
        /// <response code="404">Submodel not found / No Operations found</response>      
        [HttpGet("aas/submodels/{submodelIdShort}/submodel/operations", Name = "RoutedGetOperations")]
        [ProducesResponseType(typeof(Operation[]), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult RoutedGetOperations()
        {
            var controller = HttpContext.RequestServices.GetService<SubmodelServices>();
            return controller.GetOperations();
        }

        /// <summary>
        /// Retrieves all Events from the Submodel
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Success</response>
        /// <response code="404">Submodel not found / No Events found</response>      
        [HttpGet("aas/submodels/{submodelIdShort}/submodel/events", Name = "RoutedGetEvents")]
        [ProducesResponseType(typeof(Event[]), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult RoutedGetEvents()
        {
            var controller = HttpContext.RequestServices.GetService<SubmodelServices>();
            return controller.GetEvents();
        }

        #region SubmodelElement - REST-Calls
        /// <summary>
        /// Creates or updates a Submodel-Element to the Asset Administration Shell's Submodel
        /// </summary>
        /// <param name="submodelElement">The serialized Submodel Element object</param>
        /// <returns></returns>
        /// <response code="201">Submodel-Element created successfully</response>
        /// <response code="400">Bad Request</response>
        /// <response code="404">Submodel not found</response>
        [HttpPut("aas/submodels/{submodelIdShort}/submodel/submodelElements", Name = "RoutedPutSubmodelElement")]
        [ProducesResponseType(typeof(SubmodelElement), 201)]
        [ProducesResponseType(typeof(Result), 400)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult RoutedPutSubmodelElement([FromBody] ISubmodelElement submodelElement)
        {
            if (submodelElement == null)
                return ResultHandling.NullResult(nameof(submodelElement));

            var controller = HttpContext.RequestServices.GetService<SubmodelServices>();
            return controller.PutSubmodelElement(submodelElement);
        }
        /// <summary>
        /// Retrieves a specific Submodel-Element from the Asset Administrations's Submodel
        /// </summary>
        /// <param name="submodelElementIdShort">The Submodel-Element's short id</param>
        /// <returns></returns>
        /// <response code="200">Returns the requested Submodel-Element</response>
        /// <response code="404">Submodel / Submodel-Element not found</response>     
        [HttpGet("aas/submodels/{submodelIdShort}/submodel/submodelElements/{submodelElementIdShort}", Name = "RoutedGetSubmodelElementByIdShort")]
        [ProducesResponseType(typeof(SubmodelElement), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult RoutedGetSubmodelElementByIdShort(string submodelElementIdShort)
        {
            if (string.IsNullOrEmpty(submodelElementIdShort))
                return ResultHandling.NullResult(nameof(submodelElementIdShort));

            var controller = HttpContext.RequestServices.GetService<SubmodelServices>();
            return controller.GetSubmodelElementByIdShort(submodelElementIdShort);
        }

        /// <summary>
        /// Deletes a specific Submodel-Element from the Asset Administration Shell's Submodel
        /// </summary>
        /// <param name="submodelElementIdShort">The Submodel-Element's short id</param>
        /// <returns></returns>
        /// <response code="204">Submodel-Element deleted successfully</response>
        /// <response code="404">Submodel not found</response>
        [HttpDelete("aas/submodels/{submodelIdShort}/submodel/submodelElements/{submodelElementIdShort}", Name = "RoutedDeleteSubmodelElementByIdShort")]
        [ProducesResponseType(typeof(Result), 200)]
        public IActionResult RoutedDeleteSubmodelElementByIdShort(string submodelElementIdShort)
        {
            if (string.IsNullOrEmpty(submodelElementIdShort))
                return ResultHandling.NullResult(nameof(submodelElementIdShort));

            var controller = HttpContext.RequestServices.GetService<SubmodelServices>();
            return controller.DeleteSubmodelElementByIdShort(submodelElementIdShort);
        }
        #endregion
        #region Property - REST-Calls
        /// <summary>
        /// Retrieves a specific Property from the Asset Administration Shell's Submodel
        /// </summary>
        /// <param name="propertyIdShort">The Property's short id</param>
        /// <returns></returns>
        /// <response code="200">Returns the requested Property</response>
        /// <response code="404">Submodel / Property not found</response>     
        [HttpGet("aas/submodels/{submodelIdShort}/submodel/properties/{propertyIdShort}", Name = "RoutedGetPropertyByIdShort")]
        [ProducesResponseType(typeof(Property), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult RoutedGetPropertyByIdShort(string propertyIdShort)
        {
            if (string.IsNullOrEmpty(propertyIdShort))
                return ResultHandling.NullResult(nameof(propertyIdShort));

            var controller = HttpContext.RequestServices.GetService<SubmodelServices>();
            return controller.GetPropertyByIdShort(propertyIdShort);
        }

        /// <summary>
        /// Retrieves the value of a specific Property from the Asset Administrations Shell's Submodel
        /// </summary>
        /// <param name="propertyIdShort">The Property's short id</param>
        /// <returns></returns>
        /// <response code="200">Returns the requested Property's value</response>
        /// <response code="404">Submodel / Property not found</response>     
        [HttpGet("aas/submodels/{submodelIdShort}/submodel/properties/{propertyIdShort}/value", Name = "RoutedGetPropertyValueByIdShort")]
        [ProducesResponseType(typeof(ElementValue), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult RoutedGetPropertyValueByIdShort(string propertyIdShort)
        {
            if (string.IsNullOrEmpty(propertyIdShort))
                return ResultHandling.NullResult(nameof(propertyIdShort));

            var controller = HttpContext.RequestServices.GetService<SubmodelServices>();
            return controller.GetPropertyValueByIdShort(propertyIdShort);
        }

        /// <summary>
        /// Updates the Asset Administration Shell's Submodel's Property's value
        /// </summary>
        /// <param name="propertyIdShort">The Property's short id</param>
        /// <param name="value">The new value</param>
        /// <returns></returns>
        /// <response code="200">Property's value changed successfully</response>
        /// <response code="404">Submodel/Property not found</response>     
        [HttpPut("aas/submodels/{submodelIdShort}/submodel/properties/{propertyIdShort}/value", Name = "RoutedPutPropertyValueByIdShort")]
        [ProducesResponseType(typeof(ElementValue), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult RoutedPutPropertyValueByIdShort(string propertyIdShort, [FromBody] IValue value)
        {
            if (string.IsNullOrEmpty(propertyIdShort))
                return ResultHandling.NullResult(nameof(propertyIdShort));
            if (value == null)
                return ResultHandling.NullResult(nameof(value));

            var controller = HttpContext.RequestServices.GetService<SubmodelServices>();
            return controller.PutPropertyValueByIdShort(propertyIdShort, value);
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
        [HttpGet("aas/submodels/{submodelIdShort}/submodel/operations/{operationIdShort}", Name = "RoutedGetOperationByIdShort")]
        [ProducesResponseType(typeof(Operation), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult RoutedGetOperationByIdShort(string operationIdShort)
        {
            if (string.IsNullOrEmpty(operationIdShort))
                return ResultHandling.NullResult(nameof(operationIdShort));

            var controller = HttpContext.RequestServices.GetService<SubmodelServices>();
            return controller.GetOperationByIdShort(operationIdShort);
        }
        /// <summary>
        /// Synchronously invokes a specific operation from the Submodel
        /// </summary>
        /// <param name="operationIdShort">The Operation's short id</param>
        /// <param name="invocationRequest">The parameterized request object for the invocation</param>
        /// <returns></returns>
        /// <response code="200">Operation invoked successfully</response>
        /// <response code="400">Bad Request</response>
        /// <response code="404">Submodel / Method handler not found</response>
        [HttpPost("aas/submodels/{submodelIdShort}/submodel/operations/{operationIdShort}", Name = "RoutedInvokeOperationByIdShort")]
        [ProducesResponseType(typeof(InvocationResponse), 200)]
        [ProducesResponseType(typeof(Result), 400)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult RoutedInvokeOperationByIdShort(string operationIdShort, [FromBody] InvocationRequest invocationRequest)
        {
            if (string.IsNullOrEmpty(operationIdShort))
                return ResultHandling.NullResult(nameof(operationIdShort));
            if (invocationRequest == null)
                return ResultHandling.NullResult(nameof(invocationRequest));

            var controller = HttpContext.RequestServices.GetService<SubmodelServices>();
            return controller.InvokeOperationByIdShort(operationIdShort, invocationRequest);
        }

        #endregion
        #region Event - REST-Calls
        /// <summary>
        /// Retrieves a specific event from the Asset Administration Shell's Submodel
        /// </summary>
        /// <param name="eventIdShort">The Event's short id</param>
        /// <returns></returns>
        /// <response code="200">Success</response>
        /// <response code="404">Submodel/Event not found</response>     
        [HttpGet("aas/submodels/{submodelIdShort}/submodel/events/{eventIdShort}", Name = "RoutedGetEventByIdShort")]
        [ProducesResponseType(typeof(Event), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult RoutedGetEventByIdShort(string eventIdShort)
        {
            if (string.IsNullOrEmpty(eventIdShort))
                return ResultHandling.NullResult(nameof(eventIdShort));

            var controller = HttpContext.RequestServices.GetService<SubmodelServices>();
            return controller.GetEventByIdShort(eventIdShort);
        }
        #endregion
        #endregion

        #region Helper Methods

        #endregion
    }
}
