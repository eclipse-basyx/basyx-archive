package org.eclipse.basyx.aas.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.basyx.aas.api.resources.basic.IOperation;
import org.eclipse.basyx.aas.api.resources.basic.IProperty;
import org.eclipse.basyx.aas.impl.reference.ElementRef;

/**
 * 
 * @author pschorn
 *
 */
public class BaSysCache<T extends ConnectedElement> {
	
	/*
	 * Initialize Cache Hash map for proxies
	 */
	private HashMap<String, T>  cache_ ;
	
	/**
	 * Initialize cache
	 */
	public BaSysCache() {
		
		this.cache_	    = new HashMap<String, T>();
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
