package org.eclipse.basyx.aas.api.resources.basic;

import java.util.Collection;
import java.util.Map;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.exception.TypeMismatchException;



/**
 * Interface for AAS properties that carry a map
 *  
 * @author kuhn, pschorn
 *
 */
public interface IMapProperty extends IProperty {
	
	/**
	 *Get value for key
	 * @throws TypeMismatchException 
	 */
	public Object getValue(Object key) throws TypeMismatchException;
	
	
	/**
	 * put entry in map
	 * @throws ServerException 
	 */
	public void put(Object key, Object value) throws ServerException;
	
	/**
	 * Set new map or override existing
	 * @param map
	 * @throws ServerException
	 */
	void set(Map<Object, Object> map) throws ServerException;
	
	/**
	 * Get map keys
	 * 
	 * @return Collection with keys
	 * @throws TypeMismatchException 
	 */
	public Collection<Object> getKeys() throws TypeMismatchException;
	
	/**
	 * Get entry count
	 * @throws TypeMismatchException 
	 */
	public Integer getEntryCount() throws TypeMismatchException;
	
	/**
	 * Remove entry 
	 * @throws ServerException 
	 * @throws TypeMismatchException 
	 */
	public void remove(Object key) throws ServerException, TypeMismatchException;


	
}

