using BaSys40.API.Platform.Agents;
using BaSys40.API.ServiceProvider;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.AssetAdministrationShell.Implementations;
using BaSys40.Models.Extensions;
using BaSys40.Utils.Client.Http;
using BaSys40.Utils.ResultHandling;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Http;
using System.Text;

namespace BaSys40.Component.REST.Client
{
    public class RestClient : SimpleHttpClient    
    {
        private const string AAS = "aas";
        private const string SUBMODELS = "submodels";
        private const string DATAELEMENTS = "dataElements";
        private const string OPERATIONS = "operations";
        private const string EVENTS = "events";
        private const string VALUE = "value";

        private const string SEPERATOR = "/";
        private const int TIMEOUT = 30000;

        public IPEndPoint Endpoint { get; }

        public RestClient(IPEndPoint endPoint)
        {
            Endpoint = endPoint;
            JsonSerializerSettings = new JsonStandardSettings();
        }

        public Uri GetUri(params string[] pathElements)
        {
            string path = "http://" + Endpoint.Address + ":" + Endpoint.Port + SEPERATOR + AAS;

            if (pathElements?.Length > 0)
                foreach (var pathElement in pathElements)
                {
                    if (!pathElement.EndsWith("/") && !pathElement.StartsWith("/"))
                        path = path + SEPERATOR + pathElement;
                    else
                        path = path + pathElement;
                }
            return new Uri(path);
        }

        public IResult<IAssetAdministrationShell> RetrieveAssetAdministrationShell()
        {
            var request = base.CreateRequest(GetUri(), HttpMethod.Get);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<IAssetAdministrationShell>(response, response.Entity);
        }

        public IResult<ISubmodel> CreateSubmodel(ISubmodel submodel)
        {
            var request = base.CreateJsonContentRequest(GetUri(SUBMODELS), HttpMethod.Post, submodel);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<ISubmodel>(response, response.Entity);
        }

        public IResult<ElementContainer<ISubmodel>> RetrieveSubmodels()
        {
            var request = base.CreateRequest(GetUri(SUBMODELS), HttpMethod.Get);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<ElementContainer<ISubmodel>>(response, response.Entity);
        }

        public IResult<ISubmodel> RetrieveSubmodel(string submodelId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId), HttpMethod.Get);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<ISubmodel>(response, response.Entity);
        }

        public IResult DeleteSubmodel(string submodelId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId), HttpMethod.Delete);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse(response, response.Entity);
        }

        public IResult<IOperation> CreateOperation(string submodelId, IOperation operation)
        {
            var request = base.CreateJsonContentRequest(GetUri(SUBMODELS, submodelId, OPERATIONS), HttpMethod.Post, operation);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<IOperation>(response, response.Entity);
        }

        public IResult<ElementContainer<IOperation>> RetrieveOperations(string submodelId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, OPERATIONS), HttpMethod.Get);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<ElementContainer<IOperation>>(response, response.Entity);
        }

        public IResult<IOperation> RetrieveOperation(string submodelId, string operationId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, OPERATIONS, operationId), HttpMethod.Get);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<IOperation>(response, response.Entity);
        }

        public IResult DeleteOperation(string submodelId, string operationId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, OPERATIONS, operationId), HttpMethod.Delete);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse(response, response.Entity);
        }

        public IResult InvokeOperation(string submodelId, string operationId, List<IArgument> inputArguments, List<IArgument> outputArguments, int timeout)
        {
            var uri = GetUri(SUBMODELS, submodelId, OPERATIONS, operationId);
            var requestUri = new Uri(uri.OriginalString + "?timeout=" + timeout);
            var request = base.CreateJsonContentRequest(requestUri, HttpMethod.Post, inputArguments);
            var response = base.SendRequest(request, TIMEOUT);
            var evaluatedResponse = base.EvaluateResponse(response, response.Entity);
            outputArguments = evaluatedResponse.GetEntity<List<IArgument>>();
            return evaluatedResponse;
        }

        public IResult<IDataElement> CreateDataElement(string submodelId, IDataElement dataElement)
        {
            var request = base.CreateJsonContentRequest(GetUri(SUBMODELS, submodelId, DATAELEMENTS), HttpMethod.Post, dataElement);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<IDataElement>(response, response.Entity);
        }

        public IResult<ElementContainer<IDataElement>> RetrieveDataElements(string submodelId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, DATAELEMENTS), HttpMethod.Get);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<ElementContainer<IDataElement>>(response, response.Entity);
        }

        public IResult<IDataElement> RetrieveDataElement(string submodelId, string dataElementId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, DATAELEMENTS, dataElementId), HttpMethod.Get);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<IDataElement>(response, response.Entity);
        }

        public IResult UpdateDataElementValue(string submodelId, string dataElementId, IValue value)
        {
            var request = base.CreateJsonContentRequest(GetUri(SUBMODELS, submodelId, DATAELEMENTS, dataElementId, VALUE), HttpMethod.Put, value);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse(response, response.Entity);
        }

        public IResult<IValue> RetrieveDataElementValue(string submodelId, string dataElementId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, DATAELEMENTS, dataElementId, VALUE), HttpMethod.Get);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<IValue>(response, response.Entity);
        }

        public IResult DeleteDataElement(string submodelId, string dataElementId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, DATAELEMENTS, dataElementId), HttpMethod.Delete);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse(response, response.Entity);
        }

        public IResult<IEvent> CreateEvent(string submodelId, IEvent eventable)
        {
            var request = base.CreateJsonContentRequest(GetUri(SUBMODELS, submodelId, EVENTS), HttpMethod.Post, eventable);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<IEvent>(response, response.Entity);
        }

        public IResult<ElementContainer<IEvent>> RetrieveEvents(string submodelId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, EVENTS), HttpMethod.Get);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<ElementContainer<IEvent>>(response, response.Entity);
        }

        public IResult<IEvent> RetrieveEvent(string submodelId, string eventId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, EVENTS, eventId), HttpMethod.Get);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<IEvent>(response, response.Entity);
        }

        public IResult DeleteEvent(string submodelId, string eventId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, EVENTS, eventId), HttpMethod.Delete);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse(response, response.Entity);
        }
    }
}
