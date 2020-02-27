/*
 * Key.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/map/reference/Key.h>

#include <BaSyx/shared/object.h>

#include <BaSyx/vab/ElementMap.h>

namespace basyx {
namespace submodel {

	constexpr char Key::KeyElements::ConceptDictionary[];


Key::Key(const std::string & type, const bool & local, const std::string & value, const std::string & idType)
	: vab::ElementMap{}
{
	this->setType(type);
	this->setLocal(local);
	this->setValue(value);
	this->setIdType(idType);
}

Key::Key(basyx::object obj)
  : vab::ElementMap(obj)
{};

std::string Key::getType() const
{
	return map.getProperty(IKey::Path::Type).GetStringContent();
}

bool Key::isLocal() const
{
	return map.getProperty(IKey::Path::Local).Get<bool>();
}

std::string Key::getValue() const
{
	return map.getProperty(IKey::Path::Value).GetStringContent();
}

std::string Key::getidType() const
{
	return map.getProperty(IKey::Path::IdType).GetStringContent();
}

void Key::setType(const std::string & type)
{
	map.insertKey(IKey::Path::Type, type, true);
}

void Key::setLocal(const bool & local)
{
	map.insertKey(IKey::Path::Local, local, true);
}

void Key::setValue(const std::string & value)
{
	map.insertKey(IKey::Path::Value, value, true);
}

void Key::setIdType(const std::string & idType)
{
	map.insertKey(IKey::Path::IdType, idType, true);
}

bool operator==(const IKey & left, const IKey & right)
{
  return (left.getidType() == right.getidType()) and (left.getValue() == right.getValue()) and (left.getType() == right.getType()) and (left.isLocal() == right.isLocal());
}

}
}
