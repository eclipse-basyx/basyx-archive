using BaSys40.API.Platform;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Utils.Client;
using BaSys40.Utils.ResultHandling;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Net.Http;
using System.Reflection;
using static BaSys40.Registry.Client.RegistryClientSettings;

namespace BaSys40.Registry.Client
{
    public class RegistryClient : SimpleHttpClient, IAssetAdministrationShellRegistry
    {
        public static RegistryClientSettings Settings { get; private set; }
        public static readonly string SettingsPath;

        private static RegistryClient registryClient;

        private RegistryConfiguration registryConfig;

        private const string RegistryPath = "registry";
        private const string SubModelPath = "subModels";
        private const string PathSeperator = "/";

        private const int Timeout = 5000;

        static RegistryClient()
        {
            SettingsPath = Path.Combine(Path.GetDirectoryName(Assembly.GetEntryAssembly().Location), RegistryClientSettings.FileName);
            Settings = RegistryClientSettings.LoadSettings(SettingsPath);

            if (Settings.ProxyConfig.UseProxy.HasValue && Settings.ProxyConfig.UseProxy.Value && !string.IsNullOrEmpty(Settings.ProxyConfig.ProxyAddress))
            {
                HttpClientHandler.UseProxy = true;
                if (!string.IsNullOrEmpty(Settings.ProxyConfig.UserName) && !string.IsNullOrEmpty(Settings.ProxyConfig.Password))
                {
                    NetworkCredential credential;
                    if (!string.IsNullOrEmpty(Settings.ProxyConfig.Domain))
                        credential = new NetworkCredential(Settings.ProxyConfig.UserName, Settings.ProxyConfig.Password, Settings.ProxyConfig.Domain);
                    else
                        credential = new NetworkCredential(Settings.ProxyConfig.UserName, Settings.ProxyConfig.Password);

                    HttpClientHandler.Proxy = new WebProxy(Settings.ProxyConfig.ProxyAddress, false, null, credential);
                }
                else
                    HttpClientHandler.Proxy = new WebProxy(Settings.ProxyConfig.ProxyAddress);
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

        public RegistryClient(RegistryConfiguration registryConfig = null)
        {
            if (registryConfig != null)
                this.registryConfig = registryConfig;
            else
                this.registryConfig = Settings.RegistryConfig;
        }

        public Uri GetUri(params string[] pathElements)
        {
            string path = registryConfig.RegistryUrl + registryConfig.BasePath;
            if (pathElements?.Length > 0)
                foreach (var pathElement in pathElements)
                {
                    path += pathElement + PathSeperator;
                }
            return new Uri(path);
        }

        public IResult<IAssetAdministrationShell> CreateAssetAdministrationShell(IAssetAdministrationShell aas)
        {
            var request = base.CreateRequest(GetUri(), HttpMethod.Post, aas);
            var response = base.SendRequest(request, Timeout);
            return base.EvaluateResponse<IAssetAdministrationShell>(response.Entity);
        }

        public IResult<IAssetAdministrationShell> RetrieveAssetAdministrationShell(string aasId)
        {
            var request = base.CreateRequest(GetUri(aasId), HttpMethod.Get);
            var response = base.SendRequest(request, Timeout);
            return base.EvaluateResponse<IAssetAdministrationShell>(response.Entity);
        }

        public IResult<List<IAssetAdministrationShell>> RetrieveAssetAdministrationShells()
        {
            var request = base.CreateRequest(GetUri(), HttpMethod.Get);
            var response = base.SendRequest(request, Timeout);
            return base.EvaluateResponse<List<IAssetAdministrationShell>>(response.Entity);
        }

        public IResult UpdateAssetAdministrationShell(string aasId, Dictionary<string, string> metaData)
        {
            var request = base.CreateRequest(GetUri(aasId), HttpMethod.Put);
            var response = base.SendRequest(request, Timeout);
            return base.EvaluateResponse(response.Entity);
        }

        public IResult DeleteAssetAdministrationShell(string aasId)
        {
            var request = base.CreateRequest(GetUri(aasId), HttpMethod.Delete);
            var response = base.SendRequest(request, Timeout);
            return base.EvaluateResponse<List<IAssetAdministrationShell>>(response.Entity);
        }

        public IResult<ISubModel> CreateSubModel(string aasId, ISubModel submodel)
        {
            var request = base.CreateRequest(GetUri(aasId, SubModelPath), HttpMethod.Post, submodel);
            var response = base.SendRequest(request, Timeout);
            return base.EvaluateResponse<ISubModel>(response.Entity);
        }

        public IResult<IElementContainer<ISubModel>> RetrieveSubModels(string aasId)
        {
            var request = base.CreateRequest(GetUri(aasId, SubModelPath), HttpMethod.Get);
            var response = base.SendRequest(request, Timeout);
            return base.EvaluateResponse<IElementContainer<ISubModel>>(response.Entity);
        }

        public IResult<ISubModel> RetrieveSubModel(string aasId, string subModelId)
        {
            var request = base.CreateRequest(GetUri(aasId, SubModelPath, subModelId), HttpMethod.Get);
            var response = base.SendRequest(request, Timeout);
            return base.EvaluateResponse<ISubModel>(response.Entity);
        }

        public IResult DeleteSubModel(string aasId, string subModelId)
        {
            var request = base.CreateRequest(GetUri(aasId, SubModelPath, subModelId), HttpMethod.Delete);
            var response = base.SendRequest(request, Timeout);
            return base.EvaluateResponse(response.Entity);
        }
    }
}
