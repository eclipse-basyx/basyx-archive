package org.eclipse.basyx.aas.backend.connector;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.vab.core.IConnectorProvider;
import org.eclipse.basyx.vab.core.IModelProvider;

public abstract class ConnectorProvider implements IConnectorProvider {

	private Map<String, IModelProvider> providerMap = new HashMap<>();

	public IModelProvider getConnector(String addr) {
		if (!providerMap.containsKey(addr)) {
			providerMap.put(addr, createProvider(addr));
		}
		return providerMap.get(addr);
	}

	protected abstract IModelProvider createProvider(String addr);
}
