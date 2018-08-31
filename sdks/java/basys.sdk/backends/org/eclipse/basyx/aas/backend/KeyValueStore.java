package org.eclipse.basyx.aas.backend;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.api.exception.ResourceNotFoundException;
import org.eclipse.basyx.aas.impl.tools.BaSysID;

/**
 * 
 * @author pschorn
 *
 */
public class KeyValueStore <V extends ConnectedElement> implements Map<String, V> {

	/**
	 * Initialize Cache Hash map for proxies
	 */
	private HashMap<String, V> cache_;

	/**
	 * Submodel reference to be used for unknown properties and operations
	 */
	private ConnectedSubmodel connectedSubmodel;

	/**
	 * Type url
	 */
	private String type;

	/**
	 * Constructor
	 */
	public KeyValueStore(ConnectedSubmodel submodel, String type) {

		this.connectedSubmodel = submodel;

		this.cache_ = new HashMap<String, V>();

		this.type = type;
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
	 * Returns value for the given key. If the key is not in cache, query server. If
	 * the value is still not found, throw exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public V get(Object key) {

		V value;
		// Try to get value from cache
		if ((value = this.cache_.get(key)) != null)
			return value;

		// Try to get value from server
		java.lang.String servicePath = BaSysID.instance.buildPath(connectedSubmodel.getAASID(), connectedSubmodel.getAASSubmodelID(), (java.lang.String) key, type);
		if ((value = (V) connectedSubmodel.getProvider().getModelPropertyValue(servicePath)) != null)
			return value;

		throw new ResourceNotFoundException("Could not find child '" + key + "' for submodel '" + connectedSubmodel.getAASSubmodelID() + "'");

	}

	@Override
	public V put(String key, V value) {
		return this.cache_.put(key, value);
	}

	@Override
	public V remove(Object key) {
		return this.cache_.remove(key);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void putAll(Map m) {
		this.cache_.putAll(m);
	}

	@Override
	public void clear() {
		this.cache_.clear();
	}

	@Override
	public Set<String> keySet() {
		return this.cache_.keySet();
	}

	@Override
	public Collection<V> values() {
		return this.cache_.values();
	}

	@Override
	public Set<Entry<String,V>> entrySet() {
		return this.cache_.entrySet();
	}

}
