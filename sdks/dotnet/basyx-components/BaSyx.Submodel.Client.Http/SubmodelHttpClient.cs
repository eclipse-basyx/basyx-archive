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
using BaSyx.API.Clients;
using BaSyx.Models.Core.AssetAdministrationShell.Generics;
using BaSyx.Models.Extensions;
using BaSyx.Utils.Client.Http;
using BaSyx.Utils.ResultHandling;
using System;
using System.Net;
using System.Net.Http;
using BaSyx.Models.Core.Common;
using BaSyx.Models.Core.AssetAdministrationShell.Generics.SubmodelElementTypes;

namespace BaSyx.Submodel.Client.Http
{
    public class SubmodelHttpClient : SimpleHttpClient, ISubmodelClient
    {
        private const string SUBMODEL = "submodel";
        private const string PROPERTIES = "properties";
        private const string OPERATIONS = "operations";
        private const string SUBMODELELEMENTS = "submodelElements";
        private const string EVENTS = "events";
        private const string VALUE = "value";

        private const string SEPERATOR = "/";
        private const int TIMEOUT = 30000;

        public IPEndPoint Endpoint { get; }
        public SubmodelHttpClient(IPEndPoint endPoint)
        {
            Endpoint = endPoint;
            JsonSerializerSettings = new JsonStandardSettings();
        }
        public Uri GetUri(params string[] pathElements)
        {
            string path = "http://" + Endpoint.Address + ":" + Endpoint.Port + SEPERATOR + SUBMODEL;

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

        public IResult<ISubmodel> RetrieveSubmodel()
        {
            var request = base.CreateRequest(GetUri(), HttpMethod.Get);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<ISubmodel>(response, response.Entity);
        }

        public IResult<IProperty> CreateProperty(IProperty dataElement)
        {
            var request = base.CreateJsonContentRequest(GetUri(PROPERTIES), HttpMethod.Post, dataElement);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<IProperty>(response, response.Entity);
        }

        public IResult<IEvent> CreateEvent(IEvent eventable)
        {
            var request = base.CreateJsonContentRequest(GetUri(EVENTS), HttpMethod.Post, eventable);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<IEvent>(response, response.Entity);
        }

        public IResult<IOperation> CreateOperation(IOperation operation)
        {
            var request = base.CreateJsonContentRequest(GetUri(OPERATIONS), HttpMethod.Post, operation);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<IOperation>(response, response.Entity);
        }

        public IResult DeleteProperty(string propertyId)
        {
            var request = base.CreateRequest(GetUri(PROPERTIES, propertyId), HttpMethod.Delete);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse(response, response.Entity);
        }

        public IResult DeleteEvent(string eventId)
        {
            var request = base.CreateRequest(GetUri(EVENTS, eventId), HttpMethod.Delete);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse(response, response.Entity);
        }

        public IResult DeleteOperation(string operationId)
        {
            var request = base.CreateRequest(GetUri(OPERATIONS, operationId), HttpMethod.Delete);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse(response, response.Entity);
        }

        public IResult InvokeOperation(string operationId, IOperationVariableSet inputArguments, IOperationVariableSet outputArguments, int timeout)
        {
            var uri = GetUri(OPERATIONS, operationId);
            var requestUri = new Uri(uri.OriginalString + "?timeout=" + timeout);
            var request = base.CreateJsonContentRequest(requestUri, HttpMethod.Post, inputArguments);
            var response = base.SendRequest(request, TIMEOUT);
            var evaluatedResponse = base.EvaluateResponse(response, response.Entity);
            outputArguments = evaluatedResponse.GetEntity<IOperationVariableSet>();
            return evaluatedResponse;
        }

        public IResult<IProperty> RetrieveProperty(string propertyId)
        {
            var request = base.CreateRequest(GetUri(PROPERTIES, propertyId), HttpMethod.Get);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<IProperty>(response, response.Entity);
        }

        public IResult<IElementContainer<IProperty>> RetrieveProperties()
        {
            var request = base.CreateRequest(GetUri(PROPERTIES), HttpMethod.Get);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<ElementContainer<IProperty>>(response, response.Entity);
        }

        public IResult<IValue> RetrievePropertyValue(string propertyId)
        {
            var request = base.CreateRequest(GetUri(PROPERTIES, propertyId, VALUE), HttpMethod.Get);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<IValue>(response, response.Entity);
        }

        public IResult<IEvent> RetrieveEvent(string eventId)
        {
            var request = base.CreateRequest(GetUri(EVENTS, eventId), HttpMethod.Get);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<IEvent>(response, response.Entity);
        }

        public IResult<IElementContainer<IEvent>> RetrieveEvents()
        {
            var request = base.CreateRequest(GetUri(EVENTS), HttpMethod.Get);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<IElementContainer<IEvent>>(response, response.Entity);
        }

        public IResult<IOperation> RetrieveOperation(string operationId)
        {
            var request = base.CreateRequest(GetUri(OPERATIONS, operationId), HttpMethod.Get);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<IOperation>(response, response.Entity);
        }

        public IResult<IElementContainer<IOperation>> RetrieveOperations()
        {
            var request = base.CreateRequest(GetUri(OPERATIONS), HttpMethod.Get);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<ElementContainer<IOperation>>(response, response.Entity);
        }

        public IResult UpdatePropertyValue(string propertyId, IValue value)
        {
            var request = base.CreateJsonContentRequest(GetUri(PROPERTIES, propertyId, VALUE), HttpMethod.Put, value);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse(response, response.Entity);
        }

        public IResult<ISubmodelElement> CreateSubmodelElement(ISubmodelElement submodelElement)
        {
            var request = base.CreateJsonContentRequest(GetUri(SUBMODELELEMENTS), HttpMethod.Post, submodelElement);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<ISubmodelElement>(response, response.Entity);
        }

        public IResult<IElementContainer<ISubmodelElement>> RetrieveSubmodelElements()
        {
            var request = base.CreateRequest(GetUri(SUBMODELELEMENTS), HttpMethod.Get);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<IElementContainer<ISubmodelElement>>(response, response.Entity);
        }

        public IResult<ISubmodelElement> RetrieveSubmodelElement(string submodelElementId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELELEMENTS, submodelElementId), HttpMethod.Get);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<ISubmodelElement>(response, response.Entity);
        }

        public IResult<IValue> RetrieveSubmodelElementValue(string submodelElementId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELELEMENTS, submodelElementId, VALUE), HttpMethod.Get);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<IValue>(response, response.Entity);
        }

        public IResult UpdateSubmodelElement(string submodelElementId, ISubmodelElement submodelElement)
        {
            var request = base.CreateJsonContentRequest(GetUri(SUBMODELELEMENTS, submodelElementId), HttpMethod.Put, submodelElement);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse(response, response.Entity);
        }

        public IResult DeleteSubmodelElement(string submodelElementId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELELEMENTS, submodelElementId), HttpMethod.Delete);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse(response, response.Entity);
        }
    }
}
