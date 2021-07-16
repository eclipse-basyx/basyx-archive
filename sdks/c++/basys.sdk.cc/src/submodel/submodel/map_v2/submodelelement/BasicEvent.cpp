#include <BaSyx/submodel/map_v2/submodelelement/BasicEvent.h>

namespace basyx {
namespace submodel {
namespace map {

constexpr char BasicEvent::Path::Observed[];

BasicEvent::BasicEvent(const std::string & idShort, Reference observed, ModelingKind kind)
  : SubmodelElement{idShort, kind}
  , observed{observed}
{
  this->map.insertKey(Path::Observed, this->observed.getMap());
}

BasicEvent::BasicEvent(basyx::object obj)
  : SubmodelElement{obj}
  , observed{obj.getProperty(Path::Observed)}
{
  this->map.insertKey(Path::Observed, this->observed.getMap());
}

const api::IReference & BasicEvent::getObserved() const
{
  return this->observed;
}

}
}
}
