package org.eclipse.basyx.testsuite.regression.aas.backend.http.basic;

import static org.junit.Assert.assertTrue;
import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ICollectionProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.backend.ConnectedAssetAdministrationShellManager;
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


class TestAASCollectionAddDeleteMember {
	

	@Rule
	public final ExpectedException exception = ExpectedException.none();


	protected ConnectedAssetAdministrationShellManager aasManager = new ConnectedAssetAdministrationShellManager(new TestsuiteDirectory());
	
	
	
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
		ICollectionProperty property4 = ((ICollectionProperty) submodel.getProperties().get("sampleProperty4"));
		
		// Add member to collection
		property4.add(5);
		
		// Check if the item has been added
		int five = (int) property4.get(1);
		assertTrue(five==5);
		
		// Check if an item has been added 
		System.out.println("size = " + property4.getElementCount());
		
		// Delete member from collection
		property4.remove(5);
		
		// Check if the item has been removed 
		System.out.println("size = " + property4.getElementCount());
		
		assertTrue(property4.getElementCount() == 1);
		

	}
}

