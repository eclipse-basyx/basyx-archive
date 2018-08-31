package org.eclipse.basyx.testsuite.regression.aas.backend.http.basic;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.backend.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory;
import org.junit.jupiter.api.Test;




/**
 * AAS Operations test case
 * 
 * Context: Static configured topology
 * - Connect to AAS "Stub1AAS"
 * - Retrieve sub model proxies
 * - Connect to sub model "statusSM" of AAS "Stub1AAS"
 * - Retrieve list of top-level properties
 * 
 * @author kuhn
 *
 */
class TestAASSimpleStructure {

	/**
	 * Store HTTP asset administration shell manager backend
	 */
	protected ConnectedAssetAdministrationShellManager aasManager = new ConnectedAssetAdministrationShellManager(new TestsuiteDirectory(), new HTTPConnectorProvider());
	
	
	
	/**
	 * Run JUnit test case
	 */
	@Test
	void runTest() throws Exception {

		// Connect to AAS with ID "Stub1AAS"
		// - Retrieve connected AAS from AAS ID
		IAssetAdministrationShell connAAS = this.aasManager.retrieveAAS("Stub1AAS");
		// - Retrieve AAS sub models
		Collection<String> submodels = connAAS.getSubModels().keySet();
		
		// Check sub model count (expected 2)
		assertTrue(submodels.size() == 2);
		
		
		// Connect to sub model
		// - FIXME: We need to define technology independent query String for directory (e.g. status.sampleAAS)
		// - Retrieve connected sub model
		// - FIXME: Get submodel by type or ID		
		ISubModel submodel = connAAS.getSubModels().get("statusSM");


		// Retrieve sub model properties
		Collection<String> properties = submodel.getProperties().keySet();
		System.out.println("pop:"+properties);
		// Check property count (expected 11)
		assertTrue(properties.size() == 11);
	}
}

