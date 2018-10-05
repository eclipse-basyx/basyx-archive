using oneM2MClient.Protocols;
using System;
using System.Collections.Generic;

namespace oneM2MClient.Client
{
    public abstract class Request : IRequest
    {
        public rqp RequestPrimitive { get; set; } = new rqp();
        public string ContentMIME { get; set; } = "application/json";
        public string AcceptMIME { get; set; } =  "application/json";
        public int MillisecondsTimeout { get; set; } = 1000;
        public string EndpointAddress { get; set; } = string.Empty;
        public Uri RequestPath { get; set; }
        public string CSEName { get; set; } = string.Empty;

        public Request() { }

        public Request(string clientId, string endpointAddress, string cseName, string path) 
            : this(clientId, endpointAddress, cseName, oneM2M.Operation.NOOPERATION, oneM2M.ResourceType.NoResource, path)  { }

        public Request(string clientId, string endpointAddress, string cseName, oneM2M.Operation operation, oneM2M.ResourceType resourceType, string path)
        {
            if (!string.IsNullOrEmpty(clientId) && !string.IsNullOrEmpty(endpointAddress) && !string.IsNullOrEmpty(cseName))
            {
                RequestPrimitive.Fc = new filterCriteria();
                RequestPrimitive.Rt = new responseTypeInfo();
                RequestPrimitive.Pc = new primitiveContent();

                RequestPrimitive.Fr = clientId;

                path = path.Replace("//", "/");
                RequestPrimitive.To = string.Join("/", cseName, path);
                RequestPath = new Uri(string.Join("/", endpointAddress, RequestPrimitive.To));
                EndpointAddress = endpointAddress;
                CSEName = cseName;

                if (operation == oneM2M.Operation.NOOPERATION)
                    RequestPrimitive.Op = null;
                else
                    RequestPrimitive.Op = (int)operation;

                if (resourceType == oneM2M.ResourceType.NoResource)
                    RequestPrimitive.Ty = null;
                else
                    RequestPrimitive.Ty = (int)resourceType;
            }
            else
                return;
        }
        
        public Request ClearRequest()
        {
            string sRqi;
            if (Int32.TryParse(RequestPrimitive.Rqi, out int iRqi))
                sRqi = (iRqi++).ToString();
            else
                sRqi = RequestPrimitive.Rqi;

            RequestPrimitive = new rqp() { Fr = RequestPrimitive.Fr, To = RequestPrimitive.To, Rqi = sRqi };

            return this;
        }


        public Request Operation(oneM2M.Operation operation)
        {
            RequestPrimitive.Op = (int)operation;
            return this;
        }

        public Request To(string to)
        {
            RequestPrimitive.To = to;
            RequestPath = new Uri(string.Join("/", EndpointAddress, RequestPrimitive.To));
            return this;
        }

        public Request AddPath(params string[] pathElements)
        {
            string joinedPathElements = string.Join("/", pathElements);
            RequestPrimitive.To = string.Join("/", RequestPrimitive.To, joinedPathElements).Replace("//", "/");
            RequestPath = new Uri(string.Join("/", EndpointAddress, RequestPrimitive.To));
            return this;
        }

        public Request SetPath(params string[] pathElements)
        {
            string joinedPathElements = string.Join("/", pathElements).Replace(CSEName, string.Empty).Replace("in-cse", string.Empty).Replace("//", "/");
            RequestPrimitive.To = string.Join("/", CSEName, joinedPathElements).Replace("//", "/").Replace("//", "/");
            RequestPath = new Uri(string.Join("/", EndpointAddress, RequestPrimitive.To));
            return this;
        }

        public Request SetResourcePath(string resourceId)
        {
            RequestPrimitive.To = resourceId;
            RequestPath = new Uri(string.Join("/", EndpointAddress, RequestPrimitive.To));
            return this;
        }

        public Request SetRequestPath(Uri requestPath)
        {
            RequestPrimitive.To = requestPath.AbsolutePath;
            RequestPath = requestPath;
            return this;
        }

        public Request From(string from)
        {
            RequestPrimitive.Fr = from;
            return this;
        }

        public Request RequestIdentifier(string requestIdentifier)
        {
            RequestPrimitive.Rqi = requestIdentifier;
            return this;
        }

        public Request ResourceType(oneM2M.ResourceType resourceType)
        {
            RequestPrimitive.Ty = (int)resourceType;
            return this;
        }

        public Request AddPrimitiveContent(object obj)
        {
            RequestPrimitive.Pc.Items.Add(obj);
            return this;
        }

        public Request PrimitiveContent(primitiveContent primitiveContent)
        {
            RequestPrimitive.Pc = primitiveContent;
            return this;
        }

        public Request OriginatingTimestamp(oneM2M.Time originatingTimestamp)
        {
            RequestPrimitive.Ot = originatingTimestamp.ToString();
            return this;
        }

        public Request RequestExpirationTimestamp(oneM2M.Time requestExpirationTimestamp)
        {
            RequestPrimitive.Rqet = requestExpirationTimestamp.ToString();
            return this;
        }        
        public Request ResultExpirationTimestamp(oneM2M.Time resultExpirationTimestamp)
        {
            RequestPrimitive.Rset = resultExpirationTimestamp.ToString();
            return this;
        }       
        public Request OperationExecutionTime(oneM2M.Time operationExecutionTime)
        {
            RequestPrimitive.Oet = operationExecutionTime.ToString();
            return this;
        }       
        public Request ResponseTypeValue(oneM2M.ResponseType responseTypeValue)
        {
            RequestPrimitive.Rt.Rtv = (int)responseTypeValue;
            return this;
        }
     
        public Request AddNotificationURI(string notificationURI)
        {
            RequestPrimitive.Rt.Nu.Add(notificationURI);
            return this;
        }
        public Request ResultPersistence(oneM2M.Time resultPersistence)
        {
            RequestPrimitive.Rp = resultPersistence.ToString();
            return this;
        }

        public Request ResultContent(oneM2M.ResultContent resultContent)
        {
            RequestPrimitive.Rcn = (int)resultContent;
            return this;
        }

        public Request EventCategory(oneM2M.StdEventCats eventCategory)
        {
            RequestPrimitive.Ec = eventCategory.ToString();
            return this;
        }

        public Request DeliveryAggregation(bool deliveryAggregation)
        {
            RequestPrimitive.Da = deliveryAggregation;
            return this;
        }

        public Request GroupRequestIdentifier(String groupRequestIdentifier)
        {
            RequestPrimitive.Gid = groupRequestIdentifier;
            return this;
        }

        public Request FilterCreatedBefore(oneM2M.Time createdBefore)
        {
            RequestPrimitive.Fc.Crb = createdBefore.ToString();
            return this;
        }

        public Request FilterCreatedAfter(oneM2M.Time createdAfter)
        {
            RequestPrimitive.Fc.Cra = createdAfter.ToString();
            return this;
        }
        public Request FilterModifiedSince(oneM2M.Time modifiedSince)
        {
            RequestPrimitive.Fc.Ms = modifiedSince.ToString();
            return this;
        }

        public Request FilterUnmodifiedSince(oneM2M.Time unmodifiedSince)
        {
            RequestPrimitive.Fc.Us = unmodifiedSince.ToString();
            return this;
        }

        public Request FilterStateTagSmaller(int stateTagSmaller)
        {
            RequestPrimitive.Fc.Sts = stateTagSmaller;
            return this;
        }

  
        public Request FilterStateTagBigger(int stateTagBigger)
        {
            RequestPrimitive.Fc.Stb = stateTagBigger;
            return this;
        }

        public Request FilterExpireBefore(oneM2M.Time expireBefore)
        {
            RequestPrimitive.Fc.Exb = expireBefore.ToString();
            return this;
        }

        public Request FilterExpireAfter(oneM2M.Time expireAfter)
        {
            RequestPrimitive.Fc.Exa = expireAfter.ToString();
            return this;
        }
   
        public Request AddFilterLabels(List<string> labels)
        {
            RequestPrimitive.Fc.Lbl = labels;
            return this;
        }
        
        public Request FilterResourceType(oneM2M.ResourceType resourceType)
        {
            RequestPrimitive.Fc.Ty = resourceType.ToString();
            return this;
        }

        public Request FilterSizeAbove(int sizeAbove)
        {
            RequestPrimitive.Fc.Sza = sizeAbove;
            return this;
        }
        public Request FilterSizeBelow(int sizeBelow)
        {
            RequestPrimitive.Fc.Szb = sizeBelow;
            return this;
        }

        public Request AddFilterContentType(string contentType)
        {
            RequestPrimitive.Fc.Cty.Add(contentType);
            return this;
        }

        public Request AddFilterAttribute(string name, object value)
        {
            attribute attribute = new attribute();
            attribute.Nm = name;
            attribute.Val = value;
            RequestPrimitive.Fc.Atr.Add(attribute);
            return this;
        }
        public Request FilterUsage(oneM2M.FilterUsage filterUsage)
        {
            RequestPrimitive.Fc.Fu = (int)filterUsage;
            return this;
        }

        public Request FilterLimit(int limit)
        {
            RequestPrimitive.Fc.Lim = limit;
            return this;
        }

        public Request DiscoveryResultType(oneM2M.DiscResType discoveryResultType)
        {
            RequestPrimitive.Drt = (int)discoveryResultType;
            return this;
        }
    }
}
