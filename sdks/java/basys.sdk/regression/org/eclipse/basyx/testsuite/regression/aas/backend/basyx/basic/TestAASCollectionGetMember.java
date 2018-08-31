package org.eclipse.basyx.testsuite.regression.aas.backend.basyx.basic;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ICollectionProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.backend.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.connector.basyx.BaSyxConnectorProvider;
import org.eclipse.basyx.aas.impl.reference.ElementRef;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory_BaSyxNative;
import org.junit.jupiter.api.Test;




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
class TestAASCollectionGetMember {
	

	/**
	 * Store HTTP asset administration shell manager backend
	 */
	protected ConnectedAssetAdministrationShellManager aasManager = new ConnectedAssetAdministrationShellManager(new TestsuiteDirectory_BaSyxNative(), new BaSyxConnectorProvider());

	
	
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
				
		
		// Retrieve connected Properties
		ICollectionProperty property4 = (ICollectionProperty) submodel.getProperties().get("sampleProperty4"); 
		ICollectionProperty property5 = (ICollectionProperty) submodel.getProperties().get("sampleProperty5");
		
		// Execute GET 
		int val4 = (int) property4.get(0);
		ElementRef val5 = (ElementRef) property5.get(0);
		// int val6 = (int) property6.get("magic");
		
		// Check test case results
		assertTrue(val4 == 42);
		assertTrue(val5.getId().equals("sampleProperty2"));
		// assertTrue(val6 == 42);
	}
}

