package org.eclipse.basyx.components;

import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
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

	// The component configuration
	private BaSyxContextConfiguration contextConfig;
	private BaSyxMongoDBConfiguration dbConfig;

	// The server with the servlet that will be created
	private AASHTTPServer server;

	/**
	 * Default constructor that loads default configurations
	 */
	public MongoDBRegistryComponent() {
		this.contextConfig = new BaSyxContextConfiguration();
		this.dbConfig = new BaSyxMongoDBConfiguration();
	}

	/**
	 * Constructs an empty MongoDB Registry component using the passed arguments
	 * 
	 * @param contextConfig
	 * @param dbConfig
	 */
	public MongoDBRegistryComponent(BaSyxContextConfiguration contextConfig, BaSyxMongoDBConfiguration dbConfig) {
		this.contextConfig = contextConfig;
		this.dbConfig = dbConfig;
	}

	/**
	 * Starts the SQLRegistry at http://${hostName}:${port}/${path}
	 */
	@Override
	public void startComponent() {
		logger.info("Create the server...");
		// Init HTTP context and add an InMemoryRegistryServlet according to the configuration
		BaSyxContext context = contextConfig.createBaSyxContext();
		context.addServletMapping("/*", new MongoDBRegistryServlet(dbConfig));
		server = new AASHTTPServer(context);

		logger.info("Start the server...");
		server.start();
	}

	@Override
	public void stopComponent() {
		server.shutdown();
	}
}
