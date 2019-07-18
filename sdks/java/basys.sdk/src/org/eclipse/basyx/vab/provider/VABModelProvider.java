package org.eclipse.basyx.vab.provider;

import java.util.Map;
import java.util.function.Function;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.operation.Operation;
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
		
		// Only write values, that already exist
		Object thisElement = handler.getElementProperty(parentElement, propertyName);
		
		if (parentElement != null && propertyName != null && thisElement != null) {
			newValue = handler.preprocessObject(newValue);
			handler.setModelPropertyValue(parentElement, propertyName, newValue);
		}
	}

	@Override
	public void createValue(String path, Object newValue) throws Exception {
		System.out.println("CRCRCR3:"+path+" -- "+newValue);
		
		// Local variables
		Object parentElement = getParentElement(path);
		String propertyName = VABPathTools.getLastElement(path);
		
		System.out.println("CRCRCR4:"+propertyName+" -- "+parentElement);

		
		// Do not process "null" paths
		if (path == null) return;
		
		// Corner case - the complete map should be replaced (path "/" or "")
		if ((path.length() == 0) || (path.equals("/"))) {
			// Overwrite elements
			elements = newValue;
			
			// End processing
			return;
		}

		// All other cases
		if (parentElement != null && propertyName != null) {
			newValue = handler.preprocessObject(newValue);
			Object childElement = handler.getElementProperty(parentElement, propertyName);
			if (childElement == null) {
				handler.setModelPropertyValue(parentElement, propertyName, newValue);
			} else {
				handler.createValue(childElement, newValue);
			}
			
			// End processing
			return;
		}
		
		// Indicate error
		throw new RuntimeException("Undefined parent element requested");
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
		System.out.println("OPERATION INVOKE:"+path+" "+elements);
		
		// Object to be invoked
		Object childElement = null;
		
		// Corner case, only an operation is provided
		if (path.length() == 0 || path.equals("/")) {
			childElement = elements;
		} else {
			Object parentElement = getParentElement(path);
			String operationName = VABPathTools.getLastElement(path);
			if (parentElement != null && operationName != null) {
				childElement = handler.getElementProperty(parentElement, operationName);
			}
		}
		
		// Invoke operation
		if (childElement != null && childElement instanceof Function<?, ?>) {
			
			// unwrap parameters
			int i = 0;
			for (Object param : parameters) {
				if (param instanceof Map<?,?>) {
					Map<String, Object> map = (Map<String, Object>) param;
					
					if (map.get("valueType") != null && map.get("value") != null) {
						parameters[i] = map.get("value");
					}
				}
				i++;
			}
			
			
			Function<Object, Object[]> function = (Function<Object, Object[]>) childElement;
			return function.apply(parameters);
		} else {
			if (childElement != null && childElement instanceof Operation) {
				// Build path
				if (path.endsWith("/"))
					return invokeOperation(path+"invokable", parameters);
				else 
					return invokeOperation(path+"/invokable", parameters); // TODO C# needs to be adapted so C# can invoke operations on java
			}
		}

		// No operation found
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
