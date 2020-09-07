#ifndef BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_IEVENT_H
#define BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_IEVENT_H

#include <BaSyx/submodel/api_v2/submodelelement/ISubmodelElement.h>

namespace basyx {
namespace submodel {
namespace api {

class IEvent
    : public virtual ISubmodelElement
{
public:
  ~IEvent() = 0;

  virtual KeyElements getKeyElementType() const override { return KeyElements::Event; };
};

inline IEvent::~IEvent() = default;

}
}
}
#endif /* BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_IEVENT_H */
