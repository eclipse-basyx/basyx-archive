package org.eclipse.basyx.components.sqlprovider.query;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.impl.provider.javahandler.genericsm.GenericHandlerOperation;
import org.eclipse.basyx.components.sqlprovider.driver.ISQLDriver;
import org.eclipse.basyx.components.sqlprovider.driver.SQLDriver;
import org.eclipse.basyx.components.tools.propertyfile.opdef.OperationDefinition;
import org.eclipse.basyx.components.tools.propertyfile.opdef.Parameter;
import org.eclipse.basyx.components.tools.propertyfile.opdef.ResultFilter;



/**
 * Implement a generic SQL query
 * 
 * @author kuhn
 *
 */
public class DynamicSQLQuery {

	
	/**
	 * Store SQL driver instance
	 */
	protected ISQLDriver sqlDriver = null;
	
	
	/**
	 * Store expected number of parameter
	 */
	protected int expectedParameterCount;
	
	
	/**
	 * Store SQL query string with place holders ($x)
	 */
	protected String sqlQueryString = null;
	
	
	/**
	 * Store SQL result filter
	 */
	protected String resultFilterString = null;
	
	
	
	
	
	/**
	 * Constructor
	 */
	public DynamicSQLQuery(String path, String user, String pass, String qryPfx, String qDrvCls, int parameterCount, String query, String sqlResultFilter) {
		// Create SQL driver instance
		sqlDriver = new SQLDriver(path, user, pass, qryPfx, qDrvCls);
		
		// Store parameter count and SQL query string
		expectedParameterCount = parameterCount;
		sqlQueryString         = query;
		resultFilterString     = sqlResultFilter;
	}
	
	
	
	/**
	 * Get method parameter definition
	 */
	protected Class<?>[] getMethodParameter(Collection<Parameter> parameter) {
		// Store operation signature
		Class<?>[] result = new Class<?>[2];
		
		// Operation signature is ResultSet and a list of string parameter that define column names
		result[0] = ResultSet.class;
		result[1] = Object[].class;
		
		// Return signature
		return result;
	}
	
	
	/**
	 * Get column names
	 */
	protected Collection<String> getColumnNames(Collection<Parameter> parameter) {
		Collection<String> result = new LinkedList<>();
		
		for (Parameter par: parameter) result.add(par.getName());
		
		return result;
	}

	
	/**
	 * Execute query without parameter
	 */
	public Object runQuery() {
		// Execute SQL query
		ResultSet sqlResult = sqlDriver.sqlQuery(sqlQueryString);

		// Extract input parameter definition
		Collection<Parameter> parameter = OperationDefinition.getParameter(resultFilterString);

		// Process result
		try {
			// Create inner parameter array for call
			Object[] callParameterInner = new Object[parameter.size()];
			int i=0; for (String column: getColumnNames(parameter)) callParameterInner[i++]=column;

			// Create parameter array for call
			Object[] callParameter = new Object[2];
			callParameter[0] = sqlResult;
			callParameter[1] = callParameterInner;

			// Invoke result filter operation using static invocation
			return ResultFilter.class.getMethod(OperationDefinition.getOperation(resultFilterString), getMethodParameter(parameter)).invoke(null, callParameter);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// No result
		return null;
	}


	/**
	 * Execute query with given parameter
	 */
	public Object runQuery(Object[] parameter) {
		// Create list of query parameter
		Collection<String> sqlQueryParameter = new LinkedList<>();
		// - Add parameter
		for (Object par: parameter) sqlQueryParameter.add(par.toString());
		
		// Apply parameter and create SQL query string
		String sqlQuery = OperationDefinition.getSQLString(sqlQueryString, sqlQueryParameter);
		
		System.out.println("Running SQL query:"+sqlQuery);

		// Execute SQL query
		ResultSet sqlResult = sqlDriver.sqlQuery(sqlQuery);
		
		// Extract input parameter definition
		Collection<Parameter> resultParameter = OperationDefinition.getParameter(resultFilterString);

		// Process result
		try {
			// Create inner parameter array for call
			Object[] callParameterInner = new Object[resultParameter.size()];
			int i=0; for (String column: getColumnNames(resultParameter)) callParameterInner[i++]=column;

			// Create parameter array for call
			Object[] callParameter = new Object[2];
			callParameter[0] = sqlResult;
			callParameter[1] = callParameterInner;
			
			// Invoke result filter operation using static invocation
			return ResultFilter.class.getMethod(OperationDefinition.getOperation(resultFilterString), getMethodParameter(resultParameter)).invoke(null, callParameter);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// No result
		return null;
	}


	/**
	 * Execute update with given parameter
	 */
	public void runUpdate(Object[] parameter) {
		// Create list of query parameter
		Collection<String> sqlQueryParameter = new LinkedList<>();
		// - Add parameter
		for (Object par: parameter) sqlQueryParameter.add(par.toString());
		
		// Apply parameter and create SQL query string
		String sqlQuery = OperationDefinition.getSQLString(sqlQueryString, sqlQueryParameter);
		
		System.out.println("Running SQL update:"+sqlQuery);

		// Execute SQL query
		sqlDriver.sqlUpdate(sqlQuery);
	}
}

