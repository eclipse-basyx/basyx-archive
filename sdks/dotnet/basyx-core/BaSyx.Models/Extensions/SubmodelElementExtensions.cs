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
using BaSyx.Models.Core.AssetAdministrationShell.Identification;
using BaSyx.Models.Core.AssetAdministrationShell.Implementations;
using BaSyx.Models.Core.Common;
using NLog;
using System;
using System.Collections.Generic;
using System.Reflection;

namespace BaSyx.Models.Extensions
{
    public static class SubmodelElementExtensions
    {
        private static readonly ILogger logger = LogManager.GetCurrentClassLogger();

        internal static T As<T>(this IReferable referable) where T : class, IReferable
        {
            return referable as T;
        }

        public static T Cast<T>(this IReferable referable) where T : class, IReferable
        {
            return referable as T;
        }

        public static IValue GetValue(this ISubmodelElement submodelElement)
        {
            return submodelElement?.Get?.Invoke(submodelElement);
        }

        public static T GetValue<T>(this ISubmodelElement submodelElement)
        {
            IValue value = submodelElement?.Get?.Invoke(submodelElement);
            if (value != null)
                return value.ToObject<T>();
            else
                return default;
        }

        
        public static void SetValue<T>(this ISubmodelElement submodelElement, T value)
        {
            if(typeof(IValue).IsAssignableFrom(typeof(T)))
                submodelElement?.Set?.Invoke(submodelElement, value as IValue);
            else
                submodelElement?.Set?.Invoke(submodelElement, new ElementValue<T>(value));
        }

        public static void SetValue(this ISubmodelElement submodelElement, IValue value)
        {
            submodelElement?.Set?.Invoke(submodelElement, value);
        }

        public static void SetValue(this ISubmodelElement submodelElement, object value, DataType valueType)
        {
            submodelElement?.Set?.Invoke(submodelElement, new ElementValue(value, valueType));
        }

        public static ISubmodelElementCollection CreateSubmodelElementCollection<T>(this IEnumerable<T> enumerable, string idShort)
        {
            SubmodelElementCollection smCollection = new SubmodelElementCollection(idShort);
            Type type = typeof(T);
            foreach (var item in enumerable)
            {
                foreach (var property in type.GetProperties())
                {                    
                    ISubmodelElement smElement = CreateSubmodelElement(property, item);
                    smCollection.Value.Create(smElement);
                }
            }
            return smCollection;
        }

        public static ISubmodelElementCollection CreateSubmodelElementCollection(this Type type, string idShort)
        {
            SubmodelElementCollection smCollection = new SubmodelElementCollection(idShort);

            foreach (var property in type.GetProperties(BindingFlags.Public | BindingFlags.Instance))
            {
                ISubmodelElement smElement = CreateSubmodelElement(property);
                smCollection.Value.Create(smElement);
            }
            return smCollection;
        }

        public static ISubmodelElement CreateSubmodelElement(this PropertyInfo property, object target = null)
        {
            DataType dataType = DataType.GetDataTypeFromSystemType(property.PropertyType);
            if(dataType == null)
            {
                logger.Warn($"Unable to convert system type {property.PropertyType} to DataType");
                return null;
            }

            if (DataType.IsSimpleType(property.PropertyType))
            {
                Property smProp = new Property(property.Name, dataType);
                if (target != null)
                    smProp.Value = property.GetValue(target);

                return smProp;
            }
            else
            {
                SubmodelElementCollection smCollection = new SubmodelElementCollection(property.Name);

                object subTarget = null;
                if(target != null)
                    subTarget = property.GetValue(target);

                foreach (var subProperty in dataType.SystemType.GetProperties(BindingFlags.Public | BindingFlags.Instance))
                {
                    ISubmodelElement smElement = CreateSubmodelElement(subProperty, subTarget);
                    smCollection.Value.Create(smElement);
                }
                return smCollection;
            }
        }
    }
}
