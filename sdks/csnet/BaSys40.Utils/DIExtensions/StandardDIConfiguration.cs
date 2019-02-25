using BaSys40.Utils.JsonHandling;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.DependencyInjection.Extensions;
using Microsoft.Extensions.Options;

namespace BaSys40.Utils.DIExtensions
{
    public static class StandardDIConfiguration
    {
        public static IServiceCollection ConfigureStandardDI(this IServiceCollection services)
        {
            services.TryAddSingleton<IDIExtension>(s =>
            {
                return new DIExtension(services);
            });
            services.AddTransient<IConfigureOptions<MvcJsonOptions>, JsonOptionsSetup>();

            return services;
        }
    }
}
