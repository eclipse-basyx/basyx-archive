#include <BaSyx/submodel/simple/submodelelement/Entity.h>


namespace basyx {
namespace submodel {
namespace simple {

Entity::Entity(EntityType entityType, const std::string & idShort, ModelingKind kind)
  : SubmodelElement(idShort, kind)
  , entityType(entityType)
{
  this->statements = util::make_unique<ElementContainer<api::ISubmodelElement>>();
}

api::IElementContainer<api::ISubmodelElement> & Entity::getStatement()
{
  return *this->statements;
}

void Entity::addStatement(std::unique_ptr<SubmodelElement> statement)
{
  this->statements->addElement(std::move(statement));
}

EntityType Entity::getEntityType() const
{
  return this->entityType;
}

const api::IReference * const Entity::getAssetRef() const
{
  if (assetRef.empty())
    return nullptr;

  return &assetRef;
}

void Entity::setAssetRef(const Reference & assetRef)
{
  this->assetRef = assetRef;
}

}
}
}
