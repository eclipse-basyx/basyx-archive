package org.eclipse.basyx.aas.backend.connector.http;

import org.eclipse.basyx.aas.api.services.IModelProvider;
import org.eclipse.basyx.aas.backend.connector.ConnectorProvider;

public class HTTPConnectorProvider extends ConnectorProvider {

	@Override
	protected IModelProvider createProvider(String addr) {
		return new HTTPConnector(addr);
	}

}
