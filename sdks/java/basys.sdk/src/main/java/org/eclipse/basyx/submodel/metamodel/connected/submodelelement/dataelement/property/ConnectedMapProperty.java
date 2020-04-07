package org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.property;

import java.util.Collection;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.property.IMapProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.property.PropertyType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.vab.exception.TypeMismatchException;
import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Connects to a PropertySingleValued as specified by DAAS containing a map
 * 
 * @author schnicke
 *
 */
public class ConnectedMapProperty extends ConnectedProperty implements IMapProperty {

	private static Logger logger = LoggerFactory.getLogger(ConnectedMapProperty.class);

	public ConnectedMapProperty(VABElementProxy proxy) {
		super(PropertyType.Map, proxy);
	}

	@Override
	public Object getValue(String key) throws TypeMismatchException, ProviderException {
		return getMap().get(key);
	}

	@Override
	public void put(String key, Object value) throws ProviderException {
		try {
			// check whether the value already exists and call update or create accordingly
			if(getValue(key) != null)
				getProxy().setModelPropertyValue(Property.VALUE + "/" + key, value);
			else
				getProxy().createValue(Property.VALUE + "/" + key, value);
		} catch (Exception e) {
			logger.error("Exception in put", e);
		}
	}

	@Override
	public void set(Map<String, Object> map) throws ProviderException {
		try {
			getProxy().setModelPropertyValue(Property.VALUE, map);
		} catch (Exception e) {
			logger.error("Exception in set", e);
		}
	}

	@Override
	public Collection<String> getKeys() throws TypeMismatchException, ProviderException {
		return getMap().keySet();
	}

	@Override
	public Integer getEntryCount() throws TypeMismatchException, ProviderException {
		return getMap().entrySet().size();
	}

	@Override
	public void remove(String key) throws ProviderException, TypeMismatchException {
		try {
			getProxy().deleteValue(Property.VALUE + "/" + key);
		} catch (Exception e) {
			logger.error("Exception in remove", e);
		}
	}

	private Map<String, Object> getMap() {
		return retrieveObject();
	}
}
