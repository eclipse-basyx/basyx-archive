package org.eclipse.basyx.vab.modelprovider.generic;

import org.eclipse.basyx.vab.exception.provider.ProviderException;

/**
 * Handler that does not handle objects by itself, but consists of multiple
 * handlers and forwards its methods to all of them.
 * 
 * @author espen
 *
 */
public class VABMultiElementHandler implements IVABElementHandler {
	private IVABElementHandler[] handlers;

	public VABMultiElementHandler(IVABElementHandler... handlers) {
		this.handlers = handlers;
	}

	@Override
	public Object postprocessObject(Object element) {
		Object result = element;
		for (IVABElementHandler handler : handlers) {
			result = handler.postprocessObject(result);
		}
		return result;
	}

	@Override
	public Object getElementProperty(Object element, String propertyName) throws ProviderException {
		Object result = null;
		for (IVABElementHandler handler : handlers) {
			result = handler.getElementProperty(element, propertyName);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	@Override
	public boolean setModelPropertyValue(Object element, String propertyName, Object newValue) throws ProviderException {
		boolean result = false;
		for (IVABElementHandler handler : handlers) {
			if(handler.setModelPropertyValue(element, propertyName, newValue)) {
				result = true;
			}
		}
		return result;
	}

	@Override
	public boolean createValue(Object element, Object newValue) throws ProviderException {
		boolean result = false;
		for (IVABElementHandler handler : handlers) {
			if(handler.createValue(element, newValue)) {
				result = true;
			}
		}
		return result;
	}

	@Override
	public boolean deleteValue(Object element, String propertyName) throws ProviderException {
		boolean result = false;
		for (IVABElementHandler handler : handlers) {
			if(handler.deleteValue(element, propertyName)) {
				result = true;
			}
		}
		return result;
	}

	@Override
	public boolean deleteValue(Object element, Object property) throws ProviderException {
		boolean result = false;
		for (IVABElementHandler handler : handlers) {
			if(handler.deleteValue(element, property)) {
				result = true;
			}
		}
		return result;
	}
}
