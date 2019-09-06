package org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property;

import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.operation.IOperation;

/**
 * Interface for AAS properties that contain other properties
 * 
 * @author schnicke
 *
 */
public interface IContainerProperty extends IProperty {
	Map<String, IProperty> getProperties();

	Map<String, IOperation> getOperations();
}
