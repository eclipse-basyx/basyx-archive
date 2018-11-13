package org.eclipse.basyx.aas.api.resources.vab;

import java.util.List;



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
	public List<ParameterType> getParameterTypes();

	
	/**
	 * Get operation return type
	 * 
	 * @return Operation return type
	 */
	public DataType getReturnDataType();
	
	
	/**
	 * Invoke operation with given parameter
	 * 
	 * @param params Operation parameter
	 * @return Return value
	 * @throws Exception
	 */
	public Object invoke(Object... params) throws Exception;
}

