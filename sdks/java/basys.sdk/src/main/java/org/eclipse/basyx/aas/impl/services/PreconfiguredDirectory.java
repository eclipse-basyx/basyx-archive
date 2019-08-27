package org.eclipse.basyx.aas.impl.services;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.vab.core.IVABDirectoryService;



/**
 * Implement a preconfigured directory that is compiled into the BaSys SDK
 * 
 * @author kuhn
 *
 */
public class PreconfiguredDirectory implements IVABDirectoryService {

	
	/**
	 * Map that stores key/value mappings
	 */
	protected Map<String, String> keyToValue = new HashMap<>();
	
	
		
	
	/**
	 * Default constructor
	 */
	public PreconfiguredDirectory() {
		// Do nothing
	}
	
	
	/**
	 * Constructor that accepts initial entries
	 */
	public PreconfiguredDirectory(Map<String, String> addedValues) {
		keyToValue.putAll(addedValues);
	}
	
	
	
	/**
	 * Add a mapping to directory
	 */
	@Override
	public IVABDirectoryService addMapping(String key, String value) {
		keyToValue.put(key, value);
		
		// Return 'this' instance
		return this;
	}

	
	/**
	 * Add several mappings to directory
	 */
	public void addMappings(Map<String,String> mappings) {
		keyToValue.putAll(mappings);
	}

	
	/**
	 * Remove a mapping from directory
	 */
	@Override
	public void removeMapping(String key) {
		keyToValue.remove(key);
	}
	
	
	/**
	 * Get mappings
	 */
	@Override
	public Map<String, String> getMappings() {
		return keyToValue;
	}
	
	
	
	/**
	 * Lookup method
	 */
	@Override
	public String lookup(String id) {
		//System.out.println("RET:"+id);
		return keyToValue.get(id);
	}
}
