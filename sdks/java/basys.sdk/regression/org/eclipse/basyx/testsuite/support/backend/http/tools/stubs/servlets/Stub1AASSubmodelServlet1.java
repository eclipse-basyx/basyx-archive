package org.eclipse.basyx.testsuite.support.backend.http.tools.stubs.servlets;

import org.eclipse.basyx.aas.impl.provider.JavaObjectVABMapper;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel.Stub1Submodel;
import org.eclipse.basyx.vab.backend.server.http.VABHTTPInterface;

/**
 * Servlet interface for stubbed sub model
 * 
 * @author kuhn, schnicke
 *
 */
public class Stub1AASSubmodelServlet1 extends VABHTTPInterface<JavaObjectVABMapper> {

	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public Stub1AASSubmodelServlet1() {
		// Invoke base constructor
		super(new JavaObjectVABMapper());

		// Register provided models and AAS
		this.getBackendReference().addScopedModel(new Stub1Submodel(), "Stub1AAS");
	}
}
