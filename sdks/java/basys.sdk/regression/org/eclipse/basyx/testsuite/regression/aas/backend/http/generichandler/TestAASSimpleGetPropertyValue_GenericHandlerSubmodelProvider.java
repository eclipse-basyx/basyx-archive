package org.eclipse.basyx.testsuite.regression.aas.backend.http.generichandler;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ISingleProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.backend.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory;
import org.junit.jupiter.api.Test;




/**
 * AAS Operations test case
 * 
 * Context: Static configured topology
 * - Connect to AAS "Stub3AAS"
 * - Connect to sub model "Stub3SM"
 * - Connect to properties "testProp1" and "testProp2"
 * - Retrieve values of both properties
 * 
 * @author kuhn
 *
 */
class TestAASSimpleGetPropertyValue_GenericHandlerSubmodelProvider {
	

	/**
	 * Store HTTP asset administration shell manager backend
	 */
	protected ConnectedAssetAdministrationShellManager aasManager = new ConnectedAssetAdministrationShellManager(new TestsuiteDirectory(), new HTTPConnectorProvider());
	
	
	
	/**
	 * Run JUnit test case
	 */
	@Test
	void runTest() throws Exception {

		// Connect to AAS with ID "Stub3AAS"
		// - Retrieve connected AAS from AAS ID
		IAssetAdministrationShell connAAS = this.aasManager.retrieveAAS("Stub3AAS");
		
		
		// Connect to sub model
		// - FIXME: We need to define technology independent query String for directory (e.g. status.sampleAAS)
		// - Retrieve connected sub model
		// - FIXME: Get submodel by type or ID		
		ISubModel submodel = ((HashMap<String, ISubModel>) connAAS.getSubModels()).get("Stub3SM");		
		
		
		// Connect to sub model property
		// - Get property values (shortcut here, we know that it is a single property type)
		int property1Val = (int) ((ISingleProperty) submodel.getProperties().get("testProp1")).get();
		int property2Val = (int) ((ISingleProperty) submodel.getProperties().get("testProp2")).get();
		
		// Check test case results
		assertTrue(property1Val == 1);
		assertTrue(property2Val == 2);
	}
}

