package org.eclipse.basyx.aas.backend.connected.property;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.resources.ISingleProperty;
import org.eclipse.basyx.aas.api.resources.PropertyType;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * Connects to a PropertySingleValued as specified by VWiD containing a simple
 * value
 * 
 * @author schnicke
 *
 */
public class ConnectedSingleProperty extends ConnectedProperty implements ISingleProperty {

	public ConnectedSingleProperty(String path, VABElementProxy proxy) {
		super(PropertyType.Single, path, proxy);
	}

	@Override
	public Object get() throws Exception {
		return getProxy().readElementValue(constructPath("value"));
	}

	@Override
	public void set(Object newValue) throws ServerException {
		try {
			getProxy().updateElementValue(constructPath("value"), newValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void moveTo(ISingleProperty propertyName) throws ServerException {
		// TODO Auto-generated method stub
	}

}
