#ifndef BASYX_SUBMODEL_MAP_ANNOTATEDRELATIONSHIPELEMENT_H
#define BASYX_SUBMODEL_MAP_ANNOTATEDRELATIONSHIPELEMENT_H

#include <BaSyx/submodel/api_v2/submodelelement/IAnnotatedRelationshipElement.h>
#include <BaSyx/submodel/map_v2/submodelelement/RelationshipElement.h>
#include <BaSyx/submodel/map_v2/submodelelement/DataElement.h>
#include <BaSyx/submodel/map_v2/common/ElementContainer.h>

namespace basyx {
namespace submodel {
namespace map {

class AnnotatedRelationshipElement 
  : public virtual api::IAnnotatedRelationshipElement
  , public RelationshipElement
{
public:
  struct Path {
    static constexpr char Annotation[] = "annotation";
  };
private:
  ElementContainer<IDataElement> annotations;

public:
  AnnotatedRelationshipElement(const Referable & first, const Referable & second, const std::string & idShort, ModelingKind kind = ModelingKind::Instance);

  const api::IElementContainer<IDataElement> & getAnnotation() const override;
  void addAnnotation(std::unique_ptr<DataElement> );

  virtual KeyElements getKeyElementType() const override { return KeyElements::AnnotatedRelationshipElement; };
};

}
}
}
#endif //BASYX_SUBMODEL_MAP_ANNOTATEDRELATIONSHIPELEMENT_H
