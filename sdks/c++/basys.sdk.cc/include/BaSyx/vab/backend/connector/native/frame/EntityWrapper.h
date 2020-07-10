#pragma once

#include <BaSyx/shared/object.h>
#include <BaSyx/shared/serialization/json.h>

#include <string>

namespace basyx {
namespace vab {

class EntityWrapper
{
public:
	static json_t build_from_object(const basyx::object & obj);
	static json_t build_from_error(basyx::object::error error, const std::string & message = "");
	//static json_t build_exception(const basyx::object::error & error, const std::string & message);
	//static json_t to_json(const EntityWrapper & result);
	static basyx::object from_json(const json_t & json);
};

}
}