#ifndef BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_IBASICEVENT_H
#define BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_IBASICEVENT_H

#include <BaSyx/submodel/api_v2/submodelelement/IEvent.h>

namespace basyx {
namespace submodel {
namespace api {

/**
 * Mandatory members: observed
 */
class IBasicEvent
  : public api::IEvent
{
public:
  ~IBasicEvent() = 0;

  virtual const IReference & getObserved() const = 0;

  virtual KeyElements getKeyElementType() const override { return KeyElements::BasicEvent; };
};

inline IBasicEvent::~IBasicEvent() = default;

}
}
}
#endif /* BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_IBASICEVENT_H */
