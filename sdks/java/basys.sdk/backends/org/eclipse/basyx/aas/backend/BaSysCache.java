package org.eclipse.basyx.aas.backend;

import java.util.Map;

import org.eclipse.basyx.aas.api.resources.basic.IOperation;
import org.eclipse.basyx.aas.api.resources.basic.IProperty;

/**
 * 
 * @author pschorn
 *
 */
public class BaSysCache<T extends ConnectedElement> {
	
	/**
	 * Initialize Cache Hash map for proxies
	 */
	private KeyValueStore<String, T>  cache_ ;
	
	
	
	/**
	 * Initialize cache
	 */
	public BaSysCache(ConnectedSubmodel submodel, String type) {
		
		this.cache_	    = new KeyValueStore<String, T>(submodel, type);
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

	
	/**
	 * Returns casted hashmap reference
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, IProperty> getProperties(){
		return (Map<String, IProperty>) this.cache_;
	}
	
	/**
	 * Returns core hashmap reference
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, IOperation> getOperations(){
		return (Map<String, IOperation>) this.cache_;
	}
	
}
