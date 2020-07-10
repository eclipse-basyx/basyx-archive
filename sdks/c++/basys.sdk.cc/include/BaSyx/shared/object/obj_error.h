#ifndef BASYX_ENUM_ERROR_H
#define BASYX_ENUM_ERROR_H

#include <string>

namespace basyx {
namespace detail {

enum class error {
    None,
    PropertyNotFound,
    IndexOutOfBounds,
    NotInvokable,
    ObjectAlreadyExists,
    MalformedRequest,
    ProviderException,
};

class error_
{
public:
    static error from_string(const std::string & name);
    static const char * to_string(error value);
};

}
}

#endif
