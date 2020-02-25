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
    public class MqttEndpoint : IEndpoint
    {
        public string Address { get; }

        public Uri BrokerUri { get; }

        public string Topic { get; }

        public string Type => EndpointType.MQTT;

        public IEndpointSecurity Security { get; set; }

        public MqttEndpoint(string brokerUri, string topic)
        {
            brokerUri = brokerUri ?? throw new ArgumentNullException("url");
            BrokerUri = new Uri(brokerUri);
            Topic = topic ?? "/";
            Address = brokerUri + topic;
        }
    }
}
