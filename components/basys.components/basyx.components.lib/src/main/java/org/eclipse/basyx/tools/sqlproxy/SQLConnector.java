package org.eclipse.basyx.tools.sqlproxy;



/**
 * Base class for classes that connect to SQL databases
 * 
 * @author kuhn
 *
 */
public abstract class SQLConnector {

	
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
	 * ID of table for this element object
	 */
	protected String sqlTableID = null;

	
	
	
	/**
	 * Constructor
	 * 
	 * @param user        SQL user name
	 * @param pass        SQL password
	 * @param url         SQL server URL
	 * @param driver      SQL driver
	 * @param prefix      JDBC SQL driver prefix
	 * @param tableID     ID of table for this element in database
	 */
	public SQLConnector(String user, String pass, String url, String driver, String prefix, String tableID) {
		// Store variables
		sqlUser   = user;
		sqlPass   = pass;
		sqlURL    = url;
		sqlDriver = driver;
		sqlPrefix = prefix;
		
		// ID of table hat contains elements of this element
		sqlTableID = tableID;
	}
}
