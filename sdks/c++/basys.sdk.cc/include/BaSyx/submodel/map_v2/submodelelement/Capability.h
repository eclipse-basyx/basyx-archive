#ifndef BASYX_SUBMODEL_MAP_CAPABILITY_H
#define BASYX_SUBMODEL_MAP_CAPABILITY_H

#include <BaSyx/submodel/api_v2/submodelelement/ICapability.h>
#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElement.h>

namespace basyx {
namespace submodel {
namespace map {

class Capability 
  : public virtual api::ICapability
  , public SubmodelElement
  , public ModelType<ModelTypes::Capability>
{
public:
  Capability(const std::string & idShort, ModelingKind kind = ModelingKind::Instance);
  Capability(basyx::object);

  virtual KeyElements getKeyElementType() const override { return KeyElements::Capability; };
};

}
}
}
#endif //BASYX_SUBMODEL_MAP_CAPABILITY_H
