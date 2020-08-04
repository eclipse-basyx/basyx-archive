package org.eclipse.basyx.components.mongodbregistry;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.components.configuration.BaSyxConfiguration;

/**
 * Represents a BaSyx configuration for a MongoDB connection.
 * 
 * @author espen
 *
 */
public class BaSyxMongoDBConfiguration extends BaSyxConfiguration {
	// Default BaSyx SQL configuration
	private static final String DEFAULT_USER = "admin";
	private static final String DEFAULT_CONNECTIONURL = "mongodb://127.0.0.1:27017/";
	private static final String DEFAULT_DATABASE = "admin";
	private static final String DEFAULT_COLLECTION = "basyx";

	private static final String USER = "dbuser";
	private static final String DATABASE = "dbname";
	private static final String CONNECTIONURL = "dbconnectionstring";
	private static final String COLLECTION = "dbcollection";

	// The default path for the context properties file
	public static final String DEFAULT_CONFIG_PATH = "mongodb.properties";

	public static Map<String, String> getDefaultProperties() {
		Map<String, String> defaultProps = new HashMap<>();
		defaultProps.put(USER, DEFAULT_USER);
		defaultProps.put(CONNECTIONURL, DEFAULT_CONNECTIONURL);
		defaultProps.put(DATABASE, DEFAULT_DATABASE);
		defaultProps.put(COLLECTION, DEFAULT_COLLECTION);

		return defaultProps;
	}

	public BaSyxMongoDBConfiguration(Map<String, String> values) {
		super(values);
	}

	public BaSyxMongoDBConfiguration() {
		super(getDefaultProperties());
	}

	public String getUser() {
		return getProperty(USER);
	}

	public String getDatabase() {
		return getProperty(DATABASE);
	}

	public String getConnectionUrl() {
		return getProperty(CONNECTIONURL);
	}

	public String getCollection() {
		return getProperty(COLLECTION);
	}
}
