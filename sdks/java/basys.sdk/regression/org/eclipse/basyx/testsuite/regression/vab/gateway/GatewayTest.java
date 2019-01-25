package org.eclipse.basyx.testsuite.regression.vab.gateway;

import org.eclipse.basyx.vab.core.IConnectorProvider;
import org.eclipse.basyx.vab.core.IModelProvider;

public class GatewayTest {
	public static void main(String[] args) throws Exception {
		PrefixRemovingModelProvider provider = new PrefixRemovingModelProvider(new DelegatingModelProvider(new IConnectorProvider() {

			@Override
			public IModelProvider getConnector(String addr) {
				System.out.println("Address: " + addr);
				return new IModelProvider() {

					@Override
					public void setModelPropertyValue(String path, Object newValue) throws Exception {
						// TODO Auto-generated method stub

					}

					@Override
					public Object invokeOperation(String path, Object[] parameter) throws Exception {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public Object getModelPropertyValue(String path) {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public void deleteValue(String path, Object obj) throws Exception {
						// TODO Auto-generated method stub

					}

					@Override
					public void deleteValue(String path) throws Exception {
						// TODO Auto-generated method stub

					}

					@Override
					public void createValue(String path, Object newEntity) throws Exception {
						System.out.println("Create: " + path);
					}
				};
			}
		}), "tcp://12.10.22.6:1010");

		provider.createValue("tcp://12.10.22.6:1010//http://go.de//path://elems/", "");
	}
}
