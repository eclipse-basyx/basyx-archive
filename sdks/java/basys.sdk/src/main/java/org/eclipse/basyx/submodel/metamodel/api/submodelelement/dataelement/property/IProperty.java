package org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.property;

import org.eclipse.basyx.submodel.metamodel.api.IElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IDataElement;

/**
 * Interface for IElement properties
 * 
 * @author kuhn
 *
 */
public interface IProperty extends IElement, IDataElement {

	/**
	 * Gets type of property
	 * 
	 * @return
	 */
	public PropertyType getPropertyType();

	/**
	 * Gets the reference to the global unique id of a coded value.
	 * 
	 * @return
	 */
	public String getValueId();
}
