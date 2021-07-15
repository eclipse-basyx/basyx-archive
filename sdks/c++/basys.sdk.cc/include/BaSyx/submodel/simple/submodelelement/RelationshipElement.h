#ifndef BASYX_SUBMODEL_SIMPLE_RELATIONSHIPELEMENT_H
#define BASYX_SUBMODEL_SIMPLE_RELATIONSHIPELEMENT_H

#include <BaSyx/submodel/api_v2/submodelelement/IRelationshipElement.h>
#include <BaSyx/submodel/simple/qualifier/Referable.h>
#include <BaSyx/submodel/simple/submodelelement/SubmodelElement.h>

namespace basyx {
namespace submodel {
namespace simple {

class RelationshipElement
  : public virtual api::IRelationshipElement
  , public SubmodelElement
{
private:
  Reference first, second;
public:
  RelationshipElement(const Reference & first, const Reference & second, const std::string & idShort, ModelingKind kind = ModelingKind::Instance);

  const Reference & getFirst() const override;
  const Reference & getSecond() const override;
};

}
}
}
#endif //BASYX_SUBMODEL_SIMPLE_RELATIONSHIPELEMENT_H
