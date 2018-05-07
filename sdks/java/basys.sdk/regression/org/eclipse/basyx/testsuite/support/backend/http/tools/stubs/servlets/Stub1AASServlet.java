package org.eclipse.basyx.testsuite.support.backend.http.tools.stubs.servlets;

import org.eclipse.basyx.aas.backend.modelprovider.http.HTTPProvider;
import org.eclipse.basyx.aas.impl.provider.JavaObjectProvider;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.aas.Stub1AAS;




/**
 * Servlet interface for AAS
 * 
 * @author kuhn
 *
 */
public class Stub1AASServlet extends HTTPProvider<JavaObjectProvider> {

	
	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor
	 */
	public Stub1AASServlet() {
		// Invoke base constructor
		super(new JavaObjectProvider());
		
		// Register provided models and AAS
		this.getBackendReference().addModel(new Stub1AAS());
	}
}
