package org.eclipse.basyx.testsuite.regression.aas.backend.http.basic;

import static org.junit.Assert.assertTrue;
import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ISingleProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.backend.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.ConnectedElement;
import org.eclipse.basyx.aas.backend.ConnectedOperation;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory;
import org.junit.jupiter.api.Test;




/**
 * Testing the new REST Interface
 * 
 *
 * 
 * @author pschorn
 *
 */
class TestAASSimpleNewRESTInterface {

	/**
	 * Store HTTP asset administration shell manager backend
	 */
	protected ConnectedAssetAdministrationShellManager aasManager = new ConnectedAssetAdministrationShellManager(new TestsuiteDirectory());

	
	/**
	 * Test Basys Set method implementing HTTP PATCH
	 */
	@Test
	void testPatch() throws Exception {

		// Retrieve connected AAS from AAS ID
		IAssetAdministrationShell connAAS = this.aasManager.retrieveAAS("RestAAS");

		// Connect to sub model		
		ISubModel submodel = connAAS.getSubModels().get("description");		

		// Set property value
		String expected = "tasty product";
		ISingleProperty property = ((ISingleProperty) submodel.getProperties().get("productDescription"));
		property.set(expected);
		
		// Get property value from server
		((ConnectedElement) property).invalidate();
		String desc = (String) property.get();
		
		assertTrue(desc.equals(expected));
		
		// Connect to sub model		
		ISubModel statusSM = connAAS.getSubModels().get("status");		
		
		// Invoke operation
		int a = 10;
		int b = 2;
		int result = (int) ((ConnectedOperation) statusSM.getOperations().get("sub")).invoke(a, b);
		
		int expected2 = a - b;
		
		assertTrue(result == expected2);
	}
}

