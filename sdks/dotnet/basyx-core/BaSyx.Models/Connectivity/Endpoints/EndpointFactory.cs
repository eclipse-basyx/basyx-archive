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

namespace BaSyx.Models.Connectivity
{
    public static partial class EndpointFactory
    {
        public static IEndpoint CreateEndpoint(string endpointType, string address, IEndpointSecurity security)
        {
            switch (endpointType.ToLower())
            {
                case EndpointType.HTTP: return new HttpEndpoint(address);
                case EndpointType.MQTT:
                    {
                        Uri uri = new Uri(address);
                        var brokerUri = uri.AbsoluteUri;
                        var topic = uri.AbsolutePath;

                        return new MqttEndpoint(brokerUri, topic);
                    }
                case EndpointType.OPC_UA: return new OpcUaEndpoint(address);
                default:
                    return null;
            }
        }
    }
}
