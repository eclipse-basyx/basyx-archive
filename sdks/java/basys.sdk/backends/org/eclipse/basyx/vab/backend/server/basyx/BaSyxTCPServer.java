package org.eclipse.basyx.vab.backend.server.basyx;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.eclipse.basyx.vab.core.IModelProvider;

/**
 * BaSyx TCP server thread
 * 
 * @author kuhn
 *
 */
public class BaSyxTCPServer<T extends IModelProvider> extends Thread {

	/**
	 * Store server socket instance
	 */
	protected ServerSocket tcpServerSocket = null;

	/**
	 * Reference to IModelProvider backend
	 */
	protected T providerBackend = null;

	/**
	 * Constructor
	 */
	public BaSyxTCPServer(T modelProviderBackend, int serverPort) {
		// Store model provider backend reference
		providerBackend = modelProviderBackend;

		// Create server socket
		try {
			tcpServerSocket = new ServerSocket(serverPort);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Default constructor without port number
	 */
	public BaSyxTCPServer(T modelProviderBackend) {
		this(modelProviderBackend, 6998);
	}

	/**
	 * Thread main method
	 */
	@Override
	public void run() {
		// Accept connections
		while (true) {
			// Handle communication exceptions
			try {
				// Wait for connections
				Socket communicationSocket = tcpServerSocket.accept();

				// Handle an incoming connection
				// - Create and connect BaSyx client provider for communication socket
				VABBaSyxTCPInterface<T> tcpProvider = new VABBaSyxTCPInterface<T>(providerBackend, communicationSocket);
				// - Start TCP provider
				tcpProvider.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
