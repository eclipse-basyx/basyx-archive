package org.eclipse.basyx.testsuite.regression.vab.gateway;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.testsuite.support.vab.stub.IModelProviderStub;
import org.eclipse.basyx.vab.core.IConnectorProvider;
import org.eclipse.basyx.vab.core.IModelProvider;
import org.junit.Test;

public class TestDelegatingModelProvider {

	private String address;
	private IModelProviderStub stub = new IModelProviderStub();

	@Test
	public void test() {
		String basyx = "basyx://12.34.56.78:9090";
		String rest = "http://abc.de";

		DelegatingModelProvider provider = new DelegatingModelProvider(new IConnectorProvider() {

			@Override
			public IModelProvider getConnector(String addr) {
				address = addr;
				return stub;
			}
		});

		provider.getModelPropertyValue(basyx + "//" + rest);

		assertEquals(address, basyx);

		assertEquals(rest, stub.getPath());
	}
}
