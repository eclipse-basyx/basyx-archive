package org.eclipse.basyx.aas.backend.connected.aas.submodelelement.property;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.resources.ISingleProperty;
import org.eclipse.basyx.aas.api.resources.PropertyType;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * Connects to a PropertySingleValued as specified by DAAS containing a simple
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



}
