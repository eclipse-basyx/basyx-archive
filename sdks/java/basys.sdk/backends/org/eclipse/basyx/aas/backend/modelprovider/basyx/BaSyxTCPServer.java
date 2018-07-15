package org.eclipse.basyx.aas.backend.modelprovider.basyx;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.eclipse.basyx.aas.api.services.IModelProvider;



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
			tcpServerSocket = new ServerSocket(6998);
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
				BaSyxTCPProvider<T> tcpProvider = new BaSyxTCPProvider<T>(providerBackend, communicationSocket);
				// - Start TCP provider
				tcpProvider.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

