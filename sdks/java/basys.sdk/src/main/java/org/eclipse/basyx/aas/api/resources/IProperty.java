package org.eclipse.basyx.aas.api.resources;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.IDataElement;

/**
 * Interface for IElement properties
 * 
 * @author kuhn
 *
 */
public interface IProperty extends IElement, IDataElement {

	public PropertyType getPropertyType();
	
	
	
	
	public void setValue(Object obj);
	public Object getValue();
	public void setValueId(Object obj);
	public Object getValueId();
}
