#ifndef BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_ICAPABILITY_H
#define BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_ICAPABILITY_H

#include <BaSyx/submodel/api_v2/submodelelement/ISubmodelElement.h>

namespace basyx {
namespace submodel {
namespace api {

class ICapability
  : ISubmodelElement
{
public:
  ~ICapability() = 0;

  virtual KeyElements getKeyElementType() const override { return KeyElements::Capability; };
};

inline ICapability::~ICapability() = default;

}
}
}
#endif /* BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_ICAPABILITY_H */
