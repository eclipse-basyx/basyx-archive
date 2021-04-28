#ifndef BASYX_SUBMODEL_ENTITY_H
#define BASYX_SUBMODEL_ENTITY_H

#include <BaSyx/submodel/api_v2/submodelelement/IEntity.h>

#include <BaSyx/submodel/simple/submodelelement/SubmodelElement.h>
#include <BaSyx/submodel/simple/common/ElementContainer.h>

namespace basyx {
namespace submodel {
namespace simple {

class Entity
  : public virtual api::IEntity
  , public SubmodelElement
{
private:
  std::unique_ptr<ElementContainer<api::ISubmodelElement>> statements;
  EntityType entityType;
  Reference assetRef;

public:
  Entity(EntityType, const std::string &, ModelingKind kind = ModelingKind::Instance);

  api::IElementContainer<ISubmodelElement> & getStatement() override;
  void addStatement(std::unique_ptr<SubmodelElement>);

  EntityType getEntityType() const override;

  const api::IReference * const getAssetRef() const override;
  void setAssetRef(const Reference & assetRef);

};

}
}
}
#endif //BASYX_SUBMODEL_ENTITY_H
