/*
 * JSONTools.cpp
 *
 *  Created on: Mar 17, 2018
 *      Author: cioroaic
 */

#include "JSONTools.h"

#include <ref/BRef.h>
#include <types/BType.h>
#include <types/BArray.h>

using namespace std;

#include <iostream>
#include <map>



/* ********************************************************************************************************************************************
 * Constructor and destructor
 * ********************************************************************************************************************************************/

//
/**
 * Constructor
 */
JSONTools::JSONTools() {
	// TODO Auto-generated constructor stub

}


/**
 * Destructor
 */
JSONTools::~JSONTools() {
	// TODO Auto-generated destructor stub
}




/* ********************************************************************************************************************************************
 * Utility functions
 * ********************************************************************************************************************************************/





/* ********************************************************************************************************************************************
 * Serialization functions
 * ********************************************************************************************************************************************/


/**
 * Serialize a null value
 */
bool JSONTools::serializeNull(json *target, BRef<BType> value, json *repository) {
	// Type check
	if (value->getType() != BASYS_NULL) return false;

	// Serialize null value
	(*target)["kind"] = "null";

	// Return serialized value
	return true;
}


/**
 * Serialize a primitive type
 */
bool JSONTools::serializePrimitiveType(json *target, BRef<BType> value, json *serObjRepo) {
	// Type check
	switch(value->getType()) {
		case BASYS_INT:       return serializePrimitive(target, ((BRef<BValue>) value)->getInt(),        serObjRepo, "int");
		case BASYS_BOOLEAN:   return serializePrimitive(target, ((BRef<BValue>) value)->getBoolean(),    serObjRepo, "boolean");
		case BASYS_FLOAT:     return serializePrimitive(target, ((BRef<BValue>) value)->getFloat(),      serObjRepo, "float");
		case BASYS_DOUBLE:    return serializePrimitive(target, ((BRef<BValue>) value)->getDouble(),     serObjRepo, "double");
		case BASYS_CHARACTER: return serializePrimitive(target, ((BRef<BValue>) value)->getCharacter(),  serObjRepo, "character");
		case BASYS_STRING:    return serializePrimitive(target, ((BRef<BString>) value)->getString(),    serObjRepo, "string");

		// BRef does not point to primitive type
		default:
			// Indicate failure
			return false;
	}
}



/**
 * Serialize a primitive type
 */
template<typename T> bool JSONTools::serializePrimitive(json *target, T primitiveValue, json *serObjRepo, std::string typeName) {
	// Serialize type
	(*target)["value"]  = primitiveValue;
	(*target)["typeid"] = typeName;
	(*target)["kind"]   = "value";

	// indicate success
	return true;
}



/**
 * Implement array type serialization
 */
bool JSONTools::doSerializeArray(json *target, BRef<BArray> arrayValue, std::string typeName, json *serObjRepo, std::string scope) {
	// Serialize array data
	(*target)["kind"] = "array";
	(*target)["size"] = arrayValue->getArraySize();
	(*target)["type"] = typeName;

	// Serialize array elements
	for (int i=0; i<arrayValue->getArraySize(); i++) {
		// Serialize array member
		// - Buffer for array member
		json *arraymember = new json();
		// - Array members are primitive types in C++. Therefore, we need to do some type checks here.
		switch(arrayValue->getType()) {
			// These are arrays of primitives
			case BASYS_INTARRAY:       serializePrimitive(arraymember, ((BRef<BArray>) arrayValue)->getMembersInt()[i],    serObjRepo, "int");       (*target)[std::to_string(i)] = *arraymember; break;
			case BASYS_BOOLEANARRAY:   serializePrimitive(arraymember, ((BRef<BArray>) arrayValue)->getMembersBool()[i],   serObjRepo, "boolean");   (*target)[std::to_string(i)] = *arraymember; break;
			case BASYS_FLOATARRAY:     serializePrimitive(arraymember, ((BRef<BArray>) arrayValue)->getMembersFloat()[i],  serObjRepo, "float");     (*target)[std::to_string(i)] = *arraymember; break;
			case BASYS_DOUBLEARRAY:    serializePrimitive(arraymember, ((BRef<BArray>) arrayValue)->getMembersDouble()[i], serObjRepo, "double");    (*target)[std::to_string(i)] = *arraymember; break;
			case BASYS_CHARACTERARRAY: serializePrimitive(arraymember, ((BRef<BArray>) arrayValue)->getMembersChar()[i],   serObjRepo, "character"); (*target)[std::to_string(i)] = *arraymember; break;
			case BASYS_STRINGARRAY:    serializePrimitive(arraymember, ((BRef<BArray>) arrayValue)->getMembersString()[i], serObjRepo, "string");    (*target)[std::to_string(i)] = *arraymember; break;

			// Array of objects
			case BASYS_OBJECTARRAY:    (*target)[""+i] = serialize(arrayValue->getMembersObject()[i], serObjRepo, scope); break;

			// Unknown type
			default:
				return false;
		}
	}

	// Indicate success
	return true;
}


/**
 * Serialize an array type
 */
bool JSONTools::serializeArrayType(json *target, BRef<BArray> value, json *serObjRepo, std::string scope) {
	// Serialize known array types
	switch(value->getType()) {
		// These are arrays of primitives
		case BASYS_INTARRAY:       return doSerializeArray(target, value, "int",        serObjRepo, scope);
		case BASYS_BOOLEANARRAY:   return doSerializeArray(target, value, "boolean",    serObjRepo, scope);
		case BASYS_FLOATARRAY:     return doSerializeArray(target, value, "float",      serObjRepo, scope);
		case BASYS_DOUBLEARRAY:    return doSerializeArray(target, value, "double",     serObjRepo, scope);
		case BASYS_CHARACTERARRAY: return doSerializeArray(target, value, "character",  serObjRepo, scope);
		case BASYS_STRINGARRAY:    return doSerializeArray(target, value, "string",     serObjRepo, scope);

		// Array of objects
		case BASYS_OBJECTARRAY:    return doSerializeArray(target, value, "object",     serObjRepo, scope);
	}

	// Value is not an array type
	return false;
}


/**
 * Serialize a collection
 */
bool JSONTools::serializeCollectionType(json *target, BRef<BObjectCollection> value, json *serObjRepo, std::string scope) {
	// Element counter
	int counter = 0;
	
	// Check type of BRef
	if (value->getType() != BASYS_COLLECTION) return false;
	
	// Serialize collection
	(*target)["kind"] = "collection";
	(*target)["size"] = value->size();

	// Serialize collection elements
	for (std::list<BRef<BType>>::const_iterator iterator = value->elements()->begin(), end = value->elements()->end(); iterator != end; ++iterator) {
		(*target)[""+counter++] = serialize(*iterator, serObjRepo, scope);
	}
	
	// Indicate success
	return true;
}


/**
 * Serialize a map
 */
bool JSONTools::serializeMapType(json *target, BRef<BObjectMap> value, json *serObjRepo, std::string scope) {

	// Check type of BRef
	if (value->getType() != BASYS_MAP) return false;

	// Serialize map
	(*target)["kind"] = "map";
	(*target)["size"] = value->size();

	// Serialize map elements  
    for (std::map< std::string, BRef<BType> >::iterator iterator = value->elements()->begin() ; iterator != value->elements()->end() ; ++iterator) {
		(*target)[""+iterator->first] = serialize(iterator->second, serObjRepo, scope);
    }

	// Indicate success
	return true;
}



/**
 * Try to serialize a BaSyx reference (BRef)
 */
json JSONTools::serialize(BRef<BType> value, json *serObjRepo, std::string scope) {
	// Create return value
	json *returnValue = new json();

	// Serialize type
	if (serializeNull(returnValue, value, serObjRepo))                   return *returnValue;
	if (serializePrimitiveType(returnValue, value, serObjRepo))          return *returnValue;
	if (serializeArrayType(returnValue, value, serObjRepo, scope))       return *returnValue;
	if (serializeCollectionType(returnValue, value, serObjRepo, scope))  return *returnValue;
	if (serializeMapType(returnValue, value, serObjRepo, scope))         return *returnValue;
	// FIXME: IElementRef and BObject is missing

	// Return serialized value
	return *returnValue;
}


/* ********************************************************************************************************************************************
 * Deserialization functions
 * ********************************************************************************************************************************************/


/**
 * Deserialize a null value
 */
BRef<BType> JSONTools::deserializeNull(json serializedValue, bool *success, json *repository) {
	// Type check
	if (std::string("null").compare(serializedValue["kind"]) != 0) {*success=false; return BRef<BType>();}

	// Return serialized value
	*success=true;
	return BRef<BType>();
}


/**
 * Try to deserialize a primitive type
 */
BRef<BType> JSONTools::deserializePrimitiveType(json serializedValue, bool *success, json *serObjRepo) {
	// Type check
	if (std::string("value").compare(serializedValue["kind"])!=0) {*success=false; return BRef<BType>();}

	// Deserialize primitive type
	if(std::string("int").compare(serializedValue["typeid"])==0)       {int         value; doDeserializePrimitive(serializedValue, &value, serObjRepo); *success = true; return BRef<BValue>(value);}
	if(std::string("float").compare(serializedValue["typeid"])==0)     {float       value; doDeserializePrimitive(serializedValue, &value, serObjRepo); *success = true; return BRef<BValue>(value);}
	if(std::string("double").compare(serializedValue["typeid"])==0)    {double      value; doDeserializePrimitive(serializedValue, &value, serObjRepo); *success = true; return BRef<BValue>(value);}
	if(std::string("boolean").compare(serializedValue["typeid"])==0)   {bool        value; doDeserializePrimitive(serializedValue, &value, serObjRepo); *success = true; return BRef<BValue>(value);}
	if(std::string("character").compare(serializedValue["typeid"])==0) {char        value; doDeserializePrimitiveChar(serializedValue, &value, serObjRepo); *success = true; return BRef<BValue>(value);}
	if(std::string("string").compare(serializedValue["typeid"])==0)    {std::string value; doDeserializePrimitive(serializedValue, &value, serObjRepo); *success = true; return BRef<BString>(value);}

	// Indicate error
	*success=false; return BRef<BType>();
}


/**
 * Deserialize a primitive type
 */
template<typename T> void JSONTools::doDeserializePrimitive(json serializedValue, T *target, json *serObjRepo) {
	*target = serializedValue["value"];
}


/**
 * Deserialize a primitive type
 */
void JSONTools::doDeserializePrimitiveChar(json serializedValue, char *target, json *serObjRepo) {
	std::string value = serializedValue["value"];
	*target = value[0];
}


/**
 * Deserialize an array
 */
BRef<BType> JSONTools::deserializeArrayType(json serializedValue, bool *success, json *serObjRepo, std::string scope) {
	// Type check
	if (std::string("array").compare(serializedValue["kind"])!=0) {*success=false; return BRef<BType>();}

	// Deserialize array of primitives
	if (std::string("int").compare(serializedValue["type"])==0) {
		*success=true;
		int arraySize = serializedValue["size"];
		int *arrayValue = new int[arraySize];
		BRef<BArray> result = BRef<BArray>(new BArray(arrayValue, serializedValue["size"]));

		for (int i=0; i<arraySize; i++) doDeserializePrimitive(serializedValue[std::to_string(i)], &result->getMembersInt()[i], serObjRepo);

		return result;
	}
	if (std::string("float").compare(serializedValue["type"])==0) {
		*success=true;
		int arraySize = serializedValue["size"];
		float *arrayValue = new float[arraySize];
		BRef<BArray> result = BRef<BArray>(new BArray(arrayValue, serializedValue["size"]));

		for (int i=0; i<arraySize; i++) doDeserializePrimitive(serializedValue[std::to_string(i)], &result->getMembersFloat()[i], serObjRepo);

		return result;
	}
	if (std::string("double").compare(serializedValue["type"])==0) {
		*success=true;
		int arraySize = serializedValue["size"];
		double *arrayValue = new double[arraySize];
		BRef<BArray> result = BRef<BArray>(new BArray(arrayValue, serializedValue["size"]));

		for (int i=0; i<arraySize; i++) doDeserializePrimitive(serializedValue[std::to_string(i)], &result->getMembersDouble()[i], serObjRepo);

		return result;
	}
	if (std::string("character").compare(serializedValue["type"])==0) {
		*success=true;
		int arraySize = serializedValue["size"];
		char *arrayValue = new char[arraySize];
		BRef<BArray> result = BRef<BArray>(new BArray(arrayValue, serializedValue["size"]));

		for (int i=0; i<arraySize; i++) doDeserializePrimitiveChar(serializedValue[std::to_string(i)], &result->getMembersChar()[i], serObjRepo);

		return result;
	}
	if (std::string("boolean").compare(serializedValue["type"])==0) {
		*success=true;
		int arraySize = serializedValue["size"];
		bool *arrayValue = new bool[arraySize];
		BRef<BArray> result = BRef<BArray>(new BArray(arrayValue, serializedValue["size"]));

		for (int i=0; i<arraySize; i++) doDeserializePrimitive(serializedValue[std::to_string(i)], &result->getMembersBool()[i], serObjRepo);

		return result;
	}
	if (std::string("string").compare(serializedValue["type"])==0) {
		*success=true;
		int arraySize = serializedValue["size"];
		std::string *arrayValue = new std::string[arraySize];
		BRef<BArray> result = BRef<BArray>(new BArray(arrayValue, serializedValue["size"]));

		for (int i=0; i<arraySize; i++) doDeserializePrimitive(serializedValue[std::to_string(i)], &result->getMembersString()[i], serObjRepo);

		return result;
	}

	// Deserialize array of objects
	if (std::string("object").compare(serializedValue["type"])==0) {
		*success=true;
		int arraySize = serializedValue["size"];
		BRef<BType> *arrayValue = new BRef<BType>[arraySize];
		BRef<BArray> result = BRef<BArray>(new BArray(arrayValue, serializedValue["size"]));

		for (int i=0; i<arraySize; i++) arrayValue[i] = deserialize(serializedValue[std::to_string(i)], serObjRepo, scope);

		return result;
	}

	// Indicate error
	*success=false; return BRef<BType>();

}


/**
 * Deserialize a collection
 */
BRef<BType> JSONTools::deserializeCollectionType(json serializedValue, bool *success, json *serObjRepo, std::string scope) {
	// Type check
	if (std::string("collection").compare(serializedValue["kind"])!=0) {*success=false; return BRef<BType>();}

	// Create collection return value
	BRef<BObjectCollection> result = BRef<BObjectCollection>(new BObjectCollection());
	
	// Collection elements
	int elementCount = serializedValue["size"];

	// Deserialize collection elements
	for (int i=0; i<elementCount; i++) {
		result->add(deserialize(serializedValue[""+i], serObjRepo, scope));
	}
	
	// Value has been deserialized
	*success = true;
	return result;
}


/**
 * Deserialize a map
 */
BRef<BType> JSONTools::deserializeMapType(json serializedValue, bool *success, json *serObjRepo, std::string scope) {
	// Type check
	if (std::string("map").compare(serializedValue["kind"])!=0) {*success=false; return BRef<BType>();}
	
	// Create hash map return value
	BRef<BObjectMap> result = BRef<BObjectMap>(new BObjectMap());
	
	// Deserialize map elements
	for (json::iterator it = serializedValue.begin(); it != serializedValue.end(); ++it) {
		 result->elements()->insert(std::pair<std::string, BRef<BType>>(it.key(), deserialize(it.value(), serObjRepo, scope)));
	}

	// Value has been deserialized
	*success = true;
	return result;
}





/**
 * Try to deserialize a BaSyx reference (BRef)
 */
BRef<BType> JSONTools::deserialize(json serializedValue, json *serObjRepo, std::string scope) {
	// This flag indicates whether a desrialize* operation was successful
	bool success = false;

	// Store result
	BRef<BType> result;

	// Serialize type
	result = deserializeNull(serializedValue, &success, serObjRepo);                   if (success) return result;
	result = deserializePrimitiveType(serializedValue, &success, serObjRepo);          if (success) return result;
	result = deserializeArrayType(serializedValue, &success, serObjRepo, scope);       if (success) return result;
	result = deserializeCollectionType(serializedValue, &success, serObjRepo, scope);  if (success) return result;
	result = deserializeMapType(serializedValue, &success, serObjRepo, scope);         if (success) return result;
	// FIXME: IElementRef and BObject is missing

	// Return a null value
	return BRef<BType>();
}

json JSONTools::getJSONObject(json serializedValue, std::string key)
{
	json object =
		   {
				{"value", ""},
				{"kind", ""},
				{"typeid", ""}
		   };
	try {
		json tmp_json = serializedValue[key];
		return tmp_json;
	} catch (int e) {

	}
	return object;
}





