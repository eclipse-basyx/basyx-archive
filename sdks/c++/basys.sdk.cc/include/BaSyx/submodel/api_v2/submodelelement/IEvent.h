#ifndef BASYX_API_V2_SDK_IEVENT_H
#define BASYX_API_V2_SDK_IEVENT_H

#include <BaSyx/submodel/api_v2/submodelelement/ISubmodelElement.h>

namespace basyx {
namespace submodel {
namespace api {

class IEvent
    : public virtual ISubmodelElement
{
public:
  ~IEvent() = 0;
};

inline IEvent::~IEvent() = default;

}
}
}
#endif //BASYX_API_V2_SDK_IEVENT_H
