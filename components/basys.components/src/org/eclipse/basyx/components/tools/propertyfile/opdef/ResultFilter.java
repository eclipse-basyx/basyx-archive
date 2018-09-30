package org.eclipse.basyx.components.tools.propertyfile.opdef;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;




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
	public static Object stringArray(ResultSet sqlResult, String columnName) {
		// Create result
		Collection<String> result = new LinkedList<>();
		
		System.out.println("StringArray: "+columnName);
		
		// Process all SQL results
		try {
			while (sqlResult.next()) result.add(sqlResult.getString(columnName));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Return result
		return result;
	}
}
