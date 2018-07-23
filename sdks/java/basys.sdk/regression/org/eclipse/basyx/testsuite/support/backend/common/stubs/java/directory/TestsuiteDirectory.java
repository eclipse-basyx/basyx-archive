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
		addMapping("Stub1AAS", 							"http://localhost:8080/basys.sdk/");
		addMapping("Stub2AAS", 							"http://localhost:8080/basys.sdk/");
		
		// - Sub models of Stub1AAS		
		addMapping("Stub1AAS/submodels/statusSM",        "http://localhost:8080/basys.sdk/");
		addMapping("Stub1AAS/submodels/technicalDataSM", "http://localhost:8080/basys.sdk/");
		
		// - Sub model of Stub2AAS
		addMapping("Stub1AAS/submodels/Stub2SM",         "http://localhost:8080/basys.sdk/");
		//addMapping("Stub2SM.SubModel2TempAAS", 		 "http://localhost:8080/basys.sdk/");

		
		// - Sub model MainSM of Stub2AAS
		addMapping("Stub2AAS/submodels/mainSM",         "http://localhost:8080/basys.sdk/");
		//addMapping("SubModel2TempAAS/mainSM",  		"http://localhost:8080/basys.sdk/");
		
		// - Rest Interface conformant AAS
		addMapping("RestAAS",         					"http://localhost:8080/basys.sdk/");
		addMapping("RestAAS/submodels/description",     "http://localhost:8080/basys.sdk/");
		addMapping("RestAAS/submodels/status",          "http://localhost:8080/basys.sdk/");


	}	
}
