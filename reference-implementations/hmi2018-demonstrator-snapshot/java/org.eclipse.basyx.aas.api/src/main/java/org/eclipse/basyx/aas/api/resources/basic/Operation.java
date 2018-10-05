package org.eclipse.basyx.aas.api.resources.basic;


import java.util.ArrayList;
import java.util.List;

public class Operation extends BaseElement {
	
	protected List<ParameterType> parameterTypes = new ArrayList<>();
	protected DataType returnDataType;
	
	public Operation() {
		super();
	}
	
	
	public List<ParameterType> getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterTypes(List<ParameterType> parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	public DataType getReturnDataType() {
		return returnDataType;
	}

	public void setReturnDataType(DataType returnDataType) {
		this.returnDataType = returnDataType;
	}
	
}
