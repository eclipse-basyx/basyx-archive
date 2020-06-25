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
using BaSyx.Models.Connectivity.Descriptors;
using BaSyx.Models.Core.Common;

namespace BaSyx.API.Http.Controllers
{
    /// <summary>
    /// The Submodel Repository Controller
    /// </summary>
    public class SubmodelRepositoryServices : Controller, ISubmodelRepositoryServiceProvider
    {

        private readonly ISubmodelRepositoryServiceProvider repository;

        public IEnumerable<ISubmodel> Submodels => repository.Submodels;
        public ISubmodelRepositoryDescriptor ServiceDescriptor { get; }


        public SubmodelRepositoryServices(ISubmodelRepositoryServiceProvider submodelRepositoryServiceProvider)
        {
            repository = submodelRepositoryServiceProvider;
            ServiceDescriptor = submodelRepositoryServiceProvider.ServiceDescriptor;
        }

        #region REST-Interface SubmodelRepository

        /// <summary>
        /// Retrieves all Submodels from the repository service endpoint
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Returns a list of found Submodels</response>
        /// <response code="404">No Submodels found</response>            
        /// <response code="400">Bad Request</response>    
        /// <response code="502">Bad Gateway</response>
        [HttpGet("submodels", Name = "RetrieveAllSubmodels")]
        [ProducesResponseType(typeof(IResult<List<ISubmodel>>), 200)]
        public IActionResult RetrieveAllSubmodels()
        {
            var result = RetrieveSubmodels();
            return result.CreateActionResult(CrudOperation.Retrieve);
        }
        /// <summary>
        /// Retrieves a specific Submodel from the repository service endpint
        /// </summary>
        /// <param name="submodelId">The Submodel's short id</param>
        /// <returns></returns>
        /// <response code="200">Returns the requested Submodel</response>
        /// <response code="404">No Submodel found</response>     
        /// <response code="400">Bad Request</response>         
        /// <response code="502">Bad Gateway</response>
        [HttpGet("submodels/{submodelId}", Name = "RetrieveSubmodelByIdShort")]
        [ProducesResponseType(typeof(IResult<ISubmodel>), 200)]
        public IActionResult RetrieveSubmodelByIdShort(string submodelId)
        {
            var result = RetrieveSubmodel(submodelId);
            return result.CreateActionResult(CrudOperation.Retrieve);
        }
        /// <summary>
        /// Updates a specific Submodel at the repository service endpint
        /// </summary>
        /// <param name="submodelId">The Submodel's unique id</param>
        /// <param name="submodel">The updated Submodel</param>
        /// <returns></returns>
        /// <response code="200">Submodel updated successfully</response>
        /// <response code="400">Bad Request</response>           
        /// <response code="502">Bad Gateway</response>   
        [HttpPut("submodels/{submodelId}", Name = "UpdateSubmodelByIdShort")]
        [ProducesResponseType(typeof(IResult), 200)]
        public IActionResult UpdateSubmodelByIdShort(string submodelId, [FromBody] ISubmodel submodel)
        {
            var result = UpdateSubmodel(submodelId, submodel);
            return result.CreateActionResult(CrudOperation.Update);
        }
        /// <summary>
        /// Creates a new Submodel at the repository service endpoint
        /// </summary>
        /// <param name="submodel">The serialized Submodel object</param>
        /// <returns></returns>
        /// <response code="201">Submodel created successfully</response>
        /// <response code="400">Bad Request</response>             
        /// <response code="502">Bad Gateway</response> 
        [HttpPost("submodels", Name = "CreateNewSubmodel")]
        [ProducesResponseType(typeof(IResult<ISubmodel>), 201)]
        public IActionResult CreateNewSubmodel([FromBody] ISubmodel submodel)
        {
            var result = CreateSubmodel(submodel);
            return result.CreateActionResult(CrudOperation.Create);
        }
        /// <summary>
        /// Deletes a specific Submodel at the repository service endpoint
        /// </summary>
        /// <param name="submodelId">The Submodel's unique id</param>
        /// <returns></returns>
        /// <response code="200">Submodel deleted successfully</response>
        /// <response code="400">Bad Request</response>      
        /// <response code="502">Bad Gateway</response>
        [HttpDelete("submodels/{submodelId}", Name = "DeleteSubmodelByIdShort")]
        [ProducesResponseType(typeof(IResult), 200)]
        public IActionResult DeleteSubmodelByIdShort(string submodelId)
        {
            var result = DeleteSubmodel(submodelId);
            return result.CreateActionResult(CrudOperation.Delete);
        }

        #endregion
        
        #region Helper Methods

        private static IActionResult AggregateResultHandling(IResult result)
        {
            if (result != null)
            {
                var objResult = new ObjectResult(result);

                if (result.Success)
                {
                    if (result.Entity == null)
                        objResult.StatusCode = 404;
                    else
                        objResult.StatusCode = 200;
                }
                else if (Utils.ResultHandling.Utils.TryParseStatusCode(result, out int httpStatusCode))
                    objResult.StatusCode = httpStatusCode;
                else
                    objResult.StatusCode = 502;

                return objResult;
            }
            return new BadRequestResult();
        }
        #endregion

        #region Interface Implementation SubmodelRepository
        public void RegisterSubmodelServiceProvider(string id, ISubmodelServiceProvider submodelServiceProvider)
        {
            repository.RegisterSubmodelServiceProvider(id, submodelServiceProvider);
        }

        public ISubmodelServiceProvider GetSubmodelServiceProvider(string id)
        {
            return repository.GetSubmodelServiceProvider(id);
        }

        public IEnumerable<ISubmodelServiceProvider> GetSubmodelServiceProviders()
        {
            return repository.GetSubmodelServiceProviders();
        }

        public void BindTo(IEnumerable<ISubmodel> element)
        {
            repository.BindTo(element);
        }

        public IEnumerable<ISubmodel> GetBinding()
        {
            return repository.GetBinding();
        }

        public IResult<ISubmodel> CreateSubmodel(ISubmodel submodel)
        {
            return repository.CreateSubmodel(submodel);
        }

        public IResult<ISubmodel> RetrieveSubmodel(string submodelId)
        {
            return repository.RetrieveSubmodel(submodelId);
        }

        public IResult<IElementContainer<ISubmodel>> RetrieveSubmodels()
        {
            return repository.RetrieveSubmodels();
        }

        public IResult UpdateSubmodel(string submodelId, ISubmodel submodel)
        {
            return repository.UpdateSubmodel(submodelId, submodel);
        }

        public IResult DeleteSubmodel(string submodelId)
        {
            return repository.DeleteSubmodel(submodelId);
        }




        #endregion
    }
}
