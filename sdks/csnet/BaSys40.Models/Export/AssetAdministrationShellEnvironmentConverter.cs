using BaSys40.Models.Core;
using BaSys40.Models.Core.AssetAdministrationShell.Enums;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.Attributes;
using BaSys40.Models.Core.Extensions.References;
using BaSys40.Models.Core.Identification;
using BaSys40.Models.Export;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Reflection;

namespace BaSys40.Models.Export
{
    public class AssetAdministrationShellEnvironmentConverter : JsonConverter
    {
        //public override bool CanWrite => true;
        //public override bool CanRead => false;

        public override bool CanConvert(Type objectType)
        {
            if (typeof(IAssetAdministrationShell).IsAssignableFrom(objectType))
                return true;
            else
                return false;
        }

        public override object ReadJson(JsonReader reader, Type objectType, object existingValue, JsonSerializer serializer)
        {
            throw new NotImplementedException();
        }

        public override void WriteJson(JsonWriter writer, object value, JsonSerializer serializer)
        {
            JObject jObj = new JObject();
            Type type = value.GetType();

            foreach (PropertyInfo prop in type.GetProperties())
            {
                object propVal = prop.GetValue(value);
                if (propVal != null)
                {
                    var attrib = prop.GetCustomAttribute(typeof(ExportAsReferenceAttribute), true);
                    var propName = prop.Name.Substring(0, 1).ToLower() + prop.Name.Substring(1, prop.Name.Length - 1);
                    if (attrib != null)
                    {
                        var exportAttrib = (ExportAsReferenceAttribute)attrib;
                        if (propVal != null)
                        {
                            if (typeof(IIdentifiable).IsAssignableFrom(propVal.GetType()))
                            {
                                IIdentifiable identifiable = propVal as IIdentifiable;
                                if (identifiable.Identification?.Id != null)
                                {
                                    IReference reference = new Reference(new ModelKey(exportAttrib.KeyElement, identifiable.Identification.IdType.Value, identifiable.Identification.Id));
                                    jObj.Add(propName, JToken.FromObject(reference, serializer));
                                }
                                continue;
                            }
                            if(propVal is IEnumerable)
                            {
                                JArray jArray = new JArray();
                                IEnumerable<IIdentifiable> identifiables = (IEnumerable<IIdentifiable>)propVal;
                                foreach (var identifiable in identifiables)
                                {
                                    if (identifiable.Identification?.Id != null)
                                    {
                                        IReference reference = new Reference(new ModelKey(exportAttrib.KeyElement, identifiable.Identification.IdType.Value, identifiable.Identification.Id));
                                        jArray.Add(JToken.FromObject(reference, serializer));
                                    }
                                }
                                jObj.Add(propName, jArray);
                                continue;
                            }
                        }
                    }
                    jObj.Add(propName, JToken.FromObject(propVal, serializer));
                }
            }
            jObj.WriteTo(writer);
        }
    }
}
