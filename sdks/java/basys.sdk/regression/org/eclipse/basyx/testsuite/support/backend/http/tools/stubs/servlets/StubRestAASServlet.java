package org.eclipse.basyx.testsuite.support.backend.http.tools.stubs.servlets;

import org.eclipse.basyx.aas.backend.modelprovider.http.HTTPProvider;
import org.eclipse.basyx.aas.impl.provider.JavaObjectProvider;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.aas.StubRestAAS;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel.StubRestAASFrozenSM;



/**
 * Servlet interface for AAS
 * 
 * @author pschorn
 *
 */
public class StubRestAASServlet extends HTTPProvider<JavaObjectProvider> {

	
	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor
	 */
	public StubRestAASServlet() {
		// Invoke base constructor
		super(new JavaObjectProvider());
		
		// Register AAS
		this.getBackendReference().addModel(new StubRestAAS());
		
		// Register provided models and AAS
		this.getBackendReference().addModel(new StubRestAASFrozenSM(),   "RestAAS");
		
	}
}
