using BaSys40.Utils.DIExtensions;
using Newtonsoft.Json.Serialization;
using System;

namespace BaSys40.Utils.JsonHandling
{
    public class DIContractResolver : CamelCasePropertyNamesContractResolver
    {
        IDIExtension diExtension;
        IServiceProvider serviceProvider;
        public DIContractResolver(IDIExtension diExtension, IServiceProvider serviceProvider)
        {
            this.diExtension = diExtension;
            this.serviceProvider = serviceProvider;
        }
        protected override JsonObjectContract CreateObjectContract(Type objectType)
        {
            if (diExtension.IsTypeRegistered(objectType))
            {
                JsonObjectContract contract = DIResolveContract(objectType);
                contract.DefaultCreator = () => serviceProvider.GetService(objectType);
                return contract;
            }

            return base.CreateObjectContract(objectType);
        }
        private JsonObjectContract DIResolveContract(Type objectType)
        {
            var registeredType = diExtension.GetRegisteredTypeFor(objectType);
            if (registeredType != null)
                return base.CreateObjectContract(registeredType);
            else
                return CreateObjectContract(objectType);
        }
    }
}
