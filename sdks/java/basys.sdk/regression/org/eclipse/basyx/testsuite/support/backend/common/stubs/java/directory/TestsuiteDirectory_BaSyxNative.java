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
		// - Sub models of Stub1AAS		
		addMapping("statusSM.Stub1AAS",        "localhost:6998");
		addMapping("statusSM",                 "localhost:6998");
		addMapping("technicalDataSM.Stub1AAS", "localhost:6998");

		// - Sub model Stub2SM
		addMapping("Stub2SM.Stub1AAS",         "localhost:6998");
		addMapping("Stub2SM.SubModel2TempAAS", "localhost:6998");
	}	
}
