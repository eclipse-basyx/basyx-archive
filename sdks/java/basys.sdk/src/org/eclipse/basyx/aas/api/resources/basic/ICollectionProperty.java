package org.eclipse.basyx.aas.api.resources.basic;

import java.util.Collection;

import org.eclipse.basyx.aas.api.exception.ServerException;



/**
 * Interface for AAS properties that carry a collection
 *  
 * @author kuhn
 *
 */
public interface ICollectionProperty extends IProperty {

	
	/**
	 * Get value for a given index
	 * 
	 * @param objectRef Required index
	 * @return Property value
	 * @throws Exception 
	 */
	public Object get(Integer objRef) throws Exception;
	
	
	/**
	 * Add value to collection
	 * 
	 * @param Changed or added object
	 */
	public void add(Object newValue) throws Exception;
	

	/**
	 * Sets new collection value
	 * @param collection
	 * @throws ServerException 
	 */
	void set(Collection<Object> collection) throws ServerException;


	/**
	 * Remove property from collection 
	 * 
	 * @param objectRef Property reference to be removed
	 * @throws Exception 
	 */
	public abstract void remove(Object objectRef) throws Exception;


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

