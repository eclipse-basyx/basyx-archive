#include <BaSyx/submodel/enumerations/LocalKeyType.h>

#include <array>
#include <algorithm>
#include <memory>
#include <string>

using namespace basyx::submodel;

using enum_pair_t = std::pair<const char*, LocalKeyType>;

static const std::array<enum_pair_t, 2> string_to_enum = 
{
    std::make_pair("IdShort",  LocalKeyType::IdShort),
    std::make_pair("FragmentId", LocalKeyType::FragmentId),
};

LocalKeyType LocalKeyType_::from_string(const std::string & name)
{
    auto pair = std::find_if(string_to_enum.begin(), string_to_enum.end(), 
		[&name](const enum_pair_t & pair) {
			return !name.compare(pair.first);
	});

    return pair->second;
}

const char * LocalKeyType_::to_string(LocalKeyType value)
{
    auto pair = std::find_if(string_to_enum.begin(), string_to_enum.end(), 
		[value](const enum_pair_t & pair) {
			return value == pair.second;
	});

    return pair->first;
}

