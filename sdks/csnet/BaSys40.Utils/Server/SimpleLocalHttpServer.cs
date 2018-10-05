using NLog;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.IO;
using System.Net;
using System.Text;
using System.Threading;

namespace BaSys40.Utils.Server
{
    public class SimpleLocalHttpServer
    {
        public Uri Endpoint { get; }

        private Thread serverThread;
        private HttpListener listener;
        private Action<HttpListenerRequest> messageReception = null;
        private Action<HttpListenerResponse> messageResponse = null;

        private static Logger logger = LogManager.GetCurrentClassLogger();

        public SimpleLocalHttpServer(int port, string hostAddress = "127.0.0.1")
        {
            string uri = "http://" + hostAddress + ":" + port;
            Endpoint = new Uri(uri);
        }


        public void Start()
        {
            serverThread = new Thread(this.Listen);
            serverThread.Start();
        }

        public void Start(Action<HttpListenerRequest> receiveMessageMethod)
        {
            messageReception = receiveMessageMethod;
            Start();
        }

        public void Start(Action<HttpListenerRequest> receiveMessageMethod, Action<HttpListenerResponse> responseMessageMethod)
        {
            messageReception = receiveMessageMethod;
            messageResponse = responseMessageMethod;
            Start();
        }

        private void Listen()
        {
            try
            {
                listener = new HttpListener();
                listener.Prefixes.Add(Endpoint.OriginalString + "/");
                listener.Start();
            }
            catch (Exception e)
            {
                logger.Error(e, "Http-Listener could not be started: " + e.Message);
            }

            while (listener.IsListening)
            {
                try
                {
                    HttpListenerContext context = listener.GetContext();
                    if (messageReception != null)
                        messageReception.Invoke(context.Request);
                    if (messageResponse != null)
                        messageResponse.Invoke(context.Response);
                }
                catch (Exception e)
                {
                    logger.Warn(e, "Http-Listener Exception: " + e.Message);
                }
            }


        }

        public void Stop()
        {
            listener.Stop();
            serverThread.Abort();
        }

        public static List<string> GetLocalIpAddresses()
        {
            var host = System.Net.Dns.GetHostEntry(System.Net.Dns.GetHostName());
            List<string> ipAddresses = new List<string>();
            foreach (var ip in host.AddressList)
            {
                if (ip.AddressFamily == System.Net.Sockets.AddressFamily.InterNetwork)
                    ipAddresses.Add(ip.ToString());
            }
            return ipAddresses;
        }

        /// <summary>
        /// Formats the entire response, e.g. suitable for a MessageBox
        /// </summary>
        /// <param name="request"></param>
        /// <returns>Beautiful formatted response</returns>
        public static string GetCompleteResponseAsString(HttpListenerRequest request)
        {
            StringBuilder sb = new StringBuilder();
            sb.AppendLine("URI: " + request.Url.AbsoluteUri);
            sb.AppendLine("-----------HEADER-----------");

            NameValueCollection headers = request.Headers;
            for (int i = 0; i < headers.Count; i++)
            {
                string key = headers.GetKey(i);
                string value = headers.Get(i);
                sb.AppendLine(key + " = " + value);
            }

            sb.AppendLine("---------HEADER-END--------");

            sb.AppendLine("-----------BODY-----------");
            string body = new StreamReader(request.InputStream).ReadToEnd();
            sb.AppendLine(body);
            sb.AppendLine("---------BODY-END--------");

            return sb.ToString();
        }
    }
}
