package org.eclipse.basyx.aas.api.resources.basic;

import java.util.Collection;



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
	public Object getValue(Object key);
	
	
	/**
	 * put entry in map
	 */
	public void put(Object key, Object value);
	
	/**
	 * Get map keys
	 * 
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

