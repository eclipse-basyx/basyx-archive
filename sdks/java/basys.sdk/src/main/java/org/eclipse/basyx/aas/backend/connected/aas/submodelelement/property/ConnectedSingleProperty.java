package org.eclipse.basyx.aas.backend.connected.aas.submodelelement.property;

import java.util.Map;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.ISingleProperty;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.PropertyType;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.SingleProperty;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

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
		Object value = getProxy().getModelPropertyValue(SingleProperty.VALUE);

		// unpack c# value
		if (value instanceof Map<?, ?>) {
			if (((Map<?, ?>) value).get(SingleProperty.VALUETYPE) != null && ((Map<?, ?>) value).get(SingleProperty.VALUE) != null) {
				value = ((Map<?, ?>) value).get(SingleProperty.VALUE);
			}
		}

		return value;
	}

	@Override
	public void set(Object newValue) throws ServerException {
		getProxy().setModelPropertyValue(SingleProperty.VALUE, newValue);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public String getValueType() {
		return (String) ((Map<String, Object>) getProxy().getModelPropertyValue("")).get(SingleProperty.VALUETYPE);
	}

}
