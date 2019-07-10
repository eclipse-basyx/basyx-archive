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
		addMapping("product_database",          "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/office/"); //product_database/BaSys/1.0/provider");
		addMapping("product_database/aas",      "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/office/"); //product_database/BaSys/1.0/provider");
		
		// - Sub models of product_database
		addMapping("product_database/aas/submodels/products", "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/office/"); //products_product_database/BaSys/1.0/provider");

	
		// Define mappings for line 2 in domain manufacturing.de
		// - Asset administration shells
		addMapping("line2.manufacturing.de/device2",         "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/line1/gateway_line12/"); ///BaSys/1.0/provider");
		addMapping("line2.manufacturing.de/device2/aas",         "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/line1/gateway_line12/"); ///BaSys/1.0/provider");
		
		// - Sub models of device2 in line 2 in domain manufacturing.de
		addMapping("line2.manufacturing.de/device2/aas/submodels/description", "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/line1/gateway_line12/"); ///BaSys/1.0/provider");
		addMapping("line2.manufacturing.de/device2/aas/submodels/status",      "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/line1/gateway_line12/"); ///BaSys/1.0/provider");

	
		// Define gateway - mappings for line 2 internal in domain manufacturing.de
		// - Asset administration shells
		addMapping("line2int.line2.manufacturing.de/device10", "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/line1/gateway_line12/"); //BaSys/1.0/provider");
		addMapping("line2int.line2.manufacturing.de/device10/aas", "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/line1/gateway_line12/"); //BaSys/1.0/provider");
		// - Sub models of device2 in line 2 in domain manufacturing.de
		addMapping("line2int.line2.manufacturing.de/device10/aas/submodels/description", "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/line1/gateway_line12/"); // BaSys/1.0/provider");
		addMapping("line2int.line2.manufacturing.de/device10/aas/submodels/status",      "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/line1/gateway_line12/"); // BaSys/1.0/provider");
	}
}

