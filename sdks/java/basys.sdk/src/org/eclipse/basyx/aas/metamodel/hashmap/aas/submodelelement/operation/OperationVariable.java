package org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.operation;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.operation.IOperationVariable;
import org.eclipse.basyx.aas.metamodel.facades.OperationVariableFacade;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.SubmodelElement;

/**
 * OperationVariable as described by DAAS document An operation variable is a
 * submodel element that is used as input or output variable of an operation.
 * 
 * @author schnicke
 *
 */
public class OperationVariable extends SubmodelElement implements IOperationVariable {
	private static final long serialVersionUID = 1L;
	
	public static final String VALUE="value";

	/**
	 * 
	 * @param value
	 *            Describes the needed argument for an operation via a submodel
	 *            element of kind=Type
	 */
	public OperationVariable(SubmodelElement value) {
		put(VALUE, value);
	}

	@Override
	public void setValue(SubmodelElement value) {
		new OperationVariableFacade(this).setValue(value);
		
	}

	@Override
	public SubmodelElement getValue() {
		return new OperationVariableFacade(this).getValue();
	}

}
