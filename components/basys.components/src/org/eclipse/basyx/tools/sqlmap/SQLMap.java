package org.eclipse.basyx.tools.sqlmap;

import java.util.Collection;
import java.util.Map;
import java.util.Set;



/**
 * This class implements a map that mirrors its contents into a SQL database
 * 
 * - The map represents the root map that is linked into the SQL database as one table
 * - Subsequent maps map to subsequent SQL tables
 * 
 * A SQL table has the following structure:
 * - ElementNAme | TypeID | serialized value
 *  
 * @author kuhn
 *
 */
public class SQLMap implements Map<String, Object> {

	
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
	 * ID of table for this hash map object
	 */
	protected String sqlTableID = null;
	
	
	/**
	 * ID of root table in SQL database. The root table contains management data.
	 */
	protected String sqlRootTableID = null;
	
	
	
	
	
	/**
	 * Constructor
	 * 
	 * @param user        SQL user name
	 * @param pass        SQL password
	 * @param url         SQL server URL
	 * @param driver      SQL driver
	 * @param prefix      JDBC SQL driver prefix
	 * @param tableID     ID of table for this map in database
	 * @param rootTableID ID of root table in database
	 */
	public SQLMap(String user, String pass, String url, String driver, String prefix, String tableID, String rootTableID) {
		// Store variables
		sqlUser   = user;
		sqlPass   = pass;
		sqlURL    = url;
		sqlDriver = driver;
		sqlPrefix = prefix;
		
		// ID of table hat contains elements of this map
		sqlTableID = tableID;
		
		// ID of root table that contains maintenance fields
		sqlRootTableID = rootTableID;
	}
	
	
	
	/**
	 * Get number of map elements
	 */
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object get(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object put(String key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<String> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Object> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

}
