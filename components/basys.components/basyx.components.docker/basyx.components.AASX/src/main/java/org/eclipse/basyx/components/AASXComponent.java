package org.eclipse.basyx.components;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.catalina.servlets.DefaultServlet;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.components.aasx.AASXPackageManager;
import org.eclipse.basyx.components.aasx.SubmodelFileEndpointLoader;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.servlet.aas.AASBundleServlet;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.support.bundle.AASBundle;
import org.eclipse.basyx.vab.protocol.http.server.AASHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.xml.sax.SAXException;

/**
 * A component that takes an AASX file and provides it via an HTTP server
 * 
 * @author schnicke, espen
 *
 */
public class AASXComponent extends XMLAASComponent {
	public AASXComponent(BaSyxContextConfiguration contextConfig, String aasxPath,
			String registryUrl) throws IOException, ParserConfigurationException, SAXException, URISyntaxException {
		super(contextConfig);

		// Instantiate the aasx package manager
		AASXPackageManager packageManager = new AASXPackageManager(aasxPath);

		// Unpack the files referenced by the aas
		packageManager.unzipRelatedFiles(aasxPath);

		// Retrieve the aas from the package
		Set<AASBundle> aasBundles = packageManager.retrieveAASBundles();

		setAASBundle(aasBundles);
		setRegistryUrl(registryUrl);
	}

	/**
	 * Starts the AASX component at http://${hostName}:${port}/${path}
	 * 
	 * @param hostName
	 * @param port
	 * @param path
	 * @param docBasePath
	 * @throws ServletException
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	@Override
	public void startComponent() {
		// Init HTTP context and add an XMLAAServlet according to the configuration
		BaSyxContext context = contextConfig.createBaSyxContext();
		// Create the Servlet for aas
		context.addServletMapping("/*", new AASBundleServlet(aasBundles));
		context.addServletMapping("/aasx/*", new DefaultServlet());
		server = new AASHTTPServer(context);

		// Fix the file paths according to the servlet configuration
		modifyFilePaths(contextConfig.getHostname(), contextConfig.getPort(), "");

		// logger.info("Start the server...");
		server.start();

		if (registryUrl != null && !registryUrl.isEmpty()) {
			// logger.info("Registering AAS at registry \"" + registryUrl + "\"...");
			AASRegistryProxy registryProxy = new AASRegistryProxy(registryUrl);
			Set<AASDescriptor> descriptors = retrieveDescriptors();
			descriptors.stream().forEach(registryProxy::register);
		} else {
			// logger.info("No registry specified, skipped registration");
		}
	}

	/**
	 * Fixes the File submodel element value paths according to the given endpoint configuration
	 */
	private void modifyFilePaths(String hostName, int port, String rootPath) {
		for (AASBundle bundle : aasBundles) {
			Set<ISubModel> submodels = bundle.getSubmodels();
			for (ISubModel sm : submodels) {
				SubmodelFileEndpointLoader.setRelativeFileEndpoints(sm, hostName, port, rootPath);
			}
		}
	}
}
