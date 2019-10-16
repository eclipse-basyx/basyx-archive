package org.eclipse.basyx.vab.gateway;

import org.eclipse.basyx.vab.modelprovider.VABPathTools;
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
	private IConnectorProvider connectorProvider;

	public DelegatingModelProvider(IConnectorProvider connectorProvider) {
		super();
		this.connectorProvider = connectorProvider;
	}

	@Override
	public Object getModelPropertyValue(String path) throws Exception {
		return getProvider(path).getModelPropertyValue(VABPathTools.removeAddressEntry(path));
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {
		getProvider(path).setModelPropertyValue(VABPathTools.removeAddressEntry(path), newValue);
	}

	@Override
	public void createValue(String path, Object newEntity) throws Exception {
		getProvider(path).createValue(VABPathTools.removeAddressEntry(path), newEntity);
	}

	@Override
	public void deleteValue(String path) throws Exception {
		getProvider(path).deleteValue(VABPathTools.removeAddressEntry(path));
	}

	@Override
	public void deleteValue(String path, Object obj) throws Exception {
		getProvider(path).deleteValue(VABPathTools.removeAddressEntry(path), obj);
	}

	@Override
	public Object invokeOperation(String path, Object... parameter) throws Exception {
		return getProvider(path).invokeOperation(VABPathTools.removeAddressEntry(path), parameter);
	}

	/**
	 * Returns the appropriate connector based on address
	 * 
	 * @param path
	 * @return
	 */
	private IModelProvider getProvider(String path) {
		return connectorProvider.getConnector(VABPathTools.getAddressEntry(path));
	}
}
