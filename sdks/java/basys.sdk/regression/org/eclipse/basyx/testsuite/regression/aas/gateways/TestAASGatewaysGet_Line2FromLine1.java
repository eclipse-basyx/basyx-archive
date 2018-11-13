package org.eclipse.basyx.testsuite.regression.aas.gateways;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.api.resources.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.ISingleProperty;
import org.eclipse.basyx.aas.api.resources.ISubModel;
import org.eclipse.basyx.aas.backend.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.testsuite.support.gateways.common.directory.GatewayTestsuiteDirectoryLine1IESE;
import org.junit.jupiter.api.Test;




/**
 * AAS gateways test case
 * 
 * Connect BaSys asset administration shells across networks/technologies
 * 
 * Network hierarchy
 * - iese.fraunhofer.de
 *   - office
 *     - manufacturing_controller
 *     - product_database
 *       + products
 * - manufacturing.de
 *   - line2
 *     - device2
 *       + description
 *       + status
 *     - line2int
 *       - device10
 *         + description
 *         + status
 * 
 * 
 * Test case: Connect from office.iese.fraunhofer to device2.line2.manufacturing.de via http-rest web services
 * 
 * @author kuhn
 *
 */
class TestAASGatewaysGet_Line2FromLine1 {
	

	/**
	 * Store HTTP asset administration shell manager backend for office.iese.fraunhofer.de
	 */
	protected ConnectedAssetAdministrationShellManager aasManager = new ConnectedAssetAdministrationShellManager(new GatewayTestsuiteDirectoryLine1IESE(), new HTTPConnectorProvider());
	
	
	
	/**
	 * Run JUnit test case
	 */
	@Test
	void runTest() throws Exception {

		// Connect to AAS with ID "aas.device2.line2.manufacturing.de"
		// - Retrieve connected AAS from AAS ID
		IAssetAdministrationShell tempsensorAAS1 = this.aasManager.retrieveAAS("line2.manufacturing.de/device2");
		
		
		// Connect to sub models
		ISubModel submodel1 = tempsensorAAS1.getSubModels().get("description");
		ISubModel submodel2 = tempsensorAAS1.getSubModels().get("status");
		
		
		// Connect to sub model property
		// - Get property values (shortcut here, we know that it is a single property type)
		String  propertyVal1 = (String)  ((ISingleProperty) submodel1.getProperties().get("productDescription")).get();
		boolean propertyVal2 = (boolean) ((ISingleProperty) submodel2.getProperties().get("isReady")).get();
		
		// Check test case results
		assertTrue(propertyVal1.equals("device description"));
		assertTrue(propertyVal2 == true);
	}
}

