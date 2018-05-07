package org.eclipse.basyx.testsuite.support.gateways.common.gateways;

import org.eclipse.basyx.aas.backend.modelprovider.http.HTTPProvider;
import org.eclipse.basyx.aas.impl.provider.RESTHTTPClientProvider;
import org.eclipse.basyx.testsuite.support.gateways.common.directory.GatewayTestsuiteDirectoryLine2IESE;




/**
 * Servlet interface for AAS
 * 
 * @author kuhn
 *
 */
public class Gateway_Line1ToLine2 extends HTTPProvider<RESTHTTPClientProvider> {

	
	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor
	 */
	public Gateway_Line1ToLine2() {
		// Invoke base constructor
		super(new RESTHTTPClientProvider("line2.manufacturing.de", new GatewayTestsuiteDirectoryLine2IESE()));
	}
}
