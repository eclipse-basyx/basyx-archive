package org.eclipse.basyx.vab.modelprovider.generic;

import java.util.function.Function;

import org.eclipse.basyx.vab.exception.provider.MalformedRequestException;
import org.eclipse.basyx.vab.exception.provider.NotAnInvokableException;
import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.eclipse.basyx.vab.exception.provider.ResourceAlreadyExistsException;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A generic VAB model provider.
 * 
 * @author espen
 */
public class VABModelProvider implements IModelProvider {

	private static final Logger logger = LoggerFactory.getLogger(VABModelProvider.class);
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
		this.elements = elements;
	}

	@Override
	public Object getModelPropertyValue(String path) throws ProviderException {
		// Check empty paths
		VABPathTools.checkPathForNull(path);
		if (VABPathTools.isEmptyPath(path)) {
			return handler.postprocessObject(elements);
		}

		Object element = getTargetElement(path);
		Object postProcessedElement = handler.postprocessObject(element);
		
		return postProcessedElement;
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws ProviderException {
		// Check empty paths
		VABPathTools.checkPathForNull(path);
		if (VABPathTools.isEmptyPath(path)) {
			// Empty path => parent element == null => replace root, if it exists
			if (elements != null) {
				elements = newValue;
			}
			return;
		}

		Object parentElement = getParentElement(path);
		String propertyName = VABPathTools.getLastElement(path);

		// Only write values, that already exist
		Object thisElement = handler.getElementProperty(parentElement, propertyName);
		if (parentElement != null && propertyName != null && thisElement != null) {
			handler.setModelPropertyValue(parentElement, propertyName, newValue);
		}
	}

	@Override
	public void createValue(String path, Object newValue) throws ProviderException {
		// Check empty paths
		VABPathTools.checkPathForNull(path);
		if (VABPathTools.isEmptyPath(path)) {
			// The complete model should be replaced if it does not exist
			if (elements == null) {
				elements = newValue;
			} else {
				throw new ResourceAlreadyExistsException("Element \"/\" does already exist.");
			}
			return;
		}

		// Find parent & name of new element
		Object parentElement = getParentElement(path);
		String propertyName = VABPathTools.getLastElement(path);

		// Only create new, never replace existing elements
		if (parentElement != null) {
			Object childElement = getElementPropertyIfExistent(parentElement, propertyName);
			if (childElement == null) {
				// The last path element does not exist
				handler.setModelPropertyValue(parentElement, propertyName, newValue);
			} else {
				// The last path element does exist
				// Try to create the value, should work if it is a list
				if( ! handler.createValue(childElement, newValue)) {
					// createValue failed
					throw new ResourceAlreadyExistsException("Element \"" + path + "\" does already exist.");
				}
			}
		} else {
			logger.warn("Could not create element, parent element does not exist for path '{}'", path);
			throw new ResourceNotFoundException("Parent element for \"" + path + "\" does not exist.");
		}
	}

	@Override
	public void deleteValue(String path) throws ProviderException {
		// Check null path
		VABPathTools.checkPathForNull(path);

		Object parentElement = getParentElement(path);
		String propertyName = VABPathTools.getLastElement(path);
		if (parentElement != null && propertyName != null && !handler.deleteValue(parentElement, propertyName)) {
			throw new ResourceNotFoundException("Element \"" + path + "\" does not exist.");
		}
	}

	@Override
	public void deleteValue(String path, Object obj) throws ProviderException {
		// Check null path
		VABPathTools.checkPathForNull(path);
		if (path.equals("")) {
			throw new MalformedRequestException("Path must not be empty.");
		}

		Object parentElement = getParentElement(path);
		String propertyName = VABPathTools.getLastElement(path);
		if (parentElement != null && propertyName != null) {
			Object childElement = handler.getElementProperty(parentElement, propertyName);
			if (childElement != null && !handler.deleteValue(childElement, obj)) {
				// Value was not deleted by any handler, it is contained in a Map
				throw new MalformedRequestException("Can not delete element \"" + path + "\" by value.");
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object invokeOperation(String path, Object... parameters) throws ProviderException {
		VABPathTools.checkPathForNull(path);
		Object childElement = getModelPropertyValue(path);

		// Invoke operation for function interfaces
		if (childElement instanceof Function<?, ?>) {
			Function<Object[], Object> function = (Function<Object[], Object>) childElement;
			return function.apply(parameters);
		} else {
			throw new NotAnInvokableException("Element \"" + path + "\" is not a function.");
		}
	}

	/**
	 * Get the parent of an element in this provider. The path should include the path to the element separated by '/'.
	 * E.g., for accessing element c in path a/b, the path should be a/b/c.
	 */
	private Object getParentElement(String path) throws ProviderException {
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
	 * Calls getElementProperty and catches ResourceNotFOundException 
	 */
	private Object getElementPropertyIfExistent(Object parentElement, String propertyName) throws ProviderException {
		try {
			return handler.getElementProperty(parentElement, propertyName);
		} catch (ResourceNotFoundException e) {
			return null;
		}
	}

	/**
	 * Instead of returning the parent element of a path, this function gives the target element.
	 * E.g., it returns c for the path a/b/c
	 */
	protected Object getTargetElement(String path) throws ProviderException {
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
