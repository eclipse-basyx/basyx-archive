package org.eclipse.basyx.testsuite.regression.aas.backend.http.basic;

import static org.junit.Assert.assertNull;
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


class TestAASMapAddDeleteMember {
	

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
		
		// add member entry to collection
		property6.put("five", 5);
		
		// check if member has been added
		int five = (int) property6.getValue("five");
		assertTrue(five==5);
		
		// delete entry
		property6.remove("five");
		
		// check if the member has been removed
		Object _null = property6.getValue("five");
		assertNull(_null);
		
		// check if size = 1
		int size = property6.getKeys().size();
		assertTrue(size == property6.getEntryCount() && size == 1);
	}
}

