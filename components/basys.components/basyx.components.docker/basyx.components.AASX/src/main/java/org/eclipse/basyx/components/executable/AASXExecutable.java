package org.eclipse.basyx.components.executable;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.servlets.AASXAASServlet;
import org.eclipse.basyx.vab.protocol.http.server.AASHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * Starts an HTTP server providing multiple AAS and submodels as described in
 * the AASX package file specified in the properties file <br />
 * They are made available at <i>localhost:4000/aasx/$aasId/aas</i><br />
 * <br />
 * <b>Please note:</b> Neither the AASs nor the Submodels are added to the
 * registry. Additionally, the Submodel descriptors inside the AAS are missing.
 * <br />
 * There reason for this is, that the executable does not know about the outside
 * context (e.g. docker, ...)!
 * 
 * @author zhang
 */
public class AASXExecutable {
	// Creates a Logger based on the current class
	private static Logger logger = LoggerFactory.getLogger(AASXExecutable.class);

	// The path the created servlet is mapped to
	public static final String SERVLET_MAPPING = "/*";

	// The server with the servlet that will be created
	private static AASHTTPServer server;

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		logger.info("Starting BaSyx AASX component");

		// Load configuration
		BaSyxContextConfiguration config = new BaSyxContextConfiguration();
		config.loadFromResource(BaSyxContextConfiguration.DEFAULT_CONFIG_PATH);

		// Init HTTP context and add an XMLAAServlet according to the configuration
		BaSyxContext context = new BaSyxContext(config.getContextPath(), config.getDocBasePath(), config.getHostname(), config.getPort());

		// Create the Servlet for aas
		String aasxPath = config.getProperty("aasxPath");

		context.addServletMapping(SERVLET_MAPPING, new AASXAASServlet(aasxPath));

		// Create and start server
		server = new AASHTTPServer(context);

		logger.info("Starting server...");

		// start the server
		server.start();

	}



}
