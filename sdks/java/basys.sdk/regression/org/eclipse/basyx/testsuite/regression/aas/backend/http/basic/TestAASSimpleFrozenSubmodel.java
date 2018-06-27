package org.eclipse.basyx.testsuite.regression.aas.backend.http.basic;

import static org.junit.Assert.assertTrue;
import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ISingleProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.backend.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.ConnectedElement;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory;
import org.junit.jupiter.api.Test;




/**
 * AAS Operations test case
 * 
 * Context: Static configured topology
 * - Retrieve AAS "Stub1AAS"
 * 
 * @author kuhn
 *
 */
class TestAASSimpleFrozenSubmodel {

	/**
	 * Store HTTP asset administration shell manager backend
	 */
	protected ConnectedAssetAdministrationShellManager aasManager = new ConnectedAssetAdministrationShellManager(new TestsuiteDirectory());
	protected ConnectedAssetAdministrationShellManager aasManager2 = new ConnectedAssetAdministrationShellManager(new TestsuiteDirectory());

	
	
	/**
	 * Run JUnit test case
	 */
	@Test
	void runTest() throws Exception {

		// Connect to AAS with ID "Stub1AAS"
		// - Retrieve connected AAS from AAS ID
		IAssetAdministrationShell connAAS = this.aasManager.retrieveAAS("Stub1AAS");
		IAssetAdministrationShell connAAS2 = this.aasManager2.retrieveAAS("Stub1AAS");

		
		// Connect to sub model
		ISubModel submodel = connAAS.getSubModels().get("statusSM");		
		ISubModel submodel2 = connAAS2.getSubModels().get("statusSM");		

		
		
		// Connect to sub model property and change value
		((ISingleProperty) submodel.getProperties().get("sampleProperty1")).set(4);
		
		
		// Freeze Submodel
		submodel.freeze();
		assertTrue(submodel.isFrozen());
		
		// Test if you can still change any property
		((ISingleProperty) submodel.getProperties().get("sampleProperty1")).set(6);
		((ISingleProperty) submodel2.getProperties().get("sampleProperty1")).set(7);
		
		((ConnectedElement) submodel.getProperties().get("sampleProperty1")).invalidate();
		int property1Val = (int) ((ISingleProperty) submodel.getProperties().get("sampleProperty1")).get();
		
		// Test if property value has (not) changed
		assertTrue(property1Val == 4);
		
		// Unfreeze Submodel
		submodel.unfreeze();
		assertTrue(!submodel.isFrozen());
		
		// Reset property value
		((ISingleProperty) submodel.getProperties().get("sampleProperty1")).set(2);
		((ISingleProperty) submodel.getProperties().get("sampleProperty2")).set(3);
		
		// Check if reset ok
		((ConnectedElement) submodel.getProperties().get("sampleProperty1")).invalidate();
		property1Val = (int) ((ISingleProperty) submodel.getProperties().get("sampleProperty1")).get();
		assertTrue(property1Val == 2);

	}
}

