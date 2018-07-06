package org.eclipse.basyx.regression.support.directory;

import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory;




/**
 * Implement the test suite directory service with pre-configured directory entries
 * 
 * @author kuhn
 *
 */
public class ComponentTestsuiteDirectory extends TestsuiteDirectory {

	
	/**
	 * Constructor - load all directory entries
	 */
	public ComponentTestsuiteDirectory() {
		// Populate with entries from base implementation
		super();

		// Define mappings
		// - Asset administration shells
		addMapping("SQLTestAAS",               "http://localhost:8080/basys.components/Testsuite/components/BaSys/1.0/provider/sqlsm");
		addMapping("sampleDB.SQLTestAAS",      "http://localhost:8080/basys.components/Testsuite/components/BaSys/1.0/provider/sqlsm");
	}	
}
