package org.eclipse.basyx.aas.api.metamodel.aas;

import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IHasDataSpecification;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IHasSemantics;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IIdentifiable;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.haskind.IHasKind;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.operation.IOperation;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.IProperty;
import org.eclipse.basyx.vab.IElement;

/**
 * Interface API for sub models
 * 
 * @author kuhn
 *
 */
public interface ISubModel extends IElement,IHasSemantics,IIdentifiable,/*IQualifiable*/IHasDataSpecification,IHasKind {
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

	public void addProperty(IProperty property);

	public void addOperation(IOperation operation);
}
