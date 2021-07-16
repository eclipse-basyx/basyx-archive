#ifndef BASYX_SUBMODEL_SIMPLE_CAPABILITY_H
#define BASYX_SUBMODEL_SIMPLE_CAPABILITY_H

#include <BaSyx/submodel/api_v2/submodelelement/ICapability.h>
#include <BaSyx/submodel/simple/submodelelement/SubmodelElement.h>

namespace basyx {
namespace submodel {
namespace simple {

class Capability
  : public api::ICapability
  , public SubmodelElement
{
  Capability(const std::string & idShort, ModelingKind kind = ModelingKind::Instance);

  KeyElements getKeyElementType() const override { return KeyElements::Capability; };
};

}
}
}
#endif //BASYX_SUBMODEL_MAP_CAPABILITY_H
