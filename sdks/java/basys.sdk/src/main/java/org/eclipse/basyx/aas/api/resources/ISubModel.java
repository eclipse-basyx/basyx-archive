package org.eclipse.basyx.aas.api.resources;

import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IHasDataSpecification;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IHasSemantics;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IIdentifiable;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.haskind.IHasKind;

/**
 * Interface API for sub models
 * 
 * @author kuhn
 *
 */
public interface ISubModel extends IElement,IHasSemantics,IIdentifiable,/*IQualifiable*/IHasDataSpecification,IHasKind {

	/**
	 * Clone this sub model. Cloned sub models are no longer frozen
	 * 
	 * @return New sub model instance
	 */
	// public ISubModel cloneSubModel();

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
	
	
	public void setProperties(Map<String, IProperty> properties);
	
	public void setOperations(Map<String, IOperation> operations);
	
	public Map<String, Object> getElements();
	
	//public void setElements(Map<String, SubmodelElement> elements);
	
	
}
