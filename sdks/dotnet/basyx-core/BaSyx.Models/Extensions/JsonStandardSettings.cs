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
using BaSyx.Utils.DIExtensions;
using BaSyx.Utils.JsonHandling;
using Microsoft.Extensions.DependencyInjection;
using Newtonsoft.Json;
using Newtonsoft.Json.Converters;
using System;

namespace BaSyx.Models.Extensions
{
    public class JsonStandardSettings : JsonSerializerSettings
    {
        public IServiceProvider ServicePovider { get; }
        public IServiceCollection Services { get; }
        public JsonStandardSettings() : base()
        {
            Services = new ServiceCollection();
            Services.UseStandardImplementation();

            var serviceProviderFactory = new DefaultServiceProviderFactory();
            ServicePovider = serviceProviderFactory.CreateServiceProvider(Services);

            NullValueHandling = NullValueHandling.Include;
            DefaultValueHandling = DefaultValueHandling.Include;

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
            DefaultValueHandling = DefaultValueHandling.Include;

            Formatting = Formatting.Indented;
            Converters.Add(new StringEnumConverter());
            ContractResolver = new DIContractResolver(new DIExtension(Services), ServicePovider);
        }

        public static IServiceCollection GetStandardServiceCollection()
        {
            IServiceCollection services = new ServiceCollection();
            services.UseStandardImplementation();
            return services;
        }
    }
}
