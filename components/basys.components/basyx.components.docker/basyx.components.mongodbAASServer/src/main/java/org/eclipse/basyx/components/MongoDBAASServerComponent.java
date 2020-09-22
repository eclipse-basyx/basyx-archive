package org.eclipse.basyx.components;

import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxMongoDBConfiguration;
import org.eclipse.basyx.components.servlet.MongoDBAASServerServlet;
import org.eclipse.basyx.vab.protocol.http.server.AASHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Component providing a persistent AAS server that is able to receive AAS/SMs from
 * remote. It uses the Aggregator API, i.e. AAS should be pushed to ${URL}/aasList
 * 
 * @author espen
 *
 */
public class MongoDBAASServerComponent {
	private static Logger logger = LoggerFactory.getLogger(MongoDBAASServerComponent.class);

	// The component configuration
	private BaSyxContextConfiguration contextConfig;
	private BaSyxMongoDBConfiguration dbConfig;

	// The server with the servlet that will be created
	private AASHTTPServer server;

	/**
	 * Default constructor that loads default configurations
	 */
	public MongoDBAASServerComponent() {
		this.contextConfig = new BaSyxContextConfiguration();
		this.dbConfig = new BaSyxMongoDBConfiguration();
	}

	/**
	 * Constructs an empty AAS server using the passed arguments
	 * 
	 * @param contextConfig
	 * @param dbConfig
	 */
	public MongoDBAASServerComponent(BaSyxContextConfiguration contextConfig, BaSyxMongoDBConfiguration dbConfig) {
		this.contextConfig = contextConfig;
		this.dbConfig = dbConfig;
	}

	/**
	 * Starts the AASX component at http://${hostName}:${port}/${path}
	 */
	public void startComponent() {
		logger.info("Create the server...");
		// Init HTTP context and add an XMLAAServlet according to the configuration
		BaSyxContext context = contextConfig.createBaSyxContext();
		// Create the Servlet for aas
		context.addServletMapping("/*", new MongoDBAASServerServlet(dbConfig));
		server = new AASHTTPServer(context);

		logger.info("Start the server...");
		server.start();
	}

	/**
	 * Retrieves the URL on which the component is providing its HTTP server
	 * 
	 * @return
	 */
	public String getURL() {
		return contextConfig.getUrl();
	}
}
