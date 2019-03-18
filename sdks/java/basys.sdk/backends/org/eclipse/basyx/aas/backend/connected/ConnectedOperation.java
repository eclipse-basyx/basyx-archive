package org.eclipse.basyx.aas.backend.connected;

import java.util.List;
import java.util.function.Function;

import org.eclipse.basyx.aas.api.resources.IOperation;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.operation.OperationVariable;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

public class ConnectedOperation extends ConnectedElement implements IOperation {

	public ConnectedOperation(String path, VABElementProxy proxy) {
		super(path, proxy);
	}

	@Override
	public Object invoke(Object... params) throws Exception {
		// FIXME: endpoint contains a string and invokable is not explicitly in the
		// meta model
		return getProxy().invoke(constructPath("invokable"), params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OperationVariable> getParameterTypes() {
		return (List<OperationVariable>) getProxy().readElementValue("in");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OperationVariable> getReturnTypes() {
		return (List<OperationVariable>) getProxy().readElementValue("out");
	}

	// TODO Based on the requirement the following functions need to be implemented later
	@Override
	public void SetParameterTypes(List<OperationVariable> in) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setReturnTypes(List<OperationVariable> out) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setInvocable(Function<Object[], Object[]> endpoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function<Object[], Object[]> getInvocable() {
		// TODO Auto-generated method stub
		return null;
	}

}
