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
using BaSyx.AAS.Server.Http;
using BaSyx.API.AssetAdministrationShell.Extensions;
using BaSyx.API.Components;
using BaSyx.Models.Connectivity;
using BaSyx.Models.Core.AssetAdministrationShell.Generics;
using BaSyx.Models.Export;
using Microsoft.Extensions.DependencyInjection;
using NLog;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;

namespace BaSyx.AASX.Server.Http
{
    class Program
    {
        private static readonly ILogger logger = LogManager.GetCurrentClassLogger();

        static void Main(string[] args)
        {
            logger.Info("Starting AASX Hoster...");

            if(args?.Length == 0)
            {
                logger.Error("No arguments provided --> Application is shutting down...");
                return;
            }
            if (!string.IsNullOrEmpty(args[0]) && File.Exists(args[0]))
            {
                IAssetAdministrationShell shell;
                AssetAdministrationShellHttpServer server = new AssetAdministrationShellHttpServer();

                using (BaSyx.Models.Export.AASX aasx = new BaSyx.Models.Export.AASX(args[0]))
                {
                    AssetAdministrationShellEnvironment_V2_0 environment = aasx.GetEnvironment_V2_0();
                    shell = environment.AssetAdministrationShells.FirstOrDefault();
                    if (shell == null)
                    {
                        logger.Error("Asset Administration Shell cannot be obtained from AASX-Package " + args[0]);
                        return;
                    }
                    else
                    {
                        logger.Info("AASX-Package successfully loaded");

                        var service = shell.CreateServiceProvider(true);
                        List<HttpEndpoint> endpoints;
                        if (args.Length == 2 && !string.IsNullOrEmpty(args[1]))
                        {
                            logger.Info("Using " + args[1] + " as host url");
                            endpoints = new List<HttpEndpoint>() { new HttpEndpoint(args[1]) };
                        }
                        else
                        {                            
                            endpoints = server.Settings.ServerConfig.Hosting.Urls.ConvertAll(c => 
                            {
                                string address = c.Replace("+", "127.0.0.1");
                                logger.Info("Using " + address + " as host url");
                                return new HttpEndpoint(address); 
                            });
                            
                        }
                        service.UseDefaultEndpointRegistration(endpoints);
                        server.SetServiceProvider(service);

                        server.ConfigureServices(services =>
                        {
                            Assembly controllerAssembly = Assembly.Load("BaSyx.API.Http.Controllers.AASX");
                            services.AddMvc()
                                .AddApplicationPart(controllerAssembly)
                                .AddControllersAsServices();
                        });

                        foreach (var file in aasx.SupplementaryFiles)
                        {
                            using (Stream stream = file.GetStream())
                            {
                                logger.Info("Providing content on server: " + file.Uri);
                                server.ProvideContent(file.Uri, stream);
                            }
                        }
                        logger.Info("Server is starting up...");
                        server.Run();
                    }
                }
            }
            logger.Info("Application shut down");
        }
    }
}
