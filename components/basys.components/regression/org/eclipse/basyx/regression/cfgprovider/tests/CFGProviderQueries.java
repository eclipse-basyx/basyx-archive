package org.eclipse.basyx.regression.cfgprovider.tests;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ISingleProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.backend.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.regression.support.directory.ComponentsTestsuiteDirectory;
import org.junit.jupiter.api.Test;



/**
 * Test queries to CFG file provider
 * 
 * @author kuhn
 *
 */
class CFGProviderQueries {

	
	/**
	 * Store HTTP asset administration shell manager backend
	 */
	protected ConnectedAssetAdministrationShellManager aasManager = new ConnectedAssetAdministrationShellManager(new ComponentsTestsuiteDirectory(), new HTTPConnectorProvider());

	
	/**
	 * Test basic queries
	 */
	@Test
	void test() throws Exception {

		// Connect to AAS with ID "Stub3AAS"
		// - Retrieve connected AAS from AAS ID
		IAssetAdministrationShell connAAS = this.aasManager.retrieveAAS("CfgFileTestAAS");


		// Connect to sub model
		// - FIXME: We need to define technology independent query String for directory (e.g. status.sampleAAS)
		// - Retrieve connected sub model
		// - FIXME: Get submodel by type or ID		
		ISubModel submodel = connAAS.getSubModels().get("sampleCFG");		


		// Connect to sub model operations
		// - Retrieve property "sensorNames", which is provided by a SQL database
		String property1Val = (String) ((ISingleProperty) submodel.getProperties().get("cfgProperty1")).get();
		String property2Val = (String) ((ISingleProperty) submodel.getProperties().get("cfgProperty2")).get();
		String property3Val = (String) ((ISingleProperty) submodel.getProperties().get("cfgProperty3")).get();

		
		// Check property values
		assertTrue(property1Val.equals("exampleStringValue"));
		assertTrue(property2Val.equals("12"));
		assertTrue(property3Val.equals("45.8"));
	}
}
