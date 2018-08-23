package org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory;

import org.eclipse.basyx.aas.impl.services.PreconfiguredDirectory;




/**
 * Implement the test suite directory service with pre-configured directory entries
 * 
 * @author kuhn
 *
 */
public class TestsuiteDirectory_BaSyxNative extends PreconfiguredDirectory {

	
	/**
	 * Constructor - load all directory entries
	 */
	public TestsuiteDirectory_BaSyxNative() {
		// Define mappings
		// - Asset administration shells
		addMapping("Stub1AAS", "localhost:6998");
		addMapping("Stub1AAS/aas", "localhost:6998");
		
		addMapping("Stub2AAS", 	"localhost:6998");	
		addMapping("Stub2AAS/aas", 	"localhost:6998");	
		
		// - Sub models of Stub1AAS		
		addMapping("Stub1AAS/aas/submodels/statusSM",        "localhost:6998");
		addMapping("statusSM",                 				 "localhost:6998");
		
		//addMapping("Stub1AAS/aas/submodels/technicalDataSM", "localhost:6998");
		//addMapping("technicalDataSM", 						 "localhost:6998");

		addMapping("Stub1AAS/aas/submodels/Stub2SM",         "localhost:6998");
		addMapping("Stub2SM",         						 "localhost:6998");
		//addMapping("Stub2SM.SubModel2TempAAS", "localhost:6998");
		
		// - Sub model MainSM of Stub2AAS
		addMapping("Stub2AAS/aas/submodels/mainSM",            "localhost:6998");
	}	
}
