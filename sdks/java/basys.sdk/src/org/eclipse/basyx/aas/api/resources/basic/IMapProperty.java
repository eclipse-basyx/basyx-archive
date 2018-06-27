package org.eclipse.basyx.aas.api.resources.basic;

import java.util.Collection;
import java.util.Map;



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
	 */
	public void put(Object key, Object value);
	
	/**
	 * Set new map object
	 * @param map
	 */
	void set(Map<String, Object> map);
	
	/**
	 * Get map keys
	 * @return Collection with keys
	 */
	public Collection<Object> getKeys();
	
	/**
	 * Get entry count
	 */
	public Integer getEntryCount();
	
	/**
	 * Remove entry 
	 */
	public void remove(Object key);



	
}

