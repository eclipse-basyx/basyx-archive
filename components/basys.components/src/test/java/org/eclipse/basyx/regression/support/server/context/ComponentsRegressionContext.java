package org.eclipse.basyx.regression.support.server.context;

import org.eclipse.basyx.components.servlets.CFGSubModelProviderServlet;
import org.eclipse.basyx.components.servlets.RawCFGSubModelProviderServlet;
import org.eclipse.basyx.components.servlets.SQLDirectoryServlet;
import org.eclipse.basyx.components.servlets.SQLSubModelProviderServlet;
import org.eclipse.basyx.components.servlets.StaticCFGDirectoryServlet;
import org.eclipse.basyx.components.servlets.XMLXQueryServlet;
import org.eclipse.basyx.regression.support.processengine.servlet.CoilcarAASServlet;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;



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
		
		// Define Servlet infrastructure
		addServletMapping("/Testsuite/components/BaSys/1.0/provider/sqlsm/*",     new SQLSubModelProviderServlet().withParameter("config", "/WebContent/WEB-INF/config/sqlprovider/sampledb.properties"));
		addServletMapping("/Testsuite/components/BaSys/1.0/provider/cfgsm/*",     new CFGSubModelProviderServlet().withParameter("config", "/WebContent/WEB-INF/config/cfgprovider/samplecfg.properties"));
		addServletMapping("/Testsuite/components/BaSys/1.0/provider/rawcfgsm/*",  new RawCFGSubModelProviderServlet().withParameter("config", "/WebContent/WEB-INF/config/rawcfgprovider/samplecfg.properties"));
		addServletMapping("/Testsuite/components/BaSys/1.0/provider/xmlxquery/*", new XMLXQueryServlet().withParameter("config", "/WebContent/WEB-INF/config/xmlqueryprovider/xmlqueryprovider.properties"));
		addServletMapping("/Testsuite/Directory/CFGFile/*",                       new StaticCFGDirectoryServlet().withParameter("config", "/WebContent/WEB-INF/config/directory/cfgdirectory/directory.properties"));
		addServletMapping("/Testsuite/Directory/SQL/*",                           new SQLDirectoryServlet().withParameter("config", "/WebContent/WEB-INF/config/directory/sqldirectory/directory.properties"));
		addServletMapping("/Testsuite/Processengine/coilcar/*",                   new CoilcarAASServlet());
	}
}

