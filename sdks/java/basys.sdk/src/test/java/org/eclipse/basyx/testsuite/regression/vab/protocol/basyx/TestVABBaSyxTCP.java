package org.eclipse.basyx.testsuite.regression.vab.protocol.basyx;

import org.eclipse.basyx.testsuite.regression.vab.modelprovider.SimpleVABElement;
import org.eclipse.basyx.testsuite.regression.vab.modelprovider.TestProvider;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.map.VABHashmapProvider;
import org.eclipse.basyx.vab.protocol.basyx.connector.BaSyxConnectorProvider;
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
	public VABTCPServerResource res = new VABTCPServerResource(new VABHashmapProvider(new SimpleVABElement()));

	@Override
	protected VABConnectionManager getConnectionManager() {
		return connManager;
	}
}
