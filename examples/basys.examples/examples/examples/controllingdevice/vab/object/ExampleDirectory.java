package examples.controllingdevice.vab.object;

import org.eclipse.basyx.aas.impl.services.PreconfiguredDirectory;




/**
 * Implement a pre-configured directory service. This is most helpful for static topologies or test setups.
 * 
 * @author kuhn
 *
 */
public class ExampleDirectory extends PreconfiguredDirectory {

	
	/**
	 * Constructor - load all directory entries
	 */
	public ExampleDirectory() {
		// Define mappings
		// - Device sub model (VAB)
		addMapping("urn:de.FHG:devices.es.iese:statusSM:1.0:3:x-509#003",  "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/devicestatusVAB/");
		// - Device sub model (SM)
		addMapping("urn:de.FHG:devices.es.iese:statusSM:1.0:3:x-509#004",  "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/devicestatusSM/");
	}	
}

