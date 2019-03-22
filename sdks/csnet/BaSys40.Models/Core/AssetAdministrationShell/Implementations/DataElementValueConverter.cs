using BaSys40.Models.Core.AssetAdministrationShell.Enums;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.Attributes;
using BaSys40.Models.Core.Extensions.References;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Reflection;

namespace BaSys40.Models.Core.AssetAdministrationShell.Implementations
{
    public class DataElementValueConverter : JsonConverter<IDataElement>
    {
        static Dictionary<string, Type> DataElementInformationTypes;
        static DataElementValueConverter()
        {
            DataElementInformationTypes = new Dictionary<string, Type>();
            var types = typeof(DataElementValueConverter).Assembly.GetTypes();
            foreach (Type type in types)
            {
                var attrib = type.GetCustomAttribute(typeof(DataSpecificationAttribute), false);
                if(attrib != null && attrib is DataSpecificationAttribute dataSpecificationAttribute)
                {
                    DataElementInformationTypes.Add(dataSpecificationAttribute.Reference.First.Value, type);
                }
            }

        }

        public override bool CanWrite => false;
        public override bool CanRead => true;

        public override IDataElement ReadJson(JsonReader reader, Type objectType, IDataElement existingValue, bool hasExistingValue, JsonSerializer serializer)
        {
            JObject jObject;

            try
            {
                jObject = JObject.Load(reader);
            }
            catch (Exception)
            {
                return null;
            }

            var modelTypeToken = jObject.SelectToken("modelType")?.ToObject<ModelType>();
            var valueTypeToken = jObject.SelectToken("valueType")?.ToObject<DataType>();
            var embeddedDataSpecificationsToken = jObject.SelectToken("embeddedDataSpecifications");
            var conceptDescriptionToken = jObject.SelectToken("conceptDescription");

            IDataElement dataElement;
            List<IEmbeddedDataSpecification> embeddedDataSpecifications = null;
            ConceptDescription conceptDescription = null;
            var embeddedDataSpecificationsTokenChildToken = embeddedDataSpecificationsToken?.Children();
            if (embeddedDataSpecificationsTokenChildToken != null) //
            {
                embeddedDataSpecifications = new List<IEmbeddedDataSpecification>();
                foreach (var dataSpecificationToken in embeddedDataSpecificationsTokenChildToken)
                {
                    var dataSpecReference = dataSpecificationToken.SelectToken("hasDataSpecification")?.ToObject<Reference>();
                    if (dataSpecReference != null && DataElementInformationTypes.TryGetValue(dataSpecReference.First.Value, out Type type))
                    {
                        var content = dataSpecificationToken.SelectToken("dataSpecificationContent")?.ToObject(type);
                        if (content != null)
                            embeddedDataSpecifications.Add((IEmbeddedDataSpecification)content);
                    }
                }
                jObject.Remove("embeddedDataSpecifications");
            }
            if(conceptDescriptionToken != null)
            {
                var dataSpecifications = conceptDescriptionToken.SelectToken("embeddedDataSpecifications")?.Children();
                if(dataSpecifications != null)
                {
                    conceptDescription.EmbeddedDataSpecifications = new List<IEmbeddedDataSpecification>();
                    foreach (var dataSpecificationToken in dataSpecifications)
                    {
                        var dataSpecReference = dataSpecificationToken.SelectToken("hasDataSpecification")?.ToObject<Reference>();
                        if (dataSpecReference != null && DataElementInformationTypes.TryGetValue(dataSpecReference.First.Value, out Type type))
                        {
                            var content = dataSpecificationToken.SelectToken("dataSpecificationContent")?.ToObject(type);
                            if (content != null)
                                conceptDescription.EmbeddedDataSpecifications.Add((IEmbeddedDataSpecification)content);
                        }
                    }
                }
                serializer.Populate(conceptDescriptionToken.CreateReader(), conceptDescription);
            }

            if (modelTypeToken != null)
                dataElement = DataElementFactory.CreateDataElement(conceptDescription, embeddedDataSpecifications, modelTypeToken, valueTypeToken);
            else
                return null;
            
            serializer.Populate(jObject.CreateReader(), dataElement);

            return dataElement;            
        }

        public override void WriteJson(JsonWriter writer, IDataElement value, JsonSerializer serializer)
        {
            throw new NotImplementedException();
        }
    }
}
