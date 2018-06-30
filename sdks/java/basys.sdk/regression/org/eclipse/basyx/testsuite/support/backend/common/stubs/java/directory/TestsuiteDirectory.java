package org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory;

import org.eclipse.basyx.aas.impl.services.PreconfiguredDirectory;




/**
 * Implement the test suite directory service with pre-configured directory entries
 * 
 * @author kuhn
 *
 */
public class TestsuiteDirectory extends PreconfiguredDirectory {

	
	/**
	 * Constructor - load all directory entries
	 */
	public TestsuiteDirectory() {
		// Define mappings
		// - Asset administration shells
		addMapping("Stub1AAS", "http://localhost:8080/basys.sdk/Testsuite/Stub1AAS/BaSys/1.0/provider");
		addMapping("Stub2AAS", "http://localhost:8080/basys.sdk/Testsuite/Stub2AAS/BaSys/1.0/provider");
		addMapping("Stub3AAS", "http://localhost:8080/basys.sdk/Testsuite/Stub3AAS/BaSys/1.0/provider");
		// - Sub models of Stub1AAS		
		addMapping("statusSM.Stub1AAS",        "http://localhost:8080/basys.sdk/Testsuite/Stub1AASSubmodel1/BaSys/1.0/provider");
		addMapping("statusSM",                 "http://localhost:8080/basys.sdk/Testsuite/Stub1AASSubmodel1/BaSys/1.0/provider");
		addMapping("technicalDataSM.Stub1AAS", "http://localhost:8080/basys.sdk/Testsuite/Stub1AASSubmodel1/BaSys/1.0/provider");
		// - Sub model Stub2SM
		addMapping("Stub2SM.Stub1AAS",         "http://localhost:8080/basys.sdk/Testsuite/Stub1AASSubmodel2/BaSys/1.0/provider");
		addMapping("Stub2SM.SubModel2TempAAS", "http://localhost:8080/basys.sdk/Testsuite/Stub1AASSubmodel2/BaSys/1.0/provider");
		// - Sub model Stub3SM (provided by same servlet as Stub3AAS)
		addMapping("Stub3SM.Stub3AAS",         "http://localhost:8080/basys.sdk/Testsuite/Stub3AAS/BaSys/1.0/provider");

		
		// - Sub model MainSM of Stub2AAS
		addMapping("mainSM.Stub2AAS",          "http://localhost:8080/basys.sdk/Testsuite/Stub2AASSubmodel/BaSys/1.0/provider");
		addMapping("mainSM.SubModel2TempAAS",  "http://localhost:8080/basys.sdk/Testsuite/Stub2AASSubmodel/BaSys/1.0/provider");
	}	
}
