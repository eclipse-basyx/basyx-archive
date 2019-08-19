package org.eclipse.basyx.aas.backend.connected.aas.submodelelement.operation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.qualifiable.IConstraint;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.operation.IOperationVariable;
import org.eclipse.basyx.aas.api.resources.IOperation;
import org.eclipse.basyx.aas.backend.connected.aas.submodelelement.ConnectedSubmodelElement;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasDataSpecificationFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasKindFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasSemanticsFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedQualifiableFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedReferableFacade;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.operation.Operation;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.operation.OperationVariable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.valuetypedef.PropertyValueTypeDefHelper;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * "Connected" implementation of IOperation
 * 
 * @author rajashek
 *
 */
public class ConnectedOperation extends ConnectedSubmodelElement implements IOperation {
	public ConnectedOperation(String path, VABElementProxy proxy) {
		super(path, proxy);
	}

	@Override
	public String getId() {
		// try local get
		String id = (String) this.getLocal(Referable.IDSHORT);
		if (id != null) {
			return id;
		}
		return (String) getProxy().readElementValue(constructPath(Referable.IDSHORT));
	}

	@Override
	public void setId(String id) {
		// try set local if exists
		if (this.getLocal(Referable.IDSHORT) != null) {
			this.putLocal(Referable.IDSHORT, id);
		}

		getProxy().updateElementValue(constructPath(Referable.IDSHORT), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IOperationVariable> getParameterTypes() {
		return (List<IOperationVariable>) getProxy().readElementValue(constructPath(Operation.IN));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IOperationVariable> getReturnTypes() {
		return (List<IOperationVariable>) getProxy().readElementValue(constructPath(Operation.OUT));
	}

	/**
	 * Invoke a remote operation
	 * TODO C# includes idShort
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object invoke(Object... params) throws Exception {

		// Wrap parameter with valuetype information
		int i = 0;
		for (Object param : params) {
			HashMap<String, Object> valueWrapper = new HashMap<String, Object>();
			valueWrapper.put(Property.VALUETYPE, PropertyValueTypeDefHelper.fromObject(param));
			valueWrapper.put(Property.VALUE, param);
			
			params[i] = valueWrapper;
			i++;
		}

		// Invoke operation
		Object result = getProxy().invoke(super.getPath(), params);

		// Unwrap result value
		if (result instanceof List<?>) {
			Object resultWrapper = ((List<?>) result).get(0);

			if (resultWrapper instanceof Map<?, ?>) {
				Map<String, Object> map = (Map<String, Object>) resultWrapper;
				if (map.get(Referable.IDSHORT).equals("Response") && map.get(Property.VALUE) != null) {
					// TODO C# test access from C# to Java hosted operation
					result = map.get(Property.VALUE);
				}
			}
		}

		return result;
	}

	@Override
	public void SetParameterTypes(List<OperationVariable> in) {
		getProxy().updateElementValue(constructPath(Operation.IN), in);

	}

	@Override
	public void setReturnTypes(List<OperationVariable> out) {
		getProxy().updateElementValue(constructPath(Operation.OUT), out);

	}

	@Override
	public void setInvocable(Function<Object[], Object[]> endpoint) {
		getProxy().updateElementValue(constructPath(Operation.INVOKABLE), endpoint);

	}

	@SuppressWarnings("unchecked")
	@Override
	public Function<Object[], Object> getInvocable() {
		return (Function<Object[], Object>) getProxy().readElementValue(constructPath(Operation.INVOKABLE));
	}

	@Override
	public HashSet<IReference> getDataSpecificationReferences() {
		return new ConnectedHasDataSpecificationFacade(getPath(), getProxy()).getDataSpecificationReferences();
	}

	@Override
	public void setDataSpecificationReferences(HashSet<IReference> ref) {
		new ConnectedHasDataSpecificationFacade(getPath(), getProxy()).setDataSpecificationReferences(ref);

	}

	@Override
	public String getIdshort() {
		return new ConnectedReferableFacade(getPath(), getProxy()).getIdshort();
	}

	@Override
	public String getCategory() {
		return new ConnectedReferableFacade(getPath(), getProxy()).getCategory();
	}

	@Override
	public String getDescription() {
		return new ConnectedReferableFacade(getPath(), getProxy()).getDescription();
	}

	@Override
	public IReference getParent() {
		return new ConnectedReferableFacade(getPath(), getProxy()).getParent();
	}

	@Override
	public void setIdshort(String idShort) {
		new ConnectedReferableFacade(getPath(), getProxy()).setIdshort(idShort);

	}

	@Override
	public void setCategory(String category) {
		new ConnectedReferableFacade(getPath(), getProxy()).setCategory(category);

	}

	@Override
	public void setDescription(String description) {
		new ConnectedReferableFacade(getPath(), getProxy()).setDescription(description);

	}

	@Override
	public void setParent(IReference obj) {
		new ConnectedReferableFacade(getPath(), getProxy()).setParent(obj);

	}

	@Override
	public IReference getSemanticId() {
		return new ConnectedHasSemanticsFacade(getPath(), getProxy()).getSemanticId();
	}

	@Override
	public void setSemanticID(IReference ref) {
		new ConnectedHasSemanticsFacade(getPath(), getProxy()).setSemanticID(ref);

	}

	@Override
	public void setQualifier(Set<IConstraint> qualifiers) {
		new ConnectedQualifiableFacade(getPath(), getProxy()).setQualifier(qualifiers);

	}

	@Override
	public Set<IConstraint> getQualifier() {
		return new ConnectedQualifiableFacade(getPath(), getProxy()).getQualifier();
	}

	@Override
	public String getHasKindReference() {
		return new ConnectedHasKindFacade(getPath(), getProxy()).getHasKindReference();
	}

	@Override
	public void setHasKindReference(String kind) {
		new ConnectedHasKindFacade(getPath(), getProxy()).setHasKindReference(kind);

	}
}
