package org.eclipse.basyx.testsuite.support.backend.http.tools.stubs.servlets;

import org.eclipse.basyx.aas.backend.modelprovider.http.HTTPProvider;
import org.eclipse.basyx.aas.impl.provider.JavaObjectProvider;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.aas.Stub2AAS;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel.MainSMSubmodel;




/**
 * Servlet interface for AAS
 * 
 * @author kuhn
 *
 */
public class Stub2AASServlet extends HTTPProvider<JavaObjectProvider> {

	
	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor
	 */
	public Stub2AASServlet() {
		// Invoke base constructor
		super(new JavaObjectProvider());
		
		// Register provided models and AAS
		this.getBackendReference().addModel(new Stub2AAS());
		
		this.getBackendReference().addModel(new MainSMSubmodel(), "Stub2AAS");
	}
}
