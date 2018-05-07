package org.eclipse.basyx.aas.api.resources.basic;




/**
 * Interface for AAS properties that carry a single value
 *  
 * @author kuhn
 *
 */
public interface ISingleProperty extends IProperty {

	
	/**
	 * Get property value
	 * 
	 * @return Property value
	 * @throws Exception
	 */
	public Object get() throws Exception;
	
	
	/**
	 * Set property value
	 */
	public void set(Object newValue);
	
	/**
	 * Move property value to given property
	 */
	public void moveTo(ISingleProperty propertyName);

}
