package org.eclipse.basyx.vab.exception.provider;

import java.util.Collection;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperationVariable;

public class WrongNumberOfParametersException extends MalformedRequestException {
	/**
	 * Version information for serialized instances
	 */
	private static final long serialVersionUID = 1L;
	public WrongNumberOfParametersException(String operationIdShort, Collection<IOperationVariable> expected, Object... actual) {
		super(constructErrorMessage(operationIdShort, expected, actual));
	}

	private static String constructErrorMessage(String operationIdShort, Collection<IOperationVariable> expected, Object... actual) {
		return "Operation with idShort " + operationIdShort + " was called using the wrong number of parameters. Expected size: " + expected.size() + ", actual: " + actual.length;
	}
}
