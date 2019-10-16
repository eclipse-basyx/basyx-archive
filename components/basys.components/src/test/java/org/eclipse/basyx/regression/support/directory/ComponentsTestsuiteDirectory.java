package org.eclipse.basyx.regression.support.directory;

import org.eclipse.basyx.vab.directory.preconfigured.PreconfiguredDirectory;




/**
 * Implement the test suite directory service with pre-configured directory entries
 * 
 * @author kuhn
 *
 */
public class ComponentsTestsuiteDirectory extends PreconfiguredDirectory {

	
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
		// - Raw CFG provider mappings
		addMapping("AASProvider",              "http://localhost:8080/basys.components/Testsuite/components/BaSys/1.0/provider/rawcfgsm/");
		addMapping("RawCfgFileTestAAS",              "http://localhost:8080/basys.components/Testsuite/components/BaSys/1.0/provider/rawcfgsm/");
		addMapping("sampleRawCFG.RawCfgFileTestAAS", "http://localhost:8080/basys.components/Testsuite/components/BaSys/1.0/provider/rawcfgsm/");
		// - XQuery provider mappings
		addMapping("XMLXQueryFileTestAAS",     "http://localhost:8080/basys.components/Testsuite/components/BaSys/1.0/provider/xmlxquery/");
		// - Processengine mappings
		addMapping("coilcar",                  "http://localhost:8080/basys.components/Testsuite/Processengine/coilcar/");
		addMapping("submodel1",                "http://localhost:8080/basys.components/Testsuite/Processengine/coilcar/");
	}	
}
