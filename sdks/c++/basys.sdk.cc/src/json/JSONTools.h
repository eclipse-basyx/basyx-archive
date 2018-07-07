/*
 * JSONTools.h
 *
 *  Created on: Mar 17, 2018
 *      Author: cioroaic
 */


#ifndef JSONTOOLS_H_
#define JSONTOOLS_H_


/* ********************************************************************************************************************************************
 * Includes
 * ********************************************************************************************************************************************/

#include "json.hpp"
using json = nlohmann::json;


#include <ref/BRef.h>
#include <types/BType.h>
#include <types/BArray.h>
#include <types/BObjectCollection.h>
#include <types/BObjectMap.h>

#include <map>



/* ********************************************************************************************************************************************
 * Class definition
 * ********************************************************************************************************************************************/


/**
 * JSONTools class definition
 */
class JSONTools {

	// Constructor / destructor
	public:

		/**
		 * Constructor
		 */
		JSONTools();

		/**
		 * Destructor
		 */
		virtual ~JSONTools();


	// Helper functions
	protected:

		/**
		 * Try to serialize a BRef as null value
		 */
		bool                      serializeNull(json *target, BRef<BType> serializedObject, json *serObjRepo);

		/**
		 * Try to serialize a BRef as primitive value
		 */
		bool                      serializePrimitiveType(json *target, BRef<BType> value, json *serObjRepo );

		/**
		 * Serialize a BRef as primitive value
		 */
		template<typename T> bool serializePrimitive(json *target, T primitiveValue, json *serObjRepo, std::string typeName);

		/**
		 * Try to serialize a BRef as array value
		 */
		bool                      doSerializeArray(json *target, BRef<BArray> arrayValue, std::string typeName, json *serObjRepo, std::string scope);

		/**
		 * Serialize a BRef as array value
		 */
		bool                      serializeArrayType(json *target, BRef<BArray> value, json *serObjRepo, std::string scope);

		/**
		 * Try to serialize a BRef as collection value
		 */
		bool                      serializeCollectionType(json *target, BRef<BObjectCollection> value, json *serObjRepo, std::string scope);

		/**
		 * Try to serialize a BRef as map value
		 */
		bool                      serializeMapType(json *target, BRef<BObjectMap> value, json *serObjRepo, std::string scope);


		/**
		 * Try to deserialize a json serialized value as null value
		 */
		BRef<BType>               deserializeNull(json serializedValue, bool *success, json *repository);

		/**
		 * Try to deserialize a json serialized value as primitive value
		 */
		BRef<BType>               deserializePrimitiveType(json serializedValue, bool *success, json *serObjRepo);

		/**
		 * Deserialize a json serialized value as primitive value
		 */
		template<typename T> void doDeserializePrimitive(json serializedValue, T *target, json *serObjRepo);

		/**
		 * Deserialize a json serialized value as character value
		 */
		void                      doDeserializePrimitiveChar(json serializedValue, char *target, json *serObjRepo);

		/**
		 * Try to deserialize a json serialized value as array value
		 */
		BRef<BType>               deserializeArrayType(json serializedValue, bool *success, json *serObjRepo, std::string scope);

		/**
		 * Deserialize a json serialized value as array value
		 */
		BRef<BType>               deserializeCollectionType(json serializedValue, bool *success, json *serObjRepo, std::string scope);

		/**
		 * Deserialize a json serialized value as map value
		 */
		BRef<BType>               deserializeMapType(json serializedValue, bool *success, json *serObjRepo, std::string scope);


	// Public interface
	public:

		/**
		 * Serialize a BRef into a json serialized value
		 */
		json                  serialize(BRef<BType> value, json *serObjRepo, std::string scope);

		/**
		 * Deserialize a json serialized value into a BRef
		 */
		BRef<BType>           deserialize(json serializedValue, json *serObjRepo, std::string scope);
};


#endif /* JSONTOOLS_H_ */
