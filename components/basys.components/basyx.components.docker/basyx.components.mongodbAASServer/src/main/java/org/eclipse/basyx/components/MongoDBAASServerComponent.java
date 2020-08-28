package org.eclipse.basyx.components;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.basyx.components.configuration.BaSyxMongoDBConfiguration;
import org.eclipse.basyx.components.servlet.MongoDBAASServerServlet;
import org.eclipse.basyx.vab.protocol.http.server.AASHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * Component providing a persistent AAS server that is able to receive AAS/SMs from
 * remote. It uses the Aggregator API, i.e. AAS should be pushed to ${URL}/aasList
 * 
 * @author espen
 *
 */
public class MongoDBAASServerComponent {
	private static Logger logger = LoggerFactory.getLogger(MongoDBAASServerComponent.class);

	// The server with the servlet that will be created
	private AASHTTPServer server;

	private String hostName;
	private int port;
	private String path;
	private String docBasePath;
	private BaSyxMongoDBConfiguration config;

	/**
	 * Constructs an empty AAS server using the passed arguments
	 * 
	 * @param hostName
	 * @param port
	 * @param path
	 * @param docBasePath
	 */
	public MongoDBAASServerComponent(String hostName, int port, String path, String docBasePath) {
		// Sets the server context
		this.hostName = hostName;
		this.port = port;
		this.path = path;
		this.docBasePath = docBasePath;
		this.config = new BaSyxMongoDBConfiguration();
	}

	/**
	 * Constructs an empty AAS server using the passed arguments
	 * 
	 * @param hostName
	 * @param port
	 * @param path
	 * @param docBasePath
	 */
	public MongoDBAASServerComponent(String hostName, int port, String path, String docBasePath,
			BaSyxMongoDBConfiguration config) {
		// Sets the server context
		this.hostName = hostName;
		this.port = port;
		this.path = path;
		this.docBasePath = docBasePath;
		this.config = config;
	}

	/**
	 * Starts the AASX component at http://${hostName}:${port}/${path}
	 * 
	 * @param hostName
	 * @param port
	 * @param path
	 * @param docBasePath
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public void startComponent() {
		logger.info("Create the server...");
		// Init HTTP context and add an XMLAAServlet according to the configuration
		BaSyxContext context = new BaSyxContext(path, docBasePath, hostName, port);
		// Create the Servlet for aas
		context.addServletMapping("/*", new MongoDBAASServerServlet(config));
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
		return "http://" + hostName + ":" + port + "/" + path;
	}
}
