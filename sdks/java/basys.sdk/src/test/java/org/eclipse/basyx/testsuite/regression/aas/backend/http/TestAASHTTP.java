package org.eclipse.basyx.testsuite.regression.aas.backend.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.eclipse.basyx.aas.api.modelurn.ModelUrn;
import org.eclipse.basyx.aas.api.resources.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.IOperation;
import org.eclipse.basyx.aas.api.resources.IProperty;
import org.eclipse.basyx.aas.api.resources.ISingleProperty;
import org.eclipse.basyx.aas.api.resources.ISubModel;
import org.eclipse.basyx.aas.backend.connected.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.IdentifierType;
import org.eclipse.basyx.testsuite.support.backend.http.tools.stubs.servlets.StubAASServlet;
import org.eclipse.basyx.testsuite.support.backend.servers.AASHTTPServerResource;
import org.eclipse.basyx.testsuite.support.backend.servers.context.SdkRegressionContext;
import org.eclipse.basyx.testsuite.support.vab.stub.AASRegistryStub;
import org.eclipse.basyx.testsuite.support.vab.stub.DirectoryServiceStub;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * Tests access to an AAS provided by a servlet. This is an integration test
 * 
 * @author schnicke
 *
 */
public class TestAASHTTP {

	// Manager used to connect to the AAS
	ConnectedAssetAdministrationShellManager manager;

	/**
	 * Makes sure Tomcat Server is started
	 */
	@ClassRule
	public static AASHTTPServerResource res = new AASHTTPServerResource(new SdkRegressionContext());

	/**
	 * Creates the manager to be used in the test cases
	 */
	@Before
	public void build() {
		// Fill directory stub
		DirectoryServiceStub directory = new DirectoryServiceStub();
		directory.addMapping(StubAASServlet.aasId, "http://localhost:8080/basys.sdk/Testsuite/StubAAS/");
		directory.addMapping(StubAASServlet.smId, "http://localhost:8080/basys.sdk/Testsuite/StubAAS/");

		AASRegistryStub registry = new AASRegistryStub();

		// Create aas descriptor for the aas registry
		AASDescriptor aasDesriptor = new AASDescriptor(StubAASServlet.aasId, IdentifierType.URI,
				"http://localhost:8080/basys.sdk/Testsuite/StubAAS/");

		// Create the submodel descriptor
		SubmodelDescriptor submodelDescriptor = new SubmodelDescriptor(StubAASServlet.smId, IdentifierType.URI,
				"http://localhost:8080/basys.sdk/Testsuite/StubAAS/");

		// add submodel descriptor to the aas descriptor
		aasDesriptor.addSubmodelDescriptor(submodelDescriptor);

		// register the aas in the registry
		registry.register(new ModelUrn(StubAASServlet.aasId), aasDesriptor);
		
		// Create manager using the directory stub an the HTTPConnectorProvider
		manager = new ConnectedAssetAdministrationShellManager(registry, new HTTPConnectorProvider());
	}

	/**
	 * Tests accessing an aas
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAAS() throws Exception {
		// Retrieve AAS
		IAssetAdministrationShell shell = manager.retrieveAAS(new ModelUrn(StubAASServlet.aasId));

		// Check id
		assertEquals(StubAASServlet.aasId, shell.getId());

		// Retrieve submodels
		Map<String, ISubModel> submodels = shell.getSubModels();

		// Check content of submodels
		assertEquals(1, submodels.size());
		assertTrue(submodels.containsKey(StubAASServlet.smId));
	}

	/**
	 * Tests accessing a submodel
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSubModel() throws Exception {
		// Retrieve SubModel
		ISubModel sm = manager.retrieveSM(StubAASServlet.smId, new ModelUrn(StubAASServlet.aasId));

		// Check id
		assertEquals(StubAASServlet.smId, sm.getId());

		// TODO: Extend
		// - retrieve properties and operations

		Map<String, IProperty> properties = sm.getProperties();
		assertEquals(2, properties.size());
		ISingleProperty prop = (ISingleProperty) properties.get("integerProperty");
		assertEquals(123, prop.get());

		Map<String, IOperation> operations = sm.getOperations();
		assertEquals(4, operations.size());

		IOperation op = operations.get("complex");
		assertEquals(1, op.invoke(2, 1));

	}
}
