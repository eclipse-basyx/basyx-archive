package org.eclipse.basyx.vab.modelprovider.generic;

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

	public Object preprocessObject(Object element) {
		Object result = element;
		for (IVABElementHandler handler : handlers) {
			result = handler.preprocessObject(result);
		}
		return result;
	}

	public Object postprocessObject(Object element) {
		Object result = element;
		for (IVABElementHandler handler : handlers) {
			result = handler.postprocessObject(result);
		}
		return result;
	}

	@Override
	public Object getElementProperty(Object element, String propertyName) throws Exception {
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
	public void setModelPropertyValue(Object element, String propertyName, Object newValue) throws Exception {
		for (IVABElementHandler handler : handlers) {
			handler.setModelPropertyValue(element, propertyName, newValue);
		}
	}

	@Override
	public void createValue(Object element, Object newValue) throws Exception {
		for (IVABElementHandler handler : handlers) {
			handler.createValue(element, newValue);
		}
	}

	@Override
	public void deleteValue(Object element, String propertyName) throws Exception {
		for (IVABElementHandler handler : handlers) {
			handler.deleteValue(element, propertyName);
		}
	}

	@Override
	public void deleteValue(Object element, Object property) throws Exception {
		for (IVABElementHandler handler : handlers) {
			handler.deleteValue(element, property);
		}
	}
}
