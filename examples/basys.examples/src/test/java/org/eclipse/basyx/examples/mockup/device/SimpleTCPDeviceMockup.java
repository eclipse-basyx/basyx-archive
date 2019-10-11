package org.eclipse.basyx.examples.mockup.device;

import org.eclipse.basyx.components.device.BaseTCPDeviceAdapter;




/**
 * This class implements a mockup of a simple manufacturing device. 
 * 
 * All device interface functions that are called from native code in real deployments are to be 
 * called from the test script.
 * 
 * @author kuhn
 *
 */
public class SimpleTCPDeviceMockup extends BaseTCPDeviceAdapter {

	
	/**
	 * Constructor
	 */
	public SimpleTCPDeviceMockup(int port) {
		// Invoke base implementation
		super(port);
	}
}
