using System;
using System.Collections.Generic;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using Newtonsoft.Json.Serialization;
using oneM2MClient.Protocols;

namespace oneM2MClient.Utils
{
    public class PrimitiveContentJsonSerializer : JsonConverter
    {
        private static readonly JsonSerializer jsonSerializer;
        static PrimitiveContentJsonSerializer()
        {
            JsonSerializerSettings settings = new JsonSerializerSettings();
            settings.NullValueHandling = NullValueHandling.Ignore;
            settings.ContractResolver = new LowercaseContractResolver();

            jsonSerializer = JsonSerializer.Create(settings);
        }

        public override void WriteJson(JsonWriter writer, object value, JsonSerializer serializer)
        {
            primitiveContent pc = (primitiveContent)value;
            List<object> items = pc.Items;

            JObject root = new JObject();
            foreach (var obj in items)
            {
                string className = obj.GetType().Name.ToLower();
                string key = oneM2M.NAMESPACE + oneM2M.NAMESPACE_DELIMITER + className;
                               
                JObject jObj = JObject.FromObject(obj, jsonSerializer);
                jObj = DeleteEmptyCollectionsAndClasses(jObj);
                JToken jTok = root.GetValue(key);
                if (jTok != null)
                {
                    if (jTok.Type == JTokenType.Array)
                        (jTok as JArray).Add(value);
                    else if (jTok.Type == JTokenType.Object)
                    {
                        JArray jArray = new JArray();
                        jArray.Add(jTok);
                        jArray.Add(value);
                        root.Add(key, jArray);
                    }
                }
                else
                    root.Add(key, jObj);                
            }
            root.WriteTo(writer);
        }

        private JObject DeleteEmptyCollectionsAndClasses(JObject obj)
        {
            List<JToken> removeList = new List<JToken>();
            foreach (var item in obj.Children())
            {
                if (item.Type == JTokenType.Property)
                {
                    JProperty prop = (JProperty)item;
                    if (prop.Value.Type == JTokenType.Array)
                    {
                        JArray jArr = (JArray)prop.Value;
                        if (jArr.Count == 0)
                            removeList.Add(item);
                    }
                    else if (prop.Value.Type == JTokenType.Object)
                    {
                        if (prop.Value.HasValues)
                        {
                            DeleteEmptyCollectionsAndClasses((JObject)prop.Value);
                            if (!prop.Value.HasValues)
                                removeList.Add(prop);
                        }
                        else
                            removeList.Add(prop);
                        //removeList.Add(obj);
                    }
                }
                    
            }

            foreach (var item in removeList)
            {
                item.Remove();
            }

            return (obj);
        }

        public class LowercaseContractResolver : DefaultContractResolver
        {
            protected override string ResolvePropertyName(string propertyName)
            {
                return propertyName.ToLower();
            }
        }

        public override object ReadJson(JsonReader reader, Type objectType, object existingValue, JsonSerializer serializer)
        {
            throw new NotImplementedException("Unnecessary because CanRead is false. The type will skip the converter.");
        }

        public override bool CanRead
        {
            get { return false; }
        }
        public override bool CanConvert(Type objectType)
        {
            return (true);  
        }
    }
}
