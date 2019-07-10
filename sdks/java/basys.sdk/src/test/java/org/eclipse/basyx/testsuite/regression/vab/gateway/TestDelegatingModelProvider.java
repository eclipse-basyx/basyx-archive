package org.eclipse.basyx.testsuite.regression.vab.gateway;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.testsuite.support.vab.stub.IModelProviderStub;
import org.eclipse.basyx.vab.backend.gateway.DelegatingModelProvider;
import org.eclipse.basyx.vab.core.IConnectorProvider;
import org.eclipse.basyx.vab.core.IModelProvider;
import org.junit.Test;

/**
 * Tests the behaviour of DelegatingModelProvider
 * 
 * @author schnicke
 *
 */
public class TestDelegatingModelProvider {
	private String address;
	private IModelProviderStub stub = new IModelProviderStub();

	/**
	 * Tests if the DelegatingModelProvider delegates correctly
	 * 
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		// Test addresses
		String basyx = "basyx://12.34.56.78:9090";
		String rest = "http://abc.de//a/b/c";

		// Create DelegatingModelProvider with a stub writing the address and returning
		// the IModelProviderStub
		DelegatingModelProvider provider = new DelegatingModelProvider(new IConnectorProvider() {

			@Override
			public IModelProvider getConnector(String addr) {
				address = addr;
				return stub;
			}
		});

		// Get a value based on path
		provider.getModelPropertyValue(basyx + "//" + rest);

		// Assert that correct address was given to IConnectorProvider
		assertEquals(address, basyx);

		// Assert that stub was passed the correct rest of the path
		assertEquals(rest, stub.getPath());
	}
}
