package org.eclipse.basyx.examples.support.directory;

import org.eclipse.basyx.vab.directory.memory.InMemoryDirectory;

/**
 * Implement the examples directory service with pre-configured directory entries
 * 
 * @author kuhn
 *
 */
public class ExamplesPreconfiguredDirectory extends InMemoryDirectory {

	
	/**
	 * Constructor - load all directory entries
	 */
	public ExamplesPreconfiguredDirectory() {
		// Populate with entries from base implementation
		super();

		// Define mappings
		// - AAS server mapping
		addMapping("AASServer", "http://localhost:8080/basys.examples/Components/AasServer/");
	}	
}
