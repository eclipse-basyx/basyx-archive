/*
 * any_serializer.h
 *
 *  Created on: 21.03.2019
 *      Author: psota
 */

#ifndef BASYX_SERIALIZATION_JSON_JSON_SERIALIZER_H
#define BASYX_SERIALIZATION_JSON_JSON_SERIALIZER_H

#include <json/json.hpp>

#include <basyx/object/object_header.h>
#include <basyx/types.h>


namespace basyx {
namespace serialization {
namespace json {
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
    template <typename T>
    //		inline void serialize_helper(json_t & json, const T & value, typename std::enable_if<std::is_fundamental<T>::value, T>::type = 0) {
    inline void serialize_helper(json_t& json, const T& value)
    {
        json = value;
    };

    // std::string serializer
    inline void serialize_helper(json_t& json, const std::string& string)
    {
        json = string;
    };

    // basyx::object serializer
	inline void serialize_helper(json_t& json, const basyx::object& object)
	{
        json = object;
    };

	template<typename T>
	inline void serialize_helper(json_t & json, const basyx::object::list_t<T> & list)
	{
		json[basyx::serialization::typeSpecifier] = "list";
		json[basyx::serialization::valueSpecifier] = list ;
	};

	template<typename T>
	inline void serialize_helper(json_t & json, const basyx::object::set_t<T> & set)
	{
		json[basyx::serialization::typeSpecifier] = "set";
		json[basyx::serialization::valueSpecifier] = set;
	};

    // basyx::object::object_map_t serializer
    inline void serialize_helper(json_t& json, const basyx::object::object_map_t & objectMap)
    {
		// Initialize as valid json object, even if map is empty
		json = json_t::object();
        json_t collectionTypes;

        for (const auto& entry : objectMap) {
            if (entry.second.InstanceOf<basyx::object::object_list_t>()) {
                collectionTypes[entry.first] = "list";
            };

            json[entry.first] = entry.second;
            if (collectionTypes.size() > 0)
                json["_basyxTypes"] = collectionTypes;
        }
    }

    // basyx::object::object_list_t serializer
    //inline void serialize_helper(json_t& json, const basyx::object::object_list_t& objectCollection)
    //{
    //    for (const auto& object : objectCollection) {
    //        json.push_back(object);
    //    }
    //}
};
};
};

#endif /* BASYX_SERIALIZATION_JSON_JSON_SERIALIZER_H */