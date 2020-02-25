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
using BaSyx.Utils.JsonHandling;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.DependencyInjection.Extensions;
using Microsoft.Extensions.Options;

namespace BaSyx.Utils.DIExtensions
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
