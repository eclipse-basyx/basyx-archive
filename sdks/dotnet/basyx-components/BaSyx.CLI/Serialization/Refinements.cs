using BaSyx.Models.Core.Common;
using BaSyx.Models.Export;
using System;
using System.Collections.Generic;
using System.Text;

namespace BaSyx.CLI.Serialization
{
    public static class Refinements
    {
        public static void ProcessSubmodelElements(List<EnvironmentSubmodelElement_V2_0> submodelElements)
        {
            foreach (var element in submodelElements)
            {
                if (element.submodelElement.ModelType == ModelType.Property)
                {
                    Property_V2_0 property = (Property_V2_0)element.submodelElement;
                    if (string.IsNullOrEmpty(property.ValueType))
                        property.ValueType = "string";
                }
                if (element.submodelElement.ModelType == ModelType.SubmodelElementCollection)
                {
                    SubmodelElementCollection_V2_0 collection = (SubmodelElementCollection_V2_0)element.submodelElement;
                    if (collection.Value?.Count > 0)
                        ProcessSubmodelElements(collection.Value);
                }
            }
        }
    }
}
