package org.eclipse.basyx.components.directory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;

import org.eclipse.basyx.aas.backend.http.tools.GSONTools;
import org.eclipse.basyx.aas.backend.http.tools.factory.DefaultTypeFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.descriptor.AASDescriptor;
import org.eclipse.basyx.components.sqlprovider.driver.SQLDriver;
import org.eclipse.basyx.vab.core.IModelProvider;

/**
 * Model Provider handling SQL Directory Services.
 * 
 * @author pschorn, kuhn
 *
 */
public class SQLDirectoryProvider implements IModelProvider {

	private static String registryPath = "/api/v1/registry";


	/**
	 * Configuration properties (raw input from file)
	 */
	protected Properties properties = null;

	/**
	 * Uplink server
	 */
	protected String uplink = null;

	/**
	 * Downlink servers
	 */
	protected Map<String, String> downlinks = new HashMap<>();

	/**
	 * Path to database
	 */
	protected String path = null;

	/**
	 * SQL user name
	 */
	protected String user = null;

	/**
	 * SQL password
	 */
	protected String pass = null;

	/**
	 * SQL query prefix
	 */
	protected String qryPfx = null;

	/**
	 * SQL driver class name
	 */
	protected String qDrvCls = null;

	/**
	 * SQL driver instance
	 */
	protected SQLDriver sqlDriver = null;
	
	/**
	 * Reference to serializer for string based sql storage
	 */
	protected GSONTools serializer = null;

	/**
	 * Receives the path of the configuration.properties file in it's constructor.
	 * 
	 * @param configFilePath
	 */
	public SQLDirectoryProvider(String configFilePath) {
		sqlDriver = initSQLDriver(configFilePath);

		// Create GSON serializer
		serializer = new GSONTools(new DefaultTypeFactory());
	}

	/**
	 * Initialize sqlDriver
	 * 
	 * @throws ServletException
	 */
	public SQLDriver initSQLDriver(String configFilePath) {
		// - Read property file
		loadProperties(configFilePath);

		// Create SQL driver instance
		SQLDriver sqlDriver = new SQLDriver(path, user, pass, qryPfx, qDrvCls);

		return sqlDriver;
	}

	/**
	 * Load a property
	 */
	protected String extractProperty(Properties prop, String key) {
		// Check if properties contain value
		if (!prop.containsKey(key))
			return null;

		// Extract and remove value
		String value = (String) prop.get(key);
		prop.remove(key);

		// Return value
		return value;
	}

	/**
	 * Extract property keys with prefix and suffix. Prefix and suffix is removed
	 * from key.
	 */
	protected Collection<String> getProperties(Properties prop, String prefix, String suffix) {
		// Store result
		HashSet<String> result = new HashSet<>();

		// Iterate keys
		for (String key : prop.stringPropertyNames()) {
			if (key.startsWith(prefix) && key.endsWith(suffix))
				result.add(key.substring(prefix.length(), key.length() - suffix.length()));
		}

		// Return result
		return result;
	}

	/**
	 * Extract downlink servers
	 */
	protected Map<String, String> extractDownlinks(Properties prop) {
		// Return value
		Map<String, String> result = new HashMap<>();

		// Downlink server names
		Collection<String> downlinkServerNames = getProperties(prop, "cfg.downlink.", ".pattern");

		// Remove downlink pattern and server URL
		for (String name : downlinkServerNames) {
			// Get downlink pattern and server URL
			result.put(prop.getProperty("cfg.downlink." + name + ".pattern"),
					prop.getProperty("cfg.downlink." + name + ".directory"));
			// Remove pattern and directory properties
			prop.remove("cfg.downlink." + name + ".pattern");
			prop.remove("cfg.downlink." + name + ".directory");
		}

		// Return downlink server mappings
		return result;
	}



	/**
	 * Load properties from file
	 */
	protected void loadProperties(String cfgFilePath) {

		System.out.println("CfgFilePath: " + cfgFilePath);

		// Read property file
		try {

			// Open property file
			InputStream input = new FileInputStream(cfgFilePath);

			// Instantiate property structure
			properties = new Properties();
			properties.load(input);

			// Process properties
			// - Uplink server
			uplink = extractProperty(properties, "cfg.uplink");
			// - Downlink servers
			downlinks = extractDownlinks(properties);

			// SQL parameter
			path = properties.getProperty("dburl");
			user = properties.getProperty("dbuser");
			pass = properties.getProperty("dbpass");
			qryPfx = properties.getProperty("sqlPrefix");
			qDrvCls = properties.getProperty("sqlDriver");

		} catch (IOException e) {
			// Output exception
			e.printStackTrace();
		}
	}



	/**
	 * Get AAS from SQL database (string based storage)
	 * 
	 * @param path
	 *            comes with a leading /
	 */
	@Override
	public Object getModelPropertyValue(String path) throws Exception {

		// Process get request
		// - Get all (local) AAS
		if (path.equals(registryPath)) {
			System.out.println("Getting all");

			// Query database
			ResultSet resultSet = sqlDriver.sqlQuery("SELECT * FROM directory.directory");

			// Write result
			StringBuilder result = new StringBuilder();

			// Create result
			try {
				// Get results
				while (resultSet.next())
					result.append(resultSet.getString("ElementRef"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			String restring = result.toString();
			System.out.println("Res: " + restring);

			return restring.equals("") ? null : restring;
		}

		// Get a specific AAS
		else if (path.startsWith(registryPath + "/")) {
			System.out.println("Getting:" + path.substring(new String(registryPath + "/").length()));

			// Run query
			ResultSet resultSet = sqlDriver.sqlQuery("SELECT * FROM directory.directory WHERE \"ElementID\" = '"
					+ path.substring(new String(registryPath + "/").length()) + "'");

			// Write result
			StringBuilder result = new StringBuilder();

			// Create result
			try {
				// Get results
				while (resultSet.next())
					result.append(resultSet.getString("ElementRef"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			String restring = result.toString();
			System.out.println("Res: " + restring);

			return restring.equals("") ? null : serializer.deserialize(restring);
		}

		else {
			return null;
		}
	}

	/**
	 * Stores a value in the database. Might have to serialize map type. <br/>
	 * TODO Think about different storage format than serialized.
	 */
	@Override
	public void setModelPropertyValue(String path, Object aasValue) throws Exception {
		// Check path
		System.out.println("Putting:" + path + ":" + aasValue);
		if (!path.startsWith(registryPath + "/")) {
			System.out.println("Exception");
			throw new RuntimeException("Wrong request path"); 
		}

		// Extract AAS ID
		String aasID = path.substring(new String(registryPath + "/").length());

		// Update AAS registry
		sqlDriver.sqlUpdate("UPDATE directory.directory SET \"ElementRef\" = '"+aasValue.toString()+"' WHERE \"ElementID\"='"+aasID+"';");

	}

	@Override
	public void createValue(String path, Object values) throws Exception {

		System.out.println("Creating" + path + ":" + values);

		// Make serialized copy to be stored with sql
		String aasValue = serializer.serialize(values);;

		if (values instanceof Map<?,?>) {
			@SuppressWarnings("unchecked")
			AASDescriptor aasDescriptor = new AASDescriptor((Map<String, Object>) values);

			// Extract AAS ID
			String aasID = aasDescriptor.getId();
	
			// Update AAS registry
			sqlDriver.sqlUpdate("INSERT INTO directory.directory (\"ElementRef\", \"ElementID\") VALUES ('"
					+ aasValue + "', '" + aasID + "');");
		} else {
			throw new RuntimeException("Create expected AASDescriptor");
		}
	}

	@Override
	public void deleteValue(String path) throws Exception {
		// Check path
		System.out.println("Deleting:" + path);
		if (!path.startsWith(registryPath + "/")) {
			System.out.println("Exception");
			throw new RuntimeException("Wrong request path");
		}

		// Extract AAS ID
		String aasID = path.substring(new String(registryPath + "/").length());

		// Delete element
		// - Delete element from table "directory"
		sqlDriver.sqlUpdate("DELETE FROM directory.directory WHERE \"ElementID\"='" + aasID + "';");

	}

	/**
	 * Registry does not support deleting specific elements
	 */
	@Override
	public void deleteValue(String path, Object obj) throws Exception {
		throw new RuntimeException("Not implemented");

	}

	/**
	 * Registry does not support invoke
	 */
	@Override
	public Object invokeOperation(String path, Object[] parameter) throws Exception {
		throw new RuntimeException("Not implemented");
	}

}
