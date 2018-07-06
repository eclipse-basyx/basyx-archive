package org.eclipse.basyx.components.sqlprovider.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;



/**
 * Access SQL database
 * 
 * @author kuhn
 *
 */
public class SQLDriver implements ISQLDriver {

	
	/**
	 * Store user name
	 */
	protected String userName = null;
	
	/**
	 * Store password
	 */
	protected String password = null;

	/**
	 * Store path to database server
	 */
	protected String dbPath = null;
	
	
	/**
	 * Store query prefix
	 */
	protected String queryPrefix = null;
	
	
	/**
	 * Store driver class (with package name)
	 */
	protected String qualDriverClass = null;
	

	
	
	
	/**
	 * Create a SQL driver and a SQL connection
	 */
	public SQLDriver(String path, String user, String pass, String qryPfx, String qDrvCls) {
		// Store parameter
		userName        = user;
		password        = pass;
		dbPath          = path;
		queryPrefix     = qryPfx;
		qualDriverClass = qDrvCls;
		
		// This will load the MySQL driver, each DB has its own driver
		//try {Class.forName("com.mysql.cj.jdbc.Driver");} catch (ClassNotFoundException e) {e.printStackTrace();}
		try {Class.forName(qualDriverClass);} catch (ClassNotFoundException e) {e.printStackTrace();}
	}
	
	
	
	/**
	 * Execute a SQL query
	 */
	public ResultSet sqlQuery(String queryString) {
		// Store connection, SQL statement, and result
		Connection connect = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		
		// Access database
		try {
			// Setup the connection with the DB, specify database, user name and password
			connect = DriverManager.getConnection(queryPrefix+dbPath, userName, password);

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();

			// ResultSet gets the result of the SQL query
			resultSet = statement.executeQuery(queryString);			
		} catch (Exception e) {
			// Print exception to console
			e.printStackTrace();
		}

		
		// Return result of query
		return resultSet;
	}
	
	
	
	/**
	 * Execute a SQL update
	 */
	public void sqlUpdate(String updateString) {
		// Store connection, SQL statement, and result
		Connection connect = null;
		Statement statement = null;
		
		
		// Access database
		try {
			// Setup the connection with the DB, specify database, user name and password
			connect = DriverManager.getConnection(queryPrefix+dbPath, userName, password);

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();

			// ResultSet gets the result of the SQL query
			statement.executeUpdate(updateString);
			
			// Close database connection
			connect.close();
			statement.close();
		} catch (Exception e) {
			// Print exception to console
			e.printStackTrace();
		}
	}
}
