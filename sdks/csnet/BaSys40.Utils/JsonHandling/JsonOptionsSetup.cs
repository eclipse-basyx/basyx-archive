using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;
using Microsoft.Extensions.DependencyInjection;
using System;
using BaSys40.Utils.DIExtensions;

namespace BaSys40.Utils.JsonHandling
{
    public class JsonOptionsSetup : IConfigureOptions<MvcJsonOptions>
    {
        IServiceProvider serviceProvider;
        public JsonOptionsSetup(IServiceProvider serviceProvider)
        {
            this.serviceProvider = serviceProvider;
        }
        public void Configure(MvcJsonOptions options)
        {
            options.SerializerSettings.ContractResolver = new DIContractResolver(serviceProvider.GetService<IDIExtension>(), serviceProvider);
            options.SerializerSettings.NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore;
        }
    }
}
