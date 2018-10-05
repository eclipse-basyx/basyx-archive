using oneM2MClient.Client;
using oneM2MClient.Utils;
using oneM2MClient.Utils.ResultHandling;
using System;
using System.Collections.Generic;
using static oneM2MClient.oneM2M;

namespace oneM2MClient.Resources
{
    public static class CSEBase
    {
        public static Result<Response> Retrieve(IClient client, Request request)
        { return (Process(client, request, Operation.RETRIEVE)); }
        public static Result<Response> Discover(IClient client, Request request, FilterUsage? filter, ResultContent? resultContent,  List<string> labels = null)
        {
            if(filter.HasValue)
                request.FilterUsage(filter.Value);
            if(resultContent.HasValue)
                request.ResultContent(resultContent.Value);

            if (labels != null)
                request.AddFilterLabels(labels);

            return (Process(client, request, Operation.RETRIEVE));
        }

        public static Result<Response> Process(IClient client, Request request, Operation operation)
        {            
            return CRUD(client, request, operation, ResourceType.NoResource, null);
        }
         
        public static Result<Response> CRUD(IClient client, Request request, Operation operation, ResourceType resourceType, object primitiveContent)
        {
            Result<Response> response = null;
            try
            {
                if (Helper.IsNullObjects(client, request))
                    return new Result<Response>(false, new Message(MessageType.Error, "Client or request object is null", null));

                request.Operation(operation);
                request.ResourceType(resourceType);
                
                if(primitiveContent != null)
                    request.AddPrimitiveContent(primitiveContent);

                if(!client.IsAlive)
                    client.Start();

                response = client.Send(request);

                return response;
            }
            catch (Exception e)
            {
                return Helper.HandleResultWithException(response, e);
            }
            finally
            {
                request?.ClearRequest();
            }
        }
    }
}
