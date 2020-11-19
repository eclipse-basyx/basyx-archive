package org.eclipse.basyx.testsuite.regression.aas.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.memory.InMemoryRegistry;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.testsuite.regression.aas.restapi.StubAASServlet;
import org.eclipse.basyx.testsuite.regression.vab.protocol.http.AASHTTPServerResource;
import org.eclipse.basyx.vab.directory.memory.InMemoryDirectory;
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
		InMemoryDirectory directory = new InMemoryDirectory();
		directory.addMapping(StubAASServlet.AASURN.getId(), "http://localhost:8080/basys.sdk/Testsuite/StubAAS/aas");
		directory.addMapping(StubAASServlet.SMURN.getId(),
				"http://localhost:8080/basys.sdk/Testsuite/StubAAS/aas/submodels/" + StubAASServlet.SMIDSHORT + "/submodel");

		InMemoryRegistry registry = new InMemoryRegistry();

		// Create aas descriptor for the aas registry
		AASDescriptor aasDescriptor = new AASDescriptor(StubAASServlet.AASURN,
				"http://localhost:8080/basys.sdk/Testsuite/StubAAS/aas");

		// Create the submodel descriptor
		SubmodelDescriptor submodelDescriptor = new SubmodelDescriptor(StubAASServlet.SMIDSHORT, StubAASServlet.SMURN,
				"http://localhost:8080/basys.sdk/Testsuite/StubAAS/aas/submodels/" + StubAASServlet.SMIDSHORT + "/submodel");

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
		assertEquals(StubAASServlet.AASIDSHORT, shell.getIdShort());

		// Retrieve submodels
		Map<String, ISubModel> submodels = shell.getSubModels();

		// Check content of submodels
		assertEquals(1, submodels.size());
		assertTrue(submodels.containsKey(StubAASServlet.SMIDSHORT));
	}

	/**
	 * Tests accessing a submodel
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSubModel() throws Exception {
		// Retrieve SubModel
		ISubModel sm = manager.retrieveSubModel(StubAASServlet.AASURN, StubAASServlet.SMURN);

		// Check id
		assertEquals(StubAASServlet.SMIDSHORT, sm.getIdShort());

		// TODO: Extend
		// - retrieve properties and operations

		Map<String, IProperty> properties = sm.getProperties();
		// 2 properties -> SMElementCollections don't count
		assertEquals(3, properties.size());
		IProperty prop = properties.get("integerProperty");
		assertEquals(123, prop.getValue());

		Map<String, IOperation> operations = sm.getOperations();
		assertEquals(4, operations.size());

		IOperation op = operations.get("complex");
		assertEquals(1, op.invoke(2, 1));

		Map<String, ISubmodelElement> elements = sm.getSubmodelElements();
		// 2 properties, 4 operations, 1 collection
		assertEquals(8, elements.size());

	}
}
