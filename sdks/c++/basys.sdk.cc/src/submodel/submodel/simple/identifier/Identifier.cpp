#include <BaSyx/submodel/simple/identifier/Identifier.h>
#include <BaSyx/submodel/enumerations/IdentifierType.h>

namespace basyx {
namespace submodel {
namespace simple {

Identifier::Identifier()
  : idType(IdentifierType::Unknown)
{}

Identifier::Identifier(IdentifierType idType, const std::string &id)
    : idType(idType)
    , id(id)
{
};

Identifier Identifier::Custom(const std::string &id)
{
  return Identifier(IdentifierType::Custom, id);
};

Identifier Identifier::URI(const std::string &uri)
{
  return Identifier(IdentifierType::URI, uri);
};

Identifier Identifier::IRDI(const std::string &irdi)
{
  return Identifier(IdentifierType::IRDI, irdi);
};

IdentifierType Identifier::getIdType() const
{
  return this->idType;
};

const std::string &Identifier::getId() const
{
  return this->id;
};

bool operator==(const Identifier &left, const Identifier &right)
{
  if (left.getIdType() == right.getIdType())
  {
    return left.getId().compare(right.getId()) == 0;
  }
  return false;
}

}
}
}