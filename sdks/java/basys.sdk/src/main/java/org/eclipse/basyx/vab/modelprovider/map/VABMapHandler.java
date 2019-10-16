package org.eclipse.basyx.vab.modelprovider.map;

import java.util.Map;

import org.eclipse.basyx.vab.modelprovider.generic.IVABElementHandler;

/**
 * A VAB map handler. Handles "Map" elements.
 * 
 * @author espen
 *
 */
public class VABMapHandler implements IVABElementHandler {
	@Override
	public Object preprocessObject(Object object) {
		// TODO check if any element key comprises '/' and throw RunTimeException
		return object;
	}

	@Override
	public Object getElementProperty(Object element, String propertyName) throws Exception {
		if (element instanceof Map<?, ?>) {
			Map<?, ?> map = (Map<?, ?>) element;
			return map.get(propertyName);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setModelPropertyValue(Object element, String propertyName, Object newValue) throws Exception {
		if (element instanceof Map) {
			Map<String, Object> map = (Map<String, Object>) element;
			map.put(propertyName, newValue);
		}
	}

	@Override
	public void createValue(Object element, Object newValue) throws Exception {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteValue(Object element, String propertyName) throws Exception {
		if (element instanceof Map) {
			Map<String, Object> map = (Map<String, Object>) element;
			map.remove(propertyName);
		}
	}

	@Override
	public void deleteValue(Object element, Object property) throws Exception {
	}
}
