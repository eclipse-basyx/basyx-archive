package org.eclipse.basyx.submodel.metamodel.connected.submodelelement.operation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperationVariable;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.ConnectedSubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDefHelper;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * "Connected" implementation of IOperation
 * 
 * @author rajashek
 *
 */
public class ConnectedOperation extends ConnectedSubmodelElement implements IOperation {
	public ConnectedOperation(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public Collection<IOperationVariable> getInputVariables() {
		return Operation.createAsFacade(getElem()).getInputVariables();
	}

	@Override
	public Collection<IOperationVariable> getOutputVariables() {
		return Operation.createAsFacade(getElem()).getOutputVariables();
	}

	@Override
	public Collection<IOperationVariable> getInOutputVariables() {
		return Operation.createAsFacade(getElem()).getInOutputVariables();
	}

	/**
	 * Invoke a remote operation TODO C# includes idShort
	 */
	@Override
	public Object invoke(Object... params) throws Exception {

		// Invoke operation passing an empty string, since the used proxy already points
		// to the operation
		Object result = getProxy().invokeOperation(Operation.INVOKE, wrapParameters(params));

		return unwrapResult(result);
	}
	
	@Override
	public ConnectedAsyncInvocation invokeAsync(Object... params) {
		ConnectedAsyncInvocation invocation =
				new ConnectedAsyncInvocation(getProxy(), getIdShort(), wrapParameters(params));
		return invocation;
	}
	
	@Override
	protected KeyElements getKeyElement() {
		return KeyElements.OPERATION;
	}
	
	@Override
	public Object getValue() {
		throw new UnsupportedOperationException("An Operation has no value");
	}

	@Override
	public void setValue(Object value) {
		throw new UnsupportedOperationException("An Operation has no value");
	}
	
	private Object[] wrapParameters(Object[] parameters) {
		Object[] result = new Object[parameters.length];
		
		// Wrap parameter with valuetype information
		int i = 0;
		for (Object param : parameters) {
			HashMap<String, Object> valueWrapper = new HashMap<>();
			valueWrapper.put(Property.VALUETYPE, PropertyValueTypeDefHelper.getTypeWrapperFromObject(param));
			valueWrapper.put(Property.VALUE, param);

			result[i] = valueWrapper;
			i++;
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private Object unwrapResult(Object result) {
		if (result instanceof Collection<?>) {
			Collection<Object> coll = (Collection<Object>) result;
			if (coll.isEmpty()) {
				return result;
			}
			Object resultWrapper = coll.iterator().next();
			if (resultWrapper instanceof Map<?, ?>) {
				Map<String, Object> map = (Map<String, Object>) resultWrapper;
				if (map.get(Referable.IDSHORT).equals("Response") && map.get(Property.VALUE) != null) {
					return map.get(Property.VALUE);
				}
			}
		}
		return result;
	}
}
