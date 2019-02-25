using Microsoft.AspNetCore;
using Microsoft.AspNetCore.Hosting;
using NLog.Web;
using Microsoft.Extensions.Logging;
using System.IO;

namespace BaSys40.Component.REST
{
    public class Program
    {
        public static ServerSettings Settings;
        public static void Main(string[] args)
        {
            var logger = NLogBuilder.ConfigureNLog("NLog.config").GetCurrentClassLogger();
            logger.Debug("Starting main method...");

            Settings = ServerSettings.LoadSettings();

            BuildWebHost(args).Run();
        }

        public static IWebHost BuildWebHost(string[] args)
        {
            var webHost = WebHost.CreateDefaultBuilder(args)
                .UseStartup<Startup>()
                .ConfigureLogging(logging =>
                {
                    logging.ClearProviders();
                    logging.SetMinimumLevel(LogLevel.Warning);
                })
                .UseNLog()
                .UseContentRoot(Directory.GetCurrentDirectory());

            if (Settings?.Hosting?.Urls?.Count > 0)
                webHost.UseUrls(Settings.Hosting.Urls.ToArray());

            return webHost.Build();
        }     
    }
}
