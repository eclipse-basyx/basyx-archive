using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Utils.ResultHandling;
using BaSys40.API.ServiceProvider;
using static BaSys40.Utils.ResultHandling.Utils;
using BaSys40.Utils.Client;
using System;
using BaSys40.API.AssetAdministrationShell;
using BaSys40.Models.Core.AssetAdministrationShell.Implementations;
using BaSys40.Models.Connectivity;

namespace BaSys40.Component.REST.Controllers
{
    /// <summary>
    /// All Asset Administration Shell Services provided by the component
    /// </summary>
    public class SubmodelServices : Controller, ISubmodelServiceProvider
    {
        private readonly ISubmodelServiceProvider submodelServiceProvider;

        public ISubmodel Submodel => submodelServiceProvider.GetBinding();
        public IServiceDescriptor ServiceDescriptor { get; }

        public SubmodelServices(ISubmodelServiceProvider submodelServiceProvider)
        {
            this.submodelServiceProvider = submodelServiceProvider;
            ServiceDescriptor = submodelServiceProvider.ServiceDescriptor;
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
        /// Retrieves the Submodel
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Success</response>
        /// <response code="502">Bad Gateway - Submodel not available</response>       
        [HttpGet("submodel", Name = "GetSubmodel")]
        [ProducesResponseType(typeof(IResult<ISubmodel>), 200)]
        public IActionResult GetSubmodel()
        {
            var result = submodelServiceProvider.GetBinding();
            if (result != null)
                return new OkObjectResult(new Result<ISubmodel>(true, result));

            return StatusCode(502);
        }

        /// <summary>
        /// Retrieves all DataElements from the current Submodel
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Returns a list of found DataElements</response>
        /// <response code="404">Submodel not found / No DataElements found</response>      
        /// <response code="502">Bad Gateway</response>   
        [HttpGet("submodel/dataElements", Name = "GetDataElements")]
        [ProducesResponseType(typeof(IResult<ElementContainer<IDataElement>>), 200)]
        public IActionResult GetDataElements(string submodelId)
        {
            var result = RetrieveDataElements();
            return EvaluateResult(result, CrudOperation.Retrieve);
        }

        /// <summary>
        /// Retrieves all Operations from the current Submodel
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Success</response>
        /// <response code="404">Submodel not found / No Operations found</response>      
        /// <response code="502">Bad Gateway</response>   
        [HttpGet("submodel/operations", Name = "GetOperations")]
        [ProducesResponseType(typeof(IResult<ElementContainer<IOperation>>), 200)]
        public IActionResult GetOperations(string submodelId)
        {
            var result = RetrieveOperations();
            return EvaluateResult(result, CrudOperation.Retrieve);
        }

        /// <summary>
        /// Retrieves all Events from the current Submodel
        /// </summary>
        /// <returns></returns>
        /// <response code="200">Success</response>
        /// <response code="404">Submodel not found / No Events found</response>      
        /// <response code="502">Bad Gateway</response>   
        [HttpGet("submodel/events", Name = "GetEvents")]
        [ProducesResponseType(typeof(IResult<ElementContainer<IEvent>>), 200)]
        public IActionResult GetEvents(string submodelId)
        {
            var result = RetrieveEvents();
            return EvaluateResult(result, CrudOperation.Retrieve);
        }



        #region DataElement - REST-Calls
        /// <summary>
        /// Adds a new DataElement to the Asset Administration Shell's Submodel
        /// </summary>
        /// <param name="submodelId">The Submodel's short id</param>
        /// <param name="dataElement">The DataElement's description object</param>
        /// <returns></returns>
        /// <response code="201">DataElement created successfully</response>
        /// <response code="400">Bad Request</response>
        /// <response code="404">Submodel not found</response>
        /// <response code="502">Bad Gateway</response>
        [HttpPost("submodel/dataElements", Name = "PostDataElement")]
        [ProducesResponseType(typeof(IResult<IDataElement>), 201)]
        public IActionResult PostDataElement(string submodelId, [FromBody] IDataElement dataElement)
        {
            var result = CreateDataElement(dataElement);
            return EvaluateResult(result, CrudOperation.Create);
        }
        /// <summary>
        /// Retrieves a specific DataElement from the Asset Administrations's Submodel
        /// </summary>
        /// <param name="submodelId">The Submodel's short id</param>
        /// <param name="dataElementId">The DataElement's short id</param>
        /// <returns></returns>
        /// <response code="200">Returns the requested DataElement</response>
        /// <response code="404">Submodel/DataElement not found</response>     
        /// <response code="502">Bad Gateway</response>
        [HttpGet("submodel/dataElements/{dataElementId}", Name = "GetDataElement")]
        [ProducesResponseType(typeof(IResult<IDataElement>), 200)]
        public IActionResult GetDataElement(string submodelId, string dataElementId)
        {
            var result = RetrieveDataElement(dataElementId);
            return EvaluateResult(result, CrudOperation.Retrieve);
        }

        /// <summary>
        /// Retrieves the value of a specific DataElement from the Asset Administrations Shell's Submodel
        /// </summary>
        /// <param name="submodelId">The Submodel's short id</param>
        /// <param name="dataElementId">The DataElement's short id</param>
        /// <returns></returns>
        /// <response code="200">Returns the requested DataElement's value</response>
        /// <response code="404">Submodel/DataElement not found</response>     
        /// <response code="502">Bad Gateway</response>
        [HttpGet("submodel/dataElements/{dataElementId}/value", Name = "GetDataElementValue")]
        [ProducesResponseType(typeof(IResult<IValue>), 200)]
        public IActionResult GetDataElementValue(string submodelId, string dataElementId)
        {
            var result = RetrieveDataElementValue(dataElementId);
            return EvaluateResult(result, CrudOperation.Retrieve);
        }

        /// <summary>
        /// Updates the Asset Administration Shell's Submodel's DataElement
        /// </summary>
        /// <param name="submodelId">The Submodel's short id</param>
        /// <param name="dataElementId">The DataElement's short id</param>
        /// <param name="value">The new value</param>
        /// <returns></returns>
        /// <response code="204">DataElement's value changed successfully</response>
        /// <response code="404">Submodel/DataElement not found</response>     
        /// <response code="502">Bad Gateway</response>
        [HttpPut("submodel/dataElements/{dataElementId}/value", Name = "PutDataElementValue")]
        [ProducesResponseType(typeof(IResult), 204)]
        public IActionResult PutDataElementValue(string submodelId, string dataElementId, [FromBody] IValue value)
        {
            var result = UpdateDataElementValue(dataElementId, value);
            return EvaluateResult(result, CrudOperation.Update);
        }
        /// <summary>
        /// Deletes a specific DataElement from the Asset Administration Shell's Submodel
        /// </summary>
        /// <param name="submodelId">The Submodel's short id</param>
        /// <param name="dataElementId">The DataElement's short id</param>
        /// <returns></returns>
        /// <response code="204">DataElement deleted successfully</response>
        /// <response code="400">Bad Request</response> 
        /// <response code="404">Submodel not found</response>
        /// <response code="502">Bad Gateway</response>
        [HttpDelete("submodel/dataElements/{dataElementId}", Name = "DelDataElement")]
        [ProducesResponseType(typeof(IResult), 204)]
        public IActionResult DelDataElement(string submodelId, string dataElementId)
        {
            var result = DeleteDataElement(dataElementId);
            return EvaluateResult(result, CrudOperation.Delete);
        }
        #endregion
        #region Operation - REST-Calls
        /// <summary>
        /// Adds a new operation to the Asset Administraiton Shell's Submodel
        /// </summary>
        /// <param name="submodelId">The Submodel's short id</param>
        /// <param name="operation">The operation description object</param>
        /// <returns></returns>
        /// <response code="201">Operation created successfully</response>
        /// <response code="400">Bad Request</response>
        /// <response code="404">Submodel not found</response>
        /// <response code="502">Bad Gateway</response>
        [HttpPost("submodel/operations", Name = "PostOperation")]
        [ProducesResponseType(typeof(IResult<IOperation>), 201)]
        public IActionResult PostOperation(string submodelId, [FromBody] IOperation operation)
        {
            var result = CreateOperation(operation);
            return EvaluateResult(result, CrudOperation.Create);
        }
        /// <summary>
        /// Retrieves a specific Operation from the Asset Administration Shell's Submodel
        /// </summary>
        /// <param name="submodelId">The Submodel's short id</param>
        /// <param name="operationId">The Operation's short id</param>
        /// <returns></returns>
        /// <response code="200">Success</response>
        /// <response code="404">Submodel/Operation not found</response>     
        /// <response code="502">Bad Gateway</response>
        [HttpGet("submodel/operations/{operationId}", Name = "GetOperation")]
        [ProducesResponseType(typeof(IResult<IOperation>), 200)]
        public IActionResult GetOperation(string submodelId, string operationId)
        {
            var result = RetrieveOperation(operationId);
            return EvaluateResult(result, CrudOperation.Retrieve);
        }
        /// <summary>
        /// Deletes a specific Operation from the Asset Administration Shell's Submodel
        /// </summary>
        /// <param name="submodelId">The Submodel's short id</param>
        /// <param name="operationId">The Operation's short id</param>
        /// <returns></returns>
        /// <response code="204">Operation deleted successfully</response>
        /// <response code="400">Bad Request</response>    
        /// <response code="404">Submodel not found</response>
        /// <response code="502">Bad Gateway</response>
        [HttpDelete("submodel/operations/{operationId}", Name = "DelOperation")]
        [ProducesResponseType(typeof(IResult), 204)]
        public IActionResult DelOperation(string submodelId, string operationId)
        {
            var result = DeleteOperation(operationId);
            return EvaluateResult(result, CrudOperation.Delete);
        }
        /// <summary>
        /// Invokes a specific operation from the Asset Administration Shell' Submodel with a list of input parameters 
        /// </summary>
        /// <param name="submodelId">The Submodel's short id</param>
        /// <param name="operationId">The operation's short id</param>
        /// <param name="timeout">Timeout for the operation to finish</param>
        /// <param name="inputArguments">List of input arguments</param>
        /// <returns></returns>
        /// <response code="200">Operation invoked successfully</response>
        /// <response code="400">Bad Request</response>
        /// <response code="404">Submodel not found</response>
        /// <response code="502">Bad Gateway</response>
        [HttpPost("submodel/operations/{operationId}", Name = "InvokeOperationRest")]
        [ProducesResponseType(typeof(IResult<List<IArgument>>), 200)]
        public IActionResult InvokeOperationRest(string submodelId, string operationId, [FromQuery] int timeout, [FromBody] List<IArgument> inputArguments)
        {
            List<IArgument> outputArguments = new List<IArgument>();
            IResult result = InvokeOperation(operationId, inputArguments, outputArguments, timeout);

            if (result != null)
            {
                var objResult = new ObjectResult(new Result<List<IArgument>>(result.Success, outputArguments, result.Messages));

                if (result.Success)
                    objResult.StatusCode = 200;
                else if (Utils.ResultHandling.Utils.TryParseStatusCode(result, out int httpStatusCode))
                    objResult.StatusCode = httpStatusCode;
                else
                    objResult.StatusCode = 502;

                return objResult;
            }
            return StatusCode(502);
        }

        #endregion
        #region Event - REST-Calls
        /// <summary>
        /// Adds a new event to the Asset Administration Shell's Submodel
        /// </summary>
        /// <param name="submodelId">The Submodel's short id</param>
        /// <param name="eventable">The Event description object</param>
        /// <returns></returns>
        /// <response code="201">Event created successfully</response>
        /// <response code="400">Bad Request</response>
        /// <response code="404">Submodel not found</response>
        /// <response code="502">Bad Gateway</response>
        [HttpPost("submodel/events", Name = "PostEvent")]
        [ProducesResponseType(typeof(IResult<IEvent>), 201)]
        public IActionResult PostEvent(string submodelId, [FromBody] IEvent eventable)
        {
            var result = CreateEvent(eventable);
            return EvaluateResult(result, CrudOperation.Create);
        }
        /// <summary>
        /// Retrieves a specific event from the Asset Administration Shell's Submodel
        /// </summary>
        /// <param name="submodelId">The Submodel's short id</param>
        /// <param name="eventId">The Event's short id</param>
        /// <returns></returns>
        /// <response code="200">Success</response>
        /// <response code="404">Submodel/Event not found</response>     
        /// <response code="502">Bad Gateway</response>
        [HttpGet("submodel/events/{eventId}", Name = "GetEvent")]
        [ProducesResponseType(typeof(IResult<IEvent>), 200)]
        public IActionResult GetEvent(string submodelId, string eventId)
        {
            var result = RetrieveEvent(eventId);
            return EvaluateResult(result, CrudOperation.Retrieve);
        }
        /// <summary>
        /// Deletes a specific event from the Asset Administration Shell's Submodel
        /// </summary>
        /// <param name="submodelId">The Submodel's short id</param>
        /// <param name="eventId">The Event's short id</param>
        /// <returns></returns>
        /// <response code="204">Event deleted successfully</response>
        /// <response code="400">Bad Request</response> 
        /// <response code="404">Submodel not found</response>
        /// <response code="502">Bad Gateway</response>
        [HttpDelete("submodel/events/{eventId}", Name = "DelEvent")]
        [ProducesResponseType(typeof(IResult), 204)]
        public IActionResult DelEvent(string submodelId, string eventId)
        {
            var result = DeleteEvent(eventId);
            return EvaluateResult(result, CrudOperation.Delete);
        }


        #endregion

        #endregion

        #region Interface Implementation SubmodelServiceProvider

        public IResult<IOperation> CreateOperation(IOperation operation)
        {
            return submodelServiceProvider.CreateOperation(operation);
        }

        public IResult<ElementContainer<IOperation>> RetrieveOperations()
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

        public IResult InvokeOperation(string operationId, List<IArgument> inputArguments, List<IArgument> outputArguments, int timeout)
        {
            return submodelServiceProvider.InvokeOperation(operationId, inputArguments, outputArguments, timeout);
        }

        public IResult<IDataElement> CreateDataElement(IDataElement dataElement)
        {
            return submodelServiceProvider.CreateDataElement(dataElement);
        }

        public IResult<ElementContainer<IDataElement>> RetrieveDataElements()
        {
            return submodelServiceProvider.RetrieveDataElements();
        }

        public IResult<IDataElement> RetrieveDataElement(string dataElementId)
        {
            return submodelServiceProvider.RetrieveDataElement(dataElementId);
        }

        public IResult UpdateDataElementValue(string dataElementId, IValue value)
        {
            return submodelServiceProvider.UpdateDataElementValue(dataElementId, value);
        }

        public IResult DeleteDataElement(string dataElementId)
        {
            return submodelServiceProvider.DeleteDataElement(dataElementId);
        }

        public IResult<IEvent> CreateEvent(IEvent eventable)
        {
            return submodelServiceProvider.CreateEvent(eventable);
        }

        public IResult<ElementContainer<IEvent>> RetrieveEvents()
        {
            return submodelServiceProvider.RetrieveEvents();
        }

        public IResult<IEvent> RetrieveEvent(string eventId)
        {
            return submodelServiceProvider.RetrieveEvent(eventId);
        }

        public IResult ThrowEvent(IPublishableEvent publishableEvent, string topic, Action<IMessagePublishedEventArgs> MessagePublished, byte qosLevel)
        {
            return submodelServiceProvider.ThrowEvent(publishableEvent, topic, MessagePublished, qosLevel);
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

        public IResult<IValue> RetrieveDataElementValue(string dataElementId)
        {
            return submodelServiceProvider.RetrieveDataElementValue(dataElementId);
        }

        public DataElementHandler RetrieveDataElementHandler(string dataElementId)
        {
            return submodelServiceProvider.RetrieveDataElementHandler(dataElementId);
        }

        public void RegisterDataElementHandler(string dataElementId, DataElementHandler handler)
        {
            submodelServiceProvider.RegisterDataElementHandler(dataElementId, handler);
        }

        public void SubscribeUpdates(string dataElementId, Action<IValue> updateFunction)
        {
            submodelServiceProvider.SubscribeUpdates(dataElementId, updateFunction);
        }

        public void PublishUpdate(string dataElementId, IValue value)
        {
            submodelServiceProvider.PublishUpdate(dataElementId, value);
        }

        #endregion

        #region Helper Methods


        #endregion
    }
}
