package org.eclipse.basyx.aas.api.resources;

import java.util.Map;

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
