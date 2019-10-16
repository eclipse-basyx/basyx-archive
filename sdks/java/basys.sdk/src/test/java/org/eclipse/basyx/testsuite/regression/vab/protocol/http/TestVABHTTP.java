package org.eclipse.basyx.testsuite.regression.vab.protocol.http;

import org.eclipse.basyx.testsuite.regression.vab.modelprovider.TestProvider;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.junit.Rule;

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

}
