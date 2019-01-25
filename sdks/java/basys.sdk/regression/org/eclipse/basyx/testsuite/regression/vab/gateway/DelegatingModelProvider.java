package org.eclipse.basyx.testsuite.regression.vab.gateway;

import org.eclipse.basyx.vab.core.IConnectorProvider;
import org.eclipse.basyx.vab.core.IModelProvider;

public class DelegatingModelProvider implements IModelProvider {

	private IConnectorProvider connectorProvider;

	public DelegatingModelProvider(IConnectorProvider connectorProvider) {
		super();
		this.connectorProvider = connectorProvider;
	}

	@Override
	public Object getModelPropertyValue(String path) {
		return getProvider(path).getModelPropertyValue(getRest(path));
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {
		getProvider(path).setModelPropertyValue(getRest(path), newValue);
	}

	@Override
	public void createValue(String path, Object newEntity) throws Exception {
		getProvider(path).createValue(getRest(path), newEntity);
	}

	@Override
	public void deleteValue(String path) throws Exception {
		getProvider(path).deleteValue(getRest(path));
	}

	@Override
	public void deleteValue(String path, Object obj) throws Exception {
		getProvider(path).deleteValue(getRest(path), obj);
	}

	@Override
	public Object invokeOperation(String path, Object[] parameter) throws Exception {
		return getProvider(path).invokeOperation(getRest(path), parameter);
	}

	private IModelProvider getProvider(String path) {
		return connectorProvider.getConnector(getAddress(path));
	}

	private String getAddress(String path) {
		String[] splitted = path.split("//");
		return splitted[0] + "//" + splitted[1];
	}

	private String getRest(String path) {
		return path.replaceFirst(getAddress(path) + "//", "");
	}

}
