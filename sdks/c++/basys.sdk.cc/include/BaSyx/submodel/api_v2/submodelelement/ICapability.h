#ifndef BASYX_API_V2_SDK_ICAPABILITY_H
#define BASYX_API_V2_SDK_ICAPABILITY_H

#include <BaSyx/submodel/api_v2/submodelelement/ISubmodelElement.h>

namespace basyx {
namespace submodel {
namespace api {

class ICapability
  : ISubmodelElement
{
public:
  ~ICapability() = 0;
};

inline ICapability::~ICapability() = default;

}
}
}
#endif //BASYX_API_V2_SDK_ICAPABILITY_H
