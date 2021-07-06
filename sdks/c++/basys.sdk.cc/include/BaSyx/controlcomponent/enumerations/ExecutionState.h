#ifndef BASYX_CONTROLCOMPONENT_ENUM_EXECUTIONSTATE_H
#define BASYX_CONTROLCOMPONENT_ENUM_EXECUTIONSTATE_H

#include <string>

namespace basyx {
namespace controlcomponent {

enum class ExecutionState {
    idle,
    starting,
    execute,
    completing,
    complete,
    resetting,
    holding,
    held,
    unholding,
    suspending,
    suspended,
    unsuspending,
    stopping,
    stopped,
    aborting,
    aborted,
    clearing,
};

class ExecutionState_
{
public:
    static ExecutionState from_string(const std::string & name);
    static const char * to_string(ExecutionState value);
};


}
}

#endif
