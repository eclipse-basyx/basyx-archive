package org.eclipse.basyx.testsuite.regression.vab.connector;

import org.eclipse.basyx.aas.backend.connector.basyx.BaSyxConnectorProvider;
import org.eclipse.basyx.testsuite.regression.vab.provider.TestProvider;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory_BaSyxNative;
import org.eclipse.basyx.testsuite.support.backend.servers.AASTCPServerResource;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.junit.Rule;

/**
 * Test VAB using the BaSyx protocol. This is an integration test
 * 
 * @author schnicke, pschorn
 *
 */
public class TestVABBaSyxTCP extends TestProvider {
	protected VABConnectionManager connManager = new VABConnectionManager(new TestsuiteDirectory_BaSyxNative(),
			new BaSyxConnectorProvider());

	@Rule
	public AASTCPServerResource res = AASTCPServerResource.getTestResource();

	@Override
	protected VABConnectionManager getConnectionManager() {
		return connManager;
	}
}
