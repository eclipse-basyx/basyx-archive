#include <BaSyx/submodel/map_v2/submodelelement/Capability.h>


namespace basyx {
namespace submodel {
namespace map {

Capability::Capability(const std::string & idShort, ModelingKind kind)
  : SubmodelElement(idShort, kind)
{}

Capability::Capability(basyx::object obj)
 : SubmodelElement(obj)
{}

}
}
}
