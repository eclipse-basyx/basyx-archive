package org.eclipse.basyx.testsuite.support.backend.servers.context;

import org.eclipse.basyx.testsuite.support.backend.http.tools.stubs.servlets.StubAASServlet;
import org.eclipse.basyx.testsuite.support.backend.servers.BaSyxContext;
import org.eclipse.basyx.testsuite.support.vab.stub.servlet.SimpleVABElementServlet;

/**
 * BaSyx context that contains servlets with a simple VAB object and a simple AAS for regression testing of basys.sdk
 * package
 * 
 * @author espen
 *
 */
public class SdkRegressionContext extends BaSyxContext {
	private static final long serialVersionUID = 4670203024510994828L;

	public SdkRegressionContext() {
		super("/basys.sdk", System.getProperty("java.io.tmpdir"));
		
		addServletMapping("/Testsuite/SimpleVAB/*", new SimpleVABElementServlet());
		addServletMapping("/Testsuite/StubAAS/*", new StubAASServlet());
	}
}
