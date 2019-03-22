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
using NLog;

namespace BaSys40.RI.AAS.SmartControl
{
    public class SmartControlHandler : IAssetAdministrationShellHandler, IDisposable
    {
        private readonly SimpleLocalHttpServer server;
        private static Dictionary<string, IConnectableOperation> connectedOperations = new Dictionary<string, IConnectableOperation>();
        private static Dictionary<string, IConnectableDataElement> connectedProperties = new Dictionary<string, IConnectableDataElement>();
        private static Dictionary<string, IConnectableEvent> connectedEvents = new Dictionary<string, IConnectableEvent>();

        private static readonly ILogger logger = LogManager.GetCurrentClassLogger();

        public SmartControlHandler(string endpoint)
        {
            server = new SimpleLocalHttpServer(endpoint);
            server.Start(HandleNotification, HandleResponse);
        }

        public SmartControlHandler(int localHttpPort) : this("http://127.0.0.1:" + localHttpPort)
        { }

        public SmartControlHandler() : this(SmartControl.Settings.CallbackEndpointUrl)
        { }

        public void RegisterGetPropertyValueHandler(IConnectableSubmodel connectableSubmodel, IConnectableDataElement connectableProperty, GetDataElementValueEventHandler getPropertyValueHandler)
        {
            var cPropId = string.Join("/", connectableSubmodel.Submodel.IdShort, connectableProperty.DataElement.IdShort);
            if (connectedProperties.ContainsKey(cPropId))
            {
                connectedProperties[cPropId].GetDataElementValueHandler += getPropertyValueHandler;
            }
            else
            {
                connectableProperty.GetDataElementValueHandler += getPropertyValueHandler;
                connectedProperties.Add(cPropId, connectableProperty);
            }
        }

        public void RegisterSetPropertyValueHandler(IConnectableSubmodel connectableSubmodel, IConnectableDataElement connectableProperty, SetDataElementValueEventHandler SetPropertyValueHandler)
        {
            var cPropId = string.Join("/", connectableSubmodel.Submodel.IdShort, connectableProperty.DataElement.IdShort);
            if (connectedProperties.ContainsKey(cPropId))
            {
                connectedProperties[cPropId].SetDataElementValueHandler += SetPropertyValueHandler;
            }
            else
            {
                connectableProperty.SetDataElementValueHandler += SetPropertyValueHandler;
                connectedProperties.Add(cPropId, connectableProperty);
            }
        }
        public void RegisterMethodCalledEventHandler(IConnectableSubmodel connectableSubmodel, IConnectableOperation connectableOperation, MethodCalledEventHandler handler)
        {
            connectableOperation.OnCallMethod += handler;
            var cOpId = string.Join("/", connectableSubmodel.Submodel.IdShort, connectableOperation.Operation.IdShort);
            if (!connectedOperations.ContainsKey(cOpId))
                connectedOperations.Add(cOpId, connectableOperation);
            else
                connectedOperations[cOpId] = connectableOperation;
        }
        
        public void RegisterEventHandler(IConnectableSubmodel connectableSubmodel, IConnectableEvent connectableEvent, BaSys40.API.AssetAdministrationShell.EventHandler handler)
        {
            connectableEvent.EventHandler += handler;
            var cEvId = string.Join("/", connectableSubmodel.Submodel.IdShort, connectableEvent.Event.IdShort);
            if (!connectedEvents.ContainsKey(cEvId))
                connectedEvents.Add(cEvId, connectableEvent);
            else
                connectedEvents[cEvId] = connectableEvent;
        }

        private void HandleResponse(HttpListenerResponse obj)
        {
            obj.StatusCode = (int)HttpStatusCode.OK;
            obj.Close();
        }

        private void HandleNotification(HttpListenerRequest obj)
        {
            logger.Debug("Enter HandleNotification(...), Incoming request: {0},{1}", obj.HttpMethod, obj.Url);
            
            Notification notification = Notification.ReadFrom(obj);
            if (notification != null)
            {
                logger.Debug("Parsed notification, subscription reference: {0}, notification event?: {1}", notification.SubscriptionReference, notification.NotificationEvent);

                Request request = RequestFactory.CreateRequest(SmartControl.oneM2MClient, SmartControl.Settings.oneM2MConfig.ClientId, SmartControl.Settings.oneM2MConfig.Endpoint, notification.NotificationEvent.Representation.Ri.Replace("/in-cse/", string.Empty));
                var result = Helper.DiscoverResource(SmartControl.oneM2MClient, request, out List<string> resourceUrils);
                if (result.Success && resourceUrils != null && resourceUrils.Count > 0)
                {
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
                            if (connectedProperties.TryGetValue(resourceUril, out IConnectableDataElement cProp))
                            {
                                var value = SmartControlUtils.ConvertPropertyValue(requestCall.Content, cProp.DataElement.ValueType);
                                cProp.SetLocalValue(value);
                            }
                        }
                        else
                        {
                            if (connectedOperations.TryGetValue(resourceUril, out IConnectableOperation cOp))
                            {
                                List<IArgument> inputArguments = SmartControlUtils.ConvertStringArguments(requestCall.Content, cOp.Operation.In);
                                List<IArgument> outputArguments = new List<IArgument>();
                                var invokeResult = cOp.InvokeLocal(inputArguments, outputArguments, 10000);
                                if (invokeResult.Success)
                                {
                                    string argumentString = SmartControlUtils.ConvertStringArguments(outputArguments);

                                    request = RequestFactory.CreateRequest(SmartControl.oneM2MClient, SmartControl.Settings.oneM2MConfig.ClientId, SmartControl.Settings.oneM2MConfig.Endpoint, SmartControl.Settings.oneM2MConfig.CSEName,
                                        resourceTree[1], resourceTree[2], resourceTree[3], resourceTree[4], SmartControl.ContainerStrings.RESPONSE);
                                    var labels = new List<string>() { SmartControl.RELATION + SmartControl.SEPERATOR + notification.NotificationEvent.Representation.Ri };
                                    ContentInstance.Create(SmartControl.oneM2MClient, request, requestCall.ResourceName, argumentString, null, labels);
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

        #region IDisposable Support
        private bool disposedValue = false; // To detect redundant calls

        protected virtual void Dispose(bool disposing)
        {
            if (!disposedValue)
            {
                if (disposing)
                {
                    server.Stop();
                }
                disposedValue = true;
            }
        }


        public void Dispose()
        {
            Dispose(true);
        }
        #endregion
    }
}
