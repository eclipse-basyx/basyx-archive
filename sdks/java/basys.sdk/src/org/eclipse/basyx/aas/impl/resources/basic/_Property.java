package org.eclipse.basyx.aas.impl.resources.basic;

import org.eclipse.basyx.aas.api.resources.IProperty;
import org.eclipse.basyx.aas.api.resources.PropertyType;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.enums.DataObjectType;

/**
 * Base class for all properties. Properties are contained in sub models and
 * define individual aspects of the sub model. The property structure is defined
 * by the meta model of the sub model. Properties may be hierarchcial
 * structured.
 * 
 * @author schoeffler, pschorn
 *
 */
public class _Property extends BaseElement implements IProperty {

	/**
	 * Marks a property readable
	 */
	protected boolean readable;

	/**
	 * Marks this property writable
	 */
	protected boolean writeable;

	/**
	 * Marks this property eventable - FIXME: What does this mean?
	 */
	protected boolean eventable;

	/**
	 * Property data type
	 */
	protected DataObjectType dataType;

	protected PropertyType propertyType;

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
	 * @param readable
	 *            New/updated readable state
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
	 * @param writeable
	 *            New/updated writable state
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
	public DataObjectType getDataType() {
		return dataType;
	}

	/**
	 * Set property data type
	 * 
	 * @param dataType
	 *            New/updated property data type
	 */
	public void setDataType(DataObjectType dataType) {
		this.dataType = dataType;
	}

	@Override
	public PropertyType getPropertyType() {
		return propertyType;
	}
}
