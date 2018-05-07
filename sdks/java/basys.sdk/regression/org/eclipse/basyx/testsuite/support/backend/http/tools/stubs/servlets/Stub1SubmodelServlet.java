package org.eclipse.basyx.testsuite.support.backend.http.tools.stubs.servlets;

import org.eclipse.basyx.aas.backend.modelprovider.http.HTTPProvider;
import org.eclipse.basyx.aas.impl.provider.JavaObjectProvider;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel.Stub1Submodel;




/**
 * Servlet interface for stubbed sub model
 * 
 * @author kuhn
 *
 */
public class Stub1SubmodelServlet extends HTTPProvider<JavaObjectProvider> {

	
	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor
	 */
	public Stub1SubmodelServlet() {
		// Invoke base constructor
		super(new JavaObjectProvider());

		// Register provided models and AAS
		this.getBackendReference().addModel(new Stub1Submodel(), "Stub1AAS");
	}
}
