package org.eclipse.basyx.testsuite.regression.aas.backend.http.basic;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ISingleProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.backend.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
	protected ConnectedAssetAdministrationShellManager aasManager = new ConnectedAssetAdministrationShellManager(new TestsuiteDirectory());
	
	// Connect to AAS with ID "Stub1AAS"
	// - Retrieve connected AAS from AAS ID
	IAssetAdministrationShell connAAS = this.aasManager.retrieveAAS("Stub1AAS");
	
	
	// Connect to sub model and properties
	ISubModel 		submodel =  ((HashMap<String, ISubModel>) connAAS.getSubModels()).get("statusSM");	
	ISingleProperty property1 = ((ISingleProperty) 			  submodel.getProperties().get("sampleProperty1"));
	ISingleProperty property2 = ((ISingleProperty) 			  submodel.getProperties().get("sampleProperty2"));
	
	/**
	 * Reset Property Values
	 * @throws ServerException 
	 */
	@BeforeEach
    void init() throws ServerException {
		property1.set(2);
		property2.set(3);
    }
	
	/**
	 * Run JUnit test case 1: Test get and set manually without Move operator
	 */ 
	@Test
	void testManualMove() throws Exception {
		
		int property1Val = (int) property1.get(); // 2
		property2.set(property1Val);
		
		// Check if value has been set correctly
		int property2Val = (int) property2.get();
		assertTrue(property2Val == 2);
		
		
	}
	
	/**
	 * Run JUnit test case 2: Automated with MOVE operator Reset property values
	 */ 
	@Test
	void testAutomatedMove() throws Exception {
		// Test 2) 
		property1.moveTo(property2);
		
		// Check if value has been set correctly
		int property2Val = (int) property2.get();
		assertTrue(property2Val == 2);
		
	
		 
	}
	
	/**
	 * Reset property values
	 * @throws Exception 
	 */
	@AfterEach
    void tearDown() throws Exception {
		property2.set(3);
		int property2Val = (int) property2.get();
		assertTrue(property2Val == 3);
	}
}

