package org.eclipse.basyx.tools.sqlproxy;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.components.sqlprovider.driver.ISQLDriver;
import org.eclipse.basyx.components.sqlprovider.driver.SQLDriver;
import org.eclipse.basyx.components.sqlprovider.query.DynamicSQLQuery;
import org.eclipse.basyx.components.sqlprovider.query.DynamicSQLUpdate;



/**
 * This class implements a collection that mirrors its contents into a SQL database
 * 
 * A SQL table has the following structure:
 * - type:integer | value:text
 *  
 * @author kuhn
 *
 */
public class SQLCollection extends SQLProxy implements Collection<Object> {

	
	
	/**
	 * Constructor
	 * 
	 * @param rootElement SQLRootElement for this element
	 * @param tableId     Table ID of this element in SQL database
	 */
	public SQLCollection(SQLRootElement rootElement, int tableId) {
		// Invoke base constructor
		super(rootElement.sqlUser, rootElement.sqlPass, rootElement.sqlURL, rootElement.sqlDriver, rootElement.sqlPrefix, rootElement.sqlTableID+"__"+tableId, rootElement);
	}

	
	/**
	 * Constructor
	 * 
	 * @param rootElement        SQLRootElement for this element
	 * @param tableIdWithprefix  Table ID of this element in SQL database with prefix
	 */
	public SQLCollection(SQLRootElement rootElement, String tableIdWithprefix) {
		// Invoke base constructor
		super(rootElement.sqlUser, rootElement.sqlPass, rootElement.sqlURL, rootElement.sqlDriver, rootElement.sqlPrefix, tableIdWithprefix, rootElement);	
	}

	
	
	
	/**
	 * Get number of collection elements
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
	 * Check if collection is empty
	 */
	@Override
	public boolean isEmpty() {
		// Map is empty iff its size equals 0
		return (size() == 0);
	}

	
	/**
	 * Check if collection contains specified element
	 */
	@Override
	public boolean contains(Object value) {
		// Return query, use new driver
		return contains(new SQLDriver(sqlURL, sqlUser, sqlPass, sqlPrefix, sqlDriver), value);
	}
	
	
	/**
	 * Check if collection contains specified element
	 */
	protected boolean contains(ISQLDriver drv, Object value) {
		// Build query string
		String          queryString = "SELECT * FROM elements."+sqlTableID+" WHERE value='$value'";
		// - Build dynamic query
		// - basically, the last parameter is not used here, as getRaw does not post process query results 
		DynamicSQLQuery dynQuery    = new DynamicSQLQuery(drv, queryString, "mapArray(value:String,type:String)");

		// Build query parameter
		Map<String, Object> parameter = new HashMap<>();
		// - Put name in map
		parameter.put("value", SQLTableRow.getValueAsString(value));
		
		// Execute query, get result set
		ResultSet result = dynQuery.getRaw(parameter);

		// Data base table contains key iff result set size > 1
		return (getSize(result) > 0);
	}


	
	/**
	 * Return array iterator
	 */
	@Override @SuppressWarnings("unchecked")
	public Iterator<Object> iterator() {
		// Get values
		List<Map<String, Object>> sqlResult = (List<Map<String, Object>>) getMapColumnRaw(sqlTableID, "type", "value");

		// Create iterator interface instance
		Iterator<Object> it = new Iterator<Object>() {

			// Iterator element index
			private int currentIndex = 0;

			// Check if iterator has another element
			@Override
			public boolean hasNext() {
				return currentIndex < sqlResult.size();
			}

			// Increment iterator element
			@Override
			public Object next() {
				// Get result from SQL
				Map<String, Object> singleResult = sqlResult.get(currentIndex++);
				
				// Process result
				return SQLTableRow.getValueFromString(sqlRootElement, Integer.parseInt((String) singleResult.get("type")), (String) singleResult.get("value"));
			}

			// No support for remove operation
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
		
		// Return iterator
		return it;
	}

	
	/**
	 * Return collection elements as array
	 */
	@Override @SuppressWarnings("unchecked")
	public Object[] toArray() {
		// Get values
		List<Map<String, Object>> sqlResult = (List<Map<String, Object>>) getMapColumnRaw(sqlTableID, "type", "value");
		
		// Create return value
		Object[] result = new Object[sqlResult.size()];
		// - Fill result
		int counter = 0; 
		for (Map<String, Object> singleResult: sqlResult) {
			result[counter++] = (SQLTableRow.getValueFromString(sqlRootElement, Integer.parseInt((String) singleResult.get("type")), (String) singleResult.get("value")));
		}

		// Return array
		return result;
	}

	
	/**
	 * Return collection elements as array of given type
	 */
	@Override @SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] array) {
		// Get values
		List<Map<String, Object>> sqlResult = (List<Map<String, Object>>) getMapColumnRaw(sqlTableID, "type", "value");
		
		// Create return value if necessary
		T[] result = array;
		// - Size check
		if (result.length < sqlResult.size()) result = (T[]) Array.newInstance(array.getClass().getComponentType(), sqlResult.size());

		// Fill result array
		int counter = 0; 
		for (Map<String, Object> singleResult: sqlResult) {
			result[counter++] = (T) (SQLTableRow.getValueFromString(sqlRootElement, Integer.parseInt((String) singleResult.get("type")), (String) singleResult.get("value")));
		}

		// Return array
		return result;
	}

	
	/**
	 * Add element to collection
	 */
	@Override
	public boolean add(Object value) {
		// Put object
		addToCollectionSimple(sqlTableID, new SQLTableRow(value));
		
		// Indicate success
		return true;
	}

	
	/**
	 * Remove element from collection
	 */
	@Override
	public boolean remove(Object value) {
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
			if (contains(sqlDrv, value)) {
				// Delete element from map
				String updateString = "DELETE FROM elements."+sqlTableID+" WHERE value='$value'";
				DynamicSQLUpdate dynUpdate = new DynamicSQLUpdate(sqlDrv, updateString);

				// Parameter map
				Map<String, Object> parameter = new HashMap<>();
				// - Put name in map
				parameter.put("value", SQLTableRow.getValueAsString(value));
				// - Execute delete
				dynUpdate.accept(parameter);
		
				// Commit and close transaction
				sqlDrv.getConnection().commit();
				sqlDrv.closeConnection();

				// Indicate collection change
				return true;
			}
		} catch (SQLException e) {
			// Output exception
			e.printStackTrace();
		} finally {
			// Close connection
			sqlDrv.closeConnection();			
		}
		
		// No change in collection
		return false;
	}
	
	
	/**
	 * Remove element if element table does contain it
	 */
	protected boolean removeIfContained(SQLDriver sqlDrv, Object value) {
		// Check if key is in map, then update SQL database
		if (contains(sqlDrv, value)) {
			// Delete element from map
			String updateString = "DELETE FROM elements."+sqlTableID+" WHERE value='$value'";
			DynamicSQLUpdate dynUpdate = new DynamicSQLUpdate(sqlDrv, updateString);

			// Parameter map
			Map<String, Object> parameter = new HashMap<>();
			// - Put name in map
			parameter.put("value", SQLTableRow.getValueAsString(value));
			// - Execute delete
			dynUpdate.accept(parameter);
	
			// Indicate collection change
			return true;
		}
		
		// No change
		return false;
	}


	/**
	 * Remove element if element table does contain it
	 */
	protected boolean removeSerValueIfContained(SQLDriver sqlDrv, String value) {
		// Check if key is in map, then update SQL database
		if (contains(sqlDrv, value)) {
			// Delete element from map
			String updateString = "DELETE FROM elements."+sqlTableID+" WHERE value='$value'";
			DynamicSQLUpdate dynUpdate = new DynamicSQLUpdate(sqlDrv, updateString);

			// Parameter map
			Map<String, Object> parameter = new HashMap<>();
			// - Put name in map
			parameter.put("value", value);
			// - Execute delete
			dynUpdate.accept(parameter);
	
			// Indicate collection change
			return true;
		}
		
		// No change
		return false;
	}


	/**
	 * Check if collection contains all given elements
	 */
	@Override
	public boolean containsAll(Collection<?> values) {
		// SQL driver used for operation
		SQLDriver sqlDrv = null;
		
		// Flag that indicates if all checked elements are contained in map
		boolean containsAllFlag = true;

		// Try to complete transaction
		try {
			// Create connection and start transaction
			sqlDrv = new SQLDriver(sqlURL, sqlUser, sqlPass, sqlPrefix, sqlDriver);
			// - Open connection
			sqlDrv.openConnection();
			// - Switch off auto commit
			sqlDrv.getConnection().setAutoCommit(false);
		
			// Check if all elements are contained 
			for (Object val : values) if (!contains(sqlDrv, val)) containsAllFlag = false;

			// Close transaction
			sqlDrv.closeConnection();

			// Indicate if all requested elements are contained
			return containsAllFlag;
		} catch (SQLException e) {
			// Output exception
			e.printStackTrace();
		} finally {
			// Close connection
			sqlDrv.closeConnection();			
		}

		// An exception did occur
		return false;
	}

	
	/**
	 * Add all elements to collection
	 */
	@Override
	public boolean addAll(Collection<? extends Object> values) {
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
		
			// Iterate elements
			for (Object val: values) {
				// Remove element iff contained in SQL table
				addToCollectionSimple(sqlDrv, sqlTableID, new SQLTableRow(val));
			}
			
			// Commit transaction and close connection
			sqlDrv.getConnection().commit();
			sqlDrv.closeConnection();
		} catch (SQLException e) {
			// Output exception
			e.printStackTrace();
		} finally {
			// Close connection
			sqlDrv.closeConnection();			
		}

		// Indicate collection change
		return true;
	}

	
	/**
	 * Remove all elements from collection
	 */
	@Override
	public boolean removeAll(Collection<?> values) {
		// SQL driver used for operation
		SQLDriver sqlDrv = null;
		
		// Change in SQL database
		boolean performedChange = false;
		

		// Try to complete transaction
		try {
			// Create connection and start transaction
			sqlDrv = new SQLDriver(sqlURL, sqlUser, sqlPass, sqlPrefix, sqlDriver);
			// - Open connection
			sqlDrv.openConnection();
			// - Switch off auto commit
			sqlDrv.getConnection().setAutoCommit(false);
		
			// Iterate elements
			for (Object val: values) {
				// Remove element iff contained in SQL table
				performedChange |= removeIfContained(sqlDrv, val);
			}
			
			// Commit transaction and close connection
			sqlDrv.getConnection().commit();
			sqlDrv.closeConnection();
		} catch (SQLException e) {
			// Output exception
			e.printStackTrace();
		} finally {
			// Close connection
			sqlDrv.closeConnection();			
		}

		// Return changed flag
		return performedChange;
	}

	
	/**
	 * Remove all other elements from collection
	 */
	@Override @SuppressWarnings("unchecked")
	public boolean retainAll(Collection<?> values) {
		// SQL driver used for operation
		SQLDriver sqlDrv = null;
		
		// Change in SQL database
		boolean performedChange = false;
		
		// Serialize all values in collection
		Collection<String> serValues = new LinkedList<String>();
		// - Serialize values
		for (Object val: values) serValues.add(SQLTableRow.getValueAsString(val));
		

		// Try to complete transaction
		try {
			// Create connection and start transaction
			sqlDrv = new SQLDriver(sqlURL, sqlUser, sqlPass, sqlPrefix, sqlDriver);
			// - Open connection
			sqlDrv.openConnection();
			// - Switch off auto commit
			sqlDrv.getConnection().setAutoCommit(false);

			// Get all values in table
			List<Map<String, Object>> sqlResult = (List<Map<String, Object>>) getMapColumnRaw(sqlDrv, sqlTableID, "type", "value");
			
			// Remove all elements that are not part of values collection
			for (Map<String, Object> row: sqlResult) {
				// Remove value if contained in map
				if (!serValues.contains((String) row.get("value"))) performedChange |= this.removeSerValueIfContained(sqlDrv, (String) row.get("value"));
			}
			
			// Commit transaction and close connection
			sqlDrv.getConnection().commit();
			sqlDrv.closeConnection();
		} catch (SQLException e) {
			// Output exception
			e.printStackTrace();
		} finally {
			// Close connection
			sqlDrv.closeConnection();			
		}

		// Changed flag
		return performedChange;
	}

	
	/**
	 * Clear collection
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
}

