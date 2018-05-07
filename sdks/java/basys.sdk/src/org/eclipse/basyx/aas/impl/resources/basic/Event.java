package org.eclipse.basyx.aas.impl.resources.basic;



/**
 * An event is asynchronously generated from the AAS (or any other model element).
 * 
 * @author schoeffler
 *
 */
public class Event extends BaseElement {
	
	
	/**
	 * Event data type
	 */
	protected DataType dataType; 
	
	
	/**
	 * Default constructor
	 */
	public Event() {
		super();
	}
	
	
	/**
	 * Constructor that accepts a data type parameter
	 * 
	 * @param dataType Event data type
	 */
	public Event(DataType dataType) {
		// Delegate to default constructor
		this();
		
		// Set data type
		this.dataType = dataType;
	}

	
	/**
	 * Return element data type
	 * 
	 * @return Element data type
	 */
	public DataType getDataType() {
		return dataType;
	}

	
	/**
	 * Set element data type
	 * 
	 * @param dataType New data type of element
	 */
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}
	
}
