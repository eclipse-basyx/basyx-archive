#ifndef BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_IRELATIONSHIPELEMENT_H
#define BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_IRELATIONSHIPELEMENT_H

#include <BaSyx/submodel/api_v2/submodelelement/ISubmodelElement.h>

namespace basyx {
namespace submodel {
namespace api {

/**
 * Mandatory members: first, second
 */
class IRelationshipElement
    : public virtual ISubmodelElement
{
public:
  virtual ~IRelationshipElement() = 0;

  virtual IReferable & getFirst() const = 0;
  virtual IReferable & getSecond() const = 0;

  virtual KeyElements getKeyElementType() const override { return KeyElements::RelationshipElement; };
};

inline IRelationshipElement::~IRelationshipElement() = default;

}
}
}
#endif /* BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_IRELATIONSHIPELEMENT_H */
