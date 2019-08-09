package org.eclipse.basyx.vab.provider;

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
		// Check empty paths
		if (path == null) {
			throw new RuntimeException("Path is undefined");
		} else if (VABPathTools.isEmptyPath(path)) {
			return handler.postprocessObject(elements);
		}

		Object element = getTargetElement(path);
		return handler.postprocessObject(element);
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {
		// Check empty paths
		if (path == null) {
			throw new RuntimeException("Path is undefined");
		} else if (VABPathTools.isEmptyPath(path)) {
			// Empty path => parent element == null => replace root, if it exists
			if (elements != null) {
				elements = handler.preprocessObject(newValue);
			}
			return;
		}

		Object parentElement = getParentElement(path);
		String propertyName = VABPathTools.getLastElement(path);

		// Only write values, that already exist
		Object thisElement = handler.getElementProperty(parentElement, propertyName);
		if (parentElement != null && propertyName != null && thisElement != null) {
			newValue = handler.preprocessObject(newValue);
			handler.setModelPropertyValue(parentElement, propertyName, newValue);
		}
	}

	@Override
	public void createValue(String path, Object newValue) throws Exception {
		// Check empty paths
		if (path == null) {
			throw new RuntimeException("Path is undefined");
		} else if (VABPathTools.isEmptyPath(path)) {
			// The complete model should be replaced if it does not exist
			if (elements == null) {
				elements = handler.preprocessObject(newValue);
			}
			return;
		}

		// Find parent & name of new element
		Object parentElement = getParentElement(path);
		String propertyName = VABPathTools.getLastElement(path);

		// Only create new, never replace existing elements
		if (parentElement != null) {
			newValue = handler.preprocessObject(newValue);
			Object childElement = handler.getElementProperty(parentElement, propertyName);
			if (childElement == null) {
				handler.setModelPropertyValue(parentElement, propertyName, newValue);
			} else {
				handler.createValue(childElement, newValue);
			}
			return;
		}

		System.out.println("Could not create element, parent element does not exist for path '" + path + "'");
	}

	@Override
	public void deleteValue(String path) throws Exception {
		// Check null path
		if (path == null) {
			throw new RuntimeException("Path is undefined");
		}

		Object parentElement = getParentElement(path);
		String propertyName = VABPathTools.getLastElement(path);
		if (parentElement != null && propertyName != null) {
			handler.deleteValue(parentElement, propertyName);
		}
	}

	@Override
	public void deleteValue(String path, Object obj) throws Exception {
		// Check null path
		if (path == null) {
			throw new RuntimeException("Path is undefined");
		}

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
		Object childElement = getModelPropertyValue(path);

		// Invoke operation for function interfaces
		if (childElement != null && childElement instanceof Function<?, ?>) {
			Function<Object, Object[]> function = (Function<Object, Object[]>) childElement;
			return function.apply(parameters);
		}

		// No operation found
		return null;
	}

	/**
	 * Get the parent of an element in this provider. The path should include the path to the element separated by '/'.
	 * E.g., for accessing element c in path a/b, the path should be a/b/c.
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

	/**
	 * Instead of returning the parent element of a path, this function gives the target element.
	 * E.g., it returns c for the path a/b/c
	 */
	protected Object getTargetElement(String path) throws Exception {
		if (VABPathTools.isEmptyPath(path)) {
			return elements;
		} else {
			Object parentElement = getParentElement(path);
			String operationName = VABPathTools.getLastElement(path);
			if (parentElement != null && operationName != null) {
				return handler.getElementProperty(parentElement, operationName);
			}
		}
		return null;
	}
}
