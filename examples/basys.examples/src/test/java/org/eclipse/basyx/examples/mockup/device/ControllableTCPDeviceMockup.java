package org.eclipse.basyx.examples.mockup.device;

import org.eclipse.basyx.components.device.BaseTCPControllableDeviceAdapter;




/**
 * This class implements a mockup of a controllable manufacturing device. 
 * 
 * The device is controlled by an external control component that issues commands to this device via TCP strings
 * 
 * @author kuhn
 *
 */
public class ControllableTCPDeviceMockup extends BaseTCPControllableDeviceAdapter {

	
	
	/**
	 * Constructor
	 */
	public ControllableTCPDeviceMockup(int port) {
		// Invoke base implementation
		super(port);
	}
	

	
	/**
	 * Indicate device status change to device manager
	 */
	@Override
	protected void statusChange(String newStatus) {
		// Invoke base implementation
		super.statusChange(newStatus);
		
		// Write bytes to device manager
		communicationClient.sendMessage("status:"+newStatus+"\n");
	}
}
