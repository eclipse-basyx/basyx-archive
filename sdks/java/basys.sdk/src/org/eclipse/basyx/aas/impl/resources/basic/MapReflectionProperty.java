package org.eclipse.basyx.aas.impl.resources.basic;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.exception.TypeMismatchException;
import org.eclipse.basyx.aas.api.resources.basic.IMapProperty;

/**
 * Provides reflective access to a map based on a property
 * @author schnicke
 *
 */
public class MapReflectionProperty extends AbstractReflectionProperty implements IMapProperty {

	public MapReflectionProperty(Field f, Object o) {
		super(f, o);
	}

	@Override
	public Object getValue(Object key) throws TypeMismatchException, ServerException {
		return getMap().get(key);
	}

	@Override
	public void put(Object key, Object value) throws ServerException {
		getMap().put(key, value);
	}

	@Override
	public void set(Map<Object, Object> map) throws ServerException {
		try {
			f.set(getObject(), map);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			throw new ServerException("Reflection failed");
		}
	}

	@Override
	public Collection<Object> getKeys() throws TypeMismatchException, ServerException {
		return getMap().keySet();
	}

	@Override
	public Integer getEntryCount() throws TypeMismatchException, ServerException {
		return getMap().size();
	}

	@Override
	public void remove(Object key) throws ServerException, TypeMismatchException {
		getMap().remove(key);
	}

	@SuppressWarnings("unchecked")
	private Map<Object, Object> getMap() throws ServerException {
		try {
			return (Map<Object, Object>) f.get(o);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			throw new ServerException("Reflection failed");
		}
	}

}
