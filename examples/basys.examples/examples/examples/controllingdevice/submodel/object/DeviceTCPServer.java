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
	 * Main method
	 */
	public static void main(String[] args) {

		// Create IModelProvider that provides a VAB element
		VABHashmapProvider modelProvider = new VABHashmapProvider(new DeviceStatusSubmodel());

		// Create native BaSyx TCP server using default port
		BaSyxTCPServer<VABHashmapProvider> tcpServer = new BaSyxTCPServer<VABHashmapProvider>(modelProvider, 6998);

		// Start TCP server
		tcpServer.start();

		// Wait until TCP server completes (which will never happen) or do something else meaningful
		try {tcpServer.join();} catch (InterruptedException e) {e.printStackTrace();}
	}
}
