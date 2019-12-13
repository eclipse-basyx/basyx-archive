/*
 * Identifier.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/map/identifier/Identifier.h>
#include <BaSyx/submodel/map/identifier/IdentifierType.h>

namespace basyx {
namespace submodel {

Identifier::Identifier()
  : vab::ElementMap {}
{
  this->map.insertKey(Path::Id, "");
  this->map.insertKey(Path::IdType, "");
}

Identifier::Identifier(const std::string & id, const std::string & idType)
  : vab::ElementMap {}
{
  this->map.insertKey(Path::Id, id);
  this->map.insertKey(Path::IdType, idType);
}

Identifier::Identifier(basyx::object object)
  :vab::ElementMap {object}
{}

Identifier::Identifier(const std::shared_ptr<IIdentifier>& other)
  : Identifier {other->getId(), other->getIdType()}
{}

Identifier::Identifier(const IIdentifier & other)
  : Identifier {other.getId(), other.getIdType()}
{}

std::string Identifier::getIdType() const
{
  return this->map.getProperty(Path::IdType).GetStringContent();
}

std::string Identifier::getId() const
{
  return this->map.getProperty(Path::Id).GetStringContent();
}

}
}
