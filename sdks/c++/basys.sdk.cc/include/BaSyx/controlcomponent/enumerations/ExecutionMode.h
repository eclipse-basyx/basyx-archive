#ifndef BASYX_CONTROLCOMPONENT_ENUM_EXECUTIONMODE_H
#define BASYX_CONTROLCOMPONENT_ENUM_EXECUTIONMODE_H

#include <string>

namespace basyx {
namespace controlcomponent {

enum class ExecutionMode {
    Auto = 1,
    Semiauto = 2,
    Manual = 3,
    Reserved = 4,
    Simulation = 5
};

}
}

#endif
