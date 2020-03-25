/*******************************************************************************
* Copyright (c) 2020 Robert Bosch GmbH
* Author: Constantin Ziesche (constantin.ziesche@bosch.com)
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License 2.0 which is available at
* http://www.eclipse.org/legal/epl-2.0
*
* SPDX-License-Identifier: EPL-2.0
*******************************************************************************/
using BaSyx.Utils.ResultHandling;
using BaSyx.Utils.Settings.Sections;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace BaSyx.Utils.Client.Http
{
    public abstract class SimpleHttpClient
    {
        public HttpClient HttpClient { get; }
        public HttpClientHandler HttpClientHandler { get; }
        public JsonSerializerSettings JsonSerializerSettings { get; protected set; }

        protected SimpleHttpClient()
        {
            HttpClientHandler = new HttpClientHandler() { MaxConnectionsPerServer = 100, UseProxy = false };
            HttpClient = new HttpClient(HttpClientHandler);

            JsonSerializerSettings = new JsonSerializerSettings() { NullValueHandling = NullValueHandling.Ignore, Formatting = Formatting.Indented, DefaultValueHandling = DefaultValueHandling.Include };
        }

        protected virtual void LoadProxy(ProxyConfiguration proxyConfiguration)
        {
            if (proxyConfiguration == null)
                return;

            if (proxyConfiguration.UseProxy && !string.IsNullOrEmpty(proxyConfiguration.ProxyAddress))
            {
                HttpClientHandler.UseProxy = true;
                if (!string.IsNullOrEmpty(proxyConfiguration.UserName) && !string.IsNullOrEmpty(proxyConfiguration.Password))
                {
                    NetworkCredential credential;
                    if (!string.IsNullOrEmpty(proxyConfiguration.Domain))
                        credential = new NetworkCredential(proxyConfiguration.UserName, proxyConfiguration.Password, proxyConfiguration.Domain);
                    else
                        credential = new NetworkCredential(proxyConfiguration.UserName, proxyConfiguration.Password);

                    HttpClientHandler.Proxy = new WebProxy(proxyConfiguration.ProxyAddress, false, null, credential);
                }
                else
                    HttpClientHandler.Proxy = new WebProxy(proxyConfiguration.ProxyAddress);
            }
            else
                HttpClientHandler.UseProxy = false;
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

        protected virtual async Task<IResult<HttpResponseMessage>> SendRequestAsync(HttpRequestMessage message)
        {
            try
            {
                using(HttpResponseMessage response = await HttpClient.SendAsync(message).ConfigureAwait(false))
                    return new Result<HttpResponseMessage>(true, response);
            }
            catch (Exception e)
            {
                return new Result<HttpResponseMessage>(e);
            }
        }

        protected virtual HttpRequestMessage CreateRequest(Uri uri, HttpMethod method)
        {
            return new HttpRequestMessage(method, uri);
        }

        protected virtual HttpRequestMessage CreateRequest(Uri uri, HttpMethod method, HttpContent content)
        {           
            var message = CreateRequest(uri, method);
            if (content != null)
                message.Content = content;

            return message;
        }
        
        protected virtual HttpRequestMessage CreateJsonContentRequest(Uri uri, HttpMethod method, object content)
        {
            var message = CreateRequest(uri, method, () => 
            {
                var serialized = JsonConvert.SerializeObject(content, JsonSerializerSettings);
                return new StringContent(serialized, Encoding.UTF8, "application/json");
            });
            return message;
        }
        
        protected virtual HttpRequestMessage CreateRequest(Uri uri, HttpMethod method, Func<HttpContent> content)
        {
            var message = CreateRequest(uri, method);
            if (content != null)
                message.Content = content.Invoke();

            return message;
        }

        protected virtual IResult EvaluateResponse(IResult result, HttpResponseMessage response)
        {
            var messageList = new List<IMessage>();
            messageList.AddRange(result.Messages);

            if (response != null)
            {
                var responseString = response.Content.ReadAsStringAsync().Result;
                if (response.IsSuccessStatusCode)
                {
                    messageList.Add(new Message(MessageType.Information, response.ReasonPhrase, ((int)response.StatusCode).ToString()));
                    return new Result(true, messageList);
                }
                else
                {
                    messageList.Add(new Message(MessageType.Error, response.ReasonPhrase + " | " + responseString, ((int)response.StatusCode).ToString()));
                    return new Result(false, messageList);
                }
            }
            messageList.Add(new Message(MessageType.Error, "Evaluation of response failed - Response from host is null", null));
            return new Result(false, messageList);
        }

        protected virtual IResult<T> EvaluateResponse<T>(IResult result, HttpResponseMessage response)
        {
            var messageList = new List<IMessage>();
            messageList.AddRange(result.Messages);

            if (response != null)
            {
                var responseString = response.Content.ReadAsStringAsync().Result;
                if (response.IsSuccessStatusCode)
                {
                    try
                    {
                        responseString = CheckAndExtractResultContruct(responseString);
                        var requestResult = JsonConvert.DeserializeObject<T>(responseString, JsonSerializerSettings);

                        messageList.Add(new Message(MessageType.Information, response.ReasonPhrase, ((int)response.StatusCode).ToString()));
                        return new Result<T>(true, requestResult, messageList);
                    }
                    catch (Exception e)
                    {
                        messageList.Add(new Message(MessageType.Error, e.Message, e.HelpLink));
                        return new Result<T>(false, messageList);
                    }
                }
                else
                {
                    messageList.Add(new Message(MessageType.Error, response.ReasonPhrase + " | " + responseString, ((int)response.StatusCode).ToString()));
                    return new Result<T>(false, messageList);
                }
            }
            messageList.Add(new Message(MessageType.Error, "Evaluation of response failed - Response from host is null", null));
            return new Result<T>(false, messageList);
        }

        private string CheckAndExtractResultContruct(string responseString)
        {
            if (responseString == null)
                return null;

            try
            {
                JToken jToken = JToken.Parse(responseString);
                var jEntity = jToken.SelectToken("entity");
                if (jEntity != null)
                    return jEntity.ToString();
                else
                    return responseString;
            }
            catch
            {
                return responseString;
            }
        }
    }
}
