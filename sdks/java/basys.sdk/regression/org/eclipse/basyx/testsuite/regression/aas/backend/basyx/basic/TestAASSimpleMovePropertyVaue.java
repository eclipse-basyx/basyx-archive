package org.eclipse.basyx.testsuite.regression.aas.backend.basyx.basic;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ISingleProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.backend.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.connector.basyx.BaSyxConnectorProvider;
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
public class TestAASSimpleMovePropertyVaue {
	

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
		// - Retrieve connected AAS from AAS ID
		IAssetAdministrationShell connAAS = this.aasManager.retrieveAAS("Stub1AAS");
		
		
		// Connect to sub model and properties
		ISubModel 		submodel =  ((HashMap<String, ISubModel>) connAAS.getSubModels()).get("statusSM");		
		ISingleProperty property1 = ((ISingleProperty) 			  submodel.getProperties().get("sampleProperty1"));
		ISingleProperty property2 = ((ISingleProperty) 			  submodel.getProperties().get("sampleProperty2"));
		
		property1.set(2);
		property2.set(3);
		
		// Test 1) Manual without Move operator
		int property1Val = (int) property1.get(); // 2
		property2.set(property1Val);
		
		// Check if value has been set correctly
		int property2Val = (int) property2.get();
		assertTrue(property2Val == 2);
		
		// Reset property values
		property2.set(3);
		property2Val = (int) property2.get();
		assertTrue(property2Val == 3);
		
		//-----------------------------------------------------------
		// Test 2) Automated with MOVE operator Reset property values
		property1.moveTo(property2);
		
		// Check if value has been set correctly
		property2Val = (int) property2.get();
		assertTrue(property2Val == 2);
		
		// Reset property values
		property2.set(3);
		property2Val = (int) property2.get();
		assertTrue(property2Val == 3);
		 
	}
}

