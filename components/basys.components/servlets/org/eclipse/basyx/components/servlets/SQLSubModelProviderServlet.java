package org.eclipse.basyx.components.servlets;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.servlet.ServletException;

import org.eclipse.basyx.aas.backend.provider.VABMultiSubmodelProvider;
import org.eclipse.basyx.components.sqlprovider.SQLSubModelProvider;
import org.eclipse.basyx.vab.backend.server.http.VABHTTPInterface;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;




/**
 * Servlet interface for SQL sub model provider
 * 
 * @author kuhn
 *
 */
public class SQLSubModelProviderServlet extends VABHTTPInterface<VABMultiSubmodelProvider<VABHashmapProvider>> {

	
	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	

	/**
	 * Sub model ID
	 */
	protected String submodelID   = null;
	

	
	/**
	 * Configuration properties
	 */
	protected Properties cfgProperties = null;

	
	
	
	/**
	 * Constructor
	 */
	public SQLSubModelProviderServlet() {
		// Invoke base constructor
		super(new VABMultiSubmodelProvider<VABHashmapProvider>());
	}
	
	
	
	/**
	 * Initialize servlet
	 * 
	 * @throws ServletException 
	 */
	public void init() throws ServletException {
		// Call base implementation
		super.init();
		
		// Read configuration values
		String configFilePath = (String) getInitParameter("config");

		// Read property file
		try {
			// Open property file
			InputStream input = getServletContext().getResourceAsStream(configFilePath); 

			// Instantiate property structure
			cfgProperties = new Properties();
			cfgProperties.load(input);
			
			// Extract sub model provider properties
			this.submodelID   = cfgProperties.getProperty("basyx.submodelID");
			
		} catch (IOException e) {
			// Output exception
			e.printStackTrace();
		}
		
		// Instantiate and add sub model provider
		SQLSubModelProvider sqlSMProvider = new SQLSubModelProvider(cfgProperties);
		// - Add sub model provider
		this.getModelProvider().addSubmodel(submodelID, sqlSMProvider);
	}	
}

