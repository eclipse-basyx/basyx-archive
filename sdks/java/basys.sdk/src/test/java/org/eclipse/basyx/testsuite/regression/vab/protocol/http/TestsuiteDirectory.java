package org.eclipse.basyx.testsuite.regression.vab.protocol.http;

import org.eclipse.basyx.vab.directory.memory.InMemoryDirectory;




/**
 * Implement the test suite directory service with pre-configured directory entries
 * 
 * @author kuhn
 *
 */
public class TestsuiteDirectory extends InMemoryDirectory {
	
	/**
	 * Constructor - load all directory entries
	 */
	public TestsuiteDirectory() {
		this("http");
	}
	
	/**
	 * Constructor - load all directory entries with link 
	 * protocol defined in the parameter
	 * @param protocol
	 */
	public TestsuiteDirectory(String protocol) {
		defineMapping(protocol);
	}
	
	/**
	 * Define mapping of submodel and vab element
	 */
	private void defineMapping(String protocol) {
		addMapping("urn:fhg:es.iese:vab:1:1:simplevabelement",  protocol + "://localhost:8080/basys.sdk/Testsuite/SimpleVAB/");
		addMapping("urn:fhg:es.iese:aas:1:1:submodel", protocol + "://localhost:8080/basys.sdk/Testsuite/SimpleAASSubmodel/");
	}
}
