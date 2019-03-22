using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.AssetAdministrationShell.Implementations;
using BaSys40.Models.Core.Extensions.References;
using BaSys40.Models.Core.Constraints;
using Microsoft.Extensions.DependencyInjection;

namespace BaSys40.Models.Extensions
{
    public static class StandardDIExtensions
    {
        public static IServiceCollection ConfigureStandardImplementation(this IServiceCollection services)
        {
            services.AddTransient<IAsset, Asset>();
            services.AddTransient<IAssetAdministrationShell, AssetAdministrationShell>();
            services.AddTransient<ISubmodel, Submodel>();

            services.AddTransient<IDataElement, DataElement>();
            services.AddTransient(typeof(IDataElement<>), typeof(DataElement<>));
            services.AddTransient<IOperation, Operation>();
            services.AddTransient<IEvent, Event>();

            services.AddTransient<IOperationVariable, OperationVariable>();
            services.AddTransient<IArgument, Argument>();
            services.AddTransient<IValue, DataElementValue>();
            services.AddTransient<IKey, Key>();

            services.AddTransient<IReference, Reference>();

            return services;
        }
    }
}
