package org.eclipse.basyx.testsuite.regression.vab.protocol.basyx;

import org.eclipse.basyx.vab.directory.memory.InMemoryDirectory;




/**
 * Implement the test suite directory service with pre-configured directory entries
 * 
 * @author kuhn
 *
 */
public class TestsuiteDirectory_BaSyxNative extends InMemoryDirectory {

	
	/**
	 * Constructor - load all directory entries
	 */
	public TestsuiteDirectory_BaSyxNative() {
		// VAB Element mapping
		addMapping("urn:fhg:es.iese:vab:1:1:simplevabelement", "basyx://localhost:6998");
	}	
}
