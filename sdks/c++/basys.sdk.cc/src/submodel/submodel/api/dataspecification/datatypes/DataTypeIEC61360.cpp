#include <BaSyx/submodel/api/dataspecification/datatypes/DataTypeIEC61360.h>

#include <unordered_map>
#include <string>

#include <BaSyx/util/util.h>

using namespace basyx::submodel;

static const std::string DataTypeIEC61360_to_string[] =
{
	"Date",
	"String",
	"String_Translatable",
	"Real_Measure",
	"Real_Count",
	"Real_Currency",
	"Boolean",
	"Url",
	"Rational",
	"Rational_Measure",
	"Time",
	"Timestamp"
};

static const std::unordered_map<std::string, basyx::submodel::DataTypeIEC61360> string_to_DataTypeIEC61360
{
	{DataTypeIEC61360_to_string[static_cast<char>(DataTypeIEC61360::Date)], DataTypeIEC61360::Date},
	{DataTypeIEC61360_to_string[static_cast<char>(DataTypeIEC61360::String)], DataTypeIEC61360::String},
	{DataTypeIEC61360_to_string[static_cast<char>(DataTypeIEC61360::String_Translatable)], DataTypeIEC61360::String_Translatable},
	{DataTypeIEC61360_to_string[static_cast<char>(DataTypeIEC61360::Real_Measure)], DataTypeIEC61360::Real_Measure},
	{DataTypeIEC61360_to_string[static_cast<char>(DataTypeIEC61360::Real_Count)], DataTypeIEC61360::Real_Count},
	{DataTypeIEC61360_to_string[static_cast<char>(DataTypeIEC61360::Real_Currency)], DataTypeIEC61360::Real_Currency},
	{DataTypeIEC61360_to_string[static_cast<char>(DataTypeIEC61360::Boolean)], DataTypeIEC61360::Boolean},
	{DataTypeIEC61360_to_string[static_cast<char>(DataTypeIEC61360::Url)], DataTypeIEC61360::Url},
	{DataTypeIEC61360_to_string[static_cast<char>(DataTypeIEC61360::Rational)], DataTypeIEC61360::Rational},
	{DataTypeIEC61360_to_string[static_cast<char>(DataTypeIEC61360::Rational_Measure)], DataTypeIEC61360::Rational_Measure},
	{DataTypeIEC61360_to_string[static_cast<char>(DataTypeIEC61360::Time)], DataTypeIEC61360::Time},
	{DataTypeIEC61360_to_string[static_cast<char>(DataTypeIEC61360::Timestamp)], DataTypeIEC61360::Timestamp}
};

const std::string & util::to_string(basyx::submodel::DataTypeIEC61360 type)
{
	return DataTypeIEC61360_to_string[static_cast<char>(type)];
}

template<>
basyx::submodel::DataTypeIEC61360 util::from_string<basyx::submodel::DataTypeIEC61360>(const std::string & str)
{
	return string_to_DataTypeIEC61360.at(str);
}