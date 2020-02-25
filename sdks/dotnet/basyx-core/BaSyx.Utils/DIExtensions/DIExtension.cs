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
using Microsoft.Extensions.DependencyInjection;
using System;
using System.Collections.Generic;

namespace BaSyx.Utils.DIExtensions
{
    public class DIExtension : IDIExtension
    {
        IDictionary<Type, Type> typeDictionary = new Dictionary<Type, Type>();
        public DIExtension(IServiceCollection serviceCollection)
        {
            foreach (var service in serviceCollection)
            {
                typeDictionary[service.ServiceType] = service.ImplementationType;
            }
        }
        public Type GetRegisteredTypeFor(Type t)
        {
            return typeDictionary[t];
        }
        public bool IsTypeRegistered(Type t)
        {
            return typeDictionary.ContainsKey(t);
        }
    }
}
