/*
 * any_serializer.h
 *
 *  Created on: 21.03.2019
 *      Author: psota
 */

#ifndef BASYX_JSON_SERIALIZER_H
#define BASYX_JSON_SERIALIZER_H

#include <json/json.hpp>

#include <types/BaSysTypes.h>

#include <util/array.h>

namespace basyx
{
	namespace json
	{
		using json_t = nlohmann::json;
		// Unknown type serializer
		// Throw static assertion at compile time
		// signaling that no basyx serializer for this type exists
		//template<typename T>
		//void serialize_helper(json_t & json, const T &, typename std::enable_if<!std::is_fundamental<T>::value, T>::type = 0) {
		//	static_assert(false, "No basyx serialization for type exists!");
		//};

		// Fundamental type serializer
		// int, float, double, char, etc.
		template<typename T>
		inline void serialize_helper(json_t & json, const T & value, typename std::enable_if<std::is_fundamental<T>::value, T>::type = 0) {
			json = json_t{
				{ basyx::serialization::typeIdSpecifier, basyx::serialization::basysType<T>::string},
				{ basyx::serialization::typeSpecifier, "value" },
				{ basyx::serialization::valueSpecifier, value }
			};
		};

		// std::string serializer
		// isn't a fundamental type, so needs own serialization handling
		inline void serialize_helper(json_t & json, const std::string & string) {
			json = json_t{
				{ basyx::serialization::typeIdSpecifier, basyx::serialization::basysType<std::string>::string},
				{ basyx::serialization::typeSpecifier, "value" },
				{ basyx::serialization::valueSpecifier, string }
			};
		};

		// basyx::any serializer
		inline void serialize_helper(json_t & json, const basyx::any & any) {
			json = any;
		};

		// basyx::array serializer
		template<typename T>
		inline void serialize_helper(json_t & json, const basyx::array<T> & array)
		{
			// serialize header
			json = json_t{
				{ basyx::serialization::typeSpecifier, basyx::serialization::arraySpecifier },
				{ basyx::serialization::sizeSpecifier, array.size() },
				{ basyx::serialization::arrayTypeSpecifier, basyx::serialization::basysType<T>::string }
			};

			// serialize items
			for (std::size_t i = 0; i < array.size(); ++i) {
				json_t value;
				serialize_helper(value, array.get(i));
				json[std::to_string(i)] = value;
			}
		}

		// basyx::objectMap_t serializer
		inline void serialize_helper(json_t & json, const basyx::objectMap_t & objectMap)
		{
			json = json_t{
				{ basyx::serialization::typeSpecifier, "map"},
				{ basyx::serialization::sizeSpecifier, objectMap.size() }
			};

			for (const auto & entry : objectMap) {
				json[entry.first] = entry.second;
			}
		}

		//// basyx::objectSet_t serializer
		//inline void serialize_helper(json_t & json, const basyx::objectSet_t & objectSet)
		//{
		//	json = json_t{
		//		{ basyx::serialization::typeSpecifier, "set"},
		//		{ basyx::serialization::sizeSpecifier, objectSet.size() }
		//	};

		//	std::size_t index = 0;
		//	for (const auto & item : objectSet) {
		//		json[std::to_string(index++)] = item;
		//	};
		//}

		// basyx::objectCollection_t serializer
		inline void serialize_helper(json_t & json, const basyx::objectCollection_t & objectCollection)
		{
			json = json_t{
				{ basyx::serialization::typeSpecifier, "collection"},
				{ basyx::serialization::sizeSpecifier, objectCollection.size() }
			};

			for (std::size_t i = 0; i < objectCollection.size(); ++i) {
				json[std::to_string(i)] = objectCollection.at(i);
			};
		}

	};
};

#endif