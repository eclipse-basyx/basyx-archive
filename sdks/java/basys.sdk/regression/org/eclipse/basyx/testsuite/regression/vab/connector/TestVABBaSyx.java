package org.eclipse.basyx.testsuite.regression.vab.connector;

import org.eclipse.basyx.aas.backend.connector.basyx.BaSyxConnectorProvider;
import org.eclipse.basyx.testsuite.regression.vab.snippet.CreateDelete;
import org.eclipse.basyx.testsuite.regression.vab.snippet.GetPropertyValue;
import org.eclipse.basyx.testsuite.regression.vab.snippet.Invoke;
import org.eclipse.basyx.testsuite.regression.vab.snippet.SetPropertyValue;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory_BaSyxNative;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.junit.Test;

/**
 * Test VAB using the BaSyx protocol
 * @author pschorn
 *
 */
public class TestVABBaSyx {
	protected VABConnectionManager connManager = new VABConnectionManager(new TestsuiteDirectory_BaSyxNative(),
			new BaSyxConnectorProvider());

	@Test
	public void testCreateDelete() {
		CreateDelete.test(connManager);
	}

	@Test
	public void testGet() {
		GetPropertyValue.test(connManager);
	}

	@Test
	public void testInvoke() {
		Invoke.test(connManager);
	}

	@Test
	public void testSet() {
		SetPropertyValue.test(connManager);
	}
}
