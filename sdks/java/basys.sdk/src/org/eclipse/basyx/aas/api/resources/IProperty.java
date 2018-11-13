package org.eclipse.basyx.aas.api.resources;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.enums.DataObjectType;

/**
 * Interface for IElement properties
 * 
 * @author kuhn
 *
 */
public interface IProperty extends IElement {

	/**
	 * Get property data type
	 * 
	 * @return Property data type
	 */
	public DataObjectType getDataType();

	public PropertyType getPropertyType();
}
