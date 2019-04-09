package examples.deviceaas.devices.devicemanager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import basys.examples.deployment.BaSyxContextRunnable;



/**
 * This class implements a mockup of a manufacturing device
 * 
 * @author kuhn
 *
 */
public class DeviceMockup implements BaSyxContextRunnable {

	
	/**
	 * Communication socket
	 */
	protected Socket communicationSocket = null;
	
	
	/**
	 * Communication stream to connected device manager
	 */
	protected DataOutputStream toDeviceManager = null;
	
	
	/**
	 * Store server port
	 */
	protected int serverPort;
	
	
	/**
	 * Store device name
	 */
	protected String deviceName = null;
	
	
	
	/**
	 * Constructor
	 */
	public DeviceMockup(int port) {
		// Store server port
		serverPort = port;
	}
	
	
	/**
	 * Indicate device status change
	 */
	public void statusChange(String newStatus) {
		// Signal status change
		try {
			// Write bytes to device manager
			toDeviceManager.writeBytes(newStatus);
			
			// Flush communication
			toDeviceManager.flush();
		} catch (IOException e) {
			// Handle exceptions for now by printing them to the console
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Close device communication socket
	 */
	public void closeSocket() {
		// Try to close socket
		try {
			// Close socket
			communicationSocket.close();
		} catch (IOException e) {
			// Handle exceptions for now by printing them to the console
			e.printStackTrace();
		}
	}


	/**
	 * Start the device
	 */
	@Override
	public void start() {
		// Try to create connection
		try {
			// Create connection
			communicationSocket = new Socket("localhost", serverPort);
			
			// Create output stream
			// - The output stream is simple but will do for our mockup
			toDeviceManager = new DataOutputStream(communicationSocket.getOutputStream());
		} catch (IOException e) {
			// Handle exceptions for now by printing them to the console
			e.printStackTrace();
		}
	}


	/**
	 * Stop the device
	 */
	@Override
	public void stop() {
		// Close communication socket
		closeSocket();
	}
	
	
	/**
	 * Change the runnable name
	 */
	@Override
	public BaSyxContextRunnable setName(String newName) {
		// Set name
		deviceName = newName;
		
		// Return 'this' reference to enable chaining
		return this;
	}
	
	
	/**
	 * Get runnable name
	 */
	@Override
	public String getName() {
		return deviceName;
	}
}
