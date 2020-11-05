#include <BaSyx/controlcomponent/enumerations/OccupationState.h>

#include <array>
#include <algorithm>
#include <memory>
#include <string>

using namespace basyx::controlcomponent;

using enum_pair_t = std::pair<const char*, OccupationState>;

static const std::array<enum_pair_t, 4> string_to_enum = 
{
    std::make_pair("free",  OccupationState::free),
    std::make_pair("occupied", OccupationState::occupied),
    std::make_pair("priority", OccupationState::priority),
    std::make_pair("local", OccupationState::local),
};

OccupationState OccupationState_::from_string(const std::string & name)
{
    auto pair = std::find_if(string_to_enum.begin(), string_to_enum.end(), 
		[&name](const enum_pair_t & pair) {
			return !name.compare(pair.first);
	});

    return pair->second;
}

const char * OccupationState_::to_string(OccupationState value)
{
    auto pair = std::find_if(string_to_enum.begin(), string_to_enum.end(), 
		[value](const enum_pair_t & pair) {
			return value == pair.second;
	});

    return pair->first;
}

