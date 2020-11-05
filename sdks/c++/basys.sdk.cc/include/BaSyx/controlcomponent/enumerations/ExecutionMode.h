#ifndef BASYX_CONTROLCOMPONENT_ENUM_EXECUTIONMODE_H
#define BASYX_CONTROLCOMPONENT_ENUM_EXECUTIONMODE_H

#include <string>

namespace basyx {
namespace controlcomponent {

enum class ExecutionMode {
    Auto,
    Semiauto,
    Manual,
    Reserved,
    Simulation,
};

class ExecutionMode_
{
public:
    static ExecutionMode from_string(const std::string & name);
    static const char * to_string(ExecutionMode value);
};


}
}

#endif
