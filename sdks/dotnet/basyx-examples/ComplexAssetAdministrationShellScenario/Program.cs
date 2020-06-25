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
using BaSyx.Models.Connectivity;
using BaSyx.Models.Connectivity.Descriptors;
using BaSyx.Models.Core.AssetAdministrationShell.Enums;
using BaSyx.Models.Core.AssetAdministrationShell.Generics;
using BaSyx.Models.Core.AssetAdministrationShell.Identification;
using BaSyx.Models.Core.AssetAdministrationShell.Implementations;
using BaSyx.Models.Core.AssetAdministrationShell.Implementations.SubmodelElementTypes;
using BaSyx.Models.Core.AssetAdministrationShell.References;
using BaSyx.Models.Core.Common;
using BaSyx.Registry.Client.Http;
using BaSyx.Registry.ReferenceImpl.FileBased;
using BaSyx.Registry.Server.Http;
using BaSyx.Submodel.Server.Http;
using BaSyx.Utils.Settings.Sections;
using BaSyx.Utils.Settings.Types;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;

namespace ComplexAssetAdministrationShellScenario
{
    class Program
    {
        static RegistryHttpClient registryClient;
        static void Main(string[] args)
        {
            Thread.Sleep(5000);
            registryClient = new RegistryHttpClient();
            LoadScenario();

            Console.WriteLine("Press enter to quit...");
            Console.ReadKey();
        }

        private static void LoadScenario()
        {
            LoadRegistry();
            LoadSingleShell();
            LoadMultipleShells();
            LoadMultipleSubmodels();
        }

        public static void LoadMultipleSubmodels()
        {
            ServerSettings submodelRepositorySettings = ServerSettings.CreateSettings();
            submodelRepositorySettings.ServerConfig.Hosting.ContentPath = "Content";
            submodelRepositorySettings.ServerConfig.Hosting.Urls.Add("http://+:6999");

            MultiSubmodelHttpServer multiServer = new MultiSubmodelHttpServer(submodelRepositorySettings);
            SubmodelRepositoryServiceProvider repositoryService = new SubmodelRepositoryServiceProvider();

            for (int i = 0; i < 3; i++)
            {
                Submodel submodel = new Submodel()
                {
                    IdShort = "MultiSubmodel_" + i,
                    Identification = new Identifier("http://basys40.de/submodel/MultiSubmodel/" + Guid.NewGuid().ToString(), KeyType.IRI),
                    Description = new LangStringSet()
                    {
                       new LangString("de-DE", i + ". Teilmodell"),
                       new LangString("en-US", i + ". Submodel")
                    },
                    Administration = new AdministrativeInformation()
                    {
                        Version = "1.0",
                        Revision = "120"
                    },
                    SubmodelElements = new ElementContainer<ISubmodelElement>()
                    {
                        new Property<string>()
                        {
                            IdShort = "Property_" + i,
                            Value = "TestValue_" + i
                        },
                        new SubmodelElementCollection()
                        {
                            IdShort = "Coll_" + i,
                            Value = new ElementContainer<ISubmodelElement>()
                            {
                                new Property<string>()
                                {
                                    IdShort = "SubProperty_" + i,
                                    Value = "TestSubValue_" + i
                                }
                            }
                        }
                    }
                };

                var submodelServiceProvider = submodel.CreateServiceProvider();
                repositoryService.RegisterSubmodelServiceProvider(submodel.IdShort, submodelServiceProvider);
            }

            List<HttpEndpoint> endpoints = multiServer.Settings.ServerConfig.Hosting.Urls.ConvertAll(c => new HttpEndpoint(c.Replace("+", "127.0.0.1")));
            repositoryService.UseDefaultEndpointRegistration(endpoints);

            multiServer.SetServiceProvider(repositoryService);
            multiServer.ApplicationStopping = () =>
            {
                for (int i = 0; i < repositoryService.ServiceDescriptor.SubmodelDescriptors.Count; i++)
                {
                    registryClient.DeleteSubmodel("http://basyx.de/shells/MultiAAS/" + i, repositoryService.ServiceDescriptor.SubmodelDescriptors[i].IdShort);
                }
            };

            multiServer.RunAsync();

            var shells = registryClient.RetrieveAssetAdministrationShells();
            var shell = shells.Entity?.FirstOrDefault(f => f.Identification.Id.Contains("SimpleAAS"));
            for (int i = 0; i < repositoryService.ServiceDescriptor.SubmodelDescriptors.Count; i++)
            {
                registryClient.CreateSubmodel("http://basyx.de/shells/MultiAAS/" + i, repositoryService.ServiceDescriptor.SubmodelDescriptors[i]);

                if(shell != null)
                    registryClient.CreateSubmodel(shell.Identification.Id, repositoryService.ServiceDescriptor.SubmodelDescriptors[i]);
            }
        }


        private static void LoadMultipleShells()
        {
            ServerSettings aasRepositorySettings = ServerSettings.CreateSettings();
            aasRepositorySettings.ServerConfig.Hosting.ContentPath = "Content";
            aasRepositorySettings.ServerConfig.Hosting.Urls.Add("http://+:5999");

            MultiAssetAdministrationShellHttpServer multiServer = new MultiAssetAdministrationShellHttpServer(aasRepositorySettings);
            AssetAdministrationShellRepositoryServiceProvider repositoryService = new AssetAdministrationShellRepositoryServiceProvider();

            for (int i = 0; i < 3; i++)
            {
                AssetAdministrationShell aas = new AssetAdministrationShell()
                {
                    IdShort = "MultiAAS_" + i,
                    Identification = new Identifier("http://basyx.de/shells/MultiAAS/" + i, KeyType.IRI),
                    Description = new LangStringSet()
                    {
                       new LangString("de-DE", i + ". VWS"),
                       new LangString("en-US", i + ". AAS")
                    },
                    Administration = new AdministrativeInformation()
                    {
                        Version = "1.0",
                        Revision = "120"
                    },
                    Asset = new Asset()
                    {
                        IdShort = "Asset_" + i,
                        Identification = new Identifier("http://basyx.de/assets/MultiAsset/" + i, KeyType.IRI),
                        Kind = AssetKind.Instance,
                        Description = new LangStringSet()
                        {
                              new LangString("de-DE", i + ". Asset"),
                              new LangString("en-US", i + ". Asset")
                        }
                    }
                };

                aas.Submodels.Create(new Submodel()
                {
                    Identification = new Identifier("http://basyx.de/submodels/" + i, KeyType.IRI),
                    IdShort = "TestSubmodel",
                    SubmodelElements = new ElementContainer<ISubmodelElement>()
                    {
                        new Property<string>()
                        {
                            IdShort = "Property_" + i,
                            Value = "TestValue_" + i 
                        },
                        new SubmodelElementCollection()
                        {
                            IdShort = "Coll_" + i,
                            Value = new ElementContainer<ISubmodelElement>()
                            {
                                new Property<string>()
                                {
                                    IdShort = "SubProperty_" + i,
                                    Value = "TestSubValue_" + i
                                }
                            }
                        }
                    }
                });

                var aasServiceProvider = aas.CreateServiceProvider(true);
                repositoryService.RegisterAssetAdministrationShellServiceProvider(aas.IdShort, aasServiceProvider);
            }

            List<HttpEndpoint> endpoints = multiServer.Settings.ServerConfig.Hosting.Urls.ConvertAll(c => new HttpEndpoint(c.Replace("+", "127.0.0.1")));
            repositoryService.UseDefaultEndpointRegistration(endpoints);

            multiServer.SetServiceProvider(repositoryService);
            multiServer.ApplicationStopping = () =>
            {
                foreach (var aasDescriptor in repositoryService.ServiceDescriptor.AssetAdministrationShellDescriptors)
                {
                    registryClient.DeleteAssetAdministrationShell(aasDescriptor.Identification.Id);
                }
            };

            multiServer.RunAsync();

            foreach (var aasDescriptor in repositoryService.ServiceDescriptor.AssetAdministrationShellDescriptors)
            {
                registryClient.CreateAssetAdministrationShell(aasDescriptor);
            }
         
        }

        private static void LoadSingleShell()
        {
            AssetAdministrationShell aas = SimpleAssetAdministrationShell.SimpleAssetAdministrationShell.GetAssetAdministrationShell();
            ISubmodel testSubmodel = aas.Submodels["TestSubmodel"];

            ServerSettings submodelServerSettings = ServerSettings.CreateSettings();
            submodelServerSettings.ServerConfig.Hosting.ContentPath = "Content";
            submodelServerSettings.ServerConfig.Hosting.Urls.Add("http://localhost:5222");

            SubmodelHttpServer submodelServer = new SubmodelHttpServer(submodelServerSettings);
            ISubmodelServiceProvider submodelServiceProvider = testSubmodel.CreateServiceProvider();
            submodelServer.SetServiceProvider(submodelServiceProvider);
            submodelServiceProvider.UseAutoEndpointRegistration(submodelServerSettings.ServerConfig);
            submodelServer.RunAsync();

            ServerSettings aasServerSettings = ServerSettings.CreateSettings();
            aasServerSettings.ServerConfig.Hosting.ContentPath = "Content";
            aasServerSettings.ServerConfig.Hosting.Urls.Add("http://localhost:5111");

            IAssetAdministrationShellServiceProvider aasServiceProvider = aas.CreateServiceProvider(true);
            aasServiceProvider.SubmodelRegistry.RegisterSubmodelServiceProvider(testSubmodel.IdShort, submodelServiceProvider);
            aasServiceProvider.UseAutoEndpointRegistration(aasServerSettings.ServerConfig);

            AssetAdministrationShellHttpServer aasServer = new AssetAdministrationShellHttpServer(aasServerSettings);
            aasServer.SetServiceProvider(aasServiceProvider);
            aasServer.ApplicationStopping = () => { registryClient.DeleteAssetAdministrationShell(aas.Identification.Id); };
            aasServer.RunAsync();

            registryClient.CreateAssetAdministrationShell(new AssetAdministrationShellDescriptor(aas, aasServiceProvider.ServiceDescriptor.Endpoints));
            registryClient.CreateSubmodel(aas.Identification.Id, new SubmodelDescriptor(testSubmodel, submodelServiceProvider.ServiceDescriptor.Endpoints));
        }

        private static void LoadRegistry()
        {
            ServerSettings registrySettings = ServerSettings.CreateSettings();
            registrySettings.ServerConfig.Hosting = new HostingConfiguration()
            {
                Urls = new List<string>()
                {
                    "http://localhost:4999"
                }
            };

            RegistryHttpServer registryServer = new RegistryHttpServer(registrySettings);
            FileBasedRegistry fileBasedRegistry = new FileBasedRegistry();
            registryServer.SetRegistryProvider(fileBasedRegistry);
            registryServer.RunAsync();
        }
    }
}
