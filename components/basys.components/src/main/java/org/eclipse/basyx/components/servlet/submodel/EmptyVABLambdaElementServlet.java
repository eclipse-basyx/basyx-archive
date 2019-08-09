package org.eclipse.basyx.components.servlet.submodel;

import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.vab.backend.server.http.VABHTTPInterface;

/**
 * Empty VAB provider servlet. It realizes a VAB lambda provider on its endpoint.
 * 
 * @author kuhn
 *
 */
public class EmptyVABLambdaElementServlet extends VABHTTPInterface<VirtualPathModelProvider> {

	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public EmptyVABLambdaElementServlet() {
		// Invoke base constructor, instantiate a VAB object
		// - This object initially is empty, it is therefore described by an empty map
		super(new VirtualPathModelProvider());
	}
}
