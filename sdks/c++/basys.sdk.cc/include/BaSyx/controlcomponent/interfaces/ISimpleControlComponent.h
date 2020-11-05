#ifndef BASYX_CONTROLCOMPONENT_ISIMPLECONTROLCOMPONENT_H
#define BASYX_CONTROLCOMPONENT_ISIMPLECONTROLCOMPONENT_H

namespace basyx {
namespace controlcomponent {

class ISimpleControlComponent
{
public:
  virtual ~ISimpleControlComponent() = 0;
};

inline ISimpleControlComponent::~ISimpleControlComponent() = default;

}
}

#endif //BASYX_API_V2_SDK_ISIMPLECONTROLCOMPONENT_H
