package examples.controllingdevice.submodel.object;

import org.eclipse.basyx.vab.backend.server.basyx.BaSyxTCPServer;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;



/**
 * BaSyx TCP server that communicates using the native BaSyx protocol
 * 
 * @author kuhn
 *
 */
public class DeviceTCPServer {

	
	/**
	 * Create IModelProvider that provides a VAB element
	 */
	protected VABHashmapProvider modelProvider = new VABHashmapProvider(new DeviceStatusSubmodel());

	
	/**
	 * Create native BaSyx TCP server using default port
	 */
	protected BaSyxTCPServer<VABHashmapProvider> tcpServer = new BaSyxTCPServer<VABHashmapProvider>(modelProvider, 6998);

	
	
	
	/**
	 * Start TCP server
	 */
	public void startTCPServer() {
		// Start TCP server
		tcpServer.start();
	}
	
	
	/**
	 * Stop TCP server
	 */
	public void stopTCPServer() {
		// Shutdown TCP server
		tcpServer.shutdown();		
	}	
	

	
	/**
	 * Main method
	 */
	public static void main(String[] args) {
		// Create device TCP server
		DeviceTCPServer deviceServer = new DeviceTCPServer();
		
		// Start device TCP server
		deviceServer.startTCPServer();

		// Wait until TCP server completes (which will never happen) or do something else meaningful
		try {deviceServer.tcpServer.join();} catch (InterruptedException e) {e.printStackTrace();}		
	}
}
