#ifndef BASYX_SHARED_ENUMS_H
#define BASYX_SHARED_ENUMS_H

namespace basyx {

enum class EntityType : char {
    CoManagedEntity = 0,
    SelfManagedEntity = 1,
};

enum class Category : char {
    Constant = 0,
    Parameter = 1,
    Variable = 2,
};

};

#endif /* BASYX_SHARED_ENUMS_H */
