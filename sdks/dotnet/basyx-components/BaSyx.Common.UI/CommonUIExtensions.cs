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
using BaSyx.Components.Common;
using Microsoft.Extensions.DependencyInjection;

namespace BaSyx.Common.UI
{
    public static class PageNames
    {
        public const string AssetAdministrationShellServer = "SingleShell";
        public const string AssetAdministrationShellRepositoryServer = "MultipleShells";

        public const string SubmodelServer = "SingleSubmodel";
        public const string SubmodelRepositoryServer = "MultipleSubmodels";

        public const string RegistryServer = "Registry";
    }
    public static class CommonUIExtensions
    {
        public static void AddBaSyxUI(this ServerApplication serverApp, string pageName)
        {
            serverApp.ConfigureServices(services =>
            {
                services.AddBaSyxUI(pageName);
            });            
        }

        public static void AddBaSyxUI(this IServiceCollection services, string pageName)
        {
            services
             .AddRazorPages()
             .AddRazorRuntimeCompilation()
             .AddRazorPagesOptions(options => options.Conventions.AddPageRoute("/" + pageName, "ui"));

            services.ConfigureOptions(typeof(CommonUIConfigureOptions));
        }
    }
}
