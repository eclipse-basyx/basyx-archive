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
		
		addMapping("line2.manufacturing.de/device2/aas", "http://localhost:8080/basys.sdk/Testsuite/GW/Manufacturing/line2/"); //Device2/BaSys/1.0/provider");
		addMapping("line2/device2/aas",                  "http://localhost:8080/basys.sdk/Testsuite/GW/Manufacturing/line2/"); //Device2/BaSys/1.0/provider");
		addMapping("device2/aas",                        "http://localhost:8080/basys.sdk/Testsuite/GW/Manufacturing/line2/"); //Device2/BaSys/1.0/provider");
		addMapping("device2",                            "http://localhost:8080/basys.sdk/Testsuite/GW/Manufacturing/line2/"); //Device2/BaSys/1.0/provider");
		
		
		
		// - Sub models of device2 in line 2 in domain manufacturing.de
		addMapping("line2.manufacturing.de/device2/aas/submodels/description",  "http://localhost:8080/basys.sdk/Testsuite/GW/Manufacturing/line2/"); //Device2/BaSys/1.0/provider"); ///Submodels/BaSys/1.0/provider");
		addMapping("line2.manufacturing.de/device2/aas/submodels/status",       "http://localhost:8080/basys.sdk/Testsuite/GW/Manufacturing/line2/"); //Device2/BaSys/1.0/provider"); // Submodels/BaSys/1.0/provider");
		addMapping("line2/device2/aas/submodels/description",                   "http://localhost:8080/basys.sdk/Testsuite/GW/Manufacturing/line2/"); //Device2/BaSys/1.0/provider"); 
		addMapping("line2/device2/aas/submodels/status",                        "http://localhost:8080/basys.sdk/Testsuite/GW/Manufacturing/line2/"); //Device2/BaSys/1.0/provider");
		addMapping("device2/aas/submodels/description",                         "http://localhost:8080/basys.sdk/Testsuite/GW/Manufacturing/line2/"); //Device2/BaSys/1.0/provider");
		addMapping("device2/aas/submodels/status",                              "http://localhost:8080/basys.sdk/Testsuite/GW/Manufacturing/line2/"); //Device2/BaSys/1.0/provider");


		// Define mappings for line 2 internal in domain manufacturing.de - do we need this here?
		// - Asset administration shells
		addMapping("device10",         					 "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/line2/"); //gateway_line2int/BaSys/1.0/provider");
		addMapping("device10/aas",         					 "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/line2/"); //gateway_line2int/BaSys/1.0/provider");
		addMapping("device10/aas/submodels/description", "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/line2/"); //gateway_line2int/BaSys/1.0/provider");
		addMapping("device10/aas/submodels/status",      "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/line2/"); //gateway_line2int/BaSys/1.0/provider");
		addMapping("line2int/device10",         					 "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/line2/"); //gateway_line2int/BaSys/1.0/provider");
		addMapping("line2int/device10/aas",         					 "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/line2/"); //gateway_line2int/BaSys/1.0/provider");
		addMapping("line2int/device10/aas/submodels/description", "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/line2/"); //gateway_line2int/BaSys/1.0/provider");
		addMapping("line2int/device10/aas/submodels/status",      "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/line2/"); //gateway_line2int/BaSys/1.0/provider");
		addMapping("line2int.line2.manufacturing.de/device10",         					 "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/line2/"); //gateway_line2int/BaSys/1.0/provider");
		addMapping("line2int.line2.manufacturing.de/device10/aas",         					 "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/line2/"); //gateway_line2int/BaSys/1.0/provider");
		// - Sub models of device2 in line 2 in domain manufacturing.de
		addMapping("line2int.line2.manufacturing.de/device10/aas/submodels/description", "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/line2/"); //gateway_line2int/BaSys/1.0/provider");
		addMapping("line2int.line2.manufacturing.de/device10/aas/submodels/status",      "http://localhost:8080/basys.sdk/Testsuite/GW/IESE/line2/"); //gateway_line2int/BaSys/1.0/provider");
	}
}
