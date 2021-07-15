#ifndef BASYX_SUBMODEL_SIMPLE_ANNOTATEDRELATIONSHIPELEMENT_H
#define BASYX_SUBMODEL_SIMPLE_ANNOTATEDRELATIONSHIPELEMENT_H

#include <BaSyx/submodel/api_v2/submodelelement/IAnnotatedRelationshipElement.h>
#include <BaSyx/submodel/simple/submodelelement/DataElement.h>
#include <BaSyx/submodel/simple/common/ElementContainer.h>
#include <BaSyx/submodel/simple/submodelelement/RelationshipElement.h>

namespace basyx {
namespace submodel {
namespace simple {

class AnnotatedRelationshipElement
  : public virtual api::IAnnotatedRelationshipElement
  , public RelationshipElement
{
private:
  ElementContainer<IDataElement> annotations;
public:
  AnnotatedRelationshipElement(const Reference &, const Reference &, const std::string & idShort, ModelingKind kind = ModelingKind::Instance);

  const api::IElementContainer<IDataElement> & getAnnotation() const override;
  void addAnnotation(std::unique_ptr<DataElement> dataElement);

  KeyElements getKeyElementType() const override { return KeyElements::AnnotatedRelationshipElement; };
};

}
}
}
#endif //BASYX_SUBMODEL_ANNOTATEDRELATIONSHIPELEMENT_H
