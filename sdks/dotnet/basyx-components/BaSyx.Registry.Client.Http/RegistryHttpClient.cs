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
using BaSyx.API.Components;
using BaSyx.Models.Connectivity.Descriptors;
using BaSyx.Models.Core.Common;
using BaSyx.Utils.Client.Http;
using BaSyx.Utils.DependencyInjection;
using BaSyx.Utils.ResultHandling;
using NLog;
using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Http;
using System.Threading;
using System.Threading.Tasks;
using System.Web;

namespace BaSyx.Registry.Client.Http
{
    public class RegistryHttpClient : SimpleHttpClient, IAssetAdministrationShellRegistry
    {
        private static readonly ILogger logger = LogManager.GetCurrentClassLogger();
        public RegistryClientSettings Settings { get; }

        private const string RegistryPath = "api/v1/registry";
        private const string SubmodelPath = "submodels";
        private const string PathSeperator = "/";

        private const int Timeout = 5000;

        private int RepeatRegistrationInterval = -1;
        private CancellationTokenSource RepeatRegistrationCancellationToken = null;

        public void LoadSettings(RegistryClientSettings settings)
        {
            if (settings.ProxyConfig.UseProxy && !string.IsNullOrEmpty(settings.ProxyConfig.ProxyAddress))
            {
                HttpClientHandler.UseProxy = true;
                if (!string.IsNullOrEmpty(settings.ProxyConfig.UserName) && !string.IsNullOrEmpty(settings.ProxyConfig.Password))
                {
                    NetworkCredential credential;
                    if (!string.IsNullOrEmpty(settings.ProxyConfig.Domain))
                        credential = new NetworkCredential(settings.ProxyConfig.UserName, settings.ProxyConfig.Password, settings.ProxyConfig.Domain);
                    else
                        credential = new NetworkCredential(settings.ProxyConfig.UserName, settings.ProxyConfig.Password);

                    HttpClientHandler.Proxy = new WebProxy(settings.ProxyConfig.ProxyAddress, false, null, credential);
                }
                else
                    HttpClientHandler.Proxy = new WebProxy(settings.ProxyConfig.ProxyAddress);
            }
            else
                HttpClientHandler.UseProxy = false;

            if(settings.RegistryConfig.RepeatRegistration.HasValue)
                RepeatRegistrationInterval = settings.RegistryConfig.RepeatRegistration.Value;
        }

        public RegistryHttpClient(RegistryClientSettings registryClientSettings = null)
        {
            Settings = registryClientSettings ?? RegistryClientSettings.LoadSettings();

            LoadSettings(Settings);
            JsonSerializerSettings = new JsonStandardSettings();
        }

        public Uri GetUri(params string[] pathElements)
        {
            string path = string.Empty;
            if (!Settings.RegistryConfig.RegistryUrl.EndsWith("/") && !RegistryPath.StartsWith("/"))
                path = Settings.RegistryConfig.RegistryUrl + PathSeperator + RegistryPath;
            else
                path = Settings.RegistryConfig.RegistryUrl + RegistryPath;

            if (pathElements?.Length > 0)
                foreach (var pathElement in pathElements)
                {
                    string encodedPathElement = HttpUtility.UrlEncode(pathElement);
                    if (!encodedPathElement.EndsWith("/") && !encodedPathElement.StartsWith("/"))
                        path = path + PathSeperator + encodedPathElement;
                    else
                        path = path + encodedPathElement;

                    //if (!path.EndsWith("/"))
                    //    path = path + PathSeperator;
                }
            return new Uri(path);
        }

        public void RepeatRegistration(IAssetAdministrationShellDescriptor aas, CancellationTokenSource cancellationToken)
        {
            RepeatRegistrationCancellationToken = cancellationToken;
            Task.Factory.StartNew(async () =>
            {
                while (!cancellationToken.IsCancellationRequested)
                {
                    IResult<IAssetAdministrationShellDescriptor> result = CreateAssetAdministrationShell(aas);
                    logger.Info("Registration-Renewal - Success: " + result.Success + " | Messages: " + result.Messages.ToString());
                    await Task.Delay(RepeatRegistrationInterval);
                }
            }, cancellationToken.Token, TaskCreationOptions.LongRunning, TaskScheduler.Default);
        } 
        
        public void CancelRepeatingRegistration()
        {
            RepeatRegistrationCancellationToken?.Cancel();
        }

        public IResult<IAssetAdministrationShellDescriptor> CreateAssetAdministrationShell(IAssetAdministrationShellDescriptor aas)
        {
            var request = base.CreateJsonContentRequest(GetUri(), HttpMethod.Post, aas);
            var response = base.SendRequest(request, Timeout);
            return base.EvaluateResponse<IAssetAdministrationShellDescriptor>(response, response.Entity);
        }

        public IResult<IAssetAdministrationShellDescriptor> RetrieveAssetAdministrationShell(string aasId)
        {
            var request = base.CreateRequest(GetUri(aasId), HttpMethod.Get);
            var response = base.SendRequest(request, Timeout);
            return base.EvaluateResponse<IAssetAdministrationShellDescriptor>(response, response.Entity);
        }

        public IResult<IElementContainer<IAssetAdministrationShellDescriptor>> RetrieveAssetAdministrationShells()
        {
            var request = base.CreateRequest(GetUri(), HttpMethod.Get);
            var response = base.SendRequest(request, Timeout);
            return base.EvaluateResponse<ElementContainer<IAssetAdministrationShellDescriptor>>(response, response.Entity);
        }

        public IResult UpdateAssetAdministrationShell(string aasId, Dictionary<string, string> metaData)
        {
            var request = base.CreateRequest(GetUri(aasId), HttpMethod.Put);
            var response = base.SendRequest(request, Timeout);
            return base.EvaluateResponse(response, response.Entity);
        }

        public IResult DeleteAssetAdministrationShell(string aasId)
        {
            if(RepeatRegistrationInterval > 0)
            {
                RepeatRegistrationCancellationToken?.Cancel();
            }

            var request = base.CreateRequest(GetUri(aasId), HttpMethod.Delete);
            var response = base.SendRequest(request, Timeout);
            return base.EvaluateResponse(response, response.Entity);
        }

        public IResult<ISubmodelDescriptor> CreateSubmodel(string aasId, ISubmodelDescriptor submodel)
        {
            var request = base.CreateJsonContentRequest(GetUri(aasId, SubmodelPath), HttpMethod.Post, submodel);
            var response = base.SendRequest(request, Timeout);
            return base.EvaluateResponse<ISubmodelDescriptor>(response, response.Entity);
        }

        public IResult<IElementContainer<ISubmodelDescriptor>> RetrieveSubmodels(string aasId)
        {
            var request = base.CreateRequest(GetUri(aasId, SubmodelPath), HttpMethod.Get);
            var response = base.SendRequest(request, Timeout);
            return base.EvaluateResponse<ElementContainer<ISubmodelDescriptor>>(response, response.Entity);
        }

        public IResult<ISubmodelDescriptor> RetrieveSubmodel(string aasId, string submodelId)
        {
            var request = base.CreateRequest(GetUri(aasId, SubmodelPath, submodelId), HttpMethod.Get);
            var response = base.SendRequest(request, Timeout);
            return base.EvaluateResponse<ISubmodelDescriptor>(response, response.Entity);
        }

        public IResult DeleteSubmodel(string aasId, string submodelId)
        {
            var request = base.CreateRequest(GetUri(aasId, SubmodelPath, submodelId), HttpMethod.Delete);
            var response = base.SendRequest(request, Timeout);
            return base.EvaluateResponse(response, response.Entity);
        }
    }
}
