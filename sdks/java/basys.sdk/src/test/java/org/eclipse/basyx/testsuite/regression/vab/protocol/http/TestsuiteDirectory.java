package org.eclipse.basyx.testsuite.regression.vab.protocol.http;

import org.eclipse.basyx.vab.directory.preconfigured.PreconfiguredDirectory;




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
		addMapping("urn:fhg:es.iese:vab:1:1:simplevabelement",  "http://localhost:8080/basys.sdk/Testsuite/SimpleVAB/");
		addMapping("urn:fhg:es.iese:aas:1:1:submodel", "http://localhost:8080/basys.sdk/Testsuite/SimpleAASSubmodel/");
	}
}
