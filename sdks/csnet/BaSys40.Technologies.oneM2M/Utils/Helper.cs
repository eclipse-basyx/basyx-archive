using oneM2MClient.Client;
using oneM2MClient.Resources;
using oneM2MClient.Protocols;
using oneM2MClient.Utils.ResultHandling;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;

namespace oneM2MClient.Utils
{
    public static class Helper
    {
        public static Uri ConcatUrl(string Scheme, string Host, int? Port, params string[] path)
        {
            if (!string.IsNullOrEmpty(Host))
            {
                StringBuilder uriBuilder = new StringBuilder();

                if (!string.IsNullOrEmpty(Scheme))
                    uriBuilder.Append(Scheme).Append("://");

                uriBuilder.Append(Host);

                if (Port.HasValue)
                    uriBuilder.Append(":").Append(Port.Value);

                if (path != null)
                {
                    string joinedPath = string.Join("/", path);
                    uriBuilder.Append("/").Append(joinedPath);
                }
                return new Uri(uriBuilder.ToString());
            }
            return null;
        }


        /// <summary>
        /// Returns the urils of the resource and its substructure to be discovered
        /// </summary>
        /// <param name="client"></param>
        /// <param name="request"></param>
        /// <param name="labels"></param>
        /// <param name="additionalPath"></param>
        /// <returns>List with urils of the resource and its substructure</returns>
        public static Result<Response> DiscoverResource(IClient client, Request request, out List<string> resourceLinks, params string[] additionalPath)
        {
            if (additionalPath != null && additionalPath.Length > 0)
                request.AddPath(additionalPath);
            var contResp = CSEBase.Discover(client, request, oneM2M.FilterUsage.DISCOVERY_CRITERIA, null);
            if (contResp.Success && contResp.Entity != null)
            {
                if (contResp.Entity.Uril.Count > 0)
                    resourceLinks = contResp.Entity.Uril;
                else if (contResp.Entity.PrimitiveContent?.Items?.Count > 0)
                {
                    resourceLinks = new List<string>();
                    foreach (var item in contResp.Entity.PrimitiveContent.Items)
                    {
                        resourceLinks.Add((item as resource).Ri);
                    }
                    if (resourceLinks.Count == 0)
                        resourceLinks = null;
                }
                else
                    resourceLinks = null;
                return contResp;
            }
            resourceLinks = null;
            return contResp;
        }
     

        #region Read-Functions (Always return real resources)
        /// <summary>
        /// Reads resource by path
        /// </summary>
        /// <param name="client"></param>
        /// <param name="request"></param>
        /// <param name="path"></param>
        /// <returns></returns>
        public static resource ReadResourceByPath(IClient client, Request request, params string[] path)
        {
            if (path != null && path.Length > 0)
                request.SetPath(path);

            return ReadResource(client, request);
        }

        /// <summary>
        /// Reads resource by resource id
        /// </summary>
        /// <param name="client"></param>
        /// <param name="request"></param>
        /// <param name="resourceId"></param>
        /// <returns></returns>
        public static resource ReadResourceById(IClient client, Request request, string resourceId)
        {
            if (string.IsNullOrEmpty(resourceId))
                return null;

            if(resourceId.Contains("/"))
            {
                resourceId = resourceId.Split('/')[2];
            }
            request.SetResourcePath(resourceId);
            return ReadResource(client, request);
        }

        public static resource ReadResource(IClient client, Request request)
        {
            Result<Response> contResp = CSEBase.Retrieve(client, request);
            if (contResp.Success && contResp.Entity != null)
            {
                if (contResp.Entity.TryGetResource(out resource res))
                    return res;
            }
            return null;
        }

        /// <summary>
        /// Reads resources in oneM2M tree based on labels
        /// </summary>
        /// <param name="client"></param>
        /// <param name="request"></param>
        /// <param name="labels"></param>
        /// <returns>List of resources</returns>
        public static Result<Response> ReadResourcesByLabels(IClient client, Request request, List<string> labels, out List<resource> resources)
        {
            var contResp = CSEBase.Discover(client, request, oneM2M.FilterUsage.DISCOVERY_CRITERIA, null, labels);
            if (contResp.Success && contResp.Entity != null)
            {
                resources = new List<resource>();
                if (contResp.Entity.Uril.Count > 0)
                    foreach (var uril in contResp.Entity.Uril)
                    {
                        var resource = ReadResourceByPath(client, request, uril);
                        if (resource != null)
                            resources.Add(resource);
                    }
                if (contResp.Entity.PrimitiveContent?.Items.Count > 0)
                    foreach (var item in contResp.Entity.PrimitiveContent.Items)
                    {
                        if (item != null)
                            resources.Add((resource)item);
                    }
                return contResp;
            }
            resources = null;
            return contResp;
        }

        public static Result<Response> ReadLatestAddedResource(IClient client, Request request, out resource resource, params string[] additionalPath)
        {
            if (additionalPath != null && additionalPath.Length > 0)
                request.AddPath(additionalPath);

            request.AddPath("la");

            var contResp = CSEBase.Retrieve(client, request);
            if (contResp.Success && contResp.Entity != null && contResp.Entity.PrimitiveContent != null)
            {
                var items = contResp.Entity.PrimitiveContent.Items;
                var result = items?.FirstOrDefault();
                if (result != null)
                    resource = result as resource;
                else
                    resource = null;
            }
            else
                resource = null;

            return contResp;
        }
        #endregion

        #region Tree-Functions (Always return an ObjectTree)
        /// <summary>
        /// Returns an object tree with fully requested resources by resource id
        /// </summary>
        /// <param name="client"></param>
        /// <param name="request"></param>
        /// <param name="resourceId"></param>
        /// <returns></returns>
        public static Result<Response> ReadResourceTreeById(IClient client, Request request, string resourceId, out ObjectTreeBuilder tree)
        {
            request.SetResourcePath(resourceId);
            return ReadResourceTree(client, request, out tree, null);
        }

        /// <summary>
        /// Return an object tree with the full requested resources
        /// </summary>
        /// <param name="client"></param>
        /// <param name="request"></param>
        /// <param name="additionalPath"></param>
        /// <returns>Object tree with full resources</returns>
        public static Result<Response> ReadResourceTree(IClient client, Request request, out ObjectTreeBuilder tree, params string[] additionalPath)
        {
            if (additionalPath != null && additionalPath.Length > 0)
                request.AddPath(additionalPath);
            var result = DiscoverResource(client, request, out List<string> resourceReferences, additionalPath);
            if (result.Success && resourceReferences != null)
            {
                List<resource> resources = new List<resource>();
                foreach (var resourceReference in resourceReferences)
                {
                    resource res = null;
                    if (IsPath(resourceReference))
                        res = ReadResourceByPath(client, request, resourceReference);
                    else if (IsId(resourceReference))
                        res = ReadResourceById(client, request, resourceReference);

                    if (res != null)
                        resources.Add(res);
                }
                if (resources.Count > 0)
                    tree = GetResourceObjectTree(resources);
                else
                    tree = null;

                return result;
            }
            tree = null;
            return result;
        }

        

        public static void ListObjectInTree(IClient client, Request request, string objName, object obj, params string[] path)
        {
            if (path != null && path.Length > 0)
                request.SetPath(path);

            Container.Create(client, request, objName);

            var newPath = path.ToList();
            newPath.Add(objName);
            request.ClearRequest().SetPath(newPath.ToArray());

            foreach (PropertyInfo property in obj.GetType().GetProperties())
            {
                string name = property.Name;
                object value = property.GetValue(obj, null);
                string content = value == null ? "NULL" : value.ToString();
                ContentInstance.Create(client, request, name, content);
                request.ClearRequest();
            }
        }
        /// <summary>
        /// Returns a resource object tree structure based on a flat list of resources as input. This is done by comparing ParentId with ResourceId.
        /// </summary>
        /// <param name="items">A flat list of resources</param>
        /// <returns>Object tree with resources</returns>
        public static ObjectTreeBuilder GetResourceObjectTree(List<resource> items)
        {
            if (items != null && items.Count > 0)
            {
                resource firstItem = items[0];
                ObjectTreeBuilder root = new ObjectTreeBuilder(firstItem.Rn, firstItem);
                //ToDo: Remove cse names
                var childrenOfRoot = items.FindAll(c => c.Pi.Replace("/","").Replace("InCSE1", "").Replace("in-cse", "") == firstItem.Ri.Replace("/","").Replace("InCSE1", "").Replace("in-cse",""));
                foreach (var child in childrenOfRoot)
                {
                    int index = items.IndexOf(child);
                    ObjectTreeBuilder treeChild = GetResourceObjectTree(items.GetRange(index, items.Count - index));
                    root.AddChild(treeChild);
                }
                return root;
            }
            else
                return null;
        }

        /// <summary>
        /// Writes the entire tree as container structure in the oneM2M hierarchy tree based on the name of the input tree elements
        /// </summary>
        /// <param name="client">oneM2M-Client</param>
        /// <param name="request">Request-Object</param>
        /// <param name="tree">Arbitrary tree with named elements</param>
        /// <param name="path">Start path in the oneM2M hierarchy where the container structure is added to</param>
        public static void WriteTree(IClient client, Request request, ObjectTreeBuilder tree, params string[] path)
        {
            if (tree != null)
            {
                if (path != null && path.Length > 0)
                    request.SetPath(path);

                Container.Create(client, request, tree.Name);
                request.ClearRequest();
                if (tree.HasChildren())
                {
                    foreach (var child in tree.Children)
                    {
                        var newPath = path.ToList();
                        newPath.Add(tree.Name);

                        WriteTree(client, request, child, newPath.ToArray());
                    }
                }
            }
        }

        public static void WriteResourceTree(IClient client, Request request, ObjectTreeBuilder tree, params string[] path)
        {
            if (tree != null)
            {
                if (path != null && path.Length > 0)
                    request.SetPath(path);

                var res = tree.Value.FirstOrDefault();
                if (res != null)
                {
                    CSEBase.CRUD(client, request, oneM2M.Operation.CREATE, GetResourceType(res), res);
                    request.ClearRequest();
                }
                if (res != null && tree.HasChildren())
                {
                    foreach (var child in tree.Children)
                    {
                        var newPath = path.ToList();
                        newPath.Add(((resource)res).Rn);

                        WriteResourceTree(client, request, child, newPath.ToArray());
                    }
                }
            }
        }
        #endregion

        #region Resource Handling (Converter, Instantiator)
        public static object ConvertToBaseResource(object convenientResource)
        {
            if (convenientResource == null)
                return null;

            string conResTypeName = convenientResource.GetType().Name.LowercaseFirst();
            string shortname = oneM2M.Name.LongToShort(conResTypeName).ToLower();
            Type baseResourceType = Type.GetType(oneM2M.PRIMITIVE_PACKAGE + shortname, true, true);
            object baseResource = Activator.CreateInstance(baseResourceType);

            PropertyInfo[] convResField = convenientResource.GetType().GetProperties(BindingFlags.Instance | BindingFlags.Public);

            for (int i = 0; i < convResField.Length; i++)
            {
                string longName = LowercaseFirst(convResField[i].Name);
                string shortName = UppercaseFirst(oneM2M.Name.LongToShort(longName));
                
                object value = convResField[i].GetValue(convenientResource);

                if (value != null && !string.IsNullOrEmpty(shortName) && shortName != "Ty")
                {
                    PropertyInfo propInfo = baseResource.GetType().GetProperty(shortName, BindingFlags.Instance | BindingFlags.Public);
                    if (propInfo != null)
                    {
                        if(value.GetType() != propInfo.PropertyType)
                        {
                            Type t = Nullable.GetUnderlyingType(propInfo.PropertyType) ?? propInfo.PropertyType;
                            try { value = Convert.ChangeType(value, t); } catch { value = null; }
                        }
                        var setMethod = propInfo.GetSetMethod();
                        if(setMethod != null)
                            propInfo.SetValue(baseResource, value, null);
                    }
                }
            }
            return baseResource;
        }

        public static void ConvertToConvenientResource(this IResource convenientResource, object resource)
        {
            PropertyInfo[] resField = resource.GetType().GetProperties(BindingFlags.Instance | BindingFlags.Public);

            for (int i = 0; i < resField.Length; i++)
            {
                string shortName = resField[i].Name.ToLower();
                string longName = oneM2M.Name.ShortTolong(shortName).UppercaseFirst();                

                object value = resField[i].GetValue(resource);

                if (value != null && !string.IsNullOrEmpty(longName))
                {
                    PropertyInfo propInfo = convenientResource.GetType().GetProperty(longName, BindingFlags.Instance | BindingFlags.Public);
                    if (propInfo != null)
                    {
                        if (value is ICollection &&  (value as ICollection).Count == 0)
                            continue;

                        if (value.GetType() != propInfo.PropertyType)
                        {
                            Type t = Nullable.GetUnderlyingType(propInfo.PropertyType) ?? propInfo.PropertyType;
                            if (t.IsEnum)
                                value = Enum.Parse(t, value.ToString());
                            else
                                value = Convert.ChangeType(value, t);
                        }
                        var setMethod = propInfo.GetSetMethod();
                        if (setMethod != null)
                            propInfo.SetValue(convenientResource, value, null);
                    }
                }
            }
        }

        public static string UppercaseFirst(this string s)
        {
            if (string.IsNullOrEmpty(s))
            {
                return string.Empty;
            }
            return char.ToUpper(s[0]) + s.Substring(1);
        }

        public static string LowercaseFirst(this string s)
        {
            if (string.IsNullOrEmpty(s))
            {
                return string.Empty;
            }
            return char.ToLower(s[0]) + s.Substring(1);
        }

        public static object InstantiateResource(MethodBase m, int parameterIndex, Type t, params object[] args)
        {
            if (IsNullObjects(m, t))
                return null;

            ParameterInfo[] parameters = m.GetParameters();
            object resource = Activator.CreateInstance(t);

            for (int i = 0; i < parameters.Length - parameterIndex; i++)
            {
                string longName = parameters[i + parameterIndex].Name;
                string shortName = UppercaseFirst(oneM2M.Name.LongToShort(longName));

                object value = args[i];
              
                if (!string.IsNullOrEmpty(shortName))
                {
                    PropertyInfo propInfo = resource.GetType().GetProperty(shortName, BindingFlags.Instance | BindingFlags.Public);
                    if (propInfo != null)
                    {
                        propInfo.SetValue(resource, value, null);
                    }
                }
                else
                    throw new KeyNotFoundException(shortName + " is missing in the longToShort-Dictionary - longName: " + longName);
            }
            return resource;
        }

        public static cin GetLatestResource(ObjectTreeBuilder objectTreeBuilder)
        {
            if(objectTreeBuilder != null && objectTreeBuilder.HasChildren())
            {
                List<cin> cins = objectTreeBuilder.Children.Select(c => c.GetValue<cin>()).ToList();

                var latestCreationTime = cins.Where(a => a != null)?.Max(c => oneM2M.Time.Parse(c.Ct));
                var latestResource = cins.Where(a => a != null)?.FirstOrDefault(c => oneM2M.Time.Parse(c.Ct) == latestCreationTime);
                return latestResource;
            }
            return null;
        }

        public static oneM2M.ResourceType GetResourceType(object resource)
        {
            switch (resource.GetType().Name.ToString())
            {
                case "cnt": return oneM2M.ResourceType.Container;
                case "cin": return oneM2M.ResourceType.ContentInstance;
                case "ae": return oneM2M.ResourceType.ApplicationEntity;
                case "sub": return oneM2M.ResourceType.Subscription;
                default:
                    return default(oneM2M.ResourceType);
            }
        }
        #endregion

        #region Miscellaneous
        [Obsolete("Works only in openDaylight IoTDM")]
        public static string GetResourceIdFromParentId(string resourceId)
        {
            if (!string.IsNullOrEmpty(resourceId))
            {
                string[] split = resourceId.Split('/');
                if (split != null && split.Length >= 3)
                    return (split[2]);
                else
                    return null;
            }
            else
                return null;
        }

        public static bool IsPath(string path)
        {
            if (!string.IsNullOrEmpty(path))
                if (path.Contains("/"))
                    return true;
            return false;
        }
        public static bool IsId(string id)
        {
            if (!string.IsNullOrEmpty(id))
                if (!id.Contains("/"))  //ToDo: check regex
                    return true;
            return false;
        }

        public static Result<T> HandleResultWithException<T>(Result<T> result, Exception e)
        {
            if (result == null)
                return new Result<T>(false, default(T), new Message(MessageType.Error, e.Message));
            else
            {
                result.Messages.Add(new Message(MessageType.Exception, e.Message, e.HResult.ToString()));
                result.Success = false;
                return result;
            }
        }

        public static bool IsNullObjects(params object[] args)
        {
            for (int i = 0; i < args.Length; i++)
            {
                if (args[i] == null)
                    return true;
            }
            return false;
        }
        #endregion

    }
}
