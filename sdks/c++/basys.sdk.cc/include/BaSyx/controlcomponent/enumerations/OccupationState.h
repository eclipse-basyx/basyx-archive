#ifndef BASYX_CONTROLCOMPONENT_ENUM_OCCUPATIONSTATE_H
#define BASYX_CONTROLCOMPONENT_ENUM_OCCUPATIONSTATE_H

#include <string>

namespace basyx {
namespace controlcomponent {

enum class OccupationState {
    free,
    occupied,
    priority,
    local,
};

class OccupationState_
{
public:
    static OccupationState from_string(const std::string & name);
    static const char * to_string(OccupationState value);
};


}
}

#endif
