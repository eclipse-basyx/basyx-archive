package org.eclipse.basyx.vab.coder.json.serialization;

import java.util.Collection;
import java.util.Map;



/**
 * Factory that controls the kind of Maps and Collections that are produced when an Object is deserialized
 * 
 * @author kuhn, espen
 *
 */
public interface GSONToolsFactory {

	
	/**
	 * Create a Map
	 */
	public Map<String, Object> createMap();
	
	
	/**
	 * Create a Collection
	 */
	public Collection<Object> createCollection();
}
