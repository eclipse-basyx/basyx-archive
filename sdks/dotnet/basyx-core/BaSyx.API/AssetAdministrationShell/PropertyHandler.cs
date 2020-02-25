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
using BaSyx.Models.Core.AssetAdministrationShell.Generics.SubmodelElementTypes;
using System.Reflection;

namespace BaSyx.API.AssetAdministrationShell
{
    public class PropertyHandler
    {
        public GetPropertyValueHandler GetHandler { get; }
        public SetPropertyValueHandler SetHandler { get; private set; }
        public PropertyHandler(GetPropertyValueHandler getHandler, SetPropertyValueHandler setHandler)
        {
            GetHandler = getHandler;
            SetHandler = setHandler;
        }

        public PropertyHandler(MethodInfo getMethodInfo, MethodInfo setMethodInfo, object target)
        {
            if(getMethodInfo != null)
                GetHandler = (GetPropertyValueHandler)getMethodInfo.CreateDelegate(typeof(GetPropertyValueHandler), target);
            if(setMethodInfo != null)
                SetHandler = (SetPropertyValueHandler)setMethodInfo.CreateDelegate(typeof(SetPropertyValueHandler), target);
        }
    }

}
