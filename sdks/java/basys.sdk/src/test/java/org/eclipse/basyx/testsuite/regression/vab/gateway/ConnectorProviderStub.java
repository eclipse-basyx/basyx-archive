package org.eclipse.basyx.testsuite.regression.vab.gateway;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.api.IConnectorProvider;

/**
 * A simple ConnectorProvider stub returning connectors based on a String
 * mapping
 * 
 * @author schnicke
 *
 */
public class ConnectorProviderStub implements IConnectorProvider {
	private Map<String, IModelProvider> providerMap = new HashMap<>();

	public void addMapping(String addr, IModelProvider provider) {
		providerMap.put(addr, provider);
	}

	@Override
	public IModelProvider getConnector(String addr) {
		return providerMap.get(addr);
	}
}
