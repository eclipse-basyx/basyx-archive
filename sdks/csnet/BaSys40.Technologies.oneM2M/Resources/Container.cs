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
    public class Container : IResource
    {
        public const int MaxNumberOfContentInstances = 10000;

        #region Mandatory and optional properties
        [AttributeProvider(typeof(IResource))]
        public string ResourceName { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "accessControlPolicyIDs", ShortName = "acpi")]
        public List<string> AccessControlPolicyIDs { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "expirationTime", ShortName = "et")]
        public string ExpirationTime { get; set; }
        [AttributeProvider(typeof(IResource))]
        public List<string> Labels { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "announceTo", ShortName = "at")]
        public string AnnounceTo { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "announcedAttribute", ShortName = "aa")]
        public string AnnouncedAttribute { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "dynamicAuthorizationConsultationIDs", ShortName = "daci")]
        public List<string> DynamicAuthorizationConsultationIDs { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.NotProvided, LongName = "creator", ShortName = "cr")]
        public string Creator { get; set; }

        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "maxNrOfInstances", ShortName = "mni")]
        public int? MaxNrOfInstances { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "maxByteSize", ShortName = "mbs")]
        public int? MaxByteSize { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "maxInstanceAge", ShortName = "mia")]
        public int? MaxInstanceAge { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "locationID", ShortName = "li")]
        public string LocationID { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "ontologyRef", ShortName = "or")]
        public string OntologyRef { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.NotProvided, LongName = "disableRetrieval", ShortName = "disr")]
        public bool? DisableRetrieval { get; set; } = false;

        [Attribute(CreateOptionality = RequestOptionality.NotProvided, UpdateOptionality = RequestOptionality.NotProvided, LongName = "currentNrOfInstances", ShortName = "cni")]
        public int? CurrentNrOfInstances { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.NotProvided, UpdateOptionality = RequestOptionality.NotProvided, LongName = "currentByteSize", ShortName = "cbs")]
        public int? CurrentByteSize { get; set; }

        #endregion

        #region Interface implementation
        public List<Type> SupportedChildResourceTypes => new List<Type>() { typeof(Subscription), typeof(ContentInstance), typeof(Container) };
        [AttributeProvider(typeof(IResource))]
        public oneM2M.ResourceType? ResourceType => oneM2M.ResourceType.Container; 

        [AttributeProvider(typeof(IResource))]
        public string ResourceID { get; set; }
        [AttributeProvider(typeof(IResource))]
        public string ParentID { get; set; }
        [AttributeProvider(typeof(IResource))]
        public string CreationTime { get; set; }
        [AttributeProvider(typeof(IResource))]
        public string LastModifiedTime { get; set; }


        #endregion
        public Container() { }
        public Container(string resourceName) : this(resourceName, null, null)
        { }
        public Container(string resourceName, string ontologyRef, List<string> labels) : this(resourceName, ontologyRef, labels, null)
        { }
        public Container(string resourceName, string ontologyRef, List<string> labels, int? maxNrOfInstances) : this(resourceName, null, null, labels, null,null, null, null, maxNrOfInstances, null, null, null, ontologyRef, null)
        { }

        public Container(cnt baseResource)
        {
            this.ConvertToConvenientResource(baseResource);
        }

        public Container(
            string resourceName,
            List<string> accessControlPolicyIDs,
            string expirationTime,
            List<string> labels,
            string announceTo,
            string announcedAttribute,
            List<string> dynamicAuthorizationConsultationIDs,
            string creator,
            int? maxNrOfInstances, int? maxByteSize, int? maxInstanceAge,
            string locationID, string ontologyRef, bool? disableRetrieval = false)
        {
            this.ResourceName = resourceName;
            this.AccessControlPolicyIDs = accessControlPolicyIDs;
            this.ExpirationTime = expirationTime;
            this.Labels = labels;
            this.AnnounceTo = announceTo;
            this.AnnouncedAttribute = announcedAttribute;
            this.DynamicAuthorizationConsultationIDs = dynamicAuthorizationConsultationIDs;
            this.Creator = creator;
            this.MaxNrOfInstances = maxNrOfInstances ?? MaxNumberOfContentInstances;
            this.MaxByteSize = maxByteSize;
            this.MaxInstanceAge = maxInstanceAge;
            this.LocationID = locationID;
            this.OntologyRef = ontologyRef;
            this.DisableRetrieval = disableRetrieval; 
        }

        #region Static operations
        public static Result<Response> Create(IClient client, Request request, string resourceName)
        { return Create(client, request, resourceName, null, null); }
        public static Result<Response> Create(IClient client, Request request, string resourceName, string ontologyRef, List<string> labels)
        { return Create(client, request, resourceName, ontologyRef, labels, null); }
        public static Result<Response> Create(IClient client, Request request, string resourceName, string ontologyRef, List<string> labels, int? maxNrOfInstances)
        {
            Container cnt = new Container(resourceName, ontologyRef, labels, maxNrOfInstances);
            return (Process(client, request, oneM2M.Operation.CREATE, cnt));
        }
        public static Result<Response> Retrieve(IClient client, Request request)
        { return (Process(client, request, oneM2M.Operation.RETRIEVE, null)); }
        public static Result<Response> Update(IClient client, Request request, string ontologyRef, List<string> labels, int maxNrOfInstances)
        {
            Container cnt = new Container(null, ontologyRef, labels, maxNrOfInstances);
            return (Process(client, request, oneM2M.Operation.UPDATE, cnt));
        }
        public static Result<Response> Delete(IClient client, Request request)
        { return (Process(client, request, oneM2M.Operation.DELETE, null)); }
        public static Result<Response> Process(IClient client, Request request, oneM2M.Operation operation, Container container)
        {
            object resource = Helper.ConvertToBaseResource(container);
            return CSEBase.CRUD(client, request, operation, oneM2M.ResourceType.Container, resource);
        }

       
        #endregion

    }
}
