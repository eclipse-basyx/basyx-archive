package org.eclipse.basyx.vab.protocol.http.connector;

import org.eclipse.basyx.vab.coder.json.connector.JSONConnector;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.api.ConnectorProvider;

public class HTTPConnectorProvider extends ConnectorProvider {

	/**
	 * returns HTTPConnetor wrapped with ConnectedHashmapProvider that handles
	 * message header information
	 */
	@Override
	protected IModelProvider createProvider(String addr) {

		return new JSONConnector(new HTTPConnector(addr));
	}

}
