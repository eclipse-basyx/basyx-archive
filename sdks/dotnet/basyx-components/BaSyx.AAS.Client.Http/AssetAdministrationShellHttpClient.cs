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
using BaSyx.Models.Extensions;
using BaSyx.Utils.Client.Http;
using BaSyx.Utils.ResultHandling;
using System;
using System.Collections.Generic;
using System.Net.Http;
using BaSyx.Utils.PathHandling;
using BaSyx.Models.Core.Common;
using BaSyx.Models.Core.AssetAdministrationShell.Generics.SubmodelElementTypes;
using BaSyx.Models.Connectivity.Descriptors;

namespace BaSyx.AAS.Client.Http
{
    public class AssetAdministrationShellHttpClient : SimpleHttpClient, IAssetAdministrationShellClient, ISubmodelServiceProviderRegistry
    {
        private const string AAS = "aas";
        private const string SUBMODELS = "submodels";
        private const string SUBMODEL = "submodel";
        private const string PROPERTIES = "properties";
        private const string OPERATIONS = "operations";
        private const string EVENTS = "events";
        private const string VALUE = "value";

        private const string BINDING = "binding";
        private const string BINDINGS = "bindings";

        private const string SEPERATOR = "/";
        private const int TIMEOUT = 30000;

        public Uri Endpoint { get; }

        public AssetAdministrationShellHttpClient(Uri endpoint)
        {
            Endpoint = endpoint;
            JsonSerializerSettings = new JsonStandardSettings();
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
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<IAssetAdministrationShellDescriptor>(response, response.Entity);
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

        public IResult<IElementContainer<ISubmodel>> RetrieveSubmodels()
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
            var request = base.CreateJsonContentRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, OPERATIONS), HttpMethod.Post, operation);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<IOperation>(response, response.Entity);
        }

        public IResult<IElementContainer<IOperation>> RetrieveOperations(string submodelId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, OPERATIONS), HttpMethod.Get);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<ElementContainer<IOperation>>(response, response.Entity);
        }

        public IResult<IOperation> RetrieveOperation(string submodelId, string operationId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, OPERATIONS, operationId), HttpMethod.Get);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<IOperation>(response, response.Entity);
        }

        public IResult DeleteOperation(string submodelId, string operationId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, OPERATIONS, operationId), HttpMethod.Delete);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse(response, response.Entity);
        }

        public IResult InvokeOperation(string submodelId, string operationId, IElementContainer<ISubmodelElement> inputArguments, IElementContainer<ISubmodelElement> outputArguments, int timeout)
        {
            var uri = GetUri(SUBMODELS, submodelId, SUBMODEL, OPERATIONS, operationId);
            var requestUri = new Uri(uri.OriginalString + "?timeout=" + timeout);
            var request = base.CreateJsonContentRequest(requestUri, HttpMethod.Post, inputArguments);
            var response = base.SendRequest(request, TIMEOUT);
            var evaluatedResponse = base.EvaluateResponse(response, response.Entity);
            outputArguments = evaluatedResponse.GetEntity<IElementContainer<ISubmodelElement>>();
            return evaluatedResponse;
        }

        public IResult<IProperty> CreateProperty(string submodelId, IProperty property)
        {
            var request = base.CreateJsonContentRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, PROPERTIES), HttpMethod.Post, property);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<IProperty>(response, response.Entity);
        }

        public IResult<IElementContainer<IProperty>> RetrieveProperties(string submodelId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, PROPERTIES), HttpMethod.Get);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<ElementContainer<IProperty>>(response, response.Entity);
        }

        public IResult<IProperty> RetrieveProperty(string submodelId, string propertyId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, PROPERTIES, propertyId), HttpMethod.Get);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<IProperty>(response, response.Entity);
        }

        public IResult UpdatePropertyValue(string submodelId, string propertyId, IValue value)
        {
            var request = base.CreateJsonContentRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, PROPERTIES, propertyId, VALUE), HttpMethod.Put, value);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse(response, response.Entity);
        }

        public IResult<IValue> RetrievePropertyValue(string submodelId, string propertyId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, PROPERTIES, propertyId, VALUE), HttpMethod.Get);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<IValue>(response, response.Entity);
        }

        public IResult DeleteProperty(string submodelId, string propertyId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, PROPERTIES, propertyId), HttpMethod.Delete);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse(response, response.Entity);
        }

        public IResult<IEvent> CreateEvent(string submodelId, IEvent eventable)
        {
            var request = base.CreateJsonContentRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, EVENTS), HttpMethod.Post, eventable);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<IEvent>(response, response.Entity);
        }

        public IResult<IElementContainer<IEvent>> RetrieveEvents(string submodelId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, EVENTS), HttpMethod.Get);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<ElementContainer<IEvent>>(response, response.Entity);
        }

        public IResult<IEvent> RetrieveEvent(string submodelId, string eventId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, EVENTS, eventId), HttpMethod.Get);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<IEvent>(response, response.Entity);
        }

        public IResult DeleteEvent(string submodelId, string eventId)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, submodelId, SUBMODEL, EVENTS, eventId), HttpMethod.Delete);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse(response, response.Entity);
        }

        public IResult<ISubmodelDescriptor> RegisterSubmodelServiceProvider(string id, ISubmodelServiceProvider submodelServiceProvider)
        {
            var request = base.CreateJsonContentRequest(GetUri(SUBMODELS, id, BINDING), HttpMethod.Post, submodelServiceProvider.ServiceDescriptor);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<ISubmodelDescriptor>(response, response.Entity);
        }

        public IResult UnregisterSubmodelServiceProvider(string id)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, id, BINDING), HttpMethod.Delete);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse(response, response.Entity);
        }

        public IResult<ISubmodelServiceProvider> GetSubmodelServiceProvider(string id)
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, id, BINDING), HttpMethod.Get);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<ISubmodelServiceProvider>(response, response.Entity);
        }

        public IResult<IEnumerable<ISubmodelServiceProvider>> GetSubmodelServiceProviders()
        {
            var request = base.CreateRequest(GetUri(SUBMODELS, BINDINGS), HttpMethod.Get);
            var response = base.SendRequest(request, TIMEOUT);
            return base.EvaluateResponse<IEnumerable<ISubmodelServiceProvider>>(response, response.Entity);
        }
    }
}
