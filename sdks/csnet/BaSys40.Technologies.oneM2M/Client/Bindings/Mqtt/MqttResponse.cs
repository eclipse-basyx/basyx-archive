using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using oneM2MClient.Protocols;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using uPLibrary.Networking.M2Mqtt.Messages;

namespace oneM2MClient.Client
{
    public class MqttResponse : Response
    {
        public string ResponseTopic { get; private set; }

        private MqttResponse()
        { }
        public MqttResponse(oneM2M.ResponseStatusCodes responseStatusCode, string requestIdentifier, primitiveContent primitiveContent, string to, string from, oneM2M.Time originatingTimestamp, oneM2M.Time resultExpirationTimestamp, oneM2M.StdEventCats eventCategory, string responseBody)
            : base(responseStatusCode, requestIdentifier, primitiveContent, to, from, originatingTimestamp, resultExpirationTimestamp, eventCategory, responseBody) { }

        public MqttResponse(MqttMsgPublishEventArgs respMessage)
        {
            if (respMessage == null)
                return;

            ResponseBody = Encoding.UTF8.GetString(respMessage.Message);

            ResponseTopic = respMessage.Topic;
            string[] topicElements = ResponseTopic.Split('/');

            if(topicElements != null && topicElements.Length == 6)
            {
                From = topicElements[4];
                To = topicElements[3];
            }

            JObject responseJson;
            try
            {
                responseJson = JObject.Parse(ResponseBody);
            }
            catch { responseJson = null; }

            if (responseJson != null)
            {
                foreach (var child in responseJson.Children())
                {
                    if (child is JProperty)
                    {
                        JProperty jProp = child as JProperty;
                        switch (jProp.Name)
                        {
                            case oneM2M.Name.REQUEST_IDENTIFIER:
                                RequestIdentifier = jProp.Value.ToString();
                                break;
                            case oneM2M.Name.RESPONSE_STATUS_CODE:
                                ResponseStatusCode = (oneM2M.ResponseStatusCodes)int.Parse(jProp.Value.ToString());
                                break;
                            case oneM2M.Name.PRIMITIVE_CONTENT:
                                PrimitiveContent = ExtractPrimitiveContent(jProp);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }

        public MqttResponse Clone()
        {
            return new MqttResponse {
                From = this.From,
                To = this.To,
                ResponseBody = this.ResponseBody,
                ErrorMessage = this.ErrorMessage,
                RequestIdentifier = this.RequestIdentifier,
                ResponseStatusCode = this.ResponseStatusCode,
                EventCategory = this.EventCategory,
                OriginatingTimestamp = this.OriginatingTimestamp,
                PrimitiveContent = this.PrimitiveContent,
                ResultExpirationTimestamp = this.ResultExpirationTimestamp
            };
        }


    }
}
