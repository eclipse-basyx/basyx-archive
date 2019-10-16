package org.eclipse.basyx.submodel.metamodel.api.submodelelement.property;

import org.eclipse.basyx.submodel.metamodel.api.IElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.IDataElement;

/**
 * Interface for IElement properties
 * 
 * @author kuhn
 *
 */
public interface IProperty extends IElement, IDataElement {

	public PropertyType getPropertyType();
	
	public void setValueId(String obj);

	public String getValueId();
}
