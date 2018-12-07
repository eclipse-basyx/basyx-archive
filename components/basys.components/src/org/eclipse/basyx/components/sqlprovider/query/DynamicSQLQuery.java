package org.eclipse.basyx.components.sqlprovider.query;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.function.Supplier;

import org.eclipse.basyx.components.tools.propertyfile.opdef.OperationDefinition;
import org.eclipse.basyx.components.tools.propertyfile.opdef.Parameter;
import org.eclipse.basyx.components.tools.propertyfile.opdef.ResultFilter;



/**
 * Implement a generic SQL query
 * 
 * @author kuhn
 *
 */
public class DynamicSQLQuery extends DynamicSQLRunner implements Supplier<Object> {

	
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
	public DynamicSQLQuery(String path, String user, String pass, String qryPfx, String qDrvCls, String query, String sqlResultFilter) {
		// Invoke base constructor
		super(path, user, pass, qryPfx, qDrvCls);
		
		// Store SQL query string and result filter
		sqlQueryString         = query;
		resultFilterString     = sqlResultFilter;
	}

	
	/**
	 * Execute query without parameter
	 */
	@Override
	public Object get() {
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
			// Print exception to console
			e.printStackTrace();
		}

		// No result
		return null;
	}
}

