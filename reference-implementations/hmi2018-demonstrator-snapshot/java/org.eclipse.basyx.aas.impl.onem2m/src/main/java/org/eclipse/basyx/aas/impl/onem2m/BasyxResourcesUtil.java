package org.eclipse.basyx.aas.impl.onem2m;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.lang3.StringEscapeUtils;
import org.eclipse.basyx.aas.api.resources.basic.AssetKind;
import org.eclipse.basyx.aas.api.resources.basic.DataType;

import com.google.common.net.UrlEscapers;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class BasyxResourcesUtil {

	public static DataType convertStrToDataType(String str) {
		switch (str) {
		case "ref":
			return DataType.REFERENCE;
		case "bool":
			return DataType.BOOLEAN;
		case "int":
			return DataType.INTEGER;
		case "float":
			return DataType.FLOAT;
		case "double":
			return DataType.DOUBLE;
		case "string":
			return DataType.STRING;
		case "void":
			return DataType.VOID;
		}
		return null;
	}

	public static String convertDataTypeToStr(DataType dt) {
		switch (dt) {
		case REFERENCE:
			return "ref";
		case BOOLEAN:
			return "bool";
		case INTEGER:
			return "int";
		case FLOAT:
			return "float";
		case DOUBLE:
			return "double";
		case STRING:
			return "string";
		case VOID:
			return "void";
		}
		return null;
	}

	public static AssetKind convertStrToAssetKind(String str) {
		if (str == null) {
			return null;
		}
		switch (str) {
		case "inst":
			return AssetKind.INSTANCE;
		case "ty":
			return AssetKind.TYPE;
		}
		return null;
	}

	public static String convertAssetKindToStr(AssetKind ak) {
		switch (ak) {
		case INSTANCE:
			return "inst";
		case TYPE:
			return "ty";
		}
		return null;
	}

	protected static Class<?> convert(DataType dt) {
		switch (dt) {
		case BOOLEAN:
			return Boolean.class;
		case FLOAT:
			return Float.class;
		case DOUBLE:
			return Double.class;
		case INTEGER:
			return Integer.class;
		case REFERENCE:
		case STRING:
			return String.class;
		case VOID:
			throw new IllegalArgumentException("void cannot be converted to Java class");
		default:
			throw new IllegalArgumentException("Unknown Datatype");
		}
	}

	public static String toM2MValue(Object value, DataType dataType) {

		if (value == null) {
			return "";
		}

		String val = null;

		if (dataType == null) {
			val = value.toString();
		} else {
			switch (dataType) {
			case BOOLEAN:
			case FLOAT:
			case DOUBLE:
			case INTEGER:
				val = value.toString();
				break;
			case REFERENCE:
			case STRING:
				val = '"' + StringEscapeUtils.escapeEcmaScript(value.toString()) + '"';
				break;
			case VOID:
				throw new IllegalArgumentException("Void is not allowed");
			default:
				throw new IllegalArgumentException("Unknown Datatype");
			}
		}

		String encoded = UrlEscapers.urlFragmentEscaper().escape(val).replaceAll("\\+", "%2B");
		return encoded;

	}

	public static Object fromM2MValue(String value, DataType dataType) {
		try {
			String decoded = URLDecoder.decode(value, "UTF-8");
			String decodedStripped = decoded.replaceAll("\\\\+", "\\\\");
			switch (dataType) {
			case BOOLEAN:
				return new Boolean(Boolean.parseBoolean(decodedStripped.toString()));
			case FLOAT:
				return new Float(Float.parseFloat(decodedStripped.toString()));
			case DOUBLE:
				return new Double(Double.parseDouble(decodedStripped.toString()));
			case INTEGER:
				// return new Integer(Integer.parseInt(decodedStripped.toString()));
				return (new Double(Double.parseDouble(value.toString()))).intValue();
			case REFERENCE:
			case STRING:
				return new JsonParser().parse(decodedStripped.toString()).getAsString();
			case VOID:
				return null;
			default:
				throw new IllegalArgumentException("Unknown Datatype");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Object fromM2MArrayElementValue(String value, DataType dataType) {
		switch (dataType) {
		case BOOLEAN:
			return new Boolean(Boolean.parseBoolean(value.toString()));
		case FLOAT:
			return new Float(Float.parseFloat(value.toString()));
		case DOUBLE:
			return new Double(Double.parseDouble(value.toString()));
		case INTEGER:
			// return new Integer(Integer.parseInt(value.toString()));
			return (new Double(Double.parseDouble(value.toString()))).intValue();
		case REFERENCE:
		case STRING:
			return new JsonParser().parse(value.toString()).getAsString();
		case VOID:
			return null;
		default:
			throw new IllegalArgumentException("Unknown Datatype");
		}
	}

	public static JsonArray fromM2MArrayValue(String arrayValue) {
		try {
			String decoded = URLDecoder.decode(arrayValue, "UTF-8");
			return new JsonParser().parse(decoded).getAsJsonArray();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isValidBasyxIdentifier(String identifier) {
		boolean isValid = true;

		if (identifier == null || identifier.length() == 0) {
			return false;
		}
		isValid &= Character.isJavaIdentifierStart(identifier.charAt(0));
		for (Character c : identifier.toCharArray()) {
			isValid &= Character.isJavaIdentifierPart(c);
		}
		return isValid;
	}

}
