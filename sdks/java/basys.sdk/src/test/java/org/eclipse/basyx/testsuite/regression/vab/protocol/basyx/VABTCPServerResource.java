package org.eclipse.basyx.testsuite.regression.vab.protocol.basyx;

import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.basyx.server.AASTCPServer;
import org.junit.rules.ExternalResource;

/**
 * This class initializes a TCP Server and adds a provider to it. Note that this server can only provide one
 * model per port and new servers have to be started for each model.
 *
 * @author pschorn, espen
 *
 */
public class VABTCPServerResource extends ExternalResource {
	private IModelProvider provider;
	private AASTCPServer server;

	/**
	 * Constructor taking the provider of the requested server resource
	 */
	public VABTCPServerResource(IModelProvider provider) {
		this.provider = provider;
	}

	@Override
	protected void before() {
		server = new AASTCPServer(provider);
		server.start();
	}

	@Override
	protected void after() {
		server.stop();
	}
}