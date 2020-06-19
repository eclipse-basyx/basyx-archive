#ifndef BASYX_SUBMODEL_ENUM_IdentifierType_H
#define BASYX_SUBMODEL_ENUM_IdentifierType_H

#include <string>

namespace basyx {
namespace submodel {

enum class IdentifierType {
    Custom,
    IRDI,
    URI,
    Unknown,
};

class IdentifierType_
{
public:
    static IdentifierType from_string(const std::string & name);
    static const char * to_string(IdentifierType value);
};


}
}

#endif
