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
    /// The Asset Administration Shell Repository Controller
    /// </summary>
    public class AssetAdministrationShellRepositoryServices : Controller, IAssetAdministrationShellRepositoryServiceProvider
    {

        private readonly IAssetAdministrationShellRepositoryServiceProvider repository;

        public IEnumerable<IAssetAdministrationShell> AssetAdministrationShells => repository.AssetAdministrationShells;
        public IAssetAdministrationShellRepositoryDescriptor ServiceDescriptor { get; }


        public AssetAdministrationShellRepositoryServices(IAssetAdministrationShellRepositoryServiceProvider assetAdministrationShellRepositoryServiceProvider)
        {
            repository = assetAdministrationShellRepositoryServiceProvider;
            ServiceDescriptor = assetAdministrationShellRepositoryServiceProvider.ServiceDescriptor;
        }

        #region REST-Interface AssetAdministrationShellRepository

        /// <summary>
        /// Retrieves all Asset Administration Shells from the repository service endpoint
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Returns a list of found Asset Administration Shells</response>
        /// <response code="404">No Asset Administration Shells found</response>            
        /// <response code="400">Bad Request</response>    
        /// <response code="502">Bad Gateway</response>
        [HttpGet("shells", Name = "GetAllAssetAdministrationShells")]
        [ProducesResponseType(typeof(List<BaSyx.Models.Core.AssetAdministrationShell.Implementations.AssetAdministrationShell>), 200)]
        public IActionResult GetAllAssetAdministrationShells()
        {
            var result = RetrieveAssetAdministrationShells();
            return result.CreateActionResult(CrudOperation.Retrieve);
        }
        /// <summary>
        /// Retrieves a specific Asset Administration Shell from the repository service endpoint
        /// </summary>
        /// <param name="aasId">The Asset Administration Shell's unique id</param>
        /// <returns></returns>
        /// <response code="200">Returns the requested Asset Administration Shell</response>
        /// <response code="404">No Asset Administration Shell found</response>     
        /// <response code="400">Bad Request</response>         
        /// <response code="502">Bad Gateway</response>
        [HttpGet("shells/{aasId}", Name = "GetAssetAdministrationShellById")]
        [ProducesResponseType(typeof(BaSyx.Models.Core.AssetAdministrationShell.Implementations.AssetAdministrationShell), 200)]
        public IActionResult GetAssetAdministrationShellById(string aasId)
        {
            if (string.IsNullOrEmpty(aasId))
                return ResultHandling.NullResult(nameof(aasId));

            var result = RetrieveAssetAdministrationShell(aasId);
            return result.CreateActionResult(CrudOperation.Retrieve);
        }

        /// <summary>
        /// Creates or updates a Asset Administration Shell at the repository service endpoint
        /// </summary>
        /// <param name="aas">The serialized Asset Administration Shell object</param>
        /// <returns></returns>
        /// <response code="201">Asset Administration Shell created successfully</response>
        /// <response code="400">Bad Request</response>             
        /// <response code="502">Bad Gateway</response> 
        [HttpPost("shells", Name = "PutAssetAdministrationShell")]
        [ProducesResponseType(typeof(BaSyx.Models.Core.AssetAdministrationShell.Implementations.AssetAdministrationShell), 201)]
        public IActionResult PutAssetAdministrationShell([FromBody] IAssetAdministrationShell aas)
        {
            if (aas == null)
                return ResultHandling.NullResult(nameof(aas));

            var result = CreateAssetAdministrationShell(aas);
            return result.CreateActionResult(CrudOperation.Create);
        }
        /// <summary>
        /// Deletes a specific Asset Administration Shell at the repository service endpoint
        /// </summary>
        /// <param name="aasId">The Asset Administration Shell's unique id</param>
        /// <returns></returns>
        /// <response code="200">Asset Administration Shell deleted successfully</response>
        /// <response code="400">Bad Request</response>      
        /// <response code="502">Bad Gateway</response>
        [HttpDelete("shells/{aasId}", Name = "DeleteAssetAdministrationShellById")]
        [ProducesResponseType(typeof(Result), 200)]
        public IActionResult DeleteAssetAdministrationShellById(string aasId)
        {
            if (string.IsNullOrEmpty(aasId))
                return ResultHandling.NullResult(nameof(aasId));

            var result = DeleteAssetAdministrationShell(aasId);
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

        #region Interface Implementation AssetAdministrationShellRepository
        public void RegisterAssetAdministrationShellServiceProvider(string id, IAssetAdministrationShellServiceProvider assetAdministrationShellServiceProvider)
        {
            repository.RegisterAssetAdministrationShellServiceProvider(id, assetAdministrationShellServiceProvider);
        }

        public IAssetAdministrationShellServiceProvider GetAssetAdministrationShellServiceProvider(string id)
        {
            return repository.GetAssetAdministrationShellServiceProvider(id);
        }

        public IEnumerable<IAssetAdministrationShellServiceProvider> GetAssetAdministrationShellServiceProviders()
        {
            return repository.GetAssetAdministrationShellServiceProviders();
        }

        public void BindTo(IEnumerable<IAssetAdministrationShell> element)
        {
            repository.BindTo(element);
        }

        public IEnumerable<IAssetAdministrationShell> GetBinding()
        {
            return repository.GetBinding();
        }

        public IResult<IAssetAdministrationShell> CreateAssetAdministrationShell(IAssetAdministrationShell aas)
        {
            return repository.CreateAssetAdministrationShell(aas);
        }

        public IResult<IAssetAdministrationShell> RetrieveAssetAdministrationShell(string aasId)
        {
            return repository.RetrieveAssetAdministrationShell(aasId);
        }

        public IResult<IElementContainer<IAssetAdministrationShell>> RetrieveAssetAdministrationShells()
        {
            return repository.RetrieveAssetAdministrationShells();
        }

        public IResult UpdateAssetAdministrationShell(string aasId, IAssetAdministrationShell aas)
        {
            return repository.UpdateAssetAdministrationShell(aasId, aas);
        }

        public IResult DeleteAssetAdministrationShell(string aasId)
        {
            return repository.DeleteAssetAdministrationShell(aasId);
        }




        #endregion
    }
}
