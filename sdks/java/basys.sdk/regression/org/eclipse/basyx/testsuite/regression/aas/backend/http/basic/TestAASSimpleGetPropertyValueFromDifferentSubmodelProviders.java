package org.eclipse.basyx.testsuite.regression.aas.backend.http.basic;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ISingleProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.backend.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory;
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
class TestAASSimpleGetPropertyValueFromDifferentSubmodelProviders {
	

	/**
	 * Store HTTP asset administration shell manager backend
	 */
	protected ConnectedAssetAdministrationShellManager aasManager = new ConnectedAssetAdministrationShellManager(new TestsuiteDirectory());
	
	
	
	/**
	 * Run JUnit test case
	 */
	@Test
	void runTest() throws Exception {

		// Connect to AAS with ID "Stub1AAS"
		// - Retrieve connected AAS from AAS ID
		IAssetAdministrationShell connAAS = this.aasManager.retrieveAAS("Stub1AAS");
		
		
		// Connect to first sub model
		ISubModel submodel1 = ((HashMap<String, ISubModel>) connAAS.getSubModels()).get("statusSM");				

		// Connect to sub model property
		// - Get property values (shortcut here, we know that it is a single property type)
		int property1Val = (int) ((ISingleProperty) submodel1.getProperties().get("sampleProperty1")).get();
		int property2Val = (int) ((ISingleProperty) submodel1.getProperties().get("sampleProperty2")).get();

		
		// Connect to second sub model
		ISubModel submodel2 = ((HashMap<String, ISubModel>) connAAS.getSubModels()).get("Stub2SM");				

		// Connect to sub model property
		// - Get property values (shortcut here, we know that it is a single property type)
		int property3Val = (int) ((ISingleProperty) submodel2.getProperties().get("samplePropertyA")).get();
		int property4Val = (int) ((ISingleProperty) submodel2.getProperties().get("samplePropertyB")).get();

		
		// Check test case results
		assertTrue(property1Val == 2);
		assertTrue(property2Val == 3);
		assertTrue(property3Val == 5);
		assertTrue(property4Val == 6);
	}
}

