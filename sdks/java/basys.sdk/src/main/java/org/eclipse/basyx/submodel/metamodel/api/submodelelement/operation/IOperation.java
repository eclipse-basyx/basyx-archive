package org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation;

import java.util.List;
import java.util.function.Function;

import org.eclipse.basyx.submodel.metamodel.api.IElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;

/**
 * Interface for IElement operations
 * 
 * @author kuhn
 *
 */
public interface IOperation extends IElement, ISubmodelElement {

	/**
	 * Return operation parameter types (operation signature)
	 * 
	 * @return Parameter types
	 */
	public List<IOperationVariable> getParameterTypes();

	/**
	 * Get operation return type
	 * 
	 * @return Operation return type
	 */
	public List<IOperationVariable> getReturnTypes();

	/**
	 * Invoke operation with given parameter
	 * 
	 * @param params
	 *            Operation parameter
	 * @return Return value
	 * @throws Exception
	 */
	public Object invoke(Object... params) throws Exception;
	public Function<Object[], Object> getInvocable();
}
