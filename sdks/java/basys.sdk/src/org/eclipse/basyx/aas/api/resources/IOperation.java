package org.eclipse.basyx.aas.api.resources;

import java.util.List;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.operation.OperationVariable;

/**
 * Interface for IElement operations
 * 
 * @author kuhn
 *
 */
public interface IOperation extends IElement {

	/**
	 * Return operation parameter types (operation signature)
	 * 
	 * @return Parameter types
	 */
	public List<OperationVariable> getParameterTypes();

	/**
	 * Get operation return type
	 * 
	 * @return Operation return type
	 */
	public List<OperationVariable> getReturnTypes();

	/**
	 * Invoke operation with given parameter
	 * 
	 * @param params
	 *            Operation parameter
	 * @return Return value
	 * @throws Exception
	 */
	public Object invoke(Object... params) throws Exception;
}
