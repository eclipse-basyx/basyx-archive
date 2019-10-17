package org.eclipse.basyx.testsuite.regression.aas.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.preconfigured.PreconfiguredRegistry;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.IDataElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.property.ISingleProperty;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.identifier.IdentifierType;
import org.eclipse.basyx.testsuite.regression.aas.restapi.StubAASServlet;
import org.eclipse.basyx.testsuite.regression.vab.directory.DirectoryServiceStub;
import org.eclipse.basyx.testsuite.regression.vab.protocol.http.AASHTTPServerResource;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
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

	private static BaSyxContext context = new BaSyxContext("/basys.sdk", System.getProperty("java.io.tmpdir"))
			.addServletMapping("/Testsuite/StubAAS/*", new StubAASServlet());

	/**
	 * Makes sure Tomcat Server is started
	 */
	@ClassRule
	public static AASHTTPServerResource res = new AASHTTPServerResource(context);

	/**
	 * Creates the manager to be used in the test cases
	 */
	@Before
	public void build() {
		// Fill directory stub
		DirectoryServiceStub directory = new DirectoryServiceStub();
		directory.addMapping(StubAASServlet.AASID, "http://localhost:8080/basys.sdk/Testsuite/StubAAS/aas");
		directory.addMapping(StubAASServlet.SMID,
				"http://localhost:8080/basys.sdk/Testsuite/StubAAS/aas/submodels/" + StubAASServlet.SMID);

		PreconfiguredRegistry registry = new PreconfiguredRegistry();

		// Create aas descriptor for the aas registry
		IIdentifier id = new Identifier(IdentifierType.URI, StubAASServlet.AASURN.getURN());
		AASDescriptor aasDescriptor = new AASDescriptor(id,
				"http://localhost:8080/basys.sdk/Testsuite/StubAAS/aas");

		// Create the submodel descriptor
		SubmodelDescriptor submodelDescriptor = new SubmodelDescriptor(StubAASServlet.SMID, IdentifierType.URI,
				"http://localhost:8080/basys.sdk/Testsuite/StubAAS/aas/submodels/" + StubAASServlet.SMID);

		// add submodel descriptor to the aas descriptor
		aasDescriptor.addSubmodelDescriptor(submodelDescriptor);

		// register the aas in the registry
		registry.register(aasDescriptor);
		
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
		IAssetAdministrationShell shell = manager.retrieveAAS(StubAASServlet.AASURN);

		// Check id
		assertEquals(StubAASServlet.AASID, shell.getIdShort());

		// Retrieve submodels
		Map<String, ISubModel> submodels = shell.getSubModels();

		// Check content of submodels
		assertEquals(1, submodels.size());
		assertTrue(submodels.containsKey(StubAASServlet.SMID));
	}

	/**
	 * Tests accessing a submodel
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSubModel() throws Exception {
		// Retrieve SubModel
		ISubModel sm = manager.retrieveSubModel(StubAASServlet.AASURN, StubAASServlet.SMID);

		// Check id
		assertEquals(StubAASServlet.SMID, sm.getIdShort());

		// TODO: Extend
		// - retrieve properties and operations

		Map<String, IDataElement> properties = sm.getDataElements();
		assertEquals(3, properties.size());
		ISingleProperty prop = (ISingleProperty) properties.get("integerProperty");
		assertEquals(123, prop.get());

		Map<String, IOperation> operations = sm.getOperations();
		assertEquals(4, operations.size());

		IOperation op = operations.get("complex");
		assertEquals(1, op.invoke(2, 1));

	}
}
