using oneM2MClient.Protocols;
using System.Linq;
using System.Net.Http;

namespace oneM2MClient.Client
{
    public class HttpResponse : Response
    {
        public HttpResponseMessage OriginalHttpResponseMessage { get; }
        public HttpResponse(oneM2M.ResponseStatusCodes responseStatusCode, string requestIdentifier, primitiveContent primitiveContent, string to, string from, oneM2M.Time originatingTimestamp, oneM2M.Time resultExpirationTimestamp, oneM2M.StdEventCats eventCategory, string responseBody)
            : base(responseStatusCode, requestIdentifier, primitiveContent, to, from, originatingTimestamp, resultExpirationTimestamp, eventCategory, responseBody) { }

        public HttpResponse(HttpResponseMessage respMessage)
        {
            if (respMessage == null)
                return;
            else
                OriginalHttpResponseMessage = respMessage;

            var headers = respMessage.Headers;
            foreach (var header in respMessage.Headers)
            {
                switch (header.Key)
                {
                    case "X-M2M-RSC":
                        ResponseStatusCode = (oneM2M.ResponseStatusCodes)int.Parse(header.Value.FirstOrDefault());
                        break;
                    case "X-M2M-RI":
                        RequestIdentifier = header.Value.FirstOrDefault();
                        break;
                    case "X-M2M-ORIGIN":
                        From = header.Value.FirstOrDefault();
                        break;
                    case "X-M2M-OT":
                        OriginatingTimestamp = new oneM2M.Time(header.Value.FirstOrDefault());
                        break;
                    case "X-M2M-RST":
                        ResultExpirationTimestamp = new oneM2M.Time(header.Value.FirstOrDefault());
                        break;
                    case "X-M2M-EC":
                        EventCategory = (oneM2M.StdEventCats)int.Parse(header.Value.FirstOrDefault());
                        break;
                    default:
                        continue;
                }
            }

            ResponseBody = respMessage.Content.ReadAsStringAsync().Result;

            if (!string.IsNullOrEmpty(ResponseBody) && (int)ResponseStatusCode < 4000)
            {
                PrimitiveContent = ExtractPrimitiveContent(ResponseBody);
            }
        }
    }

}

