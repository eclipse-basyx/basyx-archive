package examples.controllingdevice.submodel.object;

import org.eclipse.basyx.aas.backend.connector.basyx.BaSyxConnectorProvider;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.junit.Test;



/**
 * Run example for controlling device (SubModel/BaSyx TCP)
 * 
 * @author kuhn
 *
 */
public class RunExample {

	
	/**
	 * VAB connection manager backend
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(new ExampleDirectory(), new BaSyxConnectorProvider());

	
	
	/**
	 * Test basic queries
	 */
	@Test
	public void test() throws Exception {

		// Server connections
		// - Connect to AAS server
		VABElementProxy connSubModel = this.connManager.connectToVABElement("urn:de.FHG:devices.es.iese:statusSM:1.0:3:x-509#002");
		
		// Device updates status to ready
		Object devState = connSubModel.readElementValue("properties/deviceStatus/value");
		
		// Output device status
		System.out.println("Status:"+devState);
	}
}
