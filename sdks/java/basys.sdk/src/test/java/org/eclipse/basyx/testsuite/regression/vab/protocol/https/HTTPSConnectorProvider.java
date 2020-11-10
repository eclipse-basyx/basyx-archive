package org.eclipse.basyx.testsuite.regression.vab.protocol.https;

import org.eclipse.basyx.vab.coder.json.connector.JSONConnector;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.api.ConnectorProvider;

/**
 * An HTTPS Connector provider class
 * which uses a HTTPSConnector in domain having self signed certificate
 * @author haque
 *
 */
public class HTTPSConnectorProvider extends ConnectorProvider {

	/**
	 * returns HTTPSConnetor wrapped with ConnectedHashmapProvider that handles
	 * message header information
	 */
	@Override
	protected IModelProvider createProvider(String addr) {
		return new JSONConnector(new HTTPSConnector(addr));
	}
}
