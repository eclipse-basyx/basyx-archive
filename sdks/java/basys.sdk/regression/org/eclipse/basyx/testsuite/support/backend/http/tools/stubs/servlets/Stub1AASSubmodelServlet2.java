package org.eclipse.basyx.testsuite.support.backend.http.tools.stubs.servlets;

import org.eclipse.basyx.aas.impl.provider.JavaObjectVABMapper;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel.Stub2Submodel;
import org.eclipse.basyx.vab.backend.server.http._HTTPProvider;




/**
 * Servlet interface for stubbed standalone submodel
 * 
 * @author kuhn, pschorn
 *
 */
public class Stub1AASSubmodelServlet2 extends _HTTPProvider<JavaObjectVABMapper> {

	
	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor
	 */
	public Stub1AASSubmodelServlet2() {
		// Invoke base constructor
		super(new JavaObjectVABMapper());

		// Register standalone submodel
		this.getBackendReference().addScopedModel(new Stub2Submodel());
	}
}
