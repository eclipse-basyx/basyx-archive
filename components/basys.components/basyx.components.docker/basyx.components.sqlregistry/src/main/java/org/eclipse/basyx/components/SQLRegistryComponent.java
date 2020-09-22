package org.eclipse.basyx.components;

import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxSQLConfiguration;
import org.eclipse.basyx.components.servlet.SQLRegistryServlet;
import org.eclipse.basyx.vab.protocol.http.server.AASHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A registry component based on an SQL database.
 * 
 * @author espen
 */
public class SQLRegistryComponent implements IComponent {
	private static Logger logger = LoggerFactory.getLogger(SQLRegistryComponent.class);

	// The component configuration
	private BaSyxContextConfiguration contextConfig;
	private BaSyxSQLConfiguration dbConfig;

	// The server with the servlet that will be created
	private AASHTTPServer server;

	/**
	 * Default constructor that loads default configurations
	 */
	public SQLRegistryComponent() {
		this.contextConfig = new BaSyxContextConfiguration();
		this.dbConfig = new BaSyxSQLConfiguration();
	}

	/**
	 * Constructs an empty SQL Registry component using the passed arguments
	 * 
	 * @param contextConfig
	 * @param dbConfig
	 */
	public SQLRegistryComponent(BaSyxContextConfiguration contextConfig, BaSyxSQLConfiguration dbConfig) {
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
		context.addServletMapping("/*", new SQLRegistryServlet(dbConfig));
		server = new AASHTTPServer(context);

		logger.info("Start the server...");
		server.start();
	}

	@Override
	public void stopComponent() {
		server.shutdown();
	}
}
