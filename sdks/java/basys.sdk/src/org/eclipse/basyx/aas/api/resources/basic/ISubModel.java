package org.eclipse.basyx.aas.api.resources.basic;

import java.util.Map;

import org.eclipse.basyx.aas.api.exception.ServerException;

/**
 * Interface API for sub models
 * 
 * @author kuhn
 *
 */
public interface ISubModel extends IElementContainer {

	
	/**
	 * Indicate if this sub model is frozen. Frozen sub models prevent all further modifications
	 */
	public boolean isFrozen();
	
	
	/**
	 * Freeze the sub model
	 * @throws ServerException 
	 */
	public void freeze() throws ServerException;
	
	
	/**
	 * Unfreeze the sub model
	 * @throws ServerException 
	 */
	public void unfreeze() throws ServerException;

	
	/**
	 * Clone this sub model. Cloned sub models are no longer frozen
	 * 
	 * @return New sub model instance
	 */
	//public ISubModel cloneSubModel();
	
	

	/**
	 * Get sub model properties
	 * 
	 * @return Sub model properties
	 */
	public Map<String, IProperty> getProperties();
	
	
	/**
	 * Get sub model operations
	 * 
	 * @return Sub model operations
	 */
	public Map<String, IOperation> getOperations();


	
}



