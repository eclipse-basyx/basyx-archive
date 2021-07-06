#ifndef BASYX_CONTROLCOMPONENT_ENUM_EXECUTIONORDER_H
#define BASYX_CONTROLCOMPONENT_ENUM_EXECUTIONORDER_H

#include <string>

namespace basyx {
namespace controlcomponent {

enum class ExecutionOrder {
    start,
    complete,
    reset,
    hold,
    unhold,
    suspend,
    unsuspend,
    clear,
    stop,
    abort,
};

class ExecutionOrder_
{
public:
    static ExecutionOrder from_string(const std::string & name);
    static const char * to_string(ExecutionOrder value);
};


}
}

#endif
