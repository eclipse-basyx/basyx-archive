package org.eclipse.basyx.components.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;

import org.eclipse.basyx.aas.backend.provider.VABMultiSubmodelProvider;
import org.eclipse.basyx.components.xmlxqueryprovider.XMLXQuerySubModelProvider;
import org.eclipse.basyx.vab.backend.server.http.VABHTTPInterface;

/**
 * Servlet interface for XML XQuery sub model provider
 * 
 * @author kuhn
 *
 */
public class XMLXQueryServlet extends VABHTTPInterface<VABMultiSubmodelProvider> {

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
	public XMLXQueryServlet() {
		// Invoke base constructor
		super(new VABMultiSubmodelProvider());
	}

	/**
	 * Adds init parameter to servlet
	 */
	@Override
	public String getInitParameter(String name) {

		if (name.equals("config"))
			return "/WebContent/WEB-INF/config/xmlqueryprovider/xmlqueryprovider.properties";

		return null;
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
			this.submodelID = cfgProperties.getProperty("basyx.submodelID");

		} catch (IOException e) {
			// Output exception
			e.printStackTrace();
		}

		// Add working path to configuration
		cfgProperties.put("workingDir", getServletContext().getRealPath("."));

		// Instantiate and add sub model provider
		XMLXQuerySubModelProvider xmlxqSMProvider = new XMLXQuerySubModelProvider(cfgProperties);
		// - Add sub model provider
		this.getModelProvider().addSubmodel(submodelID, xmlxqSMProvider);
	}
}
