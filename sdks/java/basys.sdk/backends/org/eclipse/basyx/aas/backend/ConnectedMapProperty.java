package org.eclipse.basyx.aas.backend;

import java.util.Collection;
import java.util.Map;

import org.eclipse.basyx.aas.api.exception.TypeMismatchException;
import org.eclipse.basyx.aas.api.resources.basic.IMapProperty;
import org.eclipse.basyx.aas.backend.connector.IBasysConnector;


/**
 * 
 * @author pschorn
 *
 */
public class ConnectedMapProperty extends ConnectedProperty implements IMapProperty  {

	public ConnectedMapProperty(String id, String submodelId, String path, String url, IBasysConnector connector, ConnectedAssetAdministrationShellManager aasMngr) {
		
		// Invoke base constructor
		super(id, submodelId, path, url, connector, aasMngr);
	}

	/**
	 * Gets Value for the given key
	 * @throws TypeMismatchException 
	 */
	@Override
	public Object getValue(String key) throws Exception {
		
		Object map = this.getElement();
		
		// Fetch value at index @objRef
		Object value = null;
		if (map instanceof Map<?,?>) {
			
			// Type safe get value for key
			value = ((Map<?,?>) map).get(key);
		} else {
			// throw type mismatch
			throw new TypeMismatchException(this.propertyPath, "Map");
		}
		
		// Return property value
		return value;
	}
	
	/**
	 * Sets a new map. Overrides existing
	 */
	@Override
	public void set(Map<String, Object> map) {
		// Post data to server
		basysConnector.basysSet(this.modelProviderURL, propertyPath, map);
		
		// update Cache
		this.setElement(map);
	}

	/**
	 * Puts a key-value pair in the map
	 */
	@Override
	public void put(Object key, Object value) {
		
		// Post data to server
		basysConnector.basysPost(this.modelProviderURL, propertyPath, "create" , key, value);
		
		// Update cache
		Object map = this.getElement();
		if (map instanceof Map<?,?>) {
			
			// Type safe cast 
			((Map) map).put(key, value);
		}
	}

	/**
	 * Gets all keys for this map
	 */
	@Override
	public Collection<Object> getKeys() {
		
		Object map = this.getElement();
		
		if (map instanceof Map<?,?>) {
			
			// Type safe get keys for map
			return (Collection<Object>) ((Map<?,?>) map).keySet();
		}
		
		return null; // throw 
		
	}

	/**
	 * Returns number of entries in map
	 */
	@Override
	public Integer getEntryCount() {
		
		Object map = this.getElement();
		
		if (map instanceof Map<?,?>) {
			
			// Type safe get size for map
			return ((Map<?,?>) map).size();
		}
		
		return null;
	}

	/**
	 * Removes key-value pair for the given key
	 */
	@Override
	public void remove(Object key) {
		
		// Post data to server
		basysConnector.basysPost(this.modelProviderURL, propertyPath, "delete" , key);
		
		// Update cache
		Object map = this.getElement();
		if (map instanceof Map<?,?>) {
			
			// Type safe remove element from map
			((Map) map).remove(key);
		}
	}

}