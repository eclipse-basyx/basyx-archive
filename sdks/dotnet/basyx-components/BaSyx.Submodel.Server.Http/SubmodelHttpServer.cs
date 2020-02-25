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
using BaSyx.Utils.Logging;
using BaSyx.Utils.Settings.Types;
using Microsoft.AspNetCore;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using NLog;
using NLog.Web;
using System;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;

namespace BaSyx.Submodel.Server.Http
{
    public class SubmodelHttpServer : IServerApplicationLifetime
    {
        private static Logger logger = NLogBuilder.ConfigureNLog("NLog.config").GetCurrentClassLogger();
        private static Microsoft.Extensions.Logging.LogLevel logLevel;
        public ServerSettings Settings { get; }

        public Action ApplicationStarted { get; set; }

        public Action ApplicationStopping { get; set; }

        public Action ApplicationStopped { get; set; }

        private IWebHostBuilder webHostBuilder;

        public SubmodelHttpServer(ServerSettings serverSettings = null, string[] webHostBuilderargs = null)
        {
            Settings = serverSettings ?? ServerSettings.LoadSettings();
            logLevel = LoggingExtentions.GetLogLevel(logger);

            webHostBuilder = BuildWebHost(webHostBuilderargs);
            webHostBuilder.ConfigureServices(services =>
            {
                services.AddSingleton(typeof(ServerSettings), Settings);
            });
        }

        public void Run()
        {
            logger.Debug("Starting Submodel Service...");

            webHostBuilder.Build().Run();
        }

        public async Task RunAsync(CancellationToken cancellationToken = default(CancellationToken))
        {
            logger.Debug("Starting Submodel Service Async...");

            await webHostBuilder.Build().RunAsync(cancellationToken);
        }


        public void ConfigureApplication(Action<IApplicationBuilder> appConfiguration) => webHostBuilder.Configure(appConfiguration);
        public void ConfigureServices(Action<IServiceCollection> configureServices) => webHostBuilder.ConfigureServices(configureServices);
        public void UseContentRoot(string contentRoot) => webHostBuilder.UseContentRoot(contentRoot);
        public void UseWebRoot(string webRoot) => webHostBuilder.UseWebRoot(webRoot);
        public void UseUrls(params string[] urls)
        {
            webHostBuilder.UseUrls(urls);
            if (Settings?.ServerConfig?.Hosting != null)
                Settings.ServerConfig.Hosting.Urls = urls.ToList();
        }

        public void SetServiceProvider(ISubmodelServiceProvider submodelServiceProvider)
        {
            webHostBuilder.ConfigureServices(services =>
            {
                services.AddSingleton<ISubmodelServiceProvider>(submodelServiceProvider);
            });
        }

        public IWebHostBuilder BuildWebHost(string[] args)
        {
            var webHost = WebHost.CreateDefaultBuilder(args)
                .UseStartup<Startup>()
                .ConfigureLogging(logging =>
                {
                    logging.ClearProviders();
                    logging.SetMinimumLevel(logLevel);
                })
                .ConfigureServices(services => services.AddSingleton<IServerApplicationLifetime>(this))
                .UseNLog()
                .UseContentRoot(AppContext.BaseDirectory);

            if (Settings?.ServerConfig?.Hosting?.Urls?.Count > 0)
                webHost.UseUrls(Settings.ServerConfig.Hosting.Urls.ToArray());

            return webHost;
        }
    }
}
