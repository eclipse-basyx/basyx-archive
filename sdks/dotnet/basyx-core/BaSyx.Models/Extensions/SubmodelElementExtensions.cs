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
using BaSyx.Models.Core.AssetAdministrationShell.Generics;
using BaSyx.Models.Core.AssetAdministrationShell.Generics.SubmodelElementTypes;
using BaSyx.Models.Core.AssetAdministrationShell.Identification;
using BaSyx.Models.Core.AssetAdministrationShell.Implementations.SubmodelElementTypes;
using BaSyx.Models.Core.Common;
using System;
using System.Collections.Generic;
using System.Reflection;

namespace BaSyx.Models.Extensions
{
    public static class SubmodelElementExtensions
    {
        public static T Cast<T>(this IReferable referable) where T : class, IReferable
        {
            return referable as T;
        }

        [Obsolete("Use Cast<T>(IReferable referable) instead")]
        public static T ToModelElement<T>(this IReferable referable) where T : class, IReferable
        {
            return referable as T;
        }

        public static ISubmodelElementCollection CreateSubmodelElementCollection<T>(this IEnumerable<T> enumerable, string idShort)
        {
            SubmodelElementCollection smCollection = new SubmodelElementCollection()
            {
                IdShort = idShort
            };
            Type type = typeof(T);
            foreach (var item in enumerable)
            {
                foreach (var property in type.GetProperties())
                {
                    ISubmodelElement smElement = CreateSubmodelElement(property, item);
                    smCollection.Value.Add(smElement);
                }
            }
            return smCollection;
        }

        public static ISubmodelElement CreateSubmodelElement(this PropertyInfo property, object target)
        {
            if (DataType.IsSimpleType(property.PropertyType))
            {
                DataType dataType = DataType.GetDataTypeFromSystemType(property.PropertyType);
                Property smProp = new Property(dataType)
                {
                    IdShort = property.Name,
                    Value = property.GetValue(target)
                };
                return smProp;
            }
            else
            {
                SubmodelElementCollection smCollection = new SubmodelElementCollection()
                {
                    IdShort = property.Name
                };
                object value = property.GetValue(target);
                Type valueType = value.GetType();
                foreach (var subProperty in valueType.GetProperties(BindingFlags.Public | BindingFlags.Instance))
                {
                    ISubmodelElement smElement = CreateSubmodelElement(subProperty, value);
                    smCollection.Value.Add(smElement);
                }
                return smCollection;
            }
        }
    }
}
