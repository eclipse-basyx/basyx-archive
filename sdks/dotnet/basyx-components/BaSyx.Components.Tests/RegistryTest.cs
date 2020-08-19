using BaSyx.API.Components;
using BaSyx.Models.Connectivity;
using BaSyx.Models.Connectivity.Descriptors;
using BaSyx.Models.Core.AssetAdministrationShell.Enums;
using BaSyx.Models.Core.AssetAdministrationShell.Generics;
using BaSyx.Models.Core.AssetAdministrationShell.Identification;
using BaSyx.Models.Core.AssetAdministrationShell.Implementations;
using BaSyx.Models.Core.AssetAdministrationShell.References;
using BaSyx.Models.Core.Common;
using BaSyx.Registry.Client.Http;
using BaSyx.Registry.ReferenceImpl.FileBased;
using BaSyx.Registry.Server.Http;
using BaSyx.Utils.ResultHandling;
using FluentAssertions;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Globalization;

namespace BaSyx.Components.Tests
{
    [TestClass]
    public class RegistryTest : IAssetAdministrationShellRegistry
    {
        private static RegistryHttpServer registryHttpServer;
        private static RegistryHttpClient registryHttpClient;

        static RegistryTest()
        {
            registryHttpServer = new RegistryHttpServer();
            registryHttpServer.SetRegistryProvider(new FileBasedRegistry());
            _ = registryHttpServer.RunAsync();

            registryHttpClient = new RegistryHttpClient();
        }

        

        private static IAssetAdministrationShell aas = new AssetAdministrationShell()
        {
            IdShort = "MyTestAAS",
            Identification = new Identifier("https://www.basys40.de/shells/MyTestAAS", KeyType.IRI),
            Asset = new Asset()
            {
                IdShort = "MyTestAsset",
                Identification = new Identifier("https://www.basys40.de/assets/MyTestAsset", KeyType.IRI),
                Kind = AssetKind.Instance
            },
            Administration = new AdministrativeInformation()
            {
                Version = "1.0.0",
                Revision = "0.0.1"
            },
            Description = new LangStringSet()
            {
                new LangString(new CultureInfo("de").TwoLetterISOLanguageName, "Meine Test-Verwaltungsschale"),
                new LangString(new CultureInfo("en").TwoLetterISOLanguageName, "My Test Asset Administration Shell"),
            },
            Submodels = new ElementContainer<ISubmodel>()
            {
                new Models.Core.AssetAdministrationShell.Implementations.Submodel()
                {
                    IdShort = "MyTestSubmodel",
                    Identification = new Identifier("https://www.basys40.de/submodels/MyTestSubmodel", KeyType.IRI),
                    SemanticId = new Reference(new Key(KeyElements.GlobalReference, KeyType.IRI, "urn:basys:org.eclipse.basyx:submodels:MyTestSubmodel:1.0.0", false))
                }
            }
        };

        private static ISubmodel submodel = new Models.Core.AssetAdministrationShell.Implementations.Submodel()
        {
            IdShort = "MyAdditionalTestSubmodel",
            Identification = new Identifier("https://www.basys40.de/submodels/MyAdditionalTestSubmodel", KeyType.IRI),
            SemanticId = new Reference(new Key(KeyElements.GlobalReference, KeyType.IRI, "urn:basys:org.eclipse.basyx:submodels:MyAdditionalTestSubmodel:1.0.0", false))
        };

        private static IAssetAdministrationShellDescriptor aasDescriptor = new AssetAdministrationShellDescriptor(aas, new List<IEndpoint>()
        {
            new HttpEndpoint("http://localhost:5080/aas")
        });

        private static ISubmodelDescriptor submodelDescriptor = new SubmodelDescriptor(submodel, new List<IEndpoint>()
        {
            new HttpEndpoint("http://localhost:5111/submodel")
        });

        [TestMethod]
        public void Test1CreateOrUpdateAssetAdministrationShellRegistration()
        {
            var result = CreateOrUpdateAssetAdministrationShellRegistration(aas.Identification.Id, aasDescriptor);
            result.Entity.Should().BeEquivalentTo(aasDescriptor);
        }
        public IResult<IAssetAdministrationShellDescriptor> CreateOrUpdateAssetAdministrationShellRegistration(string aasId, IAssetAdministrationShellDescriptor aasDescriptor)
        {
            return registryHttpClient.CreateOrUpdateAssetAdministrationShellRegistration(aasId, aasDescriptor);
        }

        [TestMethod]
        public void Test2CreateOrUpdateSubmodelRegistration()
        {
            var result = CreateOrUpdateSubmodelRegistration(aas.Identification.Id, submodel.Identification.Id, submodelDescriptor);
            result.Entity.Should().BeEquivalentTo(submodelDescriptor);
            aasDescriptor.SubmodelDescriptors.Add(submodelDescriptor);
        }

        public IResult<ISubmodelDescriptor> CreateOrUpdateSubmodelRegistration(string aasId, string submodelId, ISubmodelDescriptor submodelDescriptor)
        {
            return registryHttpClient.CreateOrUpdateSubmodelRegistration(aasId, submodelId, submodelDescriptor);
        }

        [TestMethod]
        public void Test3RetrieveAssetAdministrationShellRegistration()
        {
            var result = RetrieveAssetAdministrationShellRegistration(aas.Identification.Id);
            result.Entity.Should().BeEquivalentTo(aasDescriptor);
        }

        public IResult<IAssetAdministrationShellDescriptor> RetrieveAssetAdministrationShellRegistration(string aasId)
        {
            return registryHttpClient.RetrieveAssetAdministrationShellRegistration(aasId);
        }

        [TestMethod]
        public void Test41RetrieveAllAssetAdministrationShellRegistrations()
        {
            var result = RetrieveAllAssetAdministrationShellRegistrations();
            result.Entity.Should().ContainEquivalentOf(aasDescriptor);
        }

        public IResult<IQueryableElementContainer<IAssetAdministrationShellDescriptor>> RetrieveAllAssetAdministrationShellRegistrations()
        {
            return registryHttpClient.RetrieveAllAssetAdministrationShellRegistrations();
        }

        [TestMethod]
        public void Test42RetrieveAllAssetAdministrationShellRegistrations()
        {
            var result = RetrieveAllAssetAdministrationShellRegistrations(p => p.Identification.Id == aas.Identification.Id);
            result.Entity.Should().ContainEquivalentOf(aasDescriptor);
        }

        public IResult<IQueryableElementContainer<IAssetAdministrationShellDescriptor>> RetrieveAllAssetAdministrationShellRegistrations(Predicate<IAssetAdministrationShellDescriptor> predicate)
        {
            return registryHttpClient.RetrieveAllAssetAdministrationShellRegistrations(predicate);
        }

        [TestMethod]
        public void Test5RetrieveSubmodelRegistration()
        {
            var result = RetrieveSubmodelRegistration(aas.Identification.Id, submodel.Identification.Id);
            result.Entity.Should().BeEquivalentTo(submodelDescriptor);
        }


        public IResult<ISubmodelDescriptor> RetrieveSubmodelRegistration(string aasId, string submodelId)
        {
            return registryHttpClient.RetrieveSubmodelRegistration(aasId, submodelId);
        }

        [TestMethod]
        public void Test61RetrieveAllSubmodelRegistrations()
        {
            var result = RetrieveAllSubmodelRegistrations(aas.Identification.Id);
            result.Entity.Should().ContainEquivalentOf(submodelDescriptor);
        }

        public IResult<IQueryableElementContainer<ISubmodelDescriptor>> RetrieveAllSubmodelRegistrations(string aasId)
        {
            return registryHttpClient.RetrieveAllSubmodelRegistrations(aasId);
        }

        [TestMethod]
        public void Test62RetrieveAllSubmodelRegistrations()
        {
            var result = RetrieveAllSubmodelRegistrations(aas.Identification.Id, p => p.Identification.Id == submodel.Identification.Id);
            result.Entity.Should().ContainEquivalentOf(submodelDescriptor);
        }

        public IResult<IQueryableElementContainer<ISubmodelDescriptor>> RetrieveAllSubmodelRegistrations(string aasId, Predicate<ISubmodelDescriptor> predicate)
        {
            return registryHttpClient.RetrieveAllSubmodelRegistrations(aasId, predicate);
        }

        [TestMethod]
        public void Test7DeleteSubmodelRegistration()
        {
            var deleted = DeleteSubmodelRegistration(aas.Identification.Id, submodel.Identification.Id);
            deleted.Success.Should().BeTrue();

            var retrieved = RetrieveAllSubmodelRegistrations(aas.Identification.Id);
            retrieved.Entity.Should().NotContain(submodelDescriptor);

        }

        public IResult DeleteSubmodelRegistration(string aasId, string submodelId)
        {
            return registryHttpClient.DeleteSubmodelRegistration(aasId, submodelId);
        }

        [TestMethod]
        public void Test8DeleteAssetAdministrationShellRegistration()
        {
            var deleted = DeleteAssetAdministrationShellRegistration(aas.Identification.Id);
            deleted.Success.Should().BeTrue();

            var retrieved = RetrieveAllAssetAdministrationShellRegistrations();
            retrieved.Entity.Should().NotContain(aasDescriptor);

        }

        public IResult DeleteAssetAdministrationShellRegistration(string aasId)
        {
            return registryHttpClient.DeleteAssetAdministrationShellRegistration(aasId);
        }

    }
}