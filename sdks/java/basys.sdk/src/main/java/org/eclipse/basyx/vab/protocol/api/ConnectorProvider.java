package org.eclipse.basyx.vab.protocol.api;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

/**
 * ConnectorProvider that caches connectors for addresses to save resources
 * 
 * @author schnicke
 *
 */
public abstract class ConnectorProvider implements IConnectorProvider {

	private Map<String, IModelProvider> providerMap = new HashMap<>();

	@Override
	public IModelProvider getConnector(String addr) {
		if (!providerMap.containsKey(addr)) {
			providerMap.put(addr, createProvider(addr));
		}
		return providerMap.get(addr);
	}

	/**
	 * Creates a new provider for the given address
	 * 
	 * @param addr
	 * @return
	 */
	protected abstract IModelProvider createProvider(String addr);
}
