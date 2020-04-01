package org.eclipse.basyx.components.executable;

import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.servlet.SQLRegistryServlet;
import org.eclipse.basyx.vab.protocol.http.server.AASHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A registry servlet based on an SQL database. The servlet therefore provides an implementation
 * for the IAASRegistryService interface with a permanent storage solution. The properties for the 
 * SQL connection will be read from executables.properties in the resource folder.
 * 
 * @author espen
 */
public class SQLRegistryExecutable {
	private static Logger logger = LoggerFactory.getLogger(SQLRegistryExecutable.class);



	// The path the created servlet is mapped to
	public static final String SERVLET_MAPPING = "/*";
	
	// The server with the servlet that will be created
	private static AASHTTPServer server;

	private SQLRegistryExecutable() {
	}

	public static void main(String[] args) {
		logger.info("Starting BaSyx SQL registry");

		// Load configuration
		BaSyxContextConfiguration config = new BaSyxContextConfiguration();
		config.loadFromResource(BaSyxContextConfiguration.DEFAULT_CONFIG_PATH);
		startComponent(config.getHostname(), config.getPort(), config.getContextPath(), config.getDocBasePath(), "dockerRegistry.properties");
	}

	/**
	 * Starts the SQLRegistry at http://${hostName}:${port}/${path}
	 * 
	 * @param hostName
	 * @param port
	 * @param path
	 * @param docBasePath
	 */
	public static void startComponent(String hostName, int port, String path, String docBasePath, String sqlPropertyPath) {
		// Init HTTP context and add an SQLRegistryServlet according to the
		// configuration
		BaSyxContext context = new BaSyxContext(path, docBasePath, hostName, port);
		context.addServletMapping(SERVLET_MAPPING, new SQLRegistryServlet(sqlPropertyPath));

		// Create and start server
		server = new AASHTTPServer(context);
		logger.info("Starting server...");
		server.start();
	}

}