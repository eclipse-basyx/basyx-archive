package org.eclipse.basyx.regression.aasx;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxDockerConfiguration;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Test case for accessing an element of an aas which is hosted on Docker
 * 
 *
 */
public class ITInAASX extends AASXSuite {
	private static Logger logger = LoggerFactory.getLogger(ITInAASX.class);

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

		rootEndpoint = "http://localhost:" + dockerConfig.getHostPort() + contextConfig.getContextPath() + "/";
		aasEndpoint = rootEndpoint + aasShortId + "/aas";
		smEndpoint = rootEndpoint + aasShortId + "/aas/submodels/" + smShortId + "/submodel";

		waitUntilReady();

		logger.info("AAS URL for integration test: " + aasEndpoint);
	}

	/**
	 * Waits for at most 4s until the container is ready
	 */
	private static void waitUntilReady() {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(rootEndpoint);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		for (int i = 0; i < 20; i++) {
			try {
				invocationBuilder.get();
				return;
			} catch (ProcessingException e) {
				// retry
				try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
