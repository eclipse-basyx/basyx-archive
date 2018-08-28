package org.eclipse.basyx.aas.impl.resources.basic;

import java.util.Collection;
import java.util.Map;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.exception.TypeMismatchException;
import org.eclipse.basyx.aas.api.resources.basic.IMapProperty;

public class MapProperty extends Property implements IMapProperty {

	Map<Object, Object> map;

	public MapProperty(Map<Object, Object> map) {
		super();
		this.map = map;
		setMap(true);
	}

	@Override
	public Object getValue(Object key) throws TypeMismatchException {
		return map.get(key);
	}

	@Override
	public void put(Object key, Object value) throws ServerException {
			map.put(key, value);
	}

	@Override
	public void set(Map<Object, Object> newMap) throws ServerException {
		this.map.clear();
		for(Object key : newMap.keySet()) {
			this.map.put(key, newMap.get(key));
		}
	}

	@Override
	public Collection<Object> getKeys() throws TypeMismatchException {
		return map.keySet();
	}

	@Override
	public Integer getEntryCount() throws TypeMismatchException {
		return map.entrySet().size();
	}

	@Override
	public void remove(Object key) throws ServerException, TypeMismatchException {
		map.remove(key);
	}

}
