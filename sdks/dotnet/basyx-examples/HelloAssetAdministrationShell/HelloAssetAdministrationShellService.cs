/*******************************************************************************
* Copyright (c) 2020 Robert Bosch GmbH
* Author: Constantin Ziesche (constantin.ziesche@bosch.com)
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License 1.0 which is available at
* https://www.eclipse.org/org/documents/edl-v10.html
*
* 
*******************************************************************************/
using BaSyx.API.AssetAdministrationShell;
using BaSyx.API.Components;
using BaSyx.Models.Connectivity;
using BaSyx.Models.Core.AssetAdministrationShell.Enums;
using BaSyx.Models.Core.AssetAdministrationShell.Generics;
using BaSyx.Models.Core.AssetAdministrationShell.Generics.SubmodelElementTypes;
using BaSyx.Models.Core.AssetAdministrationShell.Identification;
using BaSyx.Models.Core.AssetAdministrationShell.Implementations;
using BaSyx.Models.Core.AssetAdministrationShell.Implementations.SubmodelElementTypes;
using BaSyx.Models.Core.AssetAdministrationShell.References;
using BaSyx.Models.Core.AssetAdministrationShell.Semantics;
using BaSyx.Models.Core.Common;
using BaSyx.Models.Extensions;
using BaSyx.Models.Extensions.Semantics.DataSpecifications;
using BaSyx.Utils.Client;
using BaSyx.Utils.Client.Mqtt;
using BaSyx.Utils.ResultHandling;
using System;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;

namespace HelloAssetAdministrationShell
{
    public class HelloAssetAdministrationShellService : AssetAdministrationShellServiceProvider
    {
        public override IAssetAdministrationShell AssetAdministrationShell { get; protected set; }

        private SubmodelServiceProvider helloSubmodelServiceProvider;
        private SubmodelServiceProvider assetIdentificationSubmodelProvider;

        private IMessageClient messageClient;
        private CancellationTokenSource cancellationToken;

        public HelloAssetAdministrationShellService()
        {
            helloSubmodelServiceProvider = new SubmodelServiceProvider();
            helloSubmodelServiceProvider.BindTo(AssetAdministrationShell.Submodels["HelloSubmodel"]);
            helloSubmodelServiceProvider.RegisterMethodCalledHandler("HelloOperation", HelloOperationHandler);
            helloSubmodelServiceProvider.RegisterPropertyHandler("HelloProperty",
                new PropertyHandler(HelloPropertyGetHandler, HelloPropertySetHandler));
            helloSubmodelServiceProvider.ConfigureEventHandler(messageClient);
            this.RegisterSubmodelServiceProvider("HelloSubmodel", helloSubmodelServiceProvider);

            assetIdentificationSubmodelProvider = new SubmodelServiceProvider();
            assetIdentificationSubmodelProvider.BindTo(AssetAdministrationShell.Submodels["AssetIdentification"]);
            assetIdentificationSubmodelProvider.UseInMemoryPropertyHandler();
            this.RegisterSubmodelServiceProvider("AssetIdentification", assetIdentificationSubmodelProvider);

            

            InitializeMessageClient();

            cancellationToken = new CancellationTokenSource();
            Task.Factory.StartNew(async() =>
            {
                while(!cancellationToken.IsCancellationRequested)
                {
                    Run();
                    await Task.Delay(5000);
                }
            }, cancellationToken.Token, TaskCreationOptions.LongRunning, TaskScheduler.Default);
        }

        private void InitializeMessageClient()
        {
            messageClient = new SimpleMqttClient(
                new MqttConfig(
                    Guid.NewGuid().ToString(),
                    "mqtt://127.0.0.1:1883"));
            messageClient.Start();
        }

        public void Run()
        {
            helloSubmodelServiceProvider.ThrowEvent(new PublishableEvent()
            {
                EventReference = new Reference<IEvent>(AssetAdministrationShell.Submodels["HelloSubmodel"].SubmodelElements["HelloEvent"].Cast<IEvent>()),
                Originator = "HelloAssetAdministrationShell",
                Timestamp = DateTime.Now.ToString(),
                Message = "Pew Pew",
            }, "/" + AssetAdministrationShell.IdShort + "/submodels/HelloSubmodel/HelloEvent", null, 2);
        }

        private void HelloPropertySetHandler(IProperty property, IValue value)
        {
            AssetAdministrationShell.Submodels["HelloSubmodel"].SubmodelElements["HelloProperty"].Cast<IProperty>().Value = value.Value;
        }

        private IValue HelloPropertyGetHandler(IProperty property)
        {
            var localProperty = AssetAdministrationShell.Submodels["HelloSubmodel"].SubmodelElements["HelloProperty"].Cast<IProperty>();
            return new ElementValue(localProperty.Value, localProperty.ValueType);
        }

        private Task<OperationResult> HelloOperationHandler(IOperation operation, IOperationVariableSet inputArguments, IOperationVariableSet outputArguments)
        {
            if (inputArguments?.Count > 0)
            {
                outputArguments.Add(
                    new Property<string>()
                    {
                        IdShort = "ReturnValue",
                        Value = "Hello '" + inputArguments["Text"].Cast<IProperty>().ToObject<string>() + "'"
                    });
                return new OperationResult(true);
            }
            return new OperationResult(false);
        }

        public override IAssetAdministrationShell GenerateAssetAdministrationShell()
        {
            AssetAdministrationShell aas = new AssetAdministrationShell();

            aas.IdShort = "HelloAAS";
            aas.Identification = new Identifier("http://basys40.de/shells/HelloAAS/" + Guid.NewGuid().ToString(), KeyType.IRI);
            aas.Description = new LangStringSet() { new LangString("en-US", "This is an exemplary Asset Administration Shell for starters") };
                        
            aas.Asset = new Asset()
            {
                Description = new LangStringSet() { new LangString("en-US", "This is an exemplary Asset reference from the Asset Administration Shell") },
                IdShort = "HelloAsset",
                Identification = new Identifier("http://basys40.de/assets/HelloAsset/" + Guid.NewGuid().ToString(), KeyType.IRI),
                Kind = AssetKind.Instance,
                SemanticId = new Reference(new GlobalKey(KeyElements.Asset, KeyType.IRI, "urn:basys:org.eclipse.basyx:assets:HelloAsset:1.0.0"))
            };

            Submodel helloSubmodel = new Submodel
            {
                Description = new LangStringSet() { new LangString("en-US", "This is an exemplary Submodel") },
                IdShort = "HelloSubmodel",
                Identification = new Identifier("http://basys40.de/submodels/HelloSubmodel/" + Guid.NewGuid().ToString(), KeyType.IRI),
                Kind = ModelingKind.Instance,
                SemanticId = new Reference(new GlobalKey(KeyElements.Submodel, KeyType.IRI, "urn:basys:org.eclipse.basyx:submodels:HelloSubmodel:1.0.0"))
            };

            helloSubmodel.SubmodelElements = new ElementContainer<ISubmodelElement>();
            helloSubmodel.SubmodelElements.Add(new Property<string>()
            {
                Description = new LangStringSet() { new LangString("en-US", "This is an exemplary property") },
                IdShort = "HelloProperty",
                Kind = ModelingKind.Instance,
                Value = "TestValue",
                SemanticId = new Reference(new GlobalKey(KeyElements.Property, KeyType.IRI, "urn:basys:org.eclipse.basyx:dataElements:HelloProperty:1.0.0"))
            });

            helloSubmodel.SubmodelElements.Add(new File()
            {
                Description = new LangStringSet() { new LangString("en-US", "This is an exemplary file attached to the Asset Administration Shell")},
                IdShort = "HelloFile",
                Kind = ModelingKind.Instance,
                MimeType = "application/pdf",
                Value = "/HelloAssetAdministrationShell.pdf"
            });


            var helloOperation_ConceptDescription = new ConceptDescription()
            {
                Identification = new Identifier("urn:basys:org.eclipse.basyx:dataSpecifications:EndpointSpecification:1.0.0", KeyType.IRI),
                EmbeddedDataSpecifications = new List<IEmbeddedDataSpecification>()
                {
                    new EndpointSpecification(
                        new EndpointSpecificationContent()
                        {
                            Endpoints = new List<IEndpoint>() { new OpcUaEndpoint("opc.tcp://127.0.0.1:4840/Objects/1:HelloAAS/1:SERVICE/1:TestOperation") }
                        })                  
                }
            };

            helloSubmodel.SubmodelElements.Add(new Operation()
            {
                Description = new LangStringSet() { new LangString("en-US", "This is an exemplary operation returning the input argument with 'Hello' as prefix") },
                IdShort = "HelloOperation",
                InputVariables = new OperationVariableSet() { new Property<string>() { IdShort = "Text" } },
                OutputVariables = new OperationVariableSet() { new Property<string>() { IdShort = "ReturnValue" } },
                ConceptDescription = helloOperation_ConceptDescription
            });

            helloSubmodel.SubmodelElements.Add(new Event()
            {
                Description = new LangStringSet() { new LangString("en-US", "This is an exemplary event with only one property as event payload") },
                IdShort = "HelloEvent",
                Kind = ModelingKind.Template,
                DataElements = new ElementContainer<ISubmodelElement>()
                {
                    new Property<string>()
                    {
                        IdShort = "HelloEvent_Property",                        
                        Kind = ModelingKind.Template
                    }
                }

            });

            aas.Submodels = new ElementContainer<ISubmodel>();
            aas.Submodels.Add(helloSubmodel);

            var assetIdentificationSubmodel = new Submodel();
            assetIdentificationSubmodel.IdShort = "AssetIdentification";
            assetIdentificationSubmodel.Identification = new Identifier(Guid.NewGuid().ToString(), KeyType.Custom);
            assetIdentificationSubmodel.Kind = ModelingKind.Instance;
            assetIdentificationSubmodel.Parent = new Reference<IAssetAdministrationShell>(aas);

            var productTypeProp = new Property<string>()
            {
                IdShort = "ProductType",
                Kind = ModelingKind.Instance,
                SemanticId = new Reference(
                  new GlobalKey(
                      KeyElements.Property,
                      KeyType.IRDI,
                      "0173-1#02-AAO057#002")),
                Value = "HelloAsset_ProductType"
            };

            ConceptDescription orderNumberCD = new ConceptDescription()
            {
                Identification = new Identifier("0173-1#02-AAO689#001", KeyType.IRDI),
                EmbeddedDataSpecifications = new List<IEmbeddedDataSpecification>()
                {
                    new DataSpecificationIEC61360(new DataSpecificationIEC61360Content()
                    {
                        PreferredName = new LangStringSet { new LangString("EN", "identifying order number") },
                        Definition =  new LangStringSet { new LangString("EN", "unique classifying number that enables to name an object and to order it from a supplier or manufacturer") },
                        DataType = DataTypeIEC61360.STRING
                    })
                }
            };

            var orderNumber = new Property<string>()
            {
                IdShort = "OrderNumber",
                Kind = ModelingKind.Instance,
                SemanticId = new Reference(
                    new GlobalKey(
                        KeyElements.Property,
                        KeyType.IRDI,
                        "0173-1#02-AAO689#001")),
                Value = "HelloAsset_OrderNumber",
                ConceptDescription = orderNumberCD
            };

            var serialNumber = new Property<string>()
            {
                IdShort = "SerialNumber",
                Kind = ModelingKind.Instance,
                Value = "HelloAsset_SerialNumber"
            };

            assetIdentificationSubmodel.SubmodelElements.Add(productTypeProp);
            assetIdentificationSubmodel.SubmodelElements.Add(orderNumber);
            assetIdentificationSubmodel.SubmodelElements.Add(serialNumber);

            (aas.Asset as Asset).AssetIdentificationModel = new Reference<ISubmodel>(assetIdentificationSubmodel);

            aas.Submodels.Add(assetIdentificationSubmodel);

            return aas;
        }
    }
}
