package org.eclipse.basyx.components.servlets;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.servlet.ServletException;

import org.eclipse.basyx.aas.backend.modelprovider.http.GenericHandlerSubmodelHTTPProvider;
import org.eclipse.basyx.components.sqlprovider.SQLSubModelProvider;




/**
 * Servlet interface for SQL sub model provider
 * 
 * @author kuhn
 *
 */
public class SQLSubModelProviderServlet extends GenericHandlerSubmodelHTTPProvider {

	
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
	protected Properties cfgProperties = null;

	
	
	
	/**
	 * Constructor
	 */
	public SQLSubModelProviderServlet() {
		// Invoke base constructor
		super();
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
			
			// Extract AAS properties
			this.aasID        = cfgProperties.getProperty("basyx_aasID");
			this.submodelID   = cfgProperties.getProperty("basyx_submodelID");
			this.submodelType = cfgProperties.getProperty("basyx_submodelType");

		} catch (IOException e) {
			// Output exception
			e.printStackTrace();
		}

		// Instantiate sub model provider
		SQLSubModelProvider sqlSMProvider = new SQLSubModelProvider(submodelID, submodelID, submodelType, aasID, aasID, cfgProperties);

		// Register sub model handlers
		this.getBackendReference().addHandler(sqlSMProvider.getAASHandler());
		this.getBackendReference().addHandler(sqlSMProvider.getSubModelHandler());
		
		// Register provided sub models and AAS
		this.getBackendReference().addModel(sqlSMProvider,             submodelID);
		this.getBackendReference().addModel(sqlSMProvider.getParent(), aasID);
	}	
}

