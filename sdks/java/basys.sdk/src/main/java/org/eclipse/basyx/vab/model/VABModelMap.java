package org.eclipse.basyx.vab.model;

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
	 * Default constructor
	 */
	public VABModelMap() {
		// Do nothing
	}
	
	
	/**
	 * Wrap an existing map into a VABModelMap
	 */
	public VABModelMap(Map<String, V> wrappedContents) {
		this.putAll(wrappedContents);
	}
	
	
	/**
	 * Put element with qualified path into map. This function assumes that all intermediate elements are maps as well.
	 * 
	 * @param path path to element in contained map(s)
	 * @param value value to be put
	 */
	@SuppressWarnings("unchecked")
	public <T extends VABModelMap<? extends Object>> T putPath(String path, Object value) {
		// Current Map, start with this map and then traverse according to path
		Map<String, Object> currentMap = (Map<String, Object>) this;
		
		// Split string
		String[] pathArray = path.split("/");
		
		// Traverse path except last element
		for (int i=0; i<pathArray.length-1; i++) {
			// Get map element
			Map<String, Object> mapElement = (Map<String, Object>) currentMap.get(pathArray[i]);
			
			// Create map element if it does not exist yet
			if (mapElement == null) {
				mapElement = new VABModelMap<Object>();
				currentMap.put(pathArray[i], mapElement);
			}
			
			// Continue with map element
			currentMap = mapElement;
		}
			
		// Put element
		currentMap.put(pathArray[pathArray.length-1], value);
		
		// Return reference to this class to enable chaining of operation calls
		return (T) this;
	}
	
	
	/**
	 * Get element from qualified path <br />
	 * To retrieve the root element, use "" as path
	 * 
	 * @param path
	 *            path to element in contained map(s)
	 */
	@SuppressWarnings("unchecked")
	public Object getPath(String path) {
		if (path.isEmpty()) {
			return this;
		}

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
