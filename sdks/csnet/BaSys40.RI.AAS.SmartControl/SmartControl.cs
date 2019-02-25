using oneM2MClient;
using oneM2MClient.Client;
using oneM2MClient.Resources;
using oneM2MClient.Protocols;


using System;
using System.Linq;
using System.IO;
using System.Reflection;
using System.Collections.Generic;

using BaSys40.API.Platform;
using BaSys40.Models.Core;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Utils.ResultHandling;
using BaSys40.Utils.ModelHandling;

using Newtonsoft.Json;
using BaSys40.Models.Core.AssetAdministrationShell.Implementations;
using BaSys40.Models.Core.AssetAdministrationShell.Enums;
using BaSys40.Models.Core.Identification;
using System.Net;
using BaSys40.Models.Core.AssetAdministrationShell.Implementations.DataElementSubtypes;
using BaSys40.Models.Core.AssetAdministrationShell;
using oneM2MClient.Utils;
using BaSys40.Models.Core.Extensions.References;
using BaSys40.Utils.Settings;
using System.Web;

namespace BaSys40.RI.AAS.SmartControl
{
    public partial class SmartControl : IAssetAdministrationShellManager
    {

        public static SmartControlSettings Settings { get; private set; }

        public static readonly IClient oneM2MClient;
        public static readonly string SettingsPath;

        static SmartControl()
        {
            SettingsPath = Path.Combine(Path.GetDirectoryName(Assembly.GetEntryAssembly().Location), SmartControlSettings.FileName);

            Assembly sourceAssembly = Assembly.GetExecutingAssembly();
            string nameSpace = typeof(SmartControl).Namespace.Split('.')[0];

            if (!ResourceChecker.CheckResource(sourceAssembly, nameSpace, "SmartControlSettings.xml", true))
                throw new FileNotFoundException("SmartControlSettings.xml is missing or has a wrong configuration!");

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

        public IResult UpdateAssetAdministrationShell(string aasId, IAssetAdministrationShell aas)
        {
            var result = UpdateAAS(aasId, aas);
            return ConvertResult(result);
        }

        public IResult DeleteAssetAdministrationShell(string aasId)
        {
            var result = RemoveComponent(new string[] { aasId } );
            return ConvertResult(result);
        }

        public IResult DeleteSubmodel(string aasId, string submodelId)
        {
            var components = new string[] { aasId, submodelId };
            var result = RemoveComponent(components);

            return ConvertResult(result);
        }
        public IResult InvokeOperation(string aasId, string submodelId, string operationId, List<IArgument> inputArguments, List<IArgument> outputArguments, int timeout)
        {
            var result = CallOperation(aasId, submodelId, operationId, inputArguments, outputArguments, timeout);
            return ConvertResult(result);
        }


        public IResult<ISubmodel> CreateSubmodel(string aasId, ISubmodel submodel)
        {
            var result = AddSubmodel(aasId, submodel);
            if(submodel.DataElements != null && submodel.DataElements.Count > 0)
            {
                foreach (var property in submodel.DataElements)
                {
                    CreateDataElement(aasId, submodel.IdShort, property);
                }
            }
            if (submodel.Operations != null && submodel.Operations.Count > 0)
            {
                foreach (var operation in submodel.Operations)
                {
                    CreateOperation(aasId, submodel.IdShort, operation);
                }
            }
            if (submodel.Events != null && submodel.Events.Count > 0)
            {
                foreach (var eventable in submodel.Events)
                {
                    CreateEvent(aasId, submodel.IdShort, eventable);
                }
            }
            if (result.Success)
            {
                return ConvertResult<ISubmodel>(submodel, result);
            }
            else
                return ConvertResult<ISubmodel>(result);
        }

        public IResult<IDataElement> CreateDataElement(string aasId, string submodelId, IDataElement property)
        {
            var result = AddProperty(aasId, submodelId, property);
            if(result.Success)
            {
                return ConvertResult<IDataElement>(property, result);
            }
            return ConvertResult<IDataElement>(result);
        }

        public IResult<IOperation> CreateOperation(string aasId, string submodelId, IOperation operation)
        {
            var result = AddOperation(aasId, submodelId, operation);
            if (result.Success)
            {
                return ConvertResult<IOperation>(operation, result);
            }
            return ConvertResult<IOperation>(result);
        }

        public IResult<IEvent> CreateEvent(string aasId, string submodelId, IEvent eventable)
        {
            var result = AddEvent(aasId, submodelId, eventable);
            if (result.Success)
            {
                return ConvertResult<IEvent>(eventable, result);
            }
            return ConvertResult<IEvent>(eventable, result);
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

        public IResult<ElementContainer<ISubmodel>> RetrieveSubmodels(string aasId)
        {
            var result = ReadSubmodels(aasId, out ElementContainer<ISubmodel> submodels);
            if (result.Success && submodels != null)
            {
                return ConvertResult(submodels, result);
            }
            return ConvertResult<ElementContainer<ISubmodel>>(result);

        }

        public IResult<ISubmodel> RetrieveSubmodel(string aasId, string submodelId)
        {
            var result = ReadSubmodel(aasId, submodelId, out Submodel submodel);
            if (result.Success && submodel != null)
            {
                return ConvertResult<ISubmodel>(submodel, result);
            }
            return ConvertResult<ISubmodel>(result);
        }

        public IResult<IDataElement> RetrieveDataElement(string aasId, string submodelId, string propertyId)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(submodelId) && !string.IsNullOrEmpty(propertyId))
            {
                var result = ReadProperty(aasId, submodelId, propertyId, out Property prop);
                if (result.Success && prop != null)
                {
                    return ConvertResult<IDataElement>(prop, result);
                }
                else
                    return ConvertResult<IDataElement>(result);
            }
            return null;
        }

        public IResult UpdateDataElementValue(string aasId, string submodelId, string propertyId, IValue value)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(submodelId) && !string.IsNullOrEmpty(propertyId))
            {
                var result = UpdatePropertyValue(aasId, submodelId, propertyId, value);
                return ConvertResult(result);
            }
            return null;
        }    
        
        public IResult<IValue> RetrieveDataElementValue(string aasId, string submodelId, string propertyId)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(submodelId) && !string.IsNullOrEmpty(propertyId))
            {
                var valueResult = ReadPropertyValue(aasId, submodelId, propertyId, out IValue value);
                if (valueResult.Success && value != null)
                    return ConvertResult<IValue>(value, valueResult);
                else
                    return ConvertResult<IValue>(valueResult);
            }
            return null;
        }


        public IResult DeleteDataElement(string aasId, string submodelId, string propertyId)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(submodelId) && !string.IsNullOrEmpty(propertyId))
            {
                var result = RemoveProperty(aasId, submodelId, propertyId);
                return ConvertResult(result);
            }
            return null;
        }

        public IResult<IOperation> RetrieveOperation(string aasId, string submodelId, string operationId)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(submodelId) && !string.IsNullOrEmpty(operationId))
            {
                var result = ReadOperation(aasId, submodelId, operationId, out Operation op);
                if (result.Success && op != null)
                {
                    return ConvertResult<IOperation>(op, result);
                }
                else
                    return ConvertResult<IOperation>(result);
            }
            return null;
        }



        public IResult DeleteOperation(string aasId, string submodelId, string operationId)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(submodelId) && !string.IsNullOrEmpty(operationId))
            {
                var result = RemoveOperation(aasId, submodelId, operationId);
                return ConvertResult(result);
            }
            return null;
        }

        

        public IResult<IEvent> RetrieveEvent(string aasId, string submodelId, string eventId)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(submodelId) && !string.IsNullOrEmpty(eventId))
            {
                var result = ReadEvent(aasId, submodelId, eventId, out Event ev);
                if (result.Success && ev != null)
                {
                    return ConvertResult<IEvent>(ev, result);
                }
                else
                    return ConvertResult<IEvent>(result);
            }
            return null;
        }

        public IResult DeleteEvent(string aasId, string submodelId, string eventId)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(submodelId) && !string.IsNullOrEmpty(eventId))
            {
                var result = RemoveEvent(aasId, submodelId, eventId);
                return ConvertResult(result);
            }
            return null;
        }

        public IResult<ElementContainer<IDataElement>> RetrieveDataElements(string aasId, string submodelId)
        {
            var result = ReadSubmodel(aasId, submodelId, out Submodel submodel);
            if (result.Success && submodel?.DataElements != null)
            {
                return ConvertResult(submodel.DataElements, result);
            }
            return ConvertResult<ElementContainer<IDataElement>>(result);
        }

        public IResult<ElementContainer<IOperation>> RetrieveOperations(string aasId, string submodelId)
        {
            var result = ReadSubmodel(aasId, submodelId, out Submodel submodel);
            if (result.Success && submodel?.Operations != null)
            {
                return ConvertResult(submodel.Operations, result);
            }
            return ConvertResult<ElementContainer<IOperation>>(result);
        }

        public IResult<ElementContainer<IEvent>> RetrieveEvents(string aasId, string submodelId)
        {
            var result = ReadSubmodel(aasId, submodelId, out Submodel submodel);
            if (result.Success && submodel?.Events != null)
            {
                return ConvertResult(submodel.Events, result);
            }
            return ConvertResult<ElementContainer<IEvent>>(result);
        }

        #endregion

        #region SmartControl oneM2M-Functions

        public static Result<Response> CallOperation(string aasId, string submodelId, string operationId, List<IArgument> inputArguments, List<IArgument> outputArguments, int timeout = DEFAULT_TIMEOUT)
        {
            if (timeout == 0)
                timeout = DEFAULT_TIMEOUT;

            ReadOperation(aasId, submodelId, operationId, out Operation operation);

            Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, submodelId, operationId, ContainerStrings.REQUEST);
            string argumentString = SmartControlUtils.ConvertStringArguments(inputArguments);
            string resourceName = "cin_"+req.GetHashCode();
            var result = ContentInstance.Create(oneM2MClient, req, resourceName, argumentString);

            req.SetPath(aasId, submodelId, operationId, ContainerStrings.RESPONSE);
            List<IArgument> outArgs = new List<IArgument>();
            bool success = Utils.ResultHandling.Utils.RetryUntilSuccessOrTimeout(
                () => GetResponseFromCall(req, resourceName, outArgs, operation.Out), TimeSpan.FromMilliseconds(timeout), TimeSpan.FromMilliseconds(100));
            outputArguments?.AddRange(outArgs);

            return result;
        }

        private static bool GetResponseFromCall(Request request, string resourceName, List<IArgument> outputArguments, List<IOperationVariable> referenceArguments)
        {
            var resp = Helper.ReadLatestAddedResource(oneM2MClient, request, out resource resource);
            if (resource != null && resource is cin cinResource)
            {
                if (cinResource.Rn == resourceName)
                {
                    List<IArgument> outArgs = SmartControlUtils.ConvertStringArguments(cinResource.Con, referenceArguments);
                    if(outArgs != null)
                        outputArguments.AddRange(outArgs);
                    return true;
                }
            }
            return false;
        }

        public static Result<Response> UpdatePropertyValue(string aasId, string submodelId, string propertyId, IValue value)
        {
            Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, submodelId, propertyId, ContainerStrings.DATA);

            string content = SmartControlUtils.ConvertPropertyValue(value);
            ContentInstance data_cin = new ContentInstance(Guid.NewGuid().ToString(), content);
            var result = data_cin.Create(oneM2MClient, req);
            return result;
        }

        public static Result<Response> UpdateAAS(string aasId, IAssetAdministrationShell aas)
        {
            Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId);
            List<string> labels = null;
            if (aas.MetaData?.Count > 0)
                labels = aas.MetaData.Select(kvp => kvp.Key + SEPERATOR + kvp.Value)?.ToList();
            var result = ApplicationEntity.Update(oneM2MClient, req, aasId, null, labels);
            return result;
        }


        #region Converters

        public static Submodel ReadSubmodelFrom(string assetId, ObjectTreeBuilder submodelTree)
        {
            var rootCnt = submodelTree?.GetValue<cnt>();
            if (rootCnt != null)
            {
                Submodel submodel = new Submodel()
                {
                    IdShort = rootCnt.Rn,
                    SemanticId = new Reference(new GlobalKey(KeyElements.Submodel, KeyType.URI, GetLabelValue(TYPE_IDENTIFIER, rootCnt.Lbl))),
                    Identification = new Identifier(GetLabelValue(Labels.ID, rootCnt.Lbl), KeyType.Custom),
                    Descriptions = GetDescriptionsFromLabels(rootCnt.Lbl)
                };

                var typeInstance = GetLabelValue(BASYS_TYPE_IDENTIFIER, rootCnt.Lbl);
                if (typeInstance == ElementType.SUBMODEL_TYPE)
                    submodel.Kind = Kind.Type;
                else if (typeInstance == ElementInstance.SUBMODEL)
                    submodel.Kind = Kind.Instance;

                if (submodelTree.HasChildren())
                {
                    submodel.Operations = new ElementContainer<IOperation>();
                    submodel.DataElements = new ElementContainer<IDataElement>();
                    submodel.Events = new ElementContainer<IEvent>();

                    foreach (var subElement in submodelTree.Children)
                    {
                        var subElementCnt = subElement.GetValue<cnt>();
                        if (subElementCnt != null)
                        {
                            if (subElementCnt.Lbl.FirstOrDefault(l => l.Contains(BASYS_TYPE_IDENTIFIER + SEPERATOR + ElementInstance.OPERATION)) != null)
                            {
                                var op = ReadOperationFrom(subElement);
                                if (op != null)
                                    submodel.Operations.Add(op);
                            }
                            else if (subElementCnt.Lbl.FirstOrDefault(l => l.Contains(BASYS_TYPE_IDENTIFIER + SEPERATOR + ElementInstance.PROPERTY)) != null)
                            {
                                var prop = ReadPropertyFrom(subElement);
                                if (prop != null)
                                    submodel.DataElements.Add(prop);
                            }
                            else if (subElementCnt.Lbl.FirstOrDefault(l => l.Contains(BASYS_TYPE_IDENTIFIER + SEPERATOR + ElementInstance.EVENT)) != null)
                            {
                                var prop = ReadEventFrom(subElement);
                                if (prop != null)
                                    submodel.Events.Add(prop);
                            }
                        }
                    }
                }
                return submodel;
            }
            return null;
        }

        public static Operation ReadOperationFrom(ObjectTreeBuilder operationTree)
        {
            var operationCnt = operationTree?.GetValue<cnt>();
            Operation operation = ConvertCntToOperation(operationCnt);
            if (operation != null)
            {
                //string listenerUriSubscriptionPath = string.Join(PATH_SEPERATOR, ContainerStrings.REQUEST, DEFAULT_SUBSCRIPTION_NAME);
                //if (operationTree.HasChildPath(listenerUriSubscriptionPath))
                //{
                //    var sub = operationTree.GetChild(listenerUriSubscriptionPath).GetValue<sub>();
                //    var uri = sub?.Nu?.FirstOrDefault();
                //    operation.OperationInformation.Endpoint = uri ?? uri;
                //}
                return operation;
            }
            return null;
        }

        public static Event ReadEventFrom(ObjectTreeBuilder eventTree)
        {
            var eventCnt = eventTree?.GetValue<cnt>();
            if (eventCnt != null)
            {
                cin schemaCin = null;
                string schemaPath = string.Join(PATH_SEPERATOR, ContainerStrings.SCHEMA, EventIdentifier.SCHEMA_CIN);
                if (eventTree.HasChildPath(schemaPath))
                    schemaCin = eventTree.GetChild(schemaPath).GetValue<cin>();
                Event eventable = ConvertCntToEvent(eventCnt, schemaCin);
                return eventable;
            }
            return null;
        }

        public static Property ReadPropertyFrom(ObjectTreeBuilder propertyTree)
        {
            var propertyCnt = propertyTree?.GetValue<cnt>();
            Property property = ConvertCntToProperty(propertyCnt);
            if (property != null)
            {
                //string listenerUriSubscriptionPath = string.Join(PATH_SEPERATOR, ContainerStrings.GET, ContainerStrings.REQUEST, DEFAULT_SUBSCRIPTION_NAME);
                //string listenerUriSubscriptionPath = string.Join(PATH_SEPERATOR, ContainerStrings.DATA, DEFAULT_SUBSCRIPTION_NAME);
                //if (propertyTree.HasChildPath(listenerUriSubscriptionPath))
                //{
                //    var sub = propertyTree.GetChild(listenerUriSubscriptionPath).GetValue<sub>();
                //    var uri = sub?.Nu?.FirstOrDefault();
                //    (property.ElementInformation as ElementInformation).Endpoint = uri ?? uri;
                //}
               
                //string valuePath = string.Join(PATH_SEPERATOR, ContainerStrings.DATA);
                //if (propertyTree.HasChildPath(valuePath) && propertyTree.GetChild(valuePath).HasChildren())
                //{
                //    var latestValue = Helper.GetLatestResource(propertyTree.GetChild(valuePath));
                //    if(latestValue?.Con != null)
                //        property.Value = new ElementValue<string>(latestValue.Con);
                //}
                
                return property;
            }
            return null;
        }

        public static IResult ConvertResult(Result<Response> requestResponse)
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

        public static IResult<T> ConvertResult<T>(Result<Response> requestResponse)
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

        public static IResult<T> ConvertResult<T>(T entity, Result<Response> requestResponse)
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

        private static Property ConvertCntToProperty(cnt propertyCnt)
        {
            if (propertyCnt != null)
            {
                var dataObjectType = GetDataTypeFromString(GetLabelValue(DATATYPE_IDENTIFIER, propertyCnt.Lbl));
                Reference dataTypeDefinition = null;
                if (dataObjectType == DataObjectType.AnyType)
                    dataTypeDefinition = new Reference(new GlobalKey(KeyElements.GlobalReference, KeyType.URI, GetLabelValue(DATATYPE_IDENTIFIER, propertyCnt.Lbl)));

                bool isCollection = false;
                if (propertyCnt.Lbl.Contains(Labels.COLLECTION))
                    isCollection = true;
                               

                Property property = new Property(new DataType(dataObjectType, isCollection, dataTypeDefinition))
                {
                    Descriptions = GetDescriptionsFromLabels(propertyCnt.Lbl),
                    IdShort = GetLabelValue(Labels.ID, propertyCnt.Lbl)
                };

                var semanticReference = GetLabelValue(TYPE_IDENTIFIER, propertyCnt.Lbl);
                if (semanticReference != null)
                    property.SemanticId = new Reference(new GlobalKey(KeyElements.Property, KeyType.URI, semanticReference));

                return property;
            }
            return null;
        }

        public static List<Description> GetDescriptionsFromLabels(List<string> labels)
        {
            var descriptionText = GetLabelValue(Labels.DESCRIPTION, labels);
            if (!string.IsNullOrEmpty(descriptionText))
                return new List<Description>() { new Description("EN", descriptionText) };
            return null;
        }

        private static Operation ConvertCntToOperation(cnt subElementCnt)
        {
            if (subElementCnt != null)
            {
                var dataObjectType = GetDataTypeFromString(GetLabelValue(RETURN_DATATYPE_IDENTIFIER, subElementCnt.Lbl));
                Reference dataTypeDefinition = null;
                if (dataObjectType == DataObjectType.AnyType)
                    dataTypeDefinition = new Reference(new GlobalKey(KeyElements.GlobalReference, KeyType.URI, GetLabelValue(RETURN_DATATYPE_IDENTIFIER, subElementCnt.Lbl)));

                Operation op = new Operation()
                {
                    Descriptions = GetDescriptionsFromLabels(subElementCnt.Lbl),
                    IdShort = GetLabelValue(Labels.ID, subElementCnt.Lbl),
                };

                if (Int32.TryParse(GetLabelValue(ParameterStrings.GetParameterLength(), subElementCnt.Lbl), out int paramLength) && paramLength > 0)
                {
                    op.In = new List<IOperationVariable>(paramLength);
                    for (int i = 0; i < paramLength; i++)
                    {
                        OperationVariable param = new OperationVariable()
                        {
                            IdShort = GetLabelValue(ParameterStrings.GetParameterName(i), subElementCnt.Lbl),
                            DataType = new DataType(GetDataTypeFromString(GetLabelValue(ParameterStrings.GetParameterDataType(i), subElementCnt.Lbl)), false),
                            Index = i
                        };
                        op.In.Add(param);
                    }
                }

                op.Out = new List<IOperationVariable>()
                {
                    new OperationVariable()
                    {
                        Index = 0,
                        DataType = new DataType(dataObjectType, false, dataTypeDefinition)                        
                    }
                };
                return op;
            }
            return null;
        }

        private static Event ConvertCntToEvent(cnt subElementCnt, cin schema)
        {
            if (subElementCnt != null)
            {
                Event ev = new Event()
                {
                    Descriptions = GetDescriptionsFromLabels(subElementCnt.Lbl),
                    IdShort = GetLabelValue(Labels.ID, subElementCnt.Lbl),                   
                    Category = GetLabelValue(EventIdentifier.EVENT_CATEGORY, subElementCnt.Lbl),
                };

                if(schema != null)
                {
                    SchemaType schemaType = (SchemaType)Enum.Parse(typeof(SchemaType), GetLabelValue(EventIdentifier.SCHEMA_TYPE, schema.Lbl));
                    string schemaString = schema.Con;
                    ev.DataType = new DataType(null, schemaType, schemaString);
                }
                return ev;
            }
            return null;
        }

        public static IAssetAdministrationShell ConvertAeResourceToAAS(ae aasAe)
        {
            if (aasAe != null)
            {
                var aas = new AssetAdministrationShell()
                {
                    Identification = new Identifier(GetLabelValue(Labels.UUID, aasAe.Lbl), KeyType.Custom),
                    IdShort = GetLabelValue(Labels.ID, aasAe.Lbl),
                    Asset =
                        new Asset()
                        {
                            Identification = new Identifier(GetLabelValue(AssetLabels.ASSET_UUID, aasAe.Lbl), KeyType.Custom),
                            IdShort = GetLabelValue(AssetLabels.ASSET_ID, aasAe.Lbl),
                            Kind = GetLabelValue(AssetLabels.ASSET_KIND, aasAe.Lbl).Contains(((int)Kind.Type).ToString()) ? Kind.Type : Kind.Instance,
                            SemanticId = new Reference(new GlobalKey(KeyElements.Asset, KeyType.URI, GetLabelValue(AssetLabels.ASSET_TYPE_DEFINITION, aasAe.Lbl)))
                        },
                    Descriptions = GetDescriptionsFromLabels(aasAe.Lbl),

                };
                return aas;
            }
            return null;
        }

        #endregion  

        public IAssetAdministrationShell ConvertAASDescriptorToAAS(AssetAdministrationShellDescriptor aasDesc)
        {
            if (aasDesc == null)
                return null;

            var aas = new AssetAdministrationShell()
            {
                Asset = aasDesc.Asset,
                Identification = aasDesc.Identification,
                IdShort = aasDesc.IdShort,
                MetaData = aasDesc.MetaData,
                Administration = aasDesc.Administration,
                Category = aasDesc.Category,
                Descriptions = aasDesc.Descriptions
            };

            if (aasDesc.Submodels?.Count > 0)
            {
                aas.Submodels = new ElementContainer<ISubmodel>();
                foreach (var submodel in aasDesc.Submodels)
                {
                    var convertedSubmodel = ConvertSubmodelDescriptorToSubmodel(submodel);
                    if (convertedSubmodel != null)
                        aas.Submodels.Add(convertedSubmodel);
                }
            }
            return aas;
        }

        public ISubmodel ConvertSubmodelDescriptorToSubmodel(SubmodelDescriptor submodelDesc)
        {
            if (submodelDesc == null)
                return null;

            var submodel = new Submodel()
            {
                Identification = submodelDesc.Identification,
                MetaData = submodelDesc.MetaData,
                Administration = submodelDesc.Administration,
                IdShort = submodelDesc.IdShort,
                Category = submodelDesc.Category,
                Descriptions = submodelDesc.Descriptions,
                SemanticId = submodelDesc.SemanticId,
                Kind = submodelDesc.Kind
            };

            return submodel;
        }

        #region Add-Operations
        public static Result<Response> AddAAS(IAssetAdministrationShell aas)
        {
            if (aas == null || aas.Identification == null || aas.IdShort == null || aas.Asset == null || aas.Asset.Identification == null || aas.Asset.IdShort == null)
                return new Result<Response>(new ArgumentNullException());

            Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName);

            List<string> labels = new List<string>();

            if (aas.Asset.Kind.HasValue)
            {
                if (aas.Asset.Kind.Value == Kind.Type)
                {
                    labels.Add(AssetLabels.ASSET_KIND + SEPERATOR + TYPE);
                    labels.Add(BASYS_TYPE_IDENTIFIER + SEPERATOR + ElementType.AAS_TYPE);
                }
                else
                {
                    labels.Add(AssetLabels.ASSET_KIND + SEPERATOR + INSTANCE);
                    labels.Add(BASYS_TYPE_IDENTIFIER + SEPERATOR + ElementInstance.AAS);
                }
            }
            else
            {
                labels.Add(AssetLabels.ASSET_KIND + SEPERATOR + INSTANCE);
                labels.Add(BASYS_TYPE_IDENTIFIER + SEPERATOR + ElementInstance.AAS);
            }

            labels.Add(Labels.ID + SEPERATOR + aas.IdShort);
            labels.Add(Labels.UUID + SEPERATOR + aas.Identification.Id);
            labels.Add(Labels.DISPLAY_NAME + SEPERATOR + aas.IdShort);

            labels.Add(AssetLabels.ASSET_ID + SEPERATOR + aas.Asset.IdShort);
            labels.Add(AssetLabels.ASSET_UUID + SEPERATOR + aas.Asset.Identification.Id);

            if (aas.Asset.SemanticId?.Keys?.Count > 0)
                labels.Add(AssetLabels.ASSET_TYPE_DEFINITION + SEPERATOR + aas.Asset.SemanticId.Keys.First().Value);

            if (aas.Descriptions?.Count > 0)
                labels.Add(Labels.DESCRIPTION + SEPERATOR + aas.Descriptions.First().Text);

            ApplicationEntity ae = new ApplicationEntity(aas.IdShort, aas.Identification.Id, true, aas.IdShort, null, labels);
            var result = ae.Create(oneM2MClient, req);
            return result;
        }
        public static Result<Response> AddProperty(string aasId, string submodelId, IDataElement property)
        {
            Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, submodelId);
            var labels = new List<string>
            {
                BASYS_TYPE_IDENTIFIER + SEPERATOR + ElementInstance.PROPERTY,
            };

            string dty = string.Empty;
            if (property.ModelType == ModelType.DataElementCollection || property.ModelType == ModelType.SubmodelElementCollection)
            {
                labels.Add(Labels.COLLECTION);
                labels.Add(DATATYPE_IDENTIFIER + SEPERATOR + "string");
            }
            else
            {
                if (property.ValueType.DataObjectType == DataObjectType.AnyType && property.ValueType.SemanticId?.Keys?.Count > 0)
                    dty = property.ValueType.SemanticId.Keys.First().Value;
                else
                    dty = SmartControlUtils.ConvertDataTypeNames(property.ValueType);
                labels.Add(DATATYPE_IDENTIFIER + SEPERATOR + dty);
            }

            //if (property.DataElementInformation.Readable.HasValue && property.DataElementInformation.Readable.Value)
                labels.Add(Labels.READABLE);
            //if (property.DataElementInformation.Writable.HasValue && property.DataElementInformation.Writable.Value)
                labels.Add(Labels.WRITABLE);
            //if (property.DataElementInformation.Eventable.HasValue && property.DataElementInformation.Eventable.Value)
                labels.Add(Labels.EVENTABLE);

            if (property.SemanticId?.Keys?.Count > 0)
                labels.Add(TYPE_IDENTIFIER + SEPERATOR + property.SemanticId.Keys.First().Value);

            if (!string.IsNullOrEmpty(property.IdShort))
                labels.Add(Labels.ID + SEPERATOR + property.IdShort);
            if (property.Descriptions?.Count > 0)
                labels.Add(Labels.DESCRIPTION + SEPERATOR + property.Descriptions.First().Text);

            Container cont = new Container(property.IdShort, null, labels, 8);
            cont.Create(oneM2MClient, req);

            req.AddPath(property.IdShort);
            Container.Create(oneM2MClient, req, ContainerStrings.DATA, null, null, 8);

            if (property.Value != null)
                UpdatePropertyValue(aasId, submodelId, property.IdShort, new DataElementValue(property.Value, property.ValueType));

            req.AddPath(ContainerStrings.DATA);
            var result = CreateSubscription(req, Settings.CallbackEndpointUrl);

            return result;
        }

        public static Result<Response> CreateSubscription(Request req, string subscriptionUri)
        {
            req.From(subscriptionUri);
            Subscription sub = new Subscription(DEFAULT_SUBSCRIPTION_NAME, new List<string> { subscriptionUri }, new List<notificationEventType> { notificationEventType.CreateofDirectChildResource });
            sub.NotificationContentType = notificationContentType.AllAttributes;
            var result = sub.Create(oneM2MClient, req);
            return result;
        }

        public static Result<Response> AddEvent(string aasId, string submodelId, IEvent eventDescription)
        {
            Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, submodelId);

            var labels = new List<string>
            {
                BASYS_TYPE_IDENTIFIER + SEPERATOR + ElementInstance.EVENT,              
                EventIdentifier.EVENT_CATEGORY  + SEPERATOR + eventDescription.Category,
                DATATYPE_IDENTIFIER + SEPERATOR + "void"
            };
            //if (!string.IsNullOrEmpty(eventDescription.Identification.Id))
               //labels.Add(Labels.UUID + SEPERATOR + eventDescription.Identification.Id);
            if (!string.IsNullOrEmpty(eventDescription.IdShort))
                labels.Add(Labels.ID + SEPERATOR + eventDescription.IdShort);
            if (eventDescription.Descriptions?.Count > 0)
                labels.Add(Labels.DESCRIPTION + SEPERATOR + eventDescription.Descriptions.First().Text);

            Container.Create(oneM2MClient, req, eventDescription.IdShort, null, labels, 1);

            req.AddPath(eventDescription.IdShort);

            var result = Container.Create(oneM2MClient, req, ContainerStrings.DATA, null, null, 8);

            req.AddPath(ContainerStrings.DATA);
            req.From(DEFAULT_SUBSCRIPTION_URI);
            Subscription sub = new Subscription(DEFAULT_SUBSCRIPTION_NAME, new List<string> { DEFAULT_SUBSCRIPTION_URI }, new List<notificationEventType> { notificationEventType.CreateofDirectChildResource });
            sub.NotificationContentType = notificationContentType.AllAttributes;
            result = sub.Create(oneM2MClient, req);
            

            if (eventDescription.DataType != null && !string.IsNullOrEmpty(eventDescription.DataType.Schema) && eventDescription.DataType.SchemaType.HasValue)
            {
                req.From(DEFAULT_FROM);
                Container.Create(oneM2MClient, req, ContainerStrings.SCHEMA, null, null, 1);
                req.SetPath(aasId, submodelId, eventDescription.IdShort, ContainerStrings.SCHEMA);

                var schemaLabels = new List<string>();
                schemaLabels.Add(EventIdentifier.SCHEMA_TYPE + SEPERATOR + Enum.GetName(typeof(SchemaType), eventDescription.DataType.SchemaType.Value));
                ContentInstance.Create(oneM2MClient, req, EventIdentifier.SCHEMA_CIN, eventDescription.DataType.Schema, null, schemaLabels);
            }
            
            return result;
        }
        public static Result<Response> AddOperation(string aasId, string submodelId, IOperation operation)
        {
            Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, submodelId);

            var labels = new List<string>
            {
                BASYS_TYPE_IDENTIFIER + SEPERATOR + ElementInstance.OPERATION
            };

            if (operation.In != null)
            {
                labels.Add(ParameterStrings.GetParameterLength() + SEPERATOR + operation.In.Count);

                for (int i = 0; i < operation.In.Count; i++)
                {
                    labels.Add(ParameterStrings.GetParameterName(i) + SEPERATOR + operation.In[i].IdShort);
                    labels.Add(ParameterStrings.GetParameterDataType(i) + SEPERATOR + SmartControlUtils.ConvertDataTypeNames(operation.In[i].DataType));
                }
            }
            else
                labels.Add(ParameterStrings.GetParameterLength() + SEPERATOR + 0);

            if (operation.Out?.Count == 1)
                labels.Add(RETURN_DATATYPE_IDENTIFIER + SEPERATOR + SmartControlUtils.ConvertDataTypeNames(operation.Out[0].DataType));
            else
                labels.Add(RETURN_DATATYPE_IDENTIFIER + SEPERATOR + "void");


            if (!string.IsNullOrEmpty(operation.IdShort))
                labels.Add(Labels.ID + SEPERATOR + operation.IdShort);
            if (operation.Descriptions?.Count > 0)
                labels.Add(Labels.DESCRIPTION + SEPERATOR + operation.Descriptions.First().Text);

            Container cont = new Container(operation.IdShort, null, labels);
            var result = cont.Create(oneM2MClient, req);

            CreateRequestConstruct(new string[] { aasId, submodelId, operation.IdShort }, Settings.CallbackEndpointUrl);

            return result;
        }
        public static Result<Response> AddSubmodel(string aasId, ISubmodel submodel)
        {
            Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId);

            var labels = new List<string>
            {
                BASYS_TYPE_IDENTIFIER + SEPERATOR + ElementInstance.SUBMODEL               
            };

            if (submodel.SemanticId?.Keys?.Count > 0)
                labels.Add(TYPE_IDENTIFIER + SEPERATOR + submodel.SemanticId.Keys.First().Value);
            else
                labels.Add(TYPE_IDENTIFIER + SEPERATOR + submodel.IdShort);

            if (submodel.Kind.HasValue)
            {
                if (submodel.Kind.Value == Kind.Instance)
                    labels.Add(AssetLabels.ASSET_KIND + SEPERATOR + INSTANCE);
                else
                    labels.Add(AssetLabels.ASSET_KIND + SEPERATOR + TYPE);
            }

            if (!string.IsNullOrEmpty(submodel.Identification.Id))
                labels.Add(Labels.UUID + SEPERATOR + submodel.Identification.Id);
            if (!string.IsNullOrEmpty(submodel.IdShort))
                labels.Add(Labels.ID + SEPERATOR + submodel.IdShort);
            if (submodel.Descriptions?.Count > 0)
                labels.Add(Labels.DESCRIPTION + SEPERATOR + submodel.Descriptions.First().Text);

            Container cont = new Container(submodel.IdShort, null, labels);
            var result = cont.Create(oneM2MClient, req);
            return result;
        }
        #endregion

        #region Read-Operations
        public static Result<Response> ReadAllAAS(out List<IAssetAdministrationShell> aasList)
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
                            ReadSubmodels(aas.IdShort, out ElementContainer<ISubmodel> submodels);
                            if (submodels?.Count > 0)
                            {
                                aas.Submodels = new ElementContainer<ISubmodel>();
                                foreach (var submodel in submodels)
                                {
                                    //string serviceUri = string.Join(PATH_SEPERATOR, PATH_SEPERATOR, aas.IdShort, ElementInstance.SUBMODELS, submodel.IdShort).Replace("//", "/");
                                    aas.Submodels.Add(submodel);
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
        public static Result<Response> ReadAAS(string aasId, out IAssetAdministrationShell aas)
        {
            if (!string.IsNullOrEmpty(aasId))
            {
                Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName);
                var response = Helper.ReadResourcesByLabels(oneM2MClient, req, new List<string> { Labels.ID + SEPERATOR + aasId }, out List<resource> resources);
                if (response.Success && resources != null
                    && resources.FirstOrDefault(r => r.Ty.Value == (int)resourceType.AE) != null)
                {
                    aas = ConvertAeResourceToAAS((ae)resources.FirstOrDefault());
                    ReadSubmodels(aasId, out ElementContainer<ISubmodel> submodels);
                    if (aas != null)
                    {
                        if (submodels != null)
                        {
                            aas.Submodels = new ElementContainer<ISubmodel>();
                            foreach (var submodel in submodels)
                            {
                                //string serviceUri = string.Join(PATH_SEPERATOR, PATH_SEPERATOR, aasId, ElementInstance.SUBMODELS, submodel.IdShort).Replace("//", "/");
                                aas.Submodels.Add(submodel);
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
        public static Result<Response> ReadSubmodel(string aasId, string submodelId, out Submodel submodel)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(submodelId))
            {
                Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, submodelId);
                var result = Helper.ReadResourceTree(oneM2MClient, req, out ObjectTreeBuilder submodelTree);
                submodel = ReadSubmodelFrom(aasId, submodelTree);

                return result;
            }
            submodel = null;
            return null;
        }
        public static Result<Response> ReadSubmodels(string aasId, out ElementContainer<ISubmodel> submodels)
        {
            if (!string.IsNullOrEmpty(aasId))
            {
                Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId);
                var result = Helper.ReadResourceTree(oneM2MClient, req, out ObjectTreeBuilder resTree);
                if (result.Success && resTree != null && resTree.HasChildren())
                {
                    submodels = new ElementContainer<ISubmodel>();
                    foreach (ObjectTreeBuilder child in resTree.Children)
                    {
                        var submodel = ReadSubmodelFrom(aasId, child);
                        submodels.Add(submodel);
                    }
                    if (submodels.Count == 0)
                        submodels = null;

                    return result;
                }
                submodels = null;
                return result;
            }
            submodels = null;
            return null;
        }
        public static Result<Response> ReadProperty(string aasId, string submodelId, string propertyId, out Property property)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(submodelId) && !string.IsNullOrEmpty(propertyId))
            {
                Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, submodelId, propertyId);
                var result = Container.Retrieve(oneM2MClient, req);
                property = null;
                if (result.Success && result.Entity.TryGetResource(out cnt prop))
                {
                    property = ConvertCntToProperty(prop);
                    //if (property != null)
                    //{
                    //    req.AddPath(ContainerStrings.DATA, DEFAULT_SUBSCRIPTION_NAME);
                    //    //req.AddPath(ContainerStrings.GET, ContainerStrings.REQUEST, DEFAULT_SUBSCRIPTION_NAME);
                    //    var listenerUriReq = Subscription.Retrieve(oneM2MClient, req);
                    //    if (listenerUriReq.Success && listenerUriReq.Entity.TryGetResource(out sub subUri))
                    //        (property.ElementInformation as ElementInformation).Endpoint = string.Join(ELEMENT_SEPERATOR, subUri.Nu);
                    //}
                }
                return result;
            }
            property = null;
            return null;
        }
        public static Result<Response> ReadPropertyValue(string aasId, string submodelId, string propertyId, out IValue value)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(submodelId) && !string.IsNullOrEmpty(propertyId))
            {
                ReadProperty(aasId, submodelId, propertyId, out Property property);
                Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, submodelId, propertyId, ContainerStrings.DATA);
                var resp = Helper.ReadLatestAddedResource(oneM2MClient, req, out resource data);
                if (resp.Success && data != null && data is cin dataCin)
                    value = SmartControlUtils.ConvertPropertyValue(dataCin.Con, property.ValueType);
                else
                    value = null;
                return resp;
            }
            value = null;
            return null;
        }
        public static Result<Response> ReadOperation(string aasId, string submodelId, string operationId, out Operation operation)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(submodelId) && !string.IsNullOrEmpty(operationId))
            {
                Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, submodelId, operationId);
                var result = Container.Retrieve(oneM2MClient, req);
                operation = null;
                if (result.Success && result.Entity.TryGetResource(out cnt op))
                {
                    operation = ConvertCntToOperation(op);
                    //if (operation != null)
                    //{
                    //    req.AddPath(ContainerStrings.REQUEST, DEFAULT_SUBSCRIPTION_NAME);
                    //    var listenerUriReq = Subscription.Retrieve(oneM2MClient, req);
                    //    if (listenerUriReq.Success && listenerUriReq.Entity.TryGetResource(out sub subUri))
                    //        operation.OperationInformation.Endpoint = string.Join(ELEMENT_SEPERATOR, subUri.Nu);
                    //}
                }
                return result;
            }
            operation = null;
            return null;
        }
        public static Result<Response> ReadEvent(string aasId, string submodelId, string eventId, out Event eventable)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(submodelId) && !string.IsNullOrEmpty(eventId))
            {
                Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, submodelId, eventId);
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
        public static Result<Response> RemoveComponent(string[] components)
        {
            Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, components);
            var result = ApplicationEntity.Delete(oneM2MClient, req);
            return result;
        }
        public static Result<Response> RemoveOperation(string aasId, string submodelId, string operationId)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(submodelId) && !string.IsNullOrEmpty(operationId))
            {
                Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, submodelId, operationId);
                var result = Container.Delete(oneM2MClient, req);
                return result;
            }
            return null;
        }
        public static Result<Response> RemoveEvent(string aasId, string submodelId, string eventId)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(submodelId) && !string.IsNullOrEmpty(eventId))
            {
                Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, submodelId, eventId);
                var result = Container.Delete(oneM2MClient, req);
                return result;
            }
            return null;
        }
        public static Result<Response> RemoveProperty(string aasId, string submodelId, string propertyId)
        {
            if (!string.IsNullOrEmpty(aasId) && !string.IsNullOrEmpty(submodelId) && !string.IsNullOrEmpty(propertyId))
            {
                Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, submodelId, propertyId);
                var result = Container.Delete(oneM2MClient, req);
                return result;
            }
            return null;
        }
        #endregion

        #region Event-Operations

        public static Result<Response> Publish(string aasId, string submodelId, string eventId, IPublishableEvent publishableEvent)
        {
            Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, submodelId, eventId, ContainerStrings.DATA);

            var evResult = ContentInstance.Create(oneM2MClient, req, publishableEvent.IdShort, JsonConvert.SerializeObject(publishableEvent));

            return evResult;
        }

        public static Result<Response> Subscribe(string aasId, string submodelId, string eventId, string subscriberId, string subscriberEndpoint)
        {
            Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, submodelId, eventId, ContainerStrings.DATA);

            req.From(subscriberEndpoint);
            Subscription sub = new Subscription(EventIdentifier.SUBSCRIBER_SUB + subscriberId, new List<string> { subscriberEndpoint }, new List<notificationEventType> { notificationEventType.CreateofDirectChildResource });
            sub.NotificationContentType = notificationContentType.AllAttributes;
            var result = sub.Create(oneM2MClient, req);

            return result;
        }

        public static Result<Response> Unsubscribe(string aasId, string submodelId, string eventId, string subscriberId)
        {
            Request req = RequestFactory.CreateRequest(oneM2MClient, Settings.oneM2MConfig.ClientId, Settings.oneM2MConfig.Endpoint, Settings.oneM2MConfig.CSEName, aasId, submodelId, eventId, ContainerStrings.DATA, EventIdentifier.SUBSCRIBER_SUB + subscriberId);
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
        private static Result<Response> CreateRequestConstruct(string[] path, string subscriptionListenerUri)
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
                if (DataObjectType.TryParse(dty, out DataObjectType dataType))
                    return dataType;
                else
                    return DataObjectType.AnyType;
            }
            return DataObjectType.None;
        }

      
        #endregion
    }
}
