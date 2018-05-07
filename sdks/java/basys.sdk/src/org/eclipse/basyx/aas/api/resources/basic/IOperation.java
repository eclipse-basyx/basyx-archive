package org.eclipse.basyx.aas.api.resources.basic;

import java.util.List;

import org.eclipse.basyx.aas.impl.resources.basic.DataType;
import org.eclipse.basyx.aas.impl.resources.basic.ParameterType;



/**
 * Interface for AAS operations 
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
}

