using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Utils.ResultHandling;
using BaSys40.API.ServiceProvider;
using BaSys40.Models.Core.AssetAdministrationShell.Implementations;
using BaSys40.Models.Core.AssetAdministrationShell;
using BaSys40.Models.Connectivity;
using static BaSys40.Utils.ResultHandling.Utils;
using System.Linq;

namespace BaSys40.Component.REST.Controllers
{
    /// <summary>
    /// All Asset Administration Shell Services provided by the component
    /// </summary>
    public class AssetAdministrationShellServices : Controller, IAssetAdministrationShellServiceProvider
    {
        private readonly IAssetAdministrationShellServiceProvider assetAdministrationShellServiceProvider;

        public IServiceDescriptor ServiceDescriptor { get; }

        public IAssetAdministrationShell AssetAdministrationShell => assetAdministrationShellServiceProvider.GetBinding();

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
        [ProducesResponseType(typeof(Result<AssetAdministrationShellDescriptor>), 200)]
        public IActionResult GetAAS()
        {
            var result = GetBinding();
            if (result != null)
            {
                var objResult = new ObjectResult(new Result<AssetAdministrationShellDescriptor>(true, new AssetAdministrationShellDescriptor(result, GetEndpoint())));
                return objResult;
            }
            return StatusCode(502);
        }

        public HttpEndpoint GetEndpoint()
        {
            var host = $"{HttpContext.Request.Scheme}://{HttpContext.Request.Host}{HttpContext.Request.Path}";
            return new HttpEndpoint(host);
        }

        #region Submodel - REST-Calls
        /// <summary>
        /// Adds a new Submodel to an existing Asset Administration Shell
        /// </summary>
        /// <param name="submodel">The Submodel's description object</param>
        /// <returns></returns>
        /// <response code="201">Submodel created successfully</response>
        /// <response code="400">Bad Request</response>       
        /// <response code="502">Bad Gateway</response>
        [HttpPost("aas/submodels", Name = "PostSubmodel")]
        [ProducesResponseType(typeof(IResult<ISubmodel>), 201)]
        public IActionResult PostSubmodel([FromBody] ISubmodel submodel)
        {
            var result = CreateSubmodel(submodel);
            return EvaluateResult(result, CrudOperation.Create);
        }
        /// <summary>
        /// Retrieves a specific Submodel from a specific Asset Administration Shell
        /// </summary>
        /// <param name="submodelId">The Submodel's unique id</param>
        /// <returns></returns>
        /// <response code="200">Submodel retrieved successfully</response>
        /// <response code="404">No Submodel found</response>    
        /// <response code="502">Bad Gateway</response> 
        [HttpGet("aas/submodels/{submodelId}", Name = "GetSubmodelServiceProviderBinding")]
        [ProducesResponseType(typeof(IResult<ISubmodel>), 200)]
        public IActionResult GetSubmodelServiceProviderBinding(string submodelId)
        {
            var submodelProvider = GetSubmodelServiceProvider(submodelId);

            if (submodelProvider == null)
                return NotFound(new Result(false, new Message(MessageType.Information, "Submodel not found", "404")));

            return new OkObjectResult(new Result<ISubmodel>(true, submodelProvider.GetBinding()));
        }
        /// <summary>
        /// Deletes a specific Submodel from the Asset Administration Shell
        /// </summary>
        /// <param name="submodelId">The Submodel's idShort</param>
        /// <returns></returns>
        /// <response code="204">Submodel deleted successfully</response>
        /// <response code="400">Bad Request</response>    
        /// <response code="502">Bad Gateway</response>
        [HttpDelete("aas/submodels/{submodelId}", Name = "DelSubmodel")]
        [ProducesResponseType(typeof(IResult), 204)]
        public IActionResult DelSubmodel(string submodelId)
        {
            var result = DeleteSubmodel(submodelId);
            return EvaluateResult(result, CrudOperation.Delete);
        }
        /// <summary>
        /// Retrieves all Submodels from the current Asset Administration Shell
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Returns a list of found Submodels</response>
        /// <response code="404">No Submodels found</response>      
        /// <response code="502">Bad Gateway</response>   
        [HttpGet("aas/submodels", Name = "GetSubmodels")]
        [ProducesResponseType(typeof(IResult<ElementContainer<ISubmodel>>), 200)]
        public IActionResult GetSubmodels()
        {
            var submodelProviders = GetSubmodelServiceProviders();

            if (submodelProviders?.Count() == 0)
                return NotFound(new Result(false, new Message(MessageType.Information, "No Submodels found", "404")));

            ElementContainer<ISubmodel> submodels = new ElementContainer<ISubmodel>();
            foreach (var provider in submodelProviders)
            {
                var submodel = provider.GetBinding();
                submodels.Add(submodel);
            }
            return new OkObjectResult(new Result<ElementContainer<ISubmodel>>(true, submodels));
        }

        #endregion

        #endregion

        #region Interface Implementation AssetAdministrationShellServiceProvider

        public void RegisterSubmodelServiceProvider(string id, ISubmodelServiceProvider submodelServiceProvider)
        {
            assetAdministrationShellServiceProvider.RegisterSubmodelServiceProvider(id, submodelServiceProvider);
        }

        public ISubmodelServiceProvider GetSubmodelServiceProvider(string id)
        {
            return assetAdministrationShellServiceProvider.GetSubmodelServiceProvider(id);
        }

        public IResult<ISubmodel> CreateSubmodel(ISubmodel submodel)
        {
            return assetAdministrationShellServiceProvider.CreateSubmodel(submodel);
        }

        public IResult<ISubmodel> RetrieveSubmodel(string submodelId)
        {
            return assetAdministrationShellServiceProvider.RetrieveSubmodel(submodelId);
        }

        public IResult<ElementContainer<ISubmodel>> RetrieveSubmodels()
        {
            return assetAdministrationShellServiceProvider.RetrieveSubmodels();
        }

        public IResult DeleteSubmodel(string submodelId)
        {
            return assetAdministrationShellServiceProvider.DeleteSubmodel(submodelId);
        }

        public IEnumerable<ISubmodelServiceProvider> GetSubmodelServiceProviders()
        {
            return assetAdministrationShellServiceProvider.GetSubmodelServiceProviders();
        }

        #endregion

        #region Helper Methods


        #endregion
    }
}
