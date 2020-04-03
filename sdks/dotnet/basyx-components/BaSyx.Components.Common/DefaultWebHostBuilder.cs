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
using BaSyx.Utils.Settings.Types;
using Microsoft.AspNetCore;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Hosting;
using System;

namespace BaSyx.Components.Common
{
    public static class DefaultWebHostBuilder
    {
        public static IWebHostBuilder CreateWebHostBuilder(string[] args, ServerSettings settings, Type startupType)
        {
            var webHost = WebHost.CreateDefaultBuilder(args)
                .UseStartup(startupType)                        
                .UseContentRoot(AppContext.BaseDirectory);

            if (settings?.ServerConfig.Hosting?.Environment != null)
                webHost.UseEnvironment(settings.ServerConfig.Hosting.Environment);
            else
                webHost.UseEnvironment(Environments.Production);

            if (settings?.ServerConfig?.Hosting?.Urls?.Count > 0)
                webHost.UseUrls(settings.ServerConfig.Hosting.Urls.ToArray());

            return webHost;
        }
    }
}
