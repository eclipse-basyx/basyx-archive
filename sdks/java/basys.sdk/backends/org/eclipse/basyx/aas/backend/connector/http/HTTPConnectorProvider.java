package org.eclipse.basyx.aas.backend.connector.http;

import org.eclipse.basyx.aas.backend.connector.ConnectorProvider;
import org.eclipse.basyx.vab.core.IModelProvider;

public class HTTPConnectorProvider extends ConnectorProvider {

	@Override
	protected IModelProvider createProvider(String addr) {
		return new HTTPConnector(addr);
	}

}
