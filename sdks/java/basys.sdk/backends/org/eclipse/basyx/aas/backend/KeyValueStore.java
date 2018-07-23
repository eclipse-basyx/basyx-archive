package org.eclipse.basyx.aas.backend;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.api.exception.ResourceNotFoundException;

/**
 * 
 * @author pschorn
 *
 */
public class KeyValueStore<String, T extends ConnectedElement> implements Map {
	
	/**
	 * Initialize Cache Hash map for proxies
	 */
	private HashMap<String, T>  cache_ ;
	
	/**
	 * Submodel reference to be used for unknown properties and operations
	 */
	private ConnectedSubmodel connectedSubmodel;

	/**
	 * Type url 
	 */
	private java.lang.String type;
	
	/**
	 * Constructor
	 */
	public KeyValueStore(ConnectedSubmodel submodel, java.lang.String type) {
		
		this.connectedSubmodel = submodel;
		
		this.cache_	          = new HashMap<String, T>();
		
		this.type = (java.lang.String) type;
	}
	
	/**
	 * Adds new entry in cache
	 * @param key
	 * @param proxy
	 */
	public void put(String key, T proxy) {
		
		// Add proxy to map
		this.cache_.put(key, proxy);
	}

		
	@Override
	public int size() {
		return this.cache_.size();
	}

	@Override
	public boolean isEmpty() {
		return this.cache_.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return this.cache_.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return this.cache_.containsValue(value);
	}

	/**
	 * Returns value for the given key.
	 * If the key is not in cache, query server. 
	 * If the value is still not found, throw exception
	 */
	@Override
	public Object get(Object key) {

		Object value;
		// Try to get value from cache
		if( (value = this.cache_.get(key)) != null ) return value;
		
		
		
		// Try to get value from server
		java.lang.String servicePath = this.connectedSubmodel.getConnector().buildPath(connectedSubmodel.getAASID(), connectedSubmodel.getAASSubmodelID(), (java.lang.String) key, type);
		if( (value = connectedSubmodel.getConnector().basysGet(this.connectedSubmodel.getModelProviderUrl(), servicePath)) != null) return value;
		
		throw new ResourceNotFoundException("Could not find child '"+key+"' for submodel '"+connectedSubmodel.getAASSubmodelID()+"'");
		
	}

	@Override
	public Object put(Object key, Object value) {
		return this.cache_.put((String) key, (T) value);
	}

	@Override
	public Object remove(Object key) {
		return this.cache_.remove(key);
	}

	@Override
	public void putAll(Map m) {
		this.cache_.putAll(m);
	}

	@Override
	public void clear() {
		this.cache_.clear();
	}

	@Override
	public Set keySet() {
		return this.cache_.keySet();
	}

	@Override
	public Collection values() {
		return this.cache_.values();
	}

	@Override
	public Set entrySet() {
		return this.cache_.entrySet();
	}
	
	
	
	


	
}
