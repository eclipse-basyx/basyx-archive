using BaSys40.Utils.DIExtensions;
using BaSys40.Utils.JsonHandling;
using Microsoft.Extensions.DependencyInjection;
using Newtonsoft.Json;
using Newtonsoft.Json.Converters;
using System;

namespace BaSys40.Models.Extensions
{
    public class JsonStandardSettings : JsonSerializerSettings
    {
        public IServiceProvider ServicePovider { get; }
        public IServiceCollection Services { get; }
        public JsonStandardSettings() : base()
        {
            Services = new ServiceCollection();
            Services.ConfigureStandardImplementation();

            var serviceProviderFactory = new DefaultServiceProviderFactory();
            ServicePovider = serviceProviderFactory.CreateServiceProvider(Services);

            NullValueHandling = NullValueHandling.Include;
            

            Formatting = Formatting.Indented;
            Converters.Add(new StringEnumConverter());
            ContractResolver = new DIContractResolver(new DIExtension(Services), ServicePovider);
        }

        public JsonStandardSettings(IServiceCollection services) : base()
        {
            Services = services;

            var serviceProviderFactory = new DefaultServiceProviderFactory();
            ServicePovider = serviceProviderFactory.CreateServiceProvider(Services);

            NullValueHandling = NullValueHandling.Include;

            Formatting = Formatting.Indented;
            Converters.Add(new StringEnumConverter());
            ContractResolver = new DIContractResolver(new DIExtension(Services), ServicePovider);
        }

        public static IServiceCollection GetStandardServiceCollection()
        {
            IServiceCollection services = new ServiceCollection();
            services.ConfigureStandardImplementation();
            return services;
        }
    }
}
