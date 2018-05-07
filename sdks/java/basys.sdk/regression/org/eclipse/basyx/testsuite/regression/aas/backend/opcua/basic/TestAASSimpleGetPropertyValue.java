package org.eclipse.basyx.testsuite.regression.aas.backend.opcua.basic;


import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ISingleProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.backend.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.connector.opcua.OPCUAConnector;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.OPCUATestsuiteDirectory;
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
 * @author kuhn, pschorn
 *
 */
class TestAASSimpleGetPropertyValue {

	/**
	 * Store HTTP asset administration shell manager backend
	 */
	protected ConnectedAssetAdministrationShellManager aasManager = new ConnectedAssetAdministrationShellManager(new OPCUATestsuiteDirectory());
	
	/**
	 * Run JUnit test case
	 */
	@Test
	void runTest() throws Exception {
		
		// Set connection technology to opcua
		aasManager.setConnector(new OPCUAConnector());

		// Connect to AAS with ID "Stub1AAS" Retrieve connected AAS from AAS ID
		IAssetAdministrationShell connAAS = this.aasManager.retrieveAAS("Stub1AAS");
		
		
		// Connect to sub model
		ISubModel submodel = ((HashMap<String, ISubModel>) connAAS.getSubModels()).get("statusSM");		
		
		
		// Connect to sub model property
		// - Get property values (shortcut here, we know that it is a single property type)
		int property1Val = (int) ((ISingleProperty) submodel.getProperties().get("sampleProperty1")).get();
		int property2Val = (int) ((ISingleProperty) submodel.getProperties().get("sampleProperty2")).get();
		
		
		// Check test case results
		assertTrue(property1Val == 2);
		assertTrue(property2Val == 3);
		 
	}
}

