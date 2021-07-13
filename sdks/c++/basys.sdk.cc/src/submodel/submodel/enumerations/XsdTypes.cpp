#include <BaSyx/submodel/enumerations/XsdTypes.h>

#include <array>
#include <algorithm>
#include <memory>
#include <string>

using namespace basyx::submodel;

using enum_pair_t = std::pair<const char*, XsdTypes>;

static const std::array<enum_pair_t, 24> string_to_enum = 
{
    std::make_pair("xsd:NotSupported",  XsdTypes::xsd_NotSupported),
    std::make_pair("xsd:boolean", XsdTypes::xsd_boolean),
    std::make_pair("xsd:byte", XsdTypes::xsd_byte),
    std::make_pair("xsd:short", XsdTypes::xsd_short),
    std::make_pair("xsd:int", XsdTypes::xsd_int),
    std::make_pair("xsd:long", XsdTypes::xsd_long),
    std::make_pair("xsd:unsignedByte", XsdTypes::xsd_unsignedByte),
    std::make_pair("xsd:unsignedShort", XsdTypes::xsd_unsignedShort),
    std::make_pair("xsd:unsignedInt", XsdTypes::xsd_unsignedInt),
    std::make_pair("xsd:unsignedLong", XsdTypes::xsd_unsignedLong),
    std::make_pair("xsd:double", XsdTypes::xsd_double),
    std::make_pair("xsd:float", XsdTypes::xsd_float),
    std::make_pair("xsd:string", XsdTypes::xsd_string),
    std::make_pair("xsd:anyuri", XsdTypes::xsd_anyuri),
    std::make_pair("xsd:date", XsdTypes::xsd_date),
    std::make_pair("xsd:dateTime", XsdTypes::xsd_dateTime),
    std::make_pair("xsd:dayTimeDuration", XsdTypes::xsd_dayTimeDuration),
    std::make_pair("xsd:yearMonthDuration", XsdTypes::xsd_yearMonthDuration),
    std::make_pair("xsd:time", XsdTypes::xsd_time),
    std::make_pair("xsd:gYearMonth", XsdTypes::xsd_gYearMonth),
    std::make_pair("xsd:gYear", XsdTypes::xsd_gYear),
    std::make_pair("xsd:gMonthDay", XsdTypes::xsd_gMonthDay),
    std::make_pair("xsd:gDay", XsdTypes::xsd_gDay),
    std::make_pair("xsd:gMonth", XsdTypes::xsd_gMonth),
};

XsdTypes XsdTypes_::from_string(const std::string & name)
{
    auto pair = std::find_if(string_to_enum.begin(), string_to_enum.end(), 
		[&name](const enum_pair_t & pair) {
			return !name.compare(pair.first);
	});

    return pair->second;
}

const char * XsdTypes_::to_string(XsdTypes value)
{
    auto pair = std::find_if(string_to_enum.begin(), string_to_enum.end(), 
		[value](const enum_pair_t & pair) {
			return value == pair.second;
	});

    return pair->first;
}

