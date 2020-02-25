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
using BaSyx.Utils.DIExtensions;
using Newtonsoft.Json.Serialization;
using System;

namespace BaSyx.Utils.JsonHandling
{
    public class DIContractResolver : CamelCasePropertyNamesContractResolver
    {
        public IDIExtension DIExtension { get; }
        public IServiceProvider ServiceProvider { get; }
        public DIContractResolver(IDIExtension diExtension, IServiceProvider serviceProvider)
        {
            DIExtension = diExtension;
            ServiceProvider = serviceProvider;
        }
        protected override JsonObjectContract CreateObjectContract(Type objectType)
        {
            if (DIExtension.IsTypeRegistered(objectType))
            {
                JsonObjectContract contract = DIResolveContract(objectType);
                contract.DefaultCreator = () => ServiceProvider.GetService(objectType);
                return contract;
            }

            return base.CreateObjectContract(objectType);
        }
        private JsonObjectContract DIResolveContract(Type objectType)
        {
            var registeredType = DIExtension.GetRegisteredTypeFor(objectType);
            if (registeredType != null)
                return base.CreateObjectContract(registeredType);
            else
                return CreateObjectContract(objectType);
        }
    }
}
