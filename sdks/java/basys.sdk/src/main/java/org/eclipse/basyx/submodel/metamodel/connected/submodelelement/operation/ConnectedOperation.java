package org.eclipse.basyx.submodel.metamodel.connected.submodelelement.operation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IConstraint;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperationVariable;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.ConnectedSubmodelElement;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.HasDataSpecificationFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.HasSemanticsFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.ReferableFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.haskind.HasKindFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.qualifiable.QualifiableFacade;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.SingleProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.valuetypedef.PropertyValueTypeDefHelper;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<IOperationVariable> getParameterTypes() {
		return (List<IOperationVariable>) getProxy().getModelPropertyValue(Operation.IN);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IOperationVariable> getReturnTypes() {
		return (List<IOperationVariable>) getProxy().getModelPropertyValue(Operation.OUT);
	}

	/**
	 * Invoke a remote operation TODO C# includes idShort
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object invoke(Object... params) throws Exception {

		// Wrap parameter with valuetype information
		int i = 0;
		for (Object param : params) {
			HashMap<String, Object> valueWrapper = new HashMap<String, Object>();
			valueWrapper.put(SingleProperty.VALUETYPE, PropertyValueTypeDefHelper.fromObject(param));
			valueWrapper.put(SingleProperty.VALUE, param);

			params[i] = valueWrapper;
			i++;
		}

		// Invoke operation passing an empty string, since the used proxy already points
		// to the operation
		Object result = getProxy().invokeOperation("", params);

		// Unwrap result value
		if (result instanceof List<?>) {
			Object resultWrapper = ((List<?>) result).get(0);
			if (resultWrapper instanceof Map<?, ?>) {
				Map<String, Object> map = (Map<String, Object>) resultWrapper;
				if (map.get(Referable.IDSHORT).equals("Response") && map.get(SingleProperty.VALUE) != null) {
					result = map.get(SingleProperty.VALUE);
				}
			}
		}

		return result;
	}


	@Override
	public Function<Object[], Object> getInvocable() {
		throw new RuntimeException("Not possible on remote side");
	}

	@Override
	public Set<IReference> getDataSpecificationReferences() {
		return new HasDataSpecificationFacade(getElem()).getDataSpecificationReferences();
	}

	@Override
	public String getIdShort() {
		return new ReferableFacade(getElem()).getIdShort();
	}

	@Override
	public String getCategory() {
		return new ReferableFacade(getElem()).getCategory();
	}

	@Override
	public String getDescription() {
		return new ReferableFacade(getElem()).getDescription();
	}

	@Override
	public IReference getParent() {
		return new ReferableFacade(getElem()).getParent();
	}

	@Override
	public IReference getSemanticId() {
		return new HasSemanticsFacade(getElem()).getSemanticId();
	}

	@Override
	public Set<IConstraint> getQualifier() {
		return new QualifiableFacade(getElem()).getQualifier();
	}

	@Override
	public String getHasKindReference() {
		return new HasKindFacade(getElem()).getHasKindReference();
	}
}
