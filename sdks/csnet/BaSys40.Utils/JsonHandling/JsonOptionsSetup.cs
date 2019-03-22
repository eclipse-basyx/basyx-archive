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

        public MvcJsonOptions Options { get; private set; }
        public JsonOptionsSetup(IServiceProvider serviceProvider)
        {
            this.serviceProvider = serviceProvider;
        }
        public void Configure(MvcJsonOptions options)
        {
            options.SerializerSettings.ContractResolver = new DIContractResolver(serviceProvider.GetService<IDIExtension>(), serviceProvider);
            options.SerializerSettings.NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore;
            options.SerializerSettings.Converters.Add(new Newtonsoft.Json.Converters.StringEnumConverter());

            Options = options;
        }
    }
}
