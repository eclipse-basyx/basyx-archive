package org.eclipse.basyx.components.tools.propertyfile.opdef;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;




/**
 * Class that provides result filters for operation definitions
 * 
 * @author kuhn
 *
 */
public class ResultFilter {

	
	/**
	 * Extract a column from a SQL result set and return this column as set
	 * 
	 * @param result     SQL result
	 * @param columnName Name of column to extract
	 */
	public static Set<String> stringSet(ResultSet sqlResult, Object... columnName) {
		// Create result
		Set<String> result = new HashSet<>();
		
		// Process all SQL results
		try {
			while (sqlResult.next()) {result.add(sqlResult.getString((String) columnName[0]));}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Return result
		return result;
	}
	
	
	/**
	 * Extract a column from a SQL result set and return this column as collection
	 * 
	 * @param result     SQL result
	 * @param columnName Name of column to extract
	 */
	public static Object stringArray(ResultSet sqlResult, Object... columnName) {
		// Create result
		Collection<String> result = new LinkedList<>();
		
		// Process all SQL results
		try {
			while (sqlResult.next()) {result.add(sqlResult.getString((String) columnName[0]));}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Return result
		return result;
	}

	
	/**
	 * Return SQL result set as Map
	 * 
	 * @param result     SQL result
	 * @param columnName Name of column to extract
	 */
	public static Object mapArray(ResultSet sqlResult, Object... columnNames) {
		// Create result
		Map<String, Object> result = new HashMap<>();
				
		// Process all SQL results
		try {
			while (sqlResult.next()) {
				// Process columns
				for (Object columnName: columnNames) {
					result.put((String) columnName, sqlResult.getString((String) columnName));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Return result
		return result;
	}
	
	
	/**
	 * Return SQL result set as List of Maps
	 * 
	 * @param result     SQL result
	 * @param columnName Name of column to extract
	 */
	public static Object listOfMaps(ResultSet sqlResult, Object... columnNames) {
		// Create result
		List<Object> result = new LinkedList<>();
				
		// Process all SQL results
		try {
			while (sqlResult.next()) {
				// List element
				Map<String, Object> listElement = new HashMap<>();				
				
				// Process columns
				for (Object columnName: columnNames) {
					listElement.put((String) columnName, sqlResult.getString((String) columnName));
				}
				
				// Add list element to result
				result.add(listElement);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Return result
		return result;
	}
}
