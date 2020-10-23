package org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef;

import java.math.BigInteger;
import java.time.Duration;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * Provides utility functions for
 * {@link org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef
 * PropertyValueTypeDef} <br />
 * * Creating a PropertyValueTypeDef from name <br/>
 * * Creating a PropertyValueTypeDef for an object
 * 
 * @author schnicke
 *
 */
public class PropertyValueTypeDefHelper {
	private static Map<String, PropertyValueTypeDef> typeMap = new HashMap<>();

	// insert all types into a Map to allow getting a PropertyValueType based on a
	// String
	static {
		for (PropertyValueTypeDef t : PropertyValueTypeDef.values()) {
			typeMap.put(t.toString(), t);
		}
	}
	
	// Strings required for meta-model conformant valueType format
	private static final String TYPE_NAME = "name";
	private static final String TYPE_OBJECT = "dataObjectType";

	/**
	 * Map the name of a PropertyValueTypeDef to a PropertyValueTypeDef
	 * 
	 * @param name
	 * @return
	 */
	public static PropertyValueTypeDef fromName(String name) {
		if (typeMap.containsKey(name)) {
			return typeMap.get(name);
		} else {
			throw new RuntimeException("Unknown type name " + name + "; can not handle this PropertyValueType");
		}
	}

	/**
	 * Creates the appropriate type map for a given object
	 * 
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> getTypeWrapperFromObject(Object obj) {
		return getWrapper(getType(obj));
	}

	/**
	 * Creates the PropertyValueTypeDef for an arbitrary object
	 * 
	 * @param obj
	 * @return
	 */
	public static PropertyValueTypeDef getType(Object obj) {
		PropertyValueTypeDef objectType;
		
		if (obj == null) {
			objectType = PropertyValueTypeDef.None;
		} else {
			Class<?> c = obj.getClass();
			if(c == byte.class || c == Byte.class) {
				objectType = PropertyValueTypeDef.Int8;
			}else if(c == short.class || c == Short.class) {
				objectType = PropertyValueTypeDef.Int16;
			}else if (c == int.class || c == Integer.class) {
				objectType = PropertyValueTypeDef.Integer;
			} else if (c == long.class || c == Long.class) {
				objectType = PropertyValueTypeDef.Int64;
			} else if (c == BigInteger.class) {
				BigInteger tmp = (BigInteger) obj;
				if (tmp.compareTo(new BigInteger("0")) > 0) {
					objectType = PropertyValueTypeDef.PositiveInteger;
				} else if (tmp.compareTo(new BigInteger("0")) < 0) {
					objectType = PropertyValueTypeDef.NegativeInteger;
				} else {
					objectType = PropertyValueTypeDef.NonNegativeInteger;
				}

			} else if (c == void.class || c == Void.class) {
				objectType = PropertyValueTypeDef.None;
			} else if (c == boolean.class || c == Boolean.class) {
				objectType = PropertyValueTypeDef.Boolean;
			} else if (c == float.class || c == Float.class) {
				// TODO C# deprecated due to new serialization
				objectType = PropertyValueTypeDef.Float;
			} else if (c == double.class || c == Double.class) {
				objectType = PropertyValueTypeDef.Double;
			} else if (c == String.class) {
				objectType = PropertyValueTypeDef.String;
			} else if (c == Duration.class) {
				objectType = PropertyValueTypeDef.Duration;
			} else if (c == Period.class) {
				objectType = PropertyValueTypeDef.YearMonthDuration;
			} else if (c == QName.class) {
				objectType = PropertyValueTypeDef.QName;
			} else if (c == XMLGregorianCalendar.class) {
				objectType = PropertyValueTypeDef.DateTime;
			} else {
				throw new RuntimeException("Cannot map object " + obj + " to any PropertyValueTypeDef");
			}
		}
		return objectType;
	}

	/**
	 * Map the PropertyValueType to Java type
	 * 
	 */
	public static Object getJavaObject(Object value, PropertyValueTypeDef objType) {
		Object target = null;
		if(objType != null) {
			switch(objType) {
			case Int8:
				if(((String)value).isEmpty()){
					target = new Byte("NaN");
				}else {
					target = new Byte((String)value);
				}
				break;
			case Int16: case UInt8:
				if(((String)value).isEmpty()){
					target = new Short("NaN");
				}else {
					target = new Short((String)value);
				}
				break;
			case Int32: case UInt16:
				if(((String)value).isEmpty()){
					target = new Integer("NaN");
				}else {
					target = new Integer((String)value);
				}
				break;
			case Int64: case UInt32:
				if(((String)value).isEmpty()){
					target = new Long("NaN");
				}else {
					target = new Long((String)value);
				}
				break;
			case UInt64:
				if(((String)value).isEmpty()){
					target = new BigInteger("NaN");
				}else {
					target = new BigInteger((String)value);
				}
				break;
			case Double:
				if(((String)value).isEmpty()){
					target = new Double("NaN");
				}else {
					target = new Double((String)value);
				}
				break;
			case Float:
				if(((String)value).isEmpty()){
					target = new Float("NaN");
				}else {
					target =  new Float((String)value);
				}
				break;
			case Boolean:
				target =  new Boolean((String)value);
				break;
			case AnySimpleType: case String: case LangString: case AnyURI: case Base64Binary: case HexBinary: case NOTATION: case ENTITY: case ID: case IDREF:
				target = (String) value;
				break;
			case Duration:		case DayTimeDuration:
				target = Duration.parse((String)value);
				break;
			case YearMonthDuration:
				target = Period.parse((String)value);
				break;
			case DateTime: case DateTimeStamp: case GDay: case GMonth: case GMonthDay: case GYear: case GYearMonth:
				try {
					target = DatatypeFactory.newInstance().newXMLGregorianCalendar((String)value);
					break;
				} catch (DatatypeConfigurationException e) {
					e.printStackTrace();
					throw new RuntimeException("Could not create DatatypeFactory for XMLGregorianCaldner handling");
				} 
			case QName:
				target = QName.valueOf((String)value);
				break;
			default:
				target = value;
				break;
			}
			return target;
		}else {
			return null;
		}
		
		
	}

	/**
	 * Convert an object which has special types (Duration, period, Qname, Date) to
	 * String object Used by Property.set() or ConnectedProperty.set(), prepare for the serialization
	 * 
	 * @param value - the target object
	 * @return
	 */
	public static Object prepareForSerialization(Object value) {
		if(value != null) {
			Class<?> c = value.getClass();
			if (c == Duration.class || c == Period.class || c == QName.class || c == XMLGregorianCalendar.class) {
				return value.toString();
			}
		}
			return value;

	}

	/**
	 * Creates the appropriate type map for a given type
	 * 
	 * @param type
	 * @return
	 */
	public static Map<String, Object> getWrapper(PropertyValueTypeDef type) {
		HashMap<String, Object> valueTypeWrapper = new HashMap<>();
		HashMap<String, Object> dataObjectTypeWrapper = new HashMap<>();
		valueTypeWrapper.put(TYPE_OBJECT, dataObjectTypeWrapper);
		dataObjectTypeWrapper.put(TYPE_NAME, type.toString());
		return valueTypeWrapper;
	}

	@SuppressWarnings("unchecked")
	public static PropertyValueTypeDef readTypeDef(Object vTypeMap) {
		
		if (vTypeMap instanceof Map<?,?>) {

			Map<String, Object> map = (Map<String, Object>) vTypeMap;
			Map<String, Object> dot = (Map<String, Object>) map.get(TYPE_OBJECT);
			
			return fromName(dot.get(TYPE_NAME).toString());
			
		} else if (vTypeMap instanceof String) {
			// From xml/json-schema point of view, this should be only a string.
			return fromName((String)vTypeMap);
		}
		return null;
	}
}
