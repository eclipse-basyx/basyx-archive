package org.eclipse.basyx.aas.api.serializableobject;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.basyx.aas.api.annotation.AASProperty;

/**
 * Base class for complex serializable objects 
 * 
 * @author kuhn
 *
 */
public class SerializableObject implements ISerializableObject {

	
	/**
	 * Serialization ID counter
	 */
	protected static int idCounter = 0;
	
	
	/**
	 * Get a new ID
	 */
	protected static int getNewID() {
		return idCounter++;
	}
	
	
	
	
	/**
	 * Object ID - must be disjoint in one serialized object graph
	 */
	protected int objectID = -1;
	
	
	
	/**
	 * Constructor
	 */
	public SerializableObject() {
		// Create a new object id
		objectID = getNewID();
	}
	
	
	/**
	 * Get object ID
	 */
	public int getObjectID() {
		// Return object ID
		return objectID;
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
	 * Get all fields of an instance
	 */
	public Map<String, Object> getElements() {
		// Store return value
		Map<String, Object> result = new HashMap<>();
		
		// Get all fields of instance
		Collection<Field> fields = getAllFields(this.getClass());
		// Get field values
		for (Field field: fields) {
			try {
				field.setAccessible(true);
				result.put(field.getName(), field.get(this));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Return map
		return result;
	}
}

