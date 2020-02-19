package org.eclipse.basyx.components.configuration;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a BaSyx http servlet configuration for a BaSyxContext,
 * that can be loaded from a properties file.
 * 
 * @author espen
 *
 */
public class BaSyxContextConfiguration extends BaSyxConfiguration {
	
	/**
	 * Initiates a logger using the current class
	 */
	private static final Logger logger = LoggerFactory.getLogger(BaSyxContextConfiguration.class);
	
	// Default BaSyx Context configuration
	public static final String DEFAULT_CONTEXTPATH = "/basys.sdk";
	public static final String DEFAULT_DOCBASE = System.getProperty("java.io.tmpdir");
	public static final String DEFAULT_HOSTNAME = "localhost";
	public static final int DEFAULT_PORT = 4000;

	private static final String CONTEXTPATH = "contextPath";
	private static final String DOCBASE = "contextDocPath";
	private static final String HOSTNAME = "contextHostname";
	private static final String PORT = "contextPort";

	// The default path for the context properties file
	public static final String DEFAULT_CONFIG_PATH = "context.properties";

	public static Map<String, String> getDefaultProperties() {
		Map<String, String> defaultProps = new HashMap<>();
		defaultProps.put(CONTEXTPATH, DEFAULT_CONTEXTPATH);
		logger.debug("DEFAULT " + DOCBASE + " - " + DEFAULT_DOCBASE);
		defaultProps.put(DOCBASE, DEFAULT_DOCBASE);
		defaultProps.put(HOSTNAME, DEFAULT_HOSTNAME);
		defaultProps.put(PORT, Integer.toString(DEFAULT_PORT));
		return defaultProps;
	}

	public BaSyxContextConfiguration() {
		super(getDefaultProperties());
	}

	public BaSyxContextConfiguration(Map<String, String> values) {
		super(values);
	}

	public String getContextPath() {
		return getProperty(CONTEXTPATH);
	}

	public String getDocBasePath() {
		logger.debug("DEFAULT " + DOCBASE + " -- " + getProperty(DOCBASE));
		return getProperty(DOCBASE);
	}

	public String getHostname() {
		return getProperty(HOSTNAME);
	}

	public int getPort() {
		return Integer.parseInt(getProperty(PORT));
	}
}
