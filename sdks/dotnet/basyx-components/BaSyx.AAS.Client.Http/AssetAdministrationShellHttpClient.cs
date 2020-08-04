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
using BaSyx.API.Components;
using BaSyx.Models.Core.AssetAdministrationShell.Generics;
using BaSyx.Utils.Client.Http;
using BaSyx.Utils.ResultHandling;
using System;
using System.Collections.Generic;
using System.Net.Http;
using BaSyx.Utils.PathHandling;
using BaSyx.Models.Core.Common;
using BaSyx.Models.Core.AssetAdministrationShell.Generics.SubmodelElementTypes;
using BaSyx.Models.Connectivity.Descriptors;
using System.Linq;
using BaSyx.Models.Connectivity;
using NLog;
using BaSyx.Models.Communication;
using BaSyx.Utils.DependencyInjection;

namespace BaSyx.AAS.Client.Http
{
    public class AssetAdministrationShellHttpClient : SimpleHttpClient, IAssetAdministrationShellClient, ISubmodelServiceProviderRegistry
    {
        private static readonly ILogger logger = LogManager.GetCurrentClassLogger();

        private const string SEPARATOR = "/";
        private const string AAS = "aas";
        private const string SUBMODELS = "submodels";
        private const string SUBMODEL = "submodel";
        private const string SUBMODEL_ELEMENTS = "submodelElements";
        private const string PROPERTIES = "properties";
        private const string OPERATIONS = "operations";
        private const string EVENTS = "events";
        private const string VALUE = "value";
        private const string ASYNC = "async";
        private const string INVOCATION_LIST = "invocationList";

        private const string BINDING = "binding";
        private const string BINDINGS = "bindings";

        private const int REQUEST_TIMEOUT = 30000;

        public Uri Endpoint { get; }

        private AssetAdministrationShellHttpClient()
        {
            JsonSerializerSettings = new JsonStandardSettings();
        }

        public AssetAdministrationShellHttpClient(Uri endpoint) : this()
        {
            endpoint = endpoint ?? throw new ArgumentNullException(nameof(endpoint));
            Endpoint = endpoint;
        }

        public AssetAdministrationShellHttpClient(IAssetAdministrationShellDescriptor aasDescriptor) : this()
        {
            aasDescriptor = aasDescriptor ?? throw new ArgumentNullException(nameof(aasDescriptor));
            HttpEndpoint httpEndpoint = aasDescriptor.Endpoints?.OfType<HttpEndpoint>()?.FirstOrDefault();
            if (httpEndpoint == null || string.IsNullOrEmpty(httpEndpoint.Address))
                throw new Exception("There is no http endpoint for instantiating a client");
            else
            {
                if (!httpEndpoint.Address.EndsWith(SEPARATOR + AAS) || !httpEndpoint.Address.EndsWith(SEPARATOR + AAS + SEPARATOR))
                    Endpoint = new Uri(httpEndpoint.Address + SEPARATOR + AAS);
                else
                    Endpoint = new Uri(httpEndpoint.Address);
            }
        }


        public Uri GetUri(params string[] pathElements)
        {
            if (pathElements == null)
                return Endpoint;
            return Endpoint.Append(pathElements);
        }

        public IResult<IAssetAdministrationShellDescriptor> RetrieveAssetAdministrationShellDescriptor()
        {
            var request = base.CreateRequest(GetUri(), HttpMethod.Get);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse<IAssetAdministrationShellDescriptor>(response, response.Entity);
        }

        public IResult<IAssetAdministrationShell> RetrieveAssetAdministrationShell()
        {
            var request = base.CreateRequest(GetUri(), HttpMethod.Get);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse<IAssetAdministrationShell>(response, response.Entity);
        }

        public IResult<ISubmodel> CreateSubmodel(ISubmodel submodel)
        {
            var request = base.CreateJsonContentRequest(GetUri(SUBMODELS), HttpMethod.Put, submodel);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse<ISubmodel>(response, response.Entity);
        }

        public IResult<IElementContainer<ISubmodel>> RetrieveSubmodels()
        {
            var request = base.CreateRequest(GetUri(SUBMODELS), HttpMethod.Get);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse<ElementContainer<ISubmodel>>(response, response.Entity);
        }

        public IResult<ISubmodel> RetrieveSubmodel(string submodelId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId), HttpMethod.Get);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse<ISubmodel>(response, response.Entity);
        }

        public IResult DeleteSubmodel(string submodelId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId), HttpMethod.Delete);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse(response, response.Entity);
        }

        public IResult<IOperation> CreateOperation(string submodelId, IOperation operation)
        {
            var request = base.CreateJsonContentRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, OPERATIONS), HttpMethod.Put, operation);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse<IOperation>(response, response.Entity);
        }

        public IResult<IElementContainer<ISubmodelElement>> RetrieveSubmodelElements(string submodelId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, SUBMODEL_ELEMENTS), HttpMethod.Get);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse<ElementContainer<ISubmodelElement>>(response, response.Entity);
        }

        public IResult<IElementContainer<IOperation>> RetrieveOperations(string submodelId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, OPERATIONS), HttpMethod.Get);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse<ElementContainer<IOperation>>(response, response.Entity);
        }

        public IResult<IOperation> RetrieveOperation(string submodelId, string operationId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, OPERATIONS, operationId), HttpMethod.Get);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse<IOperation>(response, response.Entity);
        }

        public IResult DeleteOperation(string submodelId, string operationId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, OPERATIONS, operationId), HttpMethod.Delete);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse(response, response.Entity);
        }

        public IResult<InvocationResponse> InvokeOperation(string submodelId, string operationId, InvocationRequest invocationRequest)
        {
            var request = base.CreateJsonContentRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, OPERATIONS, operationId), HttpMethod.Post, invocationRequest);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse<InvocationResponse>(response, response.Entity);
        }

        public IResult<IProperty> CreateProperty(string submodelId, IProperty property)
        {
            var request = base.CreateJsonContentRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, PROPERTIES), HttpMethod.Put, property);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse<IProperty>(response, response.Entity);
        }

        public IResult<ISubmodelElement> CreateSubmodelElement(string submodelId, ISubmodelElement submodelElement)
        {
            var request = base.CreateJsonContentRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, SUBMODEL_ELEMENTS), HttpMethod.Put, submodelElement);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse<ISubmodelElement>(response, response.Entity);
        }

        public IResult<IElementContainer<IProperty>> RetrieveProperties(string submodelId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, PROPERTIES), HttpMethod.Get);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse<ElementContainer<IProperty>>(response, response.Entity);
        }

        public IResult<IProperty> RetrieveProperty(string submodelId, string propertyId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, PROPERTIES, propertyId), HttpMethod.Get);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse<IProperty>(response, response.Entity);
        }

        public IResult<ISubmodelElement> RetrieveSubmodelElement(string submodelId, string submodelElementId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, SUBMODEL_ELEMENTS, submodelElementId), HttpMethod.Get);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse<ISubmodelElement>(response, response.Entity);
        }

        public IResult UpdatePropertyValue(string submodelId, string propertyId, IValue value)
        {
            var request = base.CreateJsonContentRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, PROPERTIES, propertyId, VALUE), HttpMethod.Put, value);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse(response, response.Entity);
        }

        public IResult<IValue> RetrievePropertyValue(string submodelId, string propertyId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, PROPERTIES, propertyId, VALUE), HttpMethod.Get);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse<IValue>(response, response.Entity);
        }

        public IResult DeleteSubmodelElement(string submodelId, string submodelElementId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, SUBMODEL_ELEMENTS, submodelElementId), HttpMethod.Delete);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse(response, response.Entity);
        }

        public IResult DeleteProperty(string submodelId, string propertyId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, PROPERTIES, propertyId), HttpMethod.Delete);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse(response, response.Entity);
        }

        public IResult<IEvent> CreateEvent(string submodelId, IEvent eventable)
        {
            var request = base.CreateJsonContentRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, EVENTS), HttpMethod.Put, eventable);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse<IEvent>(response, response.Entity);
        }

        public IResult<IElementContainer<IEvent>> RetrieveEvents(string submodelId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, EVENTS), HttpMethod.Get);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse<ElementContainer<IEvent>>(response, response.Entity);
        }

        public IResult<IEvent> RetrieveEvent(string submodelId, string eventId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, EVENTS, eventId), HttpMethod.Get);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse<IEvent>(response, response.Entity);
        }

        public IResult DeleteEvent(string submodelId, string eventId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, EVENTS, eventId), HttpMethod.Delete);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse(response, response.Entity);
        }

        public IResult<ISubmodelDescriptor> RegisterSubmodelServiceProvider(string id, ISubmodelServiceProvider submodelServiceProvider)
        {
            var request = base.CreateJsonContentRequest(GetUri(SUBMODELS, id, BINDING), HttpMethod.Post, submodelServiceProvider.ServiceDescriptor);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse<ISubmodelDescriptor>(response, response.Entity);
        }

        public IResult UnregisterSubmodelServiceProvider(string id)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, id, BINDING), HttpMethod.Delete);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse(response, response.Entity);
        }

        public IResult<ISubmodelServiceProvider> GetSubmodelServiceProvider(string id)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, id, BINDING), HttpMethod.Get);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse<ISubmodelServiceProvider>(response, response.Entity);
        }

        public IResult<IEnumerable<ISubmodelServiceProvider>> GetSubmodelServiceProviders()
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, BINDINGS), HttpMethod.Get);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse<IEnumerable<ISubmodelServiceProvider>>(response, response.Entity);
        }
        public IResult<CallbackResponse> InvokeOperationAsync(string submodelId, string operationId, InvocationRequest invocationRequest)
        {
            var request = base.CreateJsonContentRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, OPERATIONS, operationId, ASYNC), HttpMethod.Post, invocationRequest);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse<CallbackResponse>(response, response.Entity);
        }

        public IResult<InvocationResponse> GetInvocationResult(string submodelId, string operationId, string requestId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, OPERATIONS, operationId, INVOCATION_LIST, requestId), HttpMethod.Get);
            var response = base.SendRequest(request, REQUEST_TIMEOUT);
            return base.EvaluateResponse<InvocationResponse>(response, response.Entity);
        }

    }
}
