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
using BaSyx.Models.Core.AssetAdministrationShell;
using BaSyx.Models.Core.AssetAdministrationShell.Generics;
using BaSyx.Models.Core.AssetAdministrationShell.Identification;
using BaSyx.Models.Core.AssetAdministrationShell.Implementations;
using BaSyx.Models.Core.AssetAdministrationShell.References;
using BaSyx.Models.Core.Common;
using BaSyx.Utils.Settings.Types;
using System;
using System.Collections.Generic;

namespace MultiAssetAdministrationShell
{
    class Program
    {
        static void Main(string[] args)
        {
            ServerSettings aasRepositorySettings = ServerSettings.CreateSettings();
            aasRepositorySettings.ServerConfig.Hosting.ContentPath = "Content";
            aasRepositorySettings.ServerConfig.Hosting.Urls.Add("http://+:5999");

            MultiAssetAdministrationShellHttpServer server = new MultiAssetAdministrationShellHttpServer(aasRepositorySettings);
            AssetAdministrationShellRepositoryServiceProvider repositoryService = new AssetAdministrationShellRepositoryServiceProvider();

            for (int i = 0; i < 10; i++)
            {
                AssetAdministrationShell aas = new AssetAdministrationShell("MultiAAS_" + i, new Identifier("http://basys40.de/shells/MultiAAS/" + Guid.NewGuid().ToString(), KeyType.IRI))
                {
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
                    Asset = new Asset("Asset_" + i, new Identifier("http://basys40.de/assets/MultiAsset/" + Guid.NewGuid().ToString(), KeyType.IRI))
                    {
                        Kind = AssetKind.Instance,
                        Description = new LangStringSet()
                        {
                              new LangString("de-DE", i + ". Asset"),
                              new LangString("en-US", i + ". Asset")
                        }
                    }
                };

                aas.Submodels.Create(new Submodel("TestSubmodel", new Identifier("http://basys40.de/submodels/" + Guid.NewGuid().ToString(), KeyType.IRI))
                {
                    SubmodelElements =
                    {
                        new Property<double>("Property_" + i)
                        {
                            Get = prop => { return Math.Pow(i, 2); }
                        }
                    }
                });

                var aasServiceProvider = aas.CreateServiceProvider(true);
                repositoryService.RegisterAssetAdministrationShellServiceProvider(aas.IdShort, aasServiceProvider);
            }

            List<HttpEndpoint> endpoints = server.Settings.ServerConfig.Hosting.Urls.ConvertAll(c => new HttpEndpoint(c.Replace("+", "127.0.0.1")));
            repositoryService.UseDefaultEndpointRegistration(endpoints);

            server.SetServiceProvider(repositoryService);
            server.Run();
        }
    }
}
