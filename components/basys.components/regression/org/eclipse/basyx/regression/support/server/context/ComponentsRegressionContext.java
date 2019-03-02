package org.eclipse.basyx.regression.support.server.context;

import org.eclipse.basyx.components.servlets.CFGSubModelProviderServlet;
import org.eclipse.basyx.components.servlets.RawCFGSubModelProviderServlet;
import org.eclipse.basyx.components.servlets.SQLDirectoryServlet;
import org.eclipse.basyx.components.servlets.SQLSubModelProviderServlet;
import org.eclipse.basyx.components.servlets.StaticCFGDirectoryServlet;
import org.eclipse.basyx.components.servlets.XMLXQueryServlet;
import org.eclipse.basyx.regression.support.server.BaSyxContext;

/**
 * BaSyx context that contains an Industrie 4.0 Servlet infrastructure for regression testing of basys.components package
 * 
 * @author kuhn
 *
 */
public class ComponentsRegressionContext extends BaSyxContext {

	
	/**
	 * Version of serialized instance
	 */
	private static final long serialVersionUID = 1L;


	
	/**
	 * Constructor
	 */
	public ComponentsRegressionContext() {
		// Invoke base constructor to set up Tomcat server in basys.components context
		super("/basys.components", "");
		
		// Define Servlet infrastucture
		put("/Testsuite/components/BaSys/1.0/provider/sqlsm/*",     new SQLSubModelProviderServlet());
		put("/Testsuite/components/BaSys/1.0/provider/cfgsm/*",     new CFGSubModelProviderServlet());
		put("/Testsuite/components/BaSys/1.0/provider/rawcfgsm/*",  new RawCFGSubModelProviderServlet());
		put("/Testsuite/Directory/CFGFile/*",                       new StaticCFGDirectoryServlet());
		put("/Testsuite/Directory/SQL/*",                           new SQLDirectoryServlet());
		put("/Testsuite/components/BaSys/1.0/provider/xmlxquery/*", new XMLXQueryServlet());
	}
}

