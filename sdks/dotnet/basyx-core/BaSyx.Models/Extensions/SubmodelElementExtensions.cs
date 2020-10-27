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
        public static ISubmodelElementCollection CreateSubmodelElementCollectionFromObject(this object target, string idShort)
            => CreateSubmodelElementCollectionFromObject(target, idShort, BindingFlags.Public | BindingFlags.Instance);

        public static ISubmodelElementCollection CreateSubmodelElementCollectionFromObject(this object target, string idShort, BindingFlags bindingFlags)
            => CreateSubmodelElementCollection(target.GetType(), idShort, bindingFlags, target);

        public static ISubmodelElementCollection CreateSubmodelElementCollection<T>(this IEnumerable<T> enumerable, string idShort)
            => CreateSubmodelElementCollection<T>(enumerable, idShort, BindingFlags.Public | BindingFlags.Instance);

        public static ISubmodelElementCollection CreateSubmodelElementCollection<T>(this IEnumerable<T> enumerable, string idShort, BindingFlags bindingFlags)
        {
            SubmodelElementCollection smCollection = new SubmodelElementCollection(idShort);
            Type type = typeof(T);
            foreach (var item in enumerable)
            {
                foreach (var property in type.GetProperties(bindingFlags))
                {                    
                    ISubmodelElement smElement = CreateSubmodelElement(property, bindingFlags, item);
                    smCollection.Value.Create(smElement);
                }
            }
            return smCollection;
        }

        public static ISubmodelElementCollection CreateSubmodelElementCollection(this Type type, string idShort)
            => CreateSubmodelElementCollection(type, idShort, BindingFlags.Public | BindingFlags.Instance, null);

        public static ISubmodelElementCollection CreateSubmodelElementCollection(this Type type, string idShort, BindingFlags bindingFlags)
            => CreateSubmodelElementCollection(type, idShort, bindingFlags, null);

        public static ISubmodelElementCollection CreateSubmodelElementCollection(this Type type, string idShort, BindingFlags bindingFlags, object target)
        {
            SubmodelElementCollection smCollection = new SubmodelElementCollection(idShort);

            foreach (var property in type.GetProperties(bindingFlags))
            {
                ISubmodelElement smElement = CreateSubmodelElement(property, bindingFlags, target);
                smCollection.Value.Create(smElement);
            }
            return smCollection;
        }

        public static ISubmodelElement CreateSubmodelElement(this PropertyInfo property)
            => CreateSubmodelElement(property, BindingFlags.Public | BindingFlags.Instance, null);

        public static ISubmodelElement CreateSubmodelElement(this PropertyInfo property, BindingFlags bindingFlags)
           => CreateSubmodelElement(property, bindingFlags, null);

        public static ISubmodelElement CreateSubmodelElement(this PropertyInfo property, BindingFlags bindingFlags, object target)
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
            else if (property.PropertyType == typeof(DateTime))
            {
                Property smProp = new Property(property.Name, new DataType(DataObjectType.DateTime));
                if (target != null && property.GetValue(target) is DateTime dateTime)
                    smProp.Value = dateTime.ToUniversalTime().ToString("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'fff'Z'");

                return smProp;
            }
            else
            {
                SubmodelElementCollection smCollection = new SubmodelElementCollection(property.Name);

                object subTarget = null;
                if(target != null)
                    subTarget = property.GetValue(target);

                foreach (var subProperty in dataType.SystemType.GetProperties(bindingFlags))
                {
                    ISubmodelElement smElement = CreateSubmodelElement(subProperty, bindingFlags, subTarget);
                    smCollection.Value.Create(smElement);
                }
                return smCollection;
            }
        }
    }
}
