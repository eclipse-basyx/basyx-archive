package org.eclipse.basyx.examples.contexts;

import org.eclipse.basyx.components.servlets.RawCFGSubModelProviderServlet;
import org.eclipse.basyx.components.servlets.SQLDirectoryServlet;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;

/**
 * BaSyx context that contains an Industrie 4.0 Servlet infrastructure
 * - One in-memory AAS Server that receives and hosts asset administration shells and sub models
 * - One SQL directory that manages AAS and sub model registrations
 * 
 * @author kuhn
 *
 */
public class BaSyxExamplesContext_1MemoryAASServer_1SQLDirectory extends BaSyxContext {

	
	/**
	 * Version of serialized instance
	 */
	private static final long serialVersionUID = 1L;


	
	/**
	 * Constructor
	 */
	public BaSyxExamplesContext_1MemoryAASServer_1SQLDirectory() {
		// Invoke base constructor to set up Tomcat server in basys.components context
		super("/basys.examples", "");
		
		// Define Servlet infrastucture
		addServletMapping("/Components/Directory/SQL/*",       new SQLDirectoryServlet().withParameter("config", "/WebContent/WEB-INF/config/directory/sqldirectory/directory.properties"));
		addServletMapping("/Components/BaSys/1.0/aasServer/*", new RawCFGSubModelProviderServlet().withParameter("config", "/WebContent/WEB-INF/config/aasServer/aasServer.properties"));
	}
}

