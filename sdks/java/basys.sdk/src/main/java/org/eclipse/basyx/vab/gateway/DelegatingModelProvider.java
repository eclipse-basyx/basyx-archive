package org.eclipse.basyx.vab.gateway;

import org.eclipse.basyx.vab.factory.java.ModelProxyFactory;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.api.IConnectorProvider;

/**
 * IModelProvider that delegates all calls to a Connector to enable gateway
 * functionality
 * 
 * @author schnicke
 *
 */
public class DelegatingModelProvider implements IModelProvider {

	// Provider that provides the connectors
	private ModelProxyFactory proxyFactory;

	public DelegatingModelProvider(IConnectorProvider connectorProvider) {
		super();
		this.proxyFactory = new ModelProxyFactory(connectorProvider);
	}

	@Override
	public Object getModelPropertyValue(String path) throws Exception {
		return getProvider(path).getModelPropertyValue("");
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {
		getProvider(path).setModelPropertyValue("", newValue);
	}

	@Override
	public void createValue(String path, Object newEntity) throws Exception {
		getProvider(path).createValue("", newEntity);
	}

	@Override
	public void deleteValue(String path) throws Exception {
		getProvider(path).deleteValue("");
	}

	@Override
	public void deleteValue(String path, Object obj) throws Exception {
		getProvider(path).deleteValue("", obj);
	}

	@Override
	public Object invokeOperation(String path, Object... parameter) throws Exception {
		return getProvider(path).invokeOperation("", parameter);
	}

	/**
	 * Returns the appropriate connector based on address
	 * 
	 * @param path
	 * @return
	 */
	private IModelProvider getProvider(String path) {
		return proxyFactory.createProxy(path);
	}
}
