package org.eclipse.basyx.components.aas;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.catalina.servlets.DefaultServlet;
import org.eclipse.basyx.aas.aggregator.AASAggregator;
import org.eclipse.basyx.aas.aggregator.api.IAASAggregator;
import org.eclipse.basyx.aas.aggregator.restapi.AASAggregatorProvider;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.components.IComponent;
import org.eclipse.basyx.components.aas.aasx.AASXPackageManager;
import org.eclipse.basyx.components.aas.aasx.SubmodelFileEndpointLoader;
import org.eclipse.basyx.components.aas.configuration.AASServerBackend;
import org.eclipse.basyx.components.aas.configuration.BaSyxAASServerConfiguration;
import org.eclipse.basyx.components.aas.mongodb.MongoDBAASAggregator;
import org.eclipse.basyx.components.aas.servlet.AASAggregatorServlet;
import org.eclipse.basyx.components.configuration.BaSyxConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxMongoDBConfiguration;
import org.eclipse.basyx.components.xml.XMLAASBundleFactory;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.support.bundle.AASBundle;
import org.eclipse.basyx.support.bundle.AASBundleDescriptorFactory;
import org.eclipse.basyx.support.bundle.AASBundleIntegrator;
import org.eclipse.basyx.vab.protocol.http.server.AASHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * Component providing an empty AAS server that is able to receive AAS/SMs from
 * remote. It uses the Aggregator API, i.e. AAS should be pushed to
 * ${URL}/shells
 * 
 * @author schnicke, espen
 *
 */
public class AASServerComponent implements IComponent {
	private static Logger logger = LoggerFactory.getLogger(AASServerComponent.class);

	// The server with the servlet that will be created
	private AASHTTPServer server;

	// Configurations
	private BaSyxContextConfiguration contextConfig;
	private BaSyxAASServerConfiguration aasConfig;
	private BaSyxMongoDBConfiguration mongoDBConfig;

	// Initial AASBundle
	protected Collection<AASBundle> aasBundles;

	/**
	 * Constructs an empty AAS server using the passed context
	 */
	public AASServerComponent(BaSyxContextConfiguration contextConfig) {
		this.contextConfig = contextConfig;
		this.aasConfig = new BaSyxAASServerConfiguration();
	}

	/**
	 * Constructs an empty AAS server using the passed configuration
	 */
	public AASServerComponent(BaSyxContextConfiguration contextConfig, BaSyxAASServerConfiguration aasConfig) {
		this.contextConfig = contextConfig;
		this.aasConfig = aasConfig;
	}

	/**
	 * Constructs an empty AAS server using the passed configuration
	 */
	public AASServerComponent(BaSyxContextConfiguration contextConfig, BaSyxAASServerConfiguration aasConfig,
			BaSyxMongoDBConfiguration mongoDBConfig) {
		this.contextConfig = contextConfig;
		this.aasConfig = aasConfig;
		this.aasConfig.setAASBackend(AASServerBackend.MONGODB);
		this.mongoDBConfig = mongoDBConfig;
	}

	/**
	 * Starts the AASX component at http://${hostName}:${port}/${path}
	 */
	@Override
	public void startComponent() {
		logger.info("Create the server...");
		// Load the aggregator servlet
		AASAggregatorServlet aggregatorServlet = loadAggregatorServlet();

		// Init HTTP context and add an XMLAAServlet according to the configuration
		BaSyxContext context = contextConfig.createBaSyxContext();
		context.addServletMapping("/*", aggregatorServlet);

		// An initial AAS has been loaded from the drive?
		if (aasBundles != null) {
			// 1. Also provide the files
			context.addServletMapping("/aasx/*", new DefaultServlet());

			// 2. Fix the file paths according to the servlet configuration
			modifyFilePaths(contextConfig.getHostname(), contextConfig.getPort(), contextConfig.getContextPath());

			// 3. Register the initial AAS
			registerAAS();
		}

		logger.info("Start the server");
		server = new AASHTTPServer(context);
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

	private void loadBundleFromXML(String xmlPath) throws IOException, ParserConfigurationException, SAXException {
		logger.info("Loading aas from xml \"" + xmlPath + "\"");
		String xmlContent = BaSyxConfiguration.getResourceString(xmlPath);
		this.aasBundles = new XMLAASBundleFactory(xmlContent).create();
	}

	private void loadBundleFromAASX(String aasxPath)
			throws IOException, ParserConfigurationException, SAXException, URISyntaxException {
		logger.info("Loading aas from aasx \"" + aasxPath + "\"");

		// Instantiate the aasx package manager
		AASXPackageManager packageManager = new AASXPackageManager(aasxPath);

		// Unpack the files referenced by the aas
		packageManager.unzipRelatedFiles(aasxPath);

		// Retrieve the aas from the package
		this.aasBundles = packageManager.retrieveAASBundles();

		// this.aasBundles.forEach(b -> Identifiable
		// .createAsFacade((AssetAdministrationShell) b.getAAS(), KeyElements.ASSETADMINISTRATIONSHELL)
		// .setIdentification(IdentifierType.CUSTOM, b.getAAS().getIdentification().getId().split("/")[0]));
	}

	private AASAggregatorServlet loadAggregatorServlet() {
		// Load the initial AAS bundles from given source
		loadAASFromSource(aasConfig.getAASSource());

		// Load the aggregator
		IAASAggregator aggregator = loadAASAggregator();

		// Integrate the loaded bundles into the aggregator
		if (aasBundles != null) {
			AASBundleIntegrator.integrate(aggregator, aasBundles);
		}

		// Return the servlet for the resulting aggregator
		return new AASAggregatorServlet(aggregator);
	}

	private void loadAASFromSource(String aasSource) {
		if (aasSource.isEmpty()) {
			return;
		}

		try {
			if (aasSource.endsWith(".aasx")) {
				loadBundleFromAASX(aasSource);
			} else if (aasSource.endsWith(".xml")) {
				loadBundleFromXML(aasSource);
			}
		} catch (IOException | ParserConfigurationException | SAXException | URISyntaxException e) {
			logger.error("Could not load initial AAS from source " + aasSource);
			e.printStackTrace();
		}
	}


	private void registerAAS() {
		String registryUrl = this.aasConfig.getRegistry();
		if (registryUrl != null && !registryUrl.isEmpty()) {
			logger.info("Registering AAS at registry \"" + registryUrl + "\"");
			AASRegistryProxy registryProxy = new AASRegistryProxy(registryUrl);
			Set<AASDescriptor> descriptors = retrieveDescriptors(contextConfig.getUrl());
			descriptors.stream().forEach(registryProxy::register);
		} else {
			logger.info("No registry specified, skipped registration");
		}
	}

	/**
	 * Returns the set of AAS descriptors for the AAS contained in the AASX
	 * 
	 * @param hostBasePath
	 *                     the path to the server; helper method for e.g. virtualization
	 *                     environments
	 * @return
	 */
	private Set<AASDescriptor> retrieveDescriptors(String hostBasePath) {
		// Base path + aggregator accessor
		final String fullBasePath = hostBasePath + "/" + AASAggregatorProvider.PREFIX;

		return aasBundles.stream().map(b -> AASBundleDescriptorFactory.createAASDescriptor(b, fullBasePath))
				.collect(Collectors.toSet());
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

	/**
	 * Loads a aas aggregator servlet with a backend according to the configuration
	 * 
	 * @return
	 */
	private IAASAggregator loadAASAggregator() {
		// Get aggregator according to backend config
		AASServerBackend backendType = aasConfig.getAASBackend();
		IAASAggregator aggregator = null;
		if ( backendType == AASServerBackend.INMEMORY ) {
			logger.info("Using InMemory backend");
			aggregator = new AASAggregator();
		} else if ( backendType == AASServerBackend.MONGODB ) {
			logger.info("Using MongoDB backend");
			aggregator = loadMongoDBAggregator();
		}

		return aggregator;
	}

	private IAASAggregator loadMongoDBAggregator() {
		BaSyxMongoDBConfiguration config;
		if (this.mongoDBConfig == null) {
			config = new BaSyxMongoDBConfiguration();
			config.loadFromDefaultSource();
		} else {
			config = this.mongoDBConfig;
		}
		return new MongoDBAASAggregator(config);
	}
}
