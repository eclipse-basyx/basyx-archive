#include <BaSyx/submodel/simple/submodelelement/BasicEvent.h>

namespace basyx {
namespace submodel {
namespace simple {

BasicEvent::BasicEvent(const std::string & idShort, Reference observed, ModelingKind kind)
  : SubmodelElement {idShort, kind}
  , observed {observed}
{}

const api::IReference & BasicEvent::getObserved() const
{
  return this->observed;
}

}
}
}
