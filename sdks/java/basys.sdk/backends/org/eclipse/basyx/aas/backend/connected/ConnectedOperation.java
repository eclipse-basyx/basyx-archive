package org.eclipse.basyx.aas.backend.connected;

import java.util.List;

import org.eclipse.basyx.aas.api.resources.IOperation;
import org.eclipse.basyx.aas.api.resources.ParameterType;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.enums.DataObjectType;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

public class ConnectedOperation extends ConnectedElement implements IOperation {

	public ConnectedOperation(String path, VABElementProxy proxy) {
		super(path, proxy);
	}

	@Override
	public List<ParameterType> getParameterTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataObjectType getReturnDataType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object invoke(Object... params) throws Exception {
		// FIXME: endpoint contains a string and invokable is not explicitly in the
		// meta model
		return getProxy().invoke(constructPath("invokable"), params);
	}

}
