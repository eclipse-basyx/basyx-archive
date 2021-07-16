#ifndef BASYX_SUBMODEL_SIMPLE_BASICEVENT_H
#define BASYX_SUBMODEL_SIMPLE_BASICEVENT_H

#include <BaSyx/submodel/api_v2/submodelelement/IBasicEvent.h>
#include <BaSyx/submodel/simple/submodelelement/SubmodelElement.h>

namespace basyx {
namespace submodel {
namespace simple {

class BasicEvent 
  : public api::IBasicEvent
  , public SubmodelElement
{
private:
  Reference observed;

public:
  BasicEvent(const std::string & idShort, Reference observed, ModelingKind kind = ModelingKind::Instance);
  BasicEvent() = delete;

  const api::IReference & getObserved() const override;
};

}
}
}
#endif //BASYX_SUBMODEL_MAP_BASICEVENT_H
