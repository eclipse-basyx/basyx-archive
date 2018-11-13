package org.eclipse.basyx.testsuite.support.gateways.common.gateways;

import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.aas.impl.provider._RESTHTTPClientProvider;
import org.eclipse.basyx.testsuite.support.gateways.common.directory.GatewayTestsuiteDirectoryLine2InternalIESE;
import org.eclipse.basyx.vab.backend.server.http._HTTPProvider;




/**
 * Servlet interface for AAS
 * 
 * @author kuhn
 *
 */
public class Gateway_Line2ToLine2Int extends _HTTPProvider<_RESTHTTPClientProvider> {

	
	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor
	 */
	public Gateway_Line2ToLine2Int() {
		// Invoke base constructor
		super(new _RESTHTTPClientProvider("line2int.line2.manufacturing.de", new GatewayTestsuiteDirectoryLine2InternalIESE(), new HTTPConnectorProvider()));
	}
}
