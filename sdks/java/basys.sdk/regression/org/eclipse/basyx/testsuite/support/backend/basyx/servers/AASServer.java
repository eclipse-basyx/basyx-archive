package org.eclipse.basyx.testsuite.support.backend.basyx.servers;

import org.eclipse.basyx.testsuite.support.vab.stub.elements.SimpleVABElement;
import org.eclipse.basyx.vab.backend.server.basyx.BaSyxTCPServer;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;



/**
 * BaSyx provider testsuite server providing a SimpleVABElement vab element
 * 
 * @author kuhn
 *
 */
public class AASServer {

	
	/**
	 * Main method
	 */
	public static void main(String[] args) {
		
		// Create IModelProvider that provides a VAB element
		VABHashmapProvider modelProvider = new VABHashmapProvider(new SimpleVABElement());

		// Create native BaSyx TCP server using default port
		BaSyxTCPServer<VABHashmapProvider> tcpServer = new BaSyxTCPServer<VABHashmapProvider>(modelProvider);
		
		// Start tcp server
		tcpServer.start();

		// Wait until TCP server completes (which will never happen) or do something else meaningful
		try {tcpServer.join();} catch (InterruptedException e) {e.printStackTrace();}
	}
}
