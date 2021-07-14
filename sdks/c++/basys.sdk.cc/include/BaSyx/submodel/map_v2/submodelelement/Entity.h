#ifndef BASYX_SUBMODEL_MAP_ENTITY_H
#define BASYX_SUBMODEL_MAP_ENTITY_H

#include <BaSyx/submodel/api_v2/submodelelement/IEntity.h>
#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElement.h>
#include <BaSyx/submodel/map_v2/common/ElementContainer.h>

namespace basyx {
namespace submodel {
namespace map {

class Entity 
  : public virtual api::IEntity
  , public SubmodelElement
  , public ModelType<ModelTypes::Entity>
{
public:
  struct Path {
    static constexpr char EntityType[] = "entityType";
    static constexpr char Kind[] = "kind";
    static constexpr char Statement[] = "statement";
    static constexpr char Asset[] = "asset";
  };

private:
  ElementContainer<api::ISubmodelElement> statements;
  Reference asset;

public:
  Entity(EntityType, const std::string &, ModelingKind kind = ModelingKind::Instance);
  Entity(basyx::object);

  api::IElementContainer<ISubmodelElement> & getStatement() override;
  void addStatement(std::unique_ptr<SubmodelElement>);

  EntityType getEntityType() const override;

  const api::IReference * const getAssetRef() const override;
  void setAssetRef(const Reference & assetRef);

  virtual ModelingKind getKind() const override;
  virtual KeyElements getKeyElementType() const override { return KeyElements::Entity; };
};

}
}
}
#endif //BASYX_SUBMODEL_MAP_ENTITY_H
