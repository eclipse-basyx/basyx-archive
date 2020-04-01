package org.eclipse.basyx.components;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.components.aasx.AASXPackageManager;
import org.eclipse.basyx.components.servlet.aas.AASBundleServlet;
import org.eclipse.basyx.support.bundle.AASBundle;
import org.eclipse.basyx.support.bundle.AASBundleDescriptorFactory;
import org.eclipse.basyx.vab.protocol.http.server.AASHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * A component that takes an AASX file and provides it via an HTTP server
 * 
 * @author schnicke
 *
 */
public class AASXComponent {

	// Creates a Logger based on the current class
	private static Logger logger = LoggerFactory.getLogger(AASXComponent.class);

	// The server with the servlet that will be created
	private AASHTTPServer server;

	private Set<AASBundle> aasBundles;

	private String hostName;
	private int port;
	private String path;
	private String docBasePath;

	public AASXComponent(String hostName, int port, String path, String docBasePath, String aasxPath) throws IOException, ParserConfigurationException, SAXException {
		aasBundles = new AASXPackageManager(aasxPath).retrieveAASBundles();
		this.hostName = hostName;
		this.port = port;
		this.path = path;
		this.docBasePath = docBasePath;
	}

	/**
	 * Returns the set of AAS descriptors for the AAS contained in the AASX
	 * 
	 * @param hostBasePath
	 *            the path to the server; helper method for e.g. virtualization
	 *            environments
	 * @return
	 */
	public Set<AASDescriptor> retrieveDescriptors(String hostBasePath) {
		return aasBundles.stream().map(
				b ->  AASBundleDescriptorFactory.createAASDescriptor(b, hostBasePath)
		).collect(Collectors.toSet());
	}

	/**
	 *  Returns the set of AAS descriptors for the AAS contained in the AASX
	 * @return
	 */
	public Set<AASDescriptor> retrieveDescriptors() {
		String hostBasePath = "http://" + hostName + ":" + port + "/" + path;
		return retrieveDescriptors(hostBasePath);
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
	public void startComponent() throws ParserConfigurationException, SAXException, IOException {
		// Init HTTP context and add an XMLAAServlet according to the configuration
		BaSyxContext context = new BaSyxContext(path, docBasePath, hostName, port);

		// Create the Servlet for aas
		context.addServletMapping("/*", new AASBundleServlet(aasBundles));

		// Create and start server
		server = new AASHTTPServer(context);

		logger.info("Starting server...");

		// start the server
		server.start();
	}
}
