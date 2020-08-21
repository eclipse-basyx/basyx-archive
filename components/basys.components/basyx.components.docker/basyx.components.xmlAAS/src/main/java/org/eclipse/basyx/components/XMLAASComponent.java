package org.eclipse.basyx.components;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.components.configuration.BaSyxConfiguration;
import org.eclipse.basyx.components.servlet.aas.AASBundleServlet;
import org.eclipse.basyx.components.xml.XMLAASBundleFactory;
import org.eclipse.basyx.support.bundle.AASBundle;
import org.eclipse.basyx.support.bundle.AASBundleDescriptorFactory;
import org.eclipse.basyx.vab.protocol.http.server.AASHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * A component that takes an xml file (according to the AAS XML-Schema) and provides the AAS via an HTTP server
 * 
 * @author schnicke, espen
 *
 */
public class XMLAASComponent implements IComponent {
	private static Logger logger = LoggerFactory.getLogger(XMLAASComponent.class);

	// The server with the servlet that will be created
	protected AASHTTPServer server;

	protected Collection<AASBundle> aasBundles;

	protected String hostName;
	protected int port;
	protected String path;
	protected String docBasePath;
	protected String registryUrl;

	protected XMLAASComponent(String hostName, int port, String path, String docBasePath) {
		// Sets the server context (still needs to load the bundle)
		this.hostName = hostName;
		this.port = port;
		this.path = path;
		this.docBasePath = docBasePath;
	}

	public XMLAASComponent(String hostName, int port, String path, String docBasePath, String xmlPath,
			String registryUrl) throws ParserConfigurationException, SAXException, IOException {
		this(hostName, port, path, docBasePath);
		String xmlContent = BaSyxConfiguration.getResourceString(xmlPath);
		setAASBundle(new XMLAASBundleFactory(xmlContent).create());
		setRegistryUrl(registryUrl);
	}

	protected void setAASBundle(Collection<AASBundle> aasBundles) {
		this.aasBundles = aasBundles;
	}

	protected void setRegistryUrl(String registryUrl) {
		this.registryUrl = registryUrl;
	}


	/**
	 * Returns the set of AAS descriptors for the AAS contained in the AASX
	 * 
	 * @param hostBasePath
	 *                     the path to the server; helper method for e.g. virtualization
	 *                     environments
	 * @return
	 */
	public Set<AASDescriptor> retrieveDescriptors(String hostBasePath) {
		return aasBundles.stream().map(b -> AASBundleDescriptorFactory.createAASDescriptor(b, hostBasePath))
				.collect(Collectors.toSet());
	}

	/**
	 * Returns the set of AAS descriptors for the AAS contained in the AASX
	 * 
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
	@Override
	public void startComponent() {
		logger.info("Create the server...");
		// Init HTTP context and add an XMLAAServlet according to the configuration
		BaSyxContext context = new BaSyxContext(path, docBasePath, hostName, port);
		// Create the Servlet for aas
		context.addServletMapping("/*", new AASBundleServlet(aasBundles));
		server = new AASHTTPServer(context);


		logger.info("Start the server...");
		server.start();

		if (registryUrl != null && !registryUrl.isEmpty()) {
			logger.info("Registering AAS at registry \"" + registryUrl + "\"...");
			AASRegistryProxy registryProxy = new AASRegistryProxy(registryUrl);
			Set<AASDescriptor> descriptors = retrieveDescriptors();
			descriptors.stream().forEach(registryProxy::register);
		} else {
			logger.info("No registry specified, skipped registration");
		}
	}

	@Override
	public void stopComponent() {
		server.shutdown();
	}
}
