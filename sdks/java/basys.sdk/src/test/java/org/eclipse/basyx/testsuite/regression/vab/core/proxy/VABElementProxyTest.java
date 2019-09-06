package org.eclipse.basyx.testsuite.regression.vab.core.proxy;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.aas.impl.metamodel.hashmap.VABModelMap;
import org.eclipse.basyx.testsuite.support.vab.stub.VABConnectionManagerStub;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;
import org.junit.Test;

/**
 * Tests the VABElementProxy class
 * 
 * @author schnicke
 *
 */
public class VABElementProxyTest {

	/**
	 * Tests the capability of VABElementProxy to create a new proxy element
	 * pointing deeper into the element it is a proxy to
	 */
	@Test
	public void testGetDeepProxy() {
		// Create test map
		VABModelMap<Object> map = new VABModelMap<>();
		map.putPath("a/b/c", 0);

		// Setup provider and connection manager
		VABHashmapProvider provider = new VABHashmapProvider(map);
		VABConnectionManagerStub stub = new VABConnectionManagerStub(provider);

		// Connect to element
		VABElementProxy proxy = stub.connectToVABElement("");

		// Connect to element <i>a/b</i>
		VABElementProxy bProxy = proxy.getDeepProxy("a/b");

		assertEquals(0, bProxy.getModelPropertyValue("c"));
	}
}
