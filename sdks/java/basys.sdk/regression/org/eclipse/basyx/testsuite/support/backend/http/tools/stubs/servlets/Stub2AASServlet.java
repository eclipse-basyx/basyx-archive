package org.eclipse.basyx.testsuite.support.backend.http.tools.stubs.servlets;

import org.eclipse.basyx.aas.impl.provider.JavaObjectVABMapper;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.aas.Stub2AAS;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel.MainSMSubmodel;
import org.eclipse.basyx.vab.backend.server.http._HTTPProvider;




/**
 * Servlet interface for AAS
 * 
 * @author kuhn
 *
 */
public class Stub2AASServlet extends _HTTPProvider<JavaObjectVABMapper> {

	
	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor
	 */
	public Stub2AASServlet() {
		// Invoke base constructor
		super(new JavaObjectVABMapper());
		
		// Register provided models and AAS
		this.getBackendReference().addScopedModel(new Stub2AAS());
		
		this.getBackendReference().addScopedModel(new MainSMSubmodel(), "Stub2AAS");
	}
}
