package org.eclipse.basyx.regression.support.directory;

import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory;




/**
 * Implement the test suite directory service with pre-configured directory entries
 * 
 * @author kuhn
 *
 */
public class ComponentsTestsuiteDirectory extends TestsuiteDirectory {

	
	/**
	 * Constructor - load all directory entries
	 */
	public ComponentsTestsuiteDirectory() {
		// Populate with entries from base implementation
		super();

		// Define mappings
		// - SQL provider mappings
		addMapping("SQLTestSubmodel",          "http://localhost:8080/basys.components/Testsuite/components/BaSys/1.0/provider/sqlsm/");
		addMapping("sampleDB.SQLTestAAS",      "http://localhost:8080/basys.components/Testsuite/components/BaSys/1.0/provider/sqlsm/");
		// - CFG provider mappings
		addMapping("CfgFileTestAAS",           "http://localhost:8080/basys.components/Testsuite/components/BaSys/1.0/provider/cfgsm/");
		addMapping("sampleCFG.CfgFileTestAAS", "http://localhost:8080/basys.components/Testsuite/components/BaSys/1.0/provider/cfgsm/");
		// - XQuery provider mappings
		addMapping("XMLXQueryFileTestAAS",     "http://localhost:8080/basys.components/Testsuite/components/BaSys/1.0/provider/xmlxquery/");
	}	
}
