using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using oneM2MClient.Client.Bindings;
using oneM2MClient.Protocols;
using oneM2MClient.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

namespace oneM2MClient.Client
{
    public class MqttRequest : Request
    {
        public string RequestTopic { get; private set; }
        public JObject MessageBody { get; private set; }

        public MqttRequest(string clientId, string endpointAddress, string cseName, oneM2M.Operation operation, oneM2M.ResourceType resourceType, string path) 
            : base(clientId, endpointAddress, cseName, operation, resourceType, path)
        {
            ContentMIME = "json";
            EndpointAddress = endpointAddress;
            RequestTopic = string.Join("/", MqttBinding.requestTopic, RequestPrimitive.Fr, CSEName, ContentMIME);
        }

        public MqttRequest(string clientId, string endpointAddress, string cseName, string path)
            : this (clientId, endpointAddress, cseName, oneM2M.Operation.NOOPERATION, oneM2M.ResourceType.NoResource, path)  { }

        public MqttRequest(Request request)
        {
            this.RequestPrimitive = request.RequestPrimitive;
            this.CSEName = request.CSEName;
            this.ContentMIME = "json";
            this.AcceptMIME = "json";
            this.EndpointAddress = request.EndpointAddress;
            this.MillisecondsTimeout = request.MillisecondsTimeout;
            this.RequestPath = request.RequestPath;
            this.RequestTopic = string.Join("/", MqttBinding.requestTopic, RequestPrimitive.Fr, CSEName, ContentMIME);
            

            MessageBody = new JObject();
            MessageBody.Add(oneM2M.Name.OPERATION, RequestPrimitive.Op.ToString());
            MessageBody.Add(oneM2M.Name.TO, RequestPrimitive.To);
            MessageBody.Add(oneM2M.Name.FROM, RequestPrimitive.Fr);
            MessageBody.Add(oneM2M.Name.REQUEST_IDENTIFIER, RequestPrimitive.Rqi);

            if (RequestPrimitive.Op == (int)oneM2M.Operation.CREATE)
                MessageBody.Add(oneM2M.Name.RESOURCE_TYPE, RequestPrimitive.Ty);

            if (RequestPrimitive.Pc != null && RequestPrimitive.Pc.Items.Count > 0)
            {
                string pcPayLoad = JsonConvert.SerializeObject(RequestPrimitive.Pc, Formatting.Indented, new PrimitiveContentJsonSerializer());
                MessageBody.Add(oneM2M.Name.PRIMITIVE_CONTENT, JObject.Parse(pcPayLoad));
            }

            checkRequestPrimitiveForMoreProps();
        }

        private void checkRequestPrimitiveForMoreProps()
        {
            var props = typeof(rqp).GetProperties();
            foreach (var prop in props)
            {
                var value = prop.GetValue(this.RequestPrimitive);
                if (value != null)
                {
                    switch (prop.Name.ToLower())
                    {
                        case oneM2M.Name.FILTER_CRITERIA:
                            {
                                if((value as filterCriteria).Fu != null)
                                    MessageBody.Add(oneM2M.Name.FILTER_USAGE, (value as filterCriteria).Fu);
                            }
                            break;
                        default:
                            break;
                    }
                }

            }
        }
    }
}
