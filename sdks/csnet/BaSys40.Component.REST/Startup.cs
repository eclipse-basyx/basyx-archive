using BaSys40.API.ServiceProvider;
using BaSys40.Component.REST.Controllers;
using BaSys40.Models.Extensions;
using BaSys40.Utils.DIExtensions;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using NLog;
using NLog.Web;
using Swashbuckle.AspNetCore.Swagger;
using System;
using System.IO;

namespace BaSys40.Component.REST
{
    public class Startup
    {
        private static Logger logger = NLogBuilder.ConfigureNLog("NLog.config").GetCurrentClassLogger();

        private string submodelId;
        private string aasId;

        private IAssetAdministrationShellServiceProvider shellService;

        public IConfiguration Configuration { get; }

        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddMvc().AddControllersAsServices();

            services.ConfigureStandardImplementation();
            services.ConfigureStandardDI();

            /* If more than one Asset Administration Shell is accessible via this HTTP-Server uncomment the following lines *//*
            services.AddTransient(ctx =>
            {
                var smsp = ctx.GetRequiredService<IAssetAdministrationShellAggregatorServiceProvider>().GetAssetAdministrationShellServiceProvider(aasId);
                return new AssetAdministrationShellServices(smsp);
            });
            */

            /* Initialize IAssetAdministrationShellService Provider */
            //shellService = new ...;

            services.AddSingleton<IAssetAdministrationShellServiceProvider>(ctx =>
            {
                return shellService;
            });
            
            
            services.AddTransient(ctx =>
            {
                var smsp = ctx.GetRequiredService<IAssetAdministrationShellServiceProvider>().GetSubmodelServiceProvider(submodelId);
                return new SubmodelServices(smsp);
            });           
            
            
            // Register the Swagger generator, defining one or more Swagger documents
            services.AddSwaggerGen(c =>
            {
                c.SwaggerDoc("v1", new Info
                {
                    Version = "v1",
                    Title = "BaSys 4.0 Asset Administration Shell REST-API",
                    Description = "The full description of the generic BaSys 4.0 Asset Administration Shell REST-API",
                    TermsOfService = "None",
                    Contact = new Contact { Name = "Constantin Ziesche", Email = "constantin.ziesche@bosch.com", Url = "https://www.bosch.com/de/" },
                    License = new License { Name = "Use under Eclipse Public License 2.0", Url = "https://www.eclipse.org/legal/epl-2.0/" }
                });

                // Set the comments path for the Swagger JSON and UI.
                var basePath = AppContext.BaseDirectory;
                var xmlPath = Path.Combine(basePath, "BaSys40.Component.REST.xml");
                c.IncludeXmlComments(xmlPath);
                c.SchemaFilter<SchemaFilter>();
                //c.DocumentFilter<DocumentFilter>();             
            });
        }
        
        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IServiceProvider serviceProvider, IHostingEnvironment env, ILoggerFactory loggerFactory, IApplicationLifetime applicationLifetime)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }
            app.UseStaticFiles();

            applicationLifetime.ApplicationStopping.Register(OnShutdown);

            // Enable middleware to serve generated Swagger as a JSON endpoint.
            app.UseSwagger();

            // Enable middleware to serve swagger-ui (HTML, JS, CSS, etc.), specifying the Swagger JSON endpoint.
            app.UseSwaggerUI(c =>
            {
                c.SwaggerEndpoint("/swagger/v1/swagger.json", "BaSys 4.0 Asset Administration Shell REST-API");
            });

            app.AddStandardRewriterOptions();

            app.Use((context, next) =>
            {
                var pathElements = context.Request.Path.ToUriComponent().Split(new char[] { '/' }, StringSplitOptions.RemoveEmptyEntries);

                if (context.Request.Query?.Count > 0)
                {
                    if (context.Request.Query.ContainsKey("aasId"))
                        aasId = context.Request.Query["aasId"].ToString();

                    if (context.Request.Query.ContainsKey("submodelId"))
                        submodelId = context.Request.Query["submodelId"].ToString();
                }
                return next();
            });

            app.UseMvc();
        }

        private void OnShutdown()
        {
            logger.Info("Shutdown completed!");
        }
    }
}
