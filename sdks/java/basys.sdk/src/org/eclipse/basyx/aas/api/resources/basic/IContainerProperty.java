package org.eclipse.basyx.aas.api.resources.basic;

/**
 * Interface for AAS properties that contain other properties
 * TODO: Reflexivity?
 * @author schnicke
 *
 */
public interface IContainerProperty extends IProperty {
	IProperty getProperty(String name);
}
