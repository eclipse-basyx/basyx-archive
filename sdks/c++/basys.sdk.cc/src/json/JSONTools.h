/*
 * JSONTools.h
 *
 *  Created on: Mar 17, 2018
 *      Author: cioroaic
 */


#ifndef JSONTOOLS_H_
#define JSONTOOLS_H_

#include "json.hpp"
using json = nlohmann::json;


#include "ref/BRef.h"


class JSONTools {
public:
	JSONTools();
	virtual ~JSONTools();
	json serializePrimitive(json target, int serializedObject, std::string type );
	json serializePrimitive(json target, float serializedObject, std::string type );
	json serializePrimitive(json target, double serializedObject, std::string type );
	json serializePrimitive(json target, std::string serializedObject, std::string type );
	json serializePrimitive(json target, bool serializedObject, std::string type );
	json serializePrimitive(json target, char serializedObject, std::string type );
	std::string deserializePrimitive(json serializedValue);
	bool serializePrimitiveType(json target, int value, json serObjRepo);
	bool serializePrimitiveType(json target, float value, json serObjRepo);
	bool serializePrimitiveType(json target, double value, json serObjRepo);
	bool serializePrimitiveType(json target, std::string value, json serObjRepo);
	bool serializePrimitiveType(json target, bool value, json serObjRepo);
	bool serializePrimitiveType(json target, char value, json serObjRepo);

	//serialize null values
	bool serializeNull(json target, int value, json serObjRepo);
	bool serializeNull(json target, float value, json serObjRepo);
	bool serializeNull(json target, double value, json serObjRepo);
	bool serializeNull(json target, std::string value, json serObjRepo);
	bool serializeNull(json target, bool value, json serObjRepo);
	bool serializeNull(json target, char value, json serObjRepo);

	//deserialize null value
	bool deserializeNull(json serializedValue, std::map<int, int> serPbjRepo, json repository);
	bool deserializeNull(json serializedValue, std::map<int, float> serPbjRepo, json repository);
	bool deserializeNull(json serializedValue, std::map<int, double> serPbjRepo, json repository);
	bool deserializeNull(json serializedValue, std::map<int, char> serPbjRepo, json repository);
	bool deserializeNull(json serializedValue, std::map<int, std::string> serPbjRepo, json repository);
	bool deserializeNull(json serializedValue, std::map<int, bool> serPbjRepo, json repository);

	//serialize array
	bool doSerializeArray(json target, int* arrayValue, std::string typeName, json serObjRepo, std::string scope);
	bool doSerializeArray(json target, float* arrayValue, std::string typeName, json serObjRepo, std::string scope);
	bool doSerializeArray(json target, double* arrayValue, std::string typeName, json serObjRepo, std::string scope);
	bool doSerializeArray(json target, char* arrayValue, std::string typeName, json serObjRepo, std::string scope);
	bool doSerializeArray(json target, bool* arrayValue, std::string typeName, json serObjRepo, std::string scope);
	bool doSerializeArray(json target, std::string* arrayValue, std::string typeName, json serObjRepo, std::string scope);

	//serialize a primitive or complex value into JSON object
	bool serialize(int value, json serObjRepo, std::string scope);
	bool serialize(float value, json serObjRepo, std::string scope);
	bool serialize(double value, json serObjRepo, std::string scope);
	bool serialize(std::string value, json serObjRepo, std::string scope);
	bool serialize(bool value, json serObjRepo, std::string scope);
	bool serialize(char value, json serObjRepo, std::string scope);

	bool serialize(int value, std::string Scope);

	//serialize an array type
	bool serializeArrayType(json target, int value[], json serObjRepo, std::string scope);
	bool serializeArrayType(json target, float value[], json serObjRepo, std::string scope);
	bool serializeArrayType(json target, double value[], json serObjRepo, std::string scope);
	bool serializeArrayType(json target, char value[], json serObjRepo, std::string scope);
	bool serializeArrayType(json target, bool value[], json serObjRepo, std::string scope);
	bool serializeArrayType(json target, std::string value[], json serObjRepo, std::string scope);

	//Implement array type deserialization
	int* doDeserializeArray(json serializedValue, int* arrayValue, std::map<int, int>  serObjRepo, json repository);
	float*  doDeserializeArray(json serializedValue, float* arrayValue, std::map<int, float>  serObjRepo, json repository);
	double* doDeserializeArray(json serializedValue, double* arrayValue, std::map<int, double>  serObjRepo, json repository);
	char* doDeserializeArray(json serializedValue, char* arrayValue, std::map<int, char>  serObjRepo, json repository);
	bool* doDeserializeArray(json serializedValue, bool* arrayValue, std::map<int, bool>  serObjRepo, json repository);
	std::string* doDeserializeArray(json serializedValue, std::string* arrayValue, std::map<int, std::string>  serObjRepo, json repository);

	//Deserialize an array
	int deserializeArrayType(json serializedValue, std::map<int, int> serObjRepo, json repository);
	float deserializeArrayType(json serializedValue, std::map<int, float> serObjRepo, json repository);
	double deserializeArrayType(json serializedValue, std::map<int, double> serObjRepo, json repository);
	char deserializeArrayType(json serializedValue, std::map<int, char> serObjRepo, json repository);
	bool deserializeArrayType(json serializedValue, std::map<int, bool> serObjRepo, json repository);
	std::string deserializeArrayType(json serializedValue, std::map<int, std::string> serObjRepo, json repository);

	//Deserialize a primitive or complex value from JSON Object
	int deserialize(json serializedValue, std::map<int, int> serObjRepo, json repository);
	float deserialize(json serializedValue, std::map<int, float> serObjRepo, json repository);
	double deserialize(json serializedValue, std::map<int, double> serObjRepo, json repository);
	char deserialize(json serializedValue, std::map<int, char> serObjRepo, json repository);
	bool deserialize(json serializedValue, std::map<int, bool> serObjRepo, json repository);
	std::string deserialize(json serializedValue, std::map<int, std::string> serObjRepo, json repository);

	int deserializePrimitiveType(json serializedValue, std::map<int, int> serObjRepo, json repository);
	float deserializePrimitiveType(json serializedValue, std::map<int, float> serObjRepo, json repository);
	double deserializePrimitiveType(json serializedValue, std::map<int, double> serObjRepo, json repository);
	char deserializePrimitiveType(json serializedValue, std::map<int, char> serObjRepo, json repository);
	bool deserializePrimitiveType(json serializedValue, std::map<int, bool> serObjRepo, json repository);
	std::string deserializePrimitiveType(json serializedValue, std::map<int, std::string> serObjRepo, json repository);




	/**
	 * Serialize integer primitive
	 */
	bool serializePrimitive(json *target, int value, std::string type);

	/**
	 * Serialize float primitive
	 */
	bool serializePrimitive(json *target, float value, std::string type);




	/**
	 * Serialize null object
	 */
	bool serializeNull(json *target, BRef<BType> value, json *serObjRepo);


	/**
	 * Serialize primitive value
	 */
	bool serializePrimitiveType(json *target, BRef<BType> value, json *serObjRepo);

	/**
	 * Serialize object
	 */
	json serialize(BRef<BType> value, json *serObjRepo, char *scope);

};

#endif /* JSONTOOLS_H_ */
