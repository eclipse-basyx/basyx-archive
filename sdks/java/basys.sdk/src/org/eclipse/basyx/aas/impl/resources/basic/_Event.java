package org.eclipse.basyx.aas.impl.resources.basic;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.enums.DataObjectType;

/**
 * An event is asynchronously generated from the AAS (or any other model element).
 * 
 * @author schoeffler
 *
 */
public class _Event extends BaseElement {
	
	
	/**
	 * Event data type
	 */
	protected DataObjectType dataType; 
	
	
	/**
	 * Default constructor
	 */
	public _Event() {
		super();
	}
	
	
	/**
	 * Constructor that accepts a data type parameter
	 * 
	 * @param dataType Event data type
	 */
	public _Event(DataObjectType dataType) {
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
	public DataObjectType getDataType() {
		return dataType;
	}

	
	/**
	 * Set element data type
	 * 
	 * @param dataType New data type of element
	 */
	public void setDataType(DataObjectType dataType) {
		this.dataType = dataType;
	}
	
}
