package org.eclipse.basyx.testsuite.support.gateways.common.directory;

import org.eclipse.basyx.aas.impl.services.PreconfiguredDirectory;




/**
 * Implement the test suite directory service with pre-configured directory entries
 * 
 * @author kuhn
 *
 */
public class GatewayTestsuiteDirectoryLine2IESE extends PreconfiguredDirectory {

	
	/**
	 * Constructor - load all directory entries
	 */
	public GatewayTestsuiteDirectoryLine2IESE() {
		// Define mappings for line 2 in domain manufacturing.de
		// - Asset administration shells
		addMapping("aas.device2.line2.manufacturing.de", "http://localhost:8080/basys.sdk/Testsuite/GW/Manufacturing/line2/Device2/BaSys/1.0/provider");
		addMapping("aas.device2.line2",                  "http://localhost:8080/basys.sdk/Testsuite/GW/Manufacturing/line2/Device2/BaSys/1.0/provider");
		addMapping("aas.device2",                        "http://localhost:8080/basys.sdk/Testsuite/GW/Manufacturing/line2/Device2/BaSys/1.0/provider");
		addMapping("device2",                            "http://localhost:8080/basys.sdk/Testsuite/GW/Manufacturing/line2/Device2/BaSys/1.0/provider");
		
		// - Sub models of device2 in line 2 in domain manufacturing.de
		addMapping("description.device2.line2.manufacturing.de", "http://localhost:8080/basys.sdk/Testsuite/GW/Manufacturing/line2/Device2/Submodels/BaSys/1.0/provider");
		addMapping("status.device2.line2.manufacturing.de",      "http://localhost:8080/basys.sdk/Testsuite/GW/Manufacturing/line2/Device2/Submodels/BaSys/1.0/provider");
		addMapping("description.device2.line2",                  "http://localhost:8080/basys.sdk/Testsuite/GW/Manufacturing/line2/Device2/Submodels/BaSys/1.0/provider");
		addMapping("status.device2.line2",                       "http://localhost:8080/basys.sdk/Testsuite/GW/Manufacturing/line2/Device2/Submodels/BaSys/1.0/provider");
		addMapping("description.device2",                        "http://localhost:8080/basys.sdk/Testsuite/GW/Manufacturing/line2/Device2/Submodels/BaSys/1.0/provider");
		addMapping("status.device2",                             "http://localhost:8080/basys.sdk/Testsuite/GW/Manufacturing/line2/Device2/Submodels/BaSys/1.0/provider");


		// Define mappings for line 2 internal in domain manufacturing.de
		// - Asset administration shells
		addMapping("aas.device10.line2int.line2.manufacturing.de",         "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/line2/gateway_line2int/BaSys/1.0/provider");
		// - Sub models of device2 in line 2 in domain manufacturing.de
		addMapping("description.device10.line2int.line2.manufacturing.de", "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/line2/gateway_line2int/BaSys/1.0/provider");
		addMapping("status.device10.line2int.line2.manufacturing.de",      "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/line2/gateway_line2int/BaSys/1.0/provider");
	}
}
