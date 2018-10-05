using BaSys40.Utils.ResultHandling;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace BaSys40.Utils.Client
{ 
    public abstract class SimpleHttpClient
    {
        public static HttpClient HttpClient { get; }
        public static HttpClientHandler HttpClientHandler { get; }
        public static JsonSerializerSettings JsonSerializerSettings { get; }

        static SimpleHttpClient()
        {
            JsonSerializerSettings = new JsonSerializerSettings() { NullValueHandling = NullValueHandling.Ignore, Formatting = Formatting.Indented };

            HttpClientHandler = new HttpClientHandler() { MaxConnectionsPerServer = 100 };
            HttpClient = new HttpClient(HttpClientHandler);
        }

        protected virtual IResult<HttpResponseMessage> SendRequest(HttpRequestMessage message, int timeout)
        {
            try
            {
                var task = HttpClient.SendAsync(message);
                if (Task.WhenAny(task, Task.Delay(timeout)).Result == task)
                {
                    return new Result<HttpResponseMessage>(true, task.Result);
                }
                else
                {
                    return new Result<HttpResponseMessage>(false, new List<IMessage> { new Message(MessageType.Error, "Error while sending the request: timeout") });
                }
            }
            catch (Exception e)
            {
                return new Result<HttpResponseMessage>(e);
            }
        }

        protected virtual HttpRequestMessage CreateRequest(Uri uri, HttpMethod method)
        {
            return CreateRequest(uri, method, null);
        }

        protected virtual HttpRequestMessage CreateRequest(Uri uri, HttpMethod method, object content)
        {           
            HttpRequestMessage message = new HttpRequestMessage(method, uri);
            if (content != null)
                message.Content = new StringContent(JsonConvert.SerializeObject(content, JsonSerializerSettings), Encoding.Default, "application/json");

            return message;
        }

        protected virtual IResult EvaluateResponse(HttpResponseMessage response)
        {
            if (response != null)
            {
                var responseString = response.Content.ReadAsStringAsync().Result;
                if (response.IsSuccessStatusCode)
                    return new Result(true, new List<IMessage> { new Message(MessageType.Information, response.ReasonPhrase, ((int)response.StatusCode).ToString()) });
                else
                    return new Result(false, new List<IMessage> { new Message(MessageType.Error, response.ReasonPhrase + "| " + responseString, ((int)response.StatusCode).ToString()) });
            }
            return new Result(false, new List<IMessage> { new Message(MessageType.Error, "Response from host is null", null) });
        }

        protected virtual IResult<T> EvaluateResponse<T>(HttpResponseMessage response)
        {
            if (response != null)
            {
                var responseString = response.Content.ReadAsStringAsync().Result;
                if (response.IsSuccessStatusCode)
                {
                    try
                    {
                        var requestResult = JsonConvert.DeserializeObject<T>(responseString);
                        return new Result<T>(true, requestResult, new List<IMessage> { new Message(MessageType.Information, response.ReasonPhrase, ((int)response.StatusCode).ToString()) });
                    }
                    catch (Exception e)
                    {
                        return new Result<T>(false, new List<IMessage> { new Message(MessageType.Error, e.Message, e.HelpLink) });
                    }
                }
                else
                    return new Result<T>(false, new List<IMessage> { new Message(MessageType.Error, response.ReasonPhrase + "| " + responseString, ((int)response.StatusCode).ToString()) });
            }
            return new Result<T>(false, new List<IMessage> { new Message(MessageType.Error, "Response from host is null", null) });
        }
    }
}
