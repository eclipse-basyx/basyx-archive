package org.eclipse.basyx.aas.impl.resources.basic;

import java.util.Map;

import org.eclipse.basyx.aas.api.resources.basic.IElement;
import org.eclipse.basyx.aas.api.resources.basic.IOperation;
import org.eclipse.basyx.aas.api.resources.basic.IProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;





/**
 * Sub model that is dynamic and referenced, its internal structure is not known 
 *  
 * @author kuhn
 *
 */
public class ReferencedSubModel extends BaseElement implements ISubModel {

	
	/**
	 * Get sub model properties
	 */
	@Override
	public Map<String, IProperty> getProperties() {
		// No properties are known for referenced sub models
		return null;
	}
	
	
	/**
	 * Return a contained element
	 */
	@Override
	public IElement getElement(String name) {
		return null;
	}


	/**
	 * Return all contained elements
	 */	
	@Override
	public Map<String, IElement> getElements() {
		return null;
	}


	@Override
	public Map<String, IOperation> getOperations() {
		// TODO Auto-generated method stub
		return null;
	}	
	
	@Override
	public boolean isFrozen() {
		return false;
	}


	@Override
	public void freeze() {
		return;
	}	
	
	@Override
	public void unfreeze() {
		return;
	}	
}
