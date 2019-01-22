package org.eclipse.basyx.testsuite.regression.vab.connector;

import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.testsuite.regression.vab.snippet.CreateDelete;
import org.eclipse.basyx.testsuite.regression.vab.snippet.GetPropertyValue;
import org.eclipse.basyx.testsuite.regression.vab.snippet.Invoke;
import org.eclipse.basyx.testsuite.regression.vab.snippet.SetPropertyValue;
import org.eclipse.basyx.testsuite.regression.vab.snippet.TestCollectionProperty;
import org.eclipse.basyx.testsuite.regression.vab.snippet.TestMapProperty;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory;
import org.eclipse.basyx.testsuite.support.backend.servers.AASHTTPServerResource;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * Test VAB using HTTP protocol. This is an integration test
 * 
 * @author schnicke, pschorn
 *
 */
public class TestVABHTTP {
	protected VABConnectionManager connManager = new VABConnectionManager(new TestsuiteDirectory(), new HTTPConnectorProvider());
	
	
	/** 
	 * Makes sure Tomcat Server is started
	 */
	@ClassRule
	public static AASHTTPServerResource res = AASHTTPServerResource.getTestResource();
	
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
