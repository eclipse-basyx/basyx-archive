package org.eclipse.basyx.tools.sqlproxy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.Collection;
import java.util.Map;

import org.eclipse.basyx.tools.sqlproxy.exception.UnknownElementTypeException;



/**
 * Represent a SQL element table row
 * 
 * @author kuhn
 *
 */
public class SQLTableRow {

	
	/**
	 * Type constant: Null
	 */
	public static final int TYPE_NULL = 0;

	
	/**
	 * Type constant: Integer
	 */
	public static final int TYPE_INT = 1;

	
	/**
	 * Type constant: Float
	 */
	public static final int TYPE_FLOAT = 2;

	
	/**
	 * Type constant: Double
	 */
	public static final int TYPE_DOUBLE = 3;

	
	/**
	 * Type constant: Character
	 */
	public static final int TYPE_CHARACTER = 4;

	
	/**
	 * Type constant: String
	 */
	public static final int TYPE_STRING = 5;

	
	/**
	 * Type constant: Boolean
	 */
	public static final int TYPE_BOOLEAN = 6;
	
	
	/**
	 * Type constant: Integer array
	 */
	public static final int TYPE_INTARRAY = 10;

	
	/**
	 * Type constant: Float array
	 */
	public static final int TYPE_FLOATARRAY = 11;

	
	/**
	 * Type constant: Double array
	 */
	public static final int TYPE_DOUBLEARRAY = 12;

	
	/**
	 * Type constant: Character array
	 */
	public static final int TYPE_CHARACTERARRAY = 13;

	
	/**
	 * Type constant: String array
	 */
	public static final int TYPE_STRINGARRAY = 14;

	
	/**
	 * Type constant: Boolean array
	 */
	public static final int TYPE_BOOLEANARRAY = 15;

	
	/**
	 * Type constant: Collection
	 */
	public static final int TYPE_COLLECTION = 20;

	
	/**
	 * Type constant: Map
	 */
	public static final int TYPE_MAP = 21;

	
	/**
	 * Type constant: Exception
	 */
	public static final int TYPE_EXCEPTION = 22;

	
	
	
	
	/**
	 * Store name
	 */
	String entryName = null;

	
	/**
	 * Store value
	 */
	Object entryValue = null;

	
	/**
	 * Store value as String
	 */
	String entryValueAsString = null;
	
	
	/**
	 * Store type
	 */
	int entryType = -1;
	
	
	
	
	/**
	 * Constructor
	 */
	public SQLTableRow(Object value) {
		// Store value
		entryValue  = value;
		entryName   = null;
		
		// Convert value to String
		entryValueAsString = getValueAsString(value);
		
		// Extract type
		entryType = getTypeID(value);
	}

	
	
	/**
	 * Constructor
	 */
	public SQLTableRow(String name, Object value) {
		// Store name and value
		entryName   = name;
		entryValue  = value;
		
		// Convert value to String
		entryValueAsString = getValueAsString(value);
		
		// Extract type
		entryType = getTypeID(value);
	}
	
	
	/**
	 * Constructor
	 */
	public SQLTableRow(SQLRootElement rootElement, String name, int typeId, String valueAsString) {
		// Store name, type ID, and value as String
		entryName          = name;
		entryType          = typeId;
		entryValueAsString = valueAsString;
		
		// Convert type back
		entryValue = getValueFromString(rootElement, typeId, valueAsString);
	}
	
	
	
	/**
	 * Serialize object to String
	 * 
	 * @param object Object to be serialized
	 * @return Object serialized as string
	 */
	protected static String serializeToString(Object object) {
		// Try to serialize
		try {
			// Serialize object to bytes first
			ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
			ObjectOutputStream    outputStream    = new ObjectOutputStream(byteArrayOutput);
			// - Serialize object
			outputStream.writeObject(object);
			
			// Convert object to String
			return Base64.getEncoder().encodeToString(byteArrayOutput.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	/**
	 * Serialize SQL Map to String
	 */
	protected static String serializeSQLMapToString(SQLMap value) {
		return ""+value.sqlTableID;
	}

	
	
	/**
	 * Serialize SQL Collection to String
	 */
	protected static String serializeSQLCollectionToString(SQLCollection value) {
		return ""+value.sqlTableID;
	}

	
	
	/**
	 * Deserialize object from String
	 * 
	 * @param serializedObject Serialized object
	 * @return Deserialized object
	 */
	protected static Object deserializeFromString(String serializedObject) {
		// Decode String into byte array
		byte[] objectInBytes = Base64.getDecoder().decode(serializedObject);
		
		// Deserialize object
		try {
			// Create object input stream
			ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(objectInBytes));
			
			// Deserialize object
			return inputStream.readObject();
		} catch(IOException | ClassNotFoundException e) {
			// Output exception
			e.printStackTrace();
			
			// Return null
			return null;
		}
	}

	
	
	/**
	 * Serialize SQL Map to String
	 */
	protected static SQLMap deserializeSQLMapFromString(SQLRootElement rootElement, String serializedObject) {
		return new SQLMap(rootElement, serializedObject);
	}

	
	
	/**
	 * Serialize SQL Collection to String
	 */
	protected static SQLCollection deserializeSQLCollectionFromString(SQLRootElement rootElement, String serializedObject) {
		return new SQLCollection(rootElement, serializedObject);
	}

	
	
	/**
	 * Get value as string
	 */
	public static String getValueAsString(Object value) {
		// Null values
		if (value == null) return "(null)";
		
		// Primitive types
		if (value instanceof Integer) return value.toString();
		if (value instanceof Float) return value.toString();
		if (value instanceof Double) return value.toString();
		if (value instanceof String) return value.toString();
		if (value instanceof Boolean) return value.toString();
		if (value instanceof Character) return value.toString();
		
		// Array types
		if (value instanceof int[]) return serializeToString(value);
		if (value instanceof Integer[]) return serializeToString(value);
		if (value instanceof float[]) return serializeToString(value);
		if (value instanceof Float[]) return serializeToString(value);
		if (value instanceof double[]) return serializeToString(value);
		if (value instanceof Double[]) return serializeToString(value);
		if (value instanceof char[]) return serializeToString(value);
		if (value instanceof Character[]) return serializeToString(value);
		if (value instanceof boolean[]) return serializeToString(value);
		if (value instanceof Boolean[]) return serializeToString(value);
		if (value instanceof String[]) return serializeToString(value);
		
		// Collection and Map types - first check for SQL types, then check for generic types
		if (value instanceof SQLMap) return serializeSQLMapToString((SQLMap) value);
		if (value instanceof SQLCollection) return serializeSQLCollectionToString((SQLCollection) value);

		// Unknown or unsupported type
		throw new UnknownElementTypeException("");
	}
	
	
	/**
	 * Get value from string
	 */
	public static Object getValueFromString(SQLRootElement rootElement, int typeId, String valAsString) {
		// Null values
		if (typeId == TYPE_NULL) return null;
		
		// Primitive types
		if (typeId == TYPE_INT)       return Integer.parseInt(valAsString);
		if (typeId == TYPE_FLOAT)     return Float.parseFloat(valAsString);
		if (typeId == TYPE_DOUBLE)    return Double.parseDouble(valAsString);
		if (typeId == TYPE_STRING)    return valAsString;
		if (typeId == TYPE_BOOLEAN)   return Boolean.parseBoolean(valAsString);
		if (typeId == TYPE_CHARACTER) return valAsString.charAt(0);
		
		// Array types
		if (typeId == TYPE_INTARRAY)       return deserializeFromString(valAsString);
		if (typeId == TYPE_FLOATARRAY)     return deserializeFromString(valAsString);
		if (typeId == TYPE_DOUBLEARRAY)    return deserializeFromString(valAsString);
		if (typeId == TYPE_STRINGARRAY)    return deserializeFromString(valAsString);
		if (typeId == TYPE_BOOLEANARRAY)   return deserializeFromString(valAsString);
		if (typeId == TYPE_CHARACTERARRAY) return deserializeFromString(valAsString);
		
		// Collection and Map types - first check for SQL types, then check for generic types
		if (typeId == TYPE_COLLECTION)     return deserializeSQLCollectionFromString(rootElement, valAsString);
		if (typeId == TYPE_MAP)            return deserializeSQLMapFromString(rootElement, valAsString);
		
		// Unknown or unsupported type
		return null;
	}
	
	
	/**
	 * Get numeric type ID that represents the type
	 */
	protected int getTypeID(Object value) {
		// Null pointer check
		if (value == null) return TYPE_NULL;
		
		// Check primitive types
		if (value instanceof Integer) return TYPE_INT;
		if (value instanceof Float) return TYPE_FLOAT;
		if (value instanceof Double) return TYPE_DOUBLE;
		if (value instanceof String) return TYPE_STRING;
		if (value instanceof Boolean) return TYPE_BOOLEAN;
		if (value instanceof Character) return TYPE_CHARACTER;
		
		// Check array types
		if (value instanceof int[]) return TYPE_INTARRAY;
		if (value instanceof Integer[]) return TYPE_INTARRAY;
		if (value instanceof float[]) return TYPE_FLOATARRAY;
		if (value instanceof Float[]) return TYPE_FLOATARRAY;
		if (value instanceof double[]) return TYPE_DOUBLEARRAY;
		if (value instanceof Double[]) return TYPE_DOUBLEARRAY;
		if (value instanceof char[]) return TYPE_CHARACTERARRAY;
		if (value instanceof Character[]) return TYPE_CHARACTERARRAY;
		if (value instanceof boolean[]) return TYPE_BOOLEANARRAY;
		if (value instanceof Boolean[]) return TYPE_BOOLEANARRAY;
		if (value instanceof String[]) return TYPE_STRINGARRAY;

		// Complex types
		if (value instanceof Map) return TYPE_MAP;
		if (value instanceof Collection) return TYPE_COLLECTION;
		if (value instanceof Exception) return TYPE_EXCEPTION;

		// Function is not supported at the moment
		
		// Unknown type
		return -1;
	}
	
	
	/**
	 * Get entry name
	 */
	public String getName() {
		return entryName;
	}
	
	
	/**
	 * Get entry value
	 */
	public Object getValue() {
		return entryValue;
	}

	
	/**
	 * Get entry value
	 */
	public String getValueAsString() {
		return entryValueAsString;
	}


	/**
	 * Get entry type id
	 */
	public int getTypeID() {
		return entryType;
	}
}
