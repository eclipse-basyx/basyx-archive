package org.eclipse.basyx.components;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.servlet.AASServerServlet;
import org.eclipse.basyx.vab.protocol.http.server.AASHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * Component providing an empty AAS server that is able to receive AAS/SMs from
 * remote. It uses the Aggregator API, i.e. AAS should be pushed to
 * ${URL}/aasList
 * 
 * @author schnicke
 *
 */
public class AASServerComponent implements IComponent {
	private static Logger logger = LoggerFactory.getLogger(AASServerComponent.class);

	// The server with the servlet that will be created
	private AASHTTPServer server;

	private BaSyxContextConfiguration contextConfig;

	/**
	 * Constructs an empty AAS server using the passed arguments
	 * 
	 * @param hostName
	 * @param port
	 * @param path
	 * @param docBasePath
	 */
	public AASServerComponent(BaSyxContextConfiguration contextConfig) {
		this.contextConfig = contextConfig;
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
	@Override
	public void startComponent() {
		logger.info("Create the server...");
		// Init HTTP context and add an XMLAAServlet according to the configuration
		BaSyxContext context = contextConfig.createBaSyxContext();
		// Create the Servlet for aas
		context.addServletMapping("/*", new AASServerServlet());
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

	@Override
	public void stopComponent() {
		server.shutdown();
	}
}
