package org.eclipse.basyx.aas.backend.connected.aas.submodelelement.property;

import java.util.Collection;
import java.util.Map;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.exception.TypeMismatchException;
import org.eclipse.basyx.aas.api.resources.IMapProperty;
import org.eclipse.basyx.aas.api.resources.PropertyType;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * Connects to a PropertySingleValued as specified by DAAS containing a map
 * 
 * @author schnicke
 *
 */
public class ConnectedMapProperty extends ConnectedProperty implements IMapProperty {

	public ConnectedMapProperty(String path, VABElementProxy proxy) {
		super(PropertyType.Map, path, proxy);
	}

	@Override
	public Object getValue(String key) throws TypeMismatchException, ServerException {
		return getMap().get(key);
	}

	@Override
	public void put(String key, Object value) throws ServerException {
		try {
			// check whether the value already exists and call update or create accordingly
			if(getValue(key) != null)
				getProxy().updateElementValue(constructPath(Property.VALUE + "/" + key), value);
			else
				getProxy().createElement(constructPath(Property.VALUE + "/" + key), value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void set(Map<String, Object> map) throws ServerException {
		try {
			getProxy().updateElementValue(constructPath(Property.VALUE), map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Collection<String> getKeys() throws TypeMismatchException, ServerException {
		return getMap().keySet();
	}

	@Override
	public Integer getEntryCount() throws TypeMismatchException, ServerException {
		return getMap().entrySet().size();
	}

	@Override
	public void remove(String key) throws ServerException, TypeMismatchException {
		try {
			getProxy().deleteElement(constructPath(Property.VALUE + "/" + key));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> getMap() {
		return ((Map<String, Object>) getProxy().readElementValue(constructPath(Property.VALUE)));
	}
}
