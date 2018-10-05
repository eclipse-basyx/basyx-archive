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
    public class ApplicationEntity : IResource
    {
        public List<Type> SupportedChildResourceTypes => new List<Type>() { typeof(Subscription), typeof(Container) };

        [AttributeProvider(typeof(IResource))]
        public oneM2M.ResourceType? ResourceType => oneM2M.ResourceType.ApplicationEntity; 
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

        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "accessControlPolicyIDs", ShortName = "acpi")]
        public List<string> AccessControlPolicyIDs { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "expirationTime", ShortName = "et")]
        public string ExpirationTime { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "dynamicAuthorizationConsultationIDs", ShortName = "daci")]
        public List<string> DynamicAuthorizationConsultationIDs { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "ontologyRef", ShortName = "or")]
        public string OntologyRef { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "announceTo", ShortName = "at")]
        public string AnnounceTo { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "announcedAttribute", ShortName = "aa")]
        public string AnnouncedAttribute { get; set; }

        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "appName", ShortName = "apn")]
        public string AppName { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Mandatory, UpdateOptionality = RequestOptionality.NotProvided, LongName = "App-ID", ShortName = "api")]
        public string AppID { get; set; }

        [Attribute(CreateOptionality = RequestOptionality.NotProvided, UpdateOptionality = RequestOptionality.NotProvided, LongName = "AE-ID", ShortName = "aei")]
        public string AEID { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "pointOfAccess", ShortName = "poa")]
        public List<string> PointOfAccess { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "nodeLink", ShortName = "nl")]
        public string NodeLink { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Mandatory, UpdateOptionality = RequestOptionality.Optional, LongName = "requestReachability", ShortName = "rr")]
        public bool? RequestReachability { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "contentSerialization", ShortName = "csz")]
        public List<permittedMediaTypes> ContentSerialization { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "e2eSecInfo", ShortName = "esi")]
        public e2eSecInfo E2eSecInfo { get; set; }

        public ApplicationEntity() { }
        public ApplicationEntity(ae baseResource)
        {
            this.ConvertToConvenientResource(baseResource);
        }

        public ApplicationEntity(
            string resourceName,
            List<string> accessControlPolicyIDs,
            string expirationTime,
            List<string> labels,
            string announceTo,
            string announcedAttribute,
            List<string> dynamicAuthorizationConsultationIDs,
            string appName,
            string AppID,
            List<string> pointOfAccess,
            string ontologyRef,
            string nodeLink,
            bool? requestReachability,
            List<permittedMediaTypes> contentSerialization,
            e2eSecInfo e2eSecInfo)
        {
            this.ResourceName = resourceName;
            this.AccessControlPolicyIDs = accessControlPolicyIDs;
            this.ExpirationTime = expirationTime;
            this.Labels = labels;
            this.AnnounceTo = announceTo;
            this.AnnouncedAttribute = announcedAttribute;
            this.DynamicAuthorizationConsultationIDs = dynamicAuthorizationConsultationIDs;
            this.AppName = appName;
            this.AppID = AppID; 
            this.PointOfAccess = pointOfAccess;
            this.OntologyRef = ontologyRef;
            this.NodeLink = nodeLink;
            this.RequestReachability = requestReachability; 
            this.ContentSerialization = contentSerialization;
            this.E2eSecInfo = e2eSecInfo;
        }

        public ApplicationEntity(string resourceName, string AppID, bool? requestReachability) : this(resourceName, AppID, requestReachability, null, null, null) { }
        public ApplicationEntity(string resourceName, string AppID, bool? requestReachability, string appName, string ontologyRef, List<string> labels) : this(resourceName, null, null, labels, null, null, null, appName, AppID, null, ontologyRef, null, requestReachability, null, null) { }

        #region Static operations
        public static Result<Response> Create(IClient client, Request request, string resourceName, string AppID, bool requestReachability, string appName)
        { return (Create(client, request, resourceName, AppID, requestReachability, appName, null, null)); }

        public static Result<Response> Create(IClient client, Request request, string resourceName, string AppID, bool requestReachability, string appName, string ontologyRef, List<string> labels)
        {
            ApplicationEntity ae = new ApplicationEntity(resourceName, AppID, requestReachability, appName, ontologyRef, labels);
            return (Process(client, request, oneM2M.Operation.CREATE, ae));
        }

        public static Result<Response> Retrieve(IClient client, Request request)
        { return (Process(client, request, oneM2M.Operation.RETRIEVE, null)); }

        public static Result<Response> Update(IClient client, Request request, string appName, string ontologyRef, List<string> labels)
        {
            ApplicationEntity ae = new ApplicationEntity(null, null, null, appName, ontologyRef, labels);
            return (Process(client, request, oneM2M.Operation.UPDATE, ae));
        }

        public static Result<Response> Delete(IClient client, Request request)
        { return (Process(client, request, oneM2M.Operation.DELETE, null)); }

        public static Result<Response> Process(IClient client, Request request, oneM2M.Operation operation, ApplicationEntity ae)
        {
            object resource = Helper.ConvertToBaseResource(ae);
            return CSEBase.CRUD(client, request, operation, oneM2M.ResourceType.ApplicationEntity, resource);
        }
        #endregion
    }
}
