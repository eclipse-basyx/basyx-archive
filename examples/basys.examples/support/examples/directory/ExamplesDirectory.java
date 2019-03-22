package examples.directory;

import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory;




/**
 * Implement the test suite directory service with pre-configured directory entries
 * 
 * @author kuhn
 *
 */
public class ExamplesDirectory extends TestsuiteDirectory {

	
	/**
	 * Constructor - load all directory entries
	 */
	public ExamplesDirectory() {
		// Populate with entries from base implementation
		super();

		// Define mappings
		// - AAS server mapping
		//addMapping("AASServer", "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/aasserver/");
		addMapping("AASServer", "http://localhost:8080/basys.examples/Components/BaSys/1.0/aasServer/");
	}	
}
