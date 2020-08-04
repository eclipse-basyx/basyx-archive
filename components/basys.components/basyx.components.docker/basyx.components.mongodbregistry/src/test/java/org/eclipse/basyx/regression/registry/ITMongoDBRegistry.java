package org.eclipse.basyx.regression.registry;

import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxDockerConfiguration;
import org.eclipse.basyx.testsuite.regression.aas.registration.TestRegistryProviderSuite;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ITMongoDBRegistry extends TestRegistryProviderSuite {
	private static Logger logger = LoggerFactory.getLogger(ITMongoDBRegistry.class);

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

	@Override
	protected IAASRegistryService getRegistryService() {
		// Create a registry proxy directly pointing to the servlet
		return new AASRegistryProxy(registryUrl);
	}
}
