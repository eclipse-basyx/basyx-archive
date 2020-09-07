/*
 * basyx::serialization::json::json
 *
 *  Created on: Mar 17, 2019
 *      Author: psota
 */

#ifndef BASYX_SHARED_SERIALIZATION_JSON_JSON_H
#define BASYX_SHARED_SERIALIZATION_JSON_JSON_H

#include <nlohmann/json.hpp>

#include <BaSyx/log/log.h>

#include <BaSyx/shared/object/object_header.h>

#include <BaSyx/shared/serialization/json/json_deserializer.h>
#include <BaSyx/shared/serialization/json/json_serializer.h>
#include <BaSyx/shared/serialization/json/typeid.h>

namespace basyx {
	using json_t = nlohmann::json;
namespace serialization {
namespace json {

    template <typename T>
    inline json_t serialize(const T& t)
    {
        json_t json;
        serialize_helper(json, t);

        basyx::log::topic("Serializer").debug("Serialized: \n{}", json.dump(4));

        return json;
    }

    inline basyx::object deserialize(const json_t& json)
    {
        basyx::log::topic("Serializer").debug("Deserializing: \n{}", json.dump(4));

        return deserialize_helper::deserialize(json);
    };

    inline basyx::object deserialize(const std::string& jsonString)
    {
        return deserialize(json_t::parse(jsonString));
    }
};
};
};

#endif /* BASYX_SHARED_SERIALIZATION_JSON_JSON_H */
