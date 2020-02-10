package org.eclipse.basyx.components.sqlprovider.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Access SQL database
 * 
 * @author kuhn
 *
 */
public class SQLDriver implements ISQLDriver {
	private static Logger logger = LoggerFactory.getLogger(SQLDriver.class);

	
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
	 * JDBC connection
	 */
	protected Connection connect = null;
	

	
	
	
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
		try {
			Class.forName(qualDriverClass);
		} catch (ClassNotFoundException e) {
			logger.error("Could not init SQLDriver", e);
		}
	}
	
	
	
	/**
	 * Execute a SQL query
	 */
	public ResultSet sqlQuery(String queryString) {
		// Store SQL statement, flag that indicates whether the connection was created by this 
		// operation (and needs to be closed), and result
		Statement statement              = null;
		boolean   createdByThisOperation = false;
		ResultSet resultSet              = null;
		
		
		// Access database
		try {
			// Setup the connection with the DB, specify database, user name and password
			if (connect == null) {connect = DriverManager.getConnection(queryPrefix+dbPath, userName, password); createdByThisOperation = true;}

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();

			// ResultSet gets the result of the SQL query
			resultSet = statement.executeQuery(queryString);	

			// Close database connection
			if (createdByThisOperation) {
				connect.close();
				connect = null;
			}
		} catch (SQLException e) {
			logger.error("sqlQuery failed", e);
		}
		
		// Return result of query
		return resultSet;
	}
	
	
	
	/**
	 * Execute a SQL update
	 */
	public void sqlUpdate(String updateString) {
		// Store SQL statement
		Statement statement              = null;
		boolean   createdByThisOperation = false;

		
		// Access database
		try {
			// Setup the connection with the DB, specify database, user name and password
			if (connect == null) {
				connect = DriverManager.getConnection(queryPrefix + dbPath, userName, password);
				createdByThisOperation = true;
			}

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();

			// ResultSet gets the result of the SQL query
			statement.executeUpdate(updateString);

			if (createdByThisOperation) {
				connect.close();
				connect = null;
			}
		} catch (SQLException e) {
			logger.error("sqlUpdate failed", e);
		}
	}
	
	
	
	/**
	 * Open connection
	 */
	public void openConnection() {
		// Access database
		try {
			// Open connection
			connect = DriverManager.getConnection(queryPrefix+dbPath, userName, password);
		} catch (SQLException e) {
			logger.error("Failed to open sql driver connection", e);
		}
	}
	
	
	/**
	 * Close connection
	 */
	public void closeConnection() {
		// Access database
		try {
			// Close connection
			if (connect != null) {
				connect.close();
				connect = null;
			}
		} catch (SQLException e) {
			logger.error("Failed to close sql driver connection", e);
		}
	}
	
	
	/**
	 * Get connection
	 */
	public Connection getConnection() {
		return connect;
	}
	
	
	/**
	 * Indicate if driver has open connection
	 */
	public boolean hasOpenConnection() {
		return (connect == null);
	}
}

