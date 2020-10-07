package org.eclipse.basyx.tools.sql.driver;

import java.sql.ResultSet;



/**
 * Database access interface
 * 
 * @author kuhn
 *
 */
public interface ISQLDriver {

	
	/**
	 * Execute a SQL query
	 */
	public ResultSet sqlQuery(String queryString);

	
	/**
	 * Execute a SQL update
	 */
	public void sqlUpdate(String updateString);
}
