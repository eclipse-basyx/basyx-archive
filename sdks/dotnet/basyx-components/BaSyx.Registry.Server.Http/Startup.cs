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
using System;
using System.Diagnostics;
using System.IO;
using System.Reflection;
using BaSyx.Utils.Settings;
using BaSyx.Utils.Settings.Types;
using Microsoft.AspNetCore.Builder;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Hosting;
using Microsoft.OpenApi.Models;
using NLog;
using NLog.Web;
using Microsoft.AspNetCore.Hosting;
using BaSyx.Components.Common;
using BaSyx.Utils.DependencyInjection;

namespace BaSyx.Registry.Server.Http
{
    public class Startup
    {
        private static Logger logger = NLogBuilder.ConfigureNLog("NLog.config").GetCurrentClassLogger();
        private const string ControllerAssemblyName = "BaSyx.API.Http.Controllers";

        public IConfiguration Configuration { get; }
        public IServerApplicationLifetime ServerApplicationLifetime { get; }
        public static ServerSettings ServerSettings { get; set; }

        public Startup(IConfiguration configuration, ServerSettings serverSettings, IServerApplicationLifetime serverApplicationLifetime)
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

           
            services.AddSwaggerGen(c =>
            {
                c.SwaggerDoc("v1", new OpenApiInfo()
                {
                    Version = "v1",
                    Title = "BaSyx Registry HTTP REST-API",
                    Description = "The full description of the BaSyx Registry HTTP REST-API",                    
                    Contact = new OpenApiContact { Name = "Constantin Ziesche", Email = "constantin.ziesche@bosch.com", Url = new Uri("https://www.bosch.com/de/") },
                    License = new OpenApiLicense { Name = "EPL-2.0", Url = new Uri("https://www.eclipse.org/legal/epl-2.0/") }
                });

                var xmlFile = $"{controllerAssembly.GetName().Name}.xml";
                var xmlPath = Path.Combine(AppContext.BaseDirectory, xmlFile);
                if (ResourceChecker.CheckResourceAvailability(controllerAssembly, ControllerAssemblyName, xmlFile, true))
                    c.IncludeXmlComments(xmlPath, true);               
            });
            services.AddSwaggerGenNewtonsoftSupport();
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, ILoggerFactory loggerFactory, IWebHostEnvironment env, IHostApplicationLifetime applicationLifetime)
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
            app.UseStaticFiles();

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

            app.UseSwaggerUI(c =>
            {
                c.SwaggerEndpoint("/swagger/v1/swagger.json", "BaSyx Registry Http REST-API");
            });
        }
    }
}
