package org.eclipse.basyx.sandbox.components.sqlprovider.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;

import org.eclipse.basyx.aas.restapi.VABMultiSubmodelProvider;
import org.eclipse.basyx.components.provider.BaseConfiguredProvider;
import org.eclipse.basyx.sandbox.components.sqlprovider.SQLPreconfiguredSubModelProvider;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;

/**
 * Servlet interface for SQL sub model provider
 * 
 * @author kuhn
 *
 */
public class SQLSubModelProviderServlet extends VABHTTPInterface<VABMultiSubmodelProvider> {

	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Sub model ID
	 */
	protected String submodelID = null;

	/**
	 * Configuration properties
	 */
	protected Properties cfgProperties = null;

	/**
	 * Constructor
	 */
	public SQLSubModelProviderServlet() {
		// Invoke base constructor
		super(new VABMultiSubmodelProvider());
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
			this.submodelID = cfgProperties.getProperty(BaseConfiguredProvider.buildBasyxCfgName(BaseConfiguredProvider.SUBMODELID));

		} catch (IOException e) {
			// Output exception
			e.printStackTrace();
		}

		// Instantiate and add sub model provider
		SQLPreconfiguredSubModelProvider sqlSMProvider = new SQLPreconfiguredSubModelProvider(cfgProperties);
		// - Add sub model provider
		this.getModelProvider().addSubmodel(submodelID, sqlSMProvider);
	}
}
