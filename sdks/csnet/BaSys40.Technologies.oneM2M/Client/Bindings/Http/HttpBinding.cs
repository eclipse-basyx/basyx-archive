using BaSys40.Utils.ResultHandling;
using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;

namespace oneM2MClient.Client.Bindings
{
    public class HttpBinding : IClient
    {
        private static HttpClient httpClient;
        private bool isAlive = false;

        public ClientFactory.Protocol Protocol => ClientFactory.Protocol.Http;

        public static void ConfigureHttpClient()
        {
            httpClient = new HttpClient(
                new HttpClientHandler
                {
                    MaxConnectionsPerServer = 100,                    
                    UseProxy = false
                });
        }

        public Result<Response> Send(Request request)
        {
            if (request == null)
                return new Result<Response>(new ArgumentNullException("request"));

            string rqi = request.GetHashCode().ToString();
            request.RequestIdentifier(rqi);

            HttpRequest requestHelper = new HttpRequest(request);
            requestHelper.Query.Remove(oneM2M.Name.RESOURCE_TYPE);

            string uri = requestHelper.RequestPath + GetQueryString(requestHelper.Query);

            HttpRequestMessage requestMessage = new HttpRequestMessage();
            requestMessage.RequestUri = new Uri(uri);
            requestMessage.Headers.Accept.Add(new MediaTypeWithQualityHeaderValue(requestHelper.AcceptMIME));

            AddHeader(requestMessage.Headers, requestHelper.Header);

            oneM2M.Operation op = (oneM2M.Operation)requestHelper.Method;
            switch (op)
            {
                case oneM2M.Operation.CREATE:
                    requestMessage.Method = HttpMethod.Post;
                    requestMessage.Content = new StringContent(requestHelper.Body);

                    var contentType = new MediaTypeHeaderValue(requestHelper.ContentMIME);
                    contentType.Parameters.Add(new NameValueHeaderValue(oneM2M.Name.RESOURCE_TYPE, request.RequestPrimitive.Ty.Value.ToString()));
                    requestMessage.Content.Headers.ContentType = contentType;
                    break;

                case oneM2M.Operation.RETRIEVE:
                    requestMessage.Method = HttpMethod.Get;
                    break;

                case oneM2M.Operation.UPDATE:
                    requestMessage.Method = HttpMethod.Put;
                    requestMessage.Content = new StringContent(requestHelper.Body, Encoding.Default, requestHelper.ContentMIME);
                    break;

                case oneM2M.Operation.DELETE:
                    requestMessage.Method = HttpMethod.Delete;
                    break;

                case oneM2M.Operation.NOTIFY:
                    requestMessage.Method = HttpMethod.Post;
                    break;
                default:
                    return new Result<Response>(new InvalidOperationException("Invalid Operation: " + op));
            }
            try
            {
                var task = Task.Run(() => httpClient.SendAsync(requestMessage));
                bool success = task.Wait(request.MillisecondsTimeout);
                if (success && task.Result != null)
                {
                    HttpResponse response = new HttpResponse(task.Result);
                    if (response.OriginalHttpResponseMessage.IsSuccessStatusCode)
                    {
                        if (response.RequestIdentifier == rqi)
                            return new Result<Response>(true, response);
                        else
                            return new Result<Response>(false, response, new Message(MessageType.Error, "Response-Id: '" + response.RequestIdentifier + "' does not match Request-Id: '" + rqi + "'"));
                    }
                    else
                    {
                        StringBuilder errorMsg = new StringBuilder();
                        errorMsg.Append("Http-Response: " + (int)response.OriginalHttpResponseMessage.StatusCode + " - " + response.OriginalHttpResponseMessage.ReasonPhrase + " || ");
                        errorMsg.Append("oneM2M-Response: ").Append(response.ResponseStatusCode + " - ").Append(response.ErrorMessage + " ").Append(response.ResponseBody);
                        return new Result<Response>(false, response, new Message(MessageType.Error, errorMsg.ToString(), ((int)response.OriginalHttpResponseMessage.StatusCode).ToString()));
                    }
                }
                else
                    return new Result<Response>(false, new Message(MessageType.Error, "Error getting response from host", ((int)HttpStatusCode.BadGateway).ToString()));
            }
            catch (Exception e)
            {
                string msg = e.Message + ((e.InnerException != null) ? " - InnerException: " + e.InnerException.Message : string.Empty);
                return new Result<Response>(false, new Message(MessageType.Error, msg, ((int)HttpStatusCode.InternalServerError).ToString()));
            }
        }

        protected void AddHeader(HttpRequestHeaders headers, Dictionary<string, List<string>> dic)
        {
            foreach (var entry in dic)
            {
                string key = HttpMapping.Header.map(entry.Key);
                string value = HttpRequest.ConcatQuery(entry.Value);
                headers.Add(key, value);
            }
        }

        private string GetQueryString(Dictionary<string, List<string>> dic)
        {
            if (dic != null && dic.Count > 0)
            {
                StringBuilder sb = new StringBuilder();
                sb.Append("?");
                int count = 1;
                foreach (var entry in dic)
                {
                    string key = entry.Key;
                    string value = HttpRequest.ConcatQuery(entry.Value);
                    sb.Append(key + "=" + value);
                    if (count != dic.Count)
                        sb.Append("&");
                    count++;
                }
                return sb.ToString();
            }
            else
                return string.Empty;
        }

        public void Start()
        {
            ConfigureHttpClient();   
            isAlive = true;
        }

        public void Stop()
        {
            httpClient.Dispose();
            isAlive = false;
        }

        public bool IsAlive
        {
            get
            {
                return isAlive;
            }
        }

        
    }
}
