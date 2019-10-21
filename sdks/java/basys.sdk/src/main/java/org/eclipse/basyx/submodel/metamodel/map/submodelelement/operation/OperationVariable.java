package org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperationVariable;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.SingleProperty;

/**
 * OperationVariable as described by DAAS document An operation variable is a
 * submodel element that is used as input or output variable of an operation.
 * 
 * @author schnicke
 *
 */
public class OperationVariable extends SubmodelElement implements IOperationVariable {
	private static final long serialVersionUID = 1L;
	public static final String MODELTYPE = "OperationVariable";

	/**
	 * 
	 * @param value
	 *            Describes the needed argument for an operation via a submodel
	 *            element of kind=Type
	 */
	public OperationVariable(SubmodelElement value) {
		put(SingleProperty.VALUE, value);
	}

	public OperationVariable() {
		// Add model type
		putAll(new ModelType(MODELTYPE));
	}

	public void setValue(ISubmodelElement value) {
		put(SingleProperty.VALUE, value);
	}

	@Override
	public ISubmodelElement getValue() {
		return (ISubmodelElement) get(SingleProperty.VALUE);
	}
}
