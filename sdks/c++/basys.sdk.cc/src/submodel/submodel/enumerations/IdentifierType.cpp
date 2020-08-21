#ifdef IDENT_TYPE

#include <BaSyx/submodel/enumerations/IdentifierType.h>

#include <array>
#include <algorithm>
#include <memory>
#include <string>

using namespace basyx::submodel;

using enum_pair_t = std::pair<const char*, IdentifierType>;

static const std::array<enum_pair_t, 4> string_to_enum = 
{
    std::make_pair("Custom",  IdentifierType::Custom),
    std::make_pair("IRDI", IdentifierType::IRDI),
    std::make_pair("URI", IdentifierType::URI),
    std::make_pair("Unknown", IdentifierType::Unknown),
};

IdentifierType IdentifierType_::from_string(const std::string & name)
{
    auto pair = std::find_if(string_to_enum.begin(), string_to_enum.end(), 
		[&name](const enum_pair_t & pair) {
			return !name.compare(pair.first);
	});

    return pair->second;
}

const char * IdentifierType_::to_string(IdentifierType value)
{
    auto pair = std::find_if(string_to_enum.begin(), string_to_enum.end(), 
		[value](const enum_pair_t & pair) {
			return value == pair.second;
	});

    return pair->first;
}

#endif