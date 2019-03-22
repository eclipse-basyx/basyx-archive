package org.eclipse.basyx.vab.backend.server.basyx;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

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
	 * Exit flag
	 */
	protected boolean exit = false;
	
	
	
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
		while (!exit) {
			// Handle communication exceptions
			try {
				
				Socket communicationSocket = null;
				
				// Wait for connections
				try {
					communicationSocket = tcpServerSocket.accept();
				} catch (SocketException e) {
					// End process; Server socket has been closed by shutdown
					break;
				}

				// Handle an incoming connection
				// - Create and connect BaSyx client provider for communication socket
				VABBaSyxTCPInterface<T> tcpProvider = new VABBaSyxTCPInterface<T>(providerBackend, communicationSocket);
				// - Start TCP provider
				tcpProvider.start();
			} catch (IOException e) {
				// Indicate exception only iff exit flag is false
				if (!exit) e.printStackTrace();
				
				// Return
				return;
			}
		}
	}
	
	
	/**
	 * End server
	 * 
	 * @throws IOException
	 */
	public void shutdown() {
		// End thread
		exit = true;
		
		// Handle IOException
		try {
			// Close stream
			this.tcpServerSocket.close();
		} catch (IOException e) {
			// Indicate exception
			e.printStackTrace();
		}			
	}
}
