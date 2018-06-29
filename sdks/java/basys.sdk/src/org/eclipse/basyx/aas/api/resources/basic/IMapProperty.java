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
	 */
	public Object getValue(String key) throws Exception;
	
	/**
	 * put entry in map
	 * @throws Exception 
	 */
	public void put(Object key, Object value) throws Exception;
	
	/**
	 * Set new map object
	 * @param map
	 * @throws ServerException 
	 */
	void set(Map<String, Object> map) throws ServerException;
	
	/**
	 * Get map keys
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
	 * @throws Exception 
	 */
	public void remove(Object key) throws Exception;



	
}

