package org.eclipse.basyx.components.executable;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.servlets.XMLAASFactory;
import org.eclipse.basyx.vab.protocol.http.server.AASHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * A XML registry servlet based on an InMemory Registry. The servlet therefore
 * provides an implementation for the IAASRegistryService interface without a
 * permanent storage capability. it imports AAS from given XML location provided
 * in the context.properties and maps the AAS to servlet as well
 * 
 * Do not use this registry in a productive environment - the entries are not
 * persistent!
 * 
 * @author haque
 */
public class XMLExecutable {

	// Creates a Logger based on the current class
	private static Logger logger = LoggerFactory.getLogger(XMLExecutable.class);

	// The path the created servlet is mapped to
	public static final String SERVLET_MAPPING = "/";
	
	// The server with the servlet that will be created
	private static AASHTTPServer server;

	// Default constructor
	private XMLExecutable() {
	}

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		logger.info("Starting BaSyx XML registry");

		// Load configuration
		BaSyxContextConfiguration config = new BaSyxContextConfiguration();
		config.loadFromResource(BaSyxContextConfiguration.DEFAULT_CONFIG_PATH);

		// Create a context suing the configuration
		BaSyxContext context = XMLAASFactory.createContext(config);

		// Create and start server
		server = new AASHTTPServer(context);
		logger.info("Starting server...");
		server.start();
	}

}