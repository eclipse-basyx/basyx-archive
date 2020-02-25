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
using BaSyx.AAS.Server.Http;
using BaSyx.API.AssetAdministrationShell.Extensions;
using BaSyx.API.Components;
using BaSyx.Models.Core.AssetAdministrationShell.Enums;
using BaSyx.Models.Core.AssetAdministrationShell.Generics;
using BaSyx.Models.Core.AssetAdministrationShell.Identification;
using BaSyx.Models.Core.AssetAdministrationShell.Implementations;
using BaSyx.Models.Core.AssetAdministrationShell.Implementations.SubmodelElementTypes;
using BaSyx.Models.Core.AssetAdministrationShell.References;
using BaSyx.Models.Core.Common;
using BaSyx.Submodel.Server.Http;
using BaSyx.Utils.ResultHandling.ResultTypes;
using BaSyx.Utils.Settings.Types;
using System;
using System.Threading.Tasks;

namespace SimpleAssetAdministrationShell
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Hello World!");

            string propertyValue = "TestFromInside";
            int i = 0;
            double y = 2.0;

            Submodel identificationSubmodel = new Submodel()
            {
                Identification = new Identifier(Guid.NewGuid().ToString(), KeyType.Custom),
                IdShort = "AssetIdentification"
            };

            AssetAdministrationShell aas = new AssetAdministrationShell()
            {
                IdShort = "SimpleAAS",
                Identification = new Identifier("http://basys40.de/shells/SimpleAAS/" + Guid.NewGuid().ToString(), KeyType.IRI),
                Description = new LangStringSet()
                {
                   new LangString("de-DE", "Einfache VWS"),
                   new LangString("en-US", "Simple AAS")
                },
                Administration = new AdministrativeInformation()
                {
                    Version = "1.0",
                    Revision = "120"
                },
                Asset = new Asset()
                {
                    IdShort = "SimpleAsset",
                    Identification = new Identifier("http://basys40.de/assets/SimpleAsset/" + Guid.NewGuid().ToString(), KeyType.IRI),
                    Kind = AssetKind.Instance,
                    Description = new LangStringSet()
                    {
                          new LangString("de-DE", "Einfaches Asset"),
                          new LangString("en-US", "Simple Asset")
                    },
                    AssetIdentificationModel = new Reference<Submodel>(identificationSubmodel)
                }
            };

            Submodel submodel = new Submodel()
            {
                IdShort = "TestSubmodel",
                Identification = new Identifier(Guid.NewGuid().ToString(), KeyType.Custom),
                SubmodelElements = new ElementContainer<ISubmodelElement>()
                {
                    new Property<string>()
                    {
                        IdShort = "TestProperty1",
                        Set = (prop, val) => propertyValue = val,
                        Get = prop => { return propertyValue + "_" + i++; }
                    },
                    new Property<string>()
                    {
                        IdShort = "TestProperty2",
                        Set = (prop, val) => propertyValue = val,
                        Get = prop => { return propertyValue + "_" + i++; }
                    },
                    new Property<int>()
                    {
                        IdShort = "TestProperty3",
                        Set = (prop, val) => i = val,
                        Get = prop => { return i++; }
                    },
                    new Property<double>()
                    {
                        IdShort = "TestProperty4",
                        Set = (prop, val) => y = val,
                        Get = prop => { return Math.Pow(y, i); }
                    },
                    new Operation()
                    {
                        IdShort = "GetTime",
                        OutputVariables = new OperationVariableSet()
                        {
                            new Property<string>()
                            {
                                IdShort = "Date"
                            },
                            new Property<string>()
                            {
                                IdShort = "Time"
                            },
                            new Property<string>()
                            {
                                IdShort = "Ticks"
                            },
                        },
                        OnMethodCalled = (op, inArgs, outArgs) =>
                        {
                            outArgs.Add(new Property<string>() { IdShort = "Date", Value = "Heute ist der " + DateTime.Now.Date.ToString() });
                            outArgs.Add(new Property<string>() { IdShort = "Time", Value = "Es ist " + DateTime.Now.TimeOfDay.ToString() + " Uhr" });
                            outArgs.Add(new Property<string>() { IdShort = "Ticks", Value = "Ticks: " + DateTime.Now.Ticks.ToString() });
                            return new OperationResult(true);
                        }
                    }
                }
            };
            aas.Submodels.Add(identificationSubmodel);
            aas.Submodels.Add(submodel);

            var submodelSettings = ServerSettings.CreateSettings();
            submodelSettings.ServerConfig.Hosting.ContentPath = "Content";
            submodelSettings.ServerConfig.Hosting.Urls.Add("http://+:5222");
            submodelSettings.RegistryConfig.Activated = false;

            SubmodelHttpServer submodelLoader = new SubmodelHttpServer(submodelSettings);
            var submodelServiceProvider = submodel.CreateServiceProvider();
            submodelLoader.SetServiceProvider(submodelServiceProvider);
            Task runSubmodelTask = submodelLoader.RunAsync();

            var aasSettings = ServerSettings.CreateSettings();
            aasSettings.ServerConfig.Hosting.ContentPath = "Content";
            aasSettings.ServerConfig.Hosting.Urls.Add("http://+:5111");
            aasSettings.RegistryConfig.Activated = false;

            IAssetAdministrationShellServiceProvider serviceProvider = aas.CreateServiceProvider(true);

            serviceProvider.SubmodelRegistry.RegisterSubmodelServiceProvider(submodel.IdShort, submodelServiceProvider);

            AssetAdministrationShellHttpServer loader = new AssetAdministrationShellHttpServer(aasSettings);
            loader.SetServiceProvider(serviceProvider);

            loader.Run();
        }
    }
}
