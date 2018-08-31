package org.eclipse.basyx.testsuite.regression.aas.backend.basyx.basic;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ISingleProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.backend.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.ConnectedElement;
import org.eclipse.basyx.aas.backend.connector.basyx.BaSyxConnectorProvider;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory_BaSyxNative;
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
class TestAASSimpleSetPropertyValue {

	/**
	 * Store HTTP asset administration shell manager backend
	 */
	protected ConnectedAssetAdministrationShellManager aasManager = new ConnectedAssetAdministrationShellManager(new TestsuiteDirectory_BaSyxNative(), new BaSyxConnectorProvider());
	protected ConnectedAssetAdministrationShellManager aasManager2 = new ConnectedAssetAdministrationShellManager(new TestsuiteDirectory_BaSyxNative(), new BaSyxConnectorProvider());

	
	
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
		// - FIXME: We need to define technology independent query String for directory (e.g. status.sampleAAS)
		// - Retrieve connected sub model
		// - FIXME: Get submodel by type or ID		
		ISubModel submodel = connAAS.getSubModels().get("statusSM");		
		ISubModel submodel2 = connAAS2.getSubModels().get("statusSM");		

		
		
		// Connect to sub model property and change values
		// - Change property values (shortcut here, we know that it is a single property type)
		((ISingleProperty) submodel.getProperties().get("sampleProperty1")).set(5);
		((ISingleProperty) submodel.getProperties().get("sampleProperty2")).set(6);
		
		
		// - Get property values (shortcut again)
		int property1Val = (int) ((ISingleProperty) submodel.getProperties().get("sampleProperty1")).get();
		int property2Val = (int) ((ISingleProperty) submodel.getProperties().get("sampleProperty2")).get();

		// Check changed property values
		assertTrue(property1Val == 5);
		assertTrue(property2Val == 6);


		// Other connection changes properties
		((ISingleProperty) submodel2.getProperties().get("sampleProperty1")).set(2);
		((ISingleProperty) submodel2.getProperties().get("sampleProperty2")).set(3);
		
		// Invalidate information of first connection
		((ConnectedElement) submodel.getProperties().get("sampleProperty1")).invalidate();
		((ConnectedElement) submodel.getProperties().get("sampleProperty2")).invalidate();
		
		// - Get property values (shortcut again)
		property1Val = (int) ((ISingleProperty) submodel.getProperties().get("sampleProperty1")).get();
		property2Val = (int) ((ISingleProperty) submodel.getProperties().get("sampleProperty2")).get();

		// Check test case results
		assertTrue(property1Val == 2);
		assertTrue(property2Val == 3);
	}
}

