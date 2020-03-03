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
using BaSyx.AAS.Client.Http;
using BaSyx.API.Components;
using BaSyx.Models.Connectivity;
using BaSyx.Models.Connectivity.Descriptors;
using BaSyx.Utils.ResultHandling;
using NLog;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using BaSyx.Utils.Logging;

namespace BaSyx.Discovery.mDNS
{
    public static class DiscoveryExtensions
    {
        private static DiscoveryServer discoveryServer;
        private static DiscoveryClient discoveryClient;
        private static IAssetAdministrationShellRegistry assetAdministrationShellRegistry;

        public const string ASSETADMINISTRATIONSHELL_ID = "aas.id";
        public const string ASSETADMINISTRATIONSHELL_IDSHORT = "aas.idShort";
        public const string ASSETADMINISTRATIONSHELL_ENDPOINT = "aas.endpoint";
        public const string KEY_VALUE_SEPERATOR = "=";

        private static readonly ILogger logger = LogManager.GetCurrentClassLogger();

        public static void StartDiscovery(this IAssetAdministrationShellRegistry registry)
        {
            assetAdministrationShellRegistry = registry;

            discoveryServer = new DiscoveryServer(ServiceTypes.AAS_SERVICE_TYPE);
            discoveryServer.ServiceInstanceDiscovered += DiscoveryServer_ServiceInstanceDiscovered;
            discoveryServer.ServiceInstanceShutdown += DiscoveryServer_ServiceInstanceShutdown;
            discoveryServer.Start();
        }

        

        private static async void DiscoveryServer_ServiceInstanceDiscovered(object sender, ServiceInstanceEventArgs e)
        {
            try
            {
                IAssetAdministrationShellDescriptor aasDescriptor = null;
                foreach (var server in e.Servers)
                {
                    bool pingable = await BaSyx.Utils.Network.NetworkUtils.PingHostAsync(server.Address.ToString());
                    if (pingable)
                    {
                        string uri = "http://" + server.Address.ToString() + ":" + server.Port + "/aas";
                        Uri endpoint = new Uri(uri);

                        AssetAdministrationShellHttpClient client = new AssetAdministrationShellHttpClient(endpoint);
                        IResult<IAssetAdministrationShellDescriptor> retrieveDescriptor = client.RetrieveAssetAdministrationShellDescriptor();
                        if (retrieveDescriptor.Success && retrieveDescriptor.Entity != null)
                        {
                            retrieveDescriptor.LogResult(logger, LogLevel.Info, "Successfully retrieved AAS descriptor");
                            if (aasDescriptor == null)
                            {
                                aasDescriptor = retrieveDescriptor.Entity;
                                aasDescriptor.SetEndpoints(new List<IEndpoint>() { new HttpEndpoint(uri) });
                            }
                            else
                            {
                                aasDescriptor.AddEndpoints(new List<IEndpoint>() { new HttpEndpoint(uri) });
                            }
                        }
                        else
                            retrieveDescriptor.LogResult(logger, LogLevel.Info, "Could not retrieve AAS descriptor");
                    }
                }
                if (aasDescriptor != null)
                {
                    var registeredResult = assetAdministrationShellRegistry.CreateAssetAdministrationShell(aasDescriptor);
                    if (registeredResult.Success)
                        registeredResult.LogResult(logger, LogLevel.Info, "Successfully registered AAS at registry");
                    else
                        registeredResult.LogResult(logger, LogLevel.Error, "Could not register AAS at registry");
                }
            }
            catch (Exception exc)
            {
                logger.Error(exc, "Error accessing discovered service instance");
            }
        }
        
        private static void DiscoveryServer_ServiceInstanceShutdown(object sender, ServiceInstanceEventArgs e)
        {
            try
            {
                if (assetAdministrationShellRegistry != null && e.TxtRecords?.Count > 0)
                {
                    string aasIdKeyValue = e.TxtRecords.FirstOrDefault(t => t.StartsWith(ASSETADMINISTRATIONSHELL_ID + KEY_VALUE_SEPERATOR));
                    if (!string.IsNullOrEmpty(aasIdKeyValue))
                    {
                        string[] splittedItem = aasIdKeyValue.Split(new char[] { '=' }, StringSplitOptions.RemoveEmptyEntries);
                        if (splittedItem != null && splittedItem.Length == 2)
                        {
                            if (splittedItem[0] == ASSETADMINISTRATIONSHELL_ID)
                            {
                                var deletedResult = assetAdministrationShellRegistry.DeleteAssetAdministrationShell(splittedItem[1]);
                                if (deletedResult.Success)
                                    deletedResult.LogResult(logger, LogLevel.Info, "Successfully deregistered AAS from registry");
                                else
                                    deletedResult.LogResult(logger, LogLevel.Error, "Could not unregister AAS from registry");
                            }
                        }
                    }
                }
            }
            catch (Exception exc)
            {
                logger.Error(exc, "Error service instance shutdown");
            }
        }
        
        public static void StopDiscovery(this IAssetAdministrationShellRegistry registryHttpServer)
        {
            discoveryServer.Stop();
        }

        public static void StartDiscovery(this IAssetAdministrationShellServiceProvider serviceProvider) => StartDiscovery(serviceProvider, null);

        public static void StartDiscovery(this IAssetAdministrationShellServiceProvider serviceProvider, IEnumerable<IPAddress> iPAddresses)
        {
            int port = new Uri(serviceProvider.ServiceDescriptor.Endpoints.First().Address).Port;
            discoveryClient = new DiscoveryClient(serviceProvider.ServiceDescriptor.IdShort, (ushort)port, ServiceTypes.AAS_SERVICE_TYPE, iPAddresses);
            discoveryClient.AddProperty(ASSETADMINISTRATIONSHELL_ID, serviceProvider.ServiceDescriptor.Identification.Id);
            discoveryClient.AddProperty(ASSETADMINISTRATIONSHELL_IDSHORT, serviceProvider.ServiceDescriptor.IdShort);
            foreach (var endpoint in serviceProvider.ServiceDescriptor.Endpoints)
            {
                discoveryClient.AddProperty(ASSETADMINISTRATIONSHELL_ENDPOINT + "." + endpoint.Type, endpoint.Address + "aas");
            }
            discoveryClient.Start();
            
        }
        public static void StopDiscovery(this IAssetAdministrationShellServiceProvider serviceProvider)
        {
            discoveryClient.Stop();
        }                   
    }
}
