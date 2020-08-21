package org.eclipse.basyx.components;

import org.eclipse.basyx.components.servlets.InMemoryRegistryServlet;
import org.eclipse.basyx.vab.protocol.http.server.AASHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Registry component based on an InMemory Registry.
 * 
 * Do not use this registry in a productive environment - the entries are not persistent!
 * 
 * @author espen
 */
public class InMemoryRegistryComponent implements IComponent {
	private static Logger logger = LoggerFactory.getLogger(InMemoryRegistryComponent.class);

	// BaSyx context information
	private String hostName;
	private int port;
	private String path;
	private String docBasePath;

	// The server with the servlet that will be created
	private AASHTTPServer server;

	public InMemoryRegistryComponent(String hostName, int port, String path, String docBasePath) {
		// Sets the server context
		this.hostName = hostName;
		this.port = port;
		this.path = path;
		this.docBasePath = docBasePath;
	}

	/**
	 * Starts the InMemoryRegistry at http://${hostName}:${port}/${path}
	 */
	@Override
	public void startComponent() {
		logger.info("Create the server...");
		// Init HTTP context and add an InMemoryRegistryServlet according to the configuration
		BaSyxContext context = new BaSyxContext(path, docBasePath, hostName, port);
		context.addServletMapping("/*", new InMemoryRegistryServlet());
		server = new AASHTTPServer(context);

		logger.info("Start the server...");
		server.start();
	}

	@Override
	public void stopComponent() {
		server.shutdown();
	}
}
