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
using System;
using System.Xml.Serialization;

namespace BaSyx.Utils.Settings.Types
{
    public class ServerSettings : Settings<ServerSettings>
    {
        public RegistryConfiguration RegistryConfig { get; set; } = new RegistryConfiguration();
       
        public class RegistryConfiguration
        {
            [XmlElement]
            public bool Activated { get; set; }
            [XmlElement]
            public string RegistryUrl { get; set; }
        }

    }
}
