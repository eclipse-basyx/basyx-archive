package org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation;

import java.util.List;

import org.eclipse.basyx.submodel.metamodel.api.IElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;

/**
 * An operation is a submodel element with input and output variables.
 * 
 * @author kuhn, schnicke
 *
 */
public interface IOperation extends IElement, ISubmodelElement {

	/**
	 * Return operation parameter types (operation signature)
	 * 
	 * @return Parameter types
	 */
	public List<IOperationVariable> getInputVariables();

	/**
	 * Get operation return type
	 * 
	 * @return Operation return type
	 */
	public List<IOperationVariable> getOutputVariables();

	/**
	 * Get the parameters that are input and output of the operation.
	 * 
	 * @return
	 */
	List<IOperationVariable> getInOutputVariables();

	/**
	 * Invoke operation with given parameter
	 * 
	 * @param params
	 *            Operation parameter
	 * @return If multiple values are returned, Object is here a list of Objects
	 * @throws Exception
	 */
	public Object invoke(Object... params) throws Exception;
}
