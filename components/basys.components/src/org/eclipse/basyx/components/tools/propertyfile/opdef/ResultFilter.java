package org.eclipse.basyx.components.tools.propertyfile.opdef;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;




/**
 * Class that provides result filters for operation definitions
 * 
 * @author kuhn
 *
 */
public class ResultFilter {

	
	/**
	 * Extract a column from a SQL result set and return this column as collection
	 * 
	 * @param result     SQL result
	 * @param columnName Name of column to extract
	 */
	public static Object stringArray(ResultSet sqlResult, Object... columnName) {
		// Create result
		Collection<String> result = new LinkedList<>();
		
		System.out.println("StringArray: "+columnName);
		
		// Process all SQL results
		try {
			while (sqlResult.next()) {result.add(sqlResult.getString((String) columnName[0])); System.out.println("Ading: "+sqlResult.getString((String) columnName[0]));}
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
}
