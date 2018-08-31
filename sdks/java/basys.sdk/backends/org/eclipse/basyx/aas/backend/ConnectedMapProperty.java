package org.eclipse.basyx.aas.backend;

import java.util.Collection;
import java.util.Map;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.exception.TypeMismatchException;
import org.eclipse.basyx.aas.api.resources.basic.IMapProperty;
import org.eclipse.basyx.aas.api.services.IModelProvider;

/**
 * 
 * @author pschorn
 *
 */
public class ConnectedMapProperty extends ConnectedProperty implements IMapProperty {

	public ConnectedMapProperty(String id, String submodelId, String path, IModelProvider provider, ConnectedAssetAdministrationShellManager aasMngr) {

		// Invoke base constructor
		super(id, submodelId, path, provider, aasMngr);
	}

	/**
	 * Get value by key
	 * 
	 * @throws TypeMismatchException
	 */
	@Override
	public Object getValue(Object key) throws TypeMismatchException {

		Object map = this.getElement();

		// Fetch value for the given key
		Object value = null;
		if (map instanceof Map<?, ?>) {

			// Type safe get value for key
			value = ((Map<?, ?>) map).get(key);
		} else {
			// throw type mismatch
			throw new TypeMismatchException(this.propertyPath, "Map");
		}

		// Return property value
		return value;
	}

	/**
	 * Put new Entry in map
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void put(Object key, Object value) throws ServerException {

		// Add entry to map
		try {
			provider.setModelPropertyValue(propertyPath, key, value);

			// Update cache
			Object map = this.getElement();
			if (map instanceof Map<?, ?>) {

				// Type safe cast
				((Map) map).put(key, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerException("Exception: " + e.toString());
		}

	}

	/**
	 * Sets new map. Overwrites existing values
	 * 
	 * @param collection
	 * @throws ServerException
	 */
	@Override
	public void set(Map<Object, Object> map) throws ServerException {
		try {
			provider.setModelPropertyValue(propertyPath, map);

			// update Cache
			this.setElement(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerException("Exception: " + e.toString());
		}
	}

	/**
	 * Gets the keyset of this map
	 * 
	 * @return keyset
	 * @throws TypeMismatchException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Object> getKeys() throws TypeMismatchException {

		Object map = this.getElement();

		if (map instanceof Map<?, ?>) {

			// Type safe get keys for map
			return (Collection<Object>) ((Map<?, ?>) map).keySet();
		} else {
			// throw type mismatch
			throw new TypeMismatchException(this.propertyPath, "Map");
		}

	}

	/**
	 * @return entry count
	 * @throws TypeMismatchException
	 */
	@Override
	public Integer getEntryCount() throws TypeMismatchException {

		Object map = this.getElement();

		if (map instanceof Map<?, ?>) {

			// Type safe get size for map
			return ((Map<?, ?>) map).size();
		} else {
			// throw type mismatch
			throw new TypeMismatchException(this.propertyPath, "Map");
		}

	}

	/**
	 * Remove entry from map by key
	 * 
	 * @throws TypeMismatchException
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void remove(Object key) throws ServerException, TypeMismatchException {

		// Delete entry from map
		try {
			provider.deleteValue(propertyPath, key);
			// Update cache
			Object map = this.getElement();
			if (map instanceof Map<?, ?>) {

				// Type safe remove element from map
				((Map) map).remove(key);
			} else {
				// throw type mismatch
				throw new TypeMismatchException(this.propertyPath, "Map");
			}
		} catch (Exception e) {
			if (e instanceof TypeMismatchException) {
				throw (TypeMismatchException) e;
			} else {
				e.printStackTrace();
				throw new ServerException("Exception: " + e.toString());
			}
		}

	}

}