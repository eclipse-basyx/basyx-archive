package org.eclipse.basyx.components.configuration;

import java.util.HashMap;
import java.util.Map;

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
	private static final String DEFAULT_REGISTRY_COLLECTION = "basyxregistry";
	private static final String DEFAULT_AAS_COLLECTION = "basyxaas";
	private static final String DEFAULT_SUBMODEL_COLLECTION = "basyxsubmodel";

	private static final String USER = "dbuser";
	private static final String DATABASE = "dbname";
	private static final String CONNECTIONURL = "dbconnectionstring";
	private static final String REGISTRY_COLLECTION = "dbcollectionRegistry";
	private static final String AAS_COLLECTION = "dbcollectionAAS";
	private static final String SUBMODEL_COLLECTION = "dbcollectionSubmodels";

	// The default path for the context properties file
	public static final String DEFAULT_CONFIG_PATH = "mongodb.properties";

	public static Map<String, String> getDefaultProperties() {
		Map<String, String> defaultProps = new HashMap<>();
		defaultProps.put(USER, DEFAULT_USER);
		defaultProps.put(CONNECTIONURL, DEFAULT_CONNECTIONURL);
		defaultProps.put(DATABASE, DEFAULT_DATABASE);
		defaultProps.put(REGISTRY_COLLECTION, DEFAULT_REGISTRY_COLLECTION);
		defaultProps.put(AAS_COLLECTION, DEFAULT_AAS_COLLECTION);
		defaultProps.put(SUBMODEL_COLLECTION, DEFAULT_SUBMODEL_COLLECTION);

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

	public String getRegistryCollection() {
		return getProperty(REGISTRY_COLLECTION);
	}

	public String getAASCollection() {
		return getProperty(AAS_COLLECTION);
	}

	public String getSubmodelCollection() {
		return getProperty(SUBMODEL_COLLECTION);
	}
}
