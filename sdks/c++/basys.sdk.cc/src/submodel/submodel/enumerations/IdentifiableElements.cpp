#include <BaSyx/submodel/enumerations/IdentifiableElements.h>

#include <array>
#include <algorithm>
#include <memory>
#include <string>

using namespace basyx::submodel;

using enum_pair_t = std::pair<const char*, IdentifiableElements>;

static const std::array<enum_pair_t, 18> string_to_enum = 
{
    std::make_pair("AccessPermissionRule",  IdentifiableElements::AccessPermissionRule),
    std::make_pair("AnnotatedRelationshipElemenBasicEvent", IdentifiableElements::AnnotatedRelationshipElemenBasicEvent),
    std::make_pair("Blob", IdentifiableElements::Blob),
    std::make_pair("Capability", IdentifiableElements::Capability),
    std::make_pair("ConceptDictionary", IdentifiableElements::ConceptDictionary),
    std::make_pair("DataElement", IdentifiableElements::DataElement),
    std::make_pair("Entity", IdentifiableElements::Entity),
    std::make_pair("Event", IdentifiableElements::Event),
    std::make_pair("File", IdentifiableElements::File),
    std::make_pair("MultiLanguageProperty", IdentifiableElements::MultiLanguageProperty),
    std::make_pair("Operation", IdentifiableElements::Operation),
    std::make_pair("Property", IdentifiableElements::Property),
    std::make_pair("Range", IdentifiableElements::Range),
    std::make_pair("ReferenceElement", IdentifiableElements::ReferenceElement),
    std::make_pair("RelationshipElement", IdentifiableElements::RelationshipElement),
    std::make_pair("SubmodelElement", IdentifiableElements::SubmodelElement),
    std::make_pair("SubmodelElementCollection", IdentifiableElements::SubmodelElementCollection),
    std::make_pair("View", IdentifiableElements::View),
};

IdentifiableElements IdentifiableElements_::from_string(const std::string & name)
{
    auto pair = std::find_if(string_to_enum.begin(), string_to_enum.end(), 
		[&name](const enum_pair_t & pair) {
			return !name.compare(pair.first);
	});

    return pair->second;
}

const char * IdentifiableElements_::to_string(IdentifiableElements value)
{
    auto pair = std::find_if(string_to_enum.begin(), string_to_enum.end(), 
		[value](const enum_pair_t & pair) {
			return value == pair.second;
	});

    return pair->first;
}

