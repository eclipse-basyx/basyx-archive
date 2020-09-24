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
using BaSyx.Utils.AssemblyHandling;
using BaSyx.Utils.Settings.Types;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.FileProviders;
using Microsoft.Extensions.Hosting;
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

        private string _contentRoot;
        private string _webRoot;

        public const string DEFAULT_CONTENT_ROOT = "Content";
        public const string DEFAULT_WEB_ROOT = "wwwroot";

        public ServerSettings Settings { get; protected set; }
        public IWebHostBuilder WebHostBuilder { get; protected set; }
        public string ExecutionPath { get; }

        public Action ApplicationStarted { get; set; }

        public Action ApplicationStopping { get; set; }

        public Action ApplicationStopped { get; set; }

        protected ServerApplication(Type startupType) : this(startupType, ServerSettings.LoadSettings(), null)
        { }

        protected ServerApplication(Type startupType, ServerSettings settings) : this(startupType, settings, null)
        { }

        protected ServerApplication(Type startupType, ServerSettings settings, string[] webHostBuilderArgs)
        {
            ExecutionPath = Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location);

            if (!EmbeddedResource.CheckOrWriteRessourceToFile(this.GetType().Assembly, Path.Combine(ExecutionPath, "NLog.config")))
                logger.Error("NLog.config cannot be loaded or written");

            if (settings == null && !EmbeddedResource.CheckOrWriteRessourceToFile(this.GetType().Assembly, Path.Combine(ExecutionPath, "ServerSettings.xml")))
                logger.Error("ServerSettings.xml cannot be loaded or written");

            Settings = settings ?? ServerSettings.LoadSettingsFromFile("ServerSettings.xml") ?? throw new ArgumentNullException(nameof(settings));

            if (string.IsNullOrEmpty(Settings.ServerConfig.Hosting.ContentPath))
                _contentRoot = Path.Join(ExecutionPath, DEFAULT_CONTENT_ROOT);
            else if (Path.IsPathRooted(Settings.ServerConfig.Hosting.ContentPath))
                _contentRoot = Settings.ServerConfig.Hosting.ContentPath;
            else
                _contentRoot = Path.Join(ExecutionPath, Settings.ServerConfig.Hosting.ContentPath);

            _webRoot = Path.Join(ExecutionPath, DEFAULT_WEB_ROOT);

            if (webHostBuilderArgs?.Length > 0)
                for (int i = 0; i < webHostBuilderArgs.Length; i++)
                    logger.Info($"webHostBuilderArg[{i}]: {webHostBuilderArgs[i]}");

            WebHostBuilder = DefaultWebHostBuilder.CreateWebHostBuilder(webHostBuilderArgs, Settings, startupType);

            try
            {
                if (!Directory.Exists(_contentRoot))
                    Directory.CreateDirectory(_contentRoot);
                WebHostBuilder.UseContentRoot(_contentRoot);
            }
            catch (Exception e)
            {
                logger.Error(e, $"ContentRoot path {_contentRoot} cannot be created ");
            }


            try
            {
                if (!Directory.Exists(_webRoot))
                    Directory.CreateDirectory(_webRoot);
                WebHostBuilder.UseWebRoot(_webRoot);
            }
            catch (Exception e)
            {
                logger.Error(e, $"WebRoot path {_webRoot} cannot be created ");
            }


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
        public virtual void UseContentRoot(string contentRoot)
        {
            _contentRoot = contentRoot;
            WebHostBuilder.UseContentRoot(contentRoot);
        }
        public virtual void UseWebRoot(string webRoot)
        {
            _webRoot = webRoot;
            WebHostBuilder.UseWebRoot(webRoot);
        }
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
                    logger.Info("FileName: " + fileName);
                    string directory = Path.GetDirectoryName(relativeUri.ToString()).TrimStart('\\');
                    logger.Info("Directory: " + directory);

                    string hostingDirectory = Path.Join(_contentRoot, directory);

                    logger.Info($"Try creating hosting directory if not existing: {hostingDirectory}");
                    Directory.CreateDirectory(hostingDirectory);

                    string filePath = Path.Join(hostingDirectory, fileName);
                    logger.Info($"Try writing file: {filePath}");

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
                    foreach (var controllerAssemblyString in controllerConfig.Controllers)
                    {
                        Assembly controllerAssembly = null;
                        try
                        {
                            controllerAssembly = Assembly.Load(controllerAssemblyString);
                        }
                        catch (Exception e)
                        {
                            logger.Warn(e, $"Assembly {controllerAssemblyString} cannot be loaded - maybe it is not referenced. Try reading from file...");
                            try
                            {
                                if (File.Exists(controllerAssemblyString))
                                    controllerAssembly = Assembly.LoadFile(controllerAssemblyString);
                                else if (File.Exists(controllerAssemblyString + ".dll"))
                                    controllerAssembly = Assembly.LoadFile(controllerAssemblyString + ".dll");
                                else
                                    controllerAssembly = Assembly.LoadFrom(controllerAssemblyString);
                            }
                            catch (Exception exp)
                            {
                                logger.Warn(exp, $"Assembly {controllerAssemblyString} can finally not be loaded");
                            }
                        }
                        if (controllerAssembly != null)
                        {
                            mvcBuilder.AddApplicationPart(controllerAssembly);
                            string controllerAssemblyName = controllerAssembly.GetName().Name;
                            string xmlDocFile = $"{controllerAssemblyName}.xml";
                            string xmlDocFilePath = Path.Combine(ExecutionPath, xmlDocFile);

                            if (File.Exists(xmlDocFilePath))
                                continue;

                            try
                            {
                                ManifestEmbeddedFileProvider embeddedFileProvider = new ManifestEmbeddedFileProvider(controllerAssembly);
                                IFileInfo fileInfo = embeddedFileProvider.GetFileInfo(xmlDocFile);
                                if (fileInfo == null)
                                {
                                    logger.Warn($"{xmlDocFile} of Assembly {controllerAssemblyName} not found");
                                    continue;
                                }
                                using (Stream stream = fileInfo.CreateReadStream())
                                {
                                    using (FileStream fileStream = File.OpenWrite(xmlDocFilePath))
                                    {
                                        stream.CopyTo(fileStream);
                                    }
                                }
                                logger.Info($"{xmlDocFile} of Assembly {controllerAssemblyName} has been created successfully");
                            }
                            catch (Exception e)
                            {
                                logger.Warn(e, $"{xmlDocFile} of Assembly {controllerAssemblyName} cannot be read");
                            }
                        }
                    }
                    mvcBuilder.AddControllersAsServices();
                }
            });
        }
    }
}
