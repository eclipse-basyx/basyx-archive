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
using BaSyx.API.Http.Controllers;
using BaSyx.Components.Common;
using BaSyx.Utils.DependencyInjection;
using BaSyx.Utils.Settings;
using BaSyx.Utils.Settings.Types;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.FileProviders;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using Microsoft.OpenApi.Models;
using NLog;
using NLog.Web;
using System;
using System.Diagnostics;
using System.IO;
using System.Reflection;

namespace BaSyx.AAS.Server.Http
{
    public class MultiStartup
    {
        private static readonly Logger logger = NLogBuilder.ConfigureNLog("NLog.config").GetCurrentClassLogger();
        private const string ControllerAssemblyName = "BaSyx.API.Http.Controllers";
        public IConfiguration Configuration { get; }
        public IServerApplicationLifetime ServerApplicationLifetime { get; }
        public static ServerSettings ServerSettings { get; set; } 
               
        private string submodelId = string.Empty;
        private string aasId = string.Empty;

        public MultiStartup(IConfiguration configuration, ServerSettings serverSettings, IServerApplicationLifetime serverApplicationLifetime)
        {
            Configuration = configuration;
            ServerSettings = serverSettings;
            ServerApplicationLifetime = serverApplicationLifetime;
        }


        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddStandardImplementation();

            Assembly controllerAssembly = Assembly.Load(ControllerAssemblyName);
            services.AddCors();
            services.AddMvc()
                .AddApplicationPart(controllerAssembly)
                .AddControllersAsServices()
                .AddNewtonsoftJson(options => options.GetDefaultMvcJsonOptions(services));

            services.AddRazorPages(options =>
            {
                string pageName = ServerSettings.ServerConfig?.DefaultRoute ?? "/Index";
                options.Conventions.AddPageRoute(pageName, "");
            });

            //Check whether Asset Administration Shell Service Provider exists and bind it to the AssetAdministrationShell-Services-Controller
            services.AddTransient<IAssetAdministrationShellServiceProvider>(ctx =>
            {
                IAssetAdministrationShellServiceProvider aasServiceProvider = ctx
                .GetRequiredService<IAssetAdministrationShellRepositoryServiceProvider>()
                .GetAssetAdministrationShellServiceProvider(aasId);

                return new AssetAdministrationShellServices(aasServiceProvider);
            });


            //Check whether Submodel Service Provider exists and bind it to the Submodel-Services-Controller
            services.AddTransient<ISubmodelServiceProvider>(ctx =>
            {
                IAssetAdministrationShellServiceProvider aasServiceProvider = ctx
               .GetRequiredService<IAssetAdministrationShellRepositoryServiceProvider>()
               .GetAssetAdministrationShellServiceProvider(aasId);

                var submodelServiceProvider = aasServiceProvider.SubmodelRegistry.GetSubmodelServiceProvider(submodelId);
                if(!submodelServiceProvider.Success || submodelServiceProvider.Entity == null)
                {
                    SubmodelServiceProvider cssp = new SubmodelServiceProvider();
                    return new SubmodelServices(cssp);
                }
                else
                    return new SubmodelServices(submodelServiceProvider.Entity);
            });

            // Register the Swagger generator, defining one or more Swagger documents
            services.AddSwaggerGen(c =>
            {
                c.SwaggerDoc("v1", new OpenApiInfo
                {
                    Version = "v1",
                    Title = "BaSyx Asset Administration Shell Repository HTTP REST-API",
                    Description = "The full description of the generic BaSyx Asset Administration Shell Repository HTTP REST-API",
                    Contact = new OpenApiContact { Name = "Constantin Ziesche", Email = "constantin.ziesche@bosch.com", Url = new Uri("https://www.bosch.com/de/") },
                    License = new OpenApiLicense { Name = "EPL-2.0", Url = new Uri("https://www.eclipse.org/legal/epl-2.0/") }
                });                

                // Set the comments path for the Swagger JSON and UI.
                var xmlFile = $"{controllerAssembly.GetName().Name}.xml";
                var xmlPath = Path.Combine(AppContext.BaseDirectory, xmlFile);
                if (ResourceChecker.CheckResourceAvailability(controllerAssembly, ControllerAssemblyName, xmlFile, true))
                    c.IncludeXmlComments(xmlPath);
            });
            services.AddSwaggerGenNewtonsoftSupport();
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IServiceProvider serviceProvider, IWebHostEnvironment env, ILoggerFactory loggerFactory, IHostApplicationLifetime applicationLifetime)
        {
            if (env.IsDevelopment() || Debugger.IsAttached)
            {
                app.UseDeveloperExceptionPage();
            }
            else
            {
                app.UseExceptionHandler("/error");
                //app.UseHsts();
            }
            //app.UseHttpsRedirection();
            app.UseStaticFiles(); //necessary for the wwwroot folder

            string path = Path.Combine(env.ContentRootPath, ServerSettings.ServerConfig.Hosting.ContentPath);
            if (Directory.Exists(path))
            {
                app.UseStaticFiles(new StaticFileOptions()
                {
                    FileProvider = new PhysicalFileProvider(@path),
                    RequestPath = new PathString("")
                });

                app.UseDirectoryBrowser(new DirectoryBrowserOptions
                {
                    FileProvider = new PhysicalFileProvider(@path),
                    RequestPath = new PathString("/browse")
                });
            }

            app.Use((context, next) =>
            {
                string[] pathElements = context.Request.Path.ToUriComponent()?.Split(new char[] { '/' }, StringSplitOptions.RemoveEmptyEntries);


                if (pathElements == null || pathElements.Length == 0)
                {
                    string defaultRoute = ServerSettings.ServerConfig.DefaultRoute ?? "/MultiIndex";
                    context.Request.Path = new PathString(defaultRoute);
                    return next();
                }
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

            app.UseRouting();

            app.UseCors(
                options => options
                            .AllowAnyHeader()
                            .AllowAnyMethod()
                            .AllowAnyOrigin()
            );

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapRazorPages();
                endpoints.MapControllers();
            });

            if (ServerApplicationLifetime.ApplicationStarted != null)
                applicationLifetime.ApplicationStarted.Register(ServerApplicationLifetime.ApplicationStarted);
            if (ServerApplicationLifetime.ApplicationStopping != null)
                applicationLifetime.ApplicationStopping.Register(ServerApplicationLifetime.ApplicationStopping);
            if (ServerApplicationLifetime.ApplicationStopped != null)
                applicationLifetime.ApplicationStopped.Register(ServerApplicationLifetime.ApplicationStopped);

            // Enable middleware to serve generated Swagger as a JSON endpoint.
            app.UseSwagger();

            // Enable middleware to serve swagger-ui (HTML, JS, CSS, etc.), specifying the Swagger JSON endpoint.
            app.UseSwaggerUI(c =>
            {
                c.SwaggerEndpoint("/swagger/v1/swagger.json", "BaSyx Asset Administration Shell Repository HTTP REST-API");
            });                      
        }
    }
}
