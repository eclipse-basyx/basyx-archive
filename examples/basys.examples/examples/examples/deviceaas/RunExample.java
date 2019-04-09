package examples.deviceaas;

import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.junit.ClassRule;
import org.junit.Test;

import basys.examples.deployment.BaSyxDeployment;
import examples.contexts.BaSyxExamplesContext_1MemoryAASServer_1SQLDirectory;
import examples.deviceaas.devices.devicemanager.DeviceMockup;
import examples.deviceaas.devices.devicemanager.ManufacturingDeviceManager;
import examples.deviceaas.devices.devicemanager.ReceiveDeviceStatusApplication;
import examples.directory.ExamplesDirectory;



/**
 * Run example for device AAS
 * 
 * @author kuhn
 *
 */
public class RunExample {

	
	/**
	 * VAB connection manager backend
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(new ExamplesDirectory(), new HTTPConnectorProvider());

	
	/**
	 * Instantiate and start context elements for this example. BaSyxDeployment contexts instantiate all
	 * components on the IP address of the host. Therefore, all components use the same IP address. 
	 */
	@ClassRule
	public static BaSyxDeployment context = new BaSyxDeployment(
				// Simulated servlets
				// - BaSys topology with one AAS Server and one SQL directory
				new BaSyxExamplesContext_1MemoryAASServer_1SQLDirectory(),
				
				// Simulated runnables
				// - Manufacturing device manager, e.g. deployed to additonal device
				new ManufacturingDeviceManager().setName("DeviceManager"),
				
				// Simulated mockups
				new DeviceMockup(9998).setName("Device"),
				new ReceiveDeviceStatusApplication().setName("Application")
			);
	
	
	
	/**
	 * Test sequence: 
	 * - Device status update
	 * - Read device status from AAS
	 */
	@Test 
	public void test() throws Exception {

		// Device updates status to ready
		((DeviceMockup) context.getRunnable("Device")).statusChange("ready\n");
		
		// Application waits for status change
		while (!((ReceiveDeviceStatusApplication) context.getRunnable("Application")).getDeviceStatus().equals("ready")) Thread.yield();	
	}
}
