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
using BaSyx.API.Components;
using BaSyx.Components.Common;
using BaSyx.Utils.Settings.Types;
using Microsoft.Extensions.DependencyInjection;

namespace BaSyx.Registry.Server.Http
{
    public class RegistryHttpServer : ServerApplication
    {
        public RegistryHttpServer(ServerSettings serverSettings = null, string[] webHostBuilderArgs = null)
            : base(typeof(Startup), serverSettings ?? ServerSettings.LoadSettings(), webHostBuilderArgs)
        { }

        public void SetRegistryProvider(IAssetAdministrationShellRegistry aasRegistryProvider)
        {
            WebHostBuilder.ConfigureServices(services =>
            {
                services.AddSingleton<IAssetAdministrationShellRegistry>(aasRegistryProvider);
            });
        }
    }
}
