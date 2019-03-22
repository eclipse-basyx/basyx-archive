package org.eclipse.basyx.components.sqlprovider.query;

import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.basyx.components.sqlprovider.driver.ISQLDriver;
import org.eclipse.basyx.components.tools.propertyfile.opdef.OperationDefinition;



/**
 * Implement a generic SQL query
 * 
 * @author kuhn
 *
 */
public class DynamicSQLUpdate extends DynamicSQLRunner implements Consumer<Map<String,Object>> {


	/**
	 * Store SQL query string with place holders ($x)
	 */
	protected String sqlQueryString = null;
	
	
	
	
	/**
	 * Constructor
	 */
	public DynamicSQLUpdate(ISQLDriver driver, String query) {
		// Invoke base constructor
		super(driver);

		// Store parameter count and SQL query string
		sqlQueryString         = query;
	}

	
	/**
	 * Constructor
	 */
	public DynamicSQLUpdate(String path, String user, String pass, String qryPfx, String qDrvCls, String query) {
		// Invoke base constructor
		super(path, user, pass, qryPfx, qDrvCls);
		
		// Store parameter count and SQL query string
		sqlQueryString         = query;
	}
	


	/**
	 * Execute update with given parameter
	 */
	@Override
	public void accept(Map<String,Object> parameter) {
		System.out.println("************************* Running SQL update:"+parameter);

		// Apply parameter and create SQL query string
		String sqlQuery = OperationDefinition.getSQLString(sqlQueryString, parameter);
		
		System.out.println("Running SQL update:"+sqlQuery);

		// Execute SQL query
		sqlDriver.sqlUpdate(sqlQuery);
	}
}

