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
using BaSyx.Models.Connectivity;
using BaSyx.Utils.Network;
using BaSyx.Utils.Settings.Sections;
using NLog;
using System;
using System.Collections.Generic;
using System.Net;

namespace BaSyx.API.Components
{
    public static class DefaultEndpointRegistration
    {
        private static readonly ILogger logger = LogManager.GetCurrentClassLogger();
        public static void UseAutoEndpointRegistration(this IAssetAdministrationShellAggregatorServiceProvider serviceProvider, ServerConfiguration serverConfiguration)
        {
            string multiUrl = serverConfiguration.Hosting.Urls.Find(u => u.Contains("+"));
            if (!string.IsNullOrEmpty(multiUrl))
            {
                Uri uri = new Uri(multiUrl.Replace("+", "localhost"));
                List<IEndpoint> endpoints = GetNetworkInterfaceBasedEndpoints(uri.Scheme, uri.Port);
                serviceProvider.UseDefaultEndpointRegistration(endpoints);
            }
            else
            {
                List<IEndpoint> endpoints = serverConfiguration.Hosting.Urls.ConvertAll(endpointConverter);
                serviceProvider.UseDefaultEndpointRegistration(endpoints);
            }
        }

        private static IEndpoint endpointConverter(string input)
        {
            Uri uri = new Uri(input);
            return EndpointFactory.CreateEndpoint(uri.Scheme, uri.Authority, null);
        }

        public static void UseAutoEndpointRegistration(this IAssetAdministrationShellServiceProvider serviceProvider, ServerConfiguration serverConfiguration)
        {
            string multiUrl = serverConfiguration.Hosting.Urls.Find(u => u.Contains("+"));
            if (!string.IsNullOrEmpty(multiUrl))
            {
                Uri uri = new Uri(multiUrl.Replace("+", "localhost"));
                List<IEndpoint> endpoints = GetNetworkInterfaceBasedEndpoints(uri.Scheme, uri.Port);
                serviceProvider.UseDefaultEndpointRegistration(endpoints);
            }
            else
            {
                List<IEndpoint> endpoints = serverConfiguration.Hosting.Urls.ConvertAll(endpointConverter);
                serviceProvider.UseDefaultEndpointRegistration(endpoints);
            }
        }

        public static void UseAutoEndpointRegistration(this ISubmodelServiceProvider serviceProvider, ServerConfiguration serverConfiguration)
        {
            string multiUrl = serverConfiguration.Hosting.Urls.Find(u => u.Contains("+"));
            if (!string.IsNullOrEmpty(multiUrl))
            {
                Uri uri = new Uri(multiUrl.Replace("+", "localhost"));
                List<IEndpoint> endpoints = GetNetworkInterfaceBasedEndpoints(uri.Scheme, uri.Port);
                serviceProvider.UseDefaultEndpointRegistration(endpoints);
            }
            else
            {
                List<IEndpoint> endpoints = serverConfiguration.Hosting.Urls.ConvertAll(endpointConverter);
                serviceProvider.UseDefaultEndpointRegistration(endpoints);
            }
        }

        private static List<IEndpoint> GetNetworkInterfaceBasedEndpoints(string endpointType, int port)
        {
            IEnumerable<IPAddress> ipAddresses = NetworkUtils.GetIPAddresses();
            List<IEndpoint> aasEndpoints = new List<IEndpoint>();
            foreach (var ipAddress in ipAddresses)
            {
                if (ipAddress.AddressFamily == System.Net.Sockets.AddressFamily.InterNetwork)
                    aasEndpoints.Add(EndpointFactory.CreateEndpoint(endpointType, endpointType + "://" + ipAddress.ToString() + ":" + port, null));
                else if (ipAddress.AddressFamily == System.Net.Sockets.AddressFamily.InterNetworkV6)
                    aasEndpoints.Add(EndpointFactory.CreateEndpoint(endpointType, endpointType + "://[" + ipAddress.ToString() + "]:" + port, null));
                else
                    logger.Error("Invalid address family: " + ipAddress.AddressFamily);
            }
            return aasEndpoints;
        }

        public static void UseDefaultEndpointRegistration(this IAssetAdministrationShellAggregatorServiceProvider serviceProvider, IEnumerable<IEndpoint> endpoints)
        {
            List<IEndpoint> aggregatorEndpoints = new List<IEndpoint>();
            foreach (var endpoint in endpoints)
            {
                string epAddress = endpoint.Address;
                if (!epAddress.EndsWith("/shells"))
                    epAddress = epAddress + (epAddress.EndsWith("/") ? "" : "/") + "shells";

                aggregatorEndpoints.Add(EndpointFactory.CreateEndpoint(endpoint.Type, epAddress, endpoint.Security));
            }

            serviceProvider.ServiceDescriptor.AddEndpoints(aggregatorEndpoints);
            var aasAggregatorDescriptor = serviceProvider.ServiceDescriptor;
            foreach (var aasDescriptor in aasAggregatorDescriptor.AssetAdministrationShellDescriptors)
            {
                List<IEndpoint> aasEndpoints = new List<IEndpoint>();
                foreach (var endpoint in aggregatorEndpoints)
                {
                    var ep = EndpointFactory.CreateEndpoint(endpoint.Type, GetAssetAdministrationShellEndpoint(endpoint, aasDescriptor.IdShort), endpoint.Security);
                    aasEndpoints.Add(ep);
                }
                aasDescriptor.AddEndpoints(aasEndpoints);

                foreach (var submodelDescriptor in aasDescriptor.SubmodelDescriptors)
                {
                    List<IEndpoint> submodelEndpoints = new List<IEndpoint>();
                    foreach (var endpoint in aasEndpoints)
                    {
                        var ep = EndpointFactory.CreateEndpoint(endpoint.Type, GetSubmodelEndpoint(endpoint, submodelDescriptor.IdShort), endpoint.Security);
                        submodelEndpoints.Add(ep);
                    }
                    submodelDescriptor.AddEndpoints(submodelEndpoints);
                }
            }
        }

        public static void UseDefaultEndpointRegistration(this IAssetAdministrationShellServiceProvider serviceProvider, IEnumerable<IEndpoint> endpoints)
        {
            List<IEndpoint> aasEndpoints = new List<IEndpoint>();
            foreach (var endpoint in endpoints)
            {
                string epAddress = endpoint.Address;
                if (!epAddress.EndsWith("/aas"))
                    epAddress = epAddress + (epAddress.EndsWith("/") ? "" : "/") + "aas";

                aasEndpoints.Add(EndpointFactory.CreateEndpoint(endpoint.Type, epAddress, endpoint.Security));
            }

            serviceProvider.ServiceDescriptor.AddEndpoints(aasEndpoints);
            var aasDescriptor = serviceProvider.ServiceDescriptor;
            foreach (var submodel in aasDescriptor.SubmodelDescriptors)
            {
                List<IEndpoint> spEndpoints = new List<IEndpoint>();
                foreach (var endpoint in aasEndpoints)
                {
                    var ep = EndpointFactory.CreateEndpoint(endpoint.Type, GetSubmodelEndpoint(endpoint, submodel.IdShort), endpoint.Security);
                    spEndpoints.Add(ep);
                }
                submodel.AddEndpoints(spEndpoints);
            }
        }

        public static void UseDefaultEndpointRegistration(this ISubmodelServiceProvider serviceProvider, IEnumerable<IEndpoint> endpoints)
        {
            List<IEndpoint> submodelEndpoints = new List<IEndpoint>();
            foreach (var endpoint in endpoints)
            {
                string epAddress = endpoint.Address;
                if (!epAddress.EndsWith("/submodel"))
                    epAddress = epAddress + (epAddress.EndsWith("/") ? "" : "/") + "submodel";

                submodelEndpoints.Add(EndpointFactory.CreateEndpoint(endpoint.Type, epAddress, endpoint.Security));
            }

            serviceProvider.ServiceDescriptor.AddEndpoints(submodelEndpoints);         
        }

        public static string GetSubmodelEndpoint(IEndpoint endpoint, string submodelId)
        {
            string epAddress = endpoint.Address;
            if (!epAddress.EndsWith("/aas"))
                epAddress = epAddress + (epAddress.EndsWith("/") ? "" : "/") + "aas";

            return epAddress + "/submodels/" + submodelId + "/submodel";
        }

        public static string GetAssetAdministrationShellEndpoint(IEndpoint endpoint, string aasId)
        {
            string epAddress = endpoint.Address;
            if (!epAddress.EndsWith("/shells"))
                epAddress = epAddress + (epAddress.EndsWith("/") ? "" : "/") + "shells";

            return epAddress + "/" + aasId + "/aas";
        }
    }
}
