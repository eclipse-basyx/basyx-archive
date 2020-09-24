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
using BaSyx.Utils.AssemblyHandling;
using BaSyx.Utils.DependencyInjection;
using BaSyx.Utils.Settings.Types;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Rewrite;
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
    public class SingleStartup
    {
        private static readonly Logger logger = NLogBuilder.ConfigureNLog("NLog.config").GetCurrentClassLogger();
        private const string ControllerAssemblyName = "BaSyx.API.Http.Controllers";

        private const string UI_RELATIVE_PATH = "/ui";

        public IConfiguration Configuration { get; }
        public IServerApplicationLifetime ServerApplicationLifetime { get; }
        public static ServerSettings ServerSettings { get; set; } 
               
        private string submodelId = string.Empty;

        public SingleStartup(IConfiguration configuration, ServerSettings serverSettings, IServerApplicationLifetime serverApplicationLifetime)
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
            services
                .AddRazorPages(options => options.Conventions.AddPageRoute("/Index", "/ui"))
                .AddRazorRuntimeCompilation();

            services.AddDirectoryBrowser();
           
            //Check whether Submodel Service Provider exists and bind it to Submodel-REST-Controller
            services.AddTransient<ISubmodelServiceProvider>(ctx =>
            {
                var aasServiceProvider = ctx.GetRequiredService<IAssetAdministrationShellServiceProvider>();
                var submodelServiceProvider = aasServiceProvider.SubmodelRegistry.GetSubmodelServiceProvider(submodelId);
                if (!submodelServiceProvider.Success || submodelServiceProvider.Entity == null)
                    return new SubmodelServiceProvider();
                else
                    return submodelServiceProvider.Entity;
            });

            // Register the Swagger generator, defining one or more Swagger documents
            services.AddSwaggerGen(c =>
            {
                c.SwaggerDoc("v1", new OpenApiInfo
                {
                    Version = "v1",
                    Title = "BaSyx Asset Administration Shell HTTP REST-API",
                    Description = "The full description of the generic BaSyx Asset Administration Shell HTTP REST-API",
                    Contact = new OpenApiContact { Name = "Constantin Ziesche", Email = "constantin.ziesche@bosch.com", Url = new Uri("https://www.bosch.com/de/") },
                    License = new OpenApiLicense { Name = "EPL-2.0", Url = new Uri("https://www.eclipse.org/legal/epl-2.0/") }
                });                

                // Set the comments path for the Swagger JSON and UI.
                var xmlFile = $"{controllerAssembly.GetName().Name}.xml";
                var xmlPath = Path.Combine(Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location), xmlFile);
                if (EmbeddedResource.CheckOrWriteRessourceToFile(controllerAssembly, xmlPath))
                    c.IncludeXmlComments(xmlPath, true);
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

            string path = env.ContentRootPath;
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
                if (pathElements.Length >= 4)
                {
                    submodelId = pathElements[2];
                    string[] restOfPathArray = new string[pathElements.Length - 3];
                    Array.Copy(pathElements, 3, restOfPathArray, 0, pathElements.Length - 3);
                    string restOfPath = string.Join("/", restOfPathArray);
                    context.Request.Path = new PathString("/" + restOfPath);
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

            var options = new RewriteOptions().AddRedirect("^$", UI_RELATIVE_PATH);
            app.UseRewriter(options);

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
                c.SwaggerEndpoint("/swagger/v1/swagger.json", "BaSyx Asset Administration Shell HTTP REST-API");
            });            
        }
    }
}
