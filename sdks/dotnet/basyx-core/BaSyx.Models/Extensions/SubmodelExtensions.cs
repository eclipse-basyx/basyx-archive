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
using BaSyx.Models.Core.AssetAdministrationShell.Implementations;
using BaSyx.Models.Core.AssetAdministrationShell.Implementations.SubmodelElementTypes;
using BaSyx.Models.Core.AssetAdministrationShell.References;
using BaSyx.Models.Core.Common;
using BaSyx.Utils.StringOperations;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;

namespace BaSyx.Models.Extensions
{
    public static class SubmodelExtensions
    {
        public static ISubmodel CreateSubmodelFromObject(this object target)
        {
            if (target == null)
                return null;

            Type type = target.GetType();
            Submodel submodel = new Submodel()
            {
                IdShort = type.Name,
                Identification = new Identifier(type.FullName, KeyType.Custom)
            };
            foreach (var property in type.GetProperties(BindingFlags.Public | BindingFlags.Instance))
            {
                ISubmodelElement smElement = property.CreateSubmodelElement(target);
                submodel.SubmodelElements.Add(smElement);
            }
            return submodel;
        }

        private static readonly JsonMergeSettings JsonMergeSettings = new JsonMergeSettings
        {
            MergeArrayHandling = MergeArrayHandling.Merge,
            MergeNullValueHandling = MergeNullValueHandling.Ignore
        };

        public static JArray CustomizeSubmodel(this ISubmodel submodel, string[] columns)
        {
            JArray jArray = new JArray();
            if (submodel.SubmodelElements.Count > 0)
            {
                foreach (var element in submodel.SubmodelElements)
                {
                    JArray elementJArray = CustomizeSubmodelElement(element, columns);
                    jArray.Merge(elementJArray, new JsonMergeSettings() { MergeArrayHandling = MergeArrayHandling.Union } );
                }
            }
            return jArray;
        }

        private static JArray CustomizeSubmodelElement(ISubmodelElement element, string[] columns)
        {
            JArray jArray = new JArray();
            Type elementType = element.GetType();
            List<PropertyInfo> propertyInfos = elementType.GetProperties(BindingFlags.Public | BindingFlags.Instance).ToList();
            JObject jObj = new JObject();
            if (element.ModelType == ModelType.SubmodelElementCollection)
            {
                var valueContainer = element as SubmodelElementCollection;
                foreach (var subElement in valueContainer.Value)
                {
                    JArray subJArray = CustomizeSubmodelElement(subElement, columns);
                    jArray.Merge(subJArray, new JsonMergeSettings() { MergeArrayHandling = MergeArrayHandling.Union });
                }
            }
            else
            {
                foreach (var column in columns)
                {
                    var info = propertyInfos.Find(p => p.Name == column.UppercaseFirst());
                    if (info != null)
                    {
                        var value = info.GetValue(element);
                        if (value != null)
                        {
                            jObj.Add(column, JToken.FromObject(value));
                        }
                    }
                }
            }
            if(jObj.HasValues)
                jArray.Add(jObj);

            return jArray;
        }

        public static JObject MinimizeSubmodel(this ISubmodel submodel)
        {
            JObject jObject = new JObject();
            if (submodel.SubmodelElements.Count > 0)
            {
                JObject submodelElementsObject = MinimizeSubmodelElements(submodel.SubmodelElements);
                jObject.Merge(submodelElementsObject, JsonMergeSettings);
            }
            return jObject;
        }

        private static JObject MinimizeSubmodelElements(IEnumerable<ISubmodelElement> submodelElements)
        {
            JObject jObject = new JObject();
            foreach (var smElement in submodelElements)
            {
                if (smElement.ModelType == ModelType.SubmodelElementCollection)
                {
                    JObject subObjects = MinimizeSubmodelElements((smElement as SubmodelElementCollection).Value);
                    jObject.Add(smElement.IdShort, subObjects);
                }
                else
                {
                    if (smElement is IFile file)
                        jObject.Add(file.IdShort, new JObject(new JProperty("mimeType", file.MimeType), new JProperty("value", file.Value)));
                    else if (smElement is Property property)
                    {
                        object value = property.Value;
                        if (value == null)
                            value = property.Get?.Invoke(property)?.Value;

                        jObject.Add(property.IdShort, new JValue(value));
                    }
                }
            }
            return jObject;
        }
    }
}
