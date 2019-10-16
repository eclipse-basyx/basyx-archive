package org.eclipse.basyx.vab.modelprovider.generic;

import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

/**
 * Interface for a handler, that can handle an object in the VAB. Can process
 * the object and supports all primitives defined by {@link IModelProvider}.
 * 
 * @author espen
 *
 */
public interface IVABElementHandler {
	/**
	 * Handles external objects and prepares them before they get processed further
	 */
	public default Object preprocessObject(Object element) {
		return element;
	}

	/**
	 * Handles internal objects after they have been processed
	 */
	public default Object postprocessObject(Object element) {
		return element;
	}

	public Object getElementProperty(Object element, String propertyName) throws Exception;

	public void setModelPropertyValue(Object element, String propertyName, Object newValue) throws Exception;

	public void createValue(Object element, Object newValue) throws Exception;

	public void deleteValue(Object element, String propertyName) throws Exception;

	public void deleteValue(Object element, Object property) throws Exception;
}
