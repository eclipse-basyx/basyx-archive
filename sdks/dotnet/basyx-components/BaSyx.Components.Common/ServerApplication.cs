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
using BaSyx.Utils.Settings.Types;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using NLog;
using NLog.Web;
using System;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Threading;
using System.Threading.Tasks;
using static BaSyx.Utils.Settings.Types.ServerSettings;

namespace BaSyx.Components.Common
{
    public abstract class ServerApplication : IServerApplicationLifetime
    {
        private static readonly Logger logger = NLogBuilder.ConfigureNLog("NLog.config").GetCurrentClassLogger();

        public ServerSettings Settings { get; protected set; }
        public IWebHostBuilder WebHostBuilder { get; protected set; }

        public Action ApplicationStarted { get; set; }

        public Action ApplicationStopping { get; set; }

        public Action ApplicationStopped { get; set; }

        protected ServerApplication(Type startupType) : this(startupType, ServerSettings.LoadSettings() ?? throw new NullReferenceException("ServerSettings.xml not found"), null)
        { }

        protected ServerApplication(Type startupType, ServerSettings settings) : this(startupType, settings, null)
        { }

        protected ServerApplication(Type startupType, ServerSettings settings, string[] webHostBuilderArgs)
        {
            Settings = settings ?? throw new ArgumentNullException(nameof(settings));
            WebHostBuilder = DefaultWebHostBuilder.CreateWebHostBuilder(webHostBuilderArgs, Settings, startupType);
            WebHostBuilder.ConfigureServices(services =>
            {
                services.AddSingleton(typeof(ServerSettings), Settings);
                services.AddSingleton<IServerApplicationLifetime>(this);
            })
            .UseNLog();
            ConfigureLogging(logger.GetLogLevel());
        }

        public virtual void Run()
        {
            logger.Debug("Starting Server...");

            WebHostBuilder.Build().Run();
        }

        public virtual async Task RunAsync(CancellationToken cancellationToken = default)
        {
            logger.Debug("Starting Server Async...");

            await WebHostBuilder.Build().RunAsync(cancellationToken);
        }

        public virtual void ConfigureLogging(Microsoft.Extensions.Logging.LogLevel logLevel)
        {
            WebHostBuilder.ConfigureLogging(logging =>
             {
                 logging.ClearProviders();
                 logging.SetMinimumLevel(logLevel);
             });
        }

        public virtual void ConfigureApplication(Action<IApplicationBuilder> appConfiguration) => WebHostBuilder.Configure(appConfiguration);
        public virtual void ConfigureServices(Action<IServiceCollection> configureServices) => WebHostBuilder.ConfigureServices(configureServices);
        public virtual void UseContentRoot(string contentRoot) => WebHostBuilder.UseContentRoot(contentRoot);
        public virtual void UseWebRoot(string webRoot) => WebHostBuilder.UseWebRoot(webRoot);
        public virtual void UseUrls(params string[] urls)
        {
            WebHostBuilder.UseUrls(urls);
            if (Settings?.ServerConfig?.Hosting != null)
                Settings.ServerConfig.Hosting.Urls = urls?.ToList();
        }

        public virtual void ProvideContent(Uri relativeUri, Stream content)
        {
            try
            {
                using (Stream stream = content)
                {
                    string fileName = Path.GetFileName(relativeUri.ToString());
                    string directory = Path.GetDirectoryName(relativeUri.ToString());
                    if (directory.StartsWith("\\"))
                        directory = directory.Substring(1, directory.Length - 1);
                    string hostingDirectory = Path.Combine(AppContext.BaseDirectory, Settings.ServerConfig.Hosting.ContentPath, directory);
                    Directory.CreateDirectory(hostingDirectory);

                    string filePath = Path.Combine(hostingDirectory, fileName);

                    using (FileStream fileStream = File.OpenWrite(filePath))
                    {
                        stream.CopyTo(fileStream);
                    }
                }
            }
            catch (Exception e)
            {
                logger.Error(e, $"Error providing content {relativeUri}");
            }
        }

        public virtual void MapControllers(ControllerConfiguration controllerConfig)
        {
            this.ConfigureServices(services =>
            {
                if (controllerConfig?.Controllers?.Count > 0)
                {
                    var mvcBuilder = services.AddMvc();
                    foreach (var controllerAssemblyName in controllerConfig.Controllers)
                    {
                        Assembly controllerAssembly = Assembly.Load(controllerAssemblyName);
                        mvcBuilder.AddApplicationPart(controllerAssembly);
                    }
                    mvcBuilder.AddControllersAsServices();
                }
            });
        }
    }
}
