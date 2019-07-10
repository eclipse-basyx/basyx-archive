package org.eclipse.basyx.testsuite.support.backend.servers;

import java.io.IOException;

import org.eclipse.basyx.sdk.api.service.BaSyxService;
import org.eclipse.basyx.vab.backend.server.basyx.BaSyxTCPServer;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;



/**
 * BaSyx TCP provider test suite server providing a SimpleVABElement vab element
 * 
 * @author kuhn
 *
 */
public class AASTCPServer implements BaSyxService {
	
	
	/**
	 * TCP server reference
	 */
	protected BaSyxTCPServer<VABHashmapProvider> tcpServer;

	
	/**
	 * Service name
	 */
	protected String name = null;
	
	
	/**
	 * Service end flag 
	 */
	protected boolean hasExecutionEnded = false;
	
	
	
	
	/**
	 * Main method
	 */
	public AASTCPServer(VABHashmapProvider provider) {
		
		// Create IModelProvider that provides a VAB element
		VABHashmapProvider modelProvider = provider;

		// Create native BaSyx TCP server using default port
		tcpServer = new BaSyxTCPServer<VABHashmapProvider>(modelProvider);
	}
	
	
	/**
	 * Start the TCP server
	 */
	@Override
	public void start() {
		// Start tcp server
		tcpServer.start();
	}
	
	
	/**
	 * Shutdown TCP server
	 * 
	 * @throws IOException
	 */
	@Override
	public void stop() {
		// Indicate end of execution
		hasExecutionEnded = true;
		
		// Shutdown server
		tcpServer.shutdown();
	}


	/**
	 * Change service name
	 */
	@Override
	public BaSyxService setName(String newName) {
		// Update name
		name = newName;
		
		// Return 'this' reference
		return this;
	}


	/**
	 * Return service name
	 */
	@Override
	public String getName() {
		// Return service name
		return name;
	}
	
	
	/**
	 * Wait for end of runnable
	 */
	public void waitFor() {
		// Wait for thread end
		tcpServer.waitFor();		
	}
	
	
	/**
	 * Indicate if this service has ended
	 */
	public boolean hasEnded() {
		// Return execution ended flag
		return hasExecutionEnded;
	}
}

