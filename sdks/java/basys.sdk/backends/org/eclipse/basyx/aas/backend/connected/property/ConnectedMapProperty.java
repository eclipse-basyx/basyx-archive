package org.eclipse.basyx.aas.backend.connected.property;

import java.util.Collection;
import java.util.Map;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.exception.TypeMismatchException;
import org.eclipse.basyx.aas.api.resources.IMapProperty;
import org.eclipse.basyx.aas.api.resources.PropertyType;
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
			getProxy().updateElementValue(constructPath("value/" + key), value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void set(Map<String, Object> map) throws ServerException {
		try {
			getProxy().updateElementValue(constructPath("value"), map);
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
			getProxy().deleteElement(constructPath("value/" + key));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> getMap() {
		return ((Map<String, Object>) getProxy().readElementValue(constructPath("value")));
	}

	@Override
	public void setValue(Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValueId(Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getValueId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSemanticId(Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getSemanticId() {
		// TODO Auto-generated method stub
		return null;
	}

}
