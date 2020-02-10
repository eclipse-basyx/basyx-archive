package org.eclipse.basyx.components.servlet.submodel.cfg;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;

import org.eclipse.basyx.aas.restapi.VABMultiSubmodelProvider;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;

/**
 * Abstract super class for all config file using submodel provider servlets
 * 
 * @author schnicke
 *
 */
public abstract class AbstractCFGSubModelProviderServlet extends VABHTTPInterface<VABMultiSubmodelProvider> {
	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Store ID of the sub model provided by this provider
	 */
	protected String submodelID = null;

	/**
	 * Configuration properties
	 */
	protected Properties properties = null;


	/**
	 * Standard constructor creating a servlet containing a new
	 * VABMultiSubmodelProvider()
	 */
	public AbstractCFGSubModelProviderServlet() {
		super(new VABMultiSubmodelProvider());
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
			this.submodelID = properties.getProperty(getSubmodelId());
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
	@Override
	public void init() throws ServletException {
		// Call base implementation
		super.init();

		// Read configuration values
		String configFilePath = getInitParameter("config");
		// - Read property file
		loadProperties(configFilePath);

		System.out.println("1:" + submodelID);

		// Create sub model provider
		SubModelProvider submodelProvider = createProvider(properties);
		// - Add sub model provider
		this.getModelProvider().addSubmodel(submodelID, submodelProvider);

		System.out.println("CFG file loaded");
	}

	/**
	 * Retrieves the submodel id
	 * 
	 * @return
	 */
	protected abstract String getSubmodelId();

	/**
	 * Creates the appropriate provider based on the passed properties
	 * 
	 * @param properties
	 * @return
	 */
	protected abstract SubModelProvider createProvider(Properties properties);

}
