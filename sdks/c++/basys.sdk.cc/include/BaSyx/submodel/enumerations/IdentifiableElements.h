#ifndef BASYX_SUBMODEL_ENUM_IdentifiableElements_H
#define BASYX_SUBMODEL_ENUM_IdentifiableElements_H

#include <string>

namespace basyx {
namespace submodel {

enum class IdentifiableElements {
    Asset,
    AssetAdministrationShell,
    ConceptDescription,
    Submodel,
};

class IdentifiableElements_
{
public:
    static IdentifiableElements from_string(const std::string & name);
    static const char * to_string(IdentifiableElements value);
};


}
}

#endif
