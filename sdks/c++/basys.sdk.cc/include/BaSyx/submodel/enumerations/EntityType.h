#ifndef BASYX_SUBMODEL_ENUM_EntityType_H
#define BASYX_SUBMODEL_ENUM_EntityType_H

#include <string>

namespace basyx {
namespace submodel {

enum class EntityType {
    CoManagedEntity,
    SelfManagedEntity,
};

class EntityType_
{
public:
    static EntityType from_string(const std::string & name);
    static const char * to_string(EntityType value);
};


}
}

#endif
