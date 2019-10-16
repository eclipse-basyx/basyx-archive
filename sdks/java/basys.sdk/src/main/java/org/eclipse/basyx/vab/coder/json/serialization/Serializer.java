package org.eclipse.basyx.vab.coder.json.serialization;

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
	public String serialize(Object value);

	
	/**
	 * Deserialize a primitive or complex value from JSON object
	 */
	public Object deserialize(String serializedValue);
}
