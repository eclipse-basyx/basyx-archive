package org.eclipse.basyx.aas.api.resources.basic;

import java.util.Collection;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.exception.TypeMismatchException;



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
	public Object get(Object objRef) throws ServerException;
	
	
	/**
	 * Set or override collection
	 * @param new Collection to be set
	 */
	public void set(Collection<Object> collection) throws ServerException;
	
	
	/**
	 * Add value to collection
	 * @param newValue to be added
	 * @throws TypeMismatchException 
	 * @throws Exception
	 */
	void add(Object newValue) throws ServerException, TypeMismatchException;


	/**
	 * Remove property from collection 
	 * 
	 * @param objectRef Property reference to be removed
	 * @throws ServerException 
	 */
	public abstract void remove(Object objectRef) throws ServerException;


	/**
	 * Get property elements from collection
	 * 
	 * @return Collection with values
	 */
	public abstract Collection<Object> getElements() throws ServerException;


	/**
	 * Get element count
	 * 
	 * @return Element count
	 */
	public int getElementCount() throws ServerException;



}

