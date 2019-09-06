package org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.IDataElement;
import org.eclipse.basyx.vab.IElement;

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
