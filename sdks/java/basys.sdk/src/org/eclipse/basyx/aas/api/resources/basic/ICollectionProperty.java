package org.eclipse.basyx.aas.api.resources.basic;

import java.util.Collection;



/**
 * Interface for AAS properties that carry a collection
 *  
 * @author kuhn
 *
 */
public interface ICollectionProperty extends IProperty {

	
	/**
	 * Get property value
	 * 
	 * @param objectRef Referenced property whose value is queried
	 * @return Property value
	 */
	public Object get(Object objRef);
	
	
	/**
	 * Set property value
	 * 
	 * Set property value or add property to collection
	 * 
	 * @param Changed or added object
	 */
	public void set(Object newValue);


	/**
	 * Remove property from collection 
	 * 
	 * @param objectRef Property reference to be removed
	 */
	public abstract void remove(Object objectRef);


	/**
	 * Get property elements from collection
	 * 
	 * @return Collection with values
	 */
	public abstract Collection<Object> getElements();


	/**
	 * Get element count
	 * 
	 * @return Element count
	 */
	public int getElementCount();
}

