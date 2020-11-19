#include <BaSyx/controlcomponent/enumerations/ExecutionMode.h>

#include <array>
#include <algorithm>
#include <memory>
#include <string>

using namespace basyx::controlcomponent;

using enum_pair_t = std::pair<const char*, ExecutionMode>;

static const std::array<enum_pair_t, 5> string_to_enum = 
{
    std::make_pair("Auto",  ExecutionMode::Auto),
    std::make_pair("Semiauto", ExecutionMode::Semiauto),
    std::make_pair("Manual", ExecutionMode::Manual),
    std::make_pair("Reserved", ExecutionMode::Reserved),
    std::make_pair("Simulation", ExecutionMode::Simulation),
};

ExecutionMode ExecutionMode_::from_string(const std::string & name)
{
    auto pair = std::find_if(string_to_enum.begin(), string_to_enum.end(), 
		[&name](const enum_pair_t & pair) {
			return !name.compare(pair.first);
	});

    return pair->second;
}

const char * ExecutionMode_::to_string(ExecutionMode value)
{
    auto pair = std::find_if(string_to_enum.begin(), string_to_enum.end(), 
		[value](const enum_pair_t & pair) {
			return value == pair.second;
	});

    return pair->first;
}

