package org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperationVariable;
import org.eclipse.basyx.submodel.metamodel.facade.submodelelement.SubmodelElementFacadeFactory;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.vab.model.VABModelMap;

/**
 * OperationVariable as described by DAAS document An operation variable is a
 * submodel element that is used as input or output variable of an operation.
 * 
 * @author schnicke
 *
 */
public class OperationVariable extends VABModelMap<Object> implements IOperationVariable {
	public static final String MODELTYPE = "OperationVariable";

	/**
	 * 
	 * @param value
	 *            Describes the needed argument for an operation via a submodel
	 *            element of kind=Type
	 */
	public OperationVariable(SubmodelElement value) {
		// Add model type
		putAll(new ModelType(MODELTYPE));
		
		put(Property.VALUE, value);
	}

	public OperationVariable() {
		// Add model type
		putAll(new ModelType(MODELTYPE));
	}
	
	/**
	 * Creates an OperationVariable object from a map
	 * 
	 * @param obj an OperationVariable object as raw map
	 * @return an OperationVariable object, that behaves like a facade for the given map
	 */
	public static OperationVariable createAsFacade(Map<String, Object> obj) {
		if (obj == null) {
			return null;
		}
		
		OperationVariable facade = new OperationVariable();
		facade.setMap(obj);
		return facade;
	}

	public void setValue(ISubmodelElement value) {
		put(Property.VALUE, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ISubmodelElement getValue() {
		return SubmodelElementFacadeFactory.createSubmodelElement((Map<String, Object>) get(Property.VALUE));
	}

}
