package org.eclipse.basyx.testsuite.regression.vab.protocol.http;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.eclipse.basyx.testsuite.regression.vab.modelprovider.TestProvider;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test VAB using HTTP protocol. This is an integration test
 * 
 * @author schnicke, pschorn
 *
 */
public class TestVABHTTP extends TestProvider {
	protected VABConnectionManager connManager = new VABConnectionManager(new TestsuiteDirectory(),
			new HTTPConnectorProvider());

	/**
	 * Makes sure Tomcat Server is started after before each test case
	 */
	@Rule
	public AASHTTPServerResource res = new AASHTTPServerResource(
			new BaSyxContext("/basys.sdk", System.getProperty("java.io.tmpdir"))
					.addServletMapping("/Testsuite/SimpleVAB/*", new SimpleVABElementServlet()));

	@Override
	protected VABConnectionManager getConnectionManager() {
		return connManager;
	}

	/**
	 * Tests for URL with no ending slash when accessing the root element, e.g.
	 * http://localhost:8080/basys.sdk/Testsuite/SimpleVAB <br />
	 * The SDK ensures that each access ends with a <i>/</i>. However, browser
	 * requests do not necessarily conform to this
	 */
	@Test
	public void testRootURL() {
		Client client = ClientBuilder.newClient();

		// Called URL
		WebTarget resource = client.target("http://localhost:8080/basys.sdk/Testsuite/SimpleVAB");

		// Build request, set JSON encoding
		Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);

		// Perform request, no exception should be thrown here
		request.get(String.class);
	}
}
