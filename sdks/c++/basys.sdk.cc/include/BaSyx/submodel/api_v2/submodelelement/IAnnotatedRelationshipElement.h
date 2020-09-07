#ifndef BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_IANNOTATEDRELATIONSHIPELEMENT_H
#define BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_IANNOTATEDRELATIONSHIPELEMENT_H

#include <BaSyx/submodel/api_v2/submodelelement/IDataElement.h>
#include <BaSyx/submodel/api_v2/common/IElementContainer.h>

namespace basyx {
namespace submodel {
namespace api {

class IAnnotatedRelationshipElement : public IDataElement
{
public:
  virtual ~IAnnotatedRelationshipElement() = 0;

  virtual IElementContainer<IDataElement> & getAnnotation() const = 0;

  virtual KeyElements getKeyElementType() const override { return KeyElements::AnnotatedRelationshipElement; };
};

inline IAnnotatedRelationshipElement::~IAnnotatedRelationshipElement() = default;

}
}
}
#endif /* BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_IANNOTATEDRELATIONSHIPELEMENT_H */
