#ifndef BASYX_SUBMODEL_ENUM_KeyElements_H
#define BASYX_SUBMODEL_ENUM_KeyElements_H

#include <string>

namespace basyx {
namespace submodel {

enum class KeyElements {
    GlobalReference,
    FragmentReference,
    AccessPermissionRule,
    AnnotatedRelationshipElement,
    BasicEvent,
    Blob,
    Capability,
    ConceptDictionary,
    DataElement,
    Entity,
    Event,
    File,
    MultiLanguageProperty,
    Operation,
    OperationVariable,
    Property,
    Range,
    ReferenceElement,
    RelationshipElement,
    SubmodelElement,
    SubmodelElementCollection,
    View,
    Unknown,
    Asset,
    AssetAdministrationShell,
    ConceptDescription,
    Submodel,
};

class KeyElements_
{
public:
    static KeyElements from_string(const std::string & name);
    static const char * to_string(KeyElements value);
};


}
}

#endif
