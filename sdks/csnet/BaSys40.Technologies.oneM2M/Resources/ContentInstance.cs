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
    public class ContentInstance : IResource
    {
        #region Mandatory and optional properties
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.NotProvided, LongName = "resourceName", ShortName = "rn")]
        public string ResourceName { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.NotProvided, LongName = "expirationTime", ShortName = "et")]
        public string ExpirationTime { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.NotProvided, LongName = "labels", ShortName = "lbl")]
        public List<string> Labels { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.NotProvided, LongName = "announceTo", ShortName = "at")]
        public string AnnounceTo { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.NotProvided, LongName = "announcedAttribute", ShortName = "aa")]
        public string AnnouncedAttribute { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.NotProvided, LongName = "dynamicAuthorizationConsultationIDs", ShortName = "daci")]
        public List<string> DynamicAuthorizationConsultationIDs { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.NotProvided, LongName = "creator", ShortName = "cr")]
        public string Creator { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.NotProvided, LongName = "contentInfo", ShortName = "cnf")]
        public string ContentInfo { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.NotProvided, UpdateOptionality = RequestOptionality.NotProvided, LongName = "contentSize", ShortName = "cs")]
        public string ContentSize { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.NotProvided, LongName = "contentRef", ShortName = "conr")]
        public contentRef ContentRef { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.NotProvided, LongName = "ontologyRef", ShortName = "or")]
        public string OntologyRef { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Mandatory, UpdateOptionality = RequestOptionality.NotProvided, LongName = "content", ShortName = "con")]
        public string Content { get; set; }

        #endregion

        public List<Type> SupportedChildResourceTypes { get; set; } = null;

        [AttributeProvider(typeof(IResource))]
        public oneM2M.ResourceType? ResourceType => oneM2M.ResourceType.ContentInstance;
        [AttributeProvider(typeof(IResource))]
        public string ResourceID { get; set; }
        [AttributeProvider(typeof(IResource))]
        public string ParentID { get; set; }
        [AttributeProvider(typeof(IResource))]
        public string CreationTime { get; set; }
        [AttributeProvider(typeof(IResource))]
        public string LastModifiedTime { get; set; }

        public ContentInstance(string resourceName, string content) : this(resourceName, null, null, null, null, null, null, null, null, null, content) { }
        public ContentInstance(string resourceName, string content, string ontologyRef) : this(resourceName, null, null, null, null, null, null, null, null, ontologyRef, content) { }
        public ContentInstance(string resourceName, string content, string ontologyRef, Dictionary<string, string> nameUriKeyValueDic) : this(resourceName, null, null, null, null, null, null, null, ConvertDictionaryToContentRef(nameUriKeyValueDic), ontologyRef, content) { }
        public ContentInstance(string resourceName, string content, string ontologyRef, List<string> labels) : this(resourceName, null, labels, null, null, null, null, null, null, ontologyRef, content) { }

        public static contentRef ConvertDictionaryToContentRef(Dictionary<string, string> nameUriKeyValueDic)
        {
            if (nameUriKeyValueDic != null)
            {
                contentRef conr = new contentRef();
                conr.Urir = new List<contentRefUrir>();
                foreach (var contentRef in nameUriKeyValueDic)
                {
                    contentRefUrir cru = new contentRefUrir() { Nm = contentRef.Key, Uri = contentRef.Value };
                    conr.Urir.Add(cru);
                }
                return conr;
            }
            return null;
        }
        public ContentInstance() { }
        public ContentInstance(cin baseResource)
        {
            this.ConvertToConvenientResource(baseResource);
        }

        public ContentInstance(
            string resourceName,
            string expirationTime,
            List<string> labels,
            string announceTo,
            string announcedAttribute,
            List<string> dynamicAuthorizationConsultationIDs,
            string creator,
            string contentInfo,
            contentRef contentRef,
            string ontologyRef,
            string content) 
        {
            this.ResourceName = resourceName;
            this.ExpirationTime = expirationTime;
            this.Labels = labels;
            this.AnnounceTo = announceTo;
            this.AnnouncedAttribute = announcedAttribute;
            this.DynamicAuthorizationConsultationIDs = dynamicAuthorizationConsultationIDs;
            this.Creator = creator;
            this.ContentInfo = contentInfo;
            this.ContentRef = contentRef;
            this.OntologyRef = ontologyRef;
            this.Content = content;
        }


        #region Static operations

        public static Result<Response> Create(IClient client, Request request, string resourceName, string content)
        { return (Create(client, request, resourceName, content, null, null)); }

        public static Result<Response> Create(IClient client, Request request, string resourceName, string content, string ontologyRef)
        { return (Create(client, request, resourceName, content, ontologyRef, null)); }

        public static Result<Response> Create(IClient client, Request request, string resourceName, string content, string ontologyRef, List<string> labels)
        {
            ContentInstance cin = new ContentInstance(resourceName, content, ontologyRef, labels);
            return (Process(client, request, oneM2M.Operation.CREATE, cin));
        }

        //According to oneM2M service layer protocol specification there is no update of content instances allowed
        public static Result<Response> Override(IClient client, Request request, string resourceName, string content, string ontologyRef, List<string> labels)
        {
            string tempPath = request.RequestPrimitive.To;
            request.AddPath(resourceName);
            var deleted = Delete(client, request);
            if (!deleted.Success && deleted.Entity.ResponseStatusCode != oneM2M.ResponseStatusCodes.NOT_FOUND)
                return deleted;

            request.SetResourcePath(tempPath);
            ContentInstance cin = new ContentInstance(resourceName, content, ontologyRef, labels);
            return (Process(client, request, oneM2M.Operation.CREATE, cin));
        }

        public static Result<Response> Retrieve(IClient client, Request request)
        { return (Process(client, request, oneM2M.Operation.RETRIEVE, null)); }

        public static Result<Response> Delete(IClient client, Request request)
        { return (Process(client, request, oneM2M.Operation.DELETE, null)); }

        public static Result<Response> Process(IClient client, Request request, oneM2M.Operation operation, ContentInstance contentInstance)
        {
            object resource = Helper.ConvertToBaseResource(contentInstance);
            return CSEBase.CRUD(client, request, operation, oneM2M.ResourceType.ContentInstance, resource);
        }

        #endregion
    }
}
