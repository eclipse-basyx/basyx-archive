package examples.controllingdevice.submodel.object;

import org.eclipse.basyx.aas.impl.services.PreconfiguredDirectory;




/**
 * Implement a directory for the example setting
 * 
 * @author kuhn
 *
 */
public class ExampleDirectory extends PreconfiguredDirectory {


	/**
	 * Constructor - load all directory entries
	 */
	public ExampleDirectory() {
		// VAB Element mapping
		addMapping("urn:de.FHG:devices.es.iese:statusSM:1.0:3:x-509#002", "localhost:6998");
	}	
}
