/*
 * JSONTools2.h
 *
 *  Created on: Mar 17, 2019
 *      Author: psota
 */


#ifndef JSON_H_
#define JSON_H_

#include "json/json.hpp"
#include "json/typeid.h"

#include <util/any.h>

#include <json/json_serializer.h>
#include <json/json_deserializer.h>

namespace basyx
{
	namespace json
	{
		using json_t = nlohmann::json;

		template<typename T>
		inline json_t serialize(const T & t)
		{
			json_t json;
			serialize_helper(json, t);
			return json;
		}

		inline basyx::any deserialize(const json_t & json)
		{
			return deserialize_helper::deserialize(json);
		};

		inline basyx::any deserialize(const std::string & jsonString)
		{
			return deserialize(json_t::parse(jsonString));
		}
	};
};

#endif