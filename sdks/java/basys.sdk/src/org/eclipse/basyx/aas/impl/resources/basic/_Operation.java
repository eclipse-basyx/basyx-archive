package org.eclipse.basyx.aas.impl.resources.basic;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.basyx.aas.api.resources.IOperation;
import org.eclipse.basyx.aas.api.resources.ParameterType;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.enums.DataObjectType;

/**
 * Technology independent implementation of the AAS operation class. An AAS operation is invoked synchronously by a caller and returns a value. 
 * This invocation may optionally trigger an asynchronous operation that completes at a later time. 
 * 
 * @author schoeffler
 *
 */
public class _Operation extends BaseElement implements IOperation {
	
	
	/**
	 * Operation parameter
	 */
	protected List<ParameterType> parameterTypes = new ArrayList<>();
	
	
	/**
	 * Return data type
	 */
	protected DataObjectType returnDataType;
	
	
	
	
	/**
	 * Constructor
	 */
	public _Operation() {
		super();
	}
	
	
	/**
	 * Return operation parameter types (operation signature)
	 * 
	 * @return Parameter types
	 */
	@Override
	public List<ParameterType> getParameterTypes() {
		return parameterTypes;
	}

	
	/**
	 * Set operation parameter types
	 * 
	 * @param parameterTypes Operation parameter types
	 */
	public void setParameterTypes(List<ParameterType> parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	
	/**
	 * Get operation return type
	 * 
	 * @return Operation return type
	 */
	@Override
	public DataObjectType getReturnDataType() {
		return returnDataType;
	}

	
	/**
	 * Set operation return type
	 * 
	 * @param returnDataType Operation return type
	 */
	public void setReturnDataType(DataObjectType returnDataType) {
		this.returnDataType = returnDataType;
	}


	@Override
	public Object invoke(Object... params) throws Exception {
		throw new RuntimeException("Can't directly call invoke on descriptor");
	}
}

