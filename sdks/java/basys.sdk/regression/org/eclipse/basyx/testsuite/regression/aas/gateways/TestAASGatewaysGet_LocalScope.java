package org.eclipse.basyx.testsuite.regression.aas.gateways;

import static org.junit.Assert.assertTrue;
import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ISingleProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.backend.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.connector.http.HTTPConnector;
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
 * Test case: Connect from office.iese.fraunhofer to product_database.office.iese.fraunhofer via http-rest web services
 * 
 * @author kuhn
 *
 */
class TestAASGatewaysGet_LocalScope {
	

	/**
	 * Store HTTP asset administration shell manager backend for office.iese.fraunhofer
	 */
	protected ConnectedAssetAdministrationShellManager aasManager = new ConnectedAssetAdministrationShellManager(new GatewayTestsuiteDirectoryLine1IESE(), new HTTPConnector());
	
	
	
	/**
	 * Run JUnit test case
	 */
	@Test
	void runTest() throws Exception {

		// Connect to AAS with ID "aas.product_database" (local scope in iese.fraunhofer)
		// - Retrieve connected AAS from AAS ID
		IAssetAdministrationShell databaseAAS1 = this.aasManager.retrieveAAS("product_database");
		
		
		// Connect to sub model
		ISubModel submodel1 = databaseAAS1.getSubModels().get("products");
		
		
		// Connect to sub model property
		// - Get property values (shortcut here, we know that it is a single property type)
		int property1Val = (int) ((ISingleProperty) submodel1.getProperties().get("product1Size")).get();

		
		// Check test case results
		assertTrue(property1Val == 22);
	}
}

