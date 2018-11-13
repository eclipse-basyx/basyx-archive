package org.eclipse.basyx.testsuite.support.backend.http.tools.stubs.servlets;

import org.eclipse.basyx.aas.impl.provider.JavaObjectVABMapper;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.aas.StubRestAAS;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel.StubRestAASFrozenSM;
import org.eclipse.basyx.vab.backend.server.http._HTTPProvider;



/**
 * Servlet interface for AAS
 * 
 * @author pschorn
 *
 */
public class StubRestAASServlet extends _HTTPProvider<JavaObjectVABMapper> {

	
	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor
	 */
	public StubRestAASServlet() {
		// Invoke base constructor
		super(new JavaObjectVABMapper());
		
		// Register AAS
		this.getBackendReference().addScopedModel(new StubRestAAS());
		
		// Register provided models and AAS
		this.getBackendReference().addScopedModel(new StubRestAASFrozenSM(),   "RestAAS");
		
	}
}
