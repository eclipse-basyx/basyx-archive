package org.eclipse.basyx.testsuite.support.backend.http.tools.stubs.servlets;

import org.eclipse.basyx.aas.backend.modelprovider.http.HTTPProvider;
import org.eclipse.basyx.aas.impl.provider.JavaObjectProvider;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel.Stub2Submodel;




/**
 * Servlet interface for stubbed standalone submodel
 * 
 * @author kuhn, pschorn
 *
 */
public class Stub1AASSubmodelServlet2 extends HTTPProvider<JavaObjectProvider> {

	
	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor
	 */
	public Stub1AASSubmodelServlet2() {
		// Invoke base constructor
		super(new JavaObjectProvider());

		// Register standalone submodel
		this.getBackendReference().addModel(new Stub2Submodel());
	}
}
