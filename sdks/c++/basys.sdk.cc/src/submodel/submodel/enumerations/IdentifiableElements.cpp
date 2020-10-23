#include <BaSyx/submodel/enumerations/IdentifiableElements.h>

#include <array>
#include <algorithm>
#include <memory>
#include <string>

using namespace basyx::submodel;

using enum_pair_t = std::pair<const char*, IdentifiableElements>;

static const std::array<enum_pair_t, 4> string_to_enum = 
{
    std::make_pair("Asset",  IdentifiableElements::Asset),
    std::make_pair("AssetAdministrationShell", IdentifiableElements::AssetAdministrationShell),
    std::make_pair("ConceptDescription", IdentifiableElements::ConceptDescription),
    std::make_pair("Submodel", IdentifiableElements::Submodel),
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

