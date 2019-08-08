package org.eclipse.basyx.testsuite.regression.vab.connector;

import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.testsuite.regression.vab.provider.TestProvider;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory;
import org.eclipse.basyx.testsuite.support.backend.servers.AASHTTPServerResource;
import org.eclipse.basyx.vab.core.VABConnectionManager;
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
	 * Makes sure Tomcat Server is started
	 */
	@Rule
	public AASHTTPServerResource res = AASHTTPServerResource.getTestResource();

	@Override
	protected VABConnectionManager getConnectionManager() {
		return connManager;
	}

}
