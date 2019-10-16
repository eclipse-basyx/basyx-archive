package org.eclipse.basyx.vab.protocol.basyx.server;

import java.io.IOException;

import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.service.api.BaSyxService;

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
	protected BaSyxTCPServer<IModelProvider> tcpServer;

	/**
	 * Service name
	 */
	protected String name = null;

	/**
	 * Main method
	 */
	public AASTCPServer(IModelProvider provider) {

		// Create IModelProvider that provides a VAB element
		IModelProvider modelProvider = provider;

		// Create native BaSyx TCP server using default port
		tcpServer = new BaSyxTCPServer<IModelProvider>(modelProvider);
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
	 * Stop the TCP server, blocks until the server has stopped
	 * 
	 * @throws IOException
	 */
	@Override
	public void stop() {
		// Stops the server
		tcpServer.stop();
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
	 * Wait for end of thread
	 */
	public void waitFor() {
		tcpServer.waitFor();
	}

	/**
	 * Indicate if the server has ended
	 */
	public boolean hasEnded() {
		return tcpServer.hasEnded();
	}
}
