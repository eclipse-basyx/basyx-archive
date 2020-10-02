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
using BaSyx.Components.Common;
using BaSyx.Utils.Settings.Types;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.DependencyInjection;
using System;
using System.Reflection;

namespace BaSyx.AAS.Server.Http
{
    public class AssetAdministrationShellRepositoryHttpServer : ServerApplication
    {
        private string submodelId = string.Empty;
        private string aasId = string.Empty;

        public AssetAdministrationShellRepositoryHttpServer() : this(null, null)
        { }

        public AssetAdministrationShellRepositoryHttpServer(ServerSettings serverSettings) : this(serverSettings, null)
        { }

        public AssetAdministrationShellRepositoryHttpServer(ServerSettings serverSettings, string[] webHostBuilderArgs)
            : base(serverSettings, webHostBuilderArgs)
        {
            Assembly entryAssembly = Assembly.GetEntryAssembly();
            WebHostBuilder.UseSetting(WebHostDefaults.ApplicationKey, entryAssembly.FullName);
        }

        public void SetServiceProvider(IAssetAdministrationShellRepositoryServiceProvider repositoryServiceProvider)
        {
            WebHostBuilder.ConfigureServices(services =>
            {
                services.AddSingleton<IAssetAdministrationShellRepositoryServiceProvider>(repositoryServiceProvider);
            });
        }

        protected override void ConfigureServices(IServiceCollection services)
        {
            base.ConfigureServices(services);

            //Check whether Asset Administration Shell Service Provider exists and bind it to the AssetAdministrationShell-Services-Controller
            services.AddTransient<IAssetAdministrationShellServiceProvider>(ctx =>
            {
                IAssetAdministrationShellServiceProvider aasServiceProvider = ctx
                .GetRequiredService<IAssetAdministrationShellRepositoryServiceProvider>()
                .GetAssetAdministrationShellServiceProvider(aasId);

                return aasServiceProvider;
            });

            //Check whether Submodel Service Provider exists and bind it to the Submodel-Services-Controller
            services.AddTransient<ISubmodelServiceProvider>(ctx =>
            {
                IAssetAdministrationShellServiceProvider aasServiceProvider = ctx
               .GetRequiredService<IAssetAdministrationShellRepositoryServiceProvider>()
               .GetAssetAdministrationShellServiceProvider(aasId);

                var submodelServiceProvider = aasServiceProvider.SubmodelRegistry.GetSubmodelServiceProvider(submodelId);
                if (!submodelServiceProvider.Success || submodelServiceProvider.Entity == null)
                {
                    SubmodelServiceProvider cssp = new SubmodelServiceProvider();
                    return cssp;
                }
                else
                    return submodelServiceProvider.Entity;
            });
        }

        protected override void Configure(IApplicationBuilder app)
        {
            base.Configure(app);
            app.Use((context, next) =>
            {
                string[] pathElements = context.Request.Path.ToUriComponent()?.Split(new char[] { '/' }, StringSplitOptions.RemoveEmptyEntries);
                if (pathElements.Length >= 2)
                {
                    aasId = pathElements[1];
                    if (pathElements.Length == 2)
                    {
                        context.Request.Path = new PathString("/shells/" + aasId);
                    }
                    else if (pathElements.Length == 3 && pathElements[2] == "aas")
                    {
                        context.Request.Path = new PathString("/aas");
                    }
                    else if (pathElements.Length == 4 && pathElements[3] == "submodels")
                    {
                        context.Request.Path = new PathString("/aas/submodels");
                    }
                    else if (pathElements.Length == 5)
                    {
                        submodelId = pathElements[4];
                        context.Request.Path = new PathString("/aas/submodels/" + submodelId);
                    }
                    else if (pathElements.Length > 5)
                    {
                        submodelId = pathElements[4];
                        string[] restOfPathArray = new string[pathElements.Length - 5];
                        Array.Copy(pathElements, 5, restOfPathArray, 0, pathElements.Length - 5);
                        string restOfPath = string.Join("/", restOfPathArray);
                        context.Request.Path = new PathString("/" + restOfPath);
                    }
                }
                return next();
            });
        }
    }
}
