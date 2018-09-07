package org.eclipse.basyx.aas.backend;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.serializableobject.ISerializableObject;



/**
 * Connected serializable object
 * 
 * @author kuhn
 *
 */
public class ConnectedSerializableObject implements ISerializableObject {

	
	
	/**
	 * Store a map of elements
	 */
	protected Map<String, Object> elements = new HashMap<>();
	
	
	/**
	 * Store object ID of this serialized object
	 */
	protected int objId = -1;
	
	
	
		

	/**
	 * Constructor
	 */
	public ConnectedSerializableObject() {
		// Do nothing
	}
	
	
	/**
	 * Set object ID for this connected serialized element
	 */
	public void setObjectId(int newId) {
		// Store object id
		objId = newId;
	}
		
	
	/**
	 * Return object ID of this object
	 */
	@Override
	public int getObjectID() {
		// Return object id
		return objId;
	}

	
	/**
	 * Get elements of this serialized object
	 */
	@Override
	public Map<String, Object> getElements() {
		// Return contained elements
		return elements;
	}

	
	/**
	 * Add an element to this connected serialized object
	 */
	public void addElement(String elementName, Object elementRef) {
		// Add element
		elements.put(elementName, elementRef);
	}
}
