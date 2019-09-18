/*
 * JSONTools2.h
 *
 *  Created on: Mar 17, 2019
 *      Author: psota
 */

#ifndef BASYX_SERIALIZATION_JSON_JSON_H
#define BASYX_SERIALIZATION_JSON_JSON_H

#include <json/json.hpp>

#include <log/log.h>

#include <basyx/any.h>

#include <basyx/serialization/json/json_deserializer.h>
#include <basyx/serialization/json/json_serializer.h>
#include <basyx/serialization/json/typeid.h>

namespace basyx {
namespace serialization {
namespace json {
    using json_t = nlohmann::json;

    template <typename T>
    inline json_t serialize(const T& t)
    {
        json_t json;
        serialize_helper(json, t);

        basyx::log::topic("Serializer").debug("Serialized: \n{}", json.dump(4));

        return json;
    }

    inline basyx::any deserialize(const json_t& json)
    {
        basyx::log::topic("Serializer").debug("Deserializing: \n{}", json.dump(4));

        return deserialize_helper::deserialize(json);
    };

    inline basyx::any deserialize(const std::string& jsonString)
    {
        return deserialize(json_t::parse(jsonString));
    }
};
};
};

#endif /* BASYX_SERIALIZATION_JSON_JSON_H */
