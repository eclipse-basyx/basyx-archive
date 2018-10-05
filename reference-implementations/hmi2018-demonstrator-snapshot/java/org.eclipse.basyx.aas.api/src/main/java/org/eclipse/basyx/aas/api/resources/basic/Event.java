package org.eclipse.basyx.aas.api.resources.basic;

public class Event extends BaseElement {
	
	protected DataType dataType; 
	
	public Event() {
		super();
	}
		
	public Event(DataType dataType) {
		this();
		this.dataType = dataType;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}
	
}
