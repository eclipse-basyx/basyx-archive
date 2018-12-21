package org.eclipse.basyx.testsuite.regression.vab.connector;

import org.eclipse.basyx.aas.backend.connector.basyx.BaSyxConnectorProvider;
import org.eclipse.basyx.testsuite.regression.vab.snippet.CreateDelete;
import org.eclipse.basyx.testsuite.regression.vab.snippet.GetPropertyValue;
import org.eclipse.basyx.testsuite.regression.vab.snippet.Invoke;
import org.eclipse.basyx.testsuite.regression.vab.snippet.SetPropertyValue;
import org.eclipse.basyx.testsuite.regression.vab.snippet.TestCollectionProperty;
import org.eclipse.basyx.testsuite.regression.vab.snippet.TestMapProperty;
import org.eclipse.basyx.testsuite.support.backend.basyx.servers.AASServer;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory_BaSyxNative;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test VAB using the BaSyx protocol
 * 
 * pschorn: This is actually an integration test and should be declared as such. 
 * TODO: Requires TCP Server to be startet first. Start Server before this test class runs and tear down after it finished.
 * 
 * @author schnicke, pschorn
 *
 */
public class TestVABBaSyx {
	protected VABConnectionManager connManager = new VABConnectionManager(new TestsuiteDirectory_BaSyxNative(),
			new BaSyxConnectorProvider());
	
	static AASServer server;
	
	@BeforeClass
	public static void startServer() {
		server = new AASServer();
		server.start();
	}
	
	@AfterClass
	public static void stopServer() {
	 	server.shutdown();
	}

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

	@Test
	public void testMapGet() {
		TestMapProperty.testGet(connManager);
	}

	@Test
	public void testMapUpdateComplete() {
		TestMapProperty.testUpdateComplete(connManager);
	}

	@Test
	public void testMapUpdateElement() {
		TestMapProperty.testUpdateElement(connManager);
	}

	@Test
	public void testMapRemoveElement() {
		TestMapProperty.testRemoveElement(connManager);
	}

	@Test
	public void testCollectionGet() {
		TestCollectionProperty.testGet(connManager);
	}

	@Test
	public void testCollectionUpdateComplete() {
		TestCollectionProperty.testUpdateComplete(connManager);
	}

	@Test
	public void testCollectionUpdateElement() {
		TestCollectionProperty.testUpdateElement(connManager);
	}

	@Test
	public void testCollectionRemoveElement() {
		TestCollectionProperty.testRemoveElement(connManager);
	}
}
