#include <BaSyx/submodel/map_v2/submodelelement/Entity.h>


namespace basyx {
namespace submodel {
namespace map {

constexpr char Entity::Path::EntityType[];
constexpr char Entity::Path::Kind[];
constexpr char Entity::Path::Asset[];
constexpr char Entity::Path::Statement[];

Entity::Entity(EntityType entityType, const std::string & idShort, ModelingKind kind)
  : SubmodelElement(idShort, kind)
{
  this->map.insertKey(Path::EntityType, EntityType_::to_string(entityType));
  this->map.insertKey(Path::Statement, statements.getMap());
}

api::IElementContainer<api::ISubmodelElement> & Entity::getStatement()
{
  return this->statements;
}

void Entity::addStatement(std::unique_ptr<SubmodelElement> statement)
{
  this->statements.addElement(std::move(statement));
}

EntityType Entity::getEntityType() const
{
  return EntityType_::from_string(this->map.getProperty(Path::EntityType).GetStringContent());
}

const api::IReference * const Entity::getAssetRef() const
{
  if (this->map.getProperty(Path::Asset).IsNull())
    return nullptr;
  return &this->asset;
}

void Entity::setAssetRef(const Reference & assetRef)
{
  this->asset = assetRef;
  this->map.insertKey(Path::Asset, asset.getMap());
}

ModelingKind Entity::getKind() const
{
  return ModelingKind_::from_string(this->map.getProperty(Path::Kind).GetStringContent());
}

}
}
}
