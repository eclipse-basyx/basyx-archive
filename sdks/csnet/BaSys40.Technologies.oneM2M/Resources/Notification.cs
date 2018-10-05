using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using oneM2MClient.Protocols;
using oneM2MClient.Utils;
using System;
using System.IO;
using System.Net;

namespace oneM2MClient.Resources
{
    public class Notification
    {
        [JsonProperty(PropertyName = "sur")]
        public string SubscriptionReference { get; private set; }

        [JsonProperty(PropertyName = "sud")]
        public bool? SubscriptionDeletion { get; private set; }

        [JsonProperty(PropertyName = "nev")]
        public notificationEvent NotificationEvent { get; private set; }

        public class notificationEvent
        {
            [JsonProperty(PropertyName = "rep")]
            public resource Representation { get; internal set; }

            [JsonProperty(PropertyName = "net")]
            public notificationEventType NotificationEventType { get; internal set; }
        }

        public static Notification ReadFrom(HttpListenerRequest obj)
        {
            if (obj != null)
                return Parse(new StreamReader(obj.InputStream).ReadToEnd());
            return null;
        }

        public static Notification ReadFrom(Stream stream)
        {
            if (stream != null)
                return Parse(new StreamReader(stream).ReadToEnd());
            return null;
        }

        public static Notification Parse(string jsonString)
        {
            if(!string.IsNullOrEmpty(jsonString))
            {
                if (jsonString.Contains("m2m:sgn"))
                    return oM2M_Parser(JObject.Parse(jsonString));
                else
                    return iotDM_Parser(JObject.Parse(jsonString));
            }
            return null;
        }
        private Notification()
        { }

        private static Notification oM2M_Parser(JObject notification)
        {
            try
            {
                Notification not = new Notification();
                //JProperty subscriptionSource = (JProperty)notification.;
                not.SubscriptionReference = notification.SelectToken("m2m:sgn.m2m:sur").Value<string>();
                not.SubscriptionDeletion = notification.SelectToken("m2m:sgn.m2m:sud").Value<bool>();
                //notificationEventType net = (notificationEventType)int.Parse(notification.SelectToken("nev.net").Value<string>());

                JToken jContent = notification.SelectToken("m2m:sgn.m2m:nev.m2m:rep").First;
                string contentTypeName = (jContent as JProperty).Name;

                if (contentTypeName.Contains(oneM2M.NAMESPACE + oneM2M.NAMESPACE_DELIMITER))
                    contentTypeName = contentTypeName.Split(char.Parse(oneM2M.NAMESPACE_DELIMITER))[1];

                Type t = Type.GetType("oneM2MClient.Protocols." + contentTypeName, false, true);
                object primContent = jContent.First.ToObject(t);
                if (primContent is resource)
                {
                    not.NotificationEvent = new notificationEvent() { Representation = primContent as resource };
                }
                else
                    throw new Exception("primitive content is not of type resource");

                return not;
            }
            catch
            {
                return null;
            }
        }

        private static Notification iotDM_Parser(JObject notification)
        {
            try
            {
                Notification not = new Notification();

                JProperty subscriptionSource = (JProperty)notification.First;
                not.SubscriptionReference = subscriptionSource.Value.ToString();

                notificationEventType net = (notificationEventType)int.Parse(notification.SelectToken("nev.net").Value<string>());

                JToken jContent = notification.SelectToken("nev.rep").First;
                string contentTypeName = (jContent as JProperty).Name;

                if (contentTypeName.Contains(oneM2M.NAMESPACE + oneM2M.NAMESPACE_DELIMITER))
                    contentTypeName = contentTypeName.Split(char.Parse(oneM2M.NAMESPACE_DELIMITER))[1];

                Type t = Type.GetType("oneM2MClient.Protocols." + contentTypeName, false, true);
                object primContent = jContent.First.ToObject(t);
                if (primContent is resource)
                {
                    not.NotificationEvent = new notificationEvent() { NotificationEventType = net, Representation = primContent as resource };
                }
                else
                    throw new Exception("primitive content is not of type resource");

                return not;
            }
            catch
            {
                return null;
            }
        }

        public bool TryGetResource<T>(out T resource) where T : IResource, new()
        {
            if (this.NotificationEvent.Representation != null)
            {
                resource = new T();
                resource.ConvertToConvenientResource(this.NotificationEvent.Representation);
                return true;
            }
            resource = default(T);
            return false;
        }
    }
}
