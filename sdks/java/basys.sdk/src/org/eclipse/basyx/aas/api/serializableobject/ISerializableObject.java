package org.eclipse.basyx.aas.api.serializableobject;

import java.util.Map;



/**
 * Interface for a serializable object
 * 
 * @author kuhn
 *
 */
public interface ISerializableObject {

	
	/**
	 * Get object ID
	 */
	public int getObjectID();
	
	
	/**
	 * Get map of elements
	 */
	public Map<String, Object> getElements();
}
