package org.eclipse.basyx.testsuite.regression.aas.backend.http.basic;

import static org.junit.Assert.assertTrue;
import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ICollectionProperty;
import org.eclipse.basyx.aas.api.resources.basic.IMapProperty;
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

	// Retrieve connected AAS from AAS ID
	IAssetAdministrationShell connAAS = this.aasManager.retrieveAAS("RestAAS");

	// Connect to sub model		
	ISubModel submodel = connAAS.getSubModels().get("description");		

	/**
	 * Test new REST API
	 */
	@Test
	void runTest() throws Exception {

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
	
	/**
	 * Test to add a new value to an existing collection
	 * @throws Exception
	 */
	@Test
	public void testCollections() throws Exception {
		
		// Connect to property 
		ICollectionProperty testCollection= (ICollectionProperty) submodel.getProperties().get("testCollection");
				
		// Test to add a new value to an existing map or collection
		testCollection.add(59);
		
		// Clear cache
		((ConnectedElement) testCollection).invalidate();
		
		int val = (int) testCollection.get(testCollection.getElementCount() - 1);
		assertTrue(val == 59);
		
		// Test to remove a value from an existing map or collection by key
		testCollection.remove(59);
		
		// Clear cache
		((ConnectedElement) testCollection).invalidate();
	
		val = (int) testCollection.get(testCollection.getElementCount() - 1);
		assertTrue(val != 59);
		
	}
	
	/**
	 * Test to add a new entry to an existing map 
	 * @throws Exception
	 */
	@Test
	public void testMaps() throws Exception {
		
		// Connect to property 
		IMapProperty testMap= (IMapProperty) submodel.getProperties().get("testMap");
				
		// Test to add a new value to an existing map or collection
		testMap.put("ten", "isten");
		
		// Clear cache
		((ConnectedElement) testMap).invalidate();
		
		String ten = (String) testMap.getValue("ten");
		assertTrue(ten.equals("isten"));
		
		// Test to remove a value from an existing map or collection by key
		testMap.remove("ten");
		
		// Clear cache again		
		((ConnectedElement) testMap).invalidate();

		assertTrue(((String) testMap.getValue("ten") == null));
		
	}
}

