package org.eclipse.basyx.vab.modelprovider.map;

import java.util.Map;

import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.eclipse.basyx.vab.modelprovider.generic.IVABElementHandler;

/**
 * A VAB map handler. Handles "Map" elements.
 * 
 * @author espen
 *
 */
public class VABMapHandler implements IVABElementHandler {
	@Override
	public Object getElementProperty(Object element, String propertyName) throws ProviderException {
		if (element instanceof Map<?, ?>) {
			Map<?, ?> map = (Map<?, ?>) element;

			//check if requested property exists in map
			if(!map.containsKey(propertyName)) {
				throw new ResourceNotFoundException("Property \"" + propertyName + "\" does not exist.");
			}
			
			return map.get(propertyName);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean setModelPropertyValue(Object element, String propertyName, Object newValue) throws ProviderException {
		if (element instanceof Map) {
			Map<String, Object> map = (Map<String, Object>) element;
			
			//It is not possible to block creating new values here,
			//as createValue doesn't work in MapHandler because of missing propertyName
			
			map.put(propertyName, newValue);
			return true;
		}
		return false;
	}

	@Override
	public boolean createValue(Object element, Object newValue) throws ProviderException {
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean deleteValue(Object element, String propertyName) throws ProviderException {
		if (element instanceof Map) {
			Map<String, Object> map = (Map<String, Object>) element;
			//check if requested property exists in map
			if(!map.containsKey(propertyName)) {
				throw new ResourceNotFoundException("Property \"" + propertyName + "\" does not exist. Therefore it can not be deleted.");
			}
			map.remove(propertyName);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteValue(Object element, Object property) throws ProviderException {
		return false;
	}
}
