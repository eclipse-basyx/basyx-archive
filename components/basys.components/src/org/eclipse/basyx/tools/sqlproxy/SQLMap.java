package org.eclipse.basyx.tools.sqlproxy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.components.sqlprovider.driver.ISQLDriver;
import org.eclipse.basyx.components.sqlprovider.driver.SQLDriver;
import org.eclipse.basyx.components.sqlprovider.query.DynamicSQLQuery;
import org.eclipse.basyx.components.sqlprovider.query.DynamicSQLUpdate;



/**
 * This class implements a map that mirrors its contents into a SQL database
 * 
 * - The map represents the root map that is linked into the SQL database as one table
 * - Subsequent maps map to subsequent SQL tables
 * 
 * A SQL table has the following structure:
 * - name:text | type:integer | value:text
 *  
 * @author kuhn
 *
 */
public class SQLMap extends SQLProxy implements Map<String, Object> {
	
	
	/**
	 * Constructor
	 * 
	 * @param rootElement SQLRootElement for this element
	 * @param tableId     Table ID of this element in SQL database
	 */
	public SQLMap(SQLRootElement rootElement, int tableId) {
		// Invoke base constructor
		super(rootElement.sqlUser, rootElement.sqlPass, rootElement.sqlURL, rootElement.sqlDriver, rootElement.sqlPrefix, rootElement.sqlTableID+"__"+tableId, rootElement);	
	}

	
	/**
	 * Constructor
	 * 
	 * @param rootElement        SQLRootElement for this element
	 * @param tableIdWithprefix  Table ID of this element in SQL database with prefix
	 */
	public SQLMap(SQLRootElement rootElement, String tableIdWithprefix) {
		// Invoke base constructor
		super(rootElement.sqlUser, rootElement.sqlPass, rootElement.sqlURL, rootElement.sqlDriver, rootElement.sqlPrefix, tableIdWithprefix, rootElement);	
	}

	
	
	/**
	 * Get number of map elements
	 */
	@Override
	public int size() {
		// Build query string
		String          queryString = "SELECT * FROM elements."+sqlTableID;
		// - Build dynamic query
		// - basically, the last parameter is not used here, as getRaw does not post process query results 
		DynamicSQLQuery dynQuery    = new DynamicSQLQuery(sqlURL, sqlUser, sqlPass, sqlPrefix, sqlDriver, queryString, "mapArray(name:String,value:String,type:String)");
		
		// Execute query and get result set
		ResultSet result = dynQuery.getRaw();

		// Calculate size
		return getSize(result);
	}

	
	
	/**
	 * Check if map is empty
	 */
	@Override
	public boolean isEmpty() {
		// Map is empty iff its size equals 0
		return (size() == 0);
	}

	
	
	/**
	 * Check if map contains the given key (name)
	 */
	@Override
	public boolean containsKey(Object key) {
		// Use new driver for operation
		return containsKey(new SQLDriver(sqlURL, sqlUser, sqlPass, sqlPrefix, sqlDriver), key);
	}
	
	
	/**
	 * Check if map contains the given key (name)
	 */
	protected boolean containsKey(ISQLDriver drv, Object key) {
		// Build query string
		String          queryString = "SELECT * FROM elements."+sqlTableID+" WHERE name='$name'";
		// - Build dynamic query
		// - basically, the last parameter is not used here, as getRaw does not post process query results 
		DynamicSQLQuery dynQuery    = new DynamicSQLQuery(drv, queryString, "mapArray(name:String,value:String,type:String)");

		// Build query parameter
		Map<String, Object> parameter = new HashMap<>();
		// - Put name in map
		parameter.put("name", key);
		
		// Execute query, get result set
		ResultSet result = dynQuery.getRaw(parameter);

		// Data base table contains key iff result set size > 1
		return getSize(result) > 0;
	}


	
	
	/**
	 * Check if map contains the given value
	 */
	@Override
	public boolean containsValue(Object value) {
		// Use new driver for operation
		return containsValue(new SQLDriver(sqlURL, sqlUser, sqlPass, sqlPrefix, sqlDriver), value);
	}


	
	/**
	 * Check if map contains the given value
	 */
	protected boolean containsValue(ISQLDriver drv, Object value) {
		// Build query string
		String          queryString = "SELECT * FROM elements."+sqlTableID+" WHERE value='$value'";
		// - Build dynamic query
		// - basically, the last parameter is not used here, as getRaw does not post process query results 
		DynamicSQLQuery dynQuery    = new DynamicSQLQuery(drv, queryString, "mapArray(name:String,value:String,type:String)");

		// Build query parameter
		Map<String, Object> parameter = new HashMap<>();
		// - Put name in map
		parameter.put("value", SQLTableRow.getValueAsString(value));
		
		// Execute query, get result set
		ResultSet result = dynQuery.getRaw(parameter);

		// Data base table contains key iff result set size > 1
		return getSize(result) > 0;
	}


	/**
	 * Get value of map element that is identified by given key
	 */
	@Override
	public Object get(Object key) {
		// Get value from SQL database table
		return getValueFromMap(sqlTableID, key.toString());
	}

	
	
	/**
	 * Put a key into a map
	 */
	@Override
	public Object put(String key, Object value) {
		// SQL driver used for operation
		SQLDriver sqlDrv = null;

		// Try to complete transaction
		try {
			// Create connection and start transaction
			sqlDrv = new SQLDriver(sqlURL, sqlUser, sqlPass, sqlPrefix, sqlDriver);
			// - Open connection
			sqlDrv.openConnection();
			// - Switch off auto commit
			sqlDrv.getConnection().setAutoCommit(false);
		
			// Check if key is in map, then update SQL database
			if (containsKey(sqlDrv, key)) {updateInMapSimple(sqlDrv, sqlTableID, new SQLTableRow(key, value)); return value;}
		
			// Put object
			addToMapSimple(sqlDrv, sqlTableID, new SQLTableRow(key, value));
		
			// Commit and close transaction
			sqlDrv.getConnection().commit();
			sqlDrv.closeConnection();
		} catch (SQLException e) {
			// Output exception
			e.printStackTrace();
		} finally {
			// Close connection
			sqlDrv.closeConnection();			
		}
		
		// Return inserted object
		return value;
	}
	
	

	/**
	 * Remove element with key from map
	 */
	@Override
	public Object remove(Object key) {
		// SQL driver used for operation
		SQLDriver sqlDrv = null;
		
		// Return value
		Object result = null;
		

		// Try to complete transaction
		try {
			// Create connection and start transaction
			sqlDrv = new SQLDriver(sqlURL, sqlUser, sqlPass, sqlPrefix, sqlDriver);
			// - Open connection
			sqlDrv.openConnection();
			// - Switch off auto commit
			sqlDrv.getConnection().setAutoCommit(false);

			// Get element from map for return value
			result = getValueFromMap(sqlDrv, sqlTableID, key.toString());

			// Delete element from map
			String updateString = "DELETE FROM elements."+sqlTableID+" WHERE name='$name'";
			DynamicSQLUpdate dynUpdate = new DynamicSQLUpdate(sqlDrv, updateString);

			// Parameter map
			Map<String, Object> parameter = new HashMap<>();
			// - Put name in map
			parameter.put("name", key);
			// - Execute delete
			dynUpdate.accept(parameter);
			
			// Commit and close transaction
			sqlDrv.getConnection().commit();
			sqlDrv.closeConnection();
		} catch (SQLException e) {
			// Output exception
			e.printStackTrace();
		} finally {
			// Close connection
			sqlDrv.closeConnection();			
		}

		// Return element
		return result;
	}

	
	
	/**
	 * Put all elements into map
	 */
	@Override @SuppressWarnings("unchecked")
	public void putAll(Map<? extends String, ? extends Object> map) {
		// SQL driver used for operation
		SQLDriver sqlDrv = null;

		// Try to complete transaction
		try {
			// Create connection and start transaction
			sqlDrv = new SQLDriver(sqlURL, sqlUser, sqlPass, sqlPrefix, sqlDriver);
			// - Open connection
			sqlDrv.openConnection();
			// - Switch off auto commit
			sqlDrv.getConnection().setAutoCommit(false);

			// Remove old elements
			removeAllKeys(sqlDrv, (Set<String>) map.keySet());
		
			// Create map elements
			Collection<SQLTableRow> mapElements = new LinkedList<>();
			// - Fill collection
			for (String key: map.keySet()) {mapElements.add(new SQLTableRow(key, map.get(key)));}
		
			// Add elements to map
			addToMapMultiple(sqlDrv, sqlTableID, mapElements);

			// Commit and close transaction
			sqlDrv.getConnection().commit();
			sqlDrv.closeConnection();
		} catch (SQLException e) {
			// Output exception
			e.printStackTrace();
		} finally {
			// Close connection
			sqlDrv.closeConnection();			
		}
	}

	
	
	/**
	 * Delete all map elements
	 */
	@Override
	public void clear() {
		// Build SQL update string
		String updateString = "DELETE FROM elements."+sqlTableID;
		DynamicSQLUpdate dynUpdate = new DynamicSQLUpdate(sqlURL, sqlUser, sqlPass, sqlPrefix, sqlDriver, updateString);
		
		// Empty parameter set
		Map<String, Object> parameter = new HashMap<>();
		
		// Run SQL query
		dynUpdate.accept(parameter);	
	}

	
	
	/**
	 * Get the set of keys from SQL database
	 */
	@Override @SuppressWarnings("unchecked")
	public Set<String> keySet() {
		// Get key set - keys are stored in "name" column in table
		return (Set<String>) getSingleMapColumnRaw(sqlTableID, "name");
	}

	
	
	/**
	 * Get map values that are contained in the SQL database
	 */
	@Override @SuppressWarnings("unchecked")
	public Collection<Object> values() {
		// Get values
		List<Map<String, Object>> sqlResult = (List<Map<String, Object>>) getMapColumnRaw(sqlTableID, "type", "value");
		
		// Build types
		Collection<Object> result = new LinkedList<Object>();
		// - Fill result
		for (Map<String, Object> singleResult: sqlResult) {
			result.add(SQLTableRow.getValueFromString(sqlRootElement, Integer.parseInt((String) singleResult.get("type")), (String) singleResult.get("value"))); 
		}
		
		// Return result
		return result;
	}
	
	

	/**
	 * Return map elements as entry sets
	 */
	@Override @SuppressWarnings("unchecked")
	public Set<Entry<String, Object>> entrySet() {
		// Get values
		List<Map<String, Object>> sqlResult = (List<Map<String, Object>>) getMapColumnRaw(sqlTableID, "name", "type", "value");
		
		// Build result
		Set<Entry<String, Object>> result = new HashSet<>();
		
		// Fill hash set - iterate result
		for (Map<String, Object> singleResult: sqlResult) {
			// Deserialize value from string
			Object value = SQLTableRow.getValueFromString(sqlRootElement, Integer.parseInt((String) singleResult.get("type")), (String) singleResult.get("value"));
			
			// Build entry
			Entry<String, Object> resultEntry = new AbstractMap.SimpleEntry<String, Object>((String) singleResult.get("name"), value);
			
			// Add result entry to result
			result.add(resultEntry);
		}
		
		// Return result
		return result;
	}
}
