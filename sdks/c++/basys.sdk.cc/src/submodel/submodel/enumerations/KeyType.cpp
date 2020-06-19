#include <BaSyx/submodel/enumerations/KeyType.h>

#include <array>
#include <algorithm>
#include <memory>
#include <string>

using namespace basyx::submodel;

using enum_pair_t = std::pair<const char*, KeyType>;

static const std::array<enum_pair_t, 6> string_to_enum = 
{
    std::make_pair("IdShort",  KeyType::IdShort),
    std::make_pair("FragementId", KeyType::FragementId),
    std::make_pair("Custom", KeyType::Custom),
    std::make_pair("IRDI", KeyType::IRDI),
    std::make_pair("URI", KeyType::URI),
    std::make_pair("Unknown", KeyType::Unknown),
};

KeyType KeyType_::from_string(const std::string & name)
{
    auto pair = std::find_if(string_to_enum.begin(), string_to_enum.end(), 
		[&name](const enum_pair_t & pair) {
			return !name.compare(pair.first);
	});

    return pair->second;
}

const char * KeyType_::to_string(KeyType value)
{
    auto pair = std::find_if(string_to_enum.begin(), string_to_enum.end(), 
		[value](const enum_pair_t & pair) {
			return value == pair.second;
	});

    return pair->first;
}

