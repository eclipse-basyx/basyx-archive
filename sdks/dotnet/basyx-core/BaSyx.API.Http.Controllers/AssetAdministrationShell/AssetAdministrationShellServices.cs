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
using static BaSyx.Utils.ResultHandling.Utils;
using System.Linq;
using BaSyx.Models.Core.AssetAdministrationShell.Implementations;
using Microsoft.Extensions.DependencyInjection;
using BaSyx.Models.Connectivity.Descriptors;
using BaSyx.Models.Core.Common;
using BaSyx.Models.Core.AssetAdministrationShell.Implementations.SubmodelElementTypes;
using BaSyx.Models.Core.AssetAdministrationShell.Generics.SubmodelElementTypes;

namespace BaSyx.API.Http.Controllers
{
    /// <summary>
    /// All Asset Administration Shell Services provided by the component
    /// </summary>
    public class AssetAdministrationShellServices : Controller, IAssetAdministrationShellServiceProvider
    {
        private readonly IAssetAdministrationShellServiceProvider assetAdministrationShellServiceProvider;

        public IAssetAdministrationShellDescriptor ServiceDescriptor { get; }

        public IAssetAdministrationShell AssetAdministrationShell => assetAdministrationShellServiceProvider.GetBinding();

        public ISubmodelServiceProviderRegistry SubmodelRegistry => assetAdministrationShellServiceProvider.SubmodelRegistry;        

        public AssetAdministrationShellServices(IAssetAdministrationShellServiceProvider assetAdministrationShellServiceProvider)
        {
            this.assetAdministrationShellServiceProvider = assetAdministrationShellServiceProvider;
            ServiceDescriptor = assetAdministrationShellServiceProvider.ServiceDescriptor;
        }

        public void BindTo(IAssetAdministrationShell element)
        {
            assetAdministrationShellServiceProvider.BindTo(element);
        }
        public IAssetAdministrationShell GetBinding()
        {
            return assetAdministrationShellServiceProvider.GetBinding();
        }


        #region REST-Interface AssetAdministrationShell

        /// <summary>
        /// Retrieves the Asset Administration Shell
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Success</response>
        /// <response code="502">Bad Gateway - Asset Administration Shell not available</response>       
        [HttpGet("aas", Name = "GetAAS")]
        [ProducesResponseType(typeof(AssetAdministrationShellDescriptor), 200)]
        [ProducesResponseType(typeof(Result), 502)]
        public IActionResult GetAAS()
        {
            var serviceDescriptor = assetAdministrationShellServiceProvider?.ServiceDescriptor;

            if(serviceDescriptor == null)
                return StatusCode(502);
            else
                return new OkObjectResult(assetAdministrationShellServiceProvider.ServiceDescriptor);
        }

        #region Submodel - REST-Calls
        /// <summary>
        /// Adds a new Submodel to an existing Asset Administration Shell
        /// </summary>
        /// <param name="submodel">The Submodel's description object</param>
        /// <returns></returns>
        /// <response code="201">Submodel created successfully</response>
        /// <response code="400">Bad Request</response>               
        [HttpPost("aas/submodels", Name = "PostSubmodel")]
        [ProducesResponseType(typeof(BaSyx.Models.Core.AssetAdministrationShell.Implementations.Submodel), 201)]
        [ProducesResponseType(typeof(Result), 400)]
        public IActionResult PostSubmodel([FromBody] ISubmodel submodel)
        {
            var result = CreateSubmodel(submodel);
            if(result.Success && result.Entity != null)
            {
                var spEndpoints = ServiceDescriptor.Endpoints.ToList().ConvertAll(c => new HttpEndpoint(DefaultEndpointRegistration.GetSubmodelEndpoint(c, submodel.IdShort)));
                ISubmodelDescriptor descriptor = new SubmodelDescriptor(submodel, spEndpoints);

                SubmodelServiceProvider cssp = new SubmodelServiceProvider(submodel, descriptor);
                assetAdministrationShellServiceProvider.SubmodelRegistry.RegisterSubmodelServiceProvider(submodel.IdShort, cssp);
            }
            return EvaluateResult(result, CrudOperation.Create, "aas/submodels/" + submodel.IdShort);
        }
        /// <summary>
        /// Retrieves the Submodel from the Asset Administration Shell
        /// </summary>
        /// <param name="submodelId">The Submodel's unique id</param>
        /// <returns></returns>
        /// <response code="200">Submodel retrieved successfully</response>
        /// <response code="404">No Submodel Service Provider found</response>    
        [HttpGet("aas/submodels/{submodelId}", Name = "GetSubmodelById")]
        [ProducesResponseType(typeof(BaSyx.Models.Core.AssetAdministrationShell.Implementations.Submodel), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetSubmodelById(string submodelId)
        {
            var submodelProvider = GetSubmodelServiceProvider(submodelId);

            if (!submodelProvider.Success || submodelProvider?.Entity == null)
                return NotFound(new Result(false, new NotFoundMessage("Submodel")));

            return new OkObjectResult(submodelProvider.Entity.GetBinding());
        }
        /// <summary>
        /// Deletes a specific Submodel from the Asset Administration Shell
        /// </summary>
        /// <param name="submodelId">The Submodel's idShort</param>
        /// <returns></returns>
        /// <response code="204">Submodel deleted successfully</response>
        /// <response code="400">Bad Request</response>    
        [HttpDelete("aas/submodels/{submodelId}", Name = "DeleteSubmodelById")]
        [ProducesResponseType(typeof(Result), 400)]
        public IActionResult DeleteSubmodelById(string submodelId)
        {
            var result = DeleteSubmodel(submodelId);
            return EvaluateResult(result, CrudOperation.Delete);
        }
        /// <summary>
        /// Retrieves all Submodels from the  Asset Administration Shell
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Returns a list of found Submodels</response>
        /// <response code="404">No Submodel Service Providers found</response>       
        [HttpGet("aas/submodels", Name = "GetSubmodels")]
        [ProducesResponseType(typeof(BaSyx.Models.Core.AssetAdministrationShell.Implementations.Submodel[]), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult GetSubmodels()
        {
            var submodelProviders = GetSubmodelServiceProviders();

            if (!submodelProviders.Success || submodelProviders.Entity?.Count() == 0)
                return NotFound(new Result(false, new NotFoundMessage("Submodels")));

            var submodelBindings = submodelProviders.Entity.Select(s => s.GetBinding()).ToArray();

            return new OkObjectResult(submodelBindings);
        }

        #endregion

        #endregion
        
        #region REST-Interface Submodel

        /// <summary>
        /// Retrieves all Properties from the current Submodel
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Returns a list of found Properties</response>
        /// <response code="404">Submodel not found / No Properties found</response>       
        [HttpGet("aas/submodels/{submodelId}/submodel/properties", Name = "RoutedGetProperties")]
        [ProducesResponseType(typeof(Property[]), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult RoutedGetProperties()
        {
            var controller = HttpContext.RequestServices.GetService<SubmodelServices>();
            return controller.GetProperties();            
        }

        /// <summary>
        /// Retrieves all Operations from the current Submodel
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Success</response>
        /// <response code="404">Submodel not found / No Operations found</response>      
        [HttpGet("aas/submodels/{submodelId}/submodel/operations", Name = "RoutedGetOperations")]
        [ProducesResponseType(typeof(Operation[]), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult RoutedGetOperations()
        {
            var controller = HttpContext.RequestServices.GetService<SubmodelServices>();
            return controller.GetOperations();
        }

        /// <summary>
        /// Retrieves all Events from the current Submodel
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Success</response>
        /// <response code="404">Submodel not found / No Events found</response>      
        [HttpGet("aas/submodels/{submodelId}/submodel/events", Name = "RoutedGetEvents")]
        [ProducesResponseType(typeof(Event[]), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult RoutedGetEvents()
        {
            var controller = HttpContext.RequestServices.GetService<SubmodelServices>();
            return controller.GetEvents();
        }

        #region Property - REST-Calls
        /// <summary>
        /// Adds a new Property to the Asset Administration Shell's Submodel
        /// </summary>
        /// <param name="property">The Property's description object</param>
        /// <returns></returns>
        /// <response code="201">DataElement created successfully</response>
        /// <response code="400">Bad Request</response>
        /// <response code="404">Submodel not found</response>
        [HttpPost("aas/submodels/{submodelId}/submodel/properties", Name = "RoutedPostProperty")]
        [ProducesResponseType(typeof(Property), 201)]
        [ProducesResponseType(typeof(Result), 400)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult RoutedPostDataElement([FromBody] IProperty property)
        {
            var controller = HttpContext.RequestServices.GetService<SubmodelServices>();
            return controller.PostProperty(property);
        }
        /// <summary>
        /// Retrieves a specific Property from the Asset Administrations's Submodel
        /// </summary>
        /// <param name="propertyId">The Property's short id</param>
        /// <returns></returns>
        /// <response code="200">Returns the requested Property</response>
        /// <response code="404">Submodel/Property not found</response>     
        [HttpGet("aas/submodels/{submodelId}/submodel/properties/{propertyId}", Name = "RoutedGetProperty")]
        [ProducesResponseType(typeof(Property), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult RoutedGetDataElement(string propertyId)
        {
            var controller = HttpContext.RequestServices.GetService<SubmodelServices>();
            return controller.GetProperty(propertyId);
        }

        /// <summary>
        /// Retrieves the value of a specific Property from the Asset Administrations Shell's Submodel
        /// </summary>
        /// <param name="propertyId">The Property's short id</param>
        /// <returns></returns>
        /// <response code="200">Returns the requested Property's value</response>
        /// <response code="404">Submodel/Property not found</response>     
        [HttpGet("aas/submodels/{submodelId}/submodel/properties/{propertyId}/value", Name = "RoutedGetPropertyValue")]
        [ProducesResponseType(typeof(ElementValue), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult RoutedGetPropertyValue(string propertyId)
        {
            var controller = HttpContext.RequestServices.GetService<SubmodelServices>();
            return controller.GetPropertyValue(propertyId);
        }

        /// <summary>
        /// Updates the Asset Administration Shell's Submodel's Property
        /// </summary>
        /// <param name="propertyId">The Property's short id</param>
        /// <param name="value">The new value</param>
        /// <returns></returns>
        /// <response code="200">Property's value changed successfully</response>
        /// <response code="404">Submodel/Property not found</response>     
        [HttpPut("aas/submodels/{submodelId}/submodel/properties/{propertyId}/value", Name = "RoutedPutPropertyValue")]
        [ProducesResponseType(typeof(ElementValue), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult RoutedPutPropertyValue(string propertyId, [FromBody] IValue value)
        {
            var controller = HttpContext.RequestServices.GetService<SubmodelServices>();
            return controller.PutPropertyValue(propertyId, value);
        }
        /// <summary>
        /// Deletes a specific Property from the Asset Administration Shell's Submodel
        /// </summary>
        /// <param name="propertyId">The Property's short id</param>
        /// <returns></returns>
        /// <response code="204">Property deleted successfully</response>
        /// <response code="404">Submodel not found</response>
        [HttpDelete("aas/submodels/{submodelId}/submodel/properties/{propertyId}", Name = "RoutedDelProperty")]
        [ProducesResponseType(typeof(IResult), 200)]
        public IActionResult RoutedDelProperty(string propertyId)
        {
            var controller = HttpContext.RequestServices.GetService<SubmodelServices>();
            return controller.DelProperty(propertyId);
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
        [HttpPost("aas/submodels/{submodelId}/submodel/operations", Name = "RoutedPostOperation")]
        [ProducesResponseType(typeof(Operation), 201)]
        [ProducesResponseType(typeof(Result), 400)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult RoutedPostOperation([FromBody] IOperation operation)
        {
            var controller = HttpContext.RequestServices.GetService<SubmodelServices>();
            return controller.PostOperation(operation);
        }
        /// <summary>
        /// Retrieves a specific Operation from the Asset Administration Shell's Submodel
        /// </summary>
        /// <param name="operationId">The Operation's short id</param>
        /// <returns></returns>
        /// <response code="200">Success</response>
        /// <response code="404">Submodel/Operation not found</response>     
        [HttpGet("aas/submodels/{submodelId}/submodel/operations/{operationId}", Name = "RoutedGetOperation")]
        [ProducesResponseType(typeof(Operation), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult RoutedGetOperation(string operationId)
        {
            var controller = HttpContext.RequestServices.GetService<SubmodelServices>();
            return controller.GetOperation(operationId);
        }
        /// <summary>
        /// Deletes a specific Operation from the Asset Administration Shell's Submodel
        /// </summary>
        /// <param name="operationId">The Operation's short id</param>
        /// <returns></returns>
        /// <response code="204">Operation deleted successfully</response>  
        /// <response code="404">Submodel not found</response>
        [HttpDelete("aas/submodels/{submodelId}/submodel/operations/{operationId}", Name = "RoutedDelOperation")]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult RoutedDelOperation(string operationId)
        {
            var controller = HttpContext.RequestServices.GetService<SubmodelServices>();
            return controller.DelOperation(operationId);
        }
        /// <summary>
        /// Invokes a specific operation from the Asset Administration Shell' Submodel with a list of input parameters 
        /// </summary>
        /// <param name="operationId">The operation's short id</param>
        /// <param name="timeout">Timeout for the operation to finish</param>
        /// <param name="inputArguments">List of input arguments</param>
        /// <returns></returns>
        /// <response code="200">Operation invoked successfully</response>
        /// <response code="400">Bad Request</response>
        /// <response code="404">Submodel not found</response>
        [HttpPost("aas/submodels/{submodelId}/submodel/operations/{operationId}", Name = "RoutedInvokeOperationRest")]
        [ProducesResponseType(typeof(SubmodelElement[]), 200)]
        [ProducesResponseType(typeof(Result), 400)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult RoutedInvokeOperationRest(string operationId, [FromQuery] int timeout, [FromBody] OperationVariableSet inputArguments)
        {
            var controller = HttpContext.RequestServices.GetService<SubmodelServices>();
            return controller.InvokeOperationRest(operationId, timeout, inputArguments);
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
        [HttpPost("aas/submodels/{submodelId}/submodel/events", Name = "RoutedPostEvent")]
        [ProducesResponseType(typeof(Event), 201)]
        [ProducesResponseType(typeof(Result), 400)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult RoutedPostEvent([FromBody] IEvent eventable)
        {
            var controller = HttpContext.RequestServices.GetService<SubmodelServices>();
            return controller.PostEvent(eventable);
        }
        /// <summary>
        /// Retrieves a specific event from the Asset Administration Shell's Submodel
        /// </summary>
        /// <param name="eventId">The Event's short id</param>
        /// <returns></returns>
        /// <response code="200">Success</response>
        /// <response code="404">Submodel/Event not found</response>     
        [HttpGet("aas/submodels/{submodelId}/submodel/events/{eventId}", Name = "RoutedGetEvent")]
        [ProducesResponseType(typeof(Event), 200)]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult RoutedGetEvent(string eventId)
        {
            var controller = HttpContext.RequestServices.GetService<SubmodelServices>();
            return controller.GetEvent(eventId);
        }
        /// <summary>
        /// Deletes a specific event from the Asset Administration Shell's Submodel
        /// </summary>
        /// <param name="eventId">The Event's short id</param>
        /// <returns></returns>
        /// <response code="204">Event deleted successfully</response>
        /// <response code="404">Submodel not found</response>
        [HttpDelete("aas/submodels/{submodelId}/submodel/events/{eventId}", Name = "RoutedDelEvent")]
        [ProducesResponseType(typeof(Result), 404)]
        public IActionResult RoutedDelEvent(string eventId)
        {
            var controller = HttpContext.RequestServices.GetService<SubmodelServices>();
            return controller.DelEvent(eventId);
        }

        #endregion
        #endregion

        #region Interface Implementation AssetAdministrationShellServiceProvider

        public void RegisterSubmodelServiceProvider(string id, ISubmodelServiceProvider submodelServiceProvider)
        {
            assetAdministrationShellServiceProvider.SubmodelRegistry.RegisterSubmodelServiceProvider(id, submodelServiceProvider);
        }

        public IResult<ISubmodelServiceProvider> GetSubmodelServiceProvider(string id)
        {
            return assetAdministrationShellServiceProvider.SubmodelRegistry.GetSubmodelServiceProvider(id);
        }

        public IResult<ISubmodel> CreateSubmodel(ISubmodel submodel)
        {
            return assetAdministrationShellServiceProvider.CreateSubmodel(submodel);
        }

        public IResult<ISubmodel> RetrieveSubmodel(string submodelId)
        {
            return assetAdministrationShellServiceProvider.RetrieveSubmodel(submodelId);
        }

        public IResult<IElementContainer<ISubmodel>> RetrieveSubmodels()
        {
            return assetAdministrationShellServiceProvider.RetrieveSubmodels();
        }

        public IResult DeleteSubmodel(string submodelId)
        {
            return assetAdministrationShellServiceProvider.DeleteSubmodel(submodelId);
        }

        public IResult<IEnumerable<ISubmodelServiceProvider>> GetSubmodelServiceProviders()
        {
            return assetAdministrationShellServiceProvider.SubmodelRegistry.GetSubmodelServiceProviders();
        }

        public IResult<IAssetAdministrationShell> RetrieveAssetAdministrationShell()
        {
            return assetAdministrationShellServiceProvider.RetrieveAssetAdministrationShell();
        }

        #endregion

        #region Helper Methods


        #endregion
    }
}
