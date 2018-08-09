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
		addMapping("Stub1AAS", 							"http://localhost:8080/basys.sdk/Testsuite/");
		addMapping("Stub1AAS/aas", 							"http://localhost:8080/basys.sdk/Testsuite/");
		
		addMapping("RestAAS",         					"http://localhost:8080/basys.sdk/Testsuite/");
		addMapping("RestAAS/aas",         					"http://localhost:8080/basys.sdk/Testsuite/");
		
		addMapping("Stub2AAS", 					"http://localhost:8080/basys.sdk/Testsuite/Stub2AAS/");
		addMapping("Stub2AAS/aas", 					"http://localhost:8080/basys.sdk/Testsuite/Stub2AAS/");
		
		addMapping("Stub3AAS", 					"http://localhost:8080/basys.sdk/Testsuite/Stub3AAS/");
		addMapping("Stub3AAS/aas", 					"http://localhost:8080/basys.sdk/Testsuite/Stub3AAS/");
		
		addMapping("statusSM",                  "http://localhost:8080/basys.sdk/Testsuite/Stub1AASSubmodel1/");
		addMapping("statusSM/submodel",                  "http://localhost:8080/basys.sdk/Testsuite/Stub1AASSubmodel1/");
		
		
		
		// - Sub models of Stub1AAS		
		addMapping("Stub1AAS/aas/submodels/statusSM",   "http://localhost:8080/basys.sdk/Testsuite/");
		addMapping("Stub1AAS/aas/submodels/Stub2SM",    "http://localhost:8080/basys.sdk/Testsuite/");
		addMapping("Stub1AAS/aas/submodels/technicalDataSM",  "http://localhost:8080/basys.sdk/Testsuite/Stub1AASSubmodel1/");
		
		
		// - Sub models of RestAAS
		addMapping("RestAAS/aas/submodels/frozenSM",     "http://localhost:8080/basys.sdk/Testsuite/");
		
		// - Sub models oSubModel2TempAAS
		addMapping("SubModel2TempAAS/aas/submodels/Stub2SM",  "http://localhost:8080/basys.sdk/Testsuite/Stub1AASSubmodel2/");
		addMapping("SubModel2TempAAS/aas/submodels/mainSM",   "http://localhost:8080/basys.sdk/Testsuite/Stub2AASSubmodel/");
		
		// - Sub model of Stub3AAS - Stub3SM (provided by same servlet as Stub3AAS)
		addMapping("Stub3AAS/aas/submodels/Stub3SM",          "http://localhost:8080/basys.sdk/Testsuite/Stub3AAS/");

		// - Sub model MainSM of Stub2AAS
		addMapping("Stub2AAS/aas/submodels/mainSM",           "http://localhost:8080/basys.sdk/Testsuite/");
		}	
}
