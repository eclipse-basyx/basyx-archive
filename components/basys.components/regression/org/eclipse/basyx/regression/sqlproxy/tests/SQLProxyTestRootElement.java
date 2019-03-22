package org.eclipse.basyx.regression.sqlproxy.tests;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.tools.sqlproxy.SQLRootElement;



/**
 * Test SQL root element implementation, its creation, and dropping
 * 
 * @author kuhn
 *
 */
public class SQLProxyTestRootElement {

	
	/**
	 * Store SQL root element reference
	 * - An SQL root element is the main gateway to a SQL database
	 */
	protected SQLRootElement sqlRootElement = null;

	
	
	/**
	 * Test basic operations
	 */
	@Test
	public void test() throws Exception {
		// Create SQL root element
		sqlRootElement = new SQLRootElement("postgres", "admin", "//localhost/basyx-map?", "org.postgresql.Driver", "jdbc:postgresql:", "root_el_01");
		// - Create new table in database for root element
		sqlRootElement.createRootTable();
		
		// Get element IDs
		assertTrue(sqlRootElement.getNextIdentifier() == 1);
		assertTrue(sqlRootElement.getNextIdentifier() == 2);
		
		// Create map
		sqlRootElement.createMap(0);
		
		// Create collection
		sqlRootElement.createCollection(1);
		

		// Drop tables
		sqlRootElement.dropTable(0);
		sqlRootElement.dropTable(1);

		// Drop table for root element (= delete it)
		sqlRootElement.dropRootTable();
	}
}
