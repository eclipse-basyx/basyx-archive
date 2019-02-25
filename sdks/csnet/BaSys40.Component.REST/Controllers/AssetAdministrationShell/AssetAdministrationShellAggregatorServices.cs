using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Utils.ResultHandling;
using static BaSys40.Utils.ResultHandling.Utils;
using BaSys40.API.ServiceProvider;
using BaSys40.Models.Connectivity;

namespace BaSys40.Component.REST.Controllers
{
    /// <summary>
    /// All Asset Administration Shell Services provided by the component
    /// </summary>
    public class AssetAdministrationShellAggregatorServices : Controller, IAssetAdministrationShellAggregatorServiceProvider
    {

        private readonly IAssetAdministrationShellAggregatorServiceProvider aggregator;

        public IEnumerable<IAssetAdministrationShell> AssetAdministrationShells => aggregator.AssetAdministrationShells;
        public IServiceDescriptor ServiceDescriptor { get; }


        public AssetAdministrationShellAggregatorServices(IAssetAdministrationShellAggregatorServiceProvider assetAdministrationShellAggregatorServiceProvider)
        {
            aggregator = assetAdministrationShellAggregatorServiceProvider;
            ServiceDescriptor = assetAdministrationShellAggregatorServiceProvider.ServiceDescriptor;
        }

        #region REST-Interface AssetAdministrationShellAggregator

        /// <summary>
        /// Retrieves all Asset Administration Shells from the endpoint
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Returns a list of found Asset Administration Shells</response>
        /// <response code="404">No Asset Administration Shells found</response>            
        /// <response code="400">Bad Request</response>    
        /// <response code="502">Bad Gateway</response>
        [HttpGet("aasList", Name = "RetrieveAASList")]
        [ProducesResponseType(typeof(IResult<List<IAssetAdministrationShell>>), 200)]
        public IActionResult RetrieveAASList()
        {
            var result = RetrieveAssetAdministrationShells();
            return EvaluateResult(result, CrudOperation.Retrieve);
        }
        /// <summary>
        /// Retrieves a specific Asset Administration Shell
        /// </summary>
        /// <param name="aasId">The AAS's unique id</param>
        /// <returns></returns>
        /// <response code="200">Returns the requested Asset Administration Shell</response>
        /// <response code="404">No Asset Administration Shell found</response>     
        /// <response code="400">Bad Request</response>         
        /// <response code="502">Bad Gateway</response>
        [HttpGet("aasList/{aasId}", Name = "RetrieveAAS")]
        [ProducesResponseType(typeof(IResult<IAssetAdministrationShell>), 200)]
        public IActionResult RetrieveAAS(string aasId)
        {
            var result = RetrieveAssetAdministrationShell(aasId);
            return EvaluateResult(result, CrudOperation.Retrieve);
        }
        /// <summary>
        /// Updates a specific Asset Administration Shell
        /// </summary>
        /// <param name="aasId">The AAS's unique id</param>
        /// <param name="aas">The updated AAS</param>
        /// <returns></returns>
        /// <response code="204">Asset Administration Shell updated successfully</response>
        /// <response code="400">Bad Request</response>           
        /// <response code="502">Bad Gateway</response>   
        [HttpPut("aasList/{aasId}", Name = "UpdateAAS")]
        [ProducesResponseType(typeof(IResult), 204)]
        public IActionResult UpdateAAS(string aasId, [FromBody] IAssetAdministrationShell aas)
        {
            var result = UpdateAssetAdministrationShell(aasId, aas);
            return EvaluateResult(result, CrudOperation.Update);
        }
        /// <summary>
        /// Creates a new Asset Administration Shell at the endpoint
        /// </summary>
        /// <param name="aas">The AAS's description object</param>
        /// <returns></returns>
        /// <response code="201">Asset Administration Shell created successfully</response>
        /// <response code="400">Bad Request</response>             
        /// <response code="502">Bad Gateway</response> 
        [HttpPost("aasList", Name = "CreateAAS")]
        [ProducesResponseType(typeof(IResult<IAssetAdministrationShell>), 201)]
        public IActionResult CreateAAS([FromBody] IAssetAdministrationShell aas)
        {
            var result = CreateAssetAdministrationShell(aas);
            return EvaluateResult(result, CrudOperation.Create);
        }
        /// <summary>
        /// Deletes a specific Asset Administration Shell
        /// </summary>
        /// <param name="aasId">The AAS's unique id</param>
        /// <returns></returns>
        /// <response code="204">Asset Administration Shell deleted successfully</response>
        /// <response code="400">Bad Request</response>      
        /// <response code="502">Bad Gateway</response>
        [HttpDelete("aasList/{aasId}", Name = "DeleteAAS")]
        [ProducesResponseType(typeof(IResult), 204)]
        public IActionResult DeleteAAS(string aasId)
        {
            var result = DeleteAssetAdministrationShell(aasId);
            return EvaluateResult(result, CrudOperation.Delete);
        }

        #endregion


        #region Interface Implementation AssetAdministrationShellAggregator

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

        public IResult<List<IAssetAdministrationShell>> RetrieveAssetAdministrationShells()
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
