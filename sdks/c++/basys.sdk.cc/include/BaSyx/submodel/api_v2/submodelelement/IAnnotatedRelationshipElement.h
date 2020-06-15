#ifndef BASYX_API_V2_SDK_IANNOTATEDRELATIONSHIPELEMENT_H
#define BASYX_API_V2_SDK_IANNOTATEDRELATIONSHIPELEMENT_H

#include <BaSyx/submodel/api_v2/submodelelement/IDataElement.h>
#include <BaSyx/submodel/api_v2/common/IElementContainer.h>

namespace basyx {
namespace submodel {
namespace api {

class IAnnotatedRelationshipElement
{
public:
  virtual ~IAnnotatedRelationshipElement() = 0;

  virtual IElementContainer<IDataElement> & getAnnotation() const = 0;
};

inline IAnnotatedRelationshipElement::~IAnnotatedRelationshipElement() = default;

}
}
}
#endif //BASYX_API_V2_SDK_IANNOTATEDRELATIONSHIPELEMENT_H
