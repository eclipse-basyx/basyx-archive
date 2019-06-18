package org.eclipse.basyx.aas.backend.http.tools;

import java.util.Map;



/**
 * A BaSys serializer
 * 
 * @author kuhn
 *
 */
public interface Serializer {
	
	/**
	 * Serialize a primitive or complex value into JSON object
	 */
	public Map<String, Object> serialize(Object value, String scope);

	
	/**
	 * Serialize a primitive or complex value into JSON object
	 */
	public Map<String, Object> serialize(Object value);

	
	/**
	 * Deserialize a primitive or complex value from JSON object
	 */
	public Object deserialize(Map<String, Object> serializedValue);
}
