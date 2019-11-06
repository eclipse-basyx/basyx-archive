/*
 * Identifier.cpp
 *
 *      Author: wendel
 */

#include "Identifier.h"
#include "IdentifierType.h"

basyx::aas::identifier::impl::Identifier::Identifier() :
  id{""},
  idType {identifierType::IRDI}
{}

basyx::aas::identifier::impl::Identifier::Identifier(const basyx::objectMap_t & map)
{
  auto id_any = map.at(internalIdentifierPaths::ID);
  this->id = id_any.GetStringContent();
  auto id_type_any = map.at(internalIdentifierPaths::IDTYPE);
  this->idType = id_type_any.GetStringContent();
}

std::string basyx::aas::identifier::impl::Identifier::getIdType() const
{
  return this->idType;
}

std::string basyx::aas::identifier::impl::Identifier::getId() const
{
  return this->id;
}
