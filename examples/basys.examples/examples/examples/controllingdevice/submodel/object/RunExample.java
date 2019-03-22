package examples.controllingdevice.submodel.object;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.backend.connector.basyx.BaSyxConnectorProvider;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.junit.After;
import org.junit.Before;
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
	 * Device TCP server for this example
	 */
	protected DeviceTCPServer deviceTCPServer = null; 
	
	
	/**
	 * Creates the manager to be used in the test cases
	 */
	@Before
	public void before() {
		// Create and start device TCP server
		deviceTCPServer = new DeviceTCPServer();
		// - Start server
		deviceTCPServer.startTCPServer();
	}

	
	/**
	 * Creates the manager to be used in the test cases
	 */
	@After
	public void after() {
		// Stop TCP server
		deviceTCPServer.stopTCPServer();
	}

	
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
		
		// Compare and output device status
		// - Automated result check
		assertTrue(devState.equals("offline"));
		// - Output result to console
		System.out.println("Status:"+devState);
	}
}
