package org.eclipse.basyx.testsuite.support.vab.stub.servlet;

import org.eclipse.basyx.testsuite.support.vab.stub.elements.SimpleVABElement;
import org.eclipse.basyx.vab.backend.server.http.VABHTTPInterface;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;

/**
 * Servlet interface for AAS
 * 
 * @author kuhn
 *
 */
public class SimpleVABElementServlet extends VABHTTPInterface<VABHashmapProvider> {

	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public SimpleVABElementServlet() {
		// Invoke base constructor, instantiate a VABElement
		super(new VABHashmapProvider(new SimpleVABElement()));
	}
}
