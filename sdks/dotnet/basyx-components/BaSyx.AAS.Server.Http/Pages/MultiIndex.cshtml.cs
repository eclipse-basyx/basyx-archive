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
using BaSyx.Utils.Settings.Types;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace BaSyx.AAS.Server.Http.Pages
{
    public class MultiIndexModel : PageModel
    {
        public IAssetAdministrationShellAggregatorServiceProvider ServiceProvider { get; }
        public ServerSettings Settings { get; }

        public MultiIndexModel(IAssetAdministrationShellAggregatorServiceProvider provider, ServerSettings serverSettings)
        {
            ServiceProvider = provider;
            Settings = serverSettings;
        }

        public void OnGet()
        {

        }
    }
}
