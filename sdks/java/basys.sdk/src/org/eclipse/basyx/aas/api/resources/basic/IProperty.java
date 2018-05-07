package org.eclipse.basyx.aas.api.resources.basic;

import org.eclipse.basyx.aas.impl.resources.basic.DataType;

/**
 * Interface for AAS properties 
 *  
 * @author kuhn
 *
 */
public interface IProperty extends IElement {

	
	/**
	 * Get property data type
	 * 
	 * @return Property data type
	 */
	public DataType getDataType();


	/**
	 * Indicate if this property is a collection
	 * 
	 * @return Collection flag
	 */
	public boolean isCollection();


	/**
	 * Indicate if this property is a map
	 * 
	 * @return Map flag
	 */
	public boolean isMap();
	

}
