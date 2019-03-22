using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;

namespace oneM2MClient.Client.Bindings
{
    public abstract class oneM2MConfig
    {
        public string CSEHost { get; protected set; }
        public int CSEPort { get; protected set; }
        public string CSEName { get; protected set; }
        public oneM2M.CseTypeId CSEType { get; protected set; }
        public string Scheme { get; protected set; }

        public const string DefaultCSEName = "InCSE1";
        public const oneM2M.CseTypeId DefaultCSEType = oneM2M.CseTypeId.IN_CSE;
        public const string DefaultCSEHost = "localhost";
        public const int DefaultCSEPort = 8181;
        public const string DefaultScheme = "http";

        private static HttpClient httpClient;

        public static async Task<HttpResponseMessage> Send(Uri url, HttpMethod method, String payload, String username, String password, Dictionary<string, string> headers)
        {
            try
            {
                HttpRequestMessage request = new HttpRequestMessage();
                request.RequestUri = url;
                request.Method = method;
                if (!string.IsNullOrEmpty(username) && !string.IsNullOrEmpty(password))
                {
                    String authString = username + ":" + password;
                    request.Headers.Add("Authorization", "Basic " + Convert.ToBase64String((new ASCIIEncoding()).GetBytes(authString)));
                }

                if (headers != null)
                {
                    foreach (var item in headers)
                    {
                        request.Headers.TryAddWithoutValidation(item.Key, item.Value);
                    }
                }

                if (!string.IsNullOrEmpty(payload))
                    request.Content = new StringContent(payload, Encoding.UTF8, HttpMapping.MIME.APPLICATION_JSON);

                if (httpClient == null)
                {
                    httpClient = new HttpClient();
                    httpClient.DefaultRequestHeaders.Accept.Clear();
                    httpClient.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue(HttpMapping.MIME.APPLICATION_JSON));
                }

                HttpResponseMessage response = await httpClient.SendAsync(request);

                return (response);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return (null);
            }
        }
    }
}
