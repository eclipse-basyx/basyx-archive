package org.eclipse.basyx.testsuite.support.gateways.common.directory;

import org.eclipse.basyx.aas.impl.services.PreconfiguredDirectory;




/**
 * Implement the test suite directory service for line 1 (office.iese.fraunhofer.de) with pre-configured directory entries
 * 
 * @author kuhn
 *
 */
public class GatewayTestsuiteDirectoryLine1IESE extends PreconfiguredDirectory {

	
	/**
	 * Constructor - load all directory entries
	 */
	public GatewayTestsuiteDirectoryLine1IESE() {
		// Define mappings for office.iese.fraunhofer.de
		// - Asset administration shells
		addMapping("product_database/aas",      "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/office/"); //product_database/BaSys/1.0/provider");
		//addMapping("product_database",          "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/office/product_database/BaSys/1.0/provider");
		
		// - Sub models of product_database
		addMapping("product_database/aas/submodels/products", "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/office/"); //products_product_database/BaSys/1.0/provider");

	
		// Define mappings for line 2 in domain manufacturing.de
		// - Asset administration shells
		addMapping("device2.line2.manufacturing.de/aas",         "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/line1/gateway_line12"); ///BaSys/1.0/provider");
		
		// - Sub models of device2 in line 2 in domain manufacturing.de
		addMapping("device2.line2.manufacturing.de/aas/submodels/description", "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/line1/gateway_line12"); ///BaSys/1.0/provider");
		addMapping("device2.line2.manufacturing.de/aas/submodels/status",      "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/line1/gateway_line12"); ///BaSys/1.0/provider");

	
		// Define gateway - mappings for line 2 internal in domain manufacturing.de
		// - Asset administration shells
		addMapping("device10.line2int.line2.manufacturing.de/aas", "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/line1/gateway_line12/"); //BaSys/1.0/provider");
		// - Sub models of device2 in line 2 in domain manufacturing.de
		addMapping("device10.line2int.line2.manufacturing.de/aas/submodels/description", "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/line1/gateway_line12/"); // BaSys/1.0/provider");
		addMapping("device10.line2int.line2.manufacturing.de/aas/submodels/status",      "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/line1/gateway_line12/"); // BaSys/1.0/provider");
	}
}

