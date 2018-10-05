using oneM2MClient;
using oneM2MClient.Client;
using oneM2MClient.Resources;
using oneM2MClient.Protocols;
using oneM2MClient.Utils;

using System;
using System.Linq;
using System.IO;
using System.Reflection;
using System.Collections.Generic;

using BaSys40.API.Platform;
using BaSys40.Models.Core;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Utils.ResultHandling;

using Newtonsoft.Json;
using BaSys40.Models.Core.AssetAdministrationShell.Implementations;
using BaSys40.Models.Core.AssetAdministrationShell.Enums;
using BaSys40.Models.Core.Identification;
using System.Net;

namespace BaSys40.RI.AAS.SmartControl
{
    public partial class SmartControl : IAssetAdministrationShellManager, IAssetAdministrationShellRegistry
    {
        public static SmartControlSettings Settings { get; private set; }

        public static readonly IClient oneM2MClient;
        public static readonly string SettingsPath;

        static SmartControl()
        {
            SettingsPath = Path.Combine(Path.GetDirectoryName(Assembly.GetEntryAssembly().Location), SmartControlSettings.FileName);
            Settings = SmartControlSettings.LoadSettings(SettingsPath);
            oneM2MClient = ClientFactory.CreateClient(Settings.oneM2MConfig.ProtocolBinding);
        }

        private void SmartControlSettingsChanged(string fullPath)
        {
            Settings = SmartControlSettings.LoadSettings(fullPath);
        }

        private static SmartControl smartControl;
        public static SmartControl Instance
        {
            get
            {
                if (smartControl == null)
                    smartControl = new SmartControl();
                return smartControl;
            }
        }
        private SmartControl()
        {
            Settings.ConfigureSettingsWatcher(SettingsPath, SmartControlSettingsChanged);
        }


        #region Implementation of IAssetAdministrationShellManager

        public IResult<IAssetAdministrationShell> CreateAssetAdministrationShell(IAssetAdministrationShell aas)
        {
            var result = AddAAS(aas);
            if (result.Success)
            {
                return ConvertResult<IAssetAdministrationShell>(aas, result);
            }
            else
                return ConvertResult<IAssetAdministrationShell>(result);
        }

        public IResult UpdateAssetAdministrationShell(string aasId, Dictionary<string, string> metaData)
        {
            var result = UpdateAAS(aasId, metaData);
            return ConvertResult(result);
        }

        public IResult DeleteAssetAdministrationShell(string aasId)
        {
            var result = RemoveComponent(new string[] { aasId } );
            return ConvertResult(result);
        }

        public IResult DeleteSubModel(string aasId, string subModelId)
        {
            var components = new string[] { aasId, subModelId };
            var result = RemoveComponent(components);

            return ConvertResult(result);
        }
        public IResult InvokeOperation(string aasId, string subModelId, string operationId, List<IArgument> inputArguments, out List<IArgument> outputArguments, int timeout)
        {
            var result = CallOperation(aasId, subModelId, operationId, inputArguments, out outputArguments, timeout);
            return ConvertResult(result);
        }


        public IResult<ISubModel> CreateSubModel(string aasId, ISubModel subModel)
        {
            var result = AddSubModel(aasId, subModel);
            if(subModel.Properties != null && subModel.Properties.Count > 0)
            {
                foreach (var property in subModel.Properties)
                {
                    CreateProperty(aasId, subModel.Identification.Id, property);
                }
            }
            if (subModel.Operations != null && subModel.Operations.Count > 0)
            {
                foreach (var operation in subModel.Operations)
                {
                    CreateOperation(aasId, subModel.Identification.Id, operation);
                }
            }
            if (subModel.Events != null && subModel.Events.Count > 0)
            {
                foreach (var eventable in subModel.Events)
                {
                    CreateEvent(aasId, subModel.Identification.Id, eventable);
                }
            }
            if (result.Success)
            {
                return ConvertResult<ISubModel>(subModel, result);
            }
            else
                return ConvertResult<ISubModel>(result);
        }

        public IResult<IPropertyDescription> CreateProperty(string aasId, string subModelId, IPropertyDescription property)
        {
            var result = AddProperty(aasId, subModelId, property);
            if(result.Success)
            {
                return ConvertResult<IPropertyDescription>(property, result);
            }
            return ConvertResult<IPropertyDescription>(result);
        }

        public IResult<IOperationDescription> CreateOperation(string aasId, string subModelId, IOperationDescription operation)
        {
            var result = AddOperation(aasId, subModelId, (OperationDescription)operation);
            if (result.Success)
            {
                return ConvertResult<IOperationDescription>(operation, result);
            }
            return ConvertResult<IOperationDescription>(result);
        }

        public IResult<IEventDescription> CreateEvent(string aasId, string subModelId, IEventDescription eventable)
        {
            var result = AddEvent(aasId, subModelId, eventable);
            if (result.Success)
            {
                return ConvertResult<IEventDescription>(eventable, result);
            }
            return ConvertResult<IEventDescription>(eventable, result);
        }

        public IResult<IAssetAdministrationShell> RetrieveAssetAdministrationShell(string aasId)
        {
            var result = ReadAAS(aasId, out IAssetAdministrationShell aas);
            if (result.Success && aas != null)
            {
                return ConvertResult<IAssetAdministrationShell>(aas, result);
            }
            return ConvertResult<IAssetAdministrationShell>(result);
        }

        public IResult<List<IAssetAdministrationShell>> RetrieveAssetAdministrationShells()
        {
            var result = ReadAllAAS(out List<IAssetAdministrationShell> aasList);
            if (result.Success && aasList?.Count > 0)
            {
                return ConvertResult<List<IAssetAdministrationShell>>(aasList, result);
            }
            return ConvertResult<List<IAssetAdministrationShell>>(result);
        }

        public IResult<IElementContainer<ISubModel>> RetrieveSubModels(string aasId)
        {
            var result = ReadSubModels(aasId, out IElementContainer<ISubModel> subModels);
            if (result.Success && subModels != null)
            {
                return ConvertResult<IElementContainer<ISubModel>>(subModels, result);
            }
            return ConvertResult<IElementContainer<ISubModel>>(result);

        }

        public IResult<ISubModel> RetrieveSubModel(string aasId, string subModelId)
        {
            var result = ReadSubModel(aasId, subModelId, out SubModel subModel);
            if (result.Success && subModel != null)
            {
                return ConvertResult<ISubModel>(subModel, result);
            }
            return ConvertResult<ISubModel>(result);
        }

        public IResult<IPropertyDescription> RetrieveProperty(string aasId, string subModelId, string propertyId)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(subModelId) && !string.IsNullOrEmpty(propertyId))
            {
                var result = ReadProperty(aasId, subModelId, propertyId, out PropertyDescription prop);
                if (result.Success && prop != null)
                {
                    var valueResult = ReadPropertyValue(aasId, subModelId, propertyId, out object value);
                    if (valueResult.Success && value != null)
                    {
                        prop.Value = new ElementValue<object>(value, DataObjectType.Object);
                    }
                    return ConvertResult<IPropertyDescription>(prop, result);
                }
                else
                    return ConvertResult<IPropertyDescription>(result);
            }
            return null;
        }

        public IResult UpdateProperty(string aasId, string subModelId, string propertyId, IValue value)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(subModelId) && !string.IsNullOrEmpty(propertyId))
            {
                var result = SetProperty(aasId, subModelId, propertyId, value);
                return ConvertResult(result);
            }
            return null;
        }        

        public IResult DeleteProperty(string aasId, string subModelId, string propertyId)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(subModelId) && !string.IsNullOrEmpty(propertyId))
            {
                var result = RemoveProperty(aasId, subModelId, propertyId);
                return ConvertResult(result);
            }
            return null;
        }

        public IResult<IOperationDescription> RetrieveOperation(string aasId, string subModelId, string operationId)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(subModelId) && !string.IsNullOrEmpty(operationId))
            {
                var result = ReadOperation(aasId, subModelId, operationId, out OperationDescription op);
                if (result.Success && op != null)
                {
                    return ConvertResult<IOperationDescription>(op, result);
                }
                else
                    return ConvertResult<IOperationDescription>(result);
            }
            return null;
        }



        public IResult DeleteOperation(string aasId, string subModelId, string operationId)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(subModelId) && !string.IsNullOrEmpty(operationId))
            {
                var result = RemoveOperation(aasId, subModelId, operationId);
                return ConvertResult(result);
            }
            return null;
        }

        

        public IResult<IEventDescription> RetrieveEvent(string aasId, string subModelId, string eventId)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(subModelId) && !string.IsNullOrEmpty(eventId))
            {
                var result = ReadEvent(aasId, subModelId, eventId, out EventDescription ev);
                if (result.Success && ev != null)
                {
                    return ConvertResult<IEventDescription>(ev, result);
                }
                else
                    return ConvertResult<IEventDescription>(result);
            }
            return null;
        }

        public IResult DeleteEvent(string aasId, string subModelId, string eventId)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(subModelId) && !string.IsNullOrEmpty(eventId))
            {
                var result = RemoveEvent(aasId, subModelId, eventId);
                return ConvertResult(result);
            }
            return null;
        }

        public IResult<IElementContainer<IPropertyDescription>> RetrieveProperties(string aasId, string subModelId)
        {
            var result = ReadSubModel(aasId, subModelId, out SubModel subModel);
            if (result.Success && subModel?.Properties != null)
            {
                return ConvertResult<IElementContainer<IPropertyDescription>>(subModel.Properties, result);
            }
            return ConvertResult<IElementContainer<IPropertyDescription>>(result);
        }

        public IResult<IElementContainer<IOperationDescription>> RetrieveOperations(string aasId, string subModelId)
        {
            var result = ReadSubModel(aasId, subModelId, out SubModel subModel);
            if (result.Success && subModel?.Operations != null)
            {
                return ConvertResult<IElementContainer<IOperationDescription>>(subModel.Operations, result);
            }
            return ConvertResult<IElementContainer<IOperationDescription>>(result);
        }

        public IResult<IElementContainer<IEventDescription>> RetrieveEvents(string aasId, string subModelId)
        {
            var result = ReadSubModel(aasId, subModelId, out SubModel subModel);
            if (result.Success && subModel?.Events != null)
            {
                return ConvertResult<IElementContainer<IEventDescription>>(subModel.Events, result);
            }
            return ConvertResult<IElementContainer<IEventDescription>>(result);
        }

        #endregion

        #region SmartControl oneM2M-Functions

        public static oneM2MClient.Utils.ResultHandling.Result<Response> CallOperation(string aasId, string subModelId, string operationId, List<IArgument> inputArguments, out List<IArgument> outputArguments, int timeout)
        {
            Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, subModelId, operationId, ContainerStrings.REQUEST);
            string argContent = string.Empty;
            if (inputArguments != null && inputArguments.Count > 0)
            {
                string arguments = string.Join(ELEMENT_SEPERATOR, inputArguments.Select(a => a.Value.Value).ToList().ConvertAll(d => d.ToString()));
                argContent = ParameterStrings.PARAM_BRACKET_LEFT + arguments + ParameterStrings.PARAM_BRACKET_RIGHT;
            }
            else
                argContent = ParameterStrings.PARAM_BRACKET_LEFT + ParameterStrings.PARAM_BRACKET_RIGHT;

            string urlEncoded = WebUtility.UrlEncode(argContent);
            var result = ContentInstance.Create(oneM2MClient, req, Guid.NewGuid().ToString(), urlEncoded);

            req.SetPath(aasId, subModelId, operationId, ContainerStrings.RESPONSE);
            List<string> outArgs = new List<string>();
            Utils.ResultHandling.Utils.RetryUntilSuccessOrTimeout(() => GetResponseFromCall(req, outArgs), TimeSpan.FromMilliseconds(timeout), TimeSpan.FromMilliseconds(100));
            outputArguments = SmartControlUtils.ConvertStringArguments(outArgs.ToArray());

            return result;
        }

        public static bool GetResponseFromCall(Request request, List<string> outputArguments)
        {
            var resp = Helper.ReadLatestAddedResource(oneM2MClient, request, out resource resource);
            if (resource != null && resource is cin cinResource)
            {
                if (cinResource.Con.Contains(ELEMENT_SEPERATOR))
                {
                    string[] outArgs = cinResource.Con.Split(new char[] { ELEMENT_SEPERATOR[0] }, StringSplitOptions.RemoveEmptyEntries);
                    for (int i = 0; i < outArgs.Length; i++)
                    {
                        outputArguments.Add(outArgs[i]);
                    }
                }
                else
                    outputArguments.Add(cinResource.Con);
                return true;
            }
            return false;
        }

        public static oneM2MClient.Utils.ResultHandling.Result<Response> SetProperty(string aasId, string subModelId, string propertyId, object value)
        {
            Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, subModelId, propertyId, ContainerStrings.DATA);

            string content = null;
            if (Convert.GetTypeCode(value) != TypeCode.Object)
                content = Convert.ToString(value);
            else
                content = JsonConvert.SerializeObject(value);

            var urlEncodedContent = WebUtility.UrlEncode(content);
            ContentInstance data_cin = new ContentInstance(Guid.NewGuid().ToString(), urlEncodedContent);
            var result = data_cin.Create(oneM2MClient, req);
            return result;
        }

        public static oneM2MClient.Utils.ResultHandling.Result<Response> UpdateAAS(string aasId, Dictionary<string,string> keyValues)
        {
            Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId);
            List<string> labels = null;
            if (keyValues?.Count > 0)
                labels = keyValues.Select(kvp => kvp.Key + SEPERATOR + kvp.Value)?.ToList();
            var result = ApplicationEntity.Update(oneM2MClient, req, aasId, null, labels);
            return result;
        }


        #region Converters

        public static SubModel ReadSubModelFrom(string assetId, ObjectTreeBuilder subModelTree)
        {
            var rootCnt = subModelTree?.GetValue<cnt>();
            if (rootCnt != null)
            {
                SubModel subModel = new SubModel()
                {
                    DisplayName = rootCnt.Rn,
                    SemanticReference = new Identifier(GetLabelValue(TYPE_IDENTIFIER, rootCnt.Lbl), Identificator.Internal),
                    Identification = new Identifier(GetLabelValue(Labels.ID, rootCnt.Lbl), Identificator.Internal),
                    Description = GetLabelValue(Labels.DESCRIPTION, rootCnt.Lbl),
                };

                if (subModelTree.HasChildren())
                {
                    subModel.Operations = new ElementContainer<IOperationDescription>();
                    subModel.Properties = new ElementContainer<IPropertyDescription>();
                    subModel.Events = new ElementContainer<IEventDescription>();

                    foreach (var subElement in subModelTree.Children)
                    {
                        var subElementCnt = subElement.GetValue<cnt>();
                        if (subElementCnt != null)
                        {
                            if (subElementCnt.Lbl.FirstOrDefault(l => l.Contains(BASYS_TYPE_IDENTIFIER + SEPERATOR + ElementInstance.OPERATION)) != null)
                            {
                                var op = ReadOperationFrom(subElement);
                                if (op != null)
                                    subModel.Operations.Add(op);
                            }
                            else if (subElementCnt.Lbl.FirstOrDefault(l => l.Contains(BASYS_TYPE_IDENTIFIER + SEPERATOR + ElementInstance.PROPERTY)) != null)
                            {
                                var prop = ReadPropertyFrom(subElement);
                                if (prop != null)
                                    subModel.Properties.Add(prop);
                            }
                            else if (subElementCnt.Lbl.FirstOrDefault(l => l.Contains(BASYS_TYPE_IDENTIFIER + SEPERATOR + ElementInstance.EVENT)) != null)
                            {
                                var prop = ReadEventFrom(subElement);
                                if (prop != null)
                                    subModel.Events.Add(prop);
                            }
                        }
                    }
                }
                return subModel;
            }
            return null;
        }

        public static OperationDescription ReadOperationFrom(ObjectTreeBuilder operationTree)
        {
            var operationCnt = operationTree?.GetValue<cnt>();
            OperationDescription operation = ConvertCntToOperation(operationCnt);
            if (operation != null)
            {
                string listenerUriSubscriptionPath = string.Join(PATH_SEPERATOR, ContainerStrings.REQUEST, DEFAULT_SUBSCRIPTION_NAME);
                if (operationTree.HasChildPath(listenerUriSubscriptionPath))
                {
                    var sub = operationTree.GetChild(listenerUriSubscriptionPath).GetValue<sub>();
                    var uri = sub?.Nu?.FirstOrDefault();
                    operation.Endpoint = uri ?? uri;
                }
                return operation;
            }
            return null;
        }

        public static EventDescription ReadEventFrom(ObjectTreeBuilder eventTree)
        {
            var eventCnt = eventTree?.GetValue<cnt>();
            if (eventCnt != null)
            {
                cin schemaCin = null;
                string schemaPath = string.Join(PATH_SEPERATOR, ContainerStrings.SCHEMA, EventIdentifier.SCHEMA_CIN);
                if (eventTree.HasChildPath(schemaPath))
                    schemaCin = eventTree.GetChild(schemaPath).GetValue<cin>();
                EventDescription eventable = ConvertCntToEvent(eventCnt, schemaCin);
                return eventable;
            }
            return null;
        }

        public static PropertyDescription ReadPropertyFrom(ObjectTreeBuilder propertyTree)
        {
            var propertyCnt = propertyTree?.GetValue<cnt>();
            PropertyDescription property = ConvertCntToProperty(propertyCnt);
            if (property != null)
            {
                //string listenerUriSubscriptionPath = string.Join(PATH_SEPERATOR, ContainerStrings.GET, ContainerStrings.REQUEST, DEFAULT_SUBSCRIPTION_NAME);
                string listenerUriSubscriptionPath = string.Join(PATH_SEPERATOR, ContainerStrings.DATA, DEFAULT_SUBSCRIPTION_NAME);
                if (propertyTree.HasChildPath(listenerUriSubscriptionPath))
                {
                    var sub = propertyTree.GetChild(listenerUriSubscriptionPath).GetValue<sub>();
                    var uri = sub?.Nu?.FirstOrDefault();
                    property.Endpoint = uri ?? uri;
                }
                /*
                string valuePath = string.Join(PATH_SEPERATOR, ContainerStrings.DATA);
                if (propertyTree.HasChildPath(valuePath) && propertyTree.GetChild(valuePath).HasChildren())
                {
                    var latestValue = Helper.GetLatestResource(propertyTree.GetChild(valuePath));
                    if(latestValue?.Con != null)
                        property.Value = new ElementValue<string>(latestValue.Con);
                }
                */
                return property;
            }
            return null;
        }

        public static IResult ConvertResult(oneM2MClient.Utils.ResultHandling.Result<Response> requestResponse)
        {
            IResult result = new Result(requestResponse.Success);

            if (!requestResponse.Success)
            {
                foreach (var msg in requestResponse.Messages)
                {
                    result.Messages.Add(new Message((MessageType)(int)msg.MessageType, msg.Text, msg.Code));
                }
            }
            return result;
        }

        public static IResult<T> ConvertResult<T>(oneM2MClient.Utils.ResultHandling.Result<Response> requestResponse)
        {
            IResult<T> result = new Result<T>(requestResponse.Success);

            if (!requestResponse.Success)
            {
                foreach (var msg in requestResponse.Messages)
                {
                    result.Messages.Add(new Message((MessageType)(int)msg.MessageType, msg.Text, msg.Code));
                }
            }
            return result;
        }

        public static IResult<T> ConvertResult<T>(T entity, oneM2MClient.Utils.ResultHandling.Result<Response> requestResponse)
        {
            IResult<T> result;
            if (entity != null)
                result = new Result<T>(requestResponse.Success, entity);
            else
                result = new Result<T>(requestResponse.Success);

            if (!requestResponse.Success)
            {
                foreach (var msg in requestResponse.Messages)
                {
                    result.Messages.Add(new Message((MessageType)(int)msg.MessageType, msg.Text, msg.Code));
                }
            }
            return result;
        }

        private static PropertyDescription ConvertCntToProperty(cnt propertyCnt)
        {
            if (propertyCnt != null)
            {
                var dataObjectType = GetDataTypeFromString(GetLabelValue(DATATYPE_IDENTIFIER, propertyCnt.Lbl));
                Identifier dataTypeDefinition = null;
                if (dataObjectType == DataObjectType.Object)
                    dataTypeDefinition = new Identifier(GetLabelValue(DATATYPE_IDENTIFIER, propertyCnt.Lbl), Identificator.Internal);

                bool isCollection = false;
                if (propertyCnt.Lbl.Contains(Labels.COLLECTION))
                    isCollection = true;

                PropertyDescription prop = new PropertyDescription()
                {
                    Identification = new Identifier(GetLabelValue(Labels.ID, propertyCnt.Lbl), Identificator.Internal),
                    Description = GetLabelValue(Labels.DESCRIPTION, propertyCnt.Lbl),
                    DisplayName = propertyCnt.Rn,                    
                    Readable = propertyCnt.Lbl.Contains(Labels.READABLE) ? true : false,
                    Writable = propertyCnt.Lbl.Contains(Labels.WRITABLE) ? true : false,
                    Eventable = propertyCnt.Lbl.Contains(Labels.EVENTABLE) ? true : false,
                    DataType = new DataType(dataObjectType, isCollection, false, dataTypeDefinition)
                };

                var semanticReference = GetLabelValue(TYPE_IDENTIFIER, propertyCnt.Lbl);
                if (semanticReference != null)
                    prop.SemanticReference = new Identifier(semanticReference, Identificator.Internal);

                return prop;
            }
            return null;
        }

        private static OperationDescription ConvertCntToOperation(cnt subElementCnt)
        {
            if (subElementCnt != null)
            {
                var dataObjectType = GetDataTypeFromString(GetLabelValue(RETURN_DATATYPE_IDENTIFIER, subElementCnt.Lbl));
                Identifier dataTypeDefinition = null;
                if (dataObjectType == DataObjectType.Object)
                    dataTypeDefinition = new Identifier(GetLabelValue(RETURN_DATATYPE_IDENTIFIER, subElementCnt.Lbl), Identificator.Internal);

                OperationDescription op = new OperationDescription()
                {
                    Identification = new Identifier(GetLabelValue(Labels.ID, subElementCnt.Lbl), Identificator.Internal),
                    Description = GetLabelValue(Labels.DESCRIPTION, subElementCnt.Lbl),
                    DisplayName = subElementCnt.Rn,
                };
                
                if (Int32.TryParse(GetLabelValue(ParameterStrings.GetParameterLength(), subElementCnt.Lbl), out int paramLength) && paramLength > 0)
                {
                    op.InputParameters = new List<IParameter>(paramLength);
                    for (int i = 0; i < paramLength; i++)
                    {
                        Parameter param = new Parameter()
                        {
                            ParameterName = GetLabelValue(ParameterStrings.GetParameterName(i), subElementCnt.Lbl),
                            DataType = new DataType(GetDataTypeFromString(GetLabelValue(ParameterStrings.GetParameterDataType(i), subElementCnt.Lbl)), false, false),
                            Index = i
                        };
                        op.InputParameters.Add(param);
                    }
                }

                op.OutputParameters = new List<IParameter>()
                {
                    new Parameter()
                    {
                        Index = 0,
                        DataType = new DataType(dataObjectType, false, false, dataTypeDefinition)                        
                    }
                };
                return op;
            }
            return null;
        }

        private static EventDescription ConvertCntToEvent(cnt subElementCnt, cin schema)
        {
            if (subElementCnt != null)
            {
                EventDescription op = new EventDescription()
                {
                    Identification = new Identifier(GetLabelValue(Labels.ID, subElementCnt.Lbl), Identificator.Internal),
                    Description = GetLabelValue(Labels.DESCRIPTION, subElementCnt.Lbl),
                    DisplayName = subElementCnt.Rn,
                    EntityType = (EntityType)Enum.Parse(typeof(EntityType), GetLabelValue(EventIdentifier.ENTITY_TYPE, subElementCnt.Lbl)),
                    EventCategory = GetLabelValue(EventIdentifier.EVENT_CATEGORY, subElementCnt.Lbl),
                    EventName = GetLabelValue(EventIdentifier.EVENT_NAME, subElementCnt.Lbl),
                };

                if(schema != null)
                {
                    op.SchemaType = (SchemaType)Enum.Parse(typeof(SchemaType), GetLabelValue(EventIdentifier.SCHEMA_TYPE, schema.Lbl));
                    op.Schema = schema.Con;
                }
                return op;
            }
            return null;
        }

        public static IAssetAdministrationShell ConvertAeResourceToAAS(ae aasAe)
        {
            if (aasAe != null)
            {
                var aas = new AssetAdministrationShell()
                {
                    Identification = new Identifier(GetLabelValue(Labels.ID, aasAe.Lbl), Identificator.Internal),
                    DisplayName = GetLabelValue(Labels.DISPLAY_NAME, aasAe.Lbl),
                    SemanticReference = new Identifier(GetLabelValue(AssetLabels.ASSET_TYPE_DEFINITION, aasAe.Lbl), Identificator.Internal),
                    Assets = new ElementContainer<IAsset>() {
                        new Asset() {
                            Identification = new Identifier(GetLabelValue(AssetLabels.ASSET_ID, aasAe.Lbl), Identificator.Internal),
                            AssetKind = GetLabelValue(AssetLabels.ASSET_KIND, aasAe.Lbl).Contains(((int)Kind.Type).ToString()) ? Kind.Type : Kind.Instance
                    } },
                    Description = GetLabelValue(Labels.DESCRIPTION, aasAe.Lbl)
                };
                return aas;
            }
            return null;
        }

        #endregion  

        #region Add-Operations
        public static oneM2MClient.Utils.ResultHandling.Result<Response> AddAAS(IAssetAdministrationShell aas)
        {
            Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName);
            string typeOrInstance = (aas.Assets[0].AssetKind == Kind.Type) ? ElementType.AAS_TYPE : ElementInstance.AAS;
            var labels = new List<string>
            {
                BASYS_TYPE_IDENTIFIER + SEPERATOR + typeOrInstance,                
                Labels.ID + SEPERATOR + aas.Identification.Id,
                Labels.DISPLAY_NAME + SEPERATOR + aas.DisplayName,
                AssetLabels.ASSET_TYPE_DEFINITION + SEPERATOR + aas.SemanticReference.Id,
                AssetLabels.ASSET_KIND + SEPERATOR + (int)aas.Assets[0].AssetKind.Value,
                AssetLabels.ASSET_ID + SEPERATOR + aas.Assets[0].Identification.Id
            };

            if (!string.IsNullOrEmpty(aas.Description))
                labels.Add(Labels.DESCRIPTION + SEPERATOR + aas.Description);

            ApplicationEntity ae = new ApplicationEntity(aas.DisplayName, aas.Identification.Id, true, aas.Identification.Id, null, labels);
            var result = ae.Create(oneM2MClient, req);
            return result;
        }
        public static oneM2MClient.Utils.ResultHandling.Result<Response> AddProperty(string aasId, string subModelId, IPropertyDescription property)
        {
            Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, subModelId);
            string dty = string.Empty;
            if (property.DataType.DataObjectType.Value == DataObjectType.Object && !string.IsNullOrEmpty(property.DataType.SemanticReference.Id))
                dty = property.DataType.SemanticReference.Id;
            else
                dty = property.DataType.DataObjectType.Value.ToString().ToLower();


            var labels = new List<string>
            {
                BASYS_TYPE_IDENTIFIER + SEPERATOR + ElementInstance.PROPERTY,
                DATATYPE_IDENTIFIER + SEPERATOR + dty,
            };

            if (property.Readable.HasValue && property.Readable.Value)
                labels.Add(Labels.READABLE);
            if (property.Writable.HasValue && property.Writable.Value)
                labels.Add(Labels.WRITABLE);
            if (property.Eventable.HasValue && property.Eventable.Value)
                labels.Add(Labels.EVENTABLE);

            if (property.DataType.IsCollection)
                labels.Add(Labels.COLLECTION);

            if (property.SemanticReference != null)
                labels.Add(TYPE_IDENTIFIER + SEPERATOR + property.SemanticReference.Id);

            if (!string.IsNullOrEmpty(property.Identification.Id))
                labels.Add(Labels.ID + SEPERATOR + property.Identification.Id);
            if (!string.IsNullOrEmpty(property.Description))
                labels.Add(Labels.DESCRIPTION + SEPERATOR + property.Description);

            Container cont = new Container(property.Identification.Id, null, labels, 8);
            cont.Create(oneM2MClient, req);

            req.AddPath(property.Identification.Id);
            Container.Create(oneM2MClient, req, ContainerStrings.DATA, null, null, 8);
            req.AddPath(ContainerStrings.DATA);
            var result = CreateSubscription(req, property.Endpoint);

            /*
            if (property.Readable.HasValue && property.Readable.Value)
            {
                Container.Create(oneM2MClient, req, ContainerStrings.GET, null, null, 8);
                CreateRequestConstruct(new string[] { aasId, subModelId, property.Identification.Id, ContainerStrings.GET }, property.Endpoint);
            }
            if (property.Writable.HasValue && property.Writable.Value)
            {
                Container.Create(oneM2MClient, req, ContainerStrings.SET, null, null, 8);
                CreateRequestConstruct(new string[] { aasId, subModelId, property.Identification.Id, ContainerStrings.SET }, property.Endpoint);
            }
            */
            /*
            if (value != null && result.Success)
                SetProperty(aasId, subModelId, propertyId, value);
            */
            return result;
        }

        public static oneM2MClient.Utils.ResultHandling.Result<Response> CreateSubscription(Request req, string subscriptionUri)
        {
            req.From(subscriptionUri);
            Subscription sub = new Subscription(DEFAULT_SUBSCRIPTION_NAME, new List<string> { subscriptionUri }, new List<notificationEventType> { notificationEventType.CreateofDirectChildResource });
            sub.NotificationContentType = notificationContentType.AllAttributes;
            var result = sub.Create(oneM2MClient, req);
            return result;
        }

        public static oneM2MClient.Utils.ResultHandling.Result<Response> AddEvent(string aasId, string subModelId, IEventDescription eventDescription)
        {
            Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, subModelId);

            var labels = new List<string>
            {
                BASYS_TYPE_IDENTIFIER + SEPERATOR + ElementInstance.EVENT,              
                EventIdentifier.ENTITY_TYPE + SEPERATOR +  Enum.GetName(typeof(EntityType), eventDescription.EntityType.Value),
                EventIdentifier.EVENT_NAME + SEPERATOR + eventDescription.EventName,
                EventIdentifier.EVENT_CATEGORY  + SEPERATOR + eventDescription.EventCategory
            };

            if (!string.IsNullOrEmpty(eventDescription.Identification.Id))
                labels.Add(Labels.ID + SEPERATOR + eventDescription.Identification.Id);
            if (!string.IsNullOrEmpty(eventDescription.Description))
                labels.Add(Labels.DESCRIPTION + SEPERATOR + eventDescription.Description);

            Container.Create(oneM2MClient, req, eventDescription.Identification.Id, null, labels, 1);

            req.AddPath(eventDescription.Identification.Id);

            var result = Container.Create(oneM2MClient, req, ContainerStrings.DATA, null, null, 8);

            req.AddPath(ContainerStrings.DATA);
            req.From(DEFAULT_SUBSCRIPTION_URI);
            Subscription sub = new Subscription(DEFAULT_SUBSCRIPTION_NAME, new List<string> { DEFAULT_SUBSCRIPTION_URI }, new List<notificationEventType> { notificationEventType.CreateofDirectChildResource });
            sub.NotificationContentType = notificationContentType.AllAttributes;
            result = sub.Create(oneM2MClient, req);
            

            if (!string.IsNullOrEmpty(eventDescription.Schema) && eventDescription.SchemaType.HasValue)
            {
                req.From(DEFAULT_FROM);
                Container.Create(oneM2MClient, req, ContainerStrings.SCHEMA, null, null, 1);
                req.SetPath(aasId, subModelId, eventDescription.Identification.Id, ContainerStrings.SCHEMA);

                var schemaLabels = new List<string>();
                schemaLabels.Add(EventIdentifier.SCHEMA_TYPE + SEPERATOR + Enum.GetName(typeof(SchemaType), eventDescription.SchemaType.Value));
                ContentInstance.Create(oneM2MClient, req, EventIdentifier.SCHEMA_CIN, eventDescription.Schema, null, schemaLabels);
            }
            
            return result;
        }
        public static oneM2MClient.Utils.ResultHandling.Result<Response> AddOperation(string aasId, string subModelId, IOperationDescription<IParameter> operation)
        {
            Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, subModelId);

            var labels = new List<string>
            {
                BASYS_TYPE_IDENTIFIER + SEPERATOR + ElementInstance.OPERATION,
                RETURN_DATATYPE_IDENTIFIER + SEPERATOR + operation.OutputParameters[0].DataType.DataObjectType.Value.ToString(),
            };

            if (operation.InputParameters != null)
            {
                labels.Add(ParameterStrings.GetParameterLength() + SEPERATOR + operation.InputParameters.Count);

                for (int i = 0; i < operation.InputParameters.Count; i++)
                {
                    labels.Add(ParameterStrings.GetParameterName(i) + SEPERATOR + operation.InputParameters[i].ParameterName);
                    labels.Add(ParameterStrings.GetParameterDataType(i) + SEPERATOR + operation.InputParameters[i].DataType.DataObjectType.Value.ToString().ToLower());
                }
            }
            else
                labels.Add(ParameterStrings.GetParameterLength() + SEPERATOR + 0);

            if (operation.OutputParameters != null)
                labels.Add(RETURN_DATATYPE_IDENTIFIER + SEPERATOR + operation.OutputParameters[0].DataType.DataObjectType.Value.ToString().ToLower());
            else
                labels.Add(RETURN_DATATYPE_IDENTIFIER + SEPERATOR + "void");


            if (!string.IsNullOrEmpty(operation.Identification.Id))
                labels.Add(Labels.ID + SEPERATOR + operation.Identification.Id);
            if (!string.IsNullOrEmpty(operation.Description))
                labels.Add(Labels.DESCRIPTION + SEPERATOR + operation.Description);

            Container cont = new Container(operation.Identification.Id, null, labels);
            var result = cont.Create(oneM2MClient, req);

            CreateRequestConstruct(new string[] { aasId, subModelId, operation.Identification.Id }, operation.Endpoint);

            return result;
        }
        public static oneM2MClient.Utils.ResultHandling.Result<Response> AddSubModel(string aasId, ISubModel subModel)
        {
            Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId);

            var labels = new List<string>
            {
                BASYS_TYPE_IDENTIFIER + SEPERATOR + ElementInstance.SUBMODEL,
                AssetLabels.ASSET_KIND + SEPERATOR + subModel.SubModelKind.Value,
                TYPE_IDENTIFIER + SEPERATOR + subModel.SemanticReference.Id
            };

            if (!string.IsNullOrEmpty(subModel.Identification.Id))
                labels.Add(Labels.ID + SEPERATOR + subModel.Identification.Id);
            if (!string.IsNullOrEmpty(subModel.Description))
                labels.Add(Labels.DESCRIPTION + SEPERATOR + subModel.Description);

            Container cont = new Container(subModel.Identification.Id, null, labels);
            var result = cont.Create(oneM2MClient, req);
            return result;
        }
        #endregion

        #region Read-Operations
        public static oneM2MClient.Utils.ResultHandling.Result<Response> ReadAllAAS(out List<IAssetAdministrationShell> aasList)
        {
            Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName);
            var response = Helper.ReadResourcesByLabels(oneM2MClient, req, new List<string> { BASYS_TYPE_IDENTIFIER + SEPERATOR + ElementInstance.AAS }, out List<resource> resources);
            if (response.Success && resources?.Count > 0)
            {
                aasList = new List<IAssetAdministrationShell>();
                foreach (var resource in resources)
                {
                    if (resource is ae aasAe)
                    {
                        var aas = ConvertAeResourceToAAS(aasAe);
                        if (aas != null)
                        {
                            ReadSubModels(aas.Identification.Id, out IElementContainer<ISubModel> subModels);
                            if (subModels?.Count > 0)
                            {
                                aas.SubModels = new ElementContainer<ISubModel>();
                                foreach (var subModel in subModels)
                                {
                                    string serviceUri = string.Join(PATH_SEPERATOR, PATH_SEPERATOR, aas.Identification.Id, ElementInstance.SUBMODELS, subModel.DisplayName).Replace("//", "/");
                                    aas.SubModels.Add(new SubModel() { Endpoint = serviceUri });
                                }
                            }
                            aasList.Add(aas);
                        }
                    }
                }
                return response;
            }
            aasList = null;
            return response;
        }
        public static oneM2MClient.Utils.ResultHandling.Result<Response> ReadAAS(string aasId, out IAssetAdministrationShell aas)
        {
            if (!string.IsNullOrEmpty(aasId))
            {
                Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName);
                var response = Helper.ReadResourcesByLabels(oneM2MClient, req, new List<string> { Labels.ID + SEPERATOR + aasId }, out List<resource> resources);
                if (response.Success && resources != null
                    && resources.FirstOrDefault(r => r.Ty.Value == (int)resourceType.AE) != null)
                {
                    aas = ConvertAeResourceToAAS((ae)resources.FirstOrDefault());
                    ReadSubModels(aasId, out IElementContainer<ISubModel> subModels);
                    if (aas != null)
                    {
                        if (subModels != null)
                        {
                            aas.SubModels = new ElementContainer<ISubModel>();
                            foreach (var subModel in subModels)
                            {
                                string serviceUri = string.Join(PATH_SEPERATOR, PATH_SEPERATOR, aasId, ElementInstance.SUBMODELS, subModel.DisplayName).Replace("//", "/");
                                aas.SubModels.Add(new SubModel() { Endpoint = serviceUri });
                            }
                        }
                        return response;
                    }
                }
                aas = null;
                return response;
            }
            aas = null;
            return null;
        }
        public static oneM2MClient.Utils.ResultHandling.Result<Response> ReadSubModel(string aasId, string subModelId, out SubModel subModel)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(subModelId))
            {
                Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, subModelId);
                var result = Helper.ReadResourceTree(oneM2MClient, req, out ObjectTreeBuilder subModelTree);
                subModel = ReadSubModelFrom(aasId, subModelTree);

                return result;
            }
            subModel = null;
            return null;
        }
        public static oneM2MClient.Utils.ResultHandling.Result<Response> ReadSubModels(string aasId, out IElementContainer<ISubModel> subModels)
        {
            if (!string.IsNullOrEmpty(aasId))
            {
                Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId);
                var result = Helper.ReadResourceTree(oneM2MClient, req, out ObjectTreeBuilder resTree);
                if (result.Success && resTree != null && resTree.HasChildren())
                {
                    subModels = new ElementContainer<ISubModel>();
                    foreach (ObjectTreeBuilder child in resTree.Children)
                    {
                        var subModel = ReadSubModelFrom(aasId, child);
                        subModels.Add(subModel);
                    }
                    if (subModels.Count == 0)
                        subModels = null;

                    return result;
                }
                subModels = null;
                return result;
            }
            subModels = null;
            return null;
        }
        public static oneM2MClient.Utils.ResultHandling.Result<Response> ReadProperty(string aasId, string subModelId, string propertyId, out PropertyDescription property)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(subModelId) && !string.IsNullOrEmpty(propertyId))
            {
                Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, subModelId, propertyId);
                var result = Container.Retrieve(oneM2MClient, req);
                property = null;
                if (result.Success && result.Entity.TryGetResource(out cnt prop))
                {
                    property = ConvertCntToProperty(prop);
                    if (property != null)
                    {
                        req.AddPath(ContainerStrings.DATA, DEFAULT_SUBSCRIPTION_NAME);
                        //req.AddPath(ContainerStrings.GET, ContainerStrings.REQUEST, DEFAULT_SUBSCRIPTION_NAME);
                        var listenerUriReq = Subscription.Retrieve(oneM2MClient, req);
                        if (listenerUriReq.Success && listenerUriReq.Entity.TryGetResource(out sub subUri))
                            property.Endpoint = string.Join(ELEMENT_SEPERATOR, subUri.Nu);
                    }
                }
                return result;
            }
            property = null;
            return null;
        }
        public static oneM2MClient.Utils.ResultHandling.Result<Response> ReadPropertyValue(string aasId, string subModelId, string propertyId, out object value)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(subModelId) && !string.IsNullOrEmpty(propertyId))
            {
                Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, subModelId, propertyId, ContainerStrings.DATA);
                var resp = Helper.ReadLatestAddedResource(oneM2MClient, req, out resource data);
                if (resp.Success && data != null && data is cin dataCin)
                    value = dataCin.Con;
                else
                    value = null;
                return resp;
            }
            value = null;
            return null;
        }
        public static oneM2MClient.Utils.ResultHandling.Result<Response> ReadOperation(string aasId, string subModelId, string operationId, out OperationDescription operation)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(subModelId) && !string.IsNullOrEmpty(operationId))
            {
                Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, subModelId, operationId);
                var result = Container.Retrieve(oneM2MClient, req);
                operation = null;
                if (result.Success && result.Entity.TryGetResource(out cnt op))
                {
                    operation = ConvertCntToOperation(op);
                    if (operation != null)
                    {
                        req.AddPath(ContainerStrings.REQUEST, DEFAULT_SUBSCRIPTION_NAME);
                        var listenerUriReq = Subscription.Retrieve(oneM2MClient, req);
                        if (listenerUriReq.Success && listenerUriReq.Entity.TryGetResource(out sub subUri))
                            operation.Endpoint = string.Join(ELEMENT_SEPERATOR, subUri.Nu);
                    }
                }
                return result;
            }
            operation = null;
            return null;
        }
        public static oneM2MClient.Utils.ResultHandling.Result<Response> ReadEvent(string aasId, string subModelId, string eventId, out EventDescription eventable)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(subModelId) && !string.IsNullOrEmpty(eventId))
            {
                Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, subModelId, eventId);
                var result = Container.Retrieve(oneM2MClient, req);
                eventable = null;
                if (result.Success && result.Entity.TryGetResource(out cnt ev))
                {
                    cin schema = null;
                    req.AddPath(ContainerStrings.SCHEMA);
                    var schemaResult = Container.Retrieve(oneM2MClient, req);
                    if (schemaResult.Success && schemaResult.Entity.TryGetResource(out cnt sc))
                    {
                        if (sc.Cni.HasValue && sc.Cni.Value > 0)
                        {
                            schemaResult = Helper.ReadLatestAddedResource(oneM2MClient, req, out resource res);
                            if (res is cin)
                                schema = (cin)res;
                        }
                    }
                    eventable = ConvertCntToEvent(ev, schema);
                }
                return result;
            }
            eventable = null;
            return null;
        }
        #endregion

        #region Remove-Operations
        public static oneM2MClient.Utils.ResultHandling.Result<Response> RemoveComponent(string[] components)
        {
            Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, components);
            var result = ApplicationEntity.Delete(oneM2MClient, req);
            return result;
        }
        public static oneM2MClient.Utils.ResultHandling.Result<Response> RemoveOperation(string aasId, string subModelId, string operationId)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(subModelId) && !string.IsNullOrEmpty(operationId))
            {
                Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, subModelId, operationId);
                var result = Container.Delete(oneM2MClient, req);
                return result;
            }
            return null;
        }
        public static oneM2MClient.Utils.ResultHandling.Result<Response> RemoveEvent(string aasId, string subModelId, string eventId)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(subModelId) && !string.IsNullOrEmpty(eventId))
            {
                Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, subModelId, eventId);
                var result = Container.Delete(oneM2MClient, req);
                return result;
            }
            return null;
        }
        public static oneM2MClient.Utils.ResultHandling.Result<Response> RemoveProperty(string aasId, string subModelId, string propertyId)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(subModelId) && !string.IsNullOrEmpty(propertyId))
            {
                Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, subModelId, propertyId);
                var result = Container.Delete(oneM2MClient, req);
                return result;
            }
            return null;
        }
        #endregion

        #region Event-Operations

        public static oneM2MClient.Utils.ResultHandling.Result<Response> Publish(string aasId, string subModelId, string eventId, IPublishableEvent publishableEvent)
        {
            Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, subModelId, eventId, ContainerStrings.DATA);

            var evResult = ContentInstance.Create(oneM2MClient, req, publishableEvent.Identification.Id, JsonConvert.SerializeObject(publishableEvent));

            return evResult;
        }

        public static oneM2MClient.Utils.ResultHandling.Result<Response> Subscribe(string aasId, string subModelId, string eventId, string subscriberId, string subscriberEndpoint)
        {
            Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, subModelId, eventId, ContainerStrings.DATA);

            req.From(subscriberEndpoint);
            Subscription sub = new Subscription(EventIdentifier.SUBSCRIBER_SUB + subscriberId, new List<string> { subscriberEndpoint }, new List<notificationEventType> { notificationEventType.CreateofDirectChildResource });
            sub.NotificationContentType = notificationContentType.AllAttributes;
            var result = sub.Create(oneM2MClient, req);

            return result;
        }

        public static oneM2MClient.Utils.ResultHandling.Result<Response> Unsubscribe(string aasId, string subModelId, string eventId, string subscriberId)
        {
            Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, subModelId, eventId, ContainerStrings.DATA, EventIdentifier.SUBSCRIBER_SUB + subscriberId);
            var result = Subscription.Delete(oneM2MClient, req);
            return result;
        }
        #endregion

        #endregion

        #region Utility-Functions
        public static string GetLabelValue(string label, List<string> labels, string seperator = SEPERATOR)
        {
            if (!string.IsNullOrEmpty(label) && labels != null)
            {
                string typeDefiniton = labels.FirstOrDefault(l => l.StartsWith(label));
                if (!string.IsNullOrEmpty(typeDefiniton))
                    return typeDefiniton.Split(new string[] { label + seperator }, StringSplitOptions.RemoveEmptyEntries)[0];
            }
            return null;
        }
        private static oneM2MClient.Utils.ResultHandling.Result<Response> CreateRequestConstruct(string[] path, string subscriptionListenerUri)
        {
            Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, path);

            Container.Create(oneM2MClient, req, ContainerStrings.REQUEST, null, null, 8);
            Container.Create(oneM2MClient, req, ContainerStrings.RESPONSE, null, null, 8);
            Container.Create(oneM2MClient, req, ContainerStrings.PROCESSING, null, null, 8);

            req.AddPath(ContainerStrings.REQUEST);
            req.From(subscriptionListenerUri);
            Subscription sub = new Subscription(DEFAULT_SUBSCRIPTION_NAME, new List<string> { subscriptionListenerUri }, new List<notificationEventType> { notificationEventType.CreateofDirectChildResource });
            sub.NotificationContentType = notificationContentType.AllAttributes;
            var result = sub.Create(oneM2MClient, req);
            return result;
        }
        public static DataObjectType GetDataTypeFromString(string dty)
        {
            if (!string.IsNullOrEmpty(dty))
            {
                if (Enum.TryParse(dty.UppercaseFirst(), out DataObjectType dataType))
                    return dataType;
                else
                    return DataObjectType.Object;
            }
            return DataObjectType.None;
        }
        #endregion
    }
}
