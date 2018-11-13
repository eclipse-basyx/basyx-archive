package org.eclipse.basyx.sdk.provider.hashmap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.basyx.aas.api.reference.IElementReference;
import org.eclipse.basyx.aas.api.services.IModelProvider;
import org.eclipse.basyx.vab.tools.VABPathTools;





/**
 * A simple VAB model provider based on a HashMap.
 * 
 * This provider demonstrates the inclusion of new data sources to the VAB
 * 
 * @author kuhn
 *
 */
public class VABHashmapProvider implements IModelProvider {
	
	
	/**
	 * Hashmap that stores properties
	 */
	protected Map<String, Object> elements = new HashMap<>();

	
	
	
	/**
	 * Default constructor
	 */
	public VABHashmapProvider() {
		// Do nothing
	}
	
	
	
	
	/**
	 * Get the parent of an element in this provider. The scope is always a map. The path should include
	 * the path to the element separated by '/'. E.g., for accessing element c in path a/b, the path
	 * should be a/b/c. 
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Object> getParentElement(String path) {
		// Split path into its elements, separated by '/'
		String[] pathElements = VABPathTools.splitPath(path);
		
		// Get element
		// - Current element scope
		Map<String, Object> currentScope = elements;
		// - Get element
		for (int i=0; i<pathElements.length-1; i++) {
			// Get element
			Object element = currentScope.get(pathElements[i]);
			
			// Check for null pointer and map type
			if (element == null) return null;
			if (!(element instanceof Map)) return null;
			
			// Update scope
			currentScope = (Map<String, Object>) element;
		}

		// Return scope
		return currentScope;
	}
	
	
	
	/**
	 * Return the scope of an element with path
	 */
	@Override
	public String getElementScope(String elementPath) {
		System.out.println("GetElementScope:"+elementPath);
		
		// For now do not respond with a scope
		return null;
	}

	
	/**
	 * Get model property value from map
	 */
	@Override 
	public Object getModelPropertyValue(String path) {
		System.out.println("GetPropertyValue:"+path);

		// Split path into its elements, separated by '/'
		String[] pathElements = VABPathTools.splitPath(path);

		// Get parent of element
		Map<String, Object> parentElement = getParentElement(path);
		
		// Get element from scope
		Object result =  parentElement.get(pathElements[pathElements.length-1]);
		
		// Return element
		return result;
	}

	
	/**
	 * Change a property value
	 */
	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {
		System.out.println("SetPropertyValue1:"+path);

		// Split path into its elements, separated by '/'
		String[] pathElements = VABPathTools.splitPath(path);

		// Get parent of element
		Map<String, Object> parentElement = getParentElement(path);
		
		// Set element value
		parentElement.put(pathElements[pathElements.length-1], newValue);
	}

	
	@Override
	public void setModelPropertyValue(String path, Object... newEntry) throws Exception {
		System.out.println("SetPropertyValue2:"+path);

		// TODO Auto-generated method stub
	}


	/**
	 * Create a new property in the VAB element, a sub element, in a collection, or in a map
	 */
	@Override @SuppressWarnings({ "rawtypes", "unchecked" })
	public void createValue(String path, Object newValue) throws Exception {
		System.out.println("CreateValue1:"+path+" ("+newValue+")");

		// Split path into its elements, separated by '/'
		String[] pathElements = VABPathTools.splitPath(path);
		// - Element name
		String elementName = pathElements[pathElements.length-1];

		// Get parent of element
		Map<String, Object> parentElement = getParentElement(path);
		
		// Check if element is present and a collection, in this case add new element to collection
		if (parentElement.containsKey(elementName)) {
			// Get element
			Object element = parentElement.get(elementName);
			
			// Add element to collection
			if (element instanceof Collection) {
				// Add element to collection and return
				((Collection) element).add(newValue);
				return;
			}
		}
		
		// Target is map, put key and element value into map
		parentElement.put(pathElements[pathElements.length-1], newValue);
	}
	

	@Override
	public void deleteValue(String path) throws Exception {
		System.out.println("DeleteValue1:"+path);

		// Split path into its elements, separated by '/'
		String[] pathElements = VABPathTools.splitPath(path);
		// - Element name
		String elementName = pathElements[pathElements.length-1];

		// Get parent of element
		Map<String, Object> parentElement = getParentElement(path);

		// Remove named element
		parentElement.remove(elementName);
	}

	
	
	@Override @SuppressWarnings("rawtypes")
	public void deleteValue(String path, Object obj) throws Exception {
		System.out.println("DeleteValue2:"+path);

		// Split path into its elements, separated by '/'
		String[] pathElements = VABPathTools.splitPath(path);
		// - Element name
		String elementName = pathElements[pathElements.length-1];

		// Get parent of element
		Map<String, Object> parentElement = getParentElement(path);
		// - Get element
		Object element = parentElement.get(elementName);

		// Check if element is a collection
		if (!(element instanceof Collection)) return;
		// - Remove collection element
		((Collection) element).remove(obj);
	}

	
	
	/**
	 * Invoke an operation on the SQL database. Operations are SQL statements defined in the properties file.  
	 */
	@Override @SuppressWarnings("unchecked")
	public Object invokeOperation(String path, Object[] parameter) throws Exception {
		System.out.println("Invoke:"+path);

		// Split path into its elements, separated by '/'
		String[] pathElements = VABPathTools.splitPath(path);
		// - Element name
		String elementName = pathElements[pathElements.length-1];

		// Get parent of element
		Map<String, Object> parentElement = getParentElement(path);
		// - Get element
		Object element = parentElement.get(elementName);

		// Check if element is a Function
		if (!(element instanceof Function)) return null;
		
		// Invoke operation
		return ((Function) element).apply(parameter);
	}

	
	@Override
	public Map<String, IElementReference> getContainedElements(String path) {
		System.out.println("Get contained elements:"+path);

		// TODO Auto-generated method stub
		return null;
	}

}
