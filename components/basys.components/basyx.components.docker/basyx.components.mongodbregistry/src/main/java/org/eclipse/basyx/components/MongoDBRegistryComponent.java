package org.eclipse.basyx.components;

import org.eclipse.basyx.components.configuration.BaSyxMongoDBConfiguration;
import org.eclipse.basyx.components.servlet.MongoDBRegistryServlet;
import org.eclipse.basyx.vab.protocol.http.server.AASHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A registry component based on a MongoDB database.
 * 
 * @author espen
 */
public class MongoDBRegistryComponent implements IComponent {
	private static Logger logger = LoggerFactory.getLogger(MongoDBRegistryComponent.class);

	// BaSyx context information
	private String hostName;
	private int port;
	private String path;
	private String docBasePath;
	private BaSyxMongoDBConfiguration config;

	// The server with the servlet that will be created
	private AASHTTPServer server;

	public MongoDBRegistryComponent(String hostName, int port, String path, String docBasePath, String dbPropertyPath) {
		// Load configuration
		this.config = new BaSyxMongoDBConfiguration();
		this.config.loadFromResource(dbPropertyPath);
		// Sets the server context
		this.hostName = hostName;
		this.port = port;
		this.path = path;
		this.docBasePath = docBasePath;
	}

	public MongoDBRegistryComponent(String hostName, int port, String path, String docBasePath,
			BaSyxMongoDBConfiguration dbConfig) {
		this.config = dbConfig;
		// Sets the server context
		this.hostName = hostName;
		this.port = port;
		this.path = path;
		this.docBasePath = docBasePath;
	}

	/**
	 * Starts the SQLRegistry at http://${hostName}:${port}/${path}
	 */
	@Override
	public void startComponent() {
		logger.info("Create the server...");
		// Init HTTP context and add an InMemoryRegistryServlet according to the configuration
		BaSyxContext context = new BaSyxContext(path, docBasePath, hostName, port);
		context.addServletMapping("/*", new MongoDBRegistryServlet(config));
		server = new AASHTTPServer(context);

		logger.info("Start the server...");
		server.start();
	}

	@Override
	public void stopComponent() {
		server.shutdown();
	}
}
