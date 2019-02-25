
using BaSys40.API.Platform;
using BaSys40.Models.Core.AssetAdministrationShell;
using BaSys40.Models.Extensions;
using BaSys40.Utils.Client.Http;
using BaSys40.Utils.ResultHandling;
using Microsoft.Extensions.DependencyInjection;
using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Net.Http;
using System.Reflection;

namespace BaSys40.Registry.Client
{
    public class RegistryClient : SimpleHttpClient, IAssetAdministrationShellRegistry
    {
        public static RegistryClientSettings Settings { get; private set; }
        public static string SettingsPath { get; private set; }

        private static RegistryClient registryClient;

        private const string RegistryPath = "api/v1/registry";
        private const string SubmodelPath = "submodels";
        private const string PathSeperator = "/";

        private const int Timeout = 5000;

        private IServiceCollection services;
        private IServiceProvider serviceProvider;

        static RegistryClient()
        {
            SettingsPath = Path.Combine(Path.GetDirectoryName(Assembly.GetEntryAssembly().Location), RegistryClientSettings.FileName);
            Settings = RegistryClientSettings.LoadSettings(SettingsPath);

            LoadSettings(Settings);
        }

        public static void LoadSettings(RegistryClientSettings settings)
        {
            if (settings.ProxyConfig.UseProxy.HasValue && settings.ProxyConfig.UseProxy.Value && !string.IsNullOrEmpty(settings.ProxyConfig.ProxyAddress))
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
        }

        public static RegistryClient Instance
        {
            get
            {
                if (registryClient == null)
                    registryClient = new RegistryClient();
                return registryClient;
            }
        }

        public RegistryClient(RegistryClientSettings registryClientSettings = null)
        {
            if (registryClientSettings != null)
            {
                Settings = registryClientSettings;
                LoadSettings(Settings);
            }

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
                    if (!pathElement.EndsWith("/") && !pathElement.StartsWith("/"))
                        path = path + PathSeperator + pathElement;
                    else
                        path = path + pathElement;

                    //if (!path.EndsWith("/"))
                    //    path = path + PathSeperator;
                }
            return new Uri(path);
        }

        public IResult<AssetAdministrationShellDescriptor> CreateAssetAdministrationShell(AssetAdministrationShellDescriptor aas)
        {
            var request = base.CreateJsonContentRequest(GetUri(), HttpMethod.Post, aas);
            var response = base.SendRequest(request, Timeout);
            return base.EvaluateResponse<AssetAdministrationShellDescriptor>(response, response.Entity);
        }

        public IResult<AssetAdministrationShellDescriptor> RetrieveAssetAdministrationShell(string aasId)
        {
            var request = base.CreateRequest(GetUri(aasId), HttpMethod.Get);
            var response = base.SendRequest(request, Timeout);
            return base.EvaluateResponse<AssetAdministrationShellDescriptor>(response, response.Entity);
        }

        public IResult<List<AssetAdministrationShellDescriptor>> RetrieveAssetAdministrationShells()
        {
            var request = base.CreateRequest(GetUri(), HttpMethod.Get);
            var response = base.SendRequest(request, Timeout);
            return base.EvaluateResponse<List<AssetAdministrationShellDescriptor>>(response, response.Entity);
        }

        public IResult UpdateAssetAdministrationShell(string aasId, Dictionary<string, string> metaData)
        {
            var request = base.CreateRequest(GetUri(aasId), HttpMethod.Put);
            var response = base.SendRequest(request, Timeout);
            return base.EvaluateResponse(response, response.Entity);
        }

        public IResult DeleteAssetAdministrationShell(string aasId)
        {
            var request = base.CreateRequest(GetUri(aasId), HttpMethod.Delete);
            var response = base.SendRequest(request, Timeout);
            return base.EvaluateResponse(response, response.Entity);
        }

        public IResult<SubmodelDescriptor> CreateSubmodel(string aasId, SubmodelDescriptor submodel)
        {
            var request = base.CreateJsonContentRequest(GetUri(aasId, SubmodelPath), HttpMethod.Post, submodel);
            var response = base.SendRequest(request, Timeout);
            return base.EvaluateResponse<SubmodelDescriptor>(response, response.Entity);
        }

        public IResult<List<SubmodelDescriptor>> RetrieveSubmodels(string aasId)
        {
            var request = base.CreateRequest(GetUri(aasId, SubmodelPath), HttpMethod.Get);
            var response = base.SendRequest(request, Timeout);
            return base.EvaluateResponse<List<SubmodelDescriptor>>(response, response.Entity);
        }

        public IResult<SubmodelDescriptor> RetrieveSubmodel(string aasId, string submodelId)
        {
            var request = base.CreateRequest(GetUri(aasId, SubmodelPath, submodelId), HttpMethod.Get);
            var response = base.SendRequest(request, Timeout);
            return base.EvaluateResponse<SubmodelDescriptor>(response, response.Entity);
        }

        public IResult DeleteSubmodel(string aasId, string submodelId)
        {
            var request = base.CreateRequest(GetUri(aasId, SubmodelPath, submodelId), HttpMethod.Delete);
            var response = base.SendRequest(request, Timeout);
            return base.EvaluateResponse(response, response.Entity);
        }
    }
}
