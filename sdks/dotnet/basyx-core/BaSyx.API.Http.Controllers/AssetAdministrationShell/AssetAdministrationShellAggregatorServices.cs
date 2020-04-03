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
    /// The Asset Administration Shell Aggregator Controller
    /// </summary>
    public class AssetAdministrationShellAggregatorServices : Controller, IAssetAdministrationShellAggregatorServiceProvider
    {

        private readonly IAssetAdministrationShellAggregatorServiceProvider aggregator;

        public IEnumerable<IAssetAdministrationShell> AssetAdministrationShells => aggregator.AssetAdministrationShells;
        public IAssetAdministrationShellAggregatorDescriptor ServiceDescriptor { get; }


        public AssetAdministrationShellAggregatorServices(IAssetAdministrationShellAggregatorServiceProvider assetAdministrationShellAggregatorServiceProvider)
        {
            aggregator = assetAdministrationShellAggregatorServiceProvider;
            ServiceDescriptor = assetAdministrationShellAggregatorServiceProvider.ServiceDescriptor;
        }

        #region REST-Interface AssetAdministrationShellAggregator

        /// <summary>
        /// Retrieves all Asset Administration Shells from the aggregator service endpoint
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Returns a list of found Asset Administration Shells</response>
        /// <response code="404">No Asset Administration Shells found</response>            
        /// <response code="400">Bad Request</response>    
        /// <response code="502">Bad Gateway</response>
        [HttpGet("shells", Name = "RetrieveAllAssetAdministrationShells")]
        [ProducesResponseType(typeof(IResult<List<IAssetAdministrationShell>>), 200)]
        public IActionResult RetrieveAllAssetAdministrationShells()
        {
            var result = RetrieveAssetAdministrationShells();
            return result.CreateActionResult(CrudOperation.Retrieve);
        }
        /// <summary>
        /// Retrieves a specific Asset Administration Shell from the aggregator service endpint
        /// </summary>
        /// <param name="aasId">The Asset Administration Shell's short id</param>
        /// <returns></returns>
        /// <response code="200">Returns the requested Asset Administration Shell</response>
        /// <response code="404">No Asset Administration Shell found</response>     
        /// <response code="400">Bad Request</response>         
        /// <response code="502">Bad Gateway</response>
        [HttpGet("shells/{aasId}", Name = "RetrieveAssetAdministrationShellByIdShort")]
        [ProducesResponseType(typeof(IResult<IAssetAdministrationShell>), 200)]
        public IActionResult RetrieveAssetAdministrationShellByIdShort(string aasId)
        {
            var result = RetrieveAssetAdministrationShell(aasId);
            return result.CreateActionResult(CrudOperation.Retrieve);
        }
        /// <summary>
        /// Updates a specific Asset Administration Shell at the aggregator service endpint
        /// </summary>
        /// <param name="aasId">The Asset Administration Shell's unique id</param>
        /// <param name="aas">The updated Asset Administration Shell</param>
        /// <returns></returns>
        /// <response code="200">Asset Administration Shell updated successfully</response>
        /// <response code="400">Bad Request</response>           
        /// <response code="502">Bad Gateway</response>   
        [HttpPut("shells/{aasId}", Name = "UpdateAssetAdministrationShellByIdShort")]
        [ProducesResponseType(typeof(IResult), 200)]
        public IActionResult UpdateAssetAdministrationShellByIdShort(string aasId, [FromBody] IAssetAdministrationShell aas)
        {
            var result = UpdateAssetAdministrationShell(aasId, aas);
            return result.CreateActionResult(CrudOperation.Update);
        }
        /// <summary>
        /// Creates a new Asset Administration Shell at the aggregator service endpoint
        /// </summary>
        /// <param name="aas">The serialized Asset Administration Shell object</param>
        /// <returns></returns>
        /// <response code="201">Asset Administration Shell created successfully</response>
        /// <response code="400">Bad Request</response>             
        /// <response code="502">Bad Gateway</response> 
        [HttpPost("shells", Name = "CreateNewAssetAdministrationShell")]
        [ProducesResponseType(typeof(IResult<IAssetAdministrationShell>), 201)]
        public IActionResult CreateNewAssetAdministrationShell([FromBody] IAssetAdministrationShell aas)
        {
            var result = CreateAssetAdministrationShell(aas);
            return result.CreateActionResult(CrudOperation.Create);
        }
        /// <summary>
        /// Deletes a specific Asset Administration Shell at the aggregator service endpoint
        /// </summary>
        /// <param name="aasId">The Asset Administration Shell's unique id</param>
        /// <returns></returns>
        /// <response code="200">Asset Administration Shell deleted successfully</response>
        /// <response code="400">Bad Request</response>      
        /// <response code="502">Bad Gateway</response>
        [HttpDelete("shells/{aasId}", Name = "DeleteAssetAdministrationShellByIdShort")]
        [ProducesResponseType(typeof(IResult), 200)]
        public IActionResult DeleteAssetAdministrationShellByIdShort(string aasId)
        {
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

        #region Interface Implementation AssetAdministrationShellAggregator
        public void RegisterAssetAdministrationShellServiceProvider(string id, IAssetAdministrationShellServiceProvider assetAdministrationShellServiceProvider)
        {
            aggregator.RegisterAssetAdministrationShellServiceProvider(id, assetAdministrationShellServiceProvider);
        }

        public IAssetAdministrationShellServiceProvider GetAssetAdministrationShellServiceProvider(string id)
        {
            return aggregator.GetAssetAdministrationShellServiceProvider(id);
        }

        public IEnumerable<IAssetAdministrationShellServiceProvider> GetAssetAdministrationShellServiceProviders()
        {
            return aggregator.GetAssetAdministrationShellServiceProviders();
        }

        public void BindTo(IEnumerable<IAssetAdministrationShell> element)
        {
            aggregator.BindTo(element);
        }

        public IEnumerable<IAssetAdministrationShell> GetBinding()
        {
            return aggregator.GetBinding();
        }

        public IResult<IAssetAdministrationShell> CreateAssetAdministrationShell(IAssetAdministrationShell aas)
        {
            return aggregator.CreateAssetAdministrationShell(aas);
        }

        public IResult<IAssetAdministrationShell> RetrieveAssetAdministrationShell(string aasId)
        {
            return aggregator.RetrieveAssetAdministrationShell(aasId);
        }

        public IResult<IElementContainer<IAssetAdministrationShell>> RetrieveAssetAdministrationShells()
        {
            return aggregator.RetrieveAssetAdministrationShells();
        }

        public IResult UpdateAssetAdministrationShell(string aasId, IAssetAdministrationShell aas)
        {
            return aggregator.UpdateAssetAdministrationShell(aasId, aas);
        }

        public IResult DeleteAssetAdministrationShell(string aasId)
        {
            return aggregator.DeleteAssetAdministrationShell(aasId);
        }




        #endregion
    }
}
