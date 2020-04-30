#ifndef BASYX_SUBMODEL_API_V2_REFERENCE_KEYELEMENTS_H
#define BASYX_SUBMODEL_API_V2_REFERENCE_KEYELEMENTS_H

namespace basyx {
namespace submodel {

    // Enumeration:  KeyElements
    // Explanation : Enumeration of different key value types within a key.
    // Set of : ReferableElements
    enum class KeyElements {
        GlobalReference,
        FragmentReference,

        // Taken from ReferableElements
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
        Property,
        Range,
        ReferenceElement,
        RelationshipElement,
        SubmodelElement,
        SubmodelElementCollection,
        View,

        // Taken from IdentifiableElements
        Asset,
        AssetAdministrationShell,
        ConceptDescription,
        Submodel,

        Unknown
    };

    struct KeyElementsUtil {
        static KeyElements fromString(const std::string& str)
        {
            const std::unordered_map<std::string, KeyElements> table = {
                { "GlobalReference", KeyElements::GlobalReference },
                { "FragmentReference", KeyElements::FragmentReference },
                { "AccessPermissionRule", KeyElements::AccessPermissionRule },
                { "AnnotatedRelationshipElement", KeyElements::AnnotatedRelationshipElement },
                { "BasicEvent", KeyElements::BasicEvent },
                { "Blob", KeyElements::Blob },
                { "Capability", KeyElements::Capability },
                { "ConceptDictionary", KeyElements::ConceptDictionary },
                { "DataElement", KeyElements::DataElement },
                { "Entity", KeyElements::Entity },
                { "Event", KeyElements::Event },
                { "File", KeyElements::File },
                { "MultiLanguageProperty", KeyElements::MultiLanguageProperty },
                { "Operation", KeyElements::Operation },
                { "Property", KeyElements::Property },
                { "Range", KeyElements::Range },
                { "ReferenceElement", KeyElements::ReferenceElement },
                { "RelationshipElement", KeyElements::RelationshipElement },
                { "SubmodelElement", KeyElements::SubmodelElement },
                { "SubmodelElementCollection", KeyElements::SubmodelElementCollection },
                { "View", KeyElements::View },
                { "Asset", KeyElements::Asset },
                { "AssetAdministrationShell", KeyElements::AssetAdministrationShell },
                { "ConceptDescription", KeyElements::ConceptDescription },
                { "Submodel", KeyElements::Submodel },
                { "Unknown", KeyElements::Unknown }
            };

            if (table.find(str) != table.end())
                return table.at(str);

            return KeyElements::Unknown;
        };

        static std::string toString(KeyElements keyElement)
        {
            switch (keyElement) {
            case KeyElements::GlobalReference:
                return "GlobalReference";
                break;
            case KeyElements::FragmentReference:
                return "FragmentReference";
                break;
            case KeyElements::AccessPermissionRule:
                return "AccessPermissionRule";
                break;
            case KeyElements::AnnotatedRelationshipElement:
                return "AnnotatedRelationshipElement";
                break;
            case KeyElements::BasicEvent:
                return "BasicEvent";
                break;
            case KeyElements::Blob:
                return "Blob";
                break;
            case KeyElements::Capability:
                return "Capability";
                break;
            case KeyElements::ConceptDictionary:
                return "ConceptDictionary";
                break;
            case KeyElements::DataElement:
                return "DataElement";
                break;
            case KeyElements::Entity:
                return "Entity";
                break;
            case KeyElements::Event:
                return "Event";
                break;
            case KeyElements::File:
                return "File";
                break;
            case KeyElements::MultiLanguageProperty:
                return "MultiLanguageProperty";
                break;
            case KeyElements::Operation:
                return "Operation";
                break;
            case KeyElements::Property:
                return "Property";
                break;
            case KeyElements::Range:
                return "Range";
                break;
            case KeyElements::ReferenceElement:
                return "ReferenceElement";
                break;
            case KeyElements::RelationshipElement:
                return "RelationshipElement";
                break;
            case KeyElements::SubmodelElement:
                return "SubmodelElement";
                break;
            case KeyElements::SubmodelElementCollection:
                return "SubmodelElementCollection";
                break;
            case KeyElements::View:
                return "View";
                break;
            case KeyElements::Asset:
                return "Asset";
                break;
            case KeyElements::AssetAdministrationShell:
                return "AssetAdministrationShell";
                break;
            case KeyElements::ConceptDescription:
                return "ConceptDescription";
                break;
            case KeyElements::Submodel:
                return "Submodel";
                break;
            case KeyElements::Unknown:
            default:
                return "Unknown";
                break;
            };
        };
    };

    // Enumeration:  ReferableElements
    // Explanation : Enumeration of all referable elements within an asset administration shell
    // Set of : IdentifiableElements
    enum class ReferableElements {
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
        Property,
        Range,
        ReferenceElement,
        RelationshipElement,
        SubmodelElement,
        SubmodelElementCollection,
        View,

        // Taken from IdentifiableElements
        Asset,
        AssetAdministrationShell,
        ConceptDescription,
        Submodel
    };

    // Enumeration:  IdentifiableElements
    // Explanation : Enumeration of all identifiable elements within an asset administration shell that are not identifiable
    enum class IdentifiableElements {
        Asset,
        AssetAdministrationShell,
        ConceptDescription,
        Submodel
    };

}
}

#endif /* BASYX_SUBMODEL_API_V2_REFERENCE_KEYELEMENTS_H */
