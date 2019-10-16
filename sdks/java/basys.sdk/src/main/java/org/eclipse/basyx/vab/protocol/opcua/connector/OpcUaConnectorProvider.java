package org.eclipse.basyx.vab.protocol.opcua.connector;

import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.api.ConnectorProvider;

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
