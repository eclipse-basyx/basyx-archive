#include <BaSyx/submodel/simple/submodelelement/Capability.h>
#include <BaSyx/submodel/map_v2/submodelelement/Capability.h>


namespace basyx {
namespace submodel {
namespace simple {

Capability::Capability(const std::string & idShort, ModelingKind kind)
  : SubmodelElement{ idShort, kind }
{}

}
}
}
