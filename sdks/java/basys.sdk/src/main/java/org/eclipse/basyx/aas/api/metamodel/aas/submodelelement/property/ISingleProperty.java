package org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property;

import org.eclipse.basyx.aas.api.exception.ServerException;

/**
 * Interface for AAS properties that carry a single value
 *  
 * @author kuhn
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
	 * @throws ServerException 
	 */
	public void set(Object newValue) throws ServerException;
}
