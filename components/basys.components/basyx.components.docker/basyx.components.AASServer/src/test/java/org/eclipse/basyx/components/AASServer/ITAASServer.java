package org.eclipse.basyx.components.AASServer;

import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxDockerConfiguration;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests the docker container using the test suite
 * 
 * @author schnicke
 *
 */
public class ITAASServer extends AASServerSuite {

	private static String URL;
	
	@Override
	protected String getURL() {
		return URL;
	}

	private static Logger logger = LoggerFactory.getLogger(AASServerSuite.class);

	@BeforeClass
	public static void setUpClass() {
		logger.info("Running integration test...");

		logger.info("Loading servlet configuration");
		// Load the servlet configuration inside of the docker configuration from
		// properties file
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration();
		contextConfig.loadFromResource(BaSyxContextConfiguration.DEFAULT_CONFIG_PATH);

		// Load the docker environment configuration from properties file
		logger.info("Loading docker configuration");
		BaSyxDockerConfiguration dockerConfig = new BaSyxDockerConfiguration();
		dockerConfig.loadFromResource(BaSyxDockerConfiguration.DEFAULT_CONFIG_PATH);

		URL = "http://localhost:" + dockerConfig.getHostPort() + contextConfig.getContextPath();
	}
}
