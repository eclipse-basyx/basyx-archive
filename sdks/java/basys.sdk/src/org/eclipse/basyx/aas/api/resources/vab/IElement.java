package org.eclipse.basyx.aas.api.resources.vab;

import java.util.Map;




/**
 * Base interface for all VAB elements
 *  
 * @author kuhn
 *
 */
public interface IElement {

	
	/**
	 * Return the unique ID that identifies an VAB element
	 * 
	 * @return unique ID
	 */
	public String getId();

	
	/**
	 * Set the ID of an element
	 * 
	 * @param id New/updated element id
	 */
	public void setId(String id);

	
	
	
	/**
	 * Get IElement properties
	 * 
	 * @return IElement properties
	 */
	public Map<String, IProperty> getProperties();
	
	
	/**
	 * Get IElement operations
	 * 
	 * @return IElement operations
	 */
	public Map<String, IOperation> getOperations();
}


