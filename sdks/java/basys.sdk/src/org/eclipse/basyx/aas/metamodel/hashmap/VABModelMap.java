package org.eclipse.basyx.aas.metamodel.hashmap;

import java.util.HashMap;
import java.util.Map;


/**
 * Base class for all hash maps that contain VAB meta models
 * 
 * Subclasses contain meta model structures for the virtual automation bus. They may be copied during instantiation and when creating facades.
 * Implementations need to support this behavior.
 * 
 * @author kuhn
 *
 */
public class VABModelMap<V extends Object> extends HashMap<String, V> {

	
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;
	
	
	

	
	/**
	 * Put element with qualified path into map. This function assumes that all intermediate elements are maps as well.
	 * 
	 * @param path path to element in contained map(s)
	 * @param value value to be put
	 */
	@SuppressWarnings("unchecked")
	public void putPath(String path, Object value) {
		// Current Map, start with this map and then traverse according to path
		Map<String, Object> currentMap = (Map<String, Object>) this;
		
		// Split string
		String[] pathArray = path.split("/");
		
		// Traverse path except last element
		for (int i=0; i<pathArray.length-1; i++) currentMap = (Map<String, Object>) currentMap.get(pathArray[i]);
			
		// Put element
		currentMap.put(pathArray[pathArray.length-1], value);
	}
	
	
	/**
	 * Get element from qualified path
	 * 
	 * @param path path to element in contained map(s)
	 */
	@SuppressWarnings("unchecked")
	public Object getPath(String path) {
		// Current Map, start with this map and then traverse according to path
		Map<String, Object> currentMap = (Map<String, Object>) this;
		
		// Split string
		String[] pathArray = path.split("/");
		
		// Traverse path except last element
		for (int i=0; i<pathArray.length-1; i++) currentMap = (Map<String, Object>) currentMap.get(pathArray[i]);
			
		// Put element
		return currentMap.get(pathArray[pathArray.length-1]);
	}	
}
