package org.eclipse.basyx.testsuite.support.backend.servers;

import java.io.IOException;

import org.eclipse.basyx.vab.backend.server.basyx.BaSyxTCPServer;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;



/**
 * BaSyx TCP provider testsuite server providing a SimpleVABElement vab element
 * 
 * @author kuhn
 *
 */
public class AASTCPServer extends Thread {
	
	BaSyxTCPServer<VABHashmapProvider> tcpServer;

	
	/**
	 * Main method
	 */
	public AASTCPServer(VABHashmapProvider provider) {
		
		// Create IModelProvider that provides a VAB element
		VABHashmapProvider modelProvider = provider;

		// Create native BaSyx TCP server using default port
		tcpServer = new BaSyxTCPServer<VABHashmapProvider>(modelProvider);
	}
	
	
	public void run() {
		// Start tcp server
		tcpServer.start();
		
		System.out.println("BaSyx TCP Server started...");

		// Wait until TCP server completes (which will never happen) or do something else meaningful
		try {tcpServer.join();} catch (InterruptedException e) {e.printStackTrace();}
	}
	
	public void shutdown() throws IOException {
		tcpServer.shutdown();
	}
}
