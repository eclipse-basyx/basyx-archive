package org.eclipse.basyx.vab.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.vab.support.TypeDestroyer;

/**
* Base class for all hash maps that contain VAB meta models
* 
 * Subclasses contain meta model structures for the virtual automation bus. They
* may be copied during instantiation and when creating facades. Implementations
* need to support this behavior.<br>
* <br>
* <b>Warning:</b> equals and hashcode are overwritten so that classes extending
* maps with equal content are always seen as producting the same hashcode/being
* equal
* 
 * @author kuhn
*
*/

public class VABModelMap<V extends Object> implements Map<String, V> {
	Map<String, V> map;

	/**
	 * Default constructor
	 */
	public VABModelMap() {
		map = new HashMap<>();
	}

	public void setMap(Map<String, V> map) {
		this.map = map;
	}

	/**
	 * Wrap an existing map into a VABModelMap
	 */
	public VABModelMap(Map<String, V> wrappedContents) {
		this();
		this.putAll(wrappedContents);
	}

	/**
	 * Put element with qualified path into map. This function assumes that all
	 * intermediate elements are maps as well.
	 * 
	 * @param path
	 *            path to element in contained map(s)
	 * @param value
	 *            value to be put
	 */
	@SuppressWarnings("unchecked")
	public <T extends VABModelMap<? extends Object>> T putPath(String path, Object value) {
		// Current Map, start with this map and then traverse according to path
		Map<String, Object> currentMap = (Map<String, Object>) this;

		// Split string
		String[] pathArray = path.split("/");

		// Traverse path except last element
		for (int i = 0; i < pathArray.length - 1; i++) {
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
		currentMap.put(pathArray[pathArray.length - 1], value);

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
		for (int i = 0; i < pathArray.length - 1; i++)
			currentMap = (Map<String, Object>) currentMap.get(pathArray[i]);

		// Put element
		return currentMap.get(pathArray[pathArray.length - 1]);
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public V get(Object key) {
		return map.get(key);
	}

	@Override
	public V put(String key, V value) {
		return map.put(key, value);
	}

	@Override
	public V remove(Object key) {
		return map.remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends V> m) {
		map.putAll(m);
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public Set<String> keySet() {
		return map.keySet();
	}

	@Override
	public Collection<V> values() {
		return map.values();
	}

	@Override
	public Set<Entry<String, V>> entrySet() {
		return map.entrySet();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		// Use the type destroyer to ensure that classes extending maps produce the same
		// hashcode.
		Map<String, Object> thisMap = TypeDestroyer.destroyType((Map<String, Object>) map);
		result = prime * result + ((thisMap == null) ? 0 : thisMap.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		// Use the type destroyer to ensure that classes extending maps are still seen
		// as equal
		VABModelMap<Object> otherVAB = (VABModelMap<Object>) obj;
		Map<String, Object> otherMap = TypeDestroyer.destroyType(otherVAB);

		if (map == null) {
			if (otherMap != null)
				return false;
		} else {
			Map<String, Object> thisMap = TypeDestroyer.destroyType((Map<String, Object>) map);
			if (!thisMap.equals(otherMap)) {
				return false;
			}
		}
		return true;
	}
}
