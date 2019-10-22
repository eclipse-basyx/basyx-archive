package org.eclipse.basyx.vab.factory.java;

import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.api.IConnectorProvider;

/**
 * A factory for creating model providers out of addresses with multiple endpoints included.
 * 
 *  @author espen
 *
 */
public class ModelProxyFactory {
	private IConnectorProvider connectorProvider;

	public ModelProxyFactory(IConnectorProvider connectorProvider) {
		this.connectorProvider = connectorProvider;
	}
	
	/**
	 * Returns an element proxy for a path that can contain multiple endpoints
	 * 
	 * @param path A path containing one or more endpoints.
	 * @return A proxy that directly connects to the element referenced by the given path.
	 */
	public VABElementProxy createProxy(String path) {
		// Create a model provider for the first endpoint
		String addressEntry = VABPathTools.getFirstEndpoint(path);
		IModelProvider provider = connectorProvider.getConnector(addressEntry);
		
		// Return a proxy for the whole path using the connector to the first endpoint
		String subPath = VABPathTools.removeFirstEndpoint(path);
		return new VABElementProxy(subPath, provider);
	}
}
