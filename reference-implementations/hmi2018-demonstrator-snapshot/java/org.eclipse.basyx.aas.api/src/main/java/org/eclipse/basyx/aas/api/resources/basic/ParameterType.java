package org.eclipse.basyx.aas.api.resources.basic;

public class ParameterType {

	
	protected int index;
	protected DataType dataType;
	protected String name;	
	
	public ParameterType() {
		super();
	}
	public ParameterType(int index, DataType dataType, String name) {
		this();
		this.setIndex(index);
		this.setDataType(dataType);
		this.setName(name);
	}

	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public DataType getDataType() {
		return dataType;
	}
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
