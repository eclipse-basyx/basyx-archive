package org.eclipse.basyx.components.sqlprovider;

import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.eclipse.basyx.aas.impl.provider.javahandler.genericsm.GenericHandlerSubmodel;
import org.eclipse.basyx.aas.impl.resources.basic.AssetKind;
import org.eclipse.basyx.aas.impl.resources.basic.DataType;
import org.eclipse.basyx.components.sqlprovider.query.DynamicSQLQuery;



/**
 * Asset administration shell sub model provider that connects to SQL database
 * 
 * @author kuhn
 *
 */
public class SQLSubModelProvider extends GenericHandlerSubmodel {

	
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
	 * Constructor
	 */
	public SQLSubModelProvider(String smName, String smID, String smType, String aasName, String aasID, Properties cfgValues) {
		// Call base constructor
		super(AssetKind.INSTANCE, smName, smID, smType, aasName, aasID);
		
		// Extract SQL properties
		sqlUser = cfgValues.getProperty("dbuser");
		sqlPass = cfgValues.getProperty("dbpass");
		sqlURL  = cfgValues.getProperty("dburl");

		// Extract SQL driver properties
		sqlDriver = cfgValues.getProperty("sqlDriver");
		sqlPrefix = cfgValues.getProperty("sqlPrefix");
		
		// Load and parse SQL property and operation connections
		sqlPropertyConnections.addAll(splitString(cfgValues.getProperty("properties")));
		sqlOperationConnections.addAll(splitString(cfgValues.getProperty("query_operations")));

		
		// Add properties
		for (String propertyName: sqlPropertyConnections) {
			// Get parameter count and parameter count
			int    parameterCount = Integer.parseInt(cfgValues.getProperty(propertyName+"_parameter"));
			String queryString    = cfgValues.getProperty(propertyName+"_get");
			String resultFilterOp = cfgValues.getProperty(propertyName+"_get_result");
			
			// Trim query string and resultFilterOp (remove '"' at beginning and end)
			queryString    = queryString.substring(1, queryString.length()-1);
			resultFilterOp = resultFilterOp.substring(1, resultFilterOp.length()-1);
			
			// Create dynamic SQL query
			DynamicSQLQuery getQuery = new DynamicSQLQuery(sqlURL, sqlUser, sqlPass, sqlPrefix, sqlDriver, parameterCount, queryString, resultFilterOp);
			
			// Create dynamic SQL query
			this.addProperty(
					propertyName,
					DataType.OBJECT,
					(obj) -> {return getQuery.runQuery(obj);}, 
					null,
					null,
					null);
		}
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
}
