/*
 * any_serializer.h
 *
 *  Created on: 21.03.2019
 *      Author: psota
 */

#ifndef BASYX_JSON_DESERIALIZER_H
#define BASYX_JSON_DESERIALIZER_H

#include <json/json.hpp>

#include <basyx/object/object_header.h>
#include <basyx/types.h>

#include "typeid.h"

namespace basyx {
namespace serialization {
namespace json {
    using json_t = nlohmann::json;

    // This class handles the actual deserialization of the BaSyx JSON format
    // Assumes that every JSON is well formed and adheres to the VAB serialization standard
    class deserialize_helper {
    public:
        static basyx::object deserialize(const json_t& json)
        {
            // Get the basystype
            // auto basysType = json[basyx::serialization::typeSpecifier].get<std::string>();

            // deserialize fundamental types (and std::string)
            //				if (basysType == basyx::serialization::valueSpecifier)
            if (json.is_primitive()) {
                return deserialize_helper::fundamental(json);
            } else // deserialize objectMap
                //				if (basysType == basyx::serialization::mapSpecifier)
                if (json.is_object()) {
                return basyx::object { std::forward<basyx::object::object_map_t>(deserialize_helper::objectMap(json)) };
            } else // deserialize typed basyx::array
                //				if (basysType == basyx::serialization::arraySpecifier)
                if (json.is_array()) {
                return deserialize_helper::object_list(json);
            }

			return basyx::object::make_null();
        }

    private:
        // Deserialize a fundamental type (and std::string) from JSON and return a basyx::object object holding the deserialized value
        static basyx::object fundamental(const json_t& json)
        {
            if (json.is_number_float())
                return basyx::object { json.get<float>() };
            else if (json.is_number_integer())
                return basyx::object { json.get<int>() };
            else if (json.is_string())
                return basyx::object { json.get<std::string>() };

            // Get the typeId and value node
            //auto typeId = json[basyx::serialization::typeIdSpecifier].get<std::string>();
            //auto valueJson = json[basyx::serialization::valueSpecifier];

            //if (typeId == basyx::serialization::basysType<int>::string) {
            //	return basyx::object{ valueJson.get<int>() };
            //}
            //else if (typeId == basyx::serialization::basysType<bool>::string) {
            //	return basyx::object{ valueJson.get<bool>() };
            //}
            //else if (typeId == basyx::serialization::basysType<float>::string) {
            //	return basyx::object{ valueJson.get<float>() };
            //}
            //else if (typeId == basyx::serialization::basysType<double>::string) {
            //	return basyx::object{ valueJson.get<double>() };
            //}
            //else if (typeId == basyx::serialization::basysType<char>::string) {
            //	return basyx::object{ valueJson.get<char>() };
            //}
            //else if (typeId == basyx::serialization::basysType<std::string>::string) {
            //	return basyx::object{ valueJson.get<std::string>() };
            //}

            return basyx::object::make_null();
        }

        static basyx::object object_list(const json_t& json)
        {
            basyx::object::object_list_t objectList;

            for (const auto& val : json) {
                objectList.push_back(deserialize(val));
            }

            return objectList;
		}

        // Deserializes an objectMap from the given JSON
        static basyx::object::object_map_t objectMap(const json_t& json)
        {
            //auto size = json[basyx::serialization::sizeSpecifier].get<std::size_t>();
            auto size = json.size();

            basyx::object::object_map_t objectMap { size };

            for (const auto& element : json.items()) {
                objectMap.emplace(element.key(), deserialize(element.value()));
            };

            return objectMap;
        }
    };
};
};
};

#endif
