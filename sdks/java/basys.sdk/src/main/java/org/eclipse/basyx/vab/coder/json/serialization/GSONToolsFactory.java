package org.eclipse.basyx.vab.coder.json.serialization;

import java.util.List;
import java.util.Map;
import java.util.Set;



/**
 * Factory that controls the kind of Maps, Sets, and Lists that is produced when a Map is deserialized
 * 
 * @author kuhn
 *
 */
public interface GSONToolsFactory {

	
	/**
	 * Create a Map
	 */
	public Map<String, Object> createMap();
	
	
	/**
	 * Create a Set
	 */
	public Set<Object> createSet();

	
	/**
	 * Create a List
	 */
	public List<Object> createList();
}
