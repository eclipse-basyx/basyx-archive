package org.eclipse.basyx.aas.api.resources;

import java.util.List;
import java.util.function.Function;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.operation.IOperationVariable;
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
	
	
	public void SetParameterTypes(List<OperationVariable> in);
	public void setReturnTypes(List<OperationVariable> out);
	
	public void setInvocable(Function<Object[], Object[]> endpoint);
	public Function<Object[], Object[]> getInvocable();
	
}
