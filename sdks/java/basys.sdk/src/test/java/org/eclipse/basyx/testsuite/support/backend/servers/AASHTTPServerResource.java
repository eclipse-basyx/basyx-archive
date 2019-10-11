package org.eclipse.basyx.testsuite.support.backend.servers;

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
    protected void before() {
    	server = new AASHTTPServer(context);
		server.start();
    }

	/**
	 * Shutdown the created server after a test case
	 */
    protected void after() {
		server.shutdown();
    }
}