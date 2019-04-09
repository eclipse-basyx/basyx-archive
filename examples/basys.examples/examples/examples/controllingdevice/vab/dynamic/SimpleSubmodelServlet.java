package examples.controllingdevice.vab.dynamic;

import org.eclipse.basyx.vab.backend.server.http.VABHTTPInterface;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;
import org.eclipse.basyx.vab.provider.lambda.VABLambdaProvider;

import examples.controllingdevice.submodel.object.DeviceStatusSubmodel;



/**
 * Servlet interface for VAB elements, e.g. AAS, sub model, or other VAB objects
 * 
 * @author kuhn
 *
 */
public class SimpleSubmodelServlet extends VABHTTPInterface<VABHashmapProvider> {

	
	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor
	 */
	public SimpleSubmodelServlet() {
		// Invoke base constructor, instantiate device status sub model
		super(new VABLambdaProvider(new DeviceStatusSubmodel()));
	}
}
