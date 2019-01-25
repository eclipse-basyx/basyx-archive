package org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.operation;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.SubmodelElement;

/**
 * OperationVariable as described by DAAS document An operation variable is a
 * submodel element that is used as input or output variable of an operation.
 * 
 * @author schnicke
 *
 */
public class OperationVariable extends SubmodelElement {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param value
	 *            Describes the needed argument for an operation via a submodel
	 *            element of kind=Type
	 */
	public OperationVariable(SubmodelElement value) {
		put("value", value);
	}

}
