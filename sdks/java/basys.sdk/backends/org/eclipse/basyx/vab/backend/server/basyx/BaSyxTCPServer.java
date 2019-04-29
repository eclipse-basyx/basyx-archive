package org.eclipse.basyx.vab.backend.server.basyx;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import org.eclipse.basyx.sdk.api.service.BaSyxService;
import org.eclipse.basyx.vab.core.IModelProvider;



/**
 * BaSyx TCP server thread
 * 
 * @author kuhn
 *
 */
public class BaSyxTCPServer<T extends IModelProvider> implements Runnable, BaSyxService {

	
	/**
	 * Store server socket channel instance
	 */
	protected ServerSocketChannel serverSockChannel = null;

	
	/**
	 * Reference to IModelProvider backend
	 */
	protected T providerBackend = null;

	
	/**
	 * Exit flag
	 */
	protected boolean exit = false;
	
	
	/**
	 * Store thread
	 */
	protected Thread thread = null;
	
	
	/**
	 * Store name
	 */
	protected String name = null;
	
	
	
	/**
	 * Constructor
	 */
	public BaSyxTCPServer(T modelProviderBackend, int serverPort) {
		// Store model provider backend reference
		providerBackend = modelProviderBackend;

		// Create communication channel
		try {
			// Resolve address
			InetAddress hostIPAddress = InetAddress.getByName("localhost");

			// Server socket channel
			serverSockChannel = ServerSocketChannel.open();
			serverSockChannel.configureBlocking(true);
			serverSockChannel.socket().bind(new InetSocketAddress(hostIPAddress, serverPort));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**
	 * Default constructor without port number
	 */
	public BaSyxTCPServer(T modelProviderBackend) {
		// Invoke 'this' constructor
		this(modelProviderBackend, 6998);
	}

	
	/**
	 * Thread main method
	 */
	@Override
	public void run() {
		// Accept connections
		while (!exit) {
			// Accept incoming connections
			acceptIncomingConnection();
		}
	}
	
	
	/**
	 * Accept an incoming connection
	 */
	public void acceptIncomingConnection() {
		// Implement exception handling
		try {
			// Store socket channel
			SocketChannel communicationSocket = null;

			// Wait for connections
			try {
				communicationSocket = serverSockChannel.accept(); 
			} catch (SocketException e) {
				e.printStackTrace();
				// End process; Server socket has been closed by shutdown
				exit = true; return;
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
			serverSockChannel.close();
		} catch (IOException e) {
			// Indicate exception
			e.printStackTrace();
		}			
	}


	/**
	 * Start the server
	 */
	@Override
	public void start() {
		// Create thread
		thread = new Thread(this);
		
		// Start thread
		thread.start();
	}


	/**
	 * Stop the server
	 */
	@Override
	public void stop() {
		// Shutdown thread
		shutdown();
		
		// Wait for thread end
		try {thread.join();} catch (InterruptedException e) {e.printStackTrace();}
	}


	/**
	 * Change service name
	 */
	@Override
	public BaSyxService setName(String newName) {
		name = newName;
		return this;
	}


	/**
	 * Return service name
	 */
	@Override
	public String getName() {
		return name;
	}
	
	
	/**
	 * Wait for end of runnable
	 */
	public void waitFor() {
		// Wait for thread end
		try {thread.join();} catch (InterruptedException e) {e.printStackTrace();}		
	}
}

