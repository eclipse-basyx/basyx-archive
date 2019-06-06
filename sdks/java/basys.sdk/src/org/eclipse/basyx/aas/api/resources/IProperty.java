package org.eclipse.basyx.aas.api.resources;

/**
 * Interface for IElement properties
 * 
 * @author kuhn
 *
 */
public interface IProperty extends IElement {

	public PropertyType getPropertyType();
	
	
	
	
	public void setValue(Object obj);
	public Object getValue();
	public void setValueId(Object obj);
	public Object getValueId();
}
