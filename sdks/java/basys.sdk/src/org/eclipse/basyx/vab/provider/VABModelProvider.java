package org.eclipse.basyx.vab.provider;

import java.util.Collection;
import java.util.function.Function;

import org.eclipse.basyx.vab.core.IModelProvider;
import org.eclipse.basyx.vab.core.tools.VABPathTools;

/**
 * A generic VAB model provider.
 * 
 * @author espen
 */
public class VABModelProvider implements IModelProvider {
	/**
	 * Handler, which handles single element objects
	 */
	private IVABElementHandler handler;

	/**
	 * Root object that stores contained elements
	 */
	protected Object elements;

	public VABModelProvider(Object elements, IVABElementHandler handler) {
		this.handler = handler;
		this.elements = handler.preprocessObject(elements);
	}

	@Override
	public Object getModelPropertyValue(String path) throws Exception {
		if (VABPathTools.isEmptyPath(path)) {
			return handler.postprocessObject(elements);
		}

		Object parentElement = getParentElement(path);
		String propertyName = VABPathTools.getLastElement(path);
		if (parentElement != null && propertyName != null) {
			return handler.postprocessObject(handler.getElementProperty(parentElement, propertyName));
		}
		return null;
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {
		Object parentElement = getParentElement(path);
		String propertyName = VABPathTools.getLastElement(path);
		if (parentElement != null && propertyName != null) {
			newValue = handler.preprocessObject(newValue);
			handler.setModelPropertyValue(parentElement, propertyName, newValue);
		}
	}

	@Override
	public void createValue(String path, Object newValue) throws Exception {
		Object parentElement = getParentElement(path);
		String propertyName = VABPathTools.getLastElement(path);
		if (parentElement != null && propertyName != null) {
			newValue = handler.preprocessObject(newValue);
			Object childElement = handler.getElementProperty(parentElement, propertyName);
			if (handler.postprocessObject(childElement) instanceof Collection) {
				handler.createValue(childElement, newValue);
			} else {
				handler.setModelPropertyValue(parentElement, propertyName, newValue);
			}
		}
	}

	@Override
	public void deleteValue(String path) throws Exception {
		Object parentElement = getParentElement(path);
		String propertyName = VABPathTools.getLastElement(path);
		if (parentElement != null && propertyName != null) {
			handler.deleteValue(parentElement, propertyName);
		}
	}

	@Override
	public void deleteValue(String path, Object obj) throws Exception {
		Object parentElement = getParentElement(path);
		String propertyName = VABPathTools.getLastElement(path);
		if (parentElement != null && propertyName != null) {
			Object childElement = handler.getElementProperty(parentElement, propertyName);
			if (childElement != null) {
				handler.deleteValue(childElement, obj);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object invokeOperation(String path, Object[] parameters) throws Exception {
		Object parentElement = getParentElement(path);
		String operationName = VABPathTools.getLastElement(path);
		if (parentElement != null && operationName != null) {
			Object childElement = handler.getElementProperty(parentElement, operationName);
			if (childElement != null && childElement instanceof Function<?, ?>) {
				Function<Object, Object[]> function = (Function<Object, Object[]>) childElement;
				return function.apply(parameters);
			}
		}
		return null;
	}

	/**
	 * Get the parent of an element in this provider. The path should include the
	 * path to the element separated by '/'. E.g., for accessing element c in path
	 * a/b, the path should be a/b/c.
	 */
	private Object getParentElement(String path) throws Exception {
		// Split path into its elements, separated by '/'
		String[] pathElements = VABPathTools.splitPath(path);

		Object currentElement = elements;
		// ignore the leaf element, only return the leaf's parent element
		for (int i = 0; i < pathElements.length - 1; i++) {
			if (currentElement == null) {
				return null;
			}
			currentElement = handler.getElementProperty(currentElement, pathElements[i]);
		}
		return currentElement;
	}
}
