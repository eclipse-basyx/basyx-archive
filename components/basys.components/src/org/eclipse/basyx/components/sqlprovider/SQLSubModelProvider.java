package org.eclipse.basyx.components.sqlprovider;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.eclipse.basyx.aas.api.reference.IElementReference;
import org.eclipse.basyx.aas.api.services.IModelProvider;
import org.eclipse.basyx.components.sqlprovider.query.DynamicSQLQuery;



/**
 * Asset administration shell sub model provider that connects to SQL database
 * 
 * @author kuhn
 *
 */
public class SQLSubModelProvider implements IModelProvider {

	
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
	protected Map<String, DynamicSQLQuery> propertyGetQueries = new HashMap<>();

	
	/**
	 * Run SQL update to access properties via 'set' operation
	 */
	protected Map<String, DynamicSQLQuery> propertySetQueries = new HashMap<>();

	
	/**
	 * Run SQL update to access properties via 'create' operation
	 */
	protected Map<String, DynamicSQLQuery> propertyCreateQueries = new HashMap<>();


	/**
	 * Run SQL update to access properties via 'delete' operation
	 */
	protected Map<String, DynamicSQLQuery> propertyDeleteQueries = new HashMap<>();


	
	/**
	 * SQL operations
	 */
	protected Map<String, DynamicSQLQuery> operations = new HashMap<>();

	
	/**
	 * SQL operations that run as update operations. Not contained operations are query operations.
	 */
	protected Set<String> updateOperations = new HashSet<>();

	
	
	
	/**
	 * Constructor
	 */
	public SQLSubModelProvider(Properties cfgValues) {
		// Call base constructor
		//super(AssetKind.INSTANCE, smName, smID, smType, aasName, aasID);
		
		// Extract SQL properties
		sqlUser = cfgValues.getProperty("dbuser");
		sqlPass = cfgValues.getProperty("dbpass");
		sqlURL  = cfgValues.getProperty("dburl");

		// Extract SQL driver properties
		sqlDriver = cfgValues.getProperty("sqlDriver");
		sqlPrefix = cfgValues.getProperty("sqlPrefix");
		
		// Load and parse SQL property and operation connections
		sqlPropertyConnections.addAll(splitString(cfgValues.getProperty("properties")));
		sqlOperationConnections.addAll(splitString(cfgValues.getProperty("operations")));

		// Add properties
		for (String propertyName: sqlPropertyConnections) {
			// Try to parse parameter
			propertyGetQueries.put(propertyName, createSQLOperation(propertyName+"_get", cfgValues));
			propertySetQueries.put(propertyName, createSQLOperation(propertyName+"_set", cfgValues));
			propertyCreateQueries.put(propertyName, createSQLOperation(propertyName+"_create", cfgValues));
			propertyDeleteQueries.put(propertyName, createSQLOperation(propertyName+"_delete", cfgValues));
		}
		
		// Add operations
		for (String operationName: sqlOperationConnections) {
			// Create operation
			operations.put(operationName, createSQLOperation(operationName, cfgValues));
			// Mark operation as update operation depending on operations/<operationName>_kind property value
			try {
				if (cfgValues.getProperty(operationName+"_kind").trim().equalsIgnoreCase("update")) {
					updateOperations.add(operationName);
				}
			} catch (Exception e) {}
		}
	}
	
	
	
	
	/**
	 * Create a dynamic SQL operation
	 */
	protected DynamicSQLQuery createSQLOperation(String propertyName, Properties cfgValues) {
		// Check parameter presence
		if (!cfgValues.containsKey(propertyName)) return null;
		
		// Get parameter count and parameter count
		int    parameterCount = Integer.parseInt(cfgValues.getProperty(propertyName+"_parameter"));
		String queryString    = cfgValues.getProperty(propertyName);
		String resultFilterOp = cfgValues.getProperty(propertyName+"_result");
		
		// Trim query string and resultFilterOp (remove '"' at beginning and end)
		queryString    = queryString.substring(1, queryString.length()-1);
		try {resultFilterOp = resultFilterOp.substring(1, resultFilterOp.length()-1);} catch (NullPointerException | StringIndexOutOfBoundsException e) {}
		
		// Create dynamic SQL query
		DynamicSQLQuery sqlQuery = new DynamicSQLQuery(sqlURL, sqlUser, sqlPass, sqlPrefix, sqlDriver, parameterCount, queryString, resultFilterOp);

		// Return created query
		return sqlQuery;
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
	 */
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
	}
	
	
	/**
	 * Extract a list of values from a map
	 */
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
	}


	
	/**
	 * Create (insert) a value into the SQL table
	 */
	@Override @SuppressWarnings("unchecked")
	public void createValue(String propertyName, Object arg1) throws Exception {
		// Set query
		DynamicSQLQuery query = propertyCreateQueries.get(propertyName);
		
		// Null pointer check
		if (query == null) return;
		
		// Create parameter array
		Object[] parameter = new Object[2];
		parameter[0] = sqlCreateKeys(((Map<String, Object>) arg1).keySet());
		parameter[1] = sqlCreateValues(((Map<String, Object>) arg1).values());
		
		// Execute query and return result
		query.runUpdate(parameter);
	}


	
	/**
	 * Delete a value from the SQL table
	 */
	@Override
	public void deleteValue(String arg0) throws Exception {
		// This is not implemented
	}


	
	/**
	 * Delete a value from the SQL table
	 */
	@Override @SuppressWarnings("unchecked")
	public void deleteValue(String propertyName, Object arg1) throws Exception {
		// Set query
		DynamicSQLQuery query = propertyDeleteQueries.get(propertyName);
		
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
	}

	

	@Override
	public Map<String, IElementReference> getContainedElements(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getElementScope(String arg0) {
		System.out.println("GetScope:"+arg0);
		// TODO Auto-generated method stub
		return null;
	}


	
	/**
	 * Query the SQL database
	 */
	@Override
	public Object getModelPropertyValue(String propertyName) {
		// Get query
		DynamicSQLQuery query = propertyGetQueries.get(propertyName);
		
		// Null pointer check
		if (query == null) return null;
		
		// Execute query and return result
		return query.runQuery();
	}


	
	/**
	 * Invoke operation with given parameter list
	 */
	@Override
	public Object invokeOperation(String propertyName, Object[] parameter) throws Exception {
		// Set query
		DynamicSQLQuery query = operations.get(propertyName);
		
		// Null pointer check
		if (query == null) return null;
		
		// Execute query and return result
		if (updateOperations.contains(propertyName)) {
			query.runUpdate(parameter); 
			return null; 
		} else {
			return query.runQuery(parameter);
		}
	}


	
	/**
	 * Invoke set operation with given parameter
	 */
	@Override @SuppressWarnings("unchecked")
	public void setModelPropertyValue(String propertyName, Object arg1) throws Exception {
		// Set query
		DynamicSQLQuery query = propertySetQueries.get(propertyName);
		
		// Null pointer check
		if (query == null) return;

		System.out.println("LENC:"+arg1);

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
	}


	
	/**
	 * Invoke set operation with given parameter list
	 */
	@Override
	public void setModelPropertyValue(String propertyName, Object... parameter) throws Exception {
		// Set query
		DynamicSQLQuery query = propertySetQueries.get(propertyName);
		
		// Null pointer check
		if (query == null) return;
		
		System.out.println("LEN:"+parameter.length);
		System.out.println("LEN-0:"+parameter[0]);

		// Execute query and return result
		query.runUpdate(parameter);
	}
}
