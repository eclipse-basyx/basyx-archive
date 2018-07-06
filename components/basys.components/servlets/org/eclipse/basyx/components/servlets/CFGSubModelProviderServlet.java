package org.eclipse.basyx.components.servlets;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;

import org.eclipse.basyx.aas.backend.modelprovider.http.GenericHandlerSubmodelHTTPProvider;
import org.eclipse.basyx.components.cfgprovider.CFGSubModelProvider;




/**
 * Servlet interface for configuration file sub model provider
 * 
 * @author kuhn
 *
 */
public class CFGSubModelProviderServlet extends GenericHandlerSubmodelHTTPProvider {

	
	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	

	/**
	 * Asset administration shell ID
	 */
	protected String aasID        = null;
	
	/**
	 * Sub model ID
	 */
	protected String submodelID   = null;
	
	/**
	 * Sub model type
	 */
	protected String submodelType = null;

	

	/**
	 * Configuration properties
	 */
	protected Properties properties = null;
	
	
	
	
	/**
	 * Constructor
	 */
	public CFGSubModelProviderServlet() {
		// Invoke base constructor
		super();
	}
	
	
	
	/**
	 * Load properties from file
	 */
	protected void loadProperties(String cfgFilePath) {
		// Read property file
		try {
			// Open property file
			InputStream input = getServletContext().getResourceAsStream(cfgFilePath); 

			// Instantiate property structure
			properties = new Properties();
			properties.load(input);
			
			// Extract AAS properties
			this.aasID        = properties.getProperty("basyx_aasID");
			this.submodelID   = properties.getProperty("basyx_submodelID");
			this.submodelType = properties.getProperty("basyx_submodelType");
			
			// Remove AAS properties
			properties.remove("basyx_aasID");
			properties.remove("basyx_submodelID");
			properties.remove("basyx_submodelType");
		} catch (IOException e) {
			// Output exception
			e.printStackTrace();
		}		
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
		// - Read property file
		loadProperties(configFilePath);
		
		// Create sub model provider
		CFGSubModelProvider submodelProvider = new CFGSubModelProvider(submodelID, submodelID, submodelType, aasID, aasID, properties);
		
		// Register sub model handlers
		this.getBackendReference().addHandler(submodelProvider.getAASHandler());
		this.getBackendReference().addHandler(submodelProvider.getSubModelHandler());


		
		System.out.println("CFG file loaded");
	}
}

