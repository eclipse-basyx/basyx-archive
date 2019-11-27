/*
 * Key.cpp
 *
 *      Author: wendel
 */

#include "Key.h"

#include "basyx/object.h"

namespace basyx {
namespace submodel {

Key::Key(const std::string & type, const bool & local, const std::string & value, const std::string & idType)
	:map(basyx::object::make_map())
{
	map.insertKey("type", type);
	map.insertKey("local", local);
	map.insertKey("value", value);
	map.insertKey("idType", idType);
}

std::string Key::getType() const
{
	return map.getProperty("type").GetStringContent();
}

bool Key::isLocal() const
{
	return map.getProperty("local").Get<bool>();
}

std::string Key::getValue() const
{
	return map.getProperty("value").GetStringContent();
}

std::string Key::getidType() const
{
	return map.getProperty("idType").GetStringContent();
}

void Key::setType(const std::string & type)
{
	map.insertKey("type", type, true);
}

void Key::setLocal(const bool & local)
{
	map.insertKey("local", local, true);
}

void Key::setValue(const std::string & value)
{
	map.insertKey("value", value, true);
}

void Key::setIdType(const std::string & idType)
{
	map.insertKey("idType", idType, true);
}

}
}