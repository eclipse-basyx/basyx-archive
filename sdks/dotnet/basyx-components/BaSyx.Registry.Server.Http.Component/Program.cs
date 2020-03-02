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
using BaSyx.Registry.ReferenceImpl.FileBased;
using BaSyx.Technologies.mDNS;
using BaSyx.Utils.Settings.Types;

namespace BaSyx.Registry.Server.Http.Component
{
    class Program
    {
        static void Main(string[] args)
        {
            ServerSettings registrySettings = ServerSettings.LoadSettings();
            RegistryHttpServer registryServer = new RegistryHttpServer(registrySettings);
            FileBasedRegistry fileBasedRegistry = new FileBasedRegistry();
            fileBasedRegistry.StartDiscovery();
            registryServer.SetRegistryProvider(fileBasedRegistry);
            registryServer.Run();
        }
    }
}
