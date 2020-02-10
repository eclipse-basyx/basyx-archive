package org.eclipse.basyx.components.configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a BaSyx sql configuration for a sql connection.
 * 
 * @author espen
 *
 */
public class BaSyxSQLConfiguration extends BaSyxConfiguration {
	// Default BaSyx SQL configuration
	private static final String DEFAULT_USER = "postgres";
	private static final String DEFAULT_PASS = "admin";
	private static final String DEFAULT_PATH = "//localhost/basyx-directory?";
	private static final String DEFAULT_DRV = "org.postgresql.Driver";
	private static final String DEFAULT_PREFIX = "jdbc:postgresql:";

	private static final String USER = "dbuser";
	private static final String PASS = "dbpass";
	private static final String PATH = "dburl";
	private static final String DRV = "sqlDriver";
	private static final String PREFIX = "sqlPrefix";

	// The default path for the context properties file
	public static final String DEFAULT_CONFIG_PATH = "sql.properties";

	public static Map<String, String> getDefaultProperties() {
		Map<String, String> defaultProps = new HashMap<>();
		defaultProps.put(USER, DEFAULT_USER);
		defaultProps.put(PASS, DEFAULT_PASS);
		defaultProps.put(PATH, DEFAULT_PATH);
		defaultProps.put(DRV, DEFAULT_DRV);
		defaultProps.put(PREFIX, DEFAULT_PREFIX);

		return defaultProps;
	}

	public BaSyxSQLConfiguration(Map<String, String> values) {
		super(values);
	}

	public BaSyxSQLConfiguration() {
		super(getDefaultProperties());
	}

	public String getUser() {
		return getProperty(USER);
	}

	public String getPass() {
		return getProperty(PASS);
	}

	public String getPath() {
		return getProperty(PATH);
	}

	public String getDrv() {
		return getProperty(DRV);
	}

	public String getPrefix() {
		return getProperty(PREFIX);
	}
}
