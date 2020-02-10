package org.eclipse.basyx.regression.docker;

import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.aas.registration.restapi.DirectoryModelProvider;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxDockerConfiguration;
import org.eclipse.basyx.testsuite.regression.aas.registration.proxy.TestRegistryProvider;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ITInMemoryRegistry extends TestRegistryProvider {
	private static Logger logger = LoggerFactory.getLogger(ITInMemoryRegistry.class);

	private static String registryUrl;

	@BeforeClass
	public static void setUpClass() {
		logger.info("Running integration test...");

		logger.info("Loading servlet configuration");
		// Load the servlet configuration inside of the docker configuration from properties file
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration();
		contextConfig.loadFromResource(BaSyxContextConfiguration.DEFAULT_CONFIG_PATH);

		// Load the docker environment configuration from properties file
		logger.info("Loading docker configuration");
		BaSyxDockerConfiguration dockerConfig = new BaSyxDockerConfiguration();
		dockerConfig.loadFromResource(BaSyxDockerConfiguration.DEFAULT_CONFIG_PATH);

		registryUrl = "http://localhost:" + dockerConfig.getHostPort() + contextConfig.getContextPath();
		logger.info("Registry URL for integration test: " + registryUrl);
	}

	/**
	 * Returns a model provider proxy for directly accessing the registry created by the http servlet
	 */
	@Override
	protected IModelProvider getProxyProvider() {
		// Create a registry proxy directly pointing to the servlet
		IAASRegistryService registryProxy = new AASRegistryProxy(registryUrl);
		// Wrap the proxy in an IModelProvider
		DirectoryModelProvider provider = new DirectoryModelProvider(registryProxy);
		// Append the necessary registry API access to each request
		IModelProvider apiProxy = new VABElementProxy("/api/v1/registry", provider);
		return apiProxy;
	}
}
