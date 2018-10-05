using oneM2MClient.Client;
using oneM2MClient.Resources;
using oneM2MClient.Utils.ResultHandling;

namespace oneM2MClient.Utils
{
    public static class CrudExtensions
    {
        public static Result<Response> Create(this IResource convenientResource, IClient client, Request request)
        {
            Result<Response> result = CSEBase.CRUD(client, request, oneM2M.Operation.CREATE, (oneM2M.ResourceType)convenientResource.ResourceType, Helper.ConvertToBaseResource(convenientResource));
            if(result.Entity != null && result.Entity.GetResource() != null)
                convenientResource.ConvertToConvenientResource(result.Entity.GetResource());
            return result;
        }
        public static Result<Response> Retrieve(this IResource convenientResource, IClient client, Request request)
        {
            if (!request.EndpointAddress.Contains(convenientResource.ResourceName))
                request.SetPath(request.EndpointAddress, convenientResource.ResourceName);

            Result<Response> result = CSEBase.CRUD(client, request, oneM2M.Operation.RETRIEVE, oneM2M.ResourceType.NoResource, Helper.ConvertToBaseResource(convenientResource));
            if (result.Entity != null && result.Entity.GetResource() != null)
                convenientResource.ConvertToConvenientResource(result.Entity.GetResource());
            return result;
        }
        public static Result<Response> Update(this IResource convenientResource, IClient client, Request request)
        {
            if (!request.EndpointAddress.Contains(convenientResource.ResourceName))
                request.SetPath(request.EndpointAddress, convenientResource.ResourceName);

            Result<Response> result = CSEBase.CRUD(client, request, oneM2M.Operation.UPDATE, oneM2M.ResourceType.NoResource, Helper.ConvertToBaseResource(convenientResource));
            if (result.Entity != null && result.Entity.GetResource() != null)
                convenientResource.ConvertToConvenientResource(result.Entity.GetResource());
            return result;
        }
        public static Result<Response> Delete(this IResource convenientResource, IClient client, Request request)
        {
            if (!request.EndpointAddress.Contains(convenientResource.ResourceName))
                request.SetPath(request.EndpointAddress, convenientResource.ResourceName);

            Result<Response> result = CSEBase.CRUD(client, request, oneM2M.Operation.DELETE, oneM2M.ResourceType.NoResource, null);
            if (result.Entity != null && result.Entity.GetResource() != null)
                convenientResource.ConvertToConvenientResource(result.Entity.GetResource());
            return result;
        }


    }
}
