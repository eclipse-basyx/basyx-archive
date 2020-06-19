#ifndef BASYX_SUBMODEL_ENUM_KeyType_H
#define BASYX_SUBMODEL_ENUM_KeyType_H

#include <string>

namespace basyx {
namespace submodel {

enum class KeyType {
    IdShort,
    FragementId,
    Custom,
    IRDI,
    URI,
    Unknown,
};

class KeyType_
{
public:
    static KeyType from_string(const std::string & name);
    static const char * to_string(KeyType value);
};


}
}

#endif
