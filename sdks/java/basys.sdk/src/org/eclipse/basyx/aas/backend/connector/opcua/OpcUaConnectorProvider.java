package org.eclipse.basyx.aas.backend.connector.opcua;

import org.eclipse.basyx.aas.backend.connector.ConnectorProvider;
import org.eclipse.basyx.vab.core.IModelProvider;

/**
 * OPC UA connector provider class
 * 
 * @author kdorofeev
 *
 */
public class OpcUaConnectorProvider extends ConnectorProvider {

	/**
	 * returns HTTPConnetor wrapped with ConnectedHashmapProvider that handles
	 * message header information
	 */
	@Override
	protected IModelProvider createProvider(String addr) {

		return new OpcUaConnector(addr);
	}

}
