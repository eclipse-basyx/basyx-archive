package org.eclipse.basyx.aas.backend.connector.http;

import org.eclipse.basyx.aas.backend.connector.ConnectorProvider;
import org.eclipse.basyx.aas.backend.connector.JSONConnector;
import org.eclipse.basyx.vab.core.IModelProvider;

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
