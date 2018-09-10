package org.eclipse.basyx.aas.api.resources.basic;

import java.util.Map;

/**
 * Interface for AAS properties that contain other properties
 * TODO: Reflexivity?
 * @author schnicke
 *
 */
public interface IContainerProperty extends IProperty {
	Map<String, IProperty> getProperties();
}
