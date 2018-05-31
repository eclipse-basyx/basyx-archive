/*
 * JSONTools.cpp
 *
 *  Created on: Mar 17, 2018
 *      Author: cioroaic
 */

#include "JSONTools.h"
using namespace std;


JSONTools::JSONTools() {
	// TODO Auto-generated constructor stub

}

JSONTools::~JSONTools() {
	// TODO Auto-generated destructor stub
}



json JSONTools::serializePrimitive(json target, int serializedObject, std::string type )
{

   	target.at("value") = serializedObject;
	target.at("kind") = "value";
	target.at("typeid") = type;

	return target;
}

json JSONTools::serializePrimitive(json target, float serializedObject, std::string type )
{

   	target.at("value") = serializedObject;
	target.at("kind") = "value";
	target.at("typeid") = type;

	return target;
}

json JSONTools::serializePrimitive(json target, double serializedObject, std::string type )
{

   	target.at("value") = serializedObject;
	target.at("kind") = "value";
	target.at("typeid") = type;

	return target;
}

json JSONTools::serializePrimitive(json target, std::string serializedObject, std::string type )
{

   	target.at("value") = serializedObject;
	target.at("kind") = "value";
	target.at("typeid") = type;

	return target;
}

json JSONTools::serializePrimitive(json target, bool serializedObject, std::string type )
{

   if (sizeof(serializedObject) == sizeof(bool))
   	{
	   target.at("value") = serializedObject;
	   target.at("kind") = "value";
	   target.at("typeid") = type;
	}

	return target;
}

json JSONTools::serializePrimitive(json target, char serializedObject, std::string type )
{

   	target.at("value") = serializedObject;
	target.at("kind") = "value";
	target.at("typeid") = type;

	return target;
}

std::string JSONTools::deserializePrimitive(json serializedValue)
{
	std::string kind = serializedValue.at("kind");
	if(kind.compare("value") == 0) return 0;

	return serializedValue.at("value");
}

bool JSONTools::serializePrimitiveType(json target, int value, json serObjRepo)
{
	serializePrimitive(target, value, "int");
	return true;
}

bool JSONTools::serializePrimitiveType(json target, float value, json serObjRepo)
{
	serializePrimitive(target, value, "float");
	return true;
}

bool JSONTools::serializePrimitiveType(json target, double value, json serObjRepo)
{
	serializePrimitive(target, value, "double");
	return true;
}

bool JSONTools::serializePrimitiveType(json target, std::string value, json serObjRepo)
{
	serializePrimitive(target, value, "string");
	return true;
}

bool JSONTools::serializePrimitiveType(json target, bool value, json serObjRepo)
{
	serializePrimitive(target, value, "boolean");
	return true;
}

bool JSONTools::serializePrimitiveType(json target, char value, json serObjRepo)
{
	serializePrimitive(target, value, "character");
	return true;
}

bool JSONTools::serializeNull(json target, int value, json serObjRepo)
{
	if(value != (intptr_t)NULL) return false;
	target.at("kind") = "null";
	return true;
}


bool JSONTools::serializeNull(json target, float value, json serObjRepo)
{
	if(value != std::numeric_limits<float>::quiet_NaN()) return false;
	target.at("kind") = "null";
	return true;
}


bool JSONTools::serializeNull(json target, double value, json serObjRepo)
{
	if(value != std::numeric_limits<double>::quiet_NaN()) return false;
	target.at("kind") = "null";
	return true;
}

bool JSONTools::serializeNull(json target, std::string value, json serObjRepo)
{
	if(value != "") return false;
	target.at("kind") = "null";
	return true;
}

bool JSONTools::serializeNull(json target, bool value, json serObjRepo)
{
	if(value != (bool)NULL) return false;
		target.at("kind") = "null";
		return true;
}

bool JSONTools::serializeNull(json target, char value, json serObjRepo)
{
	if(value != '\0') return false;
		target.at("kind") = "null";
		return true;
}

bool JSONTools::deserializeNull(json serializedValue, std::map<int, int> serPbjRepo, json repository)
{
	if(serializedValue.at("kind") == "null") return false;
	return true;
}

bool JSONTools::deserializeNull(json serializedValue, std::map<int, float> serPbjRepo, json repository)
{
	if(serializedValue.at("kind") == "null") return false;
	return true;
}

bool JSONTools::deserializeNull(json serializedValue, std::map<int, double> serPbjRepo, json repository)
{
	if(serializedValue.at("kind") == "null") return false;
	return true;
}

bool JSONTools::deserializeNull(json serializedValue, std::map<int, bool> serPbjRepo, json repository)
{
	if(serializedValue.at("kind") == "null") return false;
	return true;
}

bool JSONTools::deserializeNull(json serializedValue, std::map<int, char> serPbjRepo, json repository)
{
	if(serializedValue.at("kind") == "null") return false;
	return true;
}

bool JSONTools::deserializeNull(json serializedValue, std::map<int, std::string> serPbjRepo, json repository)
{
	if(serializedValue.at("kind") == "null") return false;
	return true;
}

bool JSONTools::doSerializeArray(json target, int* arrayValue, std::string typeName, json serObjRepo, std::string scope)
{
	//serialize array data
	target.at("kind") = "array";
	target.at("size") = sizeof(arrayValue)/sizeof(*arrayValue);// sizeof(arrayValue);
	target.at("type") = typeName;

	//serialize array elements
	for(unsigned int i = 0; i<sizeof(arrayValue)/sizeof(*arrayValue); i++)
		target.at(""+i) = serialize(arrayValue[i], serObjRepo, scope);

	return true;
}


bool JSONTools::doSerializeArray(json target, float* arrayValue, std::string typeName, json serObjRepo, std::string scope)
{
	//serialize array data
	target.at("kind") = "array";
	target.at("size") = sizeof(arrayValue)/sizeof(*arrayValue);
	target.at("type") = typeName;

	//serialize array elements
	for(unsigned int i = 0; i<sizeof(arrayValue)/sizeof(*arrayValue); i++)
		target.at(""+i) = serialize(arrayValue[i], serObjRepo, scope);

	return true;
}

bool JSONTools::doSerializeArray(json target, char* arrayValue, std::string typeName, json serObjRepo, std::string scope)
{
	//serialize array data
	target.at("kind") = "array";
	target.at("size") = sizeof(arrayValue)/sizeof(*arrayValue);
	target.at("type") = typeName;

	//serialize array elements
	for(unsigned int i = 0; i<sizeof(arrayValue)/sizeof(*arrayValue); i++)
		target.at(""+i) = serialize(arrayValue[i], serObjRepo, scope);

	return true;
}

bool JSONTools::doSerializeArray(json target, bool* arrayValue, std::string typeName, json serObjRepo, std::string scope)
{
	//serialize array data
	target.at("kind") = "array";
	target.at("size") = sizeof(arrayValue)/sizeof(*arrayValue);
	target.at("type") = typeName;

	//serialize array elements
	for(unsigned int i = 0; i<sizeof(arrayValue)/sizeof(*arrayValue); i++)
		target.at(""+i) = serialize(arrayValue[i], serObjRepo, scope);

	return true;
}

bool JSONTools::doSerializeArray(json target, std::string* arrayValue, std::string typeName, json serObjRepo, std::string scope)
{
	//serialize array data
	target.at("kind") = "array";
	target.at("size") = sizeof(arrayValue)/sizeof(*arrayValue);
	target.at("type") = typeName;

	//serialize array elements
	for(unsigned int i = 0; i<sizeof(arrayValue)/sizeof(*arrayValue); i++)
		target.at(""+i) = serialize(arrayValue[i], serObjRepo, scope);

	return true;
}

bool JSONTools::doSerializeArray(json target, double* arrayValue, std::string typeName, json serObjRepo, std::string scope)
{
	//serialize array data
	target.at("kind") = "array";
	target.at("size") = sizeof(arrayValue)/sizeof(*arrayValue);
	target.at("type") = typeName;

	//serialize array elements
	for(unsigned int i = 0; i<sizeof(arrayValue)/sizeof(*arrayValue); i++)
		target.at(""+i) = serialize(arrayValue[i], serObjRepo, scope);

	return true;
}

bool JSONTools::serializeArrayType(json target, int value[], json serObjRepo, std::string scope)
{
	return doSerializeArray(target,value,"int",serObjRepo,scope);
}

bool JSONTools::serializeArrayType(json target, float value[], json serObjRepo, std::string scope)
{
	return doSerializeArray(target,value,"float",serObjRepo,scope);
}
bool JSONTools::serializeArrayType(json target, double value[], json serObjRepo, std::string scope)
{
	return doSerializeArray(target,value,"double",serObjRepo,scope);
}
bool JSONTools::serializeArrayType(json target, char value[], json serObjRepo, std::string scope)
{
	return doSerializeArray(target,value,"character",serObjRepo,scope);
}
bool JSONTools::serializeArrayType(json target, bool value[], json serObjRepo, std::string scope)
{
	return doSerializeArray(target,value,"boolean",serObjRepo,scope);
}
bool JSONTools::serializeArrayType(json target, std::string value[], json serObjRepo, std::string scope)
{
	return doSerializeArray(target,value,"string",serObjRepo,scope);
}

bool JSONTools::serialize(int value, json serObjRepo, std::string scope)
{
	   json returnValue =
	   {
			{"value", ""},
			{"kind", ""},
			{"typeid", ""}
	   };

	   //serialize type
	   if(serializeNull(returnValue, value, serObjRepo)) return returnValue;
	   if(serializePrimitiveType(returnValue, value, serObjRepo)) return returnValue;
	   if(serializeArrayType(returnValue, & value, serObjRepo, scope)) return returnValue;

	   return returnValue;
}

bool JSONTools::serialize(float value, json serObjRepo, std::string scope)
{
	   json returnValue =
	   {
			{"value", ""},
			{"kind", ""},
			{"typeid", ""}
	   };

	   //serialize type
	   if(serializeNull(returnValue, value, serObjRepo)) return returnValue;
	   if(serializePrimitiveType(returnValue, value, serObjRepo)) return returnValue;
	   if(serializeArrayType(returnValue, & value, serObjRepo,scope)) return returnValue;


	   return returnValue;
}

bool JSONTools::serialize(char value, json serObjRepo, std::string scope)
{
	   json returnValue =
	   {
			{"value", ""},
			{"kind", ""},
			{"typeid", ""}
	   };

	   //serialize type
	   if(serializeNull(returnValue, value, serObjRepo)) return returnValue;
	   if(serializePrimitiveType(returnValue, value, serObjRepo)) return returnValue;
	   if(serializeArrayType(returnValue, & value, serObjRepo,scope)) return returnValue;


	   return returnValue;
}

bool JSONTools::serialize(bool value, json serObjRepo, std::string scope)
{
	   json returnValue =
	   {
			{"value", ""},
			{"kind", ""},
			{"typeid", ""}
	   };

	   //serialize type
	   if(serializeNull(returnValue, value, serObjRepo)) return returnValue;
	   if(serializePrimitiveType(returnValue, value, serObjRepo)) return returnValue;
	   if(serializeArrayType(returnValue, & value, serObjRepo,scope)) return returnValue;


	   return returnValue;
}

bool JSONTools::serialize(std::string value, json serObjRepo, std::string scope)
{
	   json returnValue =
	   {
			{"value", ""},
			{"kind", ""},
			{"typeid", ""}
	   };

	   //serialize type
	   if(serializeNull(returnValue, value, serObjRepo)) return returnValue;
	   if(serializePrimitiveType(returnValue, value, serObjRepo)) return returnValue;
	   if(serializeArrayType(returnValue, & value, serObjRepo,scope)) return returnValue;


	   return returnValue;
}

bool JSONTools::serialize(double value, json serObjRepo, std::string scope)
{
	   json returnValue =
	   {
			{"value", ""},
			{"kind", ""},
			{"typeid", ""}
	   };

	   //serialize type
	   if(serializeNull(returnValue, value, serObjRepo)) return returnValue;
	   if(serializePrimitiveType(returnValue, value, serObjRepo)) return returnValue;
	   if(serializeArrayType(returnValue, & value, serObjRepo,scope)) return returnValue;


	   return returnValue;
}
/*bool JSONTools::serialize(int value){
		return serialize(value,  std::numeric_limits<int>::quiet_NaN());
}
*/


///This needs to be checked///!!!!!*************!!!!!!!!!!!!!!!!!!!!***********!!!!
///This needs to be checked///!!!!!*************!!!!!!!!!!!!!!!!!!!!***********!!!!
///This needs to be checked///!!!!!*************!!!!!!!!!!!!!!!!!!!!***********!!!!

json getJSONObject(json serializedValue, std::string key)
{
	json object =
		   {
				{"value", ""},
				{"kind", ""},
				{"typeid", ""}
		   };
	try {
		object = serializedValue.at("value");
	} catch (int e) {

	}
	return NULL;
}
/*
int* JSONTools::doDeserializeArray(json serializedValue, int* arrayValue, std::map<int, int>  serObjRepo, json repository)
{
	// Deserialize array data
			for (int i=0; i<(sizeof(arrayValue)/sizeof(*arrayValue)); i++) {
				// Get JSON Object with value
				json value = getJSONObject(serializedValue, ""+i);
				arrayValue[i] = (int) deserialize(value, serObjRepo, repository);
			}

			// Return value
			return arrayValue;
}
*/
//int doDeserializeArray(json serializedValue, float* arrayValue, std::map<int, float>  serObjRepo, json repository);
//int doDeserializeArray(json serializedValue, double* arrayValue, std::map<int, double>  serObjRepo, json repository);
//int doDeserializeArray(json serializedValue, char* arrayValue, std::map<int, char>  serObjRepo, json repository);
//int doDeserializeArray(json serializedValue, bool* arrayValue, std::map<int, bool>  serObjRepo, json repository);
//int doDeserializeArray(json serializedValue, std::string* arrayValue, std::map<int, std::string>  serObjRepo, json repository);

//Deserialize an array
//int deserializeArrayType(json serializedValue, std::map<int, int> serObjRepo, json repository);
//float deserializeArrayType(json serializedValue, std::map<int, float> serObjRepo, json repository);
//double deserializeArrayType(json serializedValue, std::map<int, double> serObjRepo, json repository);
//char deserializeArrayType(json serializedValue, std::map<int, char> serObjRepo, json repository);
//bool deserializeArrayType(json serializedValue, std::map<int, bool> serObjRepo, json repository);
//std::string deserializeArrayType(json serializedValue, std::map<int, std::string> serObjRepo, json repository);

//Deserialize
/*int JSONTools::deserialize(json serializedValue, std::map<int, int> serObjRepo, json repository)
{
	//Create return value
	int returnValue = 0;

	if (deserializeNull(serializedValue, serObjRepo, repository)) return NULL; // std::string::npos
	if ((returnValue = deserializePrimitiveType(serializedValue, serObjRepo, repository)) != null) return returnValue;
	if ((returnValue = deserializeArrayType(serializedValue, serObjRepo, repository)) != null) return returnValue;
	if ((returnValue = deserializeCollectionType(serializedValue, serObjRepo, repository)) != null) return returnValue;
	if ((returnValue = deserializeMapType(serializedValue, serObjRepo, repository)) != null) return returnValue;
	if ((returnValue = deserializeElementReference(serializedValue, serObjRepo, repository)) != null) return returnValue;
	if ((returnValue = deserializeSerializableObject(serializedValue, serObjRepo, repository)) != null) return returnValue;

	//If object type is primitive or string, serialize right away
	return returnValue;

}
*/
//float deserialize(json serializedValue, std::map<int, float> serObjRepo, json repository);
//double deserialize(json serializedValue, std::map<int, double> serObjRepo, json repository);
//char deserialize(json serializedValue, std::map<int, char> serObjRepo, json repository);
//bool deserialize(json serializedValue, std::map<int, bool> serObjRepo, json repository);
//std::string deserialize(json serializedValue, std::map<int, std::string> serObjRepo, json repository);

/*
int JSONTools::deserializePrimitiveType(json serializedValue, std::map<int, int> serObjRepo, json repository) {
		// Deserialize known primitive types
		if (isPrimitive(serializedValue)) return deserializePrimitive(serializedValue);

		// Value is not a primitive type
		return NULL;
	}
*/
