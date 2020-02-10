package org.eclipse.basyx.components.executable;

import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.servlets.InMemoryRegistryServlet;
import org.eclipse.basyx.vab.protocol.http.server.AASHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A registry servlet based on an InMemory Registry. The servlet therefore provides an implementation
 * for the IAASRegistryService interface without a permanent storage capability.
 * 
 * Do not use this registry in a productive environment - the entries are not persistent!
 * 
 * @author espen
 */
public class InMemoryRegistryExecutable {
	private static Logger logger = LoggerFactory.getLogger(InMemoryRegistryExecutable.class);



	// The path the created servlet is mapped to
	public static final String SERVLET_MAPPING = "/";
	
	// The server with the servlet that will be created
	private static AASHTTPServer server;

	private InMemoryRegistryExecutable() {
	}

	public static void main(String[] args) {
		logger.info("Starting BaSyx InMemory registry");

		// Load configuration
		BaSyxContextConfiguration config = new BaSyxContextConfiguration();
		config.loadFromResource(BaSyxContextConfiguration.DEFAULT_CONFIG_PATH);
		// Init HTTP context and add an InMemoryRegistryServlet according to the configuration
		BaSyxContext context = new BaSyxContext(config.getContextPath(), config.getDocBasePath(), config.getHostname(),
				config.getPort());
		context.addServletMapping(SERVLET_MAPPING + "*", new InMemoryRegistryServlet());

		// Create and start server
		server = new AASHTTPServer(context);
		logger.info("Starting server...");
		server.start();
	}

}