package org.eclipse.basyx.testsuite.regression.vab.protocol.http;

import org.eclipse.basyx.vab.protocol.http.server.AASHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.junit.rules.ExternalResource;

/**
 * This class initializes Tomcat server and required servlets for all HTTP test classes in this project.
 * 
 * @author espen
 *
 */
public class AASHTTPServerResource extends ExternalResource {
	private AASHTTPServer server;
	private BaSyxContext context;

	/**
	 * Constructor taking the context of the requested server resource
	 */
	public AASHTTPServerResource(BaSyxContext context) {
		this.context = context;
    }

	/**
	 * Create a new AASHTTPServer before a test case runs
	 */
	@Override
    protected void before() {
    	server = new AASHTTPServer(context);
		server.start();
    }

	/**
	 * Shutdown the created server after a test case
	 */
	@Override
    protected void after() {
		server.shutdown();
    }
}