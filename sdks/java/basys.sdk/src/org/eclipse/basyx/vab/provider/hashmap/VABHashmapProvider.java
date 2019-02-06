package org.eclipse.basyx.vab.provider.hashmap;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.vab.core.IModelProvider;
import org.eclipse.basyx.vab.core.tools.VABPathTools;

/**
 * A simple VAB model provider based on a HashMap.
 * 
 * This provider demonstrates the inclusion of new data sources to the VAB
 * 
 * @author kuhn, schnicke
 *
 */
public class VABHashmapProvider implements IModelProvider {

	/**
	 * Hashmap that stores properties
	 */
	protected Map<String, Object> elements;

	/**
	 * Default constructor
	 */
	public VABHashmapProvider() {
		this.elements = new HashMap<>();
	}

	/**
	 * Constructor that accepts an initial HashMap
	 * 
	 * TODO check if any element key comprises '/' and throw RunTimeException
	 */
	public VABHashmapProvider(Map<String, Object> elements) {
		this.elements = elements;
	}

	/**
	 * Get Elements for AAS or Submodel request
	 */
	public Map<String, Object> getElements() {
		return elements;
	}

	/**
	 * Set elements (only for derived classes, e.g. facades)
	 */
	protected void setElements(Map<String, Object> newElements) {
		elements = newElements;
	}

	/**
	 * Get the parent of an element in this provider. The scope is always a map. The
	 * path should include the path to the element separated by '/'. E.g., for
	 * accessing element c in path a/b, the path should be a/b/c.
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Object> getParentElement(String path) {
		// Split path into its elements, separated by '/'
		String[] pathElements = VABPathTools.splitPath(path);

		// Get element
		// - Current element scope
		Map<String, Object> currentScope = elements;
		// - Get element
		for (int i = 0; i < pathElements.length - 1; i++) {
			// Get element
			Object element = null;
			
			// If there is a leading '/' skip null entry
;			if (pathElements[0].equals("") && pathElements.length > 0) { pathElements[0] = "None"; continue; }
			
			System.out.println("Get Parent Element: "+ pathElements[i] + " -> " + Arrays.toString(currentScope.keySet().toArray()));
			
			// Try to find parent despite not knowing if leading "/" must be added 
			if ((element = currentScope.get(pathElements[i])) != null) 	{	 currentScope = (Map<String, Object>) element; }
			else if ((element = currentScope.get("/" + pathElements[i])) != null) { currentScope = (Map<String, Object>) element; }
			
			System.out.println("  - Element:"+element);

			
			if (element == null ) 		   return null;
			if (!(element instanceof Map)) return null;
		}
		
		System.out.println("Get Parent Element: returned parent of " + Arrays.toString(currentScope.keySet().toArray()));

		// Return scope
		return currentScope;
	}

	/**
	 * Get model property value from map
	 */
	@Override
	public Object getModelPropertyValue(String path) {
		System.out.println("GetPropertyValue:" + path);

		// Split path into its elements, separated by '/'
		String[] pathElements = VABPathTools.splitPath(path);

		// Get parent of element
		Map<String, Object> parentElement = getParentElement(path);
		
		// Return parent element if path was empty, in this case the whole map was requested
		if (path.length() == 0) return parentElement;
		
		// Get element from scope
		Object result = parentElement.get(pathElements[pathElements.length - 1]);
		
		// Return element
		return result;
	}

	/**
	 * Change a property value
	 */
	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {
		System.out.println("SetPropertyValue1:" + path);

		// Split path into its elements, separated by '/'
		String[] pathElements = VABPathTools.splitPath(path);

		// Get parent of element
		Map<String, Object> parentElement = getParentElement(path);

		// Set element value
		parentElement.put(pathElements[pathElements.length - 1], newValue);
	}

	/**
	 * Create a new property in the VAB element, a sub element, in a collection, or
	 * in a map
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void createValue(String path, Object newValue) throws Exception {
		System.out.println("CreateValue1:" + path + " (" + newValue + ")");

		// Split path into its elements, separated by '/'
		String[] pathElements = VABPathTools.splitPath(path);
		// - Element name
		String elementName = pathElements[pathElements.length - 1];

		// Get parent of element
		Map<String, Object> parentElement = getParentElement(path);

		// Check if element is present and a collection, in this case add new element to
		// collection
		if (parentElement.containsKey(elementName)) {
			// Get element
			Object element = parentElement.get(elementName);

			// Add element to collection
			if (element instanceof Collection) {
				// Add element to collection and return
				((Collection<Object>) element).add(newValue);
				return;
			}
		}

		// Target is map, put key and element value into map
		parentElement.put(pathElements[pathElements.length - 1], newValue);
	}

	@Override
	public void deleteValue(String path) throws Exception {
		System.out.println("DeleteValue1:" + path);

		// Split path into its elements, separated by '/'
		String[] pathElements = VABPathTools.splitPath(path);
		// - Element name
		String elementName = pathElements[pathElements.length - 1];

		// Get parent of element
		Map<String, Object> parentElement = getParentElement(path);

		// Remove named element
		parentElement.remove(elementName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteValue(String path, Object obj) throws Exception {
		System.out.println("DeleteValue2:" + path);

		// Split path into its elements, separated by '/'
		String[] pathElements = VABPathTools.splitPath(path);
		// - Element name
		String elementName = pathElements[pathElements.length - 1];

		// Get parent of element
		Map<String, Object> parentElement = getParentElement(path);
		// - Get element
		Object element = parentElement.get(elementName);

		// Check if element is a collection
		if (element instanceof Collection) {
			// - Remove collection element
			((Collection<?>) element).remove(obj);
		} else if (element instanceof Map) {
			((Map<Object, Object>) element).remove(obj);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object invokeOperation(String path, Object[] parameter) throws Exception {
		System.out.println("Invoke:" + path);
		Object element = getModelPropertyValue(path);

		// Check if element is a Function
		if (!(element instanceof Function<?, ?>))
			return null;

		// Invoke operation
		try {
			return ((Function<Object, Object[]>) element).apply(parameter);
		} catch (Exception e) {
			throw new ServerException(e.getClass().toString(), e.getMessage());
		}
	}

}
