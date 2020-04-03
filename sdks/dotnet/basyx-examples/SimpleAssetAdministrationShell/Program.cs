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
using BaSyx.Models.Core.AssetAdministrationShell.Generics.SubmodelElementTypes;
using BaSyx.Models.Core.AssetAdministrationShell.Identification;
using BaSyx.Models.Core.AssetAdministrationShell.Implementations;
using BaSyx.Models.Core.AssetAdministrationShell.Implementations.SubmodelElementTypes;
using BaSyx.Models.Core.AssetAdministrationShell.References;
using BaSyx.Models.Core.Common;
using BaSyx.Models.Extensions;
using BaSyx.Submodel.Server.Http;
using BaSyx.Utils.ResultHandling;
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

            Submodel testSubmodel = new Submodel()
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
                    },
                    new Operation()
                    {
                        IdShort = "Calculate",
                        Description = new LangStringSet()
                        {
                            new LangString("DE", "Taschenrechner mit simulierter langer Rechenzeit zum Testen von asynchronen Aufrufen"),
                            new LangString("EN", "Calculator with simulated long-running computing time for testing asynchronous calls")
                        },
                        InputVariables = new OperationVariableSet()
                        {
                            new Property<string>()
                            {
                                IdShort = "Expression",
                                Description = new LangStringSet()
                                {
                                    new LangString("DE", "Ein mathematischer Ausdruck (z.B. 5*9)"),
                                    new LangString("EN", "A mathematical expression (e.g. 5*9)")
                                }                                
                            },
                            new Property<int>()
                            {
                                IdShort = "ComputingTime",
                                Description = new LangStringSet()
                                {
                                    new LangString("DE", "Die Bearbeitungszeit in Millisekunden"),
                                    new LangString("EN", "The computation time in milliseconds")
                                }
                            }
                        },
                       OutputVariables = new OperationVariableSet()
                       {
                           new Property<double>()
                           {
                               IdShort = "Result"
                           }
                       },
                       OnMethodCalled = async (op, inArgs, outArgs) =>
                       {
                           string expression = inArgs.Get("Expression")?.ToModelElement<IProperty>()?.ToObject<string>();
                           int? computingTime = inArgs.Get("ComputingTime")?.ToModelElement<IProperty>()?.ToObject<int>();
                           
                           if(computingTime.HasValue)
                            await Task.Delay(computingTime.Value);

                           double value = CalulcateExpression(expression);

                           outArgs.Add(new Property<double>() { IdShort = "Result", Value = value });

                           return new OperationResult(true);
                       }
                    }

                }
            };
            aas.Submodels.Add(identificationSubmodel);
            aas.Submodels.Add(testSubmodel);

            ServerSettings submodelServerSettings = ServerSettings.CreateSettings();
            submodelServerSettings.ServerConfig.Hosting.ContentPath = "Content";
            submodelServerSettings.ServerConfig.Hosting.Urls.Add("http://localhost:5222");

            SubmodelHttpServer submodelServer = new SubmodelHttpServer(submodelServerSettings);
            ISubmodelServiceProvider submodelServiceProvider = testSubmodel.CreateServiceProvider();
            submodelServer.SetServiceProvider(submodelServiceProvider);
            submodelServiceProvider.UseAutoEndpointRegistration(submodelServerSettings.ServerConfig);
            Task runSubmodelTask = submodelServer.RunAsync();

            ServerSettings aasServerSettings = ServerSettings.CreateSettings();
            aasServerSettings.ServerConfig.Hosting.ContentPath = "Content";
            aasServerSettings.ServerConfig.Hosting.Urls.Add("http://localhost:5111");

            IAssetAdministrationShellServiceProvider serviceProvider = aas.CreateServiceProvider(true);
            serviceProvider.SubmodelRegistry.RegisterSubmodelServiceProvider(testSubmodel.IdShort, submodelServiceProvider);
            serviceProvider.UseAutoEndpointRegistration(aasServerSettings.ServerConfig);

            AssetAdministrationShellHttpServer aasServer = new AssetAdministrationShellHttpServer(aasServerSettings);
            aasServer.SetServiceProvider(serviceProvider);
            aasServer.Run();
        }

        public static double CalulcateExpression(string expression)
        {
            string columnName = "Evaluation";
            System.Data.DataTable dataTable = new System.Data.DataTable();
            System.Data.DataColumn dataColumn = new System.Data.DataColumn(columnName, typeof(double), expression);
            dataTable.Columns.Add(dataColumn);
            dataTable.Rows.Add(0);
            return (double)(dataTable.Rows[0][columnName]);
        }

    }
}
