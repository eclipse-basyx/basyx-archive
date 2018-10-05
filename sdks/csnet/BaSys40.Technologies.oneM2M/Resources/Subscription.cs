using oneM2MClient.Client;
using oneM2MClient.Protocols;
using oneM2MClient.Utils;
using oneM2MClient.Utils.ResultHandling;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using static oneM2MClient.Resources.AttributeAttribute;

namespace oneM2MClient.Resources
{
    public class Subscription : IResource
    {
        [AttributeProvider(typeof(IResource))]
        public oneM2M.ResourceType? ResourceType => oneM2M.ResourceType.Subscription;
        [AttributeProvider(typeof(IResource))]
        public string ResourceName { get; set; }
        [AttributeProvider(typeof(IResource))]
        public string ResourceID { get; set; }
        [AttributeProvider(typeof(IResource))]
        public string ParentID { get; set; }
        [AttributeProvider(typeof(IResource))]
        public string CreationTime { get; set; }
        [AttributeProvider(typeof(IResource))]
        public string LastModifiedTime { get; set; }
        [AttributeProvider(typeof(IResource))]
        public List<string> Labels { get; set; }

        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "dynamicAuthorizationConsultationIDs", ShortName = "daci")]
        public List<string> DynamicAuthorizationConsultationIDs { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "creator", ShortName = "cr")]
        public string Creator { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "accessControlPolicyIDs", ShortName = "acpi")]
        public List<string> AccessControlPolicyIDs { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "expirationTime", ShortName = "et")]
        public string ExpirationTime { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "eventNotificationCriteria", ShortName = "enc")]
        public eventNotificationCriteria EventNotificationCriteria { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "expirationCounter", ShortName = "exc")]
        public int? ExpirationCounter { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Mandatory, UpdateOptionality = RequestOptionality.Optional, LongName = "notificationURI", ShortName = "nu")]
        public List<string> NotificationURI { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "groupID", ShortName = "gpi")]
        public string GroupID { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "notificationForwardingURI", ShortName = "nfu")]
        public string NotificationForwardingURI { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "batchNotify", ShortName = "bn")]
        public batchNotify BatchNotify { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "rateLimit", ShortName = "rl")]
        public rateLimit RateLimit { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.NotProvided, LongName = "preSubscriptionNotify", ShortName = "psn")]
        public int? PreSubscriptionNotify { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "pendingNotification", ShortName = "pn")]
        public pendingNotification? PendingNotification { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "notificationStoragePriority", ShortName = "nsp")]
        public int? NotificationStoragePriority { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "latestNotify", ShortName = "ln")]
        public bool? LatestNotify { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "notificationContentType", ShortName = "nct")]
        public notificationContentType? NotificationContentType { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "notificationEventCat", ShortName = "nec")]
        public stdEventCats? NotificationEventCat { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.NotProvided, LongName = "subscriberURI", ShortName = "su")]
        public string SubscriberURI { get; set; }


        public List<Type> SupportedChildResourceTypes { get; set; } = null;

        public Subscription(string resourceName, List<string> notificationURI, List<notificationEventType> notificationEventTypes) : this(resourceName, null, null, null, null, null, ConvertToEventNotificationCriteria(notificationEventTypes), null, notificationURI, null, null, null, null, null, null, null, null, null, null, null) { }

        public static eventNotificationCriteria ConvertToEventNotificationCriteria(List<notificationEventType> notificationEventTypes)
        {
            if (notificationEventTypes != null)
            {
                eventNotificationCriteria eventNotificationCriteria = new eventNotificationCriteria();
                eventNotificationCriteria.Net = notificationEventTypes.ConvertAll(c => (int)c);
                return eventNotificationCriteria;
            }
            return null;
        }

        public Subscription() { }
        public Subscription(sub baseResource)
        {
            this.ConvertToConvenientResource(baseResource);
        }

        public Subscription(
           string resourceName,
           List<string> accessControlPolicyIDs,
           string expirationTime,
           List<string> labels,
           string creator,
           List<string> dynamicAuthorizationConsultationIDs,

           eventNotificationCriteria eventNotificationCriteria,
           int? expirationCounter,
           List<string> notificationURI, //Mandatory
           string groupID,
           string notificationForwardingURI,
           batchNotify batchNotify,
           rateLimit rateLimit,
           int? preSubscriptionNotify,
           pendingNotification? pendingNotification,
           int? notificationStoragePriority,
           bool? latestNotify,
           notificationContentType? notificationContentType,
           stdEventCats? notificationEventCat,
           string subscriberURI)
        {
            this.ResourceName = resourceName;
            this.AccessControlPolicyIDs = accessControlPolicyIDs;
            this.ExpirationTime = expirationTime;
            this.Labels = labels;
            this.Creator = creator;
            this.DynamicAuthorizationConsultationIDs = dynamicAuthorizationConsultationIDs;

            this.EventNotificationCriteria = eventNotificationCriteria;
            this.ExpirationCounter = expirationCounter;
            this.NotificationURI = notificationURI;
            this.GroupID = groupID;
            this.NotificationForwardingURI = notificationForwardingURI;
            this.BatchNotify = batchNotify;
            this.RateLimit = rateLimit;
            this.PreSubscriptionNotify = preSubscriptionNotify;
            this.PendingNotification = pendingNotification;
            this.NotificationStoragePriority = notificationStoragePriority;
            this.LatestNotify = latestNotify;
            this.NotificationContentType = notificationContentType;
            this.NotificationEventCat = notificationEventCat;
            this.SubscriberURI = subscriberURI;
        }

        #region Static operations


        public static Result<Response> Create(IClient client, Request request, string resourceName, List<string> notificationURI, List<notificationEventType> notificationEventTypes)
        {
            eventNotificationCriteria eventNotificationCriteria = new eventNotificationCriteria();
            eventNotificationCriteria.Net = notificationEventTypes.ConvertAll(c => (int)c);
            Subscription subscription = new Subscription(resourceName, notificationURI, notificationEventTypes);
            return (Process(client, request, oneM2M.Operation.CREATE, subscription));
        }

        public static Result<Response> Retrieve(IClient client, Request request)
        { return (Process(client, request, oneM2M.Operation.RETRIEVE, null)); }

        public static Result<Response> Update(IClient client, Request request, List<string> notificationURI, List<notificationEventType> notificationEventTypes)
        {
            eventNotificationCriteria eventNotificationCriteria = new eventNotificationCriteria();
            eventNotificationCriteria.Net = notificationEventTypes.ConvertAll(c => (int)c);
            Subscription subscription = new Subscription(null, notificationURI, notificationEventTypes);
            return (Process(client, request, oneM2M.Operation.CREATE, subscription));
        }

        public static Result<Response> Delete(IClient client, Request request)
        { return (Process(client, request, oneM2M.Operation.DELETE, null)); }

        public static Result<Response> Process(IClient client, Request request, oneM2M.Operation operation, Subscription subscription)
        {
            object resource = Helper.ConvertToBaseResource(subscription);
            return CSEBase.CRUD(client, request, operation, oneM2M.ResourceType.Subscription, resource);
        }
        #endregion  
    }
}
