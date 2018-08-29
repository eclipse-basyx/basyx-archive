package org.eclipse.basyx.aas.impl.resources.basic;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.resources.basic.ISingleProperty;

public class SingleProperty extends Property implements ISingleProperty {

	Object obj;
	
	public SingleProperty(Object obj) {
		super();
		this.obj = obj;
	}

	@Override
	public Object get() throws Exception {
		return obj;
	}

	@Override
	public void set(Object newValue) throws ServerException {
		this.obj = newValue;
	}

	@Override
	public void moveTo(ISingleProperty propertyName) throws ServerException {
		propertyName.set(obj);
	}

}
