package org.eclipse.basyx.aas.backend.connector;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.services.IModelProvider;

public abstract class ConnectorProvider {

	private Map<String, IModelProvider> providerMap = new HashMap<>();
	
	public IModelProvider getProvider(String addr) {
		if(!providerMap.containsKey(addr)) {
			providerMap.put(addr, createProvider(addr));
		}
		return providerMap.get(addr);
	}
	
	protected abstract IModelProvider createProvider(String addr);
}
