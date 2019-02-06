package examples.controllingdevice.vab.object;

import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
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
	protected VABConnectionManager connManager = new VABConnectionManager(new ExampleDirectory(), new HTTPConnectorProvider());

	
	
	/**
	 * Test basic queries
	 */
	@Test
	public void test() throws Exception {

		// Server connections
		// - Connect to device (VAB object)
		VABElementProxy connSubModel1 = this.connManager.connectToVABElement("urn:de.FHG:devices.es.iese:statusSM:1.0:3:x-509#003");

		// Read status from device
		Object devState1 = connSubModel1.readElementValue("properties/deviceStatus");
		// - Output device status
		System.out.println("Status1:"+devState1);

		// Read mode from device
		Object devMode1a = connSubModel1.readElementValue("properties/mode");
		// - Output device mode
		System.out.println("Mode1a:"+devMode1a);
		// - Update device mode
		connSubModel1.updateElementValue("properties/mode", "start");
		// Read mode from device again
		Object devMode1b = connSubModel1.readElementValue("properties/mode");
		// - Output device mode again
		System.out.println("Mode1b:"+devMode1b);

		

		// Server connections
		// - Connect to device (sub model)
		VABElementProxy connSubModel2 = this.connManager.connectToVABElement("urn:de.FHG:devices.es.iese:statusSM:1.0:3:x-509#004");

		// Read status from device
		Object devState2 = connSubModel2.readElementValue("properties/deviceStatus/value");
		// - Output device status
		System.out.println("Status2:"+devState2);
		
		// Read mode from device
		Object devMode2a = connSubModel2.readElementValue("properties/mode/value");
		// - Output device mode
		System.out.println("Mode2a:"+devMode2a);
		// - Update device mode
		connSubModel2.updateElementValue("properties/mode/value", "start");
		// Read mode from device again
		Object devMode2b = connSubModel2.readElementValue("properties/mode/value");
		// - Output device mode again
		System.out.println("Mode2b:"+devMode2b);
	}
}

