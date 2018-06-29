package org.eclipse.basyx.aas.impl.provider;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.basyx.aas.api.annotation.AASOperation;
import org.eclipse.basyx.aas.api.annotation.AASProperty;
import org.eclipse.basyx.aas.api.reference.IElementReference;
import org.eclipse.basyx.aas.api.resources.basic.IElement;
import org.eclipse.basyx.aas.api.resources.basic.IElementContainer;
import org.eclipse.basyx.aas.api.resources.basic.IProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.api.services.IModelProvider;
import org.eclipse.basyx.aas.impl.reference.ElementRef;
import org.eclipse.basyx.aas.impl.resources.basic.DataType;
import org.eclipse.basyx.aas.impl.resources.basic.Property;
import org.eclipse.basyx.aas.impl.resources.basic.SubModel;
import org.eclipse.basyx.aas.impl.tools.BaSysID;




/**
 * Provider class that export a Java object as BaSys model 
 * 
 * @author kuhn
 *
 */
public class JavaObjectProvider extends AbstractModelScopeProvider implements IModelProvider {

	
	
	/**
	 * Split a property path
	 */
	protected String[] splitPropertyPath(String pathString) {
		// Return empty array for empty string
		if (pathString.length() == 0) return new String[0];

		// Split string into path segments
		return pathString.split("[/\\.]");
	}

	
	/**
	 * Get all fields that are tagged with AASProperty
	 */
	protected Collection<Field> getAllFields(Class<?> cls) {
		// Return value
		Collection<Field> result = new LinkedList<>();
		
		// Try to find field
	    while (cls != null) {
	        try {
	        	// Get fields
	        	Field[] fields = cls.getDeclaredFields();
	        	
	        	// Add fields to result
	        	for (Field field: fields) if (field.getAnnotation(AASProperty.class) != null) result.add(field);
	        } catch (Exception e) {
	        	// Do nothing
	        }
	        
	        cls = cls.getSuperclass();
	    }
		
		// Return result
		return result;
	}

	
	/**
	 * Get all methods that are tagged with AASOperation
	 */
	protected Collection<Method> getAllMethods(Class<?> cls) {
		// Return value
		Collection<Method> result = new LinkedList<>();
		
		// Try to find field
	    while (cls != null) {
	        try {
	        	// Get fields
	        	Method[] methods = cls.getDeclaredMethods();
	        	
	        	// Add methods to result
	        	for (Method method: methods) if (method.getAnnotation(AASOperation.class) != null) result.add(method);
	        } catch (Exception e) {
	        	// Do nothing
	        }
	        
	        cls = cls.getSuperclass();
	    }
		
		// Return result
		return result;
	}

	
	/**
	 * Get a named field
	 */
	protected Field getNamedField(Class<?> cls, String name) {
		// Return value
		Field result = null;
		
		// Try to find field
	    while (cls != null && result == null) {
	        try {
	        	result = cls.getDeclaredField(name);
	        } catch (Exception e) {
	        	// Do nothing
	        }
	        
	        cls = cls.getSuperclass();
	    }
		
		// Return result
		return result;
	}

	
	/**
	 * Get a named method
	 * 
	 * Searches the given class type and all base types for a declared method
	 * - FIXME: No method overloading yet - do we want to have?
	 * 
	 * @param cls Class type to be searched
	 * @param name Searched method name
	 * 
	 * @return Method descriptor
	 */
	protected Method getNamedOperation(Class<?> cls, String name) {
		// Get all methods, including those of super classes
		Method[] methods = cls.getMethods();
		
		// Try to find method
		for (Method method: methods) {
			if (method.getName().equals(name)) return method;
		}
		
		// No method with matching name was found
		return null;
	}

	
	/**
	 * Get named property of object
	 */
	protected Object getNamedProperty(Object obj, String propertyName) {
		
		// Store result
		Object result = null;
		
		System.out.println("--- Getting:"+propertyName+" - "+obj);
		
		// Process supported property types
		if ((result = getIElementChildren(obj, propertyName)) != null) return result;
		if ((result = getMapProperty(obj, propertyName)) != null) return result; // for collection type
		if ((result = getFieldProperty(obj, propertyName)) != null) return result;
		
		// Check for dynamic properties
		if ((result = ((SubModel) obj).getDynamicPropertyValue(propertyName)) != null) return result; // FIXME: Endless loop if BasysGet is processed before set.
		if ((result = ((SubModel) obj).getProperties().get(propertyName)) != null) return result;
		
		
		System.out.println("Object not found");
		// Object not found
		return result;
	}
	
	
	/**
	 * Get predefined property type
	 */
	protected Collection<IElement> getIElementChildren(Object obj, String propertyName) {
		// Check predefined fields
		if (propertyName.equals("children")) {
			// Store result
			Collection<IElement> result = new LinkedList<>();
			
			// Process element containers (AAS, SubModel, NestedProperty)
			if (obj instanceof IElementContainer) {
				// Extract AAS contents
				IElementContainer elementContainer = (IElementContainer) obj;
				// - Get element names
				result = elementContainer.getElements().values();
			}
			
			// Return result
			return result;
		}
		
		// Parameter "propertyName" does not identify a predefined property
		return null;
	}
	
	
	/**
	 * Get a map property
	 */
	@SuppressWarnings("unchecked")
	protected Object getMapProperty(Object obj, String propertyName) {		
		// This only works for maps
		if (!(obj instanceof Map)) return null;

		System.out.println("getMapProperty "+ obj + " "+ obj.getClass() + ": " + propertyName);

		// Safe up cast
		Map<String, Object> valueMap = (Map<String, Object>) obj;
		
		// Return map element
		return valueMap.get(propertyName);
	}
	
	
	/**
	 * Get a field property
	 */
	protected Object getFieldProperty(Object obj, String propertyName) {
		
		System.out.println("getFieldProperty " + obj +" "+obj.getClass() + ": " +propertyName);
		
		// Lookup field
		Field propertyField = getNamedField(obj.getClass(), propertyName);
		
		// Try to get field value
		try {
			// Return field value
			System.out.println("Acc");
			propertyField.setAccessible(true);
			return propertyField.get(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// Print stack trace
			e.printStackTrace();
		} catch (NullPointerException e) {
			return null;
		}
		
		// Do not return anything
		return null;
	}	

	
	
	
	/**
	 * Return an element from a collection
	 */
	@SuppressWarnings("rawtypes")
	protected Object getCollectionElement(Collection target, Object element) {
		
		System.out.println(target.toString() + " size="+target.size() + " object="+element.toString());
		
		// Check for ID if element is instance of IElement
		if (element instanceof IElement) {
			// Safe type cast
			IElement iElement = (IElement) element;
			
			// Check collection elements
			for (Object obj: target) {
				// Compare elements - for IElements, the ID value is compared
				if (obj instanceof IElement) {if (((IElement) obj).getId().equals(iElement.getId())) return obj;}
			}
		}
		
		// Check regular element - this will only work for primitive elements
		if (target.contains(element)) return element;
		
		// Element is not found
		return null;
	}

	
	/**
	 * Check if a collection contains a value or an IElement with a given ID
	 */
	@SuppressWarnings("rawtypes")
	protected boolean collectionContains(Collection target, Object element) {
		// Check if collection contains element
		if (getCollectionElement(target, element) != null) return true;
		
		// Element was not found
		return false;
	}

	
	/**
	 * Delete an element from a collection
	 */
	@SuppressWarnings("rawtypes")
	protected void removeFromCollection(Collection target, Object element) {
		// Store element in collection
		Object collectionElement = null;
		
		// Check if collection contains element
		if ((collectionElement = getCollectionElement(target, element)) == null) return;
		
		// Remove element
		// - Serialized elements do not have same identity as collection elements, therefore we only support IElement (with ID) or primitive types
		target.remove(collectionElement);
	}

	
	/**
	 * Get property from Java instance
	 */
	protected Object getModelProperty(String path, int skippedPathEntries) {

		// Process hard coded requests first
		switch(path) {
			// Request children of the root model, e.g. hosted models
			case "children": return basysModels.values();

			// Default request - regular request for a property
			default:
				// Object reference
				Object   currentObject = null;

				// Get model IDs and path
				String   aasID      = BaSysID.instance.getAASID(path);
				String   submodelID = BaSysID.instance.getSubmodelID(path);
				String   propPath   = BaSysID.instance.getPath(path);
				String[] pathArray  = splitPropertyPath(propPath);

				System.out.println("AASID:"+aasID);
				System.out.println("SM_ID:"+submodelID);
				
				// Resolve AAS and sub model
				if (     aasID.length() > 0) currentObject = basysModels.get(aasID);
				if (submodelID.length() > 0) currentObject = basysModels.get(submodelID);
				
				// Resolve property path
				for (int i=0; i<pathArray.length-skippedPathEntries; i++) {
					System.out.println("GG:"+pathArray[i]+" -- "+currentObject);
					
					currentObject = getNamedProperty(currentObject, pathArray[i]);
				}
				// - Return property
				return currentObject;
		}		
	}
	
	
	/**
	 * Get a sub model property value
	 * 
	 * @param path Path to the requested value
	 */
	@Override
	public Object getModelPropertyValue(String path) {
		// Return model property
		return getModelProperty(path, 0);
	}

	
	/**
	 * Set a sub model property value
	 * 
	 * @param path Path to the requested value
	 * @param newValue Updated value
	 */
	@Override
	public void setModelPropertyValue(String path, Object newValue) {
		// Get container element that contains the to be changed element
		Object containerElement = getModelProperty(path, 1);
		
		// Change field 
		// - Lookup field
		Field propertyField = getNamedField(containerElement.getClass(), BaSysID.instance.getLastPathEntries(path, 1)[0]);
		
		// If not found, look for dynamic properties
		if (containerElement instanceof SubModel &&
				propertyField == null) {
			
			SubModel submodel = (SubModel) containerElement;
			IProperty dynamicProperty = submodel.getProperties().get(BaSysID.instance.getLastPathEntries(path, 1)[0]);
			
			
			// If dynamic property exists, set dynamic property value
			if (dynamicProperty != null) {
				submodel.putDynamicPropertyValue(dynamicProperty.getId(), newValue);
				return;
			}
			
			
		}
		
		// - Update field value
		try {propertyField.setAccessible(true); propertyField.set(containerElement, newValue);} catch (IllegalArgumentException | IllegalAccessException e) {e.printStackTrace();}		
	}
	
	
	
	/**
	 * Create/insert a value in a collection or map
	 * Creates a new Property Instance in a submodel
	 * 
	 * @param path Path to the collection
	 * @param newValue Inserted value. 
	 */
	@Override @SuppressWarnings({ "rawtypes", "unchecked" })
	public void createValue(String path, Object parameter) {
		
		// Get target reference
		Object target = getModelPropertyValue(path);
		
		// Type check - create Property
		if (target instanceof SubModel) {
			SubModel submodel = (SubModel) target;
			
			// Extract Parameters
			// - Extract Property Reference
			IElementReference ref = (IElementReference) ((Object[]) parameter)[0];
			
			// - Extract Attributes (name, type, isco, ismap)
			String name = (String) ((Object[]) parameter)[1];
			DataType type = DataType.valueOf( (String) ((Object[]) parameter)[2]);
			boolean isCollection = (boolean) ((Object[]) parameter)[3];
			boolean isMap = (boolean) ((Object[]) parameter)[4];
				
			// Create new dynamic property instance without a value and add it to the existing submodel
			Property property = new Property();
			property.setId(ref.getId());
			property.setName(name); 
			property.setDataType(type);
			property.setCollection(isCollection);
			property.setMap(isMap);
			submodel.addProperty(property);
			submodel.putDynamicPropertyValue(ref.getId(), null); // TODO what should be the default value?
			
			System.out.println("Added new property '" + property.getId() + "' to Submodel: " + submodel.getId());
			return;
		}
		
		// Type check - add value to collection
		if (target instanceof Collection) {
			
			// Extract new member
			Object addedMember = ((Object[]) parameter)[0];
			
			// Check if element is already inside, delete old value in this case
			if (collectionContains((Collection) target, addedMember)) removeFromCollection((Collection) target, addedMember);
			
			System.out.println("Adding value " + addedMember + " to collection: " + target);
			((Collection) target).add(addedMember);
			return;
		
		// Type check - add key-value pair to map
		}  else if (target instanceof Map) {
			
			// Extract new member
			Object key = ((Object[]) parameter)[0];
			Object value = ((Object[]) parameter)[1];
			
			// Check if ID is already inside, overwrite value in this case
			System.out.println("Adding entry " + key + " -> " + value +" from map: " + target);
			((Map) target).put(key, value);
			return;
		}
	}
	
	
	/**
	 * Delete a value from a collection or map
	 * 
	 * @param path Path to the collection
	 * @param paramete an array of objects size one. If Collection type, it is the member- if Map type, it is the key
	 */
	@Override @SuppressWarnings({ "rawtypes" })
	public void deleteValue(String path, Object parameter) {
		
		// Get collection reference
		Object target = getModelPropertyValue(path);
		
		// Extract value to be deleted
		Object deletedValue = ((Object[]) parameter)[0];

		// Type check
		if (target instanceof Collection) {
			
			// Check if ID is already inside, delete value in this case
			System.out.println("Delete value " + deletedValue + " from collection: " + target);
			if (collectionContains((Collection) target, deletedValue)) removeFromCollection((Collection) target, deletedValue);
			
		} else if (target instanceof Map) {
			
			// Check if ID is already inside, delete value in this case
			System.out.println("Delete entry " + deletedValue + " from map: " + target);
			((Map) target).remove(deletedValue);
		}
	}
	
	
	/**
	 * Invoke an operation
	 */
	@Override
	public Object invokeOperation(String path, Object[] parameter) throws Exception {
		// Get container element that contains the operation to be invoked
		Object containerElement = getModelProperty(path, 1);

		// Lookup operation
		Method operation = getNamedOperation(containerElement.getClass(), BaSysID.instance.getLastPathEntries(path, 1)[0]);
		
		// Invoke operation
		try {
			return operation.invoke(containerElement, parameter);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			System.out.println(e.toString());
			
			// Operation invocation failed
			throw e;
		}
	}
	
	
	/**
	 * Get contained sub model elements
	 * 
	 * Contained sub model elements are returned as Map of key/value pairs. Keys are Strings, values are 
	 * ElementRef objects that contain a reference to a complex object instance.
	 * 
	 * @param path Path to sub model or property
	 * @return Contained properties
	 */
	@Override 
	public Map<String, IElementReference> getContainedElements(String path) {
		// Return value
		Map<String, IElementReference> result = new HashMap<>();

		// Get collection reference
		Object target = getModelPropertyValue(path);
		
		System.out.println("ContainedProperties: "+target);
		
		// Process return types
		if (target instanceof Map)               return createMapFromMap((Map<String, IElement>) target);
		if (target instanceof Collection)        return createMapFromCollection((Collection<IElement>) target);
		if (target instanceof IElementContainer) return createMapFromIElementContainer((IElementContainer) target);
		// Simple collection: keys are numbered

		// No contained properties
		return result;
	}


	protected static Map<String, IElementReference> createMapFromMap(Map<String, IElement> elements) {
		// Return value
		Map<String, IElementReference> result = new HashMap<>();
		
		// Process keys
		for (String key: elements.keySet()) {
			IElement value = elements.get(key);
			
			if (value instanceof IElement) result.put(key, new ElementRef((IElement) value));
		}
		
		// Return elements
		return result;
	}


	protected static Map<String, IElementReference> createMapFromCollection(Collection<IElement> elements) {
		// Return value
		Map<String, IElementReference> result = new HashMap<>();

		// Process elements
		for (IElement value: elements) result.put(value.getId(), new ElementRef((IElement) value));

		// Return elements
		return result;
	}


	protected static Map<String, IElementReference> createMapFromIElementContainer(IElementContainer container) {
		// Return value
		Map<String, IElementReference> result = new HashMap<>();
		
		// Container Elements
		Map<String, IElement> containerElements = container.getElements();

		// Add contained properties
		for (String elementKey: container.getElements().keySet()) result.put(elementKey, new ElementRef(containerElements.get(elementKey)));

		// Return member names
		return result;
	}
}
