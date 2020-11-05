#ifndef BASYX_CONTROLCOMPONENT_ISIMPLEPROXYCONTROLCOMPONENT_H
#define BASYX_CONTROLCOMPONENT_ISIMPLEPROXYCONTROLCOMPONENT_H

namespace basyx {
namespace controlcomponent {

class ISimpleProxyControlComponent
{
public:
  virtual ~ISimpleProxyControlComponent() = 0;
};

inline ISimpleProxyControlComponent::~ISimpleProxyControlComponent() = default;

}
}

#endif //BASYX_API_V2_SDK_ISIMPLEPROXYCONTROLCOMPONENT_H
