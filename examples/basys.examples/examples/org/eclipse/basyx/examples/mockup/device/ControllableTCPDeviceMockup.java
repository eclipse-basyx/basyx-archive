package org.eclipse.basyx.examples.mockup.device;

import org.eclipse.basyx.components.controlcomponent.ExecutionState;
import org.eclipse.basyx.components.device.BaseTCPDevice;
import org.eclipse.basyx.components.netcomm.NetworkReceiver;
import org.eclipse.basyx.components.netcomm.TCPClient;




/**
 * This class implements a mockup of a controllable manufacturing device. 
 * 
 * The device is controlled by an external control component that issues commands to this device via TCP strings
 * 
 * @author kuhn
 *
 */
public class ControllableTCPDeviceMockup extends BaseTCPDevice implements NetworkReceiver {

	
	/**
	 * Receive thread
	 */
	protected Thread rxThread = null;
	
	
	
	
	/**
	 * Constructor
	 */
	public ControllableTCPDeviceMockup(int port) {
		// Invoke base implementation
		super(port);
	}
	
	
	
	/**
	 * Start the device
	 */
	@Override
	public void start() {
		// Invoke base implementation
		super.start();
		
		// Add this component as message listener
		communicationClient.addTCPMessageListener(this);
		// - Start receive thread
		rxThread = new Thread(communicationClient);
		// - Start receiving
		rxThread.start();
	}


	/**
	 * Stop the device
	 */
	@Override
	public void stop() {
		// Invoke base implementation
		super.stop();
		
		// End communication
		communicationClient.close();
	}

	
	/**
	 * Wait for end of all threads
	 */
	@Override
	public void waitFor() {
		// Invoke base implementation
		super.waitFor();
		
		// Wait for end of TCP thread
		try {rxThread.join();} catch (InterruptedException e) {e.printStackTrace();}
	}

	
	
	
	/**
	 * Changed operation mode
	 */
	protected void onOperationModeChange(String newOperationMode) {
		// Do nothing
	}
	
	
	/**
	 * State change
	 */
	protected void onStateChange(ExecutionState newExState) {
		// Do nothing
	}

	
	/**
	 * A message has been received
	 */
	@Override
	public void onReceive(byte[] message) {
		// Convert message to String
		String rxMessage = TCPClient.toString(message);
		
		// Process message
		if (rxMessage.startsWith("state:"))  {onStateChange(ExecutionState.byValue(rxMessage.substring("state:".length()))); return;}
		if (rxMessage.startsWith("opMode:")) {onOperationModeChange(rxMessage.substring("opMode:".length())); return;}
		
		// Indicate exception
		throw new RuntimeException("Unexpected message received:"+rxMessage);
	}
}
