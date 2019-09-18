/*
 * any_serializer.h
 *
 *  Created on: 21.03.2019
 *      Author: psota
 */

#ifndef BASYX_JSON_DESERIALIZER_H
#define BASYX_JSON_DESERIALIZER_H

#include <json/json.hpp>

#include <basyx/any.h>
#include <basyx/types.h>

#include <util/array.h>

#include "typeid.h"

namespace basyx {
namespace serialization {
namespace json {
    using json_t = nlohmann::json;

    // This class handles the actual deserialization of the BaSyx JSON format
    // Assumes that every JSON is well formed and adheres to the VAB serialization standard
    class deserialize_helper {
    public:
        static basyx::any deserialize(const json_t& json)
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
                return basyx::any { std::forward<basyx::objectMap_t>(deserialize_helper::objectMap(json)) };
            } else // deserialize typed basyx::array
                //				if (basysType == basyx::serialization::arraySpecifier)
                if (json.is_array()) {
                return deserialize_helper::object_list(json);
            }

			return basyx::any{ nullptr };
        }

    private:
        // Deserialize a fundamental type (and std::string) from JSON and return a basyx::any object holding the deserialized value
        static basyx::any fundamental(const json_t& json)
        {
            if (json.is_number_float())
                return basyx::any { json.get<float>() };
            else if (json.is_number_integer())
                return basyx::any { json.get<int>() };
            else if (json.is_string())
                return basyx::any { json.get<std::string>() };

            // Get the typeId and value node
            //auto typeId = json[basyx::serialization::typeIdSpecifier].get<std::string>();
            //auto valueJson = json[basyx::serialization::valueSpecifier];

            //if (typeId == basyx::serialization::basysType<int>::string) {
            //	return basyx::any{ valueJson.get<int>() };
            //}
            //else if (typeId == basyx::serialization::basysType<bool>::string) {
            //	return basyx::any{ valueJson.get<bool>() };
            //}
            //else if (typeId == basyx::serialization::basysType<float>::string) {
            //	return basyx::any{ valueJson.get<float>() };
            //}
            //else if (typeId == basyx::serialization::basysType<double>::string) {
            //	return basyx::any{ valueJson.get<double>() };
            //}
            //else if (typeId == basyx::serialization::basysType<char>::string) {
            //	return basyx::any{ valueJson.get<char>() };
            //}
            //else if (typeId == basyx::serialization::basysType<std::string>::string) {
            //	return basyx::any{ valueJson.get<std::string>() };
            //}

            return basyx::any{ nullptr };
        }

        static basyx::any object_list(const json_t& json)
        {
            basyx::objectCollection_t objectList;

            for (const auto& val : json) {
                objectList.push_back(deserialize(val));
            }

            return objectList;
        }

        // Deserialize basyx::array from JSON and return a basyx::any object holding the deserialized array
        static basyx::any array(const json_t& json)
        {
            auto arrayType = json[basyx::serialization::arrayTypeSpecifier].get<std::string>();

            if (arrayType == basyx::serialization::basysType<int>::string) {
                return deserialize_helper::typed_array<int>(json);
            } else if (arrayType == "object") {
                return deserialize_helper::typed_array<int>(json);
            } else if (arrayType == basyx::serialization::basysType<bool>::string) {
                return deserialize_helper::typed_array<bool>(json);
            } else if (arrayType == basyx::serialization::basysType<float>::string) {
                return deserialize_helper::typed_array<float>(json);
            } else if (arrayType == basyx::serialization::basysType<double>::string) {
                return deserialize_helper::typed_array<double>(json);
            } else if (arrayType == basyx::serialization::basysType<char>::string) {
                return deserialize_helper::typed_array<char>(json);
            } else if (arrayType == basyx::serialization::basysType<std::string>::string) {
                return deserialize_helper::typed_array<std::string>(json);
            }
        }

        // This function creates a typed basyx::array of type T from JSON
        // Assumes that every serialized array value in the json is of the same type
        template <typename T>
        static basyx::array<T> typed_array(const json_t& json)
        {
            auto size = json[basyx::serialization::sizeSpecifier].get<std::size_t>();

            auto array = basyx::make_array<T>(size);

            for (const auto& element : json.items()) {
                if (!element.value().is_object())
                    continue;

                std::size_t index = std::stoi(element.key());
                array[index] = element.value()[basyx::serialization::valueSpecifier].get<T>();
            }

            return array;
        }

        // Deserializes an objectMap from the given JSON
        static basyx::objectMap_t objectMap(const json_t& json)
        {
            //auto size = json[basyx::serialization::sizeSpecifier].get<std::size_t>();
            auto size = json.size();

            basyx::objectMap_t objectMap { size };

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
