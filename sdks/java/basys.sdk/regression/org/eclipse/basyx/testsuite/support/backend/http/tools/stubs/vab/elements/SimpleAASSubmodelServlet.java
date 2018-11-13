package org.eclipse.basyx.testsuite.support.backend.http.tools.stubs.vab.elements;

import org.eclipse.basyx.aas.backend.modelprovider.VABMultiSubmodelProvider;
import org.eclipse.basyx.aas.backend.modelprovider.http.VABHTTPInterface;




/**
 * Servlet interface for AAS
 * 
 * @author kuhn
 *
 */
public class SimpleAASSubmodelServlet extends VABHTTPInterface<VABMultiSubmodelProvider> {

	
	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor
	 */
	public SimpleAASSubmodelServlet() {
		// Invoke base constructor, instantiate a VAB multiple sub model provider that implements
		// the sub model REST API
		super(new VABMultiSubmodelProvider());
		
		// Add simple VAB element
		this.getModelProvider().addProvider("SimpleAASSubmodel", new SimpleAASSubmodel());
	}
}
