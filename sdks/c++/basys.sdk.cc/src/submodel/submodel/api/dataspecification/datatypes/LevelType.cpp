#include <BaSyx/submodel/api/dataspecification/datatypes/LevelType.h>

#include <unordered_map>
#include <string>

#include <BaSyx/util/util.h>

using namespace basyx::submodel;

static const std::string LevelType_to_string[4] =
{
	"Min",
	"Max",
	"Nom",
	"Typ"
};

static const std::unordered_map<std::string, basyx::submodel::LevelType> string_to_LevelType
{
	{LevelType_to_string[static_cast<char>(LevelType::Min)], LevelType::Min},
	{LevelType_to_string[static_cast<char>(LevelType::Max)], LevelType::Max},
	{LevelType_to_string[static_cast<char>(LevelType::Nom)], LevelType::Nom},
	{LevelType_to_string[static_cast<char>(LevelType::Typ)], LevelType::Typ}
};

const std::string & util::to_string(basyx::submodel::LevelType levelType)
{
	return LevelType_to_string[static_cast<char>(levelType)];
}

template<>
basyx::submodel::LevelType util::from_string<basyx::submodel::LevelType>(const std::string & str)
{
	return string_to_LevelType.at(str);
}