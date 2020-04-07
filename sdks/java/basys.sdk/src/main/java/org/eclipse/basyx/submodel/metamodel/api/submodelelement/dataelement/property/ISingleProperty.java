package org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.property;

import org.eclipse.basyx.vab.exception.provider.ProviderException;

/**
 * Interface for AAS properties that carry a single value
 * 
 * @author kuhn, schnicke
 *
 */
public interface ISingleProperty extends IProperty {

	
	/**
	 * Get property value
	 * 
	 * @return Property value
	 * @throws Exception
	 */
	public Object get() throws Exception;
	
	
	/**
	 * Set property value
	 * @throws ProviderException 
	 */
	public void set(Object newValue) throws ProviderException;

	/**
	 * Gets the data type of the value
	 * 
	 * @return
	 */
	public String getValueType();
}
