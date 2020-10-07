using BaSyx.AAS.Client.Http;
using BaSyx.AAS.Server.Http;
using BaSyx.API.AssetAdministrationShell.Extensions;
using BaSyx.API.Clients;
using BaSyx.API.Components;
using BaSyx.Models.Communication;
using BaSyx.Models.Connectivity;
using BaSyx.Models.Connectivity.Descriptors;
using BaSyx.Models.Core.AssetAdministrationShell;
using BaSyx.Models.Core.AssetAdministrationShell.Generics;
using BaSyx.Models.Core.AssetAdministrationShell.Identification;
using BaSyx.Models.Core.AssetAdministrationShell.Implementations;
using BaSyx.Models.Core.AssetAdministrationShell.References;
using BaSyx.Models.Core.Common;
using BaSyx.Models.Extensions;
using BaSyx.Registry.Client.Http;
using BaSyx.Registry.ReferenceImpl.FileBased;
using BaSyx.Registry.Server.Http;
using BaSyx.Utils.ResultHandling;
using BaSyx.Utils.Settings.Types;
using FluentAssertions;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Globalization;

namespace BaSyx.Components.Tests
{
    [TestClass]
    public class AssetAdministrationShellClientServerTest : IAssetAdministrationShellClient, IAssetAdministrationShellSubmodelClient
    {
        private static AssetAdministrationShellHttpServer server;
        private static AssetAdministrationShellHttpClient client;

        private static IAssetAdministrationShell aas;
        private static ISubmodel submodel;
        private static IAssetAdministrationShellDescriptor aasDescriptor;

        static AssetAdministrationShellClientServerTest()
        {
            ServerSettings aasServerSettings = ServerSettings.CreateSettings();
            aasServerSettings.ServerConfig.Hosting.ContentPath = "Content";
            aasServerSettings.ServerConfig.Hosting.Environment = "Development";
            aasServerSettings.ServerConfig.Hosting.Urls.Add("http://localhost:5080");
            aasServerSettings.ServerConfig.Hosting.Urls.Add("https://localhost:5443");

            aas = TestAssetAdministrationShell.GetAssetAdministrationShell();
            submodel = TestAssetAdministrationShell.GetTestSubmodel();
            var serviceProvider = aas.CreateServiceProvider(true);
            serviceProvider.UseAutoEndpointRegistration(aasServerSettings.ServerConfig);
            aasDescriptor = serviceProvider.ServiceDescriptor;

            server = new AssetAdministrationShellHttpServer(aasServerSettings);
            server.SetServiceProvider(serviceProvider);
            _ = server.RunAsync();

            client = new AssetAdministrationShellHttpClient(aasDescriptor);
        }

        [TestMethod]
        public void Test1_RetrieveAssetAdministrationShell()
        {
            var retrieved = RetrieveAssetAdministrationShell();
            retrieved.Success.Should().BeTrue();
        }

        public IResult<IAssetAdministrationShell> RetrieveAssetAdministrationShell()
        {
            return client.RetrieveAssetAdministrationShell();
        }

        [TestMethod]
        public void Test2_RetrieveSubmodel()
        {
            var retrieved = RetrieveSubmodel(submodel.IdShort);
            retrieved.Success.Should().BeTrue();
        }
        public IResult<ISubmodel> RetrieveSubmodel(string submodelId)
        {
            return client.RetrieveSubmodel(submodelId);
        }

        [TestMethod]
        public void Test31_CreateOrUpdateSubmodelElement_FirstLevelHierarchy()
        {
            Property testProperty = new Property("TestIntegerProperty", typeof(int), 8);
            var created = CreateOrUpdateSubmodelElement(submodel.IdShort, testProperty.IdShort, testProperty);
            created.Success.Should().BeTrue();
            created.Entity.Should().BeEquivalentTo(testProperty, opts => opts
            .Excluding(p => p.Get)
            .Excluding(p => p.Set)
            .Excluding(p => p.EmbeddedDataSpecifications)
            .Excluding(p => p.Parent));

            submodel.SubmodelElements.Add(testProperty);
        }
        public IResult<ISubmodelElement> CreateOrUpdateSubmodelElement(string submodelId, string rootSeIdShortPath, ISubmodelElement submodelElement)
        {
            return client.CreateOrUpdateSubmodelElement(submodelId, rootSeIdShortPath, submodelElement);
        }

        [TestMethod]
        public void Test41_RetrieveSubmodelElements()
        {
            var retrieved = RetrieveSubmodelElements(submodel.IdShort);
            retrieved.Success.Should().BeTrue();
            retrieved.Entity.Count.Should().Be(submodel.SubmodelElements.Count);
        }
        public IResult<IElementContainer<ISubmodelElement>> RetrieveSubmodelElements(string submodelId)
        {
            return client.RetrieveSubmodelElements(submodelId);
        }

        [TestMethod]
        public void Test51_RetrieveSubmodelElement_FirstLevelHierarchy()
        {
            var retrieved = RetrieveSubmodelElement(submodel.IdShort, "TestIntegerProperty");
            retrieved.Success.Should().BeTrue();
            retrieved.Entity.Should().BeEquivalentTo(submodel.SubmodelElements["TestIntegerProperty"].Cast<Property>(), opts => opts
            .Excluding(p => p.Get)
            .Excluding(p => p.Set)
            .Excluding(p => p.EmbeddedDataSpecifications)
            .Excluding(p => p.Parent));
        }
        public IResult<ISubmodelElement> RetrieveSubmodelElement(string submodelId, string seIdShortPath)
        {
            return client.RetrieveSubmodelElement(submodelId, seIdShortPath);
        }

        [TestMethod]
        public void Test61_RetrieveSubmodelElementValue_FirstLevelHierarchy()
        {
            var retrieved = RetrieveSubmodelElementValue(submodel.IdShort, "TestIntegerProperty");
            retrieved.Success.Should().BeTrue();
            retrieved.Entity.Value.Should().BeEquivalentTo(8);
        }
        public IResult<IValue> RetrieveSubmodelElementValue(string submodelId, string seIdShortPath)
        {
            return client.RetrieveSubmodelElementValue(submodelId, seIdShortPath);
        }

        [TestMethod]
        public void Test71_UpdateSubmodelElementValue_FirstLevelHierarchy()
        {
            var updated = UpdateSubmodelElementValue(submodel.IdShort, "TestIntegerProperty", new ElementValue(5, typeof(int)));
            updated.Success.Should().BeTrue();
        }
        [TestMethod]
        public void Test72_RetrieveSubmodelElementValue_FirstLevelHierarchy_AfterUpdate()
        {
            var retrieved = RetrieveSubmodelElementValue(submodel.IdShort, "TestIntegerProperty");
            retrieved.Success.Should().BeTrue();
            retrieved.Entity.Value.Should().BeEquivalentTo(5);
        }

        public IResult UpdateSubmodelElementValue(string submodelId, string seIdShortPath, IValue value)
        {
            return client.UpdateSubmodelElementValue(submodelId, seIdShortPath, value);
        }

        [TestMethod]
        public void Test81_DeleteSubmodelElement_FirstLevelHierarchy()
        {
            var deleted = DeleteSubmodelElement(submodel.IdShort, "TestIntegerProperty");
            deleted.Success.Should().BeTrue();
        }

        public IResult DeleteSubmodelElement(string submodelId, string seIdShortPath)
        {
            return client.DeleteSubmodelElement(submodelId, seIdShortPath);
        }

        public IResult<InvocationResponse> InvokeOperation(string submodelId, string operationIdShortPath, InvocationRequest invocationRequest)
        {
            return client.InvokeOperation(submodelId, operationIdShortPath, invocationRequest);
        }

        public IResult<CallbackResponse> InvokeOperationAsync(string submodelId, string operationIdShortPath, InvocationRequest invocationRequest)
        {
            return client.InvokeOperationAsync(submodelId, operationIdShortPath, invocationRequest);
        }

        public IResult<InvocationResponse> GetInvocationResult(string submodelId, string operationIdShortPath, string requestId)
        {
            return client.GetInvocationResult(submodelId, operationIdShortPath, requestId);
        }
    }
}
