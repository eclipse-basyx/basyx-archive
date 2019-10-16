package org.eclipse.basyx.components.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;

import org.eclipse.basyx.aas.restapi.VABMultiSubmodelProvider;
import org.eclipse.basyx.components.cfgprovider.RawCFGSubModelProvider;
import org.eclipse.basyx.components.provider.BaseConfiguredProvider;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;

/**
 * Servlet interface for configuration file sub model provider
 * 
 * @author kuhn
 *
 */
public class RawCFGSubModelProviderServlet extends VABHTTPInterface<VABMultiSubmodelProvider> {

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
	 * Constructor
	 */
	public RawCFGSubModelProviderServlet() {
		// Invoke base constructor
		super(new VABMultiSubmodelProvider());

		// Change factory instance
		// this.getProviderBackend().getSerializerReference().setFactory(newFactoryInstance);
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
			this.submodelID = properties.getProperty(BaseConfiguredProvider.buildBasyxCfgName(BaseConfiguredProvider.SUBMODELID));
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

		System.out.println("1:" + submodelID);

		// Create sub model provider
		RawCFGSubModelProvider submodelProvider = new RawCFGSubModelProvider(properties);
		// - Add sub model provider
		this.getModelProvider().addSubmodel(submodelID, submodelProvider);

		System.out.println("CFG file loaded");
	}
}
