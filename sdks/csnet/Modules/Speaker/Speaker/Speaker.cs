using BaSys40.API.AssetAdministrationShell;
using BaSys40.API.Components;
using BaSys40.Models.Core;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.AssetAdministrationShell.Implementations;
using BaSys40.Utils.ResultHandling;
using System.Collections.Generic;
using System.Globalization;
using System.Speech.Synthesis;
using Speaker.Events;
using BaSys40.Models.Core.Identification;
using BaSys40.Models.Core.AssetAdministrationShell.Enums;
using BaSys40.API.Platform;
using BaSys40.API.AssetAdministrationShell.Connectables;

namespace Speaker
{
    public partial class Speaker : IComponentConnector
    {
        private readonly SpeechSynthesizer speaker;
        private IConnectableProperty lastWordsConnected;
        private Dictionary<string, IConnectableEvent> Events;

        public string LastWords { get; set; }

        public Speaker() : this (1, 100)
        { }

        public Speaker(int rate, int volume)
        {
            //configure speech synthesizer
            speaker = new SpeechSynthesizer
            {
                Rate = rate,
                Volume = volume                
            };
 
            SaySomething("Hello World", "en-EN");
        }

        public IResult Register(IAssetAdministrationShellRegistry registry, IAssetAdministrationShell aas)
        {
            var createResult = registry.CreateAssetAdministrationShell(aas);
            return createResult;
        }

        public IResult CreateStructure(IAssetAdministrationShellManager manager, IAssetAdministrationShellHandler handler, IAssetAdministrationShell aas)
        {
            var alreadyExists = manager.RetrieveAssetAdministrationShell(aas.Identification.Id);
            if (!alreadyExists.Success || alreadyExists.Entity == null)
            {
                var created = manager.CreateAssetAdministrationShell(aas);
                if (!created.Success)
                    return new Result(created.Success, created.Messages);
            }

            var cAAS = new ConnectedAssetAdministrationShell(manager, aas);
            if (cAAS != null)
            {
                bool successForAll = (aas.SubModels as List<ISubModel>).TrueForAll(s => cAAS.CreateSubModel(s).Success);
                if (!successForAll)
                    return new Result(false, new List<IMessage>() { new Message(MessageType.Error, "Could not create SubModels") });

                var subModel = cAAS.RetrieveSubModel(aas.SubModels[0].Identification.Id).Entity;
                var cSubModel = new ConnectedSubModel(manager, aas, subModel);
                if (cSubModel != null)
                {
                    var operation = cSubModel.RetrieveOperation(aas.SubModels[0].Operations[0].Identification.Id).Entity;
                    var cOp = new ConnectedOperation(manager, aas, subModel, operation);
                    if (cOp != null)
                        handler.RegisterMethodCalledEventHandler(cSubModel, cOp, SaySomethingHandler);

                    var property = cSubModel.RetrieveProperty(aas.SubModels[0].Properties[0].Identification.Id).Entity;
                    var cProp = new ConnectedProperty(manager, aas, subModel, property);
                    if (cProp != null)
                    {
                        lastWordsConnected = cProp;
                        handler.RegisterGetPropertyValueHandler(cSubModel, cProp, LastWordsGetHandler);
                        handler.RegisterSetPropertyValueHandler(cSubModel, cProp, LastWordsSetHandler);
                    }

                    Events = new Dictionary<string, IConnectableEvent>();
                    foreach (var eventable in aas.SubModels[0].Events)
                    {
                        var @event = cSubModel.RetrieveEvent(eventable.Identification.Id).Entity;
                        var cEv = new ConnectedEvent(manager, aas, subModel, @event);
                        if (cEv != null)
                        {
                            Events.Add(eventable.Identification.Id, cEv);
                        }
                    }
                    SendEvent(Events["StartupCompleted"], new StartupCompleted());
                }
                return new Result(true);
            }
            return new Result(false);
        }

        private void LastWordsSetHandler(IConnectableProperty property, IValue value)
        {
            LastWords = value.ToObject<string>();
        }

        private IValue LastWordsGetHandler(IConnectableProperty property)
        {
            var value = new ElementValue<string>(LastWords);
            return value;
        }

        public void SendEvent(IConnectableEvent connectableEvent, IPublishableEvent publishableEvent)
        {
            connectableEvent.Publish(publishableEvent, 2);
        }

        public IResult Unregister(IAssetAdministrationShellRegistry registry, string aasId)
        {
            var result = registry.DeleteAssetAdministrationShell(aasId);
            return result;
        }

        private OperationResult SaySomethingHandler(IConnectableOperation operation, List<IArgument> inputArguments, out List<IArgument> outputArguments)
        {
            if (inputArguments != null && inputArguments.Count >= 2)
            {
                var answer = SaySomething(inputArguments[0].Value.ToObject<string>(), inputArguments[1].Value.ToObject<string>());
                SendEvent(Events["Spoken"], new Spoken() { Message = inputArguments[0].Value.ToObject<string>() });
                outputArguments = new List<IArgument>();
                outputArguments.Add(new Argument() { DataType = new DataType(DataObjectType.String, false, false), Value = new ElementValue<string>(answer) });

                if(!string.IsNullOrEmpty(answer))
                    return new OperationResult(true);
                else
                    return new OperationResult(false, new List<IMessage> { new Message(MessageType.Error, "The answer is null or empty") });

            }
            outputArguments = null;
            return new OperationResult(false, new List<IMessage> { new Message(MessageType.Error, "Input arguments are null or less than two") });
        }

        public IAssetAdministrationShell GenerateAssetAdministrationShell()
        {
            var aas = new AssetAdministrationShell()
            {
                Assets = new ElementContainer<IAsset>()
                {
                    new Asset()
                    {
                        AssetKind = Kind.Instance,
                        Identification = new Identifier("Microsoft_SAM_123", Identificator.Internal)
                    }
                },
                SemanticReference = new Identifier("com.microsoft.im.SpeechRecognition", Identificator.Internal),
                DisplayName = "Microsoft_SAM",
                Identification = new Identifier("Microsoft_SAM", Identificator.Internal)
            };
           
            var speakerSubModel = new SubModel()
            {
                Identification = new Identifier("speaker", Identificator.Internal),
                SemanticReference = new Identifier("com.microsoft.fb.modules.speakerModules", Identificator.Internal),
                DisplayName = "speaker",
                SubModelKind = Kind.Instance,
                Parent = aas
            };

            speakerSubModel.Properties = new ElementContainer<IPropertyDescription>
            {
                new PropertyDescription()
                {
                    Identification = new Identifier("LastWords", Identificator.Internal),
                    DisplayName = "LastWords",
                    DataType = new DataType(DataObjectType.String, false, false),
                    Writable = true,
                    Readable = true,
                    Eventable = true,
                    Endpoint = "http://127.0.0.1:8544",
                    Parent = speakerSubModel
                }
            };

            speakerSubModel.Events = new ElementContainer<IEventDescription>
            {
                new EventDescription()
                {
                    Identification = new Identifier("StartupCompleted", Identificator.Internal),
                    DisplayName = "StartupCompleted",
                    Parent = speakerSubModel,
                    EntityType = EntityType.Primitive,
                    EventCategory = "StatusEvents",
                    EventName = "StartupCompleted"
                },
                new EventDescription()
                {
                    Identification = new Identifier("Spoken", Identificator.Internal),
                    DisplayName = "Spoken",
                    Parent = speakerSubModel,
                    EntityType = EntityType.Primitive,
                    EventCategory = "RuntimeEvents",
                    EventName = "Spoken",
                }
            };

            speakerSubModel.Operations = new ElementContainer<IOperationDescription>
            {
                new OperationDescription()
                {
                    Identification = new Identifier("SaySomething", Identificator.Internal),
                    DisplayName = "SaySomething",
                    Endpoint = "http://127.0.0.1:8544",
                    InputParameters = new List<IParameter>()
                    {
                        new Parameter() { Index = 0, DataType = new DataType(DataObjectType.String, false, false), ParameterName = "Text" },
                        new Parameter() { Index = 1, DataType = new DataType(DataObjectType.String, false, false), ParameterName = "Language"}
                    },
                    OutputParameters = new List<IParameter>()
                    {
                        new Parameter() { Index = 0, DataType =  new DataType(DataObjectType.String, false, false) }
                    },
                    Parent = speakerSubModel
                }
            };
            aas.SubModels = new ElementContainer<ISubModel>();
            aas.SubModels.Add(speakerSubModel);

            return aas;
        }

        public string SaySomething(string text, string language)
        {
            PromptBuilder prompBuilder = null;
            CultureInfo info = null;
            if (!string.IsNullOrEmpty(language))
            {
                try
                {
                    info = CultureInfo.CreateSpecificCulture(language);
                }
                catch
                {
                    info = CultureInfo.CurrentCulture;
                }
            }
            else
            {
                info = CultureInfo.CurrentCulture;
            }

            prompBuilder = new PromptBuilder(info);
            prompBuilder.AppendText(text);
            speaker.SpeakAsync(new Prompt(prompBuilder));
            switch (language)
            {
                case "de-DE": language = "German";
                    break;
                case "en-US":
                case "en-EN": language = "English";
                    break;
                default:
                    language = "Esperando";
                    break;
            }
            string answer = "I said '" + text + "' in " + language;

            LastWords = answer;
            lastWordsConnected?.SetRemoteValue(new ElementValue<string>(LastWords));

            return answer;
        }        
    }
}
