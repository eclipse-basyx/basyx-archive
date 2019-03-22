package examples.controllingdevice.vab.object;

import org.eclipse.basyx.vab.backend.server.http.VABHTTPInterface;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;



/**
 * Servlet interface for VAB elements, e.g. AAS, sub model, or other VAB objects
 * 
 * @author kuhn
 *
 */
public class SimpleVABElementServlet extends VABHTTPInterface<VABHashmapProvider> {

	
	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor
	 */
	public SimpleVABElementServlet() {
		// Invoke base constructor, instantiate a device status VAB object
		super(new VABHashmapProvider(new DeviceStatusVABObject()));
	}
}
