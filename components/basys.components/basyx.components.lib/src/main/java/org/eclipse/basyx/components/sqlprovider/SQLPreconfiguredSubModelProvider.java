package org.eclipse.basyx.components.sqlprovider;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.eclipse.basyx.components.provider.BaseConfiguredProvider;
import org.eclipse.basyx.components.sqlprovider.driver.SQLDriver;
import org.eclipse.basyx.components.sqlprovider.query.DynamicSQLQuery;
import org.eclipse.basyx.components.sqlprovider.query.DynamicSQLRunner;
import org.eclipse.basyx.components.sqlprovider.query.DynamicSQLUpdate;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




/**
 * Asset administration shell sub model provider that connects to SQL database
 * 
 * @author kuhn
 *
 */
public class SQLPreconfiguredSubModelProvider extends BaseConfiguredProvider {
	
	/**
	 * Initiates a logger using the current class
	 */
	private static final Logger logger = LoggerFactory.getLogger(SQLPreconfiguredSubModelProvider.class);
	
	/**
	 * SQL database user name
	 */
	protected String sqlUser = null;

	/**
	 * SQL database password
	 */
	protected String sqlPass = null;
	
	/**
	 * SQL database path
	 */
	protected String sqlURL  = null;

	
	
	/**
	 * SQL database driver
	 */
	protected String sqlDriver = null;
	
	/**
	 * SQL database query prefix
	 */
	protected String sqlPrefix = null;
	
	
	
	/**
	 * SQL property connections
	 */
	protected Set<String> sqlPropertyConnections = new HashSet<>();
	

	/**
	 * SQL operation connections
	 */
	protected Set<String> sqlOperationConnections = new HashSet<>();

	
	
	/**
	 * Run queries to access properties via 'get' operation
	 */
	protected Map<String, DynamicSQLRunner> propertyGetQueries = new HashMap<>();

	
	/**
	 * Run SQL update to access properties via 'set' operation
	 */
	protected Map<String, DynamicSQLRunner> propertySetQueries = new HashMap<>();

	
	/**
	 * Run SQL update to access properties via 'create' operation
	 */
	protected Map<String, DynamicSQLRunner> propertyCreateQueries = new HashMap<>();


	/**
	 * Run SQL update to access properties via 'delete' operation
	 */
	protected Map<String, DynamicSQLRunner> propertyDeleteQueries = new HashMap<>();


	
	/**
	 * SQL operations
	 */
	protected Map<String, DynamicSQLRunner> operations = new HashMap<>();

	
	/**
	 * SQL operations that run as update operations. Not contained operations are query operations.
	 */
	protected Set<String> updateOperations = new HashSet<>();
	
	/**
	 * An SQL driver instance to connect to the database
	 */
	protected SQLDriver driver;

	
	public static final String DBUSER = "dbuser";
	public static final String DBPASS = "dbpass";
	public static final String DBURL = "dburl";
	public static final String DRIVER = "driver";
	public static final String PREFIX = "prefix";
	public static final String PROPERTIES = "properties";
	public static final String OPERATIONS = "operations";
	
	
	/**
	 * Constructor
	 */
	public SQLPreconfiguredSubModelProvider(Properties cfgValues) {
		// Call base constructor
		super(cfgValues);
		
		// Create sub model
		submodelData = createSubModel(cfgValues);

		// Load predefined elements from sub model
		
		try {
			setModelPropertyValue("", submodelData);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		// Extract SQL properties
		sqlUser = cfgValues.getProperty(buildSqlCfgName(DBUSER));
		sqlPass = cfgValues.getProperty(buildSqlCfgName(DBPASS));
		sqlURL  = cfgValues.getProperty(buildSqlCfgName(DBURL));

		// Extract SQL driver properties
		sqlDriver = cfgValues.getProperty(buildSqlCfgName(DRIVER));
		sqlPrefix = cfgValues.getProperty(buildSqlCfgName(PREFIX));
		
		// Create a SQL driver instance
		driver = new SQLDriver(sqlURL, sqlUser, sqlPass, sqlPrefix, sqlDriver);
		
		// Load and parse SQL property and operation connections
		sqlPropertyConnections.addAll(splitString(cfgValues.getProperty(buildSqlCfgName(PROPERTIES))));
		sqlOperationConnections.addAll(splitString(cfgValues.getProperty(buildSqlCfgName(OPERATIONS))));

		
		
		// Add properties
		for (String propertyName: sqlPropertyConnections) createSQLProperty(propertyName, cfgValues);
		
		/*
			// Try to parse parameter
			propertyGetQueries.put(propertyName, createSQLOperation(propertyName+".get", cfgValues));
			propertySetQueries.put(propertyName, createSQLOperation(propertyName+".set", cfgValues));
			propertyCreateQueries.put(propertyName, createSQLOperation(propertyName+".create", cfgValues));
			propertyDeleteQueries.put(propertyName, createSQLOperation(propertyName+".delete", cfgValues));
			
			
			Map<String, Object> mapAccessors = VABLambdaProviderHelper.createMap((Supplier<?>) () -> {
				return propertyMap_val;
			}, (Consumer<Map<String, Object>>) (map) -> {
				propertyMap_val = map;
			}, (BiConsumer<String, Object>) (key, value) -> {
				propertyMap_val.put(key, value);
			}, (Consumer<Object>) (o) -> {
				propertyMap_val.remove(o);
			});

		}*/
		
		
		// Add operations
		//for (String operationName: sqlOperationConnections) createSQLOperation(operationName, cfgValues); 
		/*{
			// Create operation
			operations.put(operationName, createSQLOperation(operationName, cfgValues));
			// Mark operation as update operation depending on operations/<operationName>_kind property value
			try {
				if (cfgValues.getProperty(operationName+"_kind").trim().equalsIgnoreCase("update")) {
					updateOperations.add(operationName);
				}
			} catch (Exception e) {}
		}*/
	}
	
	
	
	/**
	 * Create an SQL property
	 */
	protected void createSQLProperty(String name, Properties cfgValues) {
		// Create Map with lambdas that hold SQL operations
		Map<String, Object> value = new HashMap<>();
		
		// Get operation
		{
			// Get parameter
			String queryString    = cfgValues.getProperty(name+".get");
			String resultFilterOp = cfgValues.getProperty(name+".get.result");

			// Trim query string and resultFilterOp (remove '"' at beginning and end)
			queryString    = queryString.substring(1, queryString.length()-1);
			try {resultFilterOp = resultFilterOp.substring(1, resultFilterOp.length()-1);} catch (NullPointerException | StringIndexOutOfBoundsException e) {}

			// Create dynamic SQL query
			value.put(VABLambdaHandler.VALUE_GET_SUFFIX, new DynamicSQLQuery(driver, queryString, resultFilterOp));
		}
		
		// Set operation
		{
			// Get parameter
			String updateString   = cfgValues.getProperty(name+".set");

			// Trim query string and resultFilterOp (remove '"' at beginning and end)
			updateString    = updateString.substring(1, updateString.length()-1);

			// Create dynamic SQL query
			value.put(VABLambdaHandler.VALUE_SET_SUFFIX, new DynamicSQLUpdate(driver, updateString));
		}

		// Delete operation
		{
			// Get parameter
			String updateString   = cfgValues.getProperty(name+".delete");

			// Trim query string and resultFilterOp (remove '"' at beginning and end)
			updateString    = updateString.substring(1, updateString.length()-1);

			// Create dynamic SQL query
			value.put(VABLambdaHandler.VALUE_REMOVEKEY_SUFFIX, new DynamicSQLUpdate(driver, updateString));
			value.put(VABLambdaHandler.VALUE_REMOVEOBJ_SUFFIX, new DynamicSQLUpdate(driver, updateString));
		}
		
		// Create operation
		{
			// Get parameter
			String updateString   = cfgValues.getProperty(name+".create");

			// Trim query string and resultFilterOp (remove '"' at beginning and end)
			updateString    = updateString.substring(1, updateString.length()-1);

			// Create dynamic SQL query
			value.put(VABLambdaHandler.VALUE_INSERT_SUFFIX, new DynamicSQLUpdate(driver, updateString));
		}
		
		
		logger.debug("Putting SQL:"+name);
		// Add property as map of lambdas
		submodelData.getProperties().put(name, createSubmodelElement(name, value, cfgValues));
	}

	
	/**
	 * Create a dynamic SQL operation
	 */
	protected DynamicSQLRunner createSQLOperation(String propertyName, Properties cfgValues) {/*
		// Check parameter presence
		if (!cfgValues.containsKey(propertyName)) return null;
		
		// Get parameter count and parameter count
		int    parameterCount = Integer.parseInt(cfgValues.getProperty(propertyName+".parameter"));
		String queryString    = cfgValues.getProperty(propertyName);
		String resultFilterOp = cfgValues.getProperty(propertyName+".result");
		
		// Trim query string and resultFilterOp (remove '"' at beginning and end)
		queryString    = queryString.substring(1, queryString.length()-1);
		try {resultFilterOp = resultFilterOp.substring(1, resultFilterOp.length()-1);} catch (NullPointerException | StringIndexOutOfBoundsException e) {}
		
		// Create dynamic SQL query
		DynamicSQLRunner sqlQuery = new DynamicSQLRunner(sqlURL, sqlUser, sqlPass, sqlPrefix, sqlDriver, parameterCount, queryString, resultFilterOp);

		// Return created query
		return sqlQuery;*/
		
		return null;
	}
	
	
	/**
	 * Split a whitespace delimited string
	 */
	protected Collection<String> splitString(String input) {
		// Return value
		HashSet<String> result = new HashSet<>();
		
		// Split string into segments
		for (String inputStr: input.split(" ")) result.add(inputStr.trim());
		
		// Return result
		return result;
	}
	
	
	/**
	 * Create a key list for an SQL statement
	 *//*
	protected String sqlCreateKeys(Collection<String> keys) {
		// Return value builder
		StringBuilder result = new StringBuilder();
		
		// Process keys
		// - Flag that handles the first key
		boolean isFirst = true;
		// - Process keys
		for (String key: keys) {if (!isFirst) result.append(","); else isFirst=false; result.append(key);}
		
		// Return string
		return result.toString();
	}*/
	
	
	/**
	 * Extract a list of values from a map
	 *//*
	protected String sqlCreateValues(Collection<Object> values) {
		// Return value builder
		StringBuilder result = new StringBuilder();
		
		// Process keys
		// - Flag that handles the first key
		boolean isFirst = true;
		// - Process keys
		for (Object key: values) {if (!isFirst) result.append(","); else isFirst=false; result.append("'"+key+"'");}
		
		// Return string
		return result.toString();
	}*/


	
	/**
	 * Create (insert) a value into the SQL table
	 *//*
	@Override @SuppressWarnings("unchecked")
	public void createValue(String propertyName, Object arg1) throws Exception {
		// Set query
		DynamicSQLRunner query = propertyCreateQueries.get(propertyName);
		
		// Null pointer check
		if (query == null) return;
		
		// Create parameter array
		Object[] parameter = new Object[2];
		parameter[0] = sqlCreateKeys(((Map<String, Object>) arg1).keySet());
		parameter[1] = sqlCreateValues(((Map<String, Object>) arg1).values());
		
		// Execute query and return result
		query.runUpdate(parameter);
	}*/


	
	/**
	 * Delete a value from the SQL table
	 *//*
	@Override
	public void deleteValue(String arg0) throws Exception {
		// This is not implemented
	}*/


	
	/**
	 * Delete a value from the SQL table
	 *//*
	@Override @SuppressWarnings("unchecked")
	public void deleteValue(String propertyName, Object arg1) throws Exception {
		// Set query
		DynamicSQLRunner query = propertyDeleteQueries.get(propertyName);
		
		// Null pointer check
		if (query == null) return;

		// Cast argument to collection
		Collection<Object> parameterList = (Collection<Object>) arg1;
		
		// Create parameter array
		Object[] parameter = new Object[parameterList.size()];
		// - Copy parameter
		int counter = 0; for (Object par: parameterList) parameter[counter++] = par;
		
		// Execute query and return result
		query.runUpdate(parameter);
	}*/

	
/*
	@Override
	public Map<String, IElementReference> getContainedElements(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getElementScope(String arg0) {
		logger.debug("GetScope:"+arg0);
		// TODO Auto-generated method stub
		return null;
	}
*/

	
	/**
	 * Query the SQL database
	 *//*
	@Override
	public Object getModelPropertyValue(String propertyName) {
		// Get query
		DynamicSQLRunner query = propertyGetQueries.get(propertyName);
		
		// Null pointer check
		if (query == null) return null;
		
		// Execute query and return result
		return query.runQuery();
	}*/


	
	/**
	 * Invoke operation with given parameter list
	 *//*
	@Override
	public Object invokeOperation(String propertyName, Object[] parameter) throws Exception {
		// Set query
		DynamicSQLRunner query = operations.get(propertyName);
		
		// Null pointer check
		if (query == null) return null;
		
		// Execute query and return result
		if (updateOperations.contains(propertyName)) {
			query.runUpdate(parameter); 
			return null; 
		} else {
			return query.runQuery(parameter);
		}
	}*/


	
	/**
	 * Invoke set operation with given parameter
	 *//*
	@Override @SuppressWarnings("unchecked")
	public void setModelPropertyValue(String propertyName, Object arg1) throws Exception {
		// Set query
		DynamicSQLRunner query = propertySetQueries.get(propertyName);
		
		// Null pointer check
		if (query == null) return;

		logger.debug("LENC:"+arg1);

		// Create parameter array
		Object[] parameter = null;
		// - Process collections
		if (arg1 instanceof Collection) {
			// Cast to collection
			Collection<Object> parameterList = (Collection<Object>) arg1;
			
			// Create parameter array and copy parameter
			parameter = new Object[parameterList.size()];
			int counter = 0; for (Object par: parameterList) parameter[counter++] = par;
		} else {
			// Create parameter array and copy parameter
			parameter = new Object[1];
			parameter[0] = arg1;			
		}
		
		
		// Execute query and return result
		query.runUpdate(parameter);
	}*/


	
	/**
	 * Invoke set operation with given parameter list
	 *//*
	@Override
	public void setModelPropertyValue(String propertyName, Object... parameter) throws Exception {
		// Set query
		DynamicSQLRunner query = propertySetQueries.get(propertyName);
		
		// Null pointer check
		if (query == null) return;
		
		logger.debug("LEN:"+parameter.length);
		logger.debug("LEN-0:"+parameter[0]);

		// Execute query and return result
		query.runUpdate(parameter);
	}*/
	
	public static String buildSqlCfgName(String valueName) {
		return BaseConfiguredProvider.buildCfgName("basyx.sql", valueName);
	}
}
