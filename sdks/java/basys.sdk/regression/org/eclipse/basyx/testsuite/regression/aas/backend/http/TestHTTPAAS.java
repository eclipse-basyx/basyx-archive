package org.eclipse.basyx.testsuite.regression.aas.backend.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.eclipse.basyx.aas.api.resources.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.ISubModel;
import org.eclipse.basyx.aas.backend.connected.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.testsuite.support.backend.http.tools.stubs.servlets.StubAASServlet;
import org.eclipse.basyx.testsuite.support.vab.stub.DirectoryServiceStub;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests access to an AAS provided by a servlet
 * 
 * @author schnicke
 *
 */
public class TestHTTPAAS {

	// Manager used to connect to the AAS
	ConnectedAssetAdministrationShellManager manager;

	/**
	 * Creates the manager to be used in the test cases
	 */
	@Before
	public void build() {
		// Fill directory stub
		DirectoryServiceStub directory = new DirectoryServiceStub();
		directory.addMapping(StubAASServlet.aasId, "http://localhost:8080/basys.sdk/Testsuite/StubAAS/");
		directory.addMapping(StubAASServlet.smId, "http://localhost:8080/basys.sdk/Testsuite/StubAAS/");

		// Create manager using the directory stub an the HTTPConnectorProvider
		manager = new ConnectedAssetAdministrationShellManager(new VABConnectionManager(directory, new HTTPConnectorProvider()));
	}

	/**
	 * Tests accessing an aas
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAAS() throws Exception {
		// Retrieve AAS
		IAssetAdministrationShell shell = manager.retrieveAAS(StubAASServlet.aasId);

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
	 */
	@Test
	public void testSubModel() {
		// Retrieve SubModel
		ISubModel sm = manager.retrieveSM(StubAASServlet.smId);

		// Check id
		assertEquals(StubAASServlet.smId, sm.getId());

		// TODO: Extend
	}
}
