using oneM2MClient.Protocols;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.Text;
using oneM2MClient.Utils;

namespace oneM2MClient.Client
{
    public class HttpRequest : Request
    {
        public string Body { get; private set; }
        public int Method { get; private set; }
        public Dictionary<string, List<string>> Query { get; } = new Dictionary<string, List<string>>();
        public Dictionary<string, List<string>> Header { get; } = new Dictionary<string, List<string>>();

        public HttpRequest(Request request)
        {
            this.RequestPrimitive = request.RequestPrimitive;
            this.CSEName = request.CSEName;
            this.ContentMIME = request.ContentMIME;
            this.AcceptMIME = request.AcceptMIME;
            this.EndpointAddress = request.EndpointAddress;
            this.MillisecondsTimeout = request.MillisecondsTimeout;
            this.RequestPath = request.RequestPath;

            Method = (int)RequestPrimitive.Op;

            AdaptQuery(RequestPrimitive);
            AdaptHeader(RequestPrimitive);
            AdaptBody(RequestPrimitive);
        }

        public HttpRequest(string clientId, string endpointAddress, string cseName, oneM2M.Operation operation, oneM2M.ResourceType resourceType, string path) 
            : base(clientId, endpointAddress, cseName, operation, resourceType, path)  { }

        public HttpRequest(string clientId, string endpointAddress, string cseName, string path)
            : base (clientId, endpointAddress, cseName, path)  { }

        public static string ConcatQuery(List<string> list)
        {
            StringBuilder sb = new StringBuilder();
            bool firstStr = true;
            foreach (string str in list)
            {
                if (str != null)
                {
                    if(!firstStr)
                        sb.Append("+");
                    sb.Append(str);
                    firstStr = false;
                }
            }
            return sb.Length > 0 ? sb.ToString() : "";
        }

        private void AdaptQuery(rqp requestPrimitive)
        {
            if (requestPrimitive.Rt != null)
                Add(Query, oneM2M.Name.RESPONSE_TYPE_VALUE, requestPrimitive.Rt.Rtv);

            Add(Query, oneM2M.Name.RESOURCE_TYPE, requestPrimitive.Ty);
            Add(Query, oneM2M.Name.RESULT_PERSISTENCE, requestPrimitive.Rp);
            Add(Query, oneM2M.Name.RESULT_CONTENT, requestPrimitive.Rcn);
            Add(Query, oneM2M.Name.DELIVERY_AGGREGATION, requestPrimitive.Drt);

            if (requestPrimitive.Fc != null)
            {
                filterCriteria fc = requestPrimitive.Fc;
                Add(Query, oneM2M.Name.CREATED_BEFORE, fc.Crb);
                Add(Query, oneM2M.Name.CREATED_AFTER, fc.Cra);
                Add(Query, oneM2M.Name.MODIFIED_SINCE, fc.Ms);
                Add(Query, oneM2M.Name.UNMODIFIED_SINCE, fc.Us);
                Add(Query, oneM2M.Name.STATE_TAG_SMALLER, fc.Sts);
                Add(Query, oneM2M.Name.STATE_TAG_BIGGER, fc.Stb);
                Add(Query, oneM2M.Name.EXPIRE_BEFORE, fc.Exb);
                Add(Query, oneM2M.Name.EXPIRE_AFTER, fc.Exa);

                foreach (string value in fc.Lbl)
                {
                    Add(Query, oneM2M.Name.LABELS, value);
                }

                Add(Query, oneM2M.Name.RESOURCE_TYPE, fc.Ty);
                Add(Query, oneM2M.Name.SIZE_ABOVE, fc.Sza);
                Add(Query, oneM2M.Name.SIZE_BELOW, fc.Szb);

                foreach (string value in fc.Cty)
                {
                    Add(Query, oneM2M.Name.CONTENT_TYPE, value);
                }

                Add(Query, oneM2M.Name.LIMIT, fc.Lim);

                if (fc.Atr != null)
                {
                    foreach (attribute attribute in fc.Atr)
                    {
                        Add(Query, attribute.Nm, attribute.Val);
                    }
                }
                Add(Query, oneM2M.Name.FILTER_USAGE, fc.Fu);
            }
            Add(Query, oneM2M.Name.DISCOVERY_RESULT_TYPE, requestPrimitive.Drt);
        }

        private void Add(Dictionary<string, List<string>> dic, string name, object obj)
        {
            if (obj == null)
                return;
            if (!dic.ContainsKey(name))
                dic.Add(name, new List<string>());
            dic[name].Add(obj.ToString());
        }

        private void AdaptHeader(rqp requestPrimitive)
        {
            Add(Header, oneM2M.Name.FROM, requestPrimitive.Fr);
            Add(Header, oneM2M.Name.REQUEST_IDENTIFIER, requestPrimitive.Rqi);
            Add(Header, oneM2M.Name.GROUP_ID, requestPrimitive.Gid);
            
            if (requestPrimitive.Rt != null)
            {
                responseTypeInfo rti = requestPrimitive.Rt;
                if (rti.Nu.Count != 0)
                {
                    StringBuilder sb = new StringBuilder();
                    bool firstStr = true;
                    foreach (string ss in rti.Nu)
                    {
                        if (!firstStr)
                            sb.Append("&");

                        firstStr = false;
                        sb.Append(ss);
                    }
                    Add(Header, oneM2M.Name.RESPONSE_TYPE_VALUE, sb.ToString());
                }
            }
            
            Add(Header, oneM2M.Name.ORIGINATING_TIMESTAMP, requestPrimitive.Ot);
            Add(Header, oneM2M.Name.RESULT_EXPIRATION_TIMESTAMP, requestPrimitive.Rset);
            Add(Header, oneM2M.Name.REQUEST_EXPIRATION_TIMESTAMP, requestPrimitive.Rqet);
            Add(Header, oneM2M.Name.OPERATION_EXECUTION_TIME, requestPrimitive.Oet);
            Add(Header, oneM2M.Name.EVENT_CATEGORY, requestPrimitive.Ec);
        }

        private void AdaptBody(rqp requestPrimitive)
        {
            Body = JsonConvert.SerializeObject(requestPrimitive.Pc, Formatting.Indented, new PrimitiveContentJsonSerializer());
        }  

    }
}
