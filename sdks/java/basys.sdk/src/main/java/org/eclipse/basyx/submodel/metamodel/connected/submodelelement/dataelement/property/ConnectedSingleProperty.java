package org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.property;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.property.ISingleProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.property.PropertyType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.vab.exception.ServerException;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * Connects to a PropertySingleValued as specified by DAAS containing a simple
 * value
 * 
 * @author schnicke
 *
 */
public class ConnectedSingleProperty extends ConnectedProperty implements ISingleProperty {

	public ConnectedSingleProperty(VABElementProxy proxy) {
		super(PropertyType.Single, proxy);
	}

	@Override
	public Object get() throws Exception {
		return retrieveObject();
	}

	@Override
	public void set(Object newValue) throws ServerException {
		getProxy().setModelPropertyValue(Property.VALUE, newValue);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public String getValueType() {
		return (String) ((Map<String, Object>) getProxy().getModelPropertyValue("")).get(Property.VALUETYPE);
	}

}
