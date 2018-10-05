using System;
using System.Collections.Generic;
using System.Linq;
using oneM2MClient.Protocols;
using Newtonsoft.Json.Linq;
using oneM2MClient.Utils;

namespace oneM2MClient.Client
{
    public abstract class Response
    {
        public oneM2M.ResponseStatusCodes ResponseStatusCode { get; set; }
        public primitiveContent PrimitiveContent { get; set; }
        public oneM2M.Time OriginatingTimestamp { get; set; }
        public oneM2M.Time ResultExpirationTimestamp { get; set; }
        public oneM2M.StdEventCats EventCategory { get; set; }
        public string To { get; set; }
        public string From { get; set; }
        public string RequestIdentifier { get; set; }
        public string ResponseBody { get; set; }
        public string ErrorMessage { get; set; }

        #region Special attributes of response primitive
        private List<string> urilField;
        public List<string> Uril {
            get
            {
                if ((this.urilField == null))
                {
                    this.urilField = new List<string>();
                }
                return this.urilField;
            }
            set
            {
                this.urilField = value;
            }
        }

        #endregion

        public Response(oneM2M.ResponseStatusCodes responseStatusCode, string requestIdentifier, primitiveContent primitiveContent, string to, string from, oneM2M.Time originatingTimestamp, oneM2M.Time resultExpirationTimestamp, oneM2M.StdEventCats eventCategory, string responseBody)
        {
            this.ResponseStatusCode = responseStatusCode;
            this.RequestIdentifier = requestIdentifier;
            this.PrimitiveContent = primitiveContent;
            this.To = to;
            this.From = from;
            this.OriginatingTimestamp = originatingTimestamp;
            this.ResultExpirationTimestamp = resultExpirationTimestamp;
            this.EventCategory = eventCategory;
            this.ResponseBody = responseBody;
        }
        
        public Response()
        {

        }

        public object GetResource()
        {
            if (this.PrimitiveContent != null && this.PrimitiveContent.Items != null && this.PrimitiveContent.Items.Count > 0)
            {
                return this.PrimitiveContent.Items.FirstOrDefault();
            }
            return null;
        }

        public bool TryGetResource<TEntity>(out TEntity resource)
        {
            if (this.PrimitiveContent != null && this.PrimitiveContent.Items != null && this.PrimitiveContent.Items.Count > 0)
            {
                var obj = this.PrimitiveContent.Items.FirstOrDefault(r => r.GetType() == typeof(TEntity) || r.GetType().IsSubclassOf(typeof(TEntity)));
                if (obj != null)
                {
                    resource = (TEntity)obj;
                    return true;
                }
            }
            resource = default(TEntity);
            return false;
        }

        protected primitiveContent ExtractPrimitiveContent(JProperty jProp)
        {
            if (jProp != null)
            {
                string sPc = jProp.Value.ToString();
                return ExtractPrimitiveContent(sPc);
            }
            return null;
        }

        protected primitiveContent ExtractPrimitiveContent(string sPc)
        {
            if (sPc.TryParseJson(out JToken pcToken))
            {
                if (sPc.Contains(oneM2M.ERROR_INDICATOR))
                {
                    ErrorMessage = ((pcToken as JObject).First as JProperty).Value.ToString();
                    return null;
                }
                if (pcToken.Type == JTokenType.Object)
                {
                    JToken root = (pcToken as JObject).Descendants().First();
                    JToken first = root.First;
                    string resourceTypeName = root.Path.Replace(oneM2M.NAMESPACE + oneM2M.NAMESPACE_DELIMITER, "");
                    Type t = Type.GetType(oneM2M.PRIMITIVE_PACKAGE + resourceTypeName, false, true); //throwOnErrorTrue
                    if (t == null)
                    {
                        if (!string.IsNullOrEmpty(resourceTypeName))
                        {
                            var prop = GetType().GetProperty(resourceTypeName.UppercaseFirst());
                            if (prop != null)
                            {
                                var obj = first.ToObject(prop.PropertyType);
                                prop.SetValue(this, obj);
                                return null;
                            }
                            else
                            {
                                ErrorMessage = "Original-Content: " + sPc;
                                throw new Exception("The resource could not be deserialized to property - property not found");
                            }
                        }
                        else
                        {
                            ErrorMessage = "Original-Content: " + sPc;
                            throw new Exception("resourceTypeName could not be identified or does not exist");
                        }
                    }
                    else
                    {
                        object content = first.ToObject(t);

                        if (content == null)
                        {
                            ErrorMessage = "Original-Content: " + sPc;
                            throw new Exception("Failed to generate object from JSON");
                        }

                        primitiveContent pc = new primitiveContent();
                        pc.Items.Add(content);
                        return pc;
                    }
                }
                else if (pcToken.Type == JTokenType.Array)
                {
                    JArray jArr = (pcToken as JArray);
                    primitiveContent pc = new primitiveContent();
                    foreach (var child in jArr.Children())
                    {
                        string path = (child.First as JProperty).Name.Replace(oneM2M.NAMESPACE + oneM2M.NAMESPACE_DELIMITER, "");
                        Type t = Type.GetType(oneM2M.PRIMITIVE_PACKAGE + path, true, true); //throwOnErrorTrue
                        object content = null;
                        try
                        {
                            content = child.First.First.ToObject(t);
                        }
                        catch { continue; }

                        if (content == null)
                        {
                            ErrorMessage = "Original-Content: " + sPc;
                            throw new Exception("Failed to generate object from JSON");
                        }
                        pc.Items.Add(content);
                    }
                    return pc;
                }
            }
            return null;
        }
    }
}
