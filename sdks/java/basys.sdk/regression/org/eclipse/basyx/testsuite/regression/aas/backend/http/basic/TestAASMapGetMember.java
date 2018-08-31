package org.eclipse.basyx.testsuite.regression.aas.backend.http.basic;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.IMapProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.backend.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;




/**
 * AAS Operations test case
 * 
 * Context: Static configured topology
 * - Connect to AAS "Stub1AAS"
 * - Connect to sub model "statusSM"
 * - Connect to properties "sampleProperty1" and "sampleProperty2"
 * - Retrieve values of both properties
 * 
 * @author kuhn
 *
 */


class TestAASMapGetMember {
	

	@Rule
	public final ExpectedException exception = ExpectedException.none();


	protected ConnectedAssetAdministrationShellManager aasManager = new ConnectedAssetAdministrationShellManager(new TestsuiteDirectory(), new HTTPConnectorProvider());
	
	
	
	/**
	 * Run JUnit test case
	 */
	@Test
	void runTest() throws Exception {

		// Connect to AAS with ID "Stub1AAS"
		// Retrieve connected AAS from AAS ID
		IAssetAdministrationShell connAAS = this.aasManager.retrieveAAS("Stub1AAS");
		
		// Retrieve connected submodel
		ISubModel submodel = connAAS.getSubModels().get("statusSM");				
		
		// Connect to property 
		IMapProperty property6 = (IMapProperty) submodel.getProperties().get("sampleProperty6");
		
		// Execute GET
		int val6 = (int) property6.getValue("magic");
		
		// Check if the item has been added
		assertTrue(val6 == 42);
		

	}
}

