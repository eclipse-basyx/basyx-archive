package org.eclipse.basyx.tools.sqlproxy;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.basyx.components.sqlprovider.driver.SQLDriver;
import org.eclipse.basyx.components.sqlprovider.query.DynamicSQLQuery;
import org.eclipse.basyx.components.sqlprovider.query.DynamicSQLUpdate;



/**
 * Create a root element that connects to SQL database and that contains other maps and collections
 * 
 * @author kuhn
 *
 */
public class SQLRootElement extends SQLConnector {

	
	/**
	 * Constructor
	 * 
	 * @param user        SQL user name
	 * @param pass        SQL password
	 * @param url         SQL server URL
	 * @param driver      SQL driver
	 * @param prefix      JDBC SQL driver prefix
	 * @param tableID     ID of table for this element in database. Every element needs a unique ID
	 */
	public SQLRootElement(String user, String pass, String url, String driver, String prefix, String tableID) {
		// Base constructor
		super(user, pass, url, driver, prefix, tableID);
	}
	

	/**
	 * Get next free identifier for another element
	 */
	@SuppressWarnings("unchecked")
	public int getNextIdentifier() {
		// SQL driver used for operation
		SQLDriver sqlDrv = null;
		
		// Element ID
		int elementID = -1;

		// Try to complete transaction
		try {
			// Create connection and start transaction
			sqlDrv = new SQLDriver(sqlURL, sqlUser, sqlPass, sqlPrefix, sqlDriver);
			// - Open connection
			sqlDrv.openConnection();
			// - Switch off auto commit
			sqlDrv.getConnection().setAutoCommit(false);

			
			// SQL query string
			String queryString = "SELECT * FROM elements."+sqlTableID;
			DynamicSQLQuery dynQuery = new DynamicSQLQuery(sqlDrv, queryString, "mapArray(NextElementID:Integer,ElementPrefix:String)");
			
			// Empty parameter set
			Map<String, Object> parameter = new HashMap<>();
			// - Execute query, return the column as 
			Map<String, Object> sqlResult = (Map<String, Object>) dynQuery.get(parameter);

			// Store element ID
			elementID = Integer.parseInt((String) sqlResult.get("NextElementID"));

			
			// SQL update statement
			String updateString = "UPDATE elements."+sqlTableID+" SET NextElementID='"+(elementID+1)+"', ElementPrefix='"+sqlResult.get("ElementPrefix")+"'";
			DynamicSQLUpdate dynUpdate = new DynamicSQLUpdate(sqlDrv, updateString);
			
			// Empty parameter set
			parameter.clear();
			
			// Execute SQL statement
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

		
		// Return element ID
		return elementID;
	}
	
	
	/**
	 * Create a new root table in SQL database
	 */
	public void createRootTable() {
		// SQL command
		String sqlCommandString = "CREATE TABLE elements."+sqlTableID+" (NextElementID int, ElementPrefix varchar(255));";
		DynamicSQLUpdate dynCmd = new DynamicSQLUpdate(sqlURL, sqlUser, sqlPass, sqlPrefix, sqlDriver, sqlCommandString);
		
		// Parameter for SQL command statement
		Map<String, Object> parameter = new HashMap<>();

		// Execute SQL statement
		dynCmd.accept(parameter);
		
		
		// Initially fill table
		String sqlInsertString = "INSERT INTO elements."+sqlTableID+" (NextElementID, ElementPrefix) VALUES ('1', '"+sqlTableID+":')";
		DynamicSQLUpdate dynUpdate = new DynamicSQLUpdate(sqlURL, sqlUser, sqlPass, sqlPrefix, sqlDriver, sqlInsertString);

		// Clear parameter
		parameter.clear();
		
		// Run SQL operation
		dynUpdate.accept(parameter);
	}

	
	
	/**
	 * Create a new map element table in SQL database
	 */
	public SQLMap createMap(int elementID) {
		// SQL command
		String sqlCommandString = "CREATE TABLE elements."+sqlTableID+"__"+elementID+" (name text, type int, value text);";
		DynamicSQLUpdate dynCmd = new DynamicSQLUpdate(sqlURL, sqlUser, sqlPass, sqlPrefix, sqlDriver, sqlCommandString);
		
		// Parameter for SQL command statement
		Map<String, Object> parameter = new HashMap<>();

		// Execute SQL statement
		dynCmd.accept(parameter);
		
		// Return created map
		return new SQLMap(this, elementID);
	}

	
	/**
	 * Create a new collection element table in SQL database
	 */
	public SQLCollection createCollection(int elementID) {
		// SQL command
		String sqlCommandString = "CREATE TABLE elements."+sqlTableID+"__"+elementID+" (type int, value text);";
		DynamicSQLUpdate dynCmd = new DynamicSQLUpdate(sqlURL, sqlUser, sqlPass, sqlPrefix, sqlDriver, sqlCommandString);
		
		// Parameter for SQL command statement
		Map<String, Object> parameter = new HashMap<>();

		// Execute SQL statement
		dynCmd.accept(parameter);
		
		// Return created collection
		return new SQLCollection(this, elementID);
	}

	
	/**
	 * Drop a root table
	 */
	public void dropRootTable() {
		// SQL command
		String sqlCommandString = "DROP TABLE elements."+sqlTableID+";";
		DynamicSQLUpdate dynCmd = new DynamicSQLUpdate(sqlURL, sqlUser, sqlPass, sqlPrefix, sqlDriver, sqlCommandString);
		
		// Parameter for SQL command statement
		Map<String, Object> parameter = new HashMap<>();

		// Execute SQL statement
		dynCmd.accept(parameter);		
	}
	
	
	/**
	 * Drop a root table
	 */
	public void dropTable(int elementID) {
		// SQL command
		String sqlCommandString = "DROP TABLE elements."+sqlTableID+"__"+elementID+";";
		DynamicSQLUpdate dynCmd = new DynamicSQLUpdate(sqlURL, sqlUser, sqlPass, sqlPrefix, sqlDriver, sqlCommandString);
		
		// Parameter for SQL command statement
		Map<String, Object> parameter = new HashMap<>();

		// Execute SQL statement
		dynCmd.accept(parameter);		
	}
}

