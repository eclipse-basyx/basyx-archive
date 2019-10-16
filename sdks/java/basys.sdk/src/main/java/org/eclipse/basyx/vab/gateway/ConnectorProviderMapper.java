package org.eclipse.basyx.vab.gateway;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.api.IConnectorProvider;

/**
 * Maps an incoming address to an IConnectorProvider based on the protocol used
 * in the path<br/>
 * E.g. basyx://* can be mapped to the BasyxNative connector, http://* can be
 * mapped to the HTTP/REST connector
 * 
 * @author schnicke
 *
 */
public class ConnectorProviderMapper implements IConnectorProvider {

	private Map<String, IConnectorProvider> providerMap = new HashMap<>();

	/**
	 * 
	 * @param prefix
	 *            assumed without ending colon or slashes, e.g. <i>basyx</i> but not
	 *            <i>basyx://</i>
	 * @param provider
	 */
	public void addConnectorProvider(String prefix, IConnectorProvider provider) {
		providerMap.put(prefix, provider);
	}

	public void removeConnectorProvider(String prefix) {
		providerMap.remove(prefix);
	}

	@Override
	public IModelProvider getConnector(String addr) {
		return providerMap.get(getPrefix(addr)).getConnector(addr);
	}

	/**
	 * Returns the prefix, e.g. address <i>basyx://*</i> returns <i>basyx</i>
	 * 
	 * @param addr
	 * @return
	 */
	private String getPrefix(String addr) {
		String prefix = addr.split("//")[0];
		return prefix.replace(":", "");
	}

}
