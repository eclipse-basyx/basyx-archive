package org.eclipse.basyx.testsuite.support.backend.http.tools.stubs.servlets;

import org.eclipse.basyx.aas.backend.modelprovider.http.GenericHandlerSubmodelHTTPProvider;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel.Stub3Submodel;




/**
 * Servlet interface for stubbed sub model
 * 
 * @author kuhn
 *
 */
public class Stub3AASSubmodelServlet extends GenericHandlerSubmodelHTTPProvider {

	
	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor
	 */
	public Stub3AASSubmodelServlet() {
		// Invoke base constructor
		super();

		// Instantiate sub model and dummy AAS
		Stub3Submodel stub3SM = new Stub3Submodel();
		
		// Register sub model handlers
		this.getBackendReference().addHandler(stub3SM.getAASHandler());
		this.getBackendReference().addHandler(stub3SM.getSubModelHandler());
		
		// Register provided sub models and AAS
		this.getBackendReference().addModel(stub3SM,             "Stub3SM");
		this.getBackendReference().addModel(stub3SM.getParent(), "Stub3AAS");
	}
}

