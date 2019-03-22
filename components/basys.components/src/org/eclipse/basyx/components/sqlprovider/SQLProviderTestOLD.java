package org.eclipse.basyx.components.sqlprovider;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.basyx.components.sqlprovider.driver.ISQLDriver;
import org.eclipse.basyx.components.sqlprovider.driver.SQLDriver;

public class SQLProviderTestOLD {

	
	/**
	 * Define a parameter tuple (name/type)
	 * 
	 * @author kuhn
	 *
	 */
	static class Parameter {
		String name;
		String type;
		
		
		/**
		 * Constructor 
		 * 
		 * @param name  Parameter name
		 * @param type  Parameter type
		 */
		Parameter(String name, String type) {
			this.name = name;
			this.type = type;
		}
		
		
		/**
		 * Return parameter name
		 * 
		 * @return parameter name
		 */
		public String getName() {
			return name;
		}
		
		
		/**
		 * Return parameter type
		 * 
		 * @return parameter type
		 */
		public String getType() {
			return type;
		}
	}
	
	
	
	
	/**
	 * Get operation name of operation definition 
	 * 
	 * @param opDef The operation definition string
	 * @return Operation name
	 */
	public static String getOperation(String opDef) {
		// Get operation name
		return opDef.substring(0, opDef.indexOf("("));
	}
	
	
	
	/**
	 * Get parameter list of an operation definition
	 * 
	 * A parameter list contains of a name and of a type for each parameter+
	 * 
	 * @param opDef The operation definition string
	 * @return Collection of Parameter definitions
	 */
	public static Collection<Parameter> getParameter(String opDef) {
		// Return type
		LinkedList<Parameter> result = new LinkedList<>();
		
		// Extract parameter sequence
		String   callParameterStr  = opDef.substring(opDef.indexOf("(")+1, opDef.length()-1);
		String[] callParameterList = callParameterStr.split(",");
		
		// Iterate all parameter. If no parameter is given, the loop will execute once with an empty String (length = 0)
		for (String parameterDef: callParameterList) {
			// Only process strings with a length > 0
			if (parameterDef.length() == 0) continue;
			
			// Add parameter
			result.add(new Parameter(parameterDef.substring(0, parameterDef.indexOf(":")).trim(), parameterDef.substring(parameterDef.indexOf(":")+1).trim().toLowerCase()));			
		}
		
		// Return result
		return result;
	}
	
	

	/**
	 * Create a SQL string from an input SQL string with place holders in format $x with x being an integer number.
	 * 
	 * @param baseString SQL string with place holders
	 * @param parameter Parameter values that place holders are substituted for
	 * 
	 * @return SQL string with parameter instead of place holders
	 */
	public static String getSQLString(String baseString, Collection<String> parameter) {
		// Resulting SQL String
		String result = baseString;
		
		// Replace place holders with parameter
		// - Counter variable
		int counter = 1;
		// - Replace all place holders
		for (String par: parameter) {
			result = result.replace("$"+counter, par);
			counter++;
		}
		
		// Return SQL string with resolved parameter
		return result;
	}

	
	
	public static void main(String[] args) throws SQLException {
		System.out.println("Test");
		
		ISQLDriver sqlDriver = new SQLDriver("localhost:5432/basyx-sample-vibrations", "postgres", "admin", "jdbc:postgresql://", "org.postgresql.Driver");

		
		
		Collection<String> sqlQuery1Params = new LinkedList<>();
		sqlQuery1Params.add(new Integer(1).toString());
		String     sqlQuery1String = getSQLString("SELECT * FROM vibrations.sensors WHERE vibrations.sensors.sensorid='$1'", sqlQuery1Params);
		
		ResultSet result1 = sqlDriver.sqlQuery(sqlQuery1String);
		
		System.out.println(""+result1);
		System.out.println(""+result1.next());
		System.out.println("ID   : "+result1.getString("sensorid"));
		System.out.println("NAME : "+result1.getString("sensorname"));
		System.out.println(""+result1.next());

		
		
		Collection<String> sqlQuery2Params = new LinkedList<>();
		sqlQuery2Params.add("vibrations.sensors.sensorid");
		sqlQuery2Params.add(new Integer(1).toString());
		String     sqlQuery2String = getSQLString("SELECT * FROM vibrations.sensors WHERE $1='$2'", sqlQuery2Params);
		
		ResultSet result2 = sqlDriver.sqlQuery(sqlQuery2String);
		
		System.out.println(""+result2);
		System.out.println(""+result2.next());
		System.out.println("ID   : "+result2.getString("sensorid"));
		System.out.println("NAME : "+result2.getString("sensorname"));
		System.out.println(""+result2.next());


		
		String call1 = "MapString()";
		
		System.out.println("- "+getOperation(call1));
		System.out.println("- "+getParameter(call1));

		
		String call2 = "MapArray(sensorid:int, sensorname:String)";
		
		System.out.println("- "+getOperation(call2));
		System.out.println("- "+getParameter(call2));
		
	}
}
