/*
 * Identifier.cpp
 *
 *      Author: wendel
 */

#include "Identifier.h"
#include "IdentifierType.h"

using namespace basyx::submodel;

Identifier::Identifier()
{
	this->map.insertKey(Path::Id, "");
	this->map.insertKey(Path::IdType, "");
}

Identifier::Identifier(const std::string & id, const std::string & idType)
	: vab::ElementMap{}
{
	this->map.insertKey(Path::Id, id);
	this->map.insertKey(Path::IdType, idType);
}

Identifier::Identifier(basyx::object object)
	:vab::ElementMap(map)
{
}

std::string Identifier::getIdType() const
{
	return this->map.getProperty(Path::IdType).GetStringContent();
}

std::string Identifier::getId() const
{
	return this->map.getProperty(Path::Id).GetStringContent();
}
