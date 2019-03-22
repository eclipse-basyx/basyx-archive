package org.eclipse.basyx.components.tools.propertyfile.opdef;



/**
 * Define a parameter tuple (name/type)
 * 
 * @author kuhn
 *
 */
public class Parameter {


	/**
	 * Parameter name
	 */
	protected String name;
	
	
	/**
	 * Parameter type
	 */
	protected String type;


	/**
	 * Constructor 
	 * 
	 * @param name  Parameter name
	 * @param type  Parameter type
	 */
	public Parameter(String name, String type) {
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
