package org.eclipse.basyx.aas.impl.resources.basic;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.resources.basic.IProperty;



/**
 * Base class for all properties. Properties are contained in sub models and define individual aspects of the sub model. 
 * The property structure is defined by the meta model of the sub model. Properties may be hierarchcial structured.
 *  
 * @author schoeffler
 *
 */
public class Property extends BaseElement implements IProperty {
	
	
	/**
	 * Marks a property readable
	 */
	protected boolean readable;
	
	
	/**
	 * Marks this property writable
	 */
	protected boolean writeable;
	
	
	/**
	 * Marks this property eventable
	 * - FIXME: What does this mean?
	 */
	protected boolean eventable;
	
	
	/**
	 * Property data type
	 */
	protected DataType dataType;
	
	
	/**
	 * Defines whether this property is a collection
	 */
	protected boolean isCollection;
	
	
	/**
	 * Defines whether this property is a map
	 */
	protected boolean isMap;
	
	
	/**
	 * Property value statements associated with this property
	 */
	protected HashMap<String, Statement> statements = new HashMap<>();
	
	
	
	/**
	 * Add a property value statement to this property
	 * 
	 * @param statement The added property value statement
	 */
	public synchronized void addStatement(Statement statement) {
		// Only add named statements
		if (statement.name == null || statement.name.isEmpty()) {
			throw new IllegalArgumentException();
		}		
		
		// Add new statement to list of property/value statements
		this.statements.put(statement.name, statement);
	}
	
	
	/**
	 * Return the property/value statements of this property
	 * 
	 * @return Property/Value statements
	 */
	public Map<String, Statement> getStatements() {
		return this.statements;
	}	
		

	
	
	/**
	 * Indicate if this property is a collection
	 * 
	 * @return True if this property is a collection
	 */
	public boolean isCollection() {
		return isCollection;
	}
	
	
	/**
	 * Change the collection nature of this property
	 * 
	 * @param isCollection New/updated collection nature
	 */
	public void setCollection(boolean isCollection) { // FIXME this function is not called, thus isCollection is always false;
		this.isCollection = isCollection;
	}


	

	
	/**
	 * Indicate if this property is a map
	 * 
	 * @return True if this property is a map
	 */
	public boolean isMap() {
		return isMap;
	}
	
	
	/**
	 * Change the map nature of this property
	 * 
	 * @param isMap New/updated map nature
	 */
	public void setMap(boolean isMap) {
		this.isMap = isMap;
	}

	

		
	/**
	 * Indicate if the value of this property can be read
	 * 
	 * @return True if this property can be read
	 */
	public boolean isReadable() {
		return readable;
	}
	
	
	/**
	 * Change the readable nature of this property
	 * 
	 * @param readable New/updated readable state
	 */
	public void setReadable(boolean readable) {
		this.readable = readable;
	}

	
	
	
	/**
	 * Indicate if the value of this property can be written
	 * 
	 * @return True if the value of this property can be written
	 */
	public boolean isWriteable() {
		return writeable;
	}

	
	/**
	 * Change the writable nature of this property
	 * 
	 * @param writeable New/updated writable state
	 */
	public void setWriteable(boolean writeable) {
		this.writeable = writeable;
	}

	
	public boolean isEventable() {
		return eventable;
	}

	public void setEventable(boolean eventable) {
		this.eventable = eventable;
	}

	
	
	/**
	 * Get property data type
	 * 
	 * @return Property data type
	 */
	@Override
	public DataType getDataType() {
		return dataType;
	}
	
	
	/**
	 * Set property data type
	 * 
	 * @param dataType New/updated property data type
	 */
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}
}

