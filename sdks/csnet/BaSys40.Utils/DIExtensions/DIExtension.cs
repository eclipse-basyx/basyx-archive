using Microsoft.Extensions.DependencyInjection;
using System;
using System.Collections.Generic;

namespace BaSys40.Utils.DIExtensions
{
    public class DIExtension : IDIExtension
    {
        IDictionary<Type, Type> typeDictionary = new Dictionary<Type, Type>();
        public DIExtension(IServiceCollection serviceCollection)
        {
            foreach (var service in serviceCollection)
            {
                typeDictionary[service.ServiceType] = service.ImplementationType;
            }
        }
        public Type GetRegisteredTypeFor(Type t)
        {
            return typeDictionary[t];
        }
        public bool IsTypeRegistered(Type t)
        {
            return typeDictionary.ContainsKey(t);
        }
    }
}
