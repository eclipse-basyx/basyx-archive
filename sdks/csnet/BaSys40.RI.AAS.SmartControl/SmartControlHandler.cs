using BaSys40.API.AssetAdministrationShell;
using BaSys40.Models.Core;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.AssetAdministrationShell.Implementations;
using BaSys40.Utils.Server;
using Newtonsoft.Json;
using oneM2MClient;
using oneM2MClient.Client;
using oneM2MClient.Resources;
using oneM2MClient.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using BaSys40.API.Platform;
using Newtonsoft.Json.Linq;

namespace BaSys40.RI.AAS.SmartControl
{
    public class SmartControlHandler : IAssetAdministrationShellHandler
    {
        private readonly SimpleLocalHttpServer server;
        private static Dictionary<string, IConnectableOperation> connectedOperations = new Dictionary<string, IConnectableOperation>();
        private static Dictionary<string, IConnectableProperty> connectedProperties = new Dictionary<string, IConnectableProperty>();
        private static Dictionary<string, IConnectableEvent> connectedEvents = new Dictionary<string, IConnectableEvent>();
        public SmartControlHandler(int localHttpPort = 8544)
        {
            server = new SimpleLocalHttpServer(localHttpPort);
            server.Start(HandleNotification);
        }
        public void RegisterGetPropertyValueHandler(IConnectableSubModel connectableSubModel, IConnectableProperty connectableProperty, GetPropertyValueHandler getPropertyValueHandler)
        {
            var cPropId = string.Join("/", connectableSubModel.SubModel.Identification.Id, connectableProperty.Property.Identification.Id);
            if (connectedProperties.ContainsKey(cPropId))
            {
                connectedProperties[cPropId].GetPropertyValueHandler += getPropertyValueHandler;
            }
            else
            {
                connectableProperty.GetPropertyValueHandler += getPropertyValueHandler;
                connectedProperties.Add(cPropId, connectableProperty);
            }
        }

        public void RegisterSetPropertyValueHandler(IConnectableSubModel connectableSubModel, IConnectableProperty connectableProperty, SetPropertyValueHandler SetPropertyValueHandler)
        {
            var cPropId = string.Join("/", connectableSubModel.SubModel.Identification.Id, connectableProperty.Property.Identification.Id);
            if (connectedProperties.ContainsKey(cPropId))
            {
                connectedProperties[cPropId].SetPropertyValueHandler += SetPropertyValueHandler;
            }
            else
            {
                connectableProperty.SetPropertyValueHandler += SetPropertyValueHandler;
                connectedProperties.Add(cPropId, connectableProperty);
            }
        }
        public void RegisterMethodCalledEventHandler(IConnectableSubModel connectableSubModel, IConnectableOperation connectableOperation, MethodCalledEventHandler handler)
        {
            connectableOperation.OnCallMethod += handler;
            var cOpId = string.Join("/", connectableSubModel.SubModel.Identification.Id, connectableOperation.Operation.Identification.Id);
            if (!connectedOperations.ContainsKey(cOpId))
                connectedOperations.Add(cOpId, connectableOperation);
            else
                connectedOperations[cOpId] = connectableOperation;
        }
        
        public void RegisterEventHandler(IConnectableSubModel connectableSubModel, IConnectableEvent connectableEvent, BaSys40.API.AssetAdministrationShell.EventHandler handler)
        {
            connectableEvent.EventHandler += handler;
            var cEvId = string.Join("/", connectableSubModel.SubModel.Identification.Id, connectableEvent.Event.Identification.Id);
            if (!connectedEvents.ContainsKey(cEvId))
                connectedEvents.Add(cEvId, connectableEvent);
            else
                connectedEvents[cEvId] = connectableEvent;
        }
        
        private void HandleNotification(HttpListenerRequest obj)
        {
            Notification notification = Notification.ReadFrom(obj);
            if (notification != null)
            {
                Request request = RequestFactory.CreateRequest(SmartControl.oneM2MClient, SmartControl.Settings.oneM2MConfig.ClientId, SmartControl.Settings.oneM2MConfig.Endpoint, notification.NotificationEvent.Representation.Ri.Replace("/in-cse/", string.Empty));
                var result = Helper.DiscoverResource(SmartControl.oneM2MClient, request, out List<string> resourceUrils);
                if (result.Success && resourceUrils.Count > 0)
                    if (notification.TryGetResource(out ContentInstance requestCall))
                    {
                        string[] resourceTree = null;
                        string resourceUril = string.Empty;
                        if (!string.IsNullOrEmpty(notification.SubscriptionReference))
                        {
                            resourceTree = notification.SubscriptionReference.Split(new char[] { '/' }, StringSplitOptions.RemoveEmptyEntries);
                            if (resourceTree.Length >= 4)
                                resourceUril = string.Join("/", resourceTree[1], resourceTree[2], resourceTree[3]);
                        }

                        if (string.IsNullOrEmpty(resourceUril))
                        {
                            resourceTree = resourceUrils[0].Split(new char[] { '/' }, StringSplitOptions.RemoveEmptyEntries);
                            if (resourceTree.Length >= 5)
                                resourceUril = string.Join("/", resourceTree[3], resourceTree[4]);
                        }
                        if (resourceTree.Contains(SmartControl.ContainerStrings.DATA))
                        {
                            if (connectedProperties.TryGetValue(resourceUril, out IConnectableProperty cProp))
                            {
                                var val = new ElementValue<string>(requestCall.Content, DataObjectType.String);
                                cProp.SetLocalValue(val);
                            }
                        }
                        else if (resourceTree.Contains(SmartControl.ContainerStrings.GET))
                        {
                            if (connectedProperties.TryGetValue(resourceUril, out IConnectableProperty cProp))
                            {
                                var value = cProp.GetLocalValue();

                                request = RequestFactory.CreateRequest(SmartControl.oneM2MClient, SmartControl.Settings.oneM2MConfig.ClientId, SmartControl.Settings.oneM2MConfig.Endpoint, SmartControl.Settings.oneM2MConfig.CSEName,
                                        resourceTree[1], resourceTree[2], resourceTree[3], SmartControl.ContainerStrings.GET, SmartControl.ContainerStrings.RESPONSE);
                                ContentInstance.Create(SmartControl.oneM2MClient, request, requestCall.ResourceName, value.ToString());

                                request = RequestFactory.CreateRequest(SmartControl.oneM2MClient, SmartControl.Settings.oneM2MConfig.ClientId, SmartControl.Settings.oneM2MConfig.Endpoint, SmartControl.Settings.oneM2MConfig.CSEName,
                                        resourceTree[1], resourceTree[2], resourceTree[3], SmartControl.ContainerStrings.DATA);
                                ContentInstance.Create(SmartControl.oneM2MClient, request, Guid.NewGuid().ToString(), value.ToString());
                            }
                        }
                        else if (resourceTree.Contains(SmartControl.ContainerStrings.SET))
                        {
                            if (connectedProperties.TryGetValue(resourceUril, out IConnectableProperty cProp))
                            {
                                var val = new ElementValue<string>(requestCall.Content, DataObjectType.String);
                                cProp.SetLocalValue(val);

                                request = RequestFactory.CreateRequest(SmartControl.oneM2MClient, SmartControl.Settings.oneM2MConfig.ClientId, SmartControl.Settings.oneM2MConfig.Endpoint, SmartControl.Settings.oneM2MConfig.CSEName,
                                        resourceTree[1], resourceTree[2], resourceTree[3], SmartControl.ContainerStrings.SET, SmartControl.ContainerStrings.RESPONSE);
                                ContentInstance.Create(SmartControl.oneM2MClient, request, requestCall.ResourceName, requestCall.Content);
                            }
                        }
                        else
                        {
                            if (connectedOperations.TryGetValue(resourceUril, out IConnectableOperation cOp))
                            {
                                string urlDecoded = WebUtility.UrlDecode(requestCall.Content);
                                JArray ja = JArray.Parse(urlDecoded);
                                string[] args = ja.ToObject<string[]>();
                                List<IArgument> inputArguments = SmartControlUtils.ConvertStringArguments(args);
                                List<IArgument> outputArguments = new List<IArgument>();
                                var invokeResult = cOp.InvokeLocal(inputArguments, out outputArguments, 2000);
                                if (invokeResult.Success)
                                {
                                    string contentString = string.Empty;
                                    if (outputArguments.Count > 0)
                                    {
                                        string[] outArgs = SmartControlUtils.ConvertStringArguments(outputArguments);
                                        contentString = "[" + string.Join(SmartControl.ELEMENT_SEPERATOR, outArgs) + "]";
                                    }
                                    else
                                        contentString = "[]";

                                    //string urlEncoded = WebUtility.UrlEncode(contentString);
                                    request = RequestFactory.CreateRequest(SmartControl.oneM2MClient, SmartControl.Settings.oneM2MConfig.ClientId, SmartControl.Settings.oneM2MConfig.Endpoint, SmartControl.Settings.oneM2MConfig.CSEName,
                                        resourceTree[1], resourceTree[2], resourceTree[3], resourceTree[4], SmartControl.ContainerStrings.RESPONSE);
                                    ContentInstance.Create(SmartControl.oneM2MClient, request, requestCall.ResourceName, contentString);

                                    request = RequestFactory.CreateRequest(SmartControl.oneM2MClient, SmartControl.Settings.oneM2MConfig.ClientId, SmartControl.Settings.oneM2MConfig.Endpoint, SmartControl.Settings.oneM2MConfig.CSEName,
                                        resourceTree[1], resourceTree[2], resourceTree[3], resourceTree[4], SmartControl.ContainerStrings.REQUEST, requestCall.ResourceName);
                                    ContentInstance.Delete(SmartControl.oneM2MClient, request);
                                }

                            }
                            else if (connectedEvents.TryGetValue(resourceUril, out IConnectableEvent cEv))
                            {
                                cEv.Invoke(JsonConvert.DeserializeObject<PublishableEvent>(requestCall.Content));
                            }
                        }
                    }
            }
        }
    }
}
